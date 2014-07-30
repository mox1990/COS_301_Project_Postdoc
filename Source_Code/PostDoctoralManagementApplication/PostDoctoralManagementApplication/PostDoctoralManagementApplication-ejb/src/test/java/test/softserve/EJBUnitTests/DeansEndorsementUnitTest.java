/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.EndorsementJpaController;
import com.softserve.DBDAO.FundingReportJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Endorsement;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.DeansEndorsementServiceLocal;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import test.softserve.MockEJBClasses.DRISApprovalServiceMockUnit;
import test.softserve.MockEJBClasses.DeansEndorsementServiceMockUnit;

/**
 *
 * @author kgothatso
 */
public class DeansEndorsementUnitTest {
    
    public DeansEndorsementUnitTest() {
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
     * Test of loadPendingApplications method, of class DeansEndorsementService.
     */
    @Test
    public void testLoadPendingApplications() throws Exception {
        DeansEndorsementServiceMockUnit instance = new DeansEndorsementServiceMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        EndorsementJpaController mockEndorsementJpaController = mock(EndorsementJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.seteDAO(mockEndorsementJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        int startIndex = 0;
        int maxNumber = 5;
        try
        {
            instance.loadPendingApplications(mockSession, startIndex, maxNumber);
            
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
            
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).loadPendingApplications(new Person("u12236731"), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, startIndex, maxNumber);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingApplications method, of class DeansEndorsementService.
     */
    @Test
    public void testCountTotalPendingApplications() throws Exception {
        DeansEndorsementServiceMockUnit instance = new DeansEndorsementServiceMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        EndorsementJpaController mockEndorsementJpaController = mock(EndorsementJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.seteDAO(mockEndorsementJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));

        try
        {
            instance.countTotalPendingApplications(mockSession);
            
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
            
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of denyApplication method, of class DeansEndorsementService.
     */
    @Test
    public void testDenyApplication() throws Exception {
        DeansEndorsementServiceMockUnit instance = new DeansEndorsementServiceMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        EndorsementJpaController mockEndorsementJpaController = mock(EndorsementJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Declined application " + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.seteDAO(mockEndorsementJpaController);
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
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);

        try
        {
            instance.denyApplication(mockSession, mockApplication, reason);
            // Declined Application...
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Declined application " + Long.MAX_VALUE, new Person("u12236731"));
            
            verify(mockDBEntitiesFactory).buildNotificationEntity(new Person("u12236731"), mockPerson, "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason);
            verify(mockDBEntitiesFactory).buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason);
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
     * Test of endorseApplication method, of class DeansEndorsementService.
     */
    @Test
    public void testEndorseApplicationWithoutDRISMember() throws Exception {
        DeansEndorsementServiceMockUnit instance = new DeansEndorsementServiceMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        EndorsementJpaController mockEndorsementJpaController = mock(EndorsementJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Endorsed application " + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.seteDAO(mockEndorsementJpaController);
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
        String applicantMessage = "appMSG", cscMesssage = "cscMSG", finaceMessage = "fMSG";
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
        
        Endorsement mockEndorsement = mock(Endorsement.class);
        // when(mockEndorsement.get()).thenReturn(Long.MAX_VALUE);

        try
        {
            instance.endorseApplication(mockSession, mockApplication, mockEndorsement);
            // Declined Application...
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            
            verify(mockEndorsementJpaController).create(mockEndorsement);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Endorsed application " + Long.MAX_VALUE, new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            verify(mockNotificationService).sendBatchNotifications(new ArrayList<Notification>(), true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
}
