/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.ApplicationReviewRequest;
import com.softserve.persistence.DBEntities.ApplicationReviewRequestPK;
import com.softserve.persistence.DBEntities.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ApplicationReviewRequestJpaController implements Serializable {

    public ApplicationReviewRequestJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(ApplicationReviewRequest applicationReviewRequest) throws PreexistingEntityException, RollbackFailureException, Exception 
    {
        create(getEntityManager(), applicationReviewRequest);
    }

    public void create(EntityManager em, ApplicationReviewRequest applicationReviewRequest) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (applicationReviewRequest.getApplicationReviewRequestPK() == null) {
            applicationReviewRequest.setApplicationReviewRequestPK(new ApplicationReviewRequestPK());
        }
        applicationReviewRequest.getApplicationReviewRequestPK().setPerson(applicationReviewRequest.getPerson1().getSystemID());
        applicationReviewRequest.getApplicationReviewRequestPK().setApplication(applicationReviewRequest.getApplication1().getApplicationID());

        
        em.persist(applicationReviewRequest);

    }
    
    public ApplicationReviewRequest edit(ApplicationReviewRequest applicationReviewRequest) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), applicationReviewRequest);
    }
    
    public ApplicationReviewRequest edit(EntityManager em, ApplicationReviewRequest applicationReviewRequest) throws NonexistentEntityException, RollbackFailureException, Exception {
        ApplicationReviewRequestPK id = applicationReviewRequest.getApplicationReviewRequestPK();
        if (findApplicationReviewRequest(id) == null) {
            throw new NonexistentEntityException("The applicationReviewRequest with id " + id + " no longer exists.");
        }
        
        applicationReviewRequest.getApplicationReviewRequestPK().setPerson(applicationReviewRequest.getPerson1().getSystemID());
        applicationReviewRequest.getApplicationReviewRequestPK().setApplication(applicationReviewRequest.getApplication1().getApplicationID());

        
        return em.merge(applicationReviewRequest);
        

    }
    
    public void destroy(ApplicationReviewRequestPK id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, ApplicationReviewRequestPK id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        ApplicationReviewRequest applicationReviewRequest;
        try {
            applicationReviewRequest = em.getReference(ApplicationReviewRequest.class, id);
            applicationReviewRequest.getApplicationReviewRequestPK();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The applicationReviewRequest with id " + id + " no longer exists.", enfe);
        }

        em.remove(applicationReviewRequest);

    }

    public List<ApplicationReviewRequest> findApplicationReviewRequestEntities() {
        return findApplicationReviewRequestEntities(true, -1, -1);
    }

    public List<ApplicationReviewRequest> findApplicationReviewRequestEntities(int maxResults, int firstResult) {
        return findApplicationReviewRequestEntities(false, maxResults, firstResult);
    }

    private List<ApplicationReviewRequest> findApplicationReviewRequestEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ApplicationReviewRequest.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public ApplicationReviewRequest findApplicationReviewRequest(ApplicationReviewRequestPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ApplicationReviewRequest.class, id);
        } finally {
            
        }
    }

    public int getApplicationReviewRequestCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ApplicationReviewRequest> rt = cq.from(ApplicationReviewRequest.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
    public List<Application> findAllApplicationsRequestForPersonAs(Person person, String type)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT r.application1 FROM ApplicationReviewRequest r WHERE r.person1 = :per AND r.applicationReviewRequestPK.type = :type", Application.class).setParameter("per", person).setParameter("type", type);
        
        List<Application> applications = q.getResultList();
        return (applications != null)?applications:new ArrayList<Application>();
    }
    
    public List<Person> findAllPeopleWhoHaveBeenRequestForApplicationAs(Application application, String type)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Person> q = em.createQuery("SELECT r.person1 FROM ApplicationReviewRequest r WHERE r.application1 = :app AND r.applicationReviewRequestPK.type = :type", Person.class).setParameter("app", application).setParameter("type", type);
       
        List<Person> people = q.getResultList();
        return (people != null)?people:new ArrayList<Person>();
    }
    
    public List<ApplicationReviewRequest> findAllRequestsThatHaveBeenRequestForApplicationAs(Application application, String type)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<ApplicationReviewRequest> q = em.createQuery("SELECT r FROM ApplicationReviewRequest r WHERE r.application1 = :app AND r.applicationReviewRequestPK.type = :type", ApplicationReviewRequest.class).setParameter("app", application).setParameter("type", type);
       
        List<ApplicationReviewRequest> applicationReviewRequests = q.getResultList();
        return (applicationReviewRequests != null)?applicationReviewRequests:new ArrayList<ApplicationReviewRequest>();
    }
    
}
