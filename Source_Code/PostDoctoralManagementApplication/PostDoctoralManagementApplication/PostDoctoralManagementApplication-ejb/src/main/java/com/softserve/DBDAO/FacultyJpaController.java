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

    public FacultyJpaController(EntityManager emm) {
        this.emm = emm;
    }
    
    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(Faculty faculty) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), faculty);
    }
    
    public void create(EntityManager em, Faculty faculty) throws RollbackFailureException, Exception 
    {
        if (faculty.getDepartmentList() == null) {
            faculty.setDepartmentList(new ArrayList<Department>());
        }
        
        em.persist(faculty);           
    }
    
    public Faculty edit(Faculty faculty) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), faculty);
    }

    public Faculty edit(EntityManager em, Faculty faculty) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = faculty.getFacultyID();
        if (findFaculty(id) == null) {
            throw new NonexistentEntityException("The faculty with id " + id + " no longer exists.");
        }
        
        Faculty persistentFaculty = em.find(Faculty.class, faculty.getFacultyID());

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

        return em.merge(faculty);
        
    }
    
    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

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

        em.remove(faculty);
            
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
           
        }
    }

    public Faculty findFaculty(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Faculty.class, id);
        } finally {
            
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
            
        }
    }
    
    public List<Faculty> findAllFacultiesInInstitution(Institution institution)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Faculty> q = em.createQuery("SELECT f FROM Faculty f WHERE f.institution = :inst", Faculty.class).setParameter("inst", institution);
        
        return q.getResultList();
    }
}
