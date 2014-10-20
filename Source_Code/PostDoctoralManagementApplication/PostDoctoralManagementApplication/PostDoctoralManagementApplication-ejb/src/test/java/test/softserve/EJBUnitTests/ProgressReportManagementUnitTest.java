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
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.ProgressReport;
import java.util.Date;
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
        when(mockDAOFactory.createAmmendRequestDAO()).thenReturn(mockAmmendRequestJpaController);
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
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        ProgressReport mockProgressReport = mock(ProgressReport.class);
        
        try
        {
            instance.createProgressReport(mockSession, mockApplication, mockProgressReport);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createProgressReportDAO();
            verify(mockProgressReportJpaController).create(mockProgressReport);
            verify(mockCal).getTime();
            
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockRecommendationReportJpaController);
            verifyNoMoreInteractions(mockAmmendRequestJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockProgressReportJpaController);
                    
            
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of updateProgressReport method, of class ProgressReportManagementService.
     */
    @Test
    public void testUpdateProgressReport() throws Exception {
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        ProgressReport mockProgressReport = mock(ProgressReport.class);
        
        try
        {
            instance.updateProgressReport(mockSession, mockProgressReport);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createProgressReportDAO();
            verify(mockProgressReportJpaController).edit(mockProgressReport);
            
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockRecommendationReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockAmmendRequestJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockProgressReportJpaController);
                    
            
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of allApplicationsWithPendingReportsForUser method, of class ProgressReportManagementService.
     */
    @Test
    public void testAllApplicationsWithPendingReportsForUserBeingEmpty() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        try
        {
            List<Application> app = instance.allApplicationsWithPendingReportsForUser(mockSession);
            
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplicationJpaController).findAllApplicationsWhosFellowIs(new Person("u12236731"));
            verify(mockEntityManager).close();
            
            assertEquals(0, app.size());
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockRecommendationReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockAmmendRequestJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockProgressReportJpaController);
                    
            
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
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
    public void testGetNumberOfProgressReportsRequiredByApplicationBeingZero() throws Exception {
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        
        when(mockApplication.getStartDate()).thenReturn(new Date(1234));
        when(mockApplication.getEndDate()).thenReturn(new Date(1234));
        try
        {
            int reports = instance.getNumberOfProgressReportsRequiredByApplication(mockSession, mockApplication);
            
            verify(mockApplication).getStartDate();
            verify(mockApplication).getEndDate();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockRecommendationReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockAmmendRequestJpaController);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockProgressReportJpaController);
                    
            
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
}
