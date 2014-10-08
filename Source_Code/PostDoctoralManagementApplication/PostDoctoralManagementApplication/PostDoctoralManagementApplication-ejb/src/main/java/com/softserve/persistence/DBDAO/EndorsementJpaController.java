/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Endorsement;
import com.softserve.persistence.DBEntities.Person;
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
public class EndorsementJpaController implements Serializable {

    public EndorsementJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create( Endorsement endorsement) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        create(getEntityManager(), endorsement);
    }
    
    public void create(EntityManager em, Endorsement endorsement) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        List<String> illegalOrphanMessages = null;
        Application applicationOrphanCheck = endorsement.getApplication();
        if (applicationOrphanCheck != null) {
            Endorsement oldEndorsementOfApplication = applicationOrphanCheck.getEndorsement();
            if (oldEndorsementOfApplication != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Application " + applicationOrphanCheck + " already has an item of type Endorsement whose application column cannot be null. Please make another selection for the application field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
 
        em.persist(endorsement);           
    }
    
    public Endorsement edit(Endorsement endorsement) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), endorsement);
    }

    public Endorsement edit(EntityManager em, Endorsement endorsement) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = endorsement.getEndorsementID();
        if (findEndorsement(id) == null) {
            throw new NonexistentEntityException("The endorsement with id " + id + " no longer exists.");
        }
                
        Endorsement persistentEndorsement = em.find(Endorsement.class, endorsement.getEndorsementID());
        Application applicationOld = persistentEndorsement.getApplication();
        Application applicationNew = endorsement.getApplication();

        List<String> illegalOrphanMessages = null;
        if (applicationNew != null && !applicationNew.equals(applicationOld)) {
            Endorsement oldEndorsementOfApplication = applicationNew.getEndorsement();
            if (oldEndorsementOfApplication != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Application " + applicationNew + " already has an item of type Endorsement whose application column cannot be null. Please make another selection for the application field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }

        return em.merge(endorsement);
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Endorsement endorsement;
        try {
            endorsement = em.getReference(Endorsement.class, id);
            endorsement.getEndorsementID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The endorsement with id " + id + " no longer exists.", enfe);
        }

        em.remove(endorsement);
           
    }

    public List<Endorsement> findEndorsementEntities() {
        return findEndorsementEntities(true, -1, -1);
    }

    public List<Endorsement> findEndorsementEntities(int maxResults, int firstResult) {
        return findEndorsementEntities(false, maxResults, firstResult);
    }

    private List<Endorsement> findEndorsementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endorsement.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public Endorsement findEndorsement(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endorsement.class, id);
        } finally {
            
        }
    }

    public int getEndorsementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Endorsement> rt = cq.from(Endorsement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
