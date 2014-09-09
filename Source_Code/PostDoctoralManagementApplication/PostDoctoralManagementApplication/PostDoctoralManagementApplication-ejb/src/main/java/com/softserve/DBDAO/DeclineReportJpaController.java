/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.DeclineReport;
import com.softserve.DBEntities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class DeclineReportJpaController implements Serializable {

    public DeclineReportJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EntityManager em, DeclineReport declineReport) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Application applicationOrphanCheck = declineReport.getApplication();
        if (applicationOrphanCheck != null) {
            DeclineReport oldDeclineReportOfApplication = applicationOrphanCheck.getDeclineReport();
            if (oldDeclineReportOfApplication != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Application " + applicationOrphanCheck + " already has an item of type DeclineReport whose application column cannot be null. Please make another selection for the application field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }

        Application application = declineReport.getApplication();
        if (application != null) {
            application = em.getReference(application.getClass(), application.getApplicationID());
            declineReport.setApplication(application);
        }
        Person creator = declineReport.getCreator();
        if (creator != null) {
            creator = em.getReference(creator.getClass(), creator.getSystemID());
            declineReport.setCreator(creator);
        }
        em.persist(declineReport);
        if (application != null) {
            application.setDeclineReport(declineReport);
            application = em.merge(application);
        }
        if (creator != null) {
            creator.getDeclineReportList().add(declineReport);
            creator = em.merge(creator);
        }
            
    }

    public void edit(EntityManager em, DeclineReport declineReport) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        
        Long id = declineReport.getReportID();
        if (findDeclineReport(id) == null) {
            throw new NonexistentEntityException("The declineReport with id " + id + " no longer exists.");
        }
        
        DeclineReport persistentDeclineReport = em.find(DeclineReport.class, declineReport.getReportID());
        Application applicationOld = persistentDeclineReport.getApplication();
        Application applicationNew = declineReport.getApplication();
        Person creatorOld = persistentDeclineReport.getCreator();
        Person creatorNew = declineReport.getCreator();
        List<String> illegalOrphanMessages = null;
        if (applicationNew != null && !applicationNew.equals(applicationOld)) {
            DeclineReport oldDeclineReportOfApplication = applicationNew.getDeclineReport();
            if (oldDeclineReportOfApplication != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Application " + applicationNew + " already has an item of type DeclineReport whose application column cannot be null. Please make another selection for the application field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        if (applicationNew != null) {
            applicationNew = em.getReference(applicationNew.getClass(), applicationNew.getApplicationID());
            declineReport.setApplication(applicationNew);
        }
        if (creatorNew != null) {
            creatorNew = em.getReference(creatorNew.getClass(), creatorNew.getSystemID());
            declineReport.setCreator(creatorNew);
        }
        declineReport = em.merge(declineReport);
        if (applicationOld != null && !applicationOld.equals(applicationNew)) {
            applicationOld.setDeclineReport(null);
            applicationOld = em.merge(applicationOld);
        }
        if (applicationNew != null && !applicationNew.equals(applicationOld)) {
            applicationNew.setDeclineReport(declineReport);
            applicationNew = em.merge(applicationNew);
        }
        if (creatorOld != null && !creatorOld.equals(creatorNew)) {
            creatorOld.getDeclineReportList().remove(declineReport);
            creatorOld = em.merge(creatorOld);
        }
        if (creatorNew != null && !creatorNew.equals(creatorOld)) {
            creatorNew.getDeclineReportList().add(declineReport);
            creatorNew = em.merge(creatorNew);
        }
            
                

    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        DeclineReport declineReport;
        try {
            declineReport = em.getReference(DeclineReport.class, id);
            declineReport.getReportID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The declineReport with id " + id + " no longer exists.", enfe);
        }
        Application application = declineReport.getApplication();
        if (application != null) {
            application.setDeclineReport(null);
            application = em.merge(application);
        }
        Person creator = declineReport.getCreator();
        if (creator != null) {
            creator.getDeclineReportList().remove(declineReport);
            creator = em.merge(creator);
        }
        em.remove(declineReport);
           
    }

    public List<DeclineReport> findDeclineReportEntities() {
        return findDeclineReportEntities(true, -1, -1);
    }

    public List<DeclineReport> findDeclineReportEntities(int maxResults, int firstResult) {
        return findDeclineReportEntities(false, maxResults, firstResult);
    }

    private List<DeclineReport> findDeclineReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DeclineReport.class));
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

    public DeclineReport findDeclineReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DeclineReport.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeclineReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DeclineReport> rt = cq.from(DeclineReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
