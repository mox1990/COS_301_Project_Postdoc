/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.ForwardAndRewindReport;
import com.softserve.DBEntities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ForwardAndRewindReportJpaController implements Serializable {

    public ForwardAndRewindReportJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(ForwardAndRewindReport forwardAndRewindReport) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), forwardAndRewindReport);
    }

    public void create(EntityManager em, ForwardAndRewindReport forwardAndRewindReport) throws RollbackFailureException, Exception 
    {

        Application application = forwardAndRewindReport.getApplication();
        if (application != null) {
            application = em.getReference(application.getClass(), application.getApplicationID());
            forwardAndRewindReport.setApplication(application);
        }
        Person dris = forwardAndRewindReport.getDris();
        if (dris != null) {
            dris = em.getReference(dris.getClass(), dris.getSystemID());
            forwardAndRewindReport.setDris(dris);
        }
        em.persist(forwardAndRewindReport);
        if (application != null) {
            application.getForwardAndRewindReportList().add(forwardAndRewindReport);
            application = em.merge(application);
        }
        if (dris != null) {
            dris.getForwardAndRewindReportList().add(forwardAndRewindReport);
            dris = em.merge(dris);
        }

    }
    
    public void edit(ForwardAndRewindReport forwardAndRewindReport) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        edit(getEntityManager(), forwardAndRewindReport);
    }
    
    public void edit(EntityManager em, ForwardAndRewindReport forwardAndRewindReport) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        Long id = forwardAndRewindReport.getReportID();
        if (findForwardAndRewindReport(id) == null) {
            throw new NonexistentEntityException("The forwardAndRewindReport with id " + id + " no longer exists.");
        }
        ForwardAndRewindReport persistentForwardAndRewindReport = em.find(ForwardAndRewindReport.class, forwardAndRewindReport.getReportID());
        Application applicationOld = persistentForwardAndRewindReport.getApplication();
        Application applicationNew = forwardAndRewindReport.getApplication();
        Person drisOld = persistentForwardAndRewindReport.getDris();
        Person drisNew = forwardAndRewindReport.getDris();
        if (applicationNew != null) {
            applicationNew = em.getReference(applicationNew.getClass(), applicationNew.getApplicationID());
            forwardAndRewindReport.setApplication(applicationNew);
        }
        if (drisNew != null) {
            drisNew = em.getReference(drisNew.getClass(), drisNew.getSystemID());
            forwardAndRewindReport.setDris(drisNew);
        }
        forwardAndRewindReport = em.merge(forwardAndRewindReport);
        if (applicationOld != null && !applicationOld.equals(applicationNew)) {
            applicationOld.getForwardAndRewindReportList().remove(forwardAndRewindReport);
            applicationOld = em.merge(applicationOld);
        }
        if (applicationNew != null && !applicationNew.equals(applicationOld)) {
            applicationNew.getForwardAndRewindReportList().add(forwardAndRewindReport);
            applicationNew = em.merge(applicationNew);
        }
        if (drisOld != null && !drisOld.equals(drisNew)) {
            drisOld.getForwardAndRewindReportList().remove(forwardAndRewindReport);
            drisOld = em.merge(drisOld);
        }
        if (drisNew != null && !drisNew.equals(drisOld)) {
            drisNew.getForwardAndRewindReportList().add(forwardAndRewindReport);
            drisNew = em.merge(drisNew);
        }       

    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }
    
    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        ForwardAndRewindReport forwardAndRewindReport;
        try {
            forwardAndRewindReport = em.getReference(ForwardAndRewindReport.class, id);
            forwardAndRewindReport.getReportID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The forwardAndRewindReport with id " + id + " no longer exists.", enfe);
        }
        Application application = forwardAndRewindReport.getApplication();
        if (application != null) {
            application.getForwardAndRewindReportList().remove(forwardAndRewindReport);
            application = em.merge(application);
        }
        Person dris = forwardAndRewindReport.getDris();
        if (dris != null) {
            dris.getForwardAndRewindReportList().remove(forwardAndRewindReport);
            dris = em.merge(dris);
        }
        em.remove(forwardAndRewindReport);
           
    }

    public List<ForwardAndRewindReport> findForwardAndRewindReportEntities() {
        return findForwardAndRewindReportEntities(true, -1, -1);
    }

    public List<ForwardAndRewindReport> findForwardAndRewindReportEntities(int maxResults, int firstResult) {
        return findForwardAndRewindReportEntities(false, maxResults, firstResult);
    }

    private List<ForwardAndRewindReport> findForwardAndRewindReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ForwardAndRewindReport.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public ForwardAndRewindReport findForwardAndRewindReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ForwardAndRewindReport.class, id);
        } finally {
            
        }
    }

    public int getForwardAndRewindReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ForwardAndRewindReport> rt = cq.from(ForwardAndRewindReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
