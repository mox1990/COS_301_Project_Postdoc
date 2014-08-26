/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.Institution;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface LocationManagementServiceLocal {
    public void createInstitution(Session session, Institution institution) throws AuthenticationException, Exception;
    public void createFaculty(Session session, Faculty faculty) throws AuthenticationException, Exception;
    public void createDepartment(Session session, Department department) throws AuthenticationException, Exception;
    public void updateInstitution(Session session, Institution institution) throws AuthenticationException, Exception;
    public void updateFaculty(Session session, Faculty faculty) throws AuthenticationException, Exception;
    public void updateDepartment(Session session, Department department) throws AuthenticationException, Exception;
    public List<Institution> getAllInstitutions() throws AuthenticationException, Exception;
    public List<Faculty> getAllFacultiesInInstitution(Institution institution) throws AuthenticationException, Exception;
    public List<Department> getAllDepartmentForFaculty(Faculty faculty) throws AuthenticationException, Exception;
    public Institution getInstitution(Long institution) throws Exception;
    public Faculty getFaculty(Long faculty) throws Exception;
    public Department getDepartment(Long department) throws Exception;
}
