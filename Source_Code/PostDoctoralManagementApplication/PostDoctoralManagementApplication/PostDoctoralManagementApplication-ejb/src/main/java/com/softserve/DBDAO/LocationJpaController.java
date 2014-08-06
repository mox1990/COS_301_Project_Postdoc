/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.EmployeeInformation;
import com.softserve.DBEntities.Location;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
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
        if (location.getEmployeeInformationList() == null) {
            location.setEmployeeInformationList(new ArrayList<EmployeeInformation>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<EmployeeInformation> attachedEmployeeInformationList = new ArrayList<EmployeeInformation>();
            for (EmployeeInformation employeeInformationListEmployeeInformationToAttach : location.getEmployeeInformationList()) {
                employeeInformationListEmployeeInformationToAttach = em.getReference(employeeInformationListEmployeeInformationToAttach.getClass(), employeeInformationListEmployeeInformationToAttach.getEmployeeID());
                attachedEmployeeInformationList.add(employeeInformationListEmployeeInformationToAttach);
            }
            location.setEmployeeInformationList(attachedEmployeeInformationList);
            em.persist(location);
            for (EmployeeInformation employeeInformationListEmployeeInformation : location.getEmployeeInformationList()) {
                Location oldLocationOfEmployeeInformationListEmployeeInformation = employeeInformationListEmployeeInformation.getLocation();
                employeeInformationListEmployeeInformation.setLocation(location);
                employeeInformationListEmployeeInformation = em.merge(employeeInformationListEmployeeInformation);
                if (oldLocationOfEmployeeInformationListEmployeeInformation != null) {
                    oldLocationOfEmployeeInformationListEmployeeInformation.getEmployeeInformationList().remove(employeeInformationListEmployeeInformation);
                    oldLocationOfEmployeeInformationListEmployeeInformation = em.merge(oldLocationOfEmployeeInformationListEmployeeInformation);
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

    public void edit(Location location) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Location persistentLocation = em.find(Location.class, location.getLocationID());
            List<EmployeeInformation> employeeInformationListOld = persistentLocation.getEmployeeInformationList();
            List<EmployeeInformation> employeeInformationListNew = location.getEmployeeInformationList();
            List<EmployeeInformation> attachedEmployeeInformationListNew = new ArrayList<EmployeeInformation>();
            for (EmployeeInformation employeeInformationListNewEmployeeInformationToAttach : employeeInformationListNew) {
                employeeInformationListNewEmployeeInformationToAttach = em.getReference(employeeInformationListNewEmployeeInformationToAttach.getClass(), employeeInformationListNewEmployeeInformationToAttach.getEmployeeID());
                attachedEmployeeInformationListNew.add(employeeInformationListNewEmployeeInformationToAttach);
            }
            employeeInformationListNew = attachedEmployeeInformationListNew;
            location.setEmployeeInformationList(employeeInformationListNew);
            location = em.merge(location);
            for (EmployeeInformation employeeInformationListOldEmployeeInformation : employeeInformationListOld) {
                if (!employeeInformationListNew.contains(employeeInformationListOldEmployeeInformation)) {
                    employeeInformationListOldEmployeeInformation.setLocation(null);
                    employeeInformationListOldEmployeeInformation = em.merge(employeeInformationListOldEmployeeInformation);
                }
            }
            for (EmployeeInformation employeeInformationListNewEmployeeInformation : employeeInformationListNew) {
                if (!employeeInformationListOld.contains(employeeInformationListNewEmployeeInformation)) {
                    Location oldLocationOfEmployeeInformationListNewEmployeeInformation = employeeInformationListNewEmployeeInformation.getLocation();
                    employeeInformationListNewEmployeeInformation.setLocation(location);
                    employeeInformationListNewEmployeeInformation = em.merge(employeeInformationListNewEmployeeInformation);
                    if (oldLocationOfEmployeeInformationListNewEmployeeInformation != null && !oldLocationOfEmployeeInformationListNewEmployeeInformation.equals(location)) {
                        oldLocationOfEmployeeInformationListNewEmployeeInformation.getEmployeeInformationList().remove(employeeInformationListNewEmployeeInformation);
                        oldLocationOfEmployeeInformationListNewEmployeeInformation = em.merge(oldLocationOfEmployeeInformationListNewEmployeeInformation);
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

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
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
            List<EmployeeInformation> employeeInformationList = location.getEmployeeInformationList();
            for (EmployeeInformation employeeInformationListEmployeeInformation : employeeInformationList) {
                employeeInformationListEmployeeInformation.setLocation(null);
                employeeInformationListEmployeeInformation = em.merge(employeeInformationListEmployeeInformation);
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
    
    public List<String> getAllDepartmentsInFacultyInInstitution(String institution, String faculty) throws Exception
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<String> q = em.createQuery("SELECT l.department FROM Location l WHERE l.institution = :inst AND l.faculty = :fac", String.class).setParameter("inst", institution).setParameter("fac", faculty);
        
        return q.getResultList();
    }
    
    public List<String> getAllFacultiesInInstitution(String institution) throws Exception
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<String> q = em.createQuery("SELECT DISTINCT l.faculty FROM Location l WHERE l.institution = :inst", String.class).setParameter("inst", institution);
        
        return q.getResultList();
    }
    
    public List<String> getAllInstitutions() throws Exception
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<String> q = em.createQuery("SELECT DISTINCT l.institution FROM Location l", String.class);
        
        return q.getResultList();
    }
    
    public Location getLocationFromComponents(String institution, String faculty, String department) throws Exception
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Location> q = em.createQuery("SELECT l FROM Location l WHERE l.institution = :inst AND l.faculty = :fac AND l.department = :dep", Location.class).setParameter("inst", institution).setParameter("fac", faculty).setParameter("dep", department);
        
        return q.getSingleResult();
    }
    
}
