/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.Address;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.persistence.DBEntities.EmployeeInformation;
import java.util.ArrayList;
import java.util.List;
import com.softserve.persistence.DBEntities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class AddressJpaController implements Serializable {

    public AddressJpaController(EntityManager em) {
        this.emm = em;
    }
    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(Address address) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), address);
    }

    public void create(EntityManager em, Address address) throws RollbackFailureException, Exception 
    {
        if (address.getEmployeeInformationList() == null) {
            address.setEmployeeInformationList(new ArrayList<EmployeeInformation>());
        }
        if (address.getPersonList() == null) {
            address.setPersonList(new ArrayList<Person>());
        }

        
        em.persist(address);
    }
    
    public Address edit(Address address) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), address);
    }

    public Address edit(EntityManager em, Address address) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        Long id = address.getAddressID();
        if (findAddress(id) == null) {
            throw new NonexistentEntityException("The address with id " + id + " no longer exists.");
        }
    
        return em.merge(address);
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Address address;
        try {
            address = em.getReference(Address.class, id);
            address.getAddressID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The address with id " + id + " no longer exists.", enfe);
        }

        em.remove(address);
            
    }

    public List<Address> findAddressEntities() {
        return findAddressEntities(true, -1, -1);
    }

    public List<Address> findAddressEntities(int maxResults, int firstResult) {
        return findAddressEntities(false, maxResults, firstResult);
    }

    private List<Address> findAddressEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Address.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public Address findAddress(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Address.class, id);
        } finally {
            
        }
    }

    public int getAddressCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Address> rt = cq.from(Address.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
