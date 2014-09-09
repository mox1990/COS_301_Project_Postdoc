/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
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

        List<Person> attachedPersonList = new ArrayList<Person>();
        for (Person personListPersonToAttach : securityRole.getPersonList()) {
            personListPersonToAttach = em.getReference(personListPersonToAttach.getClass(), personListPersonToAttach.getSystemID());
            attachedPersonList.add(personListPersonToAttach);
        }
        securityRole.setPersonList(attachedPersonList);
        em.persist(securityRole);
        for (Person personListPerson : securityRole.getPersonList()) {
            personListPerson.getSecurityRoleList().add(securityRole);
            personListPerson = em.merge(personListPerson);
        }
            
    }
    
    public void edit(SecurityRole securityRole) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        edit(getEntityManager(), securityRole);
    }

    public void edit(EntityManager em, SecurityRole securityRole) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = securityRole.getRoleID();
        if (findSecurityRole(id) == null) {
            throw new NonexistentEntityException("The securityRole with id " + id + " no longer exists.");
        }
        
        SecurityRole persistentSecurityRole = em.find(SecurityRole.class, securityRole.getRoleID());
        List<Person> personListOld = persistentSecurityRole.getPersonList();
        List<Person> personListNew = securityRole.getPersonList();
        List<Person> attachedPersonListNew = new ArrayList<Person>();
        for (Person personListNewPersonToAttach : personListNew) {
            personListNewPersonToAttach = em.getReference(personListNewPersonToAttach.getClass(), personListNewPersonToAttach.getSystemID());
            attachedPersonListNew.add(personListNewPersonToAttach);
        }
        personListNew = attachedPersonListNew;
        securityRole.setPersonList(personListNew);
        securityRole = em.merge(securityRole);
        for (Person personListOldPerson : personListOld) {
            if (!personListNew.contains(personListOldPerson)) {
                personListOldPerson.getSecurityRoleList().remove(securityRole);
                personListOldPerson = em.merge(personListOldPerson);
            }
        }
        for (Person personListNewPerson : personListNew) {
            if (!personListOld.contains(personListNewPerson)) {
                personListNewPerson.getSecurityRoleList().add(securityRole);
                personListNewPerson = em.merge(personListNewPerson);
            }
        }
            
                

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
        List<Person> personList = securityRole.getPersonList();
        for (Person personListPerson : personList) {
            personListPerson.getSecurityRoleList().remove(securityRole);
            personListPerson = em.merge(personListPerson);
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
            em.close();
        }
    }

    public SecurityRole findSecurityRole(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SecurityRole.class, id);
        } finally {
            em.close();
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
            em.close();
        }
    }
    
}
