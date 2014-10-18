/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.ProgressReport;
import com.softserve.ejb.applicationservices.ApplicationRenewalService;
import com.softserve.ejb.applicationservices.ApplicationRenewalServiceLocal;
import com.softserve.ejb.applicationservices.CVManagementServiceLocal;
import com.softserve.ejb.applicationservices.ProgressReportManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBEntities.Notification;
import java.util.ArrayList;
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
import test.softserve.MockEJBClasses.ApplicationRenewalServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ApplicationRenewalUnitTest {
    private ApplicationRenewalServiceMockUnit instance;
    
    private NotificationServiceLocal mockNotificationServiceLocal;
    private CVManagementServiceLocal mockCVManagementServiceLocal;
    private ProgressReportManagementServiceLocal mockProgressReportManagementServiceLocal;
    private DAOFactory mockDAOFactory;
    private TransactionController mockTransactionController;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private ApplicationServicesUtil mockApplicationServicesUtil;
    private GregorianCalendar mockGregorianCalendar;
    private EntityManager mockEntityManager;
    private ApplicationJpaController mockApplicationJpaController;
    
    private Session mockSession;
    private Application mockApplication;
    
    public ApplicationRenewalUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new ApplicationRenewalServiceMockUnit();
        
        mockNotificationServiceLocal = mock(NotificationServiceLocal.class);
        mockCVManagementServiceLocal = mock(CVManagementServiceLocal.class);
        mockProgressReportManagementServiceLocal = mock(ProgressReportManagementServiceLocal.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockTransactionController = mock(TransactionController.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockApplicationServicesUtil = mock(ApplicationServicesUtil.class);
        mockGregorianCalendar = mock(GregorianCalendar.class);
        mockSession = mock(Session.class);
        mockEntityManager = mock(EntityManager.class);
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockApplication = mock(Application.class);
        
        instance.setNotificationServiceLocal(mockNotificationServiceLocal);
        instance.setcVManagementServiceLocal(mockCVManagementServiceLocal);
        instance.setProgressReportManagementServiceLocal(mockProgressReportManagementServiceLocal);
        instance.setdAOFactory(mockDAOFactory);
        instance.setTransactionController(mockTransactionController);
        instance.setdBEntitiesFactory(mockDBEntitiesFactory);
        instance.setApplicationServicesUtil(mockApplicationServicesUtil);
        instance.setGregorianCalendar(mockGregorianCalendar);
        instance.setEm(mockEntityManager);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getRenewableApplicationsForFellow method, of class ApplicationRenewalService.
     */
    @Test
    public void testGetRenewableApplicationsForFellow() throws Exception {
        Person p = new Person("u12236731");
        
        try
        {
            instance.getRenewableApplicationsForFellow(mockSession, p);
            
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplicationJpaController).findAllRenewalApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, 0, Integer.MAX_VALUE);
            verify(mockApplicationJpaController).getAllNewApplicationsForFellowWithEndDateInBetween(p, mockGregorianCalendar.getGregorianChange(), mockGregorianCalendar.getGregorianChange());
            verify(mockGregorianCalendar, times(2)).getTime();
            verify(mockGregorianCalendar).add(GregorianCalendar.DAY_OF_YEAR, 365);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockProgressReportManagementServiceLocal);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockCVManagementServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockApplication);
        }
        catch(Exception ex)
        {
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }

    /**
     * Test of doesApplicationHaveFinalProgressReport method, of class ApplicationRenewalService.
     */
    @Test
    public void testDoesApplicationHaveFinalProgressReport() throws Exception {
        
        try
        {
            instance.doesApplicationHaveFinalProgressReport(mockSession, mockApplication);
            
            verify(mockProgressReportManagementServiceLocal).doesApplicationHaveFinalProgressReport(mockSession, mockApplication);
            
            verifyNoMoreInteractions(mockProgressReportManagementServiceLocal);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockCVManagementServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockApplication);
            
            //verifyNoMoreInteractions(mockApplication);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }

    /**
     * Test of updateResearchFellowCV method, of class ApplicationRenewalService.
     */
    @Test
    public void testUpdateResearchFellowCV() throws Exception {
        Cv cv = new Cv("mockCV");
        
        when(mockCVManagementServiceLocal.hasCV(mockSession)).thenReturn(Boolean.TRUE);
        try {
            instance.updateResearchFellowCV(mockSession, cv);
            
            verify(mockCVManagementServiceLocal).hasCV(mockSession);
            verify(mockCVManagementServiceLocal).updateCV(mockSession, cv);
            
            verifyNoMoreInteractions(mockProgressReportManagementServiceLocal);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockCVManagementServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockApplication);
        }
        catch(Exception ex) {
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }
    
    @Test
    public void testUpdateResearchFellowCVFail() throws Exception {
        Cv cv = new Cv("mockCV");
        
        when(mockCVManagementServiceLocal.hasCV(mockSession)).thenReturn(Boolean.FALSE);
        try
        {
            instance.updateResearchFellowCV(mockSession, cv);
            
            fail("An exception didn't occur");
            
            verify(mockCVManagementServiceLocal).hasCV(mockSession);
            verify(mockCVManagementServiceLocal).updateCV(mockSession, cv);
            
            verifyNoMoreInteractions(mockProgressReportManagementServiceLocal);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockCVManagementServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockApplication);
        }
        catch(Exception ex)
        {
            
        }
    }

    /**
     * Test of createFinalProgressReportForApplication method, of class ApplicationRenewalService.
     */
    @Test
    public void testCreateFinalProgressReportForApplication() throws Exception {
        ProgressReport pr = new ProgressReport(Long.MIN_VALUE);
        
        List<ProgressReport> l = mock(List.class);
        when(l.size()).thenReturn(2);
        when(mockApplication.getProgressReportList()).thenReturn(l);
        when(mockProgressReportManagementServiceLocal.getNumberOfProgressReportsRequiredByApplication(mockSession, mockApplication)).thenReturn(3);
        try
        {
            instance.createFinalProgressReportForApplication(mockSession, mockApplication, pr);
            
            verify(mockProgressReportManagementServiceLocal).getNumberOfProgressReportsRequiredByApplication(mockSession, mockApplication);
            verify(mockProgressReportManagementServiceLocal).createProgressReport(mockSession, mockApplication, pr);
            verify(mockApplication).getProgressReportList();
            
            verifyNoMoreInteractions(mockProgressReportManagementServiceLocal);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockCVManagementServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockApplication);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }

    /**
     * Test of createRenewalApplication method, of class ApplicationRenewalService.
     */
    @Test
    public void testCreateRenewalApplicationEdit() throws Exception {
        ProgressReport pr = new ProgressReport(Long.MIN_VALUE);
        Application oldApplication = mock(Application.class);
        
        when(mockApplicationJpaController.findApplication(mockApplication.getApplicationID())).thenReturn(mockApplication);
        try
        {
            instance.createRenewalApplication(mockSession, oldApplication, mockApplication);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplication, atLeast(2)).getApplicationID(); // TODO: Why does it bug?
            verify(mockApplicationJpaController).findApplication(mockApplication.getApplicationID());
            verify(mockApplicationJpaController).edit(mockApplication);
            
            verifyNoMoreInteractions(mockProgressReportManagementServiceLocal);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockCVManagementServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockApplicationJpaController);
            // TODO: Write for verifyNoMoreInteractions(mockApplication);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }
    
    @Test
    public void testCreateRenewalApplicationCreate() throws Exception {
        ProgressReport pr = new ProgressReport(Long.MIN_VALUE);
        Application oldApplication = mock(Application.class);
        
        when(mockApplicationJpaController.findApplication(mockApplication.getApplicationID())).thenReturn(mockApplication);
        try
        {
            instance.createRenewalApplication(mockSession, oldApplication, mockApplication);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplication, atLeast(2)).getApplicationID(); // TODO: Why does it bug?
            verify(mockApplicationJpaController).findApplication(mockApplication.getApplicationID());
            verify(mockApplicationJpaController).edit(mockApplication);
            
            verifyNoMoreInteractions(mockProgressReportManagementServiceLocal);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockCVManagementServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockApplicationJpaController);
            // TODO: Write for verifyNoMoreInteractions(mockApplication);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }

    /**
     * Test of submitApplication method, of class ApplicationRenewalService.
     */
    @Test
    public void testSubmitApplication() throws Exception {
        when(mockApplication.getFellow()).thenReturn(new Person("u12236731"));
        when(mockApplication.getGrantHolder()).thenReturn(new Person("u12345678"));
        when(mockApplication.getProjectTitle()).thenReturn("Mock Project");
        
        List<Notification> n = new ArrayList<>();
        n.add(new Notification(Long.MIN_VALUE)); n.add(new Notification(Long.MAX_VALUE));
        
        when(mockDBEntitiesFactory.createNotificationEntity(null, new Person("u12236731"), "Renewal application submitted", "Please note that the renewal application '" + "Mock Project" + "' has been submitted for which you are the fellow of.")).thenReturn(n.get(0));
        when(mockDBEntitiesFactory.createNotificationEntity(null, new Person("u12345678"), "Renewal application submitted", "Please note that the renewal application '" + "Mock Project" + "' has been submitted for which you are the grant holder of.")).thenReturn(n.get(1));
        
        try
        {
            instance.submitApplication(mockSession, mockApplication);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            verify(mockApplicationServicesUtil).submitApplication(mockApplication);
            verify(mockDBEntitiesFactory).createNotificationEntity(null, mockApplication.getFellow(), "Renewal application submitted", "Please note that the renewal application '" + mockApplication.getProjectTitle() + "' has been submitted for which you are the fellow of.");
            verify(mockDBEntitiesFactory).createNotificationEntity(null, mockApplication.getGrantHolder(), "Renewal application submitted", "Please note that the renewal application '" + mockApplication.getProjectTitle() + "' has been submitted for which you are the grant holder of.");
            verify(mockNotificationServiceLocal).sendBatchNotifications(mockSession, n, true);
            verify(mockApplication, atLeastOnce()).getFellow();
            verify(mockApplication, atLeastOnce()).getGrantHolder();
            verify(mockApplication, atLeast(2)).getProjectTitle();
            
            verifyNoMoreInteractions(mockProgressReportManagementServiceLocal);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockCVManagementServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockApplication);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }
    
}
