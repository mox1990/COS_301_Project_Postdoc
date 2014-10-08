/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.Department;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.persistence.DBEntities.Faculty;
import com.softserve.persistence.DBEntities.ResearchFellowInformation;
import java.util.ArrayList;
import java.util.List;
import com.softserve.persistence.DBEntities.EmployeeInformation;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class DepartmentJpaController implements Serializable {

    public DepartmentJpaController(EntityManager em) {
        this.emm = em;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(Department department) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), department);
    }
    public void create(EntityManager em, Department department) throws RollbackFailureException, Exception {
        if (department.getResearchFellowInformationList() == null) {
            department.setResearchFellowInformationList(new ArrayList<ResearchFellowInformation>());
        }
        if (department.getEmployeeInformationList() == null) {
            department.setEmployeeInformationList(new ArrayList<EmployeeInformation>());
        }

        em.persist(department);
       
    }
    
    public Department edit(Department department) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), department);
    }
    
    public Department edit(EntityManager em, Department department) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = department.getDepartmentID();
        if (findDepartment(id) == null) {
            throw new NonexistentEntityException("The department with id " + id + " no longer exists.");
        }
 
        return em.merge(department);        
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        
        Department department;
        try {
            department = em.getReference(Department.class, id);
            department.getDepartmentID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The department with id " + id + " no longer exists.", enfe);
        }

        em.remove(department);
           
    }

    public List<Department> findDepartmentEntities() {
        return findDepartmentEntities(true, -1, -1);
    }

    public List<Department> findDepartmentEntities(int maxResults, int firstResult) {
        return findDepartmentEntities(false, maxResults, firstResult);
    }

    private List<Department> findDepartmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Department.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public Department findDepartment(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Department.class, id);
        } finally {
            
        }
    }

    public int getDepartmentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Department> rt = cq.from(Department.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
    public List<Department> findAllDepartmentsInFaculty(Faculty faculty)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Department> q = em.createQuery("SELECT d FROM Department d WHERE d.faculty = :fac", Department.class).setParameter("fac", faculty);
        
        return q.getResultList();
}
    
}
