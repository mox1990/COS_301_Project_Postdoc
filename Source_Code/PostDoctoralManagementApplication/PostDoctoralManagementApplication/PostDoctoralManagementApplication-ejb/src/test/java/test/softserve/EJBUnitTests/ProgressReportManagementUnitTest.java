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
import com.softserve.ejb.applicationservices.ProgressReportManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.ProgressReportJpaController;
import com.softserve.persistence.DBDAO.RecommendationReportJpaController;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.ProgressReport;
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
import test.softserve.MockEJBClasses.ProgressReportManagementMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ProgressReportManagementUnitTest {
    private ProgressReportManagementMockUnit instance;
    
    private ApplicationJpaController mockApplicationJpaController;
    private RecommendationReportJpaController mockRecommendationReportJpaController;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private NotificationService mockNotificationService;
    private AmmendRequestJpaController mockAmmendRequestJpaController;
    private GregorianCalendar mockCal;
    private TransactionController mockTransactionController;
    private DAOFactory mockDAOFactory;
    private EntityManager mockEntityManager;
    private ProgressReportJpaController mockProgressReportJpaController;
    
    public ProgressReportManagementUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new ProgressReportManagementMockUnit();
        
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockRecommendationReportJpaController = mock(RecommendationReportJpaController.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockNotificationService = mock(NotificationService.class);
        mockAmmendRequestJpaController = mock(AmmendRequestJpaController.class);
        mockCal = mock(GregorianCalendar.class);
        mockTransactionController = mock(TransactionController.class);
        mockEntityManager = mock(EntityManager.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockProgressReportJpaController = mock(ProgressReportJpaController.class);
                
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setgCal(mockCal);
        instance.setTransactionController(mockTransactionController);
        instance.setEntityManager(mockEntityManager);
        instance.setdAOFactory(mockDAOFactory);
        
        when(mockDAOFactory.createProgressReportDAO()).thenReturn(mockProgressReportJpaController);
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
        when(mockDAOFactory.createRecommendationReportDAO()).thenReturn(mockRecommendationReportJpaController);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createProgressReport method, of class ProgressReportManagementService.
     */
    @Test
    public void testCreateProgressReport() throws Exception {
        
    }

    /**
     * Test of updateProgressReport method, of class ProgressReportManagementService.
     */
    @Test
    public void testUpdateProgressReport() throws Exception {
        
    }

    /**
     * Test of allApplicationsWithPendingReportsForUser method, of class ProgressReportManagementService.
     */
    @Test
    public void testAllApplicationsWithPendingReportsForUser() throws Exception {
        
    }

    /**
     * Test of doesApplicationHaveFinalProgressReport method, of class ProgressReportManagementService.
     */
    @Test
    public void testDoesApplicationHaveFinalProgressReport() throws Exception {
        
    }

    /**
     * Test of getNumberOfProgressReportsRequiredByApplication method, of class ProgressReportManagementService.
     */
    @Test
    public void testGetNumberOfProgressReportsRequiredByApplication() throws Exception {
        
    }
    
}
