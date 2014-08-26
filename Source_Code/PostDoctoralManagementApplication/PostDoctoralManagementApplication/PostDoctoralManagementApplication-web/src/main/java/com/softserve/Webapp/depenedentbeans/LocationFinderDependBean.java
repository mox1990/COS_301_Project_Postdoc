/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.Institution;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.LocationManagementServiceLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
    
    /**
     * Creates a new instance of LocationFinderRequestBean
     */
    public LocationFinderDependBean() {
    }
    
    public void init()
    {
        System.out.println("Init==============================");
        try
        {
            institutions = locationManagementServiceLocal.getAllInstitutions();
        }
        catch(Exception ex)
        {
            institutions = new ArrayList<Institution>();
        }

        faculties = new ArrayList<Faculty>();
        departments = new ArrayList<Department>();        
    }
    
    public void populateFaculties(Institution institution)
    {
        System.out.println("Populate==============================");
        if(institution != null || institution.getInstitutionID() != 0)
        {
            departments = new ArrayList<Department>();
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
            departments = new ArrayList<Department>();
            faculties = new ArrayList<Faculty>();
        }
    }
    
    public void populateDepartments(Faculty faculty)
    {
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
    
    public Department getActualDepartmentEntity(Long id) throws Exception
    {
        return locationManagementServiceLocal.getDepartment(id);
    }
}
