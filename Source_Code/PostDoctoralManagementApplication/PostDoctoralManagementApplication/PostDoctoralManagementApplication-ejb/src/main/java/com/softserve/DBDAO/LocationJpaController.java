/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Location;
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
public class LocationJpaController implements Serializable {

    public LocationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Location location) throws RollbackFailureException, Exception {
        if (location.getApplicationCollection() == null) {
            location.setApplicationCollection(new ArrayList<Application>());
        }
        if (location.getPersonCollection() == null) {
            location.setPersonCollection(new ArrayList<Person>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Application> attachedApplicationCollection = new ArrayList<Application>();
            for (Application applicationCollectionApplicationToAttach : location.getApplicationCollection()) {
                applicationCollectionApplicationToAttach = em.getReference(applicationCollectionApplicationToAttach.getClass(), applicationCollectionApplicationToAttach.getApplicationID());
                attachedApplicationCollection.add(applicationCollectionApplicationToAttach);
            }
            location.setApplicationCollection(attachedApplicationCollection);
            Collection<Person> attachedPersonCollection = new ArrayList<Person>();
            for (Person personCollectionPersonToAttach : location.getPersonCollection()) {
                personCollectionPersonToAttach = em.getReference(personCollectionPersonToAttach.getClass(), personCollectionPersonToAttach.getSystemID());
                attachedPersonCollection.add(personCollectionPersonToAttach);
            }
            location.setPersonCollection(attachedPersonCollection);
            em.persist(location);
            for (Application applicationCollectionApplication : location.getApplicationCollection()) {
                Location oldLocationIDOfApplicationCollectionApplication = applicationCollectionApplication.getLocationID();
                applicationCollectionApplication.setLocationID(location);
                applicationCollectionApplication = em.merge(applicationCollectionApplication);
                if (oldLocationIDOfApplicationCollectionApplication != null) {
                    oldLocationIDOfApplicationCollectionApplication.getApplicationCollection().remove(applicationCollectionApplication);
                    oldLocationIDOfApplicationCollectionApplication = em.merge(oldLocationIDOfApplicationCollectionApplication);
                }
            }
            for (Person personCollectionPerson : location.getPersonCollection()) {
                Location oldLocationIDOfPersonCollectionPerson = personCollectionPerson.getLocationID();
                personCollectionPerson.setLocationID(location);
                personCollectionPerson = em.merge(personCollectionPerson);
                if (oldLocationIDOfPersonCollectionPerson != null) {
                    oldLocationIDOfPersonCollectionPerson.getPersonCollection().remove(personCollectionPerson);
                    oldLocationIDOfPersonCollectionPerson = em.merge(oldLocationIDOfPersonCollectionPerson);
                }
            }
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

    public void edit(Location location) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Location persistentLocation = em.find(Location.class, location.getLocationID());
            Collection<Application> applicationCollectionOld = persistentLocation.getApplicationCollection();
            Collection<Application> applicationCollectionNew = location.getApplicationCollection();
            Collection<Person> personCollectionOld = persistentLocation.getPersonCollection();
            Collection<Person> personCollectionNew = location.getPersonCollection();
            List<String> illegalOrphanMessages = null;
            for (Application applicationCollectionOldApplication : applicationCollectionOld) {
                if (!applicationCollectionNew.contains(applicationCollectionOldApplication)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Application " + applicationCollectionOldApplication + " since its locationID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Application> attachedApplicationCollectionNew = new ArrayList<Application>();
            for (Application applicationCollectionNewApplicationToAttach : applicationCollectionNew) {
                applicationCollectionNewApplicationToAttach = em.getReference(applicationCollectionNewApplicationToAttach.getClass(), applicationCollectionNewApplicationToAttach.getApplicationID());
                attachedApplicationCollectionNew.add(applicationCollectionNewApplicationToAttach);
            }
            applicationCollectionNew = attachedApplicationCollectionNew;
            location.setApplicationCollection(applicationCollectionNew);
            Collection<Person> attachedPersonCollectionNew = new ArrayList<Person>();
            for (Person personCollectionNewPersonToAttach : personCollectionNew) {
                personCollectionNewPersonToAttach = em.getReference(personCollectionNewPersonToAttach.getClass(), personCollectionNewPersonToAttach.getSystemID());
                attachedPersonCollectionNew.add(personCollectionNewPersonToAttach);
            }
            personCollectionNew = attachedPersonCollectionNew;
            location.setPersonCollection(personCollectionNew);
            location = em.merge(location);
            for (Application applicationCollectionNewApplication : applicationCollectionNew) {
                if (!applicationCollectionOld.contains(applicationCollectionNewApplication)) {
                    Location oldLocationIDOfApplicationCollectionNewApplication = applicationCollectionNewApplication.getLocationID();
                    applicationCollectionNewApplication.setLocationID(location);
                    applicationCollectionNewApplication = em.merge(applicationCollectionNewApplication);
                    if (oldLocationIDOfApplicationCollectionNewApplication != null && !oldLocationIDOfApplicationCollectionNewApplication.equals(location)) {
                        oldLocationIDOfApplicationCollectionNewApplication.getApplicationCollection().remove(applicationCollectionNewApplication);
                        oldLocationIDOfApplicationCollectionNewApplication = em.merge(oldLocationIDOfApplicationCollectionNewApplication);
                    }
                }
            }
            for (Person personCollectionOldPerson : personCollectionOld) {
                if (!personCollectionNew.contains(personCollectionOldPerson)) {
                    personCollectionOldPerson.setLocationID(null);
                    personCollectionOldPerson = em.merge(personCollectionOldPerson);
                }
            }
            for (Person personCollectionNewPerson : personCollectionNew) {
                if (!personCollectionOld.contains(personCollectionNewPerson)) {
                    Location oldLocationIDOfPersonCollectionNewPerson = personCollectionNewPerson.getLocationID();
                    personCollectionNewPerson.setLocationID(location);
                    personCollectionNewPerson = em.merge(personCollectionNewPerson);
                    if (oldLocationIDOfPersonCollectionNewPerson != null && !oldLocationIDOfPersonCollectionNewPerson.equals(location)) {
                        oldLocationIDOfPersonCollectionNewPerson.getPersonCollection().remove(personCollectionNewPerson);
                        oldLocationIDOfPersonCollectionNewPerson = em.merge(oldLocationIDOfPersonCollectionNewPerson);
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
                Long id = location.getLocationID();
                if (findLocation(id) == null) {
                    throw new NonexistentEntityException("The location with id " + id + " no longer exists.");
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
            Location location;
            try {
                location = em.getReference(Location.class, id);
                location.getLocationID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The location with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Application> applicationCollectionOrphanCheck = location.getApplicationCollection();
            for (Application applicationCollectionOrphanCheckApplication : applicationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Location (" + location + ") cannot be destroyed since the Application " + applicationCollectionOrphanCheckApplication + " in its applicationCollection field has a non-nullable locationID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Person> personCollection = location.getPersonCollection();
            for (Person personCollectionPerson : personCollection) {
                personCollectionPerson.setLocationID(null);
                personCollectionPerson = em.merge(personCollectionPerson);
            }
            em.remove(location);
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

    public List<Location> findLocationEntities() {
        return findLocationEntities(true, -1, -1);
    }

    public List<Location> findLocationEntities(int maxResults, int firstResult) {
        return findLocationEntities(false, maxResults, firstResult);
    }

    private List<Location> findLocationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Location.class));
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

    public Location findLocation(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Location.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Location> rt = cq.from(Location.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
