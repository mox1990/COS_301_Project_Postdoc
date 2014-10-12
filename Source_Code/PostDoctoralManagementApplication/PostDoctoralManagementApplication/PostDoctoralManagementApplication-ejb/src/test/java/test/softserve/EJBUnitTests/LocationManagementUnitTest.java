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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
        
    }

    /**
     * Test of createFaculty method, of class LocationManagementService.
     */
    @Test
    public void testCreateFaculty() throws Exception {
        
    }

    /**
     * Test of createDepartment method, of class LocationManagementService.
     */
    @Test
    public void testCreateDepartment() throws Exception {
        
    }

    /**
     * Test of updateInstitution method, of class LocationManagementService.
     */
    @Test
    public void testUpdateInstitution() throws Exception {
        
    }

    /**
     * Test of updateFaculty method, of class LocationManagementService.
     */
    @Test
    public void testUpdateFaculty() throws Exception {
        
    }

    /**
     * Test of updateDepartment method, of class LocationManagementService.
     */
    @Test
    public void testUpdateDepartment() throws Exception {
        
    }

    /**
     * Test of getAllInstitutions method, of class LocationManagementService.
     */
    @Test
    public void testGetAllInstitutions() throws Exception {
        
    }

    /**
     * Test of getAllFacultiesInInstitution method, of class LocationManagementService.
     */
    @Test
    public void testGetAllFacultiesInInstitution() throws Exception {
        
    }

    /**
     * Test of getAllDepartmentForFaculty method, of class LocationManagementService.
     */
    @Test
    public void testGetAllDepartmentForFaculty() throws Exception {
        
    }

    /**
     * Test of getInstitution method, of class LocationManagementService.
     */
    @Test
    public void testGetInstitution() throws Exception {
        
    }

    /**
     * Test of getFaculty method, of class LocationManagementService.
     */
    @Test
    public void testGetFaculty() throws Exception {
        
    }

    /**
     * Test of getDepartment method, of class LocationManagementService.
     */
    @Test
    public void testGetDepartment() throws Exception {
        
    }
    
}
