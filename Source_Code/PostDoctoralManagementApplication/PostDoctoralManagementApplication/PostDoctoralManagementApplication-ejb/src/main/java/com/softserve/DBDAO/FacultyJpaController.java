/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
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
import com.softserve.DBEntities.Institution;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Faculty;
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
public class FacultyJpaController implements Serializable {

    public FacultyJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Faculty faculty) throws RollbackFailureException, Exception {
        if (faculty.getDepartmentList() == null) {
            faculty.setDepartmentList(new ArrayList<Department>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Institution institution = faculty.getInstitution();
            if (institution != null) {
                institution = em.getReference(institution.getClass(), institution.getInstitutionID());
                faculty.setInstitution(institution);
            }
            List<Department> attachedDepartmentList = new ArrayList<Department>();
            for (Department departmentListDepartmentToAttach : faculty.getDepartmentList()) {
                departmentListDepartmentToAttach = em.getReference(departmentListDepartmentToAttach.getClass(), departmentListDepartmentToAttach.getDepartmentID());
                attachedDepartmentList.add(departmentListDepartmentToAttach);
            }
            faculty.setDepartmentList(attachedDepartmentList);
            em.persist(faculty);
            if (institution != null) {
                institution.getFacultyList().add(faculty);
                institution = em.merge(institution);
            }
            for (Department departmentListDepartment : faculty.getDepartmentList()) {
                Faculty oldFacultyOfDepartmentListDepartment = departmentListDepartment.getFaculty();
                departmentListDepartment.setFaculty(faculty);
                departmentListDepartment = em.merge(departmentListDepartment);
                if (oldFacultyOfDepartmentListDepartment != null) {
                    oldFacultyOfDepartmentListDepartment.getDepartmentList().remove(departmentListDepartment);
                    oldFacultyOfDepartmentListDepartment = em.merge(oldFacultyOfDepartmentListDepartment);
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

    public void edit(Faculty faculty) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Faculty persistentFaculty = em.find(Faculty.class, faculty.getFacultyID());
            Institution institutionOld = persistentFaculty.getInstitution();
            Institution institutionNew = faculty.getInstitution();
            List<Department> departmentListOld = persistentFaculty.getDepartmentList();
            List<Department> departmentListNew = faculty.getDepartmentList();
            List<String> illegalOrphanMessages = null;
            for (Department departmentListOldDepartment : departmentListOld) {
                if (!departmentListNew.contains(departmentListOldDepartment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Department " + departmentListOldDepartment + " since its faculty field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (institutionNew != null) {
                institutionNew = em.getReference(institutionNew.getClass(), institutionNew.getInstitutionID());
                faculty.setInstitution(institutionNew);
            }
            List<Department> attachedDepartmentListNew = new ArrayList<Department>();
            for (Department departmentListNewDepartmentToAttach : departmentListNew) {
                departmentListNewDepartmentToAttach = em.getReference(departmentListNewDepartmentToAttach.getClass(), departmentListNewDepartmentToAttach.getDepartmentID());
                attachedDepartmentListNew.add(departmentListNewDepartmentToAttach);
            }
            departmentListNew = attachedDepartmentListNew;
            faculty.setDepartmentList(departmentListNew);
            faculty = em.merge(faculty);
            if (institutionOld != null && !institutionOld.equals(institutionNew)) {
                institutionOld.getFacultyList().remove(faculty);
                institutionOld = em.merge(institutionOld);
            }
            if (institutionNew != null && !institutionNew.equals(institutionOld)) {
                institutionNew.getFacultyList().add(faculty);
                institutionNew = em.merge(institutionNew);
            }
            for (Department departmentListNewDepartment : departmentListNew) {
                if (!departmentListOld.contains(departmentListNewDepartment)) {
                    Faculty oldFacultyOfDepartmentListNewDepartment = departmentListNewDepartment.getFaculty();
                    departmentListNewDepartment.setFaculty(faculty);
                    departmentListNewDepartment = em.merge(departmentListNewDepartment);
                    if (oldFacultyOfDepartmentListNewDepartment != null && !oldFacultyOfDepartmentListNewDepartment.equals(faculty)) {
                        oldFacultyOfDepartmentListNewDepartment.getDepartmentList().remove(departmentListNewDepartment);
                        oldFacultyOfDepartmentListNewDepartment = em.merge(oldFacultyOfDepartmentListNewDepartment);
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
                Long id = faculty.getFacultyID();
                if (findFaculty(id) == null) {
                    throw new NonexistentEntityException("The faculty with id " + id + " no longer exists.");
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
            Faculty faculty;
            try {
                faculty = em.getReference(Faculty.class, id);
                faculty.getFacultyID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The faculty with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Department> departmentListOrphanCheck = faculty.getDepartmentList();
            for (Department departmentListOrphanCheckDepartment : departmentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Faculty (" + faculty + ") cannot be destroyed since the Department " + departmentListOrphanCheckDepartment + " in its departmentList field has a non-nullable faculty field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Institution institution = faculty.getInstitution();
            if (institution != null) {
                institution.getFacultyList().remove(faculty);
                institution = em.merge(institution);
            }
            em.remove(faculty);
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

    public List<Faculty> findFacultyEntities() {
        return findFacultyEntities(true, -1, -1);
    }

    public List<Faculty> findFacultyEntities(int maxResults, int firstResult) {
        return findFacultyEntities(false, maxResults, firstResult);
    }

    private List<Faculty> findFacultyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Faculty.class));
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

    public Faculty findFaculty(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Faculty.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacultyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Faculty> rt = cq.from(Faculty.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Faculty> findAllFacultiesInInstitution(Institution institution)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Faculty> q = em.createQuery("SELECT f FROM Faculty f WHERE f.institution = :inst", Faculty.class).setParameter("inst", institution);
        
        return q.getResultList();
    }
}
