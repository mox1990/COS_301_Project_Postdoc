/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.EmployeeInformation;
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
public class EmployeeInformationJpaController implements Serializable {

    public EmployeeInformationJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(EmployeeInformation employeeInformation) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        create(getEntityManager(), employeeInformation);
    }
    
    public void create(EntityManager em, EmployeeInformation employeeInformation) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        List<String> illegalOrphanMessages = null;
        Person personOrphanCheck = employeeInformation.getPerson();
        if (personOrphanCheck != null) {
            EmployeeInformation oldEmployeeInformationOfPerson = personOrphanCheck.getEmployeeInformation();
            if (oldEmployeeInformationOfPerson != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Person " + personOrphanCheck + " already has an item of type EmployeeInformation whose person column cannot be null. Please make another selection for the person field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }


        em.persist(employeeInformation);
            
    }
    
    public EmployeeInformation edit(EmployeeInformation employeeInformation) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), employeeInformation);
    }

    public EmployeeInformation edit(EntityManager em, EmployeeInformation employeeInformation) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

        String id = employeeInformation.getEmployeeID();
        if (findEmployeeInformation(id) == null) {
            throw new NonexistentEntityException("The employeeInformation with id " + id + " no longer exists.");
        }
        
        EmployeeInformation persistentEmployeeInformation = em.find(EmployeeInformation.class, employeeInformation.getEmployeeID());
        Person personOld = persistentEmployeeInformation.getPerson();
        Person personNew = employeeInformation.getPerson();

        List<String> illegalOrphanMessages = null;
        if (personNew != null && !personNew.equals(personOld)) {
            EmployeeInformation oldEmployeeInformationOfPerson = personNew.getEmployeeInformation();
            if (oldEmployeeInformationOfPerson != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Person " + personNew + " already has an item of type EmployeeInformation whose person column cannot be null. Please make another selection for the person field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }

        return em.merge(employeeInformation);
        
            
                
           
    }
    
    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, String id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        EmployeeInformation employeeInformation;
        try {
            employeeInformation = em.getReference(EmployeeInformation.class, id);
            employeeInformation.getEmployeeID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The employeeInformation with id " + id + " no longer exists.", enfe);
        }

        em.remove(employeeInformation);            
    }

    public List<EmployeeInformation> findEmployeeInformationEntities() {
        return findEmployeeInformationEntities(true, -1, -1);
    }

    public List<EmployeeInformation> findEmployeeInformationEntities(int maxResults, int firstResult) {
        return findEmployeeInformationEntities(false, maxResults, firstResult);
    }

    private List<EmployeeInformation> findEmployeeInformationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmployeeInformation.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public EmployeeInformation findEmployeeInformation(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmployeeInformation.class, id);
        } finally {
            
        }
    }

    public int getEmployeeInformationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmployeeInformation> rt = cq.from(EmployeeInformation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
