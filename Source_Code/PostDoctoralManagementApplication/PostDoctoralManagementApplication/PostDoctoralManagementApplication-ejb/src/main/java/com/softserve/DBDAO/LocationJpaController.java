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
        if (location.getApplicationList() == null) {
            location.setApplicationList(new ArrayList<Application>());
        }
        if (location.getPersonList() == null) {
            location.setPersonList(new ArrayList<Person>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Application> attachedApplicationList = new ArrayList<Application>();
            for (Application applicationListApplicationToAttach : location.getApplicationList()) {
                applicationListApplicationToAttach = em.getReference(applicationListApplicationToAttach.getClass(), applicationListApplicationToAttach.getApplicationID());
                attachedApplicationList.add(applicationListApplicationToAttach);
            }
            location.setApplicationList(attachedApplicationList);
            List<Person> attachedPersonList = new ArrayList<Person>();
            for (Person personListPersonToAttach : location.getPersonList()) {
                personListPersonToAttach = em.getReference(personListPersonToAttach.getClass(), personListPersonToAttach.getSystemID());
                attachedPersonList.add(personListPersonToAttach);
            }
            location.setPersonList(attachedPersonList);
            em.persist(location);
            for (Application applicationListApplication : location.getApplicationList()) {
                Location oldLocationIDOfApplicationListApplication = applicationListApplication.getLocationID();
                applicationListApplication.setLocationID(location);
                applicationListApplication = em.merge(applicationListApplication);
                if (oldLocationIDOfApplicationListApplication != null) {
                    oldLocationIDOfApplicationListApplication.getApplicationList().remove(applicationListApplication);
                    oldLocationIDOfApplicationListApplication = em.merge(oldLocationIDOfApplicationListApplication);
                }
            }
            for (Person personListPerson : location.getPersonList()) {
                Location oldLocationIDOfPersonListPerson = personListPerson.getLocationID();
                personListPerson.setLocationID(location);
                personListPerson = em.merge(personListPerson);
                if (oldLocationIDOfPersonListPerson != null) {
                    oldLocationIDOfPersonListPerson.getPersonList().remove(personListPerson);
                    oldLocationIDOfPersonListPerson = em.merge(oldLocationIDOfPersonListPerson);
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
            List<Application> applicationListOld = persistentLocation.getApplicationList();
            List<Application> applicationListNew = location.getApplicationList();
            List<Person> personListOld = persistentLocation.getPersonList();
            List<Person> personListNew = location.getPersonList();
            List<String> illegalOrphanMessages = null;
            for (Application applicationListOldApplication : applicationListOld) {
                if (!applicationListNew.contains(applicationListOldApplication)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Application " + applicationListOldApplication + " since its locationID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Application> attachedApplicationListNew = new ArrayList<Application>();
            for (Application applicationListNewApplicationToAttach : applicationListNew) {
                applicationListNewApplicationToAttach = em.getReference(applicationListNewApplicationToAttach.getClass(), applicationListNewApplicationToAttach.getApplicationID());
                attachedApplicationListNew.add(applicationListNewApplicationToAttach);
            }
            applicationListNew = attachedApplicationListNew;
            location.setApplicationList(applicationListNew);
            List<Person> attachedPersonListNew = new ArrayList<Person>();
            for (Person personListNewPersonToAttach : personListNew) {
                personListNewPersonToAttach = em.getReference(personListNewPersonToAttach.getClass(), personListNewPersonToAttach.getSystemID());
                attachedPersonListNew.add(personListNewPersonToAttach);
            }
            personListNew = attachedPersonListNew;
            location.setPersonList(personListNew);
            location = em.merge(location);
            for (Application applicationListNewApplication : applicationListNew) {
                if (!applicationListOld.contains(applicationListNewApplication)) {
                    Location oldLocationIDOfApplicationListNewApplication = applicationListNewApplication.getLocationID();
                    applicationListNewApplication.setLocationID(location);
                    applicationListNewApplication = em.merge(applicationListNewApplication);
                    if (oldLocationIDOfApplicationListNewApplication != null && !oldLocationIDOfApplicationListNewApplication.equals(location)) {
                        oldLocationIDOfApplicationListNewApplication.getApplicationList().remove(applicationListNewApplication);
                        oldLocationIDOfApplicationListNewApplication = em.merge(oldLocationIDOfApplicationListNewApplication);
                    }
                }
            }
            for (Person personListOldPerson : personListOld) {
                if (!personListNew.contains(personListOldPerson)) {
                    personListOldPerson.setLocationID(null);
                    personListOldPerson = em.merge(personListOldPerson);
                }
            }
            for (Person personListNewPerson : personListNew) {
                if (!personListOld.contains(personListNewPerson)) {
                    Location oldLocationIDOfPersonListNewPerson = personListNewPerson.getLocationID();
                    personListNewPerson.setLocationID(location);
                    personListNewPerson = em.merge(personListNewPerson);
                    if (oldLocationIDOfPersonListNewPerson != null && !oldLocationIDOfPersonListNewPerson.equals(location)) {
                        oldLocationIDOfPersonListNewPerson.getPersonList().remove(personListNewPerson);
                        oldLocationIDOfPersonListNewPerson = em.merge(oldLocationIDOfPersonListNewPerson);
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
            List<Application> applicationListOrphanCheck = location.getApplicationList();
            for (Application applicationListOrphanCheckApplication : applicationListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Location (" + location + ") cannot be destroyed since the Application " + applicationListOrphanCheckApplication + " in its applicationList field has a non-nullable locationID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Person> personList = location.getPersonList();
            for (Person personListPerson : personList) {
                personListPerson.setLocationID(null);
                personListPerson = em.merge(personListPerson);
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
