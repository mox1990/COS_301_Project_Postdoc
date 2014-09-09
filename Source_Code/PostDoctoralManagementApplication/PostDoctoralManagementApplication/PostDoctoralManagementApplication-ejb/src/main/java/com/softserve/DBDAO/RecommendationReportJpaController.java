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
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.RecommendationReport;
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
public class RecommendationReportJpaController implements Serializable {

    public RecommendationReportJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(RecommendationReport recommendationReport) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        create(getEntityManager(), recommendationReport);
    }

    public void create(EntityManager em, RecommendationReport recommendationReport) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        List<String> illegalOrphanMessages = null;
        Application applicationOrphanCheck = recommendationReport.getApplication();
        if (applicationOrphanCheck != null) {
            RecommendationReport oldRecommendationReportOfApplication = applicationOrphanCheck.getRecommendationReport();
            if (oldRecommendationReportOfApplication != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Application " + applicationOrphanCheck + " already has an item of type RecommendationReport whose application column cannot be null. Please make another selection for the application field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }

        Application application = recommendationReport.getApplication();
        if (application != null) {
            application = em.getReference(application.getClass(), application.getApplicationID());
            recommendationReport.setApplication(application);
        }
        Person hod = recommendationReport.getHod();
        if (hod != null) {
            hod = em.getReference(hod.getClass(), hod.getSystemID());
            recommendationReport.setHod(hod);
        }
        em.persist(recommendationReport);
        if (application != null) {
            application.setRecommendationReport(recommendationReport);
            application = em.merge(application);
        }
        if (hod != null) {
            hod.getRecommendationReportList().add(recommendationReport);
            hod = em.merge(hod);
        }

    }
    
    public void edit(RecommendationReport recommendationReport) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        edit(getEntityManager(), recommendationReport);
    }

    public void edit(EntityManager em, RecommendationReport recommendationReport) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = recommendationReport.getReportID();
        if (findRecommendationReport(id) == null) {
            throw new NonexistentEntityException("The recommendationReport with id " + id + " no longer exists.");
        }
        
        RecommendationReport persistentRecommendationReport = em.find(RecommendationReport.class, recommendationReport.getReportID());
        Application applicationOld = persistentRecommendationReport.getApplication();
        Application applicationNew = recommendationReport.getApplication();
        Person hodOld = persistentRecommendationReport.getHod();
        Person hodNew = recommendationReport.getHod();
        List<String> illegalOrphanMessages = null;
        if (applicationNew != null && !applicationNew.equals(applicationOld)) {
            RecommendationReport oldRecommendationReportOfApplication = applicationNew.getRecommendationReport();
            if (oldRecommendationReportOfApplication != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Application " + applicationNew + " already has an item of type RecommendationReport whose application column cannot be null. Please make another selection for the application field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        if (applicationNew != null) {
            applicationNew = em.getReference(applicationNew.getClass(), applicationNew.getApplicationID());
            recommendationReport.setApplication(applicationNew);
        }
        if (hodNew != null) {
            hodNew = em.getReference(hodNew.getClass(), hodNew.getSystemID());
            recommendationReport.setHod(hodNew);
        }
        recommendationReport = em.merge(recommendationReport);
        if (applicationOld != null && !applicationOld.equals(applicationNew)) {
            applicationOld.setRecommendationReport(null);
            applicationOld = em.merge(applicationOld);
        }
        if (applicationNew != null && !applicationNew.equals(applicationOld)) {
            applicationNew.setRecommendationReport(recommendationReport);
            applicationNew = em.merge(applicationNew);
        }
        if (hodOld != null && !hodOld.equals(hodNew)) {
            hodOld.getRecommendationReportList().remove(recommendationReport);
            hodOld = em.merge(hodOld);
        }
        if (hodNew != null && !hodNew.equals(hodOld)) {
            hodNew.getRecommendationReportList().add(recommendationReport);
            hodNew = em.merge(hodNew);
        }

                

    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        RecommendationReport recommendationReport;
        try {
            recommendationReport = em.getReference(RecommendationReport.class, id);
            recommendationReport.getReportID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The recommendationReport with id " + id + " no longer exists.", enfe);
        }
        Application application = recommendationReport.getApplication();
        if (application != null) {
            application.setRecommendationReport(null);
            application = em.merge(application);
        }
        Person hod = recommendationReport.getHod();
        if (hod != null) {
            hod.getRecommendationReportList().remove(recommendationReport);
            hod = em.merge(hod);
        }
        em.remove(recommendationReport);
            
    }

    public List<RecommendationReport> findRecommendationReportEntities() {
        return findRecommendationReportEntities(true, -1, -1);
    }

    public List<RecommendationReport> findRecommendationReportEntities(int maxResults, int firstResult) {
        return findRecommendationReportEntities(false, maxResults, firstResult);
    }

    private List<RecommendationReport> findRecommendationReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RecommendationReport.class));
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

    public RecommendationReport findRecommendationReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RecommendationReport.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecommendationReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RecommendationReport> rt = cq.from(RecommendationReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
