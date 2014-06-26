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
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.UpEmployeeInformation;
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
public class UpEmployeeInformationJpaController implements Serializable {

    public UpEmployeeInformationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UpEmployeeInformation upEmployeeInformation) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Person personOrphanCheck = upEmployeeInformation.getPerson();
        if (personOrphanCheck != null) {
            UpEmployeeInformation oldUpEmployeeInformationOfPerson = personOrphanCheck.getUpEmployeeInformation();
            if (oldUpEmployeeInformationOfPerson != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Person " + personOrphanCheck + " already has an item of type UpEmployeeInformation whose person column cannot be null. Please make another selection for the person field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person person = upEmployeeInformation.getPerson();
            if (person != null) {
                person = em.getReference(person.getClass(), person.getSystemID());
                upEmployeeInformation.setPerson(person);
            }
            Address physicalAddress = upEmployeeInformation.getPhysicalAddress();
            if (physicalAddress != null) {
                physicalAddress = em.getReference(physicalAddress.getClass(), physicalAddress.getAddressID());
                upEmployeeInformation.setPhysicalAddress(physicalAddress);
            }
            em.persist(upEmployeeInformation);
            if (person != null) {
                person.setUpEmployeeInformation(upEmployeeInformation);
                person = em.merge(person);
            }
            if (physicalAddress != null) {
                physicalAddress.getUpEmployeeInformationCollection().add(upEmployeeInformation);
                physicalAddress = em.merge(physicalAddress);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUpEmployeeInformation(upEmployeeInformation.getEmployeeID()) != null) {
                throw new PreexistingEntityException("UpEmployeeInformation " + upEmployeeInformation + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UpEmployeeInformation upEmployeeInformation) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UpEmployeeInformation persistentUpEmployeeInformation = em.find(UpEmployeeInformation.class, upEmployeeInformation.getEmployeeID());
            Person personOld = persistentUpEmployeeInformation.getPerson();
            Person personNew = upEmployeeInformation.getPerson();
            Address physicalAddressOld = persistentUpEmployeeInformation.getPhysicalAddress();
            Address physicalAddressNew = upEmployeeInformation.getPhysicalAddress();
            List<String> illegalOrphanMessages = null;
            if (personNew != null && !personNew.equals(personOld)) {
                UpEmployeeInformation oldUpEmployeeInformationOfPerson = personNew.getUpEmployeeInformation();
                if (oldUpEmployeeInformationOfPerson != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Person " + personNew + " already has an item of type UpEmployeeInformation whose person column cannot be null. Please make another selection for the person field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personNew != null) {
                personNew = em.getReference(personNew.getClass(), personNew.getSystemID());
                upEmployeeInformation.setPerson(personNew);
            }
            if (physicalAddressNew != null) {
                physicalAddressNew = em.getReference(physicalAddressNew.getClass(), physicalAddressNew.getAddressID());
                upEmployeeInformation.setPhysicalAddress(physicalAddressNew);
            }
            upEmployeeInformation = em.merge(upEmployeeInformation);
            if (personOld != null && !personOld.equals(personNew)) {
                personOld.setUpEmployeeInformation(null);
                personOld = em.merge(personOld);
            }
            if (personNew != null && !personNew.equals(personOld)) {
                personNew.setUpEmployeeInformation(upEmployeeInformation);
                personNew = em.merge(personNew);
            }
            if (physicalAddressOld != null && !physicalAddressOld.equals(physicalAddressNew)) {
                physicalAddressOld.getUpEmployeeInformationCollection().remove(upEmployeeInformation);
                physicalAddressOld = em.merge(physicalAddressOld);
            }
            if (physicalAddressNew != null && !physicalAddressNew.equals(physicalAddressOld)) {
                physicalAddressNew.getUpEmployeeInformationCollection().add(upEmployeeInformation);
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
                String id = upEmployeeInformation.getEmployeeID();
                if (findUpEmployeeInformation(id) == null) {
                    throw new NonexistentEntityException("The upEmployeeInformation with id " + id + " no longer exists.");
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
            UpEmployeeInformation upEmployeeInformation;
            try {
                upEmployeeInformation = em.getReference(UpEmployeeInformation.class, id);
                upEmployeeInformation.getEmployeeID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The upEmployeeInformation with id " + id + " no longer exists.", enfe);
            }
            Person person = upEmployeeInformation.getPerson();
            if (person != null) {
                person.setUpEmployeeInformation(null);
                person = em.merge(person);
            }
            Address physicalAddress = upEmployeeInformation.getPhysicalAddress();
            if (physicalAddress != null) {
                physicalAddress.getUpEmployeeInformationCollection().remove(upEmployeeInformation);
                physicalAddress = em.merge(physicalAddress);
            }
            em.remove(upEmployeeInformation);
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

    public List<UpEmployeeInformation> findUpEmployeeInformationEntities() {
        return findUpEmployeeInformationEntities(true, -1, -1);
    }

    public List<UpEmployeeInformation> findUpEmployeeInformationEntities(int maxResults, int firstResult) {
        return findUpEmployeeInformationEntities(false, maxResults, firstResult);
    }

    private List<UpEmployeeInformation> findUpEmployeeInformationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UpEmployeeInformation.class));
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

    public UpEmployeeInformation findUpEmployeeInformation(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UpEmployeeInformation.class, id);
        } finally {
            em.close();
        }
    }

    public int getUpEmployeeInformationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UpEmployeeInformation> rt = cq.from(UpEmployeeInformation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
