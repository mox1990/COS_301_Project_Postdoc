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
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
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
public class SecurityRoleJpaController implements Serializable {

    public SecurityRoleJpaController(EntityManager emm) {

        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(SecurityRole securityRole) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), securityRole);
    }

    public void create(EntityManager em, SecurityRole securityRole) throws RollbackFailureException, Exception 
    {
        if (securityRole.getPersonList() == null) {
            securityRole.setPersonList(new ArrayList<Person>());
        }
        
        em.persist(securityRole);            
    }
    
    public SecurityRole edit(SecurityRole securityRole) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), securityRole);
    }

    public SecurityRole edit(EntityManager em, SecurityRole securityRole) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = securityRole.getRoleID();
        if (findSecurityRole(id) == null) {
            throw new NonexistentEntityException("The securityRole with id " + id + " no longer exists.");
        }

        return em.merge(securityRole);
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        SecurityRole securityRole;
        try {
            securityRole = em.getReference(SecurityRole.class, id);
            securityRole.getRoleID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The securityRole with id " + id + " no longer exists.", enfe);
        }

        em.remove(securityRole);

    }

    public List<SecurityRole> findSecurityRoleEntities() {
        return findSecurityRoleEntities(true, -1, -1);
    }

    public List<SecurityRole> findSecurityRoleEntities(int maxResults, int firstResult) {
        return findSecurityRoleEntities(false, maxResults, firstResult);
    }

    private List<SecurityRole> findSecurityRoleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SecurityRole.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public SecurityRole findSecurityRole(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SecurityRole.class, id);
        } finally {
           
        }
    }

    public int getSecurityRoleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SecurityRole> rt = cq.from(SecurityRole.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
