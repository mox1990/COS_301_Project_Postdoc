/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.FundingReportJpaController;
import com.softserve.persistence.DBDAO.RecommendationReportJpaController;
import com.softserve.persistence.DBEntities.AmmendRequest;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.RecommendationReport;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.applicationservices.HODRecommendationServices;
import com.softserve.ejb.applicationservices.HODRecommendationServicesLocal;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
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
    private HODRecommendationServicesMockUnit instance = new HODRecommendationServicesMockUnit();
        
    private ApplicationJpaController mockApplicationJpaController;
    private RecommendationReportJpaController mockRecommendationReportJpaController;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private UserGateway mockUserGateway;
    private NotificationService mockNotificationService;
    private AuditTrailService mockAuditTrailService;
    private ApplicationServicesUtil mockApplicationServices;
    private AmmendRequestJpaController mockAmmendRequestJpaController;
    private GregorianCalendar mockCal;
        
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
        instance = new HODRecommendationServicesMockUnit();
        
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockRecommendationReportJpaController = mock(RecommendationReportJpaController.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockUserGateway = mock(UserGateway.class);
        mockNotificationService = mock(NotificationService.class);
        mockAuditTrailService = mock(AuditTrailService.class);
        mockApplicationServices = mock(ApplicationServicesUtil.class);
        mockAmmendRequestJpaController = mock(AmmendRequestJpaController.class);
        mockCal = mock(GregorianCalendar.class);

        instance.setaRDAO(mockAmmendRequestJpaController);
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setrRDAO(mockRecommendationReportJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        instance.setgCal(mockCal);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadPendingApplications method, of class HODRecommendationServices.
     */
    @Test
    public void testLoadPendingApplications() throws Exception {                
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        int start = 0;
        int max = 5; 
        
        try
        {
            instance.loadPendingApplications(mockSession, start, max);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).loadPendingApplications(mockSession.getUser(), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED, start, max);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingApplications method, of class HODRecommendationServices.
     */
    @Test
    public void testCountTotalPendingApplications() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        
        try
        {
            instance.countTotalPendingApplications(mockSession);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(mockSession.getUser(), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }

    /**
     * Test of declineAppliction method, of class HODRecommendationServices.
     */
    @Test
    public void testDenyAppliction() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Cv mockCv = mock(Cv.class);
        when(mockCv.getDateOfBirth()).thenReturn(new Date(1993, 9, 17));
        
        Person mockPerson = mock(Person.class);
        when(mockPerson.getCv()).thenReturn(mockCv);
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        String reason = "Prospective fellow does not meet the eligiblity requirement";
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Declined application " + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockPerson, "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_HOD);

        try
        {
            instance.declineAppliction(mockSession, mockApplication, reason);
            // Declined Application...
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }

    /**
     * Test of ammendAppliction method, of class HODRecommendationServices.
     */
    @Test
    public void testAmmendAppliction() throws Exception {
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Cv mockCv = mock(Cv.class);
        when(mockCv.getDateOfBirth()).thenReturn(new Date(1993, 9, 17));
        
        Person mockPerson = mock(Person.class);
        when(mockPerson.getCv()).thenReturn(mockCv);
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        String reason = "Prospective fellow does not meet the eligiblity requirement";
        
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Ammendment request for application " + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockPerson, "Application ammendment requested", "The following application requires ammendment as per request by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application ammendment requested", "The following application requires ammendment as per request by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        when(mockDBEntitiesFactory.createAmmendRequestEntity(mockApplication, mockSession.getUser(),reason, mockCal.getTime())).thenReturn(new AmmendRequest(Long.MAX_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_HOD);

        try
        {
            instance.ammendAppliction(mockSession, mockApplication, reason);
            // Declined Application...
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationJpaController).edit(mockApplication);
            
            //verify(mockDBEntitiesFactory).createAduitLogEntitiy("Ammendment request for application " + Long.MAX_VALUE, new Person("u12236731"));
            verify(mockDBEntitiesFactory).createAmmendRequestEntity(mockApplication, mockSession.getUser(),reason, mockCal.getTime());
            verify(mockDBEntitiesFactory).createNotificationEntity(new Person("u12236731"), mockPerson, "Application ammendment requested", "The following application requires ammendment as per request by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason);
            verify(mockDBEntitiesFactory).createNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application ammendment requested", "The following application requires ammendment as per request by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            //verify(mockNotificationService).sendNotification(new Notification(Long.MAX_VALUE), true);
            //verify(mockNotificationService).sendNotification(new Notification(Long.MIN_VALUE), true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }

    /**
     * Test of recommendApplication method, of class HODRecommendationServices.
     */
    @Test
    public void testApproveApplicationWithoutDeansToEndorse() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Cv mockCv = mock(Cv.class);
        when(mockCv.getDateOfBirth()).thenReturn(new Date(1993, 9, 17));
        
        Person mockPerson = mock(Person.class);
        when(mockPerson.getCv()).thenReturn(mockCv);
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        RecommendationReport mockRecommendationReport = mock(RecommendationReport.class);
        
        String reason = "Prospective fellow does not meet the eligiblity requirement";
        
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Application approved" + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockPerson, "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_HOD);

        try
        {
            instance.recommendApplication(mockSession, mockApplication, mockRecommendationReport);
            // Declined Application...
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockRecommendationReportJpaController).create(mockRecommendationReport);
            verify(mockApplicationJpaController).edit(mockApplication);
            //verify(mockDBEntitiesFactory).createAduitLogEntitiy("Application approved" + Long.MAX_VALUE, new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
           ////fail("An exception occured");
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
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Application approved" + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
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
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockPerson, "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolderID(), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);

        try
        {
            instance.recommendApplication(mockSession, mockApplication, mockRecommendationReport);
            // Declined Application...
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockRecommendationReportJpaController).create(mockRecommendationReport);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Application approved" + Long.MAX_VALUE, new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        
            // TODO: Add the Deans verifications...
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }*/
    }
    
}
