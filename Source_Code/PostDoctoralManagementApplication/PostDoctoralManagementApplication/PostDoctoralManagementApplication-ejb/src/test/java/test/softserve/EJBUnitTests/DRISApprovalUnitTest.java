/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.FundingReportJpaController;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.DRISApprovalServiceLocal;
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
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import test.softserve.MockEJBClasses.DRISApprovalServiceMockUnit;

/**
 *
 * @author kgothatso
 */
public class DRISApprovalUnitTest {
    
    public DRISApprovalUnitTest() {
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
     * Test of loadPendingEndorsedApplications method, of class DRISApprovalService.
     */
    @Test
    public void testLoadPendingEndorsedApplications() throws Exception {
        DRISApprovalServiceMockUnit instance = new DRISApprovalServiceMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        FundingReportJpaController mockFundingReportJpaController = mock(FundingReportJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setfRDAO(mockFundingReportJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        int startIndex = 0;
        int maxNumber = 5;
        try
        {
            instance.loadPendingEndorsedApplications(mockSession, startIndex, maxNumber);
            
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
            
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).loadPendingApplications(new Person("u12236731"), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED, startIndex, maxNumber);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingEndorsedApplications method, of class DRISApprovalService.
     */
    @Test
    public void testCountTotalPendingEndorsedApplications() throws Exception {
        DRISApprovalServiceMockUnit instance = new DRISApprovalServiceMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        FundingReportJpaController mockFundingReportJpaController = mock(FundingReportJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setfRDAO(mockFundingReportJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));

        try
        {
            instance.countTotalPendingEndorsedApplications(mockSession);
            
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
            
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of loadPendingEligibleApplications method, of class DRISApprovalService.
     */
    @Test
    public void testLoadPendingEligibleApplications() throws Exception {
        DRISApprovalServiceMockUnit instance = new DRISApprovalServiceMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        FundingReportJpaController mockFundingReportJpaController = mock(FundingReportJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setfRDAO(mockFundingReportJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        int startIndex = 0;
        int maxNumber = 5;
        try
        {
            instance.loadPendingEligibleApplications(mockSession, startIndex, maxNumber);
            
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
            
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).loadPendingApplications(new Person("u12236731"), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE, startIndex, maxNumber);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingEligibleApplications method, of class DRISApprovalService.
     */
    @Test
    public void testCountTotalPendingEligibleApplications() throws Exception {
        DRISApprovalServiceMockUnit instance = new DRISApprovalServiceMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        FundingReportJpaController mockFundingReportJpaController = mock(FundingReportJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setfRDAO(mockFundingReportJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));

        try
        {
            instance.countTotalPendingEligibleApplications(mockSession);
            
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
            
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of checkApplicationForEligiblity method, of class DRISApprovalService.
     */
    @Test
    public void testCheckApplicationForEligiblityDeclined() throws Exception {
        DRISApprovalServiceMockUnit instance = new DRISApprovalServiceMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        FundingReportJpaController mockFundingReportJpaController = mock(FundingReportJpaController.class);
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
        instance.setfRDAO(mockFundingReportJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        
        AcademicQualification mockAcademicQualification = mock(AcademicQualification.class);
        when(mockAcademicQualification.getName()).thenReturn("PHD");
        when(mockAcademicQualification.getYearObtained()).thenReturn(new Date(1991, 5, 12));
        
        Cv mockCv = mock(Cv.class);
        when(mockCv.getDateOfBirth()).thenReturn(new Date(1955, 9, 17));
        when(mockCv.getAcademicQualificationList()).thenReturn(Arrays.asList(mockAcademicQualification));
        
        Person mockPerson = mock(Person.class);
        when(mockPerson.getCv()).thenReturn(mockCv);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        String reason = "Prospective fellow does not meet the eligiblity requirement";
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockPerson, "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);

        try
        {
            instance.checkApplicationForEligiblity(mockSession, mockApplication);
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
     * Test of checkApplicationForEligiblity method, of class DRISApprovalService.
     */
    @Test
    public void testCheckApplicationForEligiblity() throws Exception {
        DRISApprovalServiceMockUnit instance = new DRISApprovalServiceMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        FundingReportJpaController mockFundingReportJpaController = mock(FundingReportJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Application made eligible" + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setfRDAO(mockFundingReportJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        
        AcademicQualification mockAcademicQualification = mock(AcademicQualification.class);
        when(mockAcademicQualification.getName()).thenReturn("PHD");
        when(mockAcademicQualification.getYearObtained()).thenReturn(new Date(2013, 5, 12));
        
        Cv mockCv = mock(Cv.class);
        when(mockCv.getDateOfBirth()).thenReturn(new Date(1993, 9, 17));
        when(mockCv.getAcademicQualificationList()).thenReturn(Arrays.asList(mockAcademicQualification));
        
        Person mockPerson = mock(Person.class);
        when(mockPerson.getCv()).thenReturn(mockCv);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        String reason = "Prospective fellow does not meet the eligiblity requirement";
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockPerson, "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);

        try
        {
            instance.checkApplicationForEligiblity(mockSession, mockApplication);
            // Declined Application...
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Application made eligible" + Long.MAX_VALUE, new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of denyFunding method, of class DRISApprovalService.
     */
    @Test
    public void testDenyFunding() throws Exception {
        DRISApprovalServiceMockUnit instance = new DRISApprovalServiceMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        FundingReportJpaController mockFundingReportJpaController = mock(FundingReportJpaController.class);
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
        instance.setfRDAO(mockFundingReportJpaController);
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
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);

        try
        {
            instance.denyFunding(mockSession, mockApplication, reason);
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
     * Test of approveFunding method, of class DRISApprovalService.
     */
    @Test
    public void testApproveFunding() throws Exception {
        DRISApprovalServiceMockUnit instance = new DRISApprovalServiceMockUnit();
        
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        FundingReportJpaController mockFundingReportJpaController = mock(FundingReportJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Application approved" + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setfRDAO(mockFundingReportJpaController);
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
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockPerson, "Application approved", "The following application has been approved for funding by " + mockSession.getUser().getCompleteName() + ". " + applicantMessage)).thenReturn(new Notification(new Long(1)));
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application approved", "The following application has been approved for funding by " + mockSession.getUser().getCompleteName() + ". " + applicantMessage)).thenReturn(new Notification(new Long(2)));
        
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockPerson, "Application approved", "The following application has been approved for funding by " + mockSession.getUser().getCompleteName() + ". " + cscMesssage)).thenReturn(new Notification(new Long(3)));
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application approved", "The following application has been approved for funding by " + mockSession.getUser().getCompleteName() + ". " + finaceMessage)).thenReturn(new Notification(new Long(4)));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        
        FundingReport mockFundingReport = mock(FundingReport.class);
        when(mockFundingReport.getReportID()).thenReturn(Long.MAX_VALUE);

        try
        {
            instance.approveFunding(mockSession, mockApplication, mockFundingReport, applicantMessage, cscMesssage, finaceMessage);
            // Declined Application...
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            
            verify(mockFundingReportJpaController).create(mockFundingReport);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Application approved" + Long.MAX_VALUE, new Person("u12236731"));
            
            verify(mockDBEntitiesFactory).buildNotificationEntity(new Person("u12236731"), mockPerson, "Application approved", "The following application has been approved for funding by " + mockSession.getUser().getCompleteName() + ". " + applicantMessage);
            verify(mockDBEntitiesFactory).buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application approved", "The following application has been approved for funding by " + mockSession.getUser().getCompleteName() + ". " + applicantMessage);
            
            verify(mockDBEntitiesFactory).buildNotificationEntity(new Person("u12236731"), mockPerson, "Application approved", "The following application has been approved for funding by " + mockSession.getUser().getCompleteName() + ". " + cscMesssage);
            verify(mockDBEntitiesFactory).buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application approved", "The following application has been approved for funding by " + mockSession.getUser().getCompleteName() + ". " + finaceMessage);
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            verify(mockNotificationService).sendBatchNotifications(Arrays.asList(new Notification(new Long(1)),new Notification(new Long(2)),new Notification(new Long(3)),new Notification(new Long(4))), true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
}
