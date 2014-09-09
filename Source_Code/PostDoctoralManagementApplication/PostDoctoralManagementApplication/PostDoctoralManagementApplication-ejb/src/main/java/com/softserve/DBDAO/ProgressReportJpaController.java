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
import com.softserve.DBEntities.ProgressReport;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ProgressReportJpaController implements Serializable {

    public ProgressReportJpaController(EntityManagerFactory emf) {

        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EntityManager em, ProgressReport progressReport) throws RollbackFailureException, Exception {

        Application application = progressReport.getApplication();
        if (application != null) {
            application = em.getReference(application.getClass(), application.getApplicationID());
            progressReport.setApplication(application);
        }
        em.persist(progressReport);
        if (application != null) {
            application.getProgressReportList().add(progressReport);
            application = em.merge(application);
        }

    }

    public void edit(EntityManager em, ProgressReport progressReport) throws NonexistentEntityException, RollbackFailureException, Exception {

        Long id = progressReport.getReportID();
        if (findProgressReport(id) == null) {
            throw new NonexistentEntityException("The progressReport with id " + id + " no longer exists.");
        }
        
        ProgressReport persistentProgressReport = em.find(ProgressReport.class, progressReport.getReportID());
        Application applicationOld = persistentProgressReport.getApplication();
        Application applicationNew = progressReport.getApplication();
        if (applicationNew != null) {
            applicationNew = em.getReference(applicationNew.getClass(), applicationNew.getApplicationID());
            progressReport.setApplication(applicationNew);
        }
        progressReport = em.merge(progressReport);
        if (applicationOld != null && !applicationOld.equals(applicationNew)) {
            applicationOld.getProgressReportList().remove(progressReport);
            applicationOld = em.merge(applicationOld);
        }
        if (applicationNew != null && !applicationNew.equals(applicationOld)) {
            applicationNew.getProgressReportList().add(progressReport);
            applicationNew = em.merge(applicationNew);
        }
           
                

    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception {

        ProgressReport progressReport;
        try {
            progressReport = em.getReference(ProgressReport.class, id);
            progressReport.getReportID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The progressReport with id " + id + " no longer exists.", enfe);
        }
        Application application = progressReport.getApplication();
        if (application != null) {
            application.getProgressReportList().remove(progressReport);
            application = em.merge(application);
        }
        em.remove(progressReport);

    }

    public List<ProgressReport> findProgressReportEntities() {
        return findProgressReportEntities(true, -1, -1);
    }

    public List<ProgressReport> findProgressReportEntities(int maxResults, int firstResult) {
        return findProgressReportEntities(false, maxResults, firstResult);
    }

    private List<ProgressReport> findProgressReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProgressReport.class));
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

    public ProgressReport findProgressReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProgressReport.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgressReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProgressReport> rt = cq.from(ProgressReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
