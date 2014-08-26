/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.DepartmentJpaController;
import com.softserve.DBDAO.FacultyJpaController;
import com.softserve.DBDAO.InstitutionJpaController;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.Institution;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class LocationManagementService implements LocationManagementServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private UserGatewayLocal userGatewayLocal;
    
    protected UserGatewayLocal getUserGatewayServiceEJB()
    {
        return userGatewayLocal;
    }

    public LocationManagementService() {
    }

    public LocationManagementService(EntityManagerFactory emf) {
        this.emf = emf;
    }
            
    protected InstitutionJpaController getInstitutionDAO()
    {
        return new InstitutionJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected FacultyJpaController getFacultyDAO()
    {
        return new FacultyJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected DepartmentJpaController getDepartmentDAO()
    {
        return new DepartmentJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    
    @Override
    public void createInstitution(Session session, Institution institution) throws AuthenticationException, Exception
    {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        InstitutionJpaController institutionJpaController = getInstitutionDAO();
        
        institutionJpaController.create(institution);
    }
    
    @Override
    public void createFaculty(Session session, Faculty faculty) throws AuthenticationException, Exception
    {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        FacultyJpaController facultyJpaController = getFacultyDAO();
        
        facultyJpaController.create(faculty);
    }
    
    @Override
    public void createDepartment(Session session, Department department) throws AuthenticationException, Exception
    {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        DepartmentJpaController departmentJpaController = getDepartmentDAO();
        
        departmentJpaController.create(department);
    }
    
    @Override
    public void updateInstitution(Session session, Institution institution) throws AuthenticationException, Exception
    {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        InstitutionJpaController institutionJpaController = getInstitutionDAO();
        
        institutionJpaController.edit(institution);
    }
    
    @Override
    public void updateFaculty(Session session, Faculty faculty) throws AuthenticationException, Exception
    {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        FacultyJpaController facultyJpaController = getFacultyDAO();
        
        facultyJpaController.edit(faculty);
    }
    
    @Override
    public void updateDepartment(Session session, Department department) throws AuthenticationException, Exception
    {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        DepartmentJpaController departmentJpaController = getDepartmentDAO();
        
        departmentJpaController.edit(department);
    }
        
    @Override
    public List<Institution> getAllInstitutions() throws AuthenticationException, Exception
    {
        InstitutionJpaController institutionJpaController = getInstitutionDAO();
        
        return institutionJpaController.findInstitutionEntities();
    }
    
    @Override
    public List<Faculty> getAllFacultiesInInstitution(Institution institution) throws AuthenticationException, Exception
    {
        FacultyJpaController facultyJpaController = getFacultyDAO();
        
        return facultyJpaController.findAllFacultiesInInstitution(institution);
    }
    
    @Override
    public List<Department> getAllDepartmentForFaculty(Faculty faculty) throws AuthenticationException, Exception
    {
        DepartmentJpaController departmentJpaController = getDepartmentDAO();
        
        return departmentJpaController.findAllDepartmentsInFaculty(faculty);
    }
    
    @Override
    public Institution getInstitution(Long institution) throws Exception
    {
        return getInstitutionDAO().findInstitution(institution);
    }
    
    @Override
    public Faculty getFaculty(Long faculty) throws Exception
    {
        return getFacultyDAO().findFaculty(faculty);
    }
    
    @Override
    public Department getDepartment(Long department) throws Exception
    {
        return getDepartmentDAO().findDepartment(department);
    }
}
