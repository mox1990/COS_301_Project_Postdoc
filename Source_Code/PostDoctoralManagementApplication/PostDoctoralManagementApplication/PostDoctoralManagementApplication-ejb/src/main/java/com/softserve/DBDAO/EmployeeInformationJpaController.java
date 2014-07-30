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
import com.softserve.DBEntities.Location;
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

    public EmployeeInformationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmployeeInformation employeeInformation) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
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
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person person = employeeInformation.getPerson();
            if (person != null) {
                person = em.getReference(person.getClass(), person.getSystemID());
                employeeInformation.setPerson(person);
            }
            Location location = employeeInformation.getLocation();
            if (location != null) {
                location = em.getReference(location.getClass(), location.getLocationID());
                employeeInformation.setLocation(location);
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
            if (location != null) {
                location.getEmployeeInformationList().add(employeeInformation);
                location = em.merge(location);
            }
            if (physicalAddress != null) {
                physicalAddress.getEmployeeInformationList().add(employeeInformation);
                physicalAddress = em.merge(physicalAddress);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEmployeeInformation(employeeInformation.getEmployeeID()) != null) {
                throw new PreexistingEntityException("EmployeeInformation " + employeeInformation + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmployeeInformation employeeInformation) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EmployeeInformation persistentEmployeeInformation = em.find(EmployeeInformation.class, employeeInformation.getEmployeeID());
            Person personOld = persistentEmployeeInformation.getPerson();
            Person personNew = employeeInformation.getPerson();
            Location locationOld = persistentEmployeeInformation.getLocation();
            Location locationNew = employeeInformation.getLocation();
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
            if (locationNew != null) {
                locationNew = em.getReference(locationNew.getClass(), locationNew.getLocationID());
                employeeInformation.setLocation(locationNew);
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
            if (locationOld != null && !locationOld.equals(locationNew)) {
                locationOld.getEmployeeInformationList().remove(employeeInformation);
                locationOld = em.merge(locationOld);
            }
            if (locationNew != null && !locationNew.equals(locationOld)) {
                locationNew.getEmployeeInformationList().add(employeeInformation);
                locationNew = em.merge(locationNew);
            }
            if (physicalAddressOld != null && !physicalAddressOld.equals(physicalAddressNew)) {
                physicalAddressOld.getEmployeeInformationList().remove(employeeInformation);
                physicalAddressOld = em.merge(physicalAddressOld);
            }
            if (physicalAddressNew != null && !physicalAddressNew.equals(physicalAddressOld)) {
                physicalAddressNew.getEmployeeInformationList().add(employeeInformation);
                physicalAddressNew = em.merge(physicalAddressNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = employeeInformation.getEmployeeID();
                if (findEmployeeInformation(id) == null) {
                    throw new NonexistentEntityException("The employeeInformation with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
            Location location = employeeInformation.getLocation();
            if (location != null) {
                location.getEmployeeInformationList().remove(employeeInformation);
                location = em.merge(location);
            }
            Address physicalAddress = employeeInformation.getPhysicalAddress();
            if (physicalAddress != null) {
                physicalAddress.getEmployeeInformationList().remove(employeeInformation);
                physicalAddress = em.merge(physicalAddress);
            }
            em.remove(employeeInformation);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
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
            em.close();
        }
    }

    public EmployeeInformation findEmployeeInformation(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmployeeInformation.class, id);
        } finally {
            em.close();
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
            em.close();
        }
    }
    
}
