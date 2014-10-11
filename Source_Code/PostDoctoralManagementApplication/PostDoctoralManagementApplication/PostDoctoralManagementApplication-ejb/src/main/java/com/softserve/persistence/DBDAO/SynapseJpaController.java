/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.persistence.DBEntities.NeuralNetwork;
import com.softserve.persistence.DBEntities.Neuron;
import com.softserve.persistence.DBEntities.Synapse;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class SynapseJpaController implements Serializable {

    public SynapseJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    public void create(Synapse synapse) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), synapse);
    }

    public void create(EntityManager em, Synapse synapse) throws RollbackFailureException, Exception 
    {  
        em.persist(synapse);
    }
    
    public Synapse edit(Synapse synapse) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), synapse);
    }

    public Synapse edit(EntityManager em, Synapse synapse) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = synapse.getSynapseID();
        if (findSynapse(id) == null) {
            throw new NonexistentEntityException("The synapse with id " + id + " no longer exists.");
        }

        return em.merge(synapse); 
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {   
        Synapse synapse;
        try {
            synapse = em.getReference(Synapse.class, id);
            synapse.getSynapseID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The synapse with id " + id + " no longer exists.", enfe);
        }

        em.remove(synapse);            
    }

    public List<Synapse> findSynapseEntities() {
        return findSynapseEntities(true, -1, -1);
    }

    public List<Synapse> findSynapseEntities(int maxResults, int firstResult) {
        return findSynapseEntities(false, maxResults, firstResult);
    }

    private List<Synapse> findSynapseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Synapse.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
        }
    }

    public Synapse findSynapse(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Synapse.class, id);
        } finally {
        }
    }

    public int getSynapseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Synapse> rt = cq.from(Synapse.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
        }
    }
    
}
