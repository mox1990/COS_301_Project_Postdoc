/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.DeclineReportJpaController;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.DeclineReport;
import com.softserve.DBEntities.EligiblityReport;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
import test.softserve.MockEJBClasses.ApplicationServicesUtilMockUnit;
import test.softserve.MockEJBClasses.CVManagementServiceMockUnit;

/**
 *
 * @author kgothatso
 */
public class ApplicationServicesUtilUnitTest {
    private ApplicationJpaController mockApplicationJpaController;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private UserGateway mockUserGateway;
    private NotificationService mockNotificationService;
    private AuditTrailService mockAuditTrailService;
    private DeclineReportJpaController mockDeclineReportJpaController;
    private GregorianCalendar mockGregorianCalendar;
    
    private ApplicationServicesUtilMockUnit instance;
    
    public ApplicationServicesUtilUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new ApplicationServicesUtilMockUnit();
        
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockUserGateway = mock(UserGateway.class);
        mockNotificationService = mock(NotificationService.class);
        mockAuditTrailService = mock(AuditTrailService.class);
        mockDeclineReportJpaController = mock(DeclineReportJpaController.class);
        mockGregorianCalendar = mock(GregorianCalendar.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setdRDAO(mockDeclineReportJpaController);
        instance.setgCal(mockGregorianCalendar);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadPendingApplications method, of class ApplicationServicesUtil.
     */
    @Test
    public void testLoadPendingApplications() {
        
    }

    /**
     * Test of getTotalNumberOfPendingApplications method, of class ApplicationServicesUtil.
     */
    @Test
    public void testGetTotalNumberOfPendingApplications() {
        
    }

    /**
     * Test of declineAppliction method, of class ApplicationServicesUtil.
     */
    @Test
    public void testDeclineAppliction() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        
        when(mockApplication.getFellow()).thenReturn(new Person("u12236731"));
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        when(mockApplication.getStatus()).thenReturn("Pending");
        
        String reason = "Prospective fellow does not meet the requirement";
        
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Declined application " + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), new Person("u12236731"), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        when(mockDBEntitiesFactory.bulidDeclineReportEntity(mockApplication, reason, mockGregorianCalendar.getTime())).thenReturn(new DeclineReport(Long.MAX_VALUE));
        
        try
        {
            instance.declineAppliction(mockSession, mockApplication, reason);
            
            verify(mockDBEntitiesFactory).bulidDeclineReportEntity(mockApplication, reason, mockGregorianCalendar.getTime());
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Declined application "+ Long.MAX_VALUE, new Person("u12236731"));
            verify(mockDBEntitiesFactory).buildNotificationEntity(new Person("u12236731"), new Person("u12236731"), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason);
            verify(mockDBEntitiesFactory).buildNotificationEntity(new Person("u12236731"), mockApplication.getGrantHolder(), "Application declined", "The following application has been declined by " + mockSession.getUser().getCompleteName() + ". For the following reasons: " + reason);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE)); // TODO: Why is it wrong?
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
}
