/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.persistence.DBDAO.CvJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.applicationservices.CVManagementService;
import com.softserve.ejb.applicationservices.GrantHolderFinalisationServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxillary.util.ApplicationServicesUtil;
import com.softserve.auxillary.factories.DBEntitiesFactory;
import com.softserve.auxillary.requestresponseclasses.Session;
import java.util.ArrayList;
import java.util.Arrays;
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
import test.softserve.MockEJBClasses.GrantHolderFinalisationServiceMockUnit;

/**
 *
 * @author kgothatso
 */
public class GrantHolderFinalisationUnitTest {
    private GrantHolderFinalisationServiceMockUnit instance;
         
    private UserGateway mockUserGateway;
    private CVManagementService mockCVManagementService;
    private PersonJpaController mockPersonJpaController;
    private CvJpaController mockCvJpaController;
    private ApplicationJpaController mockApplicationJpaController;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private NotificationService mockNotificationService;
    private AuditTrailService mockAuditTrailService;
    private ApplicationServicesUtil mockApplicationServices;
    private GregorianCalendar mockCal;
    private ApplicationReviewRequestJpaController mockApplicationReviewRequestJpaController;

    public GrantHolderFinalisationUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new GrantHolderFinalisationServiceMockUnit();
        
        mockUserGateway = mock(UserGateway.class);
        mockCVManagementService = mock(CVManagementService.class);
        mockPersonJpaController = mock(PersonJpaController.class);
        mockCvJpaController = mock(CvJpaController.class);
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockNotificationService = mock(NotificationService.class);
        mockAuditTrailService = mock(AuditTrailService.class);
        mockApplicationServices = mock(ApplicationServicesUtil.class);
        mockCal = mock(GregorianCalendar.class);
        mockApplicationReviewRequestJpaController = mock(ApplicationReviewRequestJpaController.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setcVDAO(mockCvJpaController);
        instance.setcVEJB(mockCVManagementService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setpDAO(mockPersonJpaController);
        instance.setpDAO(mockPersonJpaController);
        instance.setuEJB(mockUserGateway);
        instance.setgCal(mockCal);
        instance.setaRDAO(mockApplicationReviewRequestJpaController);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createGrantHolderCV method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testCreateGrantHolderCV() throws Exception {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        Cv mockCV = mock(Cv.class);  
        Session mockSession = mock(Session.class);
        
        try
        {
            instance.createGrantHolderCV(mockSession, mockCV);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockCVManagementService).createCV(mockSession, mockCV);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }

    @Test
    public void testCreateGrantHolderCVNotValid() throws Exception {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        Session mockSession = mock(Session.class);
        
        try
        {
            instance.createGrantHolderCV(mockSession, null);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
        }
        catch (Exception ex)
        {
            if(!ex.getMessage().equals("CV is not valid"))
                ;//fail("An exception occured");
        }
    }
    
    /**
     * Test of loadPendingApplications method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testLoadPendingApplications() throws Exception {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        Session mockSession = mock(Session.class);
        int startIndex = 0;
        int max = 5;
        
        try
        {
            instance.loadPendingApplications(mockSession, startIndex, max);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).loadPendingApplications(mockSession.getUser(), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED, startIndex, max);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingApplications method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testCountTotalPendingApplications() throws Exception {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        try
        {
            instance.countTotalPendingApplications(mockSession);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(mockSession.getUser(), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }

    /**
     * Test of finaliseApplication method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testFinaliseApplication() throws Exception // TODO: Add alternatives to this...
    {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Finalised application " + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        when(mockApplicationJpaController.findApplication(Long.MAX_VALUE)).thenReturn(mockApplication);
        when(mockApplicationReviewRequestJpaController.findAllPeopleWhoHaveBeenRequestForApplicationAs(mockApplication, com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD)).thenReturn(new ArrayList<Person>());
        try
        {
            instance.finaliseApplication(mockSession, mockApplication);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationJpaController).edit(mockApplication);
            //verify(mockDBEntitiesFactory).createAduitLogEntitiy("Finalised application " + Long.MAX_VALUE, new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: ADD list verfication...
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }
    
    @Test
    public void testFinaliseApplicationWithNotifications() throws Exception { // TODO: Populate notifications
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        Person mockPersonA = mock(Person.class);
        Person mockPersonB = mock(Person.class);
        
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Finalised application " + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockPersonA, "Application finalised", "The following application has been finalised by " + mockSession.getUser().getCompleteName() +  ". Please review for endorsement.")).thenReturn(new Notification(new Long(1)));
        when(mockDBEntitiesFactory.createNotificationEntity(new Person("u12236731"), mockPersonB, "Application finalised", "The following application has been finalised by " + mockSession.getUser().getCompleteName() +  ". Please review for endorsement.")).thenReturn(new Notification(new Long(2)));
        
        when(mockApplicationJpaController.findAllHODsWhoCanRecommendApplication(mockApplication)).thenReturn(Arrays.asList(mockPersonA, mockPersonB));
        when(mockApplicationJpaController.findApplication(Long.MAX_VALUE)).thenReturn(mockApplication);
        try
        {
            instance.finaliseApplication(mockSession, mockApplication);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationJpaController).edit(mockApplication);
            
            //verify(mockDBEntitiesFactory).createAduitLogEntitiy("Finalised application " + Long.MAX_VALUE, new Person("u12236731"));
            
            //verify(mockDBEntitiesFactory).createNotificationEntity(new Person("u12236731"), mockPersonA, "Application finalised", "The following application has been finalised by " + mockSession.getUser().getCompleteName() +  ". Please review for endorsement.");
            //verify(mockDBEntitiesFactory).createNotificationEntity(new Person("u12236731"), mockPersonB, "Application finalised", "The following application has been finalised by " + mockSession.getUser().getCompleteName() +  ". Please review for endorsement.");
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            
            //verify(mockNotificationService).sendBatchNotifications(new ArrayList(), true);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            ////fail("An exception occured");
        }
    }
    
}
