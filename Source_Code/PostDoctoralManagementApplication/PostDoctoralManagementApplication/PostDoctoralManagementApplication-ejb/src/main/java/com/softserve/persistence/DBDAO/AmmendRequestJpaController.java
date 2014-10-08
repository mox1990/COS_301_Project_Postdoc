/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.AmmendRequest;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Person;
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
    
    public void create(EntityManager em, AmmendRequest ammendRequest) throws RollbackFailureException, Exception 
    {
        em.persist(ammendRequest); 
    }
    
    public AmmendRequest edit( AmmendRequest ammendRequest) throws NonexistentEntityException, RollbackFailureException, Exception 
    { 
        return edit(getEntityManager(), ammendRequest);
    }

    public AmmendRequest edit(EntityManager em, AmmendRequest ammendRequest) throws NonexistentEntityException, RollbackFailureException, Exception 
    {        
        Long id = ammendRequest.getRequestID();
        if (findAmmendRequest(id) == null) {
            throw new NonexistentEntityException("The ammendRequest with id " + id + " no longer exists.");
        }        
        
        return em.merge(ammendRequest);

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
