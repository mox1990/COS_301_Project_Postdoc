/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.NeuralNetwork;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.persistence.DBEntities.Neuron;
import java.util.ArrayList;
import java.util.List;
import com.softserve.persistence.DBEntities.Synapse;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NeuralNetworkJpaController implements Serializable {

    public NeuralNetworkJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }

    public void create(NeuralNetwork neuralNetwork) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), neuralNetwork);
    }

    public void create(EntityManager em, NeuralNetwork neuralNetwork) throws RollbackFailureException, Exception 
    {  
        em.persist(neuralNetwork);
    }
    
    public NeuralNetwork edit(NeuralNetwork neuralNetwork) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), neuralNetwork);
    }

    public NeuralNetwork edit(EntityManager em, NeuralNetwork neuralNetwork) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = neuralNetwork.getNeuralnetworkID();
        if (findNeuralNetwork(id) == null) {
            throw new NonexistentEntityException("The neuralNetwork with id " + id + " no longer exists.");
        }

        return em.merge(neuralNetwork); 
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {   
        NeuralNetwork neuralNetwork;
        try {
            neuralNetwork = em.getReference(NeuralNetwork.class, id);
            neuralNetwork.getNeuralnetworkID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The neuralNetwork with id " + id + " no longer exists.", enfe);
        }

        em.remove(neuralNetwork);            
    }

    public List<NeuralNetwork> findNeuralNetworkEntities() {
        return findNeuralNetworkEntities(true, -1, -1);
    }

    public List<NeuralNetwork> findNeuralNetworkEntities(int maxResults, int firstResult) {
        return findNeuralNetworkEntities(false, maxResults, firstResult);
    }

    private List<NeuralNetwork> findNeuralNetworkEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NeuralNetwork.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public NeuralNetwork findNeuralNetwork(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NeuralNetwork.class, id);
        } finally {

        }
    }

    public int getNeuralNetworkCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NeuralNetwork> rt = cq.from(NeuralNetwork.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {

        }
    }
    
    public NeuralNetwork findTheDefaultNeuralNetwork()
    {
        EntityManager em = getEntityManager();
        List<NeuralNetwork> neuralNetworks = em.createQuery("SELECT n FROM NeuralNetwork n WHERE n.defaultNetwork == TRUE").getResultList();
        
        if(neuralNetworks == null || neuralNetworks.isEmpty())
        {
            return null;
        }
        else
        {
            return neuralNetworks.get(0);
        }
    }        
    
}
