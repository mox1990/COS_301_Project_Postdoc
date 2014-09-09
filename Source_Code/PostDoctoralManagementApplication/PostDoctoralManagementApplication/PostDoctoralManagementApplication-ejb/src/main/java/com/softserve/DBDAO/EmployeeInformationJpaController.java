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

        Person person = employeeInformation.getPerson();
        if (person != null) {
            person = em.getReference(person.getClass(), person.getSystemID());
            employeeInformation.setPerson(person);
        }
        Department department = employeeInformation.getDepartment();
        if (department != null) {
            department = em.getReference(department.getClass(), department.getDepartmentID());
            employeeInformation.setDepartment(department);
        }
        Address physicalAddress = employeeInformation.getPhysicalAddress();
        if (physicalAddress != null) {
            physicalAddress = em.getReference(physicalAddress.getClass(), physicalAddress.getAddressID());
            employeeInformation.setPhysicalAddress(physicalAddress);
        }
        em.persist(employeeInformation);
        if (person != null) {
            person.setEmployeeInformation(employeeInformation);
            person = em.merge(person);
        }
        if (department != null) {
            department.getEmployeeInformationList().add(employeeInformation);
            department = em.merge(department);
        }
        if (physicalAddress != null) {
            physicalAddress.getEmployeeInformationList().add(employeeInformation);
            physicalAddress = em.merge(physicalAddress);
        }
            
    }
    
    public void edit(EmployeeInformation employeeInformation) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        edit(getEntityManager(), employeeInformation);
    }

    public void edit(EntityManager em, EmployeeInformation employeeInformation) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

        String id = employeeInformation.getEmployeeID();
        if (findEmployeeInformation(id) == null) {
            throw new NonexistentEntityException("The employeeInformation with id " + id + " no longer exists.");
        }
        
        EmployeeInformation persistentEmployeeInformation = em.find(EmployeeInformation.class, employeeInformation.getEmployeeID());
        Person personOld = persistentEmployeeInformation.getPerson();
        Person personNew = employeeInformation.getPerson();
        Department departmentOld = persistentEmployeeInformation.getDepartment();
        Department departmentNew = employeeInformation.getDepartment();
        Address physicalAddressOld = persistentEmployeeInformation.getPhysicalAddress();
        Address physicalAddressNew = employeeInformation.getPhysicalAddress();
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
        if (personNew != null) {
            personNew = em.getReference(personNew.getClass(), personNew.getSystemID());
            employeeInformation.setPerson(personNew);
        }
        if (departmentNew != null) {
            departmentNew = em.getReference(departmentNew.getClass(), departmentNew.getDepartmentID());
            employeeInformation.setDepartment(departmentNew);
        }
        if (physicalAddressNew != null) {
            physicalAddressNew = em.getReference(physicalAddressNew.getClass(), physicalAddressNew.getAddressID());
            employeeInformation.setPhysicalAddress(physicalAddressNew);
        }
        employeeInformation = em.merge(employeeInformation);
        if (personOld != null && !personOld.equals(personNew)) {
            personOld.setEmployeeInformation(null);
            personOld = em.merge(personOld);
        }
        if (personNew != null && !personNew.equals(personOld)) {
            personNew.setEmployeeInformation(employeeInformation);
            personNew = em.merge(personNew);
        }
        if (departmentOld != null && !departmentOld.equals(departmentNew)) {
            departmentOld.getEmployeeInformationList().remove(employeeInformation);
            departmentOld = em.merge(departmentOld);
        }
        if (departmentNew != null && !departmentNew.equals(departmentOld)) {
            departmentNew.getEmployeeInformationList().add(employeeInformation);
            departmentNew = em.merge(departmentNew);
        }
        if (physicalAddressOld != null && !physicalAddressOld.equals(physicalAddressNew)) {
            physicalAddressOld.getEmployeeInformationList().remove(employeeInformation);
            physicalAddressOld = em.merge(physicalAddressOld);
        }
        if (physicalAddressNew != null && !physicalAddressNew.equals(physicalAddressOld)) {
            physicalAddressNew.getEmployeeInformationList().add(employeeInformation);
            physicalAddressNew = em.merge(physicalAddressNew);
        }
            
                
           
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
        Person person = employeeInformation.getPerson();
        if (person != null) {
            person.setEmployeeInformation(null);
            person = em.merge(person);
        }
        Department department = employeeInformation.getDepartment();
        if (department != null) {
            department.getEmployeeInformationList().remove(employeeInformation);
            department = em.merge(department);
        }
        Address physicalAddress = employeeInformation.getPhysicalAddress();
        if (physicalAddress != null) {
            physicalAddress.getEmployeeInformationList().remove(employeeInformation);
            physicalAddress = em.merge(physicalAddress);
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
