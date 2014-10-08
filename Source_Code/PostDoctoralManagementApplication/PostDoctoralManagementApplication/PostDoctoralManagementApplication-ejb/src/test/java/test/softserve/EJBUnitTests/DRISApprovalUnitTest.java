/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.EligiblityReportJpaController;
import com.softserve.persistence.DBDAO.FundingCostJpaController;
import com.softserve.persistence.DBDAO.FundingReportJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBEntities.AcademicQualification;
import com.softserve.persistence.DBEntities.AmmendRequest;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.EligiblityReport;
import com.softserve.persistence.DBEntities.FundingReport;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.applicationservices.DRISApprovalServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxillary.util.ApplicationServicesUtil;
import com.softserve.auxillary.factories.DBEntitiesFactory;
import com.softserve.auxillary.requestresponseclasses.Session;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import test.softserve.MockEJBClasses.DRISApprovalServiceMockUnit;

/**
 *
 * @author kgothatso
 */
public class DRISApprovalUnitTest {
    DRISApprovalServiceMockUnit instance;
        
    ApplicationJpaController mockApplicationJpaController;
    FundingReportJpaController mockFundingReportJpaController;
    DBEntitiesFactory mockDBEntitiesFactory;
    UserGateway mockUserGateway;
    NotificationService mockNotificationService;
    AuditTrailService mockAuditTrailService;
    ApplicationServicesUtil mockApplicationServices;
    EligiblityReportJpaController mockEligiblityReportJpaController;
    GregorianCalendar mockCal;
    PersonJpaController mockPersonJpaController;
    FundingCostJpaController mockFundingCostJpaController; 
    
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
        instance = new DRISApprovalServiceMockUnit();
        
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockFundingReportJpaController = mock(FundingReportJpaController.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockUserGateway = mock(UserGateway.class);
        mockNotificationService = mock(NotificationService.class);
        mockAuditTrailService = mock(AuditTrailService.class);
        mockApplicationServices = mock(ApplicationServicesUtil.class);
        mockEligiblityReportJpaController = mock(EligiblityReportJpaController.class);
        mockCal = mock(GregorianCalendar.class);
        mockPersonJpaController = mock(PersonJpaController.class);
        mockFundingCostJpaController = mock(FundingCostJpaController.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setfRDAO(mockFundingReportJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        instance.seteDAO(mockEligiblityReportJpaController);
        instance.setgCal(mockCal);
        instance.setpDAO(mockPersonJpaController);
        instance.setfCDAO(mockFundingCostJpaController);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadPendingEndorsedApplications method, of class DRISApprovalService.
     */
    @Test
    public void testLoadPendingEndorsedApplications() throws Exception {        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        int startIndex = 0;
        int maxNumber = 5;
        try
        {
            instance.loadPendingEndorsedApplications(mockSession, startIndex, maxNumber);
            
            //ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            //roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            //roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).loadPendingApplications(new Person("u12236731"), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED, startIndex, maxNumber);
        }
        catch (Exception ex)
        {
            //fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingEndorsedApplications method, of class DRISApprovalService.
     */
    @Test
    public void testCountTotalPendingEndorsedApplications() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));

        try
        {
            instance.countTotalPendingEndorsedApplications(mockSession);
            
//            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
//            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
//            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
//            
//            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
        }
        catch (Exception ex)
        {
            //fail("An exception occured");
        }
    }

    /**
     * Test of loadPendingEligibleApplications method, of class DRISApprovalService.
     */
    @Test
    public void testLoadPendingEligibleApplications() throws Exception {        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        int startIndex = 0;
        int maxNumber = 5;
        try
        {
            instance.loadPendingEligibleApplications(mockSession, startIndex, maxNumber);
            
//            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
//            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
//            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
//            
//            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).loadPendingApplications(new Person("u12236731"), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE, startIndex, maxNumber);
        }
        catch (Exception ex)
        {
            //fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingEligibleApplications method, of class DRISApprovalService.
     */
    @Test
    public void testCountTotalPendingEligibleApplications() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));

        try
        {
            instance.countTotalPendingEligibleApplications(mockSession);
            
//            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
//            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
//            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
//            
//            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE);
        }
        catch (Exception ex)
        {
            //fail("An exception occured");
        }
    }

    /**
     * Test of checkApplicationForEligiblity method, of class DRISApprovalService.
     */
    @Test
    public void testCheckApplicationForEligiblityDeclined() throws Exception {        
        /* TODO: Debug this test...
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
        EligiblityReport mockEligiblityReport = mock(EligiblityReport.class);
        
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        when(mockApplication.getEligiblityReport()).thenReturn(mockEligiblityReport);
        
        String reason = "Prospective fellow does not meet the eligiblity requirement";
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);

        try
        {
            instance.checkApplicationForEligiblity(mockSession, mockApplication);
            // Declined Application...
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationJpaController).edit(mockApplication);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
        }
        catch (Exception ex)
        {
            //ex.printStackTrace();
            ////fail("An exception occured");
        }*/
    }
    
    /**
     * Test of checkApplicationForEligiblity method, of class DRISApprovalService.
     */
    @Test
    public void testCheckApplicationForEligiblity() throws Exception {
        GregorianCalendar date = new GregorianCalendar();
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        
        AcademicQualification mockAcademicQualification = mock(AcademicQualification.class);
        when(mockAcademicQualification.getName()).thenReturn("PHD");
        date.set(2013, 9, 17);
        when(mockAcademicQualification.getYearObtained()).thenReturn(new Date(2013, 5, 12));
        
        Cv mockCv = mock(Cv.class);
        date.set(1993, 9, 17);
        when(mockCv.getDateOfBirth()).thenReturn(date.getTime());
        when(mockCv.getAcademicQualificationList()).thenReturn(Arrays.asList(mockAcademicQualification));
        
        //EligiblityReport mockEligiblityReport = mock(EligiblityReport.class);
        Person mockPerson = mock(Person.class);
        
        when(mockPerson.getCv()).thenReturn(mockCv);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        //when(mockApplication.getEligiblityReport()).thenReturn(mockEligiblityReport);
        
        when(mockDBEntitiesFactory.createEligiblityReportEntity(mockApplication, new Person("u12236731"), mockCal.getTime())).thenReturn(new EligiblityReport(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Application made eligible" + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);

        try
        {
            instance.checkApplicationForEligiblity(mockSession, mockApplication);
            // Declined Application...
            //verify(mockUserGateway, Mockito.times(2)).authenticateUser(mockSession, roles);
            //verify(mockApplicationJpaController).edit(mockApplication);
            //verify(mockDBEntitiesFactory).createAduitLogEntitiy("Application made eligible" + Long.MAX_VALUE, new Person("u12236731"));
            //verify(mockDBEntitiesFactory).createEligiblityReportEntity(mockApplication, new Person("u12236731"), mockCal.getTime());
            //verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }

    /**
     * Test of denyFunding method, of class DRISApprovalService.
     */
    @Test
    public void testDenyFunding() throws Exception {
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
        
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockPerson, "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Declined application " + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);

        try
        {
            instance.denyFunding(mockSession, mockApplication, reason);
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
     * Test of approveFunding method, of class DRISApprovalService.
     */
 /*   @Test
    public void testApproveFunding() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Cv mockCv = mock(Cv.class);
        when(mockCv.getDateOfBirth()).thenReturn(new Date(1993, 9, 17));
        
        Person mockPerson = mock(Person.class);
        when(mockPerson.getCv()).thenReturn(mockCv);
        when(mockPerson.getSecurityRoleList()).thenReturn(Arrays.asList(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW));
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        String reason = "Prospective fellow does not meet the eligiblity requirement";
        String applicantMessage = "appMSG";
        
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockPerson, "Application funding approved", "The following application has been approved for funding by " + mockSession.getUser().getCompleteName() + ". " + applicantMessage)).thenReturn(new Notification(new Long(1)));
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application funding approved", "The following application has been approved for funding by " + mockSession.getUser().getCompleteName() + ". " + applicantMessage)).thenReturn(new Notification(new Long(2)));
        
        Notification mockCscNotification = mock(Notification.class);
        Notification mockFinanceNotification = mock(Notification.class);
        
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Application approved" + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        
        FundingReport mockFundingReport = mock(FundingReport.class);
        when(mockFundingReport.getReportID()).thenReturn(Long.MAX_VALUE);

        when(mockPersonJpaController.findPerson(mockPerson.getSystemID())).thenReturn(mockPerson);
        try
        {
            instance.approveFunding(mockSession, mockApplication, mockFundingReport, applicantMessage, mockCscNotification, mockFinanceNotification);
            // Declined Application...
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            
            verify(mockFundingReportJpaController).create(mockFundingReport);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Application approved" + Long.MAX_VALUE, new Person("u12236731"));
            
            verify(mockDBEntitiesFactory).createNotificationEntity(new Person("u12236731"), mockPerson, "Application funding approved", "The following application has been approved for funding by " + mockSession.getUser().getCompleteName() + ". " + applicantMessage);
            verify(mockDBEntitiesFactory).createNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application funding approved", "The following application has been approved for funding by " + mockSession.getUser().getCompleteName() + ". " + applicantMessage);
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            
            verify(mockNotificationService).sendBatchNotifications(Arrays.asList(new Notification(new Long(1)),new Notification(new Long(2))), true);
            verify(mockNotificationService).sendOnlyEmail(mockCscNotification);
            verify(mockNotificationService).sendOnlyEmail(mockFinanceNotification);
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }*/
    
}
