/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.nonapplicationservices;

import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBDAO.DepartmentJpaController;
import com.softserve.DBDAO.FacultyJpaController;
import com.softserve.DBDAO.InstitutionJpaController;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.Institution;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import com.softserve.transactioncontrollers.TransactionController;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
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
            
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }

    protected TransactionController getTransactionController()
    {
        return new TransactionController(emf);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public void createInstitution(Session session, Institution institution) throws AuthenticationException, Exception
    {
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            dAOFactory.createInstitutionDAO().create(institution);

            transactionController.CommitTransaction();
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        } 
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public void createFaculty(Session session, Faculty faculty) throws AuthenticationException, Exception
    {        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            dAOFactory.createFacultyDAO().create(faculty);

            transactionController.CommitTransaction();
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        } 
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public void createDepartment(Session session, Department department) throws AuthenticationException, Exception
    {        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            dAOFactory.createDepartmentDAO().create(department);

            transactionController.CommitTransaction();
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public void updateInstitution(Session session, Institution institution) throws AuthenticationException, Exception
    {   
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            dAOFactory.createInstitutionDAO().edit(institution);

            transactionController.CommitTransaction();
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        } 
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public void updateFaculty(Session session, Faculty faculty) throws AuthenticationException, Exception
    { 
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            dAOFactory.createFacultyDAO().edit(faculty);

            transactionController.CommitTransaction();
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        } 
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public void updateDepartment(Session session, Department department) throws AuthenticationException, Exception
    {
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            dAOFactory.createDepartmentDAO().edit(department);

            transactionController.CommitTransaction();
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        }
    }
    


    @Override
    public List<Institution> getAllInstitutions() throws AuthenticationException, Exception
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getDAOFactory(em).createInstitutionDAO().findInstitutionEntities();
        }
        finally
        {
            em.close();
        }
    }
    
    @Override
    public List<Faculty> getAllFacultiesInInstitution(Institution institution) throws AuthenticationException, Exception
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getDAOFactory(em).createFacultyDAO().findAllFacultiesInInstitution(institution);
        }
        finally
        {
            em.close();
        }
    }
    
    @Override
    public List<Department> getAllDepartmentForFaculty(Faculty faculty) throws AuthenticationException, Exception
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getDAOFactory(em).createDepartmentDAO().findAllDepartmentsInFaculty(faculty);
        }
        finally
        {
            em.close();
        }
        
    }
    

    @Override
    public Institution getInstitution(Long institution) throws Exception
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getDAOFactory(em).createInstitutionDAO().findInstitution(institution);
        }
        finally
        {
            em.close();
        }
        
    }
    

    @Override
    public Faculty getFaculty(Long faculty) throws Exception
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getDAOFactory(em).createFacultyDAO().findFaculty(faculty);
        }
        finally
        {
            em.close();
        }
        
    }
    
    @Override
    public Department getDepartment(Long department) throws Exception
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getDAOFactory(em).createDepartmentDAO().findDepartment(department);
        }
        finally
        {
            em.close();
        }
        
    }
}
