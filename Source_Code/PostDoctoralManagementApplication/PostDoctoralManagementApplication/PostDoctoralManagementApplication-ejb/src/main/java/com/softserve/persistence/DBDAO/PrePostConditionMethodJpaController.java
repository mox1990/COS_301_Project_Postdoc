/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.PrePostConditionMethod;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class PrePostConditionMethodJpaController implements Serializable {

    public PrePostConditionMethodJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    public void create(PrePostConditionMethod prePostConditionMethod) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), prePostConditionMethod);
    }

    public void create(EntityManager em, PrePostConditionMethod prePostConditionMethod) throws RollbackFailureException, Exception 
    {  
        em.persist(prePostConditionMethod);
    }
    
    public PrePostConditionMethod edit(PrePostConditionMethod prePostConditionMethod) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), prePostConditionMethod);
    }

    public PrePostConditionMethod edit(EntityManager em, PrePostConditionMethod prePostConditionMethod) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = prePostConditionMethod.getPrePostConditionMethodID();
        if (findPrePostConditionMethod(id) == null) {
            throw new NonexistentEntityException("The prePostConditionMethod with id " + id + " no longer exists.");
        }

        return em.merge(prePostConditionMethod); 
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {   
        PrePostConditionMethod prePostConditionMethod;
        try {
            prePostConditionMethod = em.getReference(PrePostConditionMethod.class, id);
            prePostConditionMethod.getPrePostConditionMethodID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The prePostConditionMethod with id " + id + " no longer exists.", enfe);
        }

        em.remove(prePostConditionMethod);            
    }

    public List<PrePostConditionMethod> findPrePostConditionMethodEntities() {
        return findPrePostConditionMethodEntities(true, -1, -1);
    }

    public List<PrePostConditionMethod> findPrePostConditionMethodEntities(int maxResults, int firstResult) {
        return findPrePostConditionMethodEntities(false, maxResults, firstResult);
    }

    private List<PrePostConditionMethod> findPrePostConditionMethodEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrePostConditionMethod.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {

        }
    }

    public PrePostConditionMethod findPrePostConditionMethod(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrePostConditionMethod.class, id);
        } finally {

        }
    }

    public int getPrePostConditionMethodCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrePostConditionMethod> rt = cq.from(PrePostConditionMethod.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {

        }
    }
    
    public PrePostConditionMethod findPrePostConditionByClassAndMethodName(String methodName, String className, List<String> parameters) throws Exception
    {
        EntityManager em = getEntityManager();
        
        PrePostConditionMethod postConditionMethod = new PrePostConditionMethod();

        postConditionMethod.setMethodParametersEncode(parameters);

        System.out.println(postConditionMethod.getMethodParameters());
        List<PrePostConditionMethod> prePostConditionMethods = em.createQuery("SELECT p FROM PrePostConditionMethod p WHERE p.methodClassName = :className AND p.methodName = :methodName AND p.methodParameters = :methodParameters", PrePostConditionMethod.class).setParameter("className", className).setParameter("methodName", methodName).setParameter("methodParameters", postConditionMethod.getMethodParameters()).getResultList();
        if( prePostConditionMethods == null || prePostConditionMethods.isEmpty())
        {
            return null;
        }
        else
        {
            return prePostConditionMethods.get(0);
        }
    }
    
}
