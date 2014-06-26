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
import java.util.Collection;
import com.softserve.DBEntities.Person;
import java.util.List;
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
        if (address.getUpEmployeeInformationCollection() == null) {
            address.setUpEmployeeInformationCollection(new ArrayList<UpEmployeeInformation>());
        }
        if (address.getPersonCollection() == null) {
            address.setPersonCollection(new ArrayList<Person>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<UpEmployeeInformation> attachedUpEmployeeInformationCollection = new ArrayList<UpEmployeeInformation>();
            for (UpEmployeeInformation upEmployeeInformationCollectionUpEmployeeInformationToAttach : address.getUpEmployeeInformationCollection()) {
                upEmployeeInformationCollectionUpEmployeeInformationToAttach = em.getReference(upEmployeeInformationCollectionUpEmployeeInformationToAttach.getClass(), upEmployeeInformationCollectionUpEmployeeInformationToAttach.getEmployeeID());
                attachedUpEmployeeInformationCollection.add(upEmployeeInformationCollectionUpEmployeeInformationToAttach);
            }
            address.setUpEmployeeInformationCollection(attachedUpEmployeeInformationCollection);
            Collection<Person> attachedPersonCollection = new ArrayList<Person>();
            for (Person personCollectionPersonToAttach : address.getPersonCollection()) {
                personCollectionPersonToAttach = em.getReference(personCollectionPersonToAttach.getClass(), personCollectionPersonToAttach.getSystemID());
                attachedPersonCollection.add(personCollectionPersonToAttach);
            }
            address.setPersonCollection(attachedPersonCollection);
            em.persist(address);
            for (UpEmployeeInformation upEmployeeInformationCollectionUpEmployeeInformation : address.getUpEmployeeInformationCollection()) {
                Address oldPhysicalAddressOfUpEmployeeInformationCollectionUpEmployeeInformation = upEmployeeInformationCollectionUpEmployeeInformation.getPhysicalAddress();
                upEmployeeInformationCollectionUpEmployeeInformation.setPhysicalAddress(address);
                upEmployeeInformationCollectionUpEmployeeInformation = em.merge(upEmployeeInformationCollectionUpEmployeeInformation);
                if (oldPhysicalAddressOfUpEmployeeInformationCollectionUpEmployeeInformation != null) {
                    oldPhysicalAddressOfUpEmployeeInformationCollectionUpEmployeeInformation.getUpEmployeeInformationCollection().remove(upEmployeeInformationCollectionUpEmployeeInformation);
                    oldPhysicalAddressOfUpEmployeeInformationCollectionUpEmployeeInformation = em.merge(oldPhysicalAddressOfUpEmployeeInformationCollectionUpEmployeeInformation);
                }
            }
            for (Person personCollectionPerson : address.getPersonCollection()) {
                Address oldAddressLine1OfPersonCollectionPerson = personCollectionPerson.getAddressLine1();
                personCollectionPerson.setAddressLine1(address);
                personCollectionPerson = em.merge(personCollectionPerson);
                if (oldAddressLine1OfPersonCollectionPerson != null) {
                    oldAddressLine1OfPersonCollectionPerson.getPersonCollection().remove(personCollectionPerson);
                    oldAddressLine1OfPersonCollectionPerson = em.merge(oldAddressLine1OfPersonCollectionPerson);
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
            Collection<UpEmployeeInformation> upEmployeeInformationCollectionOld = persistentAddress.getUpEmployeeInformationCollection();
            Collection<UpEmployeeInformation> upEmployeeInformationCollectionNew = address.getUpEmployeeInformationCollection();
            Collection<Person> personCollectionOld = persistentAddress.getPersonCollection();
            Collection<Person> personCollectionNew = address.getPersonCollection();
            List<String> illegalOrphanMessages = null;
            for (UpEmployeeInformation upEmployeeInformationCollectionOldUpEmployeeInformation : upEmployeeInformationCollectionOld) {
                if (!upEmployeeInformationCollectionNew.contains(upEmployeeInformationCollectionOldUpEmployeeInformation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UpEmployeeInformation " + upEmployeeInformationCollectionOldUpEmployeeInformation + " since its physicalAddress field is not nullable.");
                }
            }
            for (Person personCollectionOldPerson : personCollectionOld) {
                if (!personCollectionNew.contains(personCollectionOldPerson)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Person " + personCollectionOldPerson + " since its addressLine1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UpEmployeeInformation> attachedUpEmployeeInformationCollectionNew = new ArrayList<UpEmployeeInformation>();
            for (UpEmployeeInformation upEmployeeInformationCollectionNewUpEmployeeInformationToAttach : upEmployeeInformationCollectionNew) {
                upEmployeeInformationCollectionNewUpEmployeeInformationToAttach = em.getReference(upEmployeeInformationCollectionNewUpEmployeeInformationToAttach.getClass(), upEmployeeInformationCollectionNewUpEmployeeInformationToAttach.getEmployeeID());
                attachedUpEmployeeInformationCollectionNew.add(upEmployeeInformationCollectionNewUpEmployeeInformationToAttach);
            }
            upEmployeeInformationCollectionNew = attachedUpEmployeeInformationCollectionNew;
            address.setUpEmployeeInformationCollection(upEmployeeInformationCollectionNew);
            Collection<Person> attachedPersonCollectionNew = new ArrayList<Person>();
            for (Person personCollectionNewPersonToAttach : personCollectionNew) {
                personCollectionNewPersonToAttach = em.getReference(personCollectionNewPersonToAttach.getClass(), personCollectionNewPersonToAttach.getSystemID());
                attachedPersonCollectionNew.add(personCollectionNewPersonToAttach);
            }
            personCollectionNew = attachedPersonCollectionNew;
            address.setPersonCollection(personCollectionNew);
            address = em.merge(address);
            for (UpEmployeeInformation upEmployeeInformationCollectionNewUpEmployeeInformation : upEmployeeInformationCollectionNew) {
                if (!upEmployeeInformationCollectionOld.contains(upEmployeeInformationCollectionNewUpEmployeeInformation)) {
                    Address oldPhysicalAddressOfUpEmployeeInformationCollectionNewUpEmployeeInformation = upEmployeeInformationCollectionNewUpEmployeeInformation.getPhysicalAddress();
                    upEmployeeInformationCollectionNewUpEmployeeInformation.setPhysicalAddress(address);
                    upEmployeeInformationCollectionNewUpEmployeeInformation = em.merge(upEmployeeInformationCollectionNewUpEmployeeInformation);
                    if (oldPhysicalAddressOfUpEmployeeInformationCollectionNewUpEmployeeInformation != null && !oldPhysicalAddressOfUpEmployeeInformationCollectionNewUpEmployeeInformation.equals(address)) {
                        oldPhysicalAddressOfUpEmployeeInformationCollectionNewUpEmployeeInformation.getUpEmployeeInformationCollection().remove(upEmployeeInformationCollectionNewUpEmployeeInformation);
                        oldPhysicalAddressOfUpEmployeeInformationCollectionNewUpEmployeeInformation = em.merge(oldPhysicalAddressOfUpEmployeeInformationCollectionNewUpEmployeeInformation);
                    }
                }
            }
            for (Person personCollectionNewPerson : personCollectionNew) {
                if (!personCollectionOld.contains(personCollectionNewPerson)) {
                    Address oldAddressLine1OfPersonCollectionNewPerson = personCollectionNewPerson.getAddressLine1();
                    personCollectionNewPerson.setAddressLine1(address);
                    personCollectionNewPerson = em.merge(personCollectionNewPerson);
                    if (oldAddressLine1OfPersonCollectionNewPerson != null && !oldAddressLine1OfPersonCollectionNewPerson.equals(address)) {
                        oldAddressLine1OfPersonCollectionNewPerson.getPersonCollection().remove(personCollectionNewPerson);
                        oldAddressLine1OfPersonCollectionNewPerson = em.merge(oldAddressLine1OfPersonCollectionNewPerson);
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
            Collection<UpEmployeeInformation> upEmployeeInformationCollectionOrphanCheck = address.getUpEmployeeInformationCollection();
            for (UpEmployeeInformation upEmployeeInformationCollectionOrphanCheckUpEmployeeInformation : upEmployeeInformationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Address (" + address + ") cannot be destroyed since the UpEmployeeInformation " + upEmployeeInformationCollectionOrphanCheckUpEmployeeInformation + " in its upEmployeeInformationCollection field has a non-nullable physicalAddress field.");
            }
            Collection<Person> personCollectionOrphanCheck = address.getPersonCollection();
            for (Person personCollectionOrphanCheckPerson : personCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Address (" + address + ") cannot be destroyed since the Person " + personCollectionOrphanCheckPerson + " in its personCollection field has a non-nullable addressLine1 field.");
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
