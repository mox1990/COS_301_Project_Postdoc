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
import com.softserve.ejb.applicationservices.RefereeReportServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.RefereeReportJpaController;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.RefereeReport;
import java.util.ArrayList;
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
import test.softserve.MockEJBClasses.RefereeReportMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class RefereeReportUnitTest {
    private RefereeReportMockUnit instance;
    
    private GregorianCalendar mockGregorianCalendar;
    private NotificationService mockNotificationService;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private ApplicationServicesUtil mockApplicationServicesUtil;
    private DAOFactory mockDAOFactory;
    private TransactionController mockTransactionController;
    private EntityManager mockEntityManager;
    private RefereeReportJpaController mockRefereeReportJpaController;
    private ApplicationJpaController mockApplicationJpaController;
    
    public RefereeReportUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new RefereeReportMockUnit();
        
        mockGregorianCalendar = mock(GregorianCalendar.class);
        mockNotificationService = mock(NotificationService.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockApplicationServicesUtil = mock(ApplicationServicesUtil.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockTransactionController = mock(TransactionController.class);
        mockEntityManager = mock(EntityManager.class);
        mockRefereeReportJpaController = mock(RefereeReportJpaController.class);
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        
        instance.setCal(mockGregorianCalendar);
        instance.setnEJB(mockNotificationService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setaSEJB(mockApplicationServicesUtil);
        instance.setdAOFactory(mockDAOFactory);
        instance.setTransactionController(mockTransactionController);
        instance.setEntityManager(mockEntityManager);
        
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
        when(mockDAOFactory.createRefereeReportDAO()).thenReturn(mockRefereeReportJpaController);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadPendingApplications method, of class RefereeReportService.
     */
    @Test
    public void testLoadPendingApplications() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        int startIndex = 0;
        int max = 5;
        
        try
        {
            // Test returned value...
            instance.loadPendingApplications(mockSession, startIndex, max);
            
            verify(mockApplicationServicesUtil).loadPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED, startIndex, max);
            verify(mockEntityManager).close();
            verify(mockSession).getUser();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockRefereeReportJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingApplications method, of class RefereeReportService.
     */
    @Test
    public void testCountTotalPendingApplications() throws Exception {
        int expected = 4;
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        when(mockApplicationServicesUtil.getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED)).thenReturn(expected);
        try
        {
            int applications = instance.countTotalPendingApplications(mockSession);
            
            assertEquals(expected, applications);
            
            verify(mockApplicationServicesUtil).getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
            verify(mockEntityManager).close();
            verify(mockSession).getUser();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockRefereeReportJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of submitReferralReport method, of class RefereeReportService.
     */
    @Test
    public void testSubmitReferralReportWithNotifications() throws Exception 
    {
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        RefereeReport mockRefereeReport = mock(RefereeReport.class);
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        when(mockApplication.getPersonList()).thenReturn(new ArrayList());
        when(mockApplication.getRefereeReportList()).thenReturn(new ArrayList());
        
        when(mockApplicationJpaController.findApplication(Long.MAX_VALUE)).thenReturn(mockApplication);
        
        try
        {
            instance.submitReferralReport(mockSession, mockApplication, mockRefereeReport);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockDAOFactory).createRefereeReportDAO();
            verify(mockRefereeReport).setTimestamp(mockGregorianCalendar.getTime());
            verify(mockRefereeReport).setApplicationID(mockApplication);
            verify(mockRefereeReportJpaController).create(mockRefereeReport);
            verify(mockGregorianCalendar, times(2)).getTime();
            
            verify(mockApplication).getApplicationID();
            verify(mockApplicationJpaController).findApplication(Long.MAX_VALUE);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockRefereeReportJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
}
