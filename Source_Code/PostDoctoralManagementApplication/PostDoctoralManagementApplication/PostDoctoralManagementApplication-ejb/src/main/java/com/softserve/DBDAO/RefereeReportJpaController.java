/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.RefereeReport;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class RefereeReportJpaController implements Serializable {

    public RefereeReportJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EntityManager em, RefereeReport refereeReport) throws RollbackFailureException, Exception {

        Application applicationID = refereeReport.getApplicationID();
        if (applicationID != null) {
            applicationID = em.getReference(applicationID.getClass(), applicationID.getApplicationID());
            refereeReport.setApplicationID(applicationID);
        }
        Person referee = refereeReport.getReferee();
        if (referee != null) {
            referee = em.getReference(referee.getClass(), referee.getSystemID());
            refereeReport.setReferee(referee);
        }
        em.persist(refereeReport);
        if (applicationID != null) {
            applicationID.getRefereeReportList().add(refereeReport);
            applicationID = em.merge(applicationID);
        }
        if (referee != null) {
            referee.getRefereeReportList().add(refereeReport);
            referee = em.merge(referee);
        }
            
    }

    public void edit(EntityManager em, RefereeReport refereeReport) throws NonexistentEntityException, RollbackFailureException, Exception {
        
        Long id = refereeReport.getReportID();
        if (findRefereeReport(id) == null) {
            throw new NonexistentEntityException("The refereeReport with id " + id + " no longer exists.");
        }
        
        RefereeReport persistentRefereeReport = em.find(RefereeReport.class, refereeReport.getReportID());
        Application applicationIDOld = persistentRefereeReport.getApplicationID();
        Application applicationIDNew = refereeReport.getApplicationID();
        Person refereeOld = persistentRefereeReport.getReferee();
        Person refereeNew = refereeReport.getReferee();
        if (applicationIDNew != null) {
            applicationIDNew = em.getReference(applicationIDNew.getClass(), applicationIDNew.getApplicationID());
            refereeReport.setApplicationID(applicationIDNew);
        }
        if (refereeNew != null) {
            refereeNew = em.getReference(refereeNew.getClass(), refereeNew.getSystemID());
            refereeReport.setReferee(refereeNew);
        }
        refereeReport = em.merge(refereeReport);
        if (applicationIDOld != null && !applicationIDOld.equals(applicationIDNew)) {
            applicationIDOld.getRefereeReportList().remove(refereeReport);
            applicationIDOld = em.merge(applicationIDOld);
        }
        if (applicationIDNew != null && !applicationIDNew.equals(applicationIDOld)) {
            applicationIDNew.getRefereeReportList().add(refereeReport);
            applicationIDNew = em.merge(applicationIDNew);
        }
        if (refereeOld != null && !refereeOld.equals(refereeNew)) {
            refereeOld.getRefereeReportList().remove(refereeReport);
            refereeOld = em.merge(refereeOld);
        }
        if (refereeNew != null && !refereeNew.equals(refereeOld)) {
            refereeNew.getRefereeReportList().add(refereeReport);
            refereeNew = em.merge(refereeNew);
        }
            
        
            
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        
        RefereeReport refereeReport;
        try {
            refereeReport = em.getReference(RefereeReport.class, id);
            refereeReport.getReportID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The refereeReport with id " + id + " no longer exists.", enfe);
        }
        Application applicationID = refereeReport.getApplicationID();
        if (applicationID != null) {
            applicationID.getRefereeReportList().remove(refereeReport);
            applicationID = em.merge(applicationID);
        }
        Person referee = refereeReport.getReferee();
        if (referee != null) {
            referee.getRefereeReportList().remove(refereeReport);
            referee = em.merge(referee);
        }
        em.remove(refereeReport);
            
    }

    public List<RefereeReport> findRefereeReportEntities() {
        return findRefereeReportEntities(true, -1, -1);
    }

    public List<RefereeReport> findRefereeReportEntities(int maxResults, int firstResult) {
        return findRefereeReportEntities(false, maxResults, firstResult);
    }

    private List<RefereeReport> findRefereeReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RefereeReport.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public RefereeReport findRefereeReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RefereeReport.class, id);
        } finally {
            em.close();
        }
    }

    public int getRefereeReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RefereeReport> rt = cq.from(RefereeReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
