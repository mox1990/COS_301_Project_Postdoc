/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.Institution;
import com.softserve.ejb.LocationManagementServiceLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "locationFinderRequestBean")
@Dependent
public class LocationFinderDependBean implements Serializable {    
    @EJB
    private LocationManagementServiceLocal locationManagementServiceLocal;   
   
    private List<Institution> institutions;
    private List<Faculty> faculties;    
    private List<Department> departments;
    
    private Institution selectedInstitution;
    private Faculty selectedFaculty;
    
    /**
     * Creates a new instance of LocationFinderRequestBean
     */
    public LocationFinderDependBean() {
    }
    
    public void init(Department department)
    {
        System.out.println("Init==============================");
        try
        {
            
            if(department == null)
            {
                selectedFaculty = new Faculty((long) 0);
                selectedInstitution = new Institution((long) 0);
                faculties = new ArrayList<Faculty>();
                departments = new ArrayList<Department>();
            }
            else
            {
                departments = department.getFaculty().getDepartmentList();
                faculties = department.getFaculty().getInstitution().getFacultyList();
                selectedFaculty = department.getFaculty();
                selectedInstitution = department.getFaculty().getInstitution();
            }
            institutions = locationManagementServiceLocal.getAllInstitutions();
        }
        catch(Exception ex)
        {
            institutions = new ArrayList<Institution>();
        }

               
    }
    
    public void populateFaculties(Institution institution)
    {
        System.out.println("Populate Faculties==============================");
        
        if(institution != null && institution.getInstitutionID() != 0)
        { 
            try
            {   
                faculties = locationManagementServiceLocal.getAllFacultiesInInstitution(institution);
            }
            catch(Exception ex)
            {
                faculties = new ArrayList<Faculty>();
            }
        }
        else
        {
            faculties = new ArrayList<Faculty>();
        }
        
        departments = new ArrayList<Department>();
        selectedFaculty = new Faculty((long) 0);
    }
    
    public void populateDepartments(Faculty faculty)
    {
        System.out.println("Populate Departments==============================");
        if(faculty != null && faculty.getFacultyID() != 0)
        {
            try
            {
                departments = locationManagementServiceLocal.getAllDepartmentForFaculty(faculty);
            }
            catch(Exception ex)
            {
                departments = new ArrayList<Department>();
            }
        }
        else
        {
            departments = new ArrayList<Department>();
        }
    }
    
    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }
    
    public void hookTest()
    {
        System.out.println("Action called =================");
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    public Faculty getSelectedFaculty() {
        return selectedFaculty;
    }

    public void setSelectedFaculty(Faculty selectedFaculty) {
        this.selectedFaculty = selectedFaculty;
    }

    public Institution getSelectedInstitution() {
        return selectedInstitution;
    }

    public void setSelectedInstitution(Institution selectedInstitution) {
        this.selectedInstitution = selectedInstitution;
    }
        
    public Department getActualDepartmentEntity(Long id) throws Exception
    {
        return locationManagementServiceLocal.getDepartment(id);
    }
}
