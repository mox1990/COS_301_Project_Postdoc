/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.AmmendRequestJpaController;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.FundingReportJpaController;
import com.softserve.DBDAO.RecommendationReportJpaController;
import com.softserve.DBEntities.AmmendRequest;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.RecommendationReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.HODRecommendationServices;
import com.softserve.ejb.HODRecommendationServicesLocal;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import test.softserve.MockEJBClasses.DRISApprovalServiceMockUnit;
import test.softserve.MockEJBClasses.HODRecommendationServicesMockUnit;

/**
 *
 * @author kgothatso
 */
public class HODRecommendationUnitTest {
    
    public HODRecommendationUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadPendingApplications method, of class HODRecommendationServices.
     */
    @Test
    public void testLoadPendingApplications() throws Exception {
        HODRecommendationServicesMockUnit instance = new HODRecommendationServicesMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        RecommendationReportJpaController mockRecommendationReportJpaController = mock(RecommendationReportJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setrRDAO(mockRecommendationReportJpaController);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        int start = 0;
        int max = 5; 
        
        try
        {
            instance.loadPendingApplications(mockSession, start, max);
            
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).loadPendingApplications(mockSession.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED, start, max);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingApplications method, of class HODRecommendationServices.
     */
    @Test
    public void testCountTotalPendingApplications() throws Exception {
        HODRecommendationServicesMockUnit instance = new HODRecommendationServicesMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        RecommendationReportJpaController mockRecommendationReportJpaController = mock(RecommendationReportJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setrRDAO(mockRecommendationReportJpaController);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        
        try
        {
            instance.countTotalPendingApplications(mockSession);
            
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(mockSession.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of declineAppliction method, of class HODRecommendationServices.
     */
    @Test
    public void testDenyAppliction() throws Exception {
        HODRecommendationServicesMockUnit instance = new HODRecommendationServicesMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        RecommendationReportJpaController mockRecommendationReportJpaController = mock(RecommendationReportJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Declined application " + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setrRDAO(mockRecommendationReportJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        Application mockApplication = mock(Application.class);
        Cv mockCv = mock(Cv.class);
        when(mockCv.getDateOfBirth()).thenReturn(new Date(1993, 9, 17));
        Person mockPerson = mock(Person.class);
        when(mockPerson.getCv()).thenReturn(mockCv);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        String reason = "Prospective fellow does not meet the eligiblity requirement";
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockPerson, "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);

        try
        {
            instance.declineAppliction(mockSession, mockApplication, reason);
            // Declined Application...
            verify(mockUserGateway).authenticateUser(mockSession, roles);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of ammendAppliction method, of class HODRecommendationServices.
     */
    @Test
    public void testAmmendAppliction() throws Exception {
        HODRecommendationServicesMockUnit instance = new HODRecommendationServicesMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        RecommendationReportJpaController mockRecommendationReportJpaController = mock(RecommendationReportJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Ammendment request for application " + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        AmmendRequestJpaController mockAmmendRequestJpaController = mock(AmmendRequestJpaController.class);
        GregorianCalendar mockCal = mock(GregorianCalendar.class);
        
        
        instance.setaRDAO(mockAmmendRequestJpaController);
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setrRDAO(mockRecommendationReportJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        instance.setgCal(mockCal);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        Application mockApplication = mock(Application.class);
        Cv mockCv = mock(Cv.class);
        when(mockCv.getDateOfBirth()).thenReturn(new Date(1993, 9, 17));
        Person mockPerson = mock(Person.class);
        when(mockPerson.getCv()).thenReturn(mockCv);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        String reason = "Prospective fellow does not meet the eligiblity requirement";
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockPerson, "Application ammendment requested", "The following application requires ammendment as per request by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application ammendment requested", "The following application requires ammendment as per request by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        when(mockDBEntitiesFactory.bulidAmmendRequestEntity(mockApplication, mockSession.getUser(),reason, mockCal.getTime())).thenReturn(new AmmendRequest(Long.MAX_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);

        try
        {
            instance.ammendAppliction(mockSession, mockApplication, reason);
            // Declined Application...
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationJpaController).edit(mockApplication);
            
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Ammendment request for application " + Long.MAX_VALUE, new Person("u12236731"));
            verify(mockDBEntitiesFactory).bulidAmmendRequestEntity(mockApplication, mockSession.getUser(),reason, mockCal.getTime());
            verify(mockDBEntitiesFactory).buildNotificationEntity(new Person("u12236731"), mockPerson, "Application ammendment requested", "The following application requires ammendment as per request by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason);
            verify(mockDBEntitiesFactory).buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application ammendment requested", "The following application requires ammendment as per request by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            verify(mockNotificationService).sendNotification(new Notification(Long.MAX_VALUE), true);
            verify(mockNotificationService).sendNotification(new Notification(Long.MIN_VALUE), true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of recommendApplication method, of class HODRecommendationServices.
     */
    @Test
    public void testApproveApplicationWithoutDeansToEndorse() throws Exception {
        HODRecommendationServicesMockUnit instance = new HODRecommendationServicesMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        RecommendationReportJpaController mockRecommendationReportJpaController = mock(RecommendationReportJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Application approved" + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setrRDAO(mockRecommendationReportJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        Application mockApplication = mock(Application.class);
        Cv mockCv = mock(Cv.class);
        when(mockCv.getDateOfBirth()).thenReturn(new Date(1993, 9, 17));
        Person mockPerson = mock(Person.class);
        when(mockPerson.getCv()).thenReturn(mockCv);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        RecommendationReport mockRecommendationReport = mock(RecommendationReport.class);
        
        String reason = "Prospective fellow does not meet the eligiblity requirement";
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockPerson, "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);

        try
        {
            instance.recommendApplication(mockSession, mockApplication, mockRecommendationReport);
            // Declined Application...
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockRecommendationReportJpaController).create(mockRecommendationReport);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Application approved" + Long.MAX_VALUE, new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
           // fail("An exception occured");
        }
    }
    
    /**
     * Test of recommendApplication method, of class HODRecommendationServices.
     */
    @Test
    public void testApproveApplicationWithDeansToEndorse() throws Exception {        
        /*HODRecommendationServicesMockUnit instance = new HODRecommendationServicesMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        RecommendationReportJpaController mockRecommendationReportJpaController = mock(RecommendationReportJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Application approved" + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setrRDAO(mockRecommendationReportJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        Application mockApplication = mock(Application.class);
        Cv mockCv = mock(Cv.class);
        when(mockCv.getDateOfBirth()).thenReturn(new Date(1993, 9, 17));
        Person mockPerson = mock(Person.class);
        when(mockPerson.getCv()).thenReturn(mockCv);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolderID()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        RecommendationReport mockRecommendationReport = mock(RecommendationReport.class);
        
        String reason = "Prospective fellow does not meet the eligiblity requirement";
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockPerson, "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolderID(), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);

        try
        {
            instance.recommendApplication(mockSession, mockApplication, mockRecommendationReport);
            // Declined Application...
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockRecommendationReportJpaController).create(mockRecommendationReport);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Application approved" + Long.MAX_VALUE, new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        
            // TODO: Add the Deans verifications...
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }*/
    }
    
}
