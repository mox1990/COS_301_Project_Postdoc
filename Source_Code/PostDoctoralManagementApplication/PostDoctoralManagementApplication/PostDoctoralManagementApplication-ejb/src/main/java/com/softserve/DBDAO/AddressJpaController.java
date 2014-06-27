/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Address;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.UpEmployeeInformation;
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

    public AddressJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Address address) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (address.getUpEmployeeInformationList() == null) {
            address.setUpEmployeeInformationList(new ArrayList<UpEmployeeInformation>());
        }
        if (address.getPersonList() == null) {
            address.setPersonList(new ArrayList<Person>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<UpEmployeeInformation> attachedUpEmployeeInformationList = new ArrayList<UpEmployeeInformation>();
            for (UpEmployeeInformation upEmployeeInformationListUpEmployeeInformationToAttach : address.getUpEmployeeInformationList()) {
                upEmployeeInformationListUpEmployeeInformationToAttach = em.getReference(upEmployeeInformationListUpEmployeeInformationToAttach.getClass(), upEmployeeInformationListUpEmployeeInformationToAttach.getEmployeeID());
                attachedUpEmployeeInformationList.add(upEmployeeInformationListUpEmployeeInformationToAttach);
            }
            address.setUpEmployeeInformationList(attachedUpEmployeeInformationList);
            List<Person> attachedPersonList = new ArrayList<Person>();
            for (Person personListPersonToAttach : address.getPersonList()) {
                personListPersonToAttach = em.getReference(personListPersonToAttach.getClass(), personListPersonToAttach.getSystemID());
                attachedPersonList.add(personListPersonToAttach);
            }
            address.setPersonList(attachedPersonList);
            em.persist(address);
            for (UpEmployeeInformation upEmployeeInformationListUpEmployeeInformation : address.getUpEmployeeInformationList()) {
                Address oldPhysicalAddressOfUpEmployeeInformationListUpEmployeeInformation = upEmployeeInformationListUpEmployeeInformation.getPhysicalAddress();
                upEmployeeInformationListUpEmployeeInformation.setPhysicalAddress(address);
                upEmployeeInformationListUpEmployeeInformation = em.merge(upEmployeeInformationListUpEmployeeInformation);
                if (oldPhysicalAddressOfUpEmployeeInformationListUpEmployeeInformation != null) {
                    oldPhysicalAddressOfUpEmployeeInformationListUpEmployeeInformation.getUpEmployeeInformationList().remove(upEmployeeInformationListUpEmployeeInformation);
                    oldPhysicalAddressOfUpEmployeeInformationListUpEmployeeInformation = em.merge(oldPhysicalAddressOfUpEmployeeInformationListUpEmployeeInformation);
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
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAddress(address.getAddressID()) != null) {
                throw new PreexistingEntityException("Address " + address + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Address address) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Address persistentAddress = em.find(Address.class, address.getAddressID());
            List<UpEmployeeInformation> upEmployeeInformationListOld = persistentAddress.getUpEmployeeInformationList();
            List<UpEmployeeInformation> upEmployeeInformationListNew = address.getUpEmployeeInformationList();
            List<Person> personListOld = persistentAddress.getPersonList();
            List<Person> personListNew = address.getPersonList();
            List<String> illegalOrphanMessages = null;
            for (UpEmployeeInformation upEmployeeInformationListOldUpEmployeeInformation : upEmployeeInformationListOld) {
                if (!upEmployeeInformationListNew.contains(upEmployeeInformationListOldUpEmployeeInformation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UpEmployeeInformation " + upEmployeeInformationListOldUpEmployeeInformation + " since its physicalAddress field is not nullable.");
                }
            }
            for (Person personListOldPerson : personListOld) {
                if (!personListNew.contains(personListOldPerson)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Person " + personListOldPerson + " since its addressLine1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<UpEmployeeInformation> attachedUpEmployeeInformationListNew = new ArrayList<UpEmployeeInformation>();
            for (UpEmployeeInformation upEmployeeInformationListNewUpEmployeeInformationToAttach : upEmployeeInformationListNew) {
                upEmployeeInformationListNewUpEmployeeInformationToAttach = em.getReference(upEmployeeInformationListNewUpEmployeeInformationToAttach.getClass(), upEmployeeInformationListNewUpEmployeeInformationToAttach.getEmployeeID());
                attachedUpEmployeeInformationListNew.add(upEmployeeInformationListNewUpEmployeeInformationToAttach);
            }
            upEmployeeInformationListNew = attachedUpEmployeeInformationListNew;
            address.setUpEmployeeInformationList(upEmployeeInformationListNew);
            List<Person> attachedPersonListNew = new ArrayList<Person>();
            for (Person personListNewPersonToAttach : personListNew) {
                personListNewPersonToAttach = em.getReference(personListNewPersonToAttach.getClass(), personListNewPersonToAttach.getSystemID());
                attachedPersonListNew.add(personListNewPersonToAttach);
            }
            personListNew = attachedPersonListNew;
            address.setPersonList(personListNew);
            address = em.merge(address);
            for (UpEmployeeInformation upEmployeeInformationListNewUpEmployeeInformation : upEmployeeInformationListNew) {
                if (!upEmployeeInformationListOld.contains(upEmployeeInformationListNewUpEmployeeInformation)) {
                    Address oldPhysicalAddressOfUpEmployeeInformationListNewUpEmployeeInformation = upEmployeeInformationListNewUpEmployeeInformation.getPhysicalAddress();
                    upEmployeeInformationListNewUpEmployeeInformation.setPhysicalAddress(address);
                    upEmployeeInformationListNewUpEmployeeInformation = em.merge(upEmployeeInformationListNewUpEmployeeInformation);
                    if (oldPhysicalAddressOfUpEmployeeInformationListNewUpEmployeeInformation != null && !oldPhysicalAddressOfUpEmployeeInformationListNewUpEmployeeInformation.equals(address)) {
                        oldPhysicalAddressOfUpEmployeeInformationListNewUpEmployeeInformation.getUpEmployeeInformationList().remove(upEmployeeInformationListNewUpEmployeeInformation);
                        oldPhysicalAddressOfUpEmployeeInformationListNewUpEmployeeInformation = em.merge(oldPhysicalAddressOfUpEmployeeInformationListNewUpEmployeeInformation);
                    }
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
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = address.getAddressID();
                if (findAddress(id) == null) {
                    throw new NonexistentEntityException("The address with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Address address;
            try {
                address = em.getReference(Address.class, id);
                address.getAddressID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The address with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<UpEmployeeInformation> upEmployeeInformationListOrphanCheck = address.getUpEmployeeInformationList();
            for (UpEmployeeInformation upEmployeeInformationListOrphanCheckUpEmployeeInformation : upEmployeeInformationListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Address (" + address + ") cannot be destroyed since the UpEmployeeInformation " + upEmployeeInformationListOrphanCheckUpEmployeeInformation + " in its upEmployeeInformationList field has a non-nullable physicalAddress field.");
            }
            List<Person> personListOrphanCheck = address.getPersonList();
            for (Person personListOrphanCheckPerson : personListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Address (" + address + ") cannot be destroyed since the Person " + personListOrphanCheckPerson + " in its personList field has a non-nullable addressLine1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(address);
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
