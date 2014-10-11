/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.IllegalOrphanException;
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
public class NeuronJpaController implements Serializable {

    public NeuronJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }

    public void create(Neuron neuron) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), neuron);
    }

    public void create(EntityManager em, Neuron neuron) throws RollbackFailureException, Exception 
    {  
        em.persist(neuron);
    }
    
    public Neuron edit(Neuron neuron) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), neuron);
    }

    public Neuron edit(EntityManager em, Neuron neuron) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = neuron.getNeuronID();
        if (findNeuron(id) == null) {
            throw new NonexistentEntityException("The neuron with id " + id + " no longer exists.");
        }

        return em.merge(neuron); 
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {   
        Neuron neuron;
        try {
            neuron = em.getReference(Neuron.class, id);
            neuron.getNeuronID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The neuron with id " + id + " no longer exists.", enfe);
        }

        em.remove(neuron);            
    }

    public List<Neuron> findNeuronEntities() {
        return findNeuronEntities(true, -1, -1);
    }

    public List<Neuron> findNeuronEntities(int maxResults, int firstResult) {
        return findNeuronEntities(false, maxResults, firstResult);
    }

    private List<Neuron> findNeuronEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Neuron.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
        }
    }

    public Neuron findNeuron(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Neuron.class, id);
        } finally {
        }
    }

    public int getNeuronCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Neuron> rt = cq.from(Neuron.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
        }
    }
    
}
