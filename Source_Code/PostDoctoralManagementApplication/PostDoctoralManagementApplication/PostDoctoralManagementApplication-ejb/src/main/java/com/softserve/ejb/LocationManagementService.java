/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBDAO.DepartmentJpaController;
import com.softserve.DBDAO.FacultyJpaController;
import com.softserve.DBDAO.InstitutionJpaController;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.Institution;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.interceptors.TransactionInterceptor;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class, TransactionInterceptor.class})
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
            
    protected DAOFactory getDAOFactory()
    {
        return new DAOFactory(emf.createEntityManager());
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    
    @Override
    public void createInstitution(Session session, Institution institution) throws AuthenticationException, Exception
    {
        DAOFactory daoFactory = getDAOFactory();
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        InstitutionJpaController institutionJpaController = daoFactory.createInstitutionDAO();
        
        institutionJpaController.create(institution);
    }
    
    @Override
    public void createFaculty(Session session, Faculty faculty) throws AuthenticationException, Exception
    {
        DAOFactory daoFactory = getDAOFactory();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        FacultyJpaController facultyJpaController = daoFactory.createFacultyDAO();
        
        facultyJpaController.create(faculty);
    }
    
    @Override
    public void createDepartment(Session session, Department department) throws AuthenticationException, Exception
    {
        DAOFactory daoFactory = getDAOFactory();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        DepartmentJpaController departmentJpaController = daoFactory.createDepartmentDAO();
        
        departmentJpaController.create(department);
    }
    
    @Override
    public void updateInstitution(Session session, Institution institution) throws AuthenticationException, Exception
    {
        DAOFactory daoFactory = getDAOFactory();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        InstitutionJpaController institutionJpaController = daoFactory.createInstitutionDAO();
        
        institutionJpaController.edit(institution);
    }
    
    @Override
    public void updateFaculty(Session session, Faculty faculty) throws AuthenticationException, Exception
    {
        DAOFactory daoFactory = getDAOFactory();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        FacultyJpaController facultyJpaController = daoFactory.createFacultyDAO();
        
        facultyJpaController.edit(faculty);
    }
    
    @Override
    public void updateDepartment(Session session, Department department) throws AuthenticationException, Exception
    {
        DAOFactory daoFactory = getDAOFactory();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        DepartmentJpaController departmentJpaController = daoFactory.createDepartmentDAO();
        
        departmentJpaController.edit(department);
    }
        
    @Override
    public List<Institution> getAllInstitutions() throws AuthenticationException, Exception
    {
        DAOFactory daoFactory = getDAOFactory();
        InstitutionJpaController institutionJpaController = daoFactory.createInstitutionDAO();
        
        return institutionJpaController.findInstitutionEntities();
    }
    
    @Override
    public List<Faculty> getAllFacultiesInInstitution(Institution institution) throws AuthenticationException, Exception
    {
        DAOFactory daoFactory = getDAOFactory();
        FacultyJpaController facultyJpaController = daoFactory.createFacultyDAO();
        
        return facultyJpaController.findAllFacultiesInInstitution(institution);
    }
    
    @Override
    public List<Department> getAllDepartmentForFaculty(Faculty faculty) throws AuthenticationException, Exception
    {
        DAOFactory daoFactory = getDAOFactory();
        DepartmentJpaController departmentJpaController = daoFactory.createDepartmentDAO();
        
        return departmentJpaController.findAllDepartmentsInFaculty(faculty);
    }
    
    @Override
    public Institution getInstitution(Long institution) throws Exception
    {
        DAOFactory daoFactory = getDAOFactory();
        return daoFactory.createInstitutionDAO().findInstitution(institution);
    }
    
    @Override
    public Faculty getFaculty(Long faculty) throws Exception
    {
        DAOFactory daoFactory = getDAOFactory();
        return daoFactory.createFacultyDAO().findFaculty(faculty);
    }
    
    @Override
    public Department getDepartment(Long department) throws Exception
    {
        DAOFactory daoFactory = getDAOFactory();
        return daoFactory.createDepartmentDAO().findDepartment(department);
    }
}
