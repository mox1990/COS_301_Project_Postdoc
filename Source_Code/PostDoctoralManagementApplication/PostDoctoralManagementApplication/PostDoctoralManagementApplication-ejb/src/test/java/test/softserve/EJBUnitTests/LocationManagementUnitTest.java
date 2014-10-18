/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.LocationManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.DepartmentJpaController;
import com.softserve.persistence.DBDAO.FacultyJpaController;
import com.softserve.persistence.DBDAO.InstitutionJpaController;
import com.softserve.persistence.DBDAO.RecommendationReportJpaController;
import com.softserve.persistence.DBEntities.Department;
import com.softserve.persistence.DBEntities.Faculty;
import com.softserve.persistence.DBEntities.Institution;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import test.softserve.MockEJBClasses.HODRecommendationServicesMockUnit;
import test.softserve.MockEJBClasses.LocationManagementServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class LocationManagementUnitTest {
    private LocationManagementServiceMockUnit instance;
    
    private DBEntitiesFactory mockDBEntitiesFactory;
    private TransactionController mockTransactionController;
    private DAOFactory mockDAOFactory;
    private EntityManager mockEntityManager;
    private InstitutionJpaController mockInstitutionJpaController;
    private DepartmentJpaController mockDepartmentJpaController;
    private FacultyJpaController mockFacultyJpaController;
    
    public LocationManagementUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new LocationManagementServiceMockUnit();
        
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockTransactionController = mock(TransactionController.class);
        mockEntityManager = mock(EntityManager.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockInstitutionJpaController = mock(InstitutionJpaController.class);
        mockDepartmentJpaController = mock(DepartmentJpaController.class);
        mockFacultyJpaController = mock(FacultyJpaController.class);
        
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setTransactionController(mockTransactionController);
        instance.setEntityManager(mockEntityManager);
        instance.setdAOFactory(mockDAOFactory);
        
        when(mockDAOFactory.createInstitutionDAO()).thenReturn(mockInstitutionJpaController);
        when(mockDAOFactory.createFacultyDAO()).thenReturn(mockFacultyJpaController);
        when(mockDAOFactory.createDepartmentDAO()).thenReturn(mockDepartmentJpaController);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createInstitution method, of class LocationManagementService.
     */
    @Test
    public void testCreateInstitution() throws Exception {
        Session mockSession = mock(Session.class);
        
        Institution mockInstituation = mock(Institution.class);
        
        try
        {
            instance.createInstitution(mockSession, mockInstituation);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createInstitutionDAO();
            verify(mockInstitutionJpaController).create(mockInstituation);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockInstitutionJpaController);
            verifyNoMoreInteractions(mockDepartmentJpaController);
            verifyNoMoreInteractions(mockFacultyJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of createFaculty method, of class LocationManagementService.
     */
    @Test
    public void testCreateFaculty() throws Exception {
        Session mockSession = mock(Session.class);
        
        Faculty mockFaculty = mock(Faculty.class);
        
        try
        {
            instance.createFaculty(mockSession, mockFaculty);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createFacultyDAO();
            verify(mockFacultyJpaController).create(mockFaculty);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockInstitutionJpaController);
            verifyNoMoreInteractions(mockDepartmentJpaController);
            verifyNoMoreInteractions(mockFacultyJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of createDepartment method, of class LocationManagementService.
     */
    @Test
    public void testCreateDepartment() throws Exception {
        Session mockSession = mock(Session.class);
        
        Department mockDepartment = mock(Department.class);
        
        try
        {
            instance.createDepartment(mockSession, mockDepartment);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createDepartmentDAO();
            verify(mockDepartmentJpaController).create(mockDepartment);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockInstitutionJpaController);
            verifyNoMoreInteractions(mockDepartmentJpaController);
            verifyNoMoreInteractions(mockFacultyJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of updateInstitution method, of class LocationManagementService.
     */
    @Test
    public void testUpdateInstitution() throws Exception {
        Session mockSession = mock(Session.class);
        
        Institution mockInstituation = mock(Institution.class);
        
        try
        {
            instance.updateInstitution(mockSession, mockInstituation);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createInstitutionDAO();
            verify(mockInstitutionJpaController).edit(mockInstituation);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockInstitutionJpaController);
            verifyNoMoreInteractions(mockDepartmentJpaController);
            verifyNoMoreInteractions(mockFacultyJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of updateFaculty method, of class LocationManagementService.
     */
    @Test
    public void testUpdateFaculty() throws Exception {
        Session mockSession = mock(Session.class);
        
        Faculty mockFaculty = mock(Faculty.class);
        
        try
        {
            instance.updateFaculty(mockSession, mockFaculty);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createFacultyDAO();
            verify(mockFacultyJpaController).edit(mockFaculty);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockInstitutionJpaController);
            verifyNoMoreInteractions(mockDepartmentJpaController);
            verifyNoMoreInteractions(mockFacultyJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of updateDepartment method, of class LocationManagementService.
     */
    @Test
    public void testUpdateDepartment() throws Exception {
        Session mockSession = mock(Session.class);
        
        Department mockDepartment = mock(Department.class);
        
        try
        {
            instance.updateDepartment(mockSession, mockDepartment);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createDepartmentDAO();
            verify(mockDepartmentJpaController).edit(mockDepartment);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockInstitutionJpaController);
            verifyNoMoreInteractions(mockDepartmentJpaController);
            verifyNoMoreInteractions(mockFacultyJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    // TODO: Develop return test for these functions...
    /**
     * Test of getAllInstitutions method, of class LocationManagementService.
     */
    @Test
    public void testGetAllInstitutions() throws Exception {        
        try
        {
            instance.getAllInstitutions();
            
            verify(mockDAOFactory).createInstitutionDAO();
            verify(mockInstitutionJpaController).findInstitutionEntities();
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockInstitutionJpaController);
            verifyNoMoreInteractions(mockDepartmentJpaController);
            verifyNoMoreInteractions(mockFacultyJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of getAllFacultiesInInstitution method, of class LocationManagementService.
     */
    @Test
    public void testGetAllFacultiesInInstitution() throws Exception {
        Institution mockInstitution = mock(Institution.class);
        try
        {
            instance.getAllFacultiesInInstitution(mockInstitution);
            
            verify(mockDAOFactory).createFacultyDAO();
            verify(mockFacultyJpaController).findAllFacultiesInInstitution(mockInstitution);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockInstitutionJpaController);
            verifyNoMoreInteractions(mockDepartmentJpaController);
            verifyNoMoreInteractions(mockFacultyJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of getAllDepartmentForFaculty method, of class LocationManagementService.
     */
    @Test
    public void testGetAllDepartmentForFaculty() throws Exception {
        Faculty mockFaculty = mock(Faculty.class);
        try
        {
            instance.getAllDepartmentForFaculty(mockFaculty);
            
            verify(mockDAOFactory).createDepartmentDAO();
            verify(mockDepartmentJpaController).findAllDepartmentsInFaculty(mockFaculty);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockInstitutionJpaController);
            verifyNoMoreInteractions(mockDepartmentJpaController);
            verifyNoMoreInteractions(mockFacultyJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of getInstitution method, of class LocationManagementService.
     */
    @Test
    public void testGetInstitution() throws Exception {
        try
        {
            instance.getInstitution(Long.MAX_VALUE);
            
            verify(mockDAOFactory).createInstitutionDAO();
            verify(mockInstitutionJpaController).findInstitution(Long.MAX_VALUE);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockInstitutionJpaController);
            verifyNoMoreInteractions(mockDepartmentJpaController);
            verifyNoMoreInteractions(mockFacultyJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of getFaculty method, of class LocationManagementService.
     */
    @Test
    public void testGetFaculty() throws Exception {
        try
        {
            instance.getFaculty(Long.MAX_VALUE);
            
            verify(mockDAOFactory).createFacultyDAO();
            verify(mockFacultyJpaController).findFaculty(Long.MAX_VALUE);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockInstitutionJpaController);
            verifyNoMoreInteractions(mockDepartmentJpaController);
            verifyNoMoreInteractions(mockFacultyJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of getDepartment method, of class LocationManagementService.
     */
    @Test
    public void testGetDepartment() throws Exception {
        Faculty mockFaculty = mock(Faculty.class);
        try
        {
            instance.getDepartment(Long.MAX_VALUE);
            
            verify(mockDAOFactory).createDepartmentDAO();
            verify(mockDepartmentJpaController).findDepartment(Long.MAX_VALUE);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockInstitutionJpaController);
            verifyNoMoreInteractions(mockDepartmentJpaController);
            verifyNoMoreInteractions(mockFacultyJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
}
