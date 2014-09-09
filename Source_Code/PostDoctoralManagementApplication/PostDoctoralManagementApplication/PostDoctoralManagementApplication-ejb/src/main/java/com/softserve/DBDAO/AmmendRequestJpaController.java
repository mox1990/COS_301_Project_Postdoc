/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.AmmendRequest;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Application;
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
public class AmmendRequestJpaController implements Serializable {

    public AmmendRequestJpaController(EntityManager em) {
        this.emm = em;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(AmmendRequest ammendRequest) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), ammendRequest);
    }
    
    public void create(EntityManager em, AmmendRequest ammendRequest) throws RollbackFailureException, Exception {


        Application application = ammendRequest.getApplication();
        if (application != null) {
            application = em.getReference(application.getClass(), application.getApplicationID());
            ammendRequest.setApplication(application);
        }
        Person creator = ammendRequest.getCreator();
        if (creator != null) {
            creator = em.getReference(creator.getClass(), creator.getSystemID());
            ammendRequest.setCreator(creator);
        }
        em.persist(ammendRequest);
        if (application != null) {
            application.getAmmendRequestList().add(ammendRequest);
            application = em.merge(application);
        }
        if (creator != null) {
            creator.getAmmendRequestList().add(ammendRequest);
            creator = em.merge(creator);
        }
  
    }
    
    public void edit( AmmendRequest ammendRequest) throws NonexistentEntityException, RollbackFailureException, Exception 
    { 
        edit(getEntityManager(), ammendRequest);
    }

    public void edit(EntityManager em, AmmendRequest ammendRequest) throws NonexistentEntityException, RollbackFailureException, Exception 
    {        
        Long id = ammendRequest.getRequestID();
        if (findAmmendRequest(id) == null) {
            throw new NonexistentEntityException("The ammendRequest with id " + id + " no longer exists.");
        }
        
        AmmendRequest persistentAmmendRequest = em.find(AmmendRequest.class, ammendRequest.getRequestID());
        Application applicationOld = persistentAmmendRequest.getApplication();
        Application applicationNew = ammendRequest.getApplication();
        Person creatorOld = persistentAmmendRequest.getCreator();
        Person creatorNew = ammendRequest.getCreator();
        if (applicationNew != null) {
            applicationNew = em.getReference(applicationNew.getClass(), applicationNew.getApplicationID());
            ammendRequest.setApplication(applicationNew);
        }
        if (creatorNew != null) {
            creatorNew = em.getReference(creatorNew.getClass(), creatorNew.getSystemID());
            ammendRequest.setCreator(creatorNew);
        }
        
        ammendRequest = em.merge(ammendRequest);
        
        if (applicationOld != null && !applicationOld.equals(applicationNew)) {
            applicationOld.getAmmendRequestList().remove(ammendRequest);
            applicationOld = em.merge(applicationOld);
        }
        if (applicationNew != null && !applicationNew.equals(applicationOld)) {
            applicationNew.getAmmendRequestList().add(ammendRequest);
            applicationNew = em.merge(applicationNew);
        }
        if (creatorOld != null && !creatorOld.equals(creatorNew)) {
            creatorOld.getAmmendRequestList().remove(ammendRequest);
            creatorOld = em.merge(creatorOld);
        }
        if (creatorNew != null && !creatorNew.equals(creatorOld)) {
            creatorNew.getAmmendRequestList().add(ammendRequest);
            creatorNew = em.merge(creatorNew);
        }  

    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        AmmendRequest ammendRequest;
        try {
            ammendRequest = em.getReference(AmmendRequest.class, id);
            ammendRequest.getRequestID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The ammendRequest with id " + id + " no longer exists.", enfe);
        }
        
        Application application = ammendRequest.getApplication();
        
        if (application != null) {
            application.getAmmendRequestList().remove(ammendRequest);
            application = em.merge(application);
        }
        Person creator = ammendRequest.getCreator();
        if (creator != null) {
            creator.getAmmendRequestList().remove(ammendRequest);
            creator = em.merge(creator);
        }
        em.remove(ammendRequest);

    }

    public List<AmmendRequest> findAmmendRequestEntities() {
        return findAmmendRequestEntities(true, -1, -1);
    }

    public List<AmmendRequest> findAmmendRequestEntities(int maxResults, int firstResult) {
        return findAmmendRequestEntities(false, maxResults, firstResult);
    }

    private List<AmmendRequest> findAmmendRequestEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AmmendRequest.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public AmmendRequest findAmmendRequest(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AmmendRequest.class, id);
        } finally {
            
        }
    }

    public int getAmmendRequestCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AmmendRequest> rt = cq.from(AmmendRequest.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
