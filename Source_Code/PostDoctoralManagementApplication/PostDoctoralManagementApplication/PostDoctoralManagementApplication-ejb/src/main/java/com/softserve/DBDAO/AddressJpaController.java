/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Address;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.EmployeeInformation;
import java.util.ArrayList;
import java.util.List;
import com.softserve.DBEntities.Person;
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

        //Replace references to with actual db references
        List<EmployeeInformation> attachedEmployeeInformationList = new ArrayList<EmployeeInformation>();
        for (EmployeeInformation employeeInformationListEmployeeInformationToAttach : address.getEmployeeInformationList()) {
            employeeInformationListEmployeeInformationToAttach = em.getReference(employeeInformationListEmployeeInformationToAttach.getClass(), employeeInformationListEmployeeInformationToAttach.getEmployeeID());
            attachedEmployeeInformationList.add(employeeInformationListEmployeeInformationToAttach);
        }
        address.setEmployeeInformationList(attachedEmployeeInformationList);
        List<Person> attachedPersonList = new ArrayList<Person>();
        for (Person personListPersonToAttach : address.getPersonList()) {
            personListPersonToAttach = em.getReference(personListPersonToAttach.getClass(), personListPersonToAttach.getSystemID());
            attachedPersonList.add(personListPersonToAttach);
        }
        address.setPersonList(attachedPersonList);
        
        em.persist(address);
        
        //Update references of old enties
        for (EmployeeInformation employeeInformationListEmployeeInformation : address.getEmployeeInformationList()) {
            Address oldPhysicalAddressOfEmployeeInformationListEmployeeInformation = employeeInformationListEmployeeInformation.getPhysicalAddress();
            employeeInformationListEmployeeInformation.setPhysicalAddress(address);
            employeeInformationListEmployeeInformation = em.merge(employeeInformationListEmployeeInformation);
            if (oldPhysicalAddressOfEmployeeInformationListEmployeeInformation != null) {
                oldPhysicalAddressOfEmployeeInformationListEmployeeInformation.getEmployeeInformationList().remove(employeeInformationListEmployeeInformation);
                oldPhysicalAddressOfEmployeeInformationListEmployeeInformation = em.merge(oldPhysicalAddressOfEmployeeInformationListEmployeeInformation);
            }
        }
        for (Person personListPerson : address.getPersonList()) {
            Address oldAddressLine1OfPersonListPerson = personListPerson.getAddressLine1();
            personListPerson.setAddressLine1(address);
            personListPerson = em.merge(personListPerson);
            if (oldAddressLine1OfPersonListPerson != null) {
                oldAddressLine1OfPersonListPerson.getPersonList().remove(personListPerson);
                oldAddressLine1OfPersonListPerson = em.merge(oldAddressLine1OfPersonListPerson);
            }
        }

    }
    
    public void edit(Address address) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        edit(getEntityManager(), address);
    }

    public void edit(EntityManager em, Address address) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        Long id = address.getAddressID();
        if (findAddress(id) == null) {
            throw new NonexistentEntityException("The address with id " + id + " no longer exists.");
        }
        
        Address persistentAddress = em.find(Address.class, address.getAddressID());

        //Update references with actual and maintain old and new changes
        List<EmployeeInformation> employeeInformationListOld = persistentAddress.getEmployeeInformationList();
        List<EmployeeInformation> employeeInformationListNew = address.getEmployeeInformationList();
        List<Person> personListOld = persistentAddress.getPersonList();
        List<Person> personListNew = address.getPersonList();
        List<EmployeeInformation> attachedEmployeeInformationListNew = new ArrayList<EmployeeInformation>();

        for (EmployeeInformation employeeInformationListNewEmployeeInformationToAttach : employeeInformationListNew) {
            employeeInformationListNewEmployeeInformationToAttach = em.getReference(employeeInformationListNewEmployeeInformationToAttach.getClass(), employeeInformationListNewEmployeeInformationToAttach.getEmployeeID());
            attachedEmployeeInformationListNew.add(employeeInformationListNewEmployeeInformationToAttach);
        }
        employeeInformationListNew = attachedEmployeeInformationListNew;
        address.setEmployeeInformationList(employeeInformationListNew);
        List<Person> attachedPersonListNew = new ArrayList<Person>();
        for (Person personListNewPersonToAttach : personListNew) {
            personListNewPersonToAttach = em.getReference(personListNewPersonToAttach.getClass(), personListNewPersonToAttach.getSystemID());
            attachedPersonListNew.add(personListNewPersonToAttach);
        }
        personListNew = attachedPersonListNew;
        address.setPersonList(personListNew);

        address = em.merge(address);

        //Update references of old enties
        for (EmployeeInformation employeeInformationListOldEmployeeInformation : employeeInformationListOld) {
            if (!employeeInformationListNew.contains(employeeInformationListOldEmployeeInformation)) {
                employeeInformationListOldEmployeeInformation.setPhysicalAddress(null);
                employeeInformationListOldEmployeeInformation = em.merge(employeeInformationListOldEmployeeInformation);
            }
        }
        for (EmployeeInformation employeeInformationListNewEmployeeInformation : employeeInformationListNew) {
            if (!employeeInformationListOld.contains(employeeInformationListNewEmployeeInformation)) {
                Address oldPhysicalAddressOfEmployeeInformationListNewEmployeeInformation = employeeInformationListNewEmployeeInformation.getPhysicalAddress();
                employeeInformationListNewEmployeeInformation.setPhysicalAddress(address);
                employeeInformationListNewEmployeeInformation = em.merge(employeeInformationListNewEmployeeInformation);
                if (oldPhysicalAddressOfEmployeeInformationListNewEmployeeInformation != null && !oldPhysicalAddressOfEmployeeInformationListNewEmployeeInformation.equals(address)) {
                    oldPhysicalAddressOfEmployeeInformationListNewEmployeeInformation.getEmployeeInformationList().remove(employeeInformationListNewEmployeeInformation);
                    oldPhysicalAddressOfEmployeeInformationListNewEmployeeInformation = em.merge(oldPhysicalAddressOfEmployeeInformationListNewEmployeeInformation);
                }
            }
        }
        for (Person personListOldPerson : personListOld) {
            if (!personListNew.contains(personListOldPerson)) {
                personListOldPerson.setAddressLine1(null);
                personListOldPerson = em.merge(personListOldPerson);
            }
        }
        for (Person personListNewPerson : personListNew) {
            if (!personListOld.contains(personListNewPerson)) {
                Address oldAddressLine1OfPersonListNewPerson = personListNewPerson.getAddressLine1();
                personListNewPerson.setAddressLine1(address);
                personListNewPerson = em.merge(personListNewPerson);
                if (oldAddressLine1OfPersonListNewPerson != null && !oldAddressLine1OfPersonListNewPerson.equals(address)) {
                    oldAddressLine1OfPersonListNewPerson.getPersonList().remove(personListNewPerson);
                    oldAddressLine1OfPersonListNewPerson = em.merge(oldAddressLine1OfPersonListNewPerson);
                }
            }
        }

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
        List<EmployeeInformation> employeeInformationList = address.getEmployeeInformationList();
        for (EmployeeInformation employeeInformationListEmployeeInformation : employeeInformationList) {
            employeeInformationListEmployeeInformation.setPhysicalAddress(null);
            employeeInformationListEmployeeInformation = em.merge(employeeInformationListEmployeeInformation);
        }
        List<Person> personList = address.getPersonList();
        for (Person personListPerson : personList) {
            personListPerson.setAddressLine1(null);
            personListPerson = em.merge(personListPerson);
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
            em.close();
        }
    }

    public Address findAddress(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Address.class, id);
        } finally {
            em.close();
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
            em.close();
        }
    }
    
}
