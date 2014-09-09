/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Department;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.ResearchFellowInformation;
import java.util.ArrayList;
import java.util.List;
import com.softserve.DBEntities.EmployeeInformation;
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

        Faculty faculty = department.getFaculty();
        if (faculty != null) {
            faculty = em.getReference(faculty.getClass(), faculty.getFacultyID());
            department.setFaculty(faculty);
        }
        List<ResearchFellowInformation> attachedResearchFellowInformationList = new ArrayList<ResearchFellowInformation>();
        for (ResearchFellowInformation researchFellowInformationListResearchFellowInformationToAttach : department.getResearchFellowInformationList()) {
            researchFellowInformationListResearchFellowInformationToAttach = em.getReference(researchFellowInformationListResearchFellowInformationToAttach.getClass(), researchFellowInformationListResearchFellowInformationToAttach.getSystemAssignedID());
            attachedResearchFellowInformationList.add(researchFellowInformationListResearchFellowInformationToAttach);
        }
        department.setResearchFellowInformationList(attachedResearchFellowInformationList);
        List<EmployeeInformation> attachedEmployeeInformationList = new ArrayList<EmployeeInformation>();
        for (EmployeeInformation employeeInformationListEmployeeInformationToAttach : department.getEmployeeInformationList()) {
            employeeInformationListEmployeeInformationToAttach = em.getReference(employeeInformationListEmployeeInformationToAttach.getClass(), employeeInformationListEmployeeInformationToAttach.getEmployeeID());
            attachedEmployeeInformationList.add(employeeInformationListEmployeeInformationToAttach);
        }
        department.setEmployeeInformationList(attachedEmployeeInformationList);
        em.persist(department);
        if (faculty != null) {
            faculty.getDepartmentList().add(department);
            faculty = em.merge(faculty);
        }
        for (ResearchFellowInformation researchFellowInformationListResearchFellowInformation : department.getResearchFellowInformationList()) {
            Department oldDepartmentOfResearchFellowInformationListResearchFellowInformation = researchFellowInformationListResearchFellowInformation.getDepartment();
            researchFellowInformationListResearchFellowInformation.setDepartment(department);
            researchFellowInformationListResearchFellowInformation = em.merge(researchFellowInformationListResearchFellowInformation);
            if (oldDepartmentOfResearchFellowInformationListResearchFellowInformation != null) {
                oldDepartmentOfResearchFellowInformationListResearchFellowInformation.getResearchFellowInformationList().remove(researchFellowInformationListResearchFellowInformation);
                oldDepartmentOfResearchFellowInformationListResearchFellowInformation = em.merge(oldDepartmentOfResearchFellowInformationListResearchFellowInformation);
            }
        }
        for (EmployeeInformation employeeInformationListEmployeeInformation : department.getEmployeeInformationList()) {
            Department oldDepartmentOfEmployeeInformationListEmployeeInformation = employeeInformationListEmployeeInformation.getDepartment();
            employeeInformationListEmployeeInformation.setDepartment(department);
            employeeInformationListEmployeeInformation = em.merge(employeeInformationListEmployeeInformation);
            if (oldDepartmentOfEmployeeInformationListEmployeeInformation != null) {
                oldDepartmentOfEmployeeInformationListEmployeeInformation.getEmployeeInformationList().remove(employeeInformationListEmployeeInformation);
                oldDepartmentOfEmployeeInformationListEmployeeInformation = em.merge(oldDepartmentOfEmployeeInformationListEmployeeInformation);
            }
        }
            
    }
    
    public void edit(Department department) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        edit(getEntityManager(), department);
    }
    
    public void edit(EntityManager em, Department department) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = department.getDepartmentID();
        if (findDepartment(id) == null) {
            throw new NonexistentEntityException("The department with id " + id + " no longer exists.");
        }
        
        Department persistentDepartment = em.find(Department.class, department.getDepartmentID());
        Faculty facultyOld = persistentDepartment.getFaculty();
        Faculty facultyNew = department.getFaculty();
        List<ResearchFellowInformation> researchFellowInformationListOld = persistentDepartment.getResearchFellowInformationList();
        List<ResearchFellowInformation> researchFellowInformationListNew = department.getResearchFellowInformationList();
        List<EmployeeInformation> employeeInformationListOld = persistentDepartment.getEmployeeInformationList();
        List<EmployeeInformation> employeeInformationListNew = department.getEmployeeInformationList();
        if (facultyNew != null) {
            facultyNew = em.getReference(facultyNew.getClass(), facultyNew.getFacultyID());
            department.setFaculty(facultyNew);
        }
        List<ResearchFellowInformation> attachedResearchFellowInformationListNew = new ArrayList<ResearchFellowInformation>();
        for (ResearchFellowInformation researchFellowInformationListNewResearchFellowInformationToAttach : researchFellowInformationListNew) {
            researchFellowInformationListNewResearchFellowInformationToAttach = em.getReference(researchFellowInformationListNewResearchFellowInformationToAttach.getClass(), researchFellowInformationListNewResearchFellowInformationToAttach.getSystemAssignedID());
            attachedResearchFellowInformationListNew.add(researchFellowInformationListNewResearchFellowInformationToAttach);
        }
        researchFellowInformationListNew = attachedResearchFellowInformationListNew;
        department.setResearchFellowInformationList(researchFellowInformationListNew);
        List<EmployeeInformation> attachedEmployeeInformationListNew = new ArrayList<EmployeeInformation>();
        for (EmployeeInformation employeeInformationListNewEmployeeInformationToAttach : employeeInformationListNew) {
            employeeInformationListNewEmployeeInformationToAttach = em.getReference(employeeInformationListNewEmployeeInformationToAttach.getClass(), employeeInformationListNewEmployeeInformationToAttach.getEmployeeID());
            attachedEmployeeInformationListNew.add(employeeInformationListNewEmployeeInformationToAttach);
        }
        employeeInformationListNew = attachedEmployeeInformationListNew;
        department.setEmployeeInformationList(employeeInformationListNew);
        department = em.merge(department);
        if (facultyOld != null && !facultyOld.equals(facultyNew)) {
            facultyOld.getDepartmentList().remove(department);
            facultyOld = em.merge(facultyOld);
        }
        if (facultyNew != null && !facultyNew.equals(facultyOld)) {
            facultyNew.getDepartmentList().add(department);
            facultyNew = em.merge(facultyNew);
        }
        for (ResearchFellowInformation researchFellowInformationListOldResearchFellowInformation : researchFellowInformationListOld) {
            if (!researchFellowInformationListNew.contains(researchFellowInformationListOldResearchFellowInformation)) {
                researchFellowInformationListOldResearchFellowInformation.setDepartment(null);
                researchFellowInformationListOldResearchFellowInformation = em.merge(researchFellowInformationListOldResearchFellowInformation);
            }
        }
        for (ResearchFellowInformation researchFellowInformationListNewResearchFellowInformation : researchFellowInformationListNew) {
            if (!researchFellowInformationListOld.contains(researchFellowInformationListNewResearchFellowInformation)) {
                Department oldDepartmentOfResearchFellowInformationListNewResearchFellowInformation = researchFellowInformationListNewResearchFellowInformation.getDepartment();
                researchFellowInformationListNewResearchFellowInformation.setDepartment(department);
                researchFellowInformationListNewResearchFellowInformation = em.merge(researchFellowInformationListNewResearchFellowInformation);
                if (oldDepartmentOfResearchFellowInformationListNewResearchFellowInformation != null && !oldDepartmentOfResearchFellowInformationListNewResearchFellowInformation.equals(department)) {
                    oldDepartmentOfResearchFellowInformationListNewResearchFellowInformation.getResearchFellowInformationList().remove(researchFellowInformationListNewResearchFellowInformation);
                    oldDepartmentOfResearchFellowInformationListNewResearchFellowInformation = em.merge(oldDepartmentOfResearchFellowInformationListNewResearchFellowInformation);
                }
            }
        }
        for (EmployeeInformation employeeInformationListOldEmployeeInformation : employeeInformationListOld) {
            if (!employeeInformationListNew.contains(employeeInformationListOldEmployeeInformation)) {
                employeeInformationListOldEmployeeInformation.setDepartment(null);
                employeeInformationListOldEmployeeInformation = em.merge(employeeInformationListOldEmployeeInformation);
            }
        }
        for (EmployeeInformation employeeInformationListNewEmployeeInformation : employeeInformationListNew) {
            if (!employeeInformationListOld.contains(employeeInformationListNewEmployeeInformation)) {
                Department oldDepartmentOfEmployeeInformationListNewEmployeeInformation = employeeInformationListNewEmployeeInformation.getDepartment();
                employeeInformationListNewEmployeeInformation.setDepartment(department);
                employeeInformationListNewEmployeeInformation = em.merge(employeeInformationListNewEmployeeInformation);
                if (oldDepartmentOfEmployeeInformationListNewEmployeeInformation != null && !oldDepartmentOfEmployeeInformationListNewEmployeeInformation.equals(department)) {
                    oldDepartmentOfEmployeeInformationListNewEmployeeInformation.getEmployeeInformationList().remove(employeeInformationListNewEmployeeInformation);
                    oldDepartmentOfEmployeeInformationListNewEmployeeInformation = em.merge(oldDepartmentOfEmployeeInformationListNewEmployeeInformation);
                }
            }
        }

               
            
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
        Faculty faculty = department.getFaculty();
        if (faculty != null) {
            faculty.getDepartmentList().remove(department);
            faculty = em.merge(faculty);
        }
        List<ResearchFellowInformation> researchFellowInformationList = department.getResearchFellowInformationList();
        for (ResearchFellowInformation researchFellowInformationListResearchFellowInformation : researchFellowInformationList) {
            researchFellowInformationListResearchFellowInformation.setDepartment(null);
            researchFellowInformationListResearchFellowInformation = em.merge(researchFellowInformationListResearchFellowInformation);
        }
        List<EmployeeInformation> employeeInformationList = department.getEmployeeInformationList();
        for (EmployeeInformation employeeInformationListEmployeeInformation : employeeInformationList) {
            employeeInformationListEmployeeInformation.setDepartment(null);
            employeeInformationListEmployeeInformation = em.merge(employeeInformationListEmployeeInformation);
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
