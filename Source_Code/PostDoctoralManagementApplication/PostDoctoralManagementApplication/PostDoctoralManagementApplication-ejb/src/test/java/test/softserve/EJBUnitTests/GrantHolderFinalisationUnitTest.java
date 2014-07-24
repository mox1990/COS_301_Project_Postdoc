/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.CVManagementService;
import com.softserve.ejb.GrantHolderFinalisationServiceLocal;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createGrantHolderCV method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testCreateGrantHolderCV() throws Exception {
        GrantHolderFinalisationServiceMockUnit instance = new GrantHolderFinalisationServiceMockUnit();
        
        UserGateway mockUserGateway = mock(UserGateway.class);
        CVManagementService mockCVManagementService = mock(CVManagementService.class);
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        CvJpaController mockCvJpaController = mock(CvJpaController.class);
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
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
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        Cv mockCV = mock(Cv.class);  
        Session mockSession = mock(Session.class);
        
        try
        {
            instance.createGrantHolderCV(mockSession, mockCV);
            
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockCVManagementService).createCV(mockSession, mockCV);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of loadPendingApplications method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testLoadPendingApplications() throws Exception {
        GrantHolderFinalisationServiceMockUnit instance = new GrantHolderFinalisationServiceMockUnit();
        
        UserGateway mockUserGateway = mock(UserGateway.class);
        CVManagementService mockCVManagementService = mock(CVManagementService.class);
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        CvJpaController mockCvJpaController = mock(CvJpaController.class);
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
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
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        Session mockSession = mock(Session.class);
        int startIndex = 0;
        int max = 5;
        
        try
        {
            instance.loadPendingApplications(mockSession, startIndex, max);
            
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).loadPendingApplications(mockSession.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED, startIndex, max);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingApplications method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testCountTotalPendingApplications() throws Exception {
        GrantHolderFinalisationServiceMockUnit instance = new GrantHolderFinalisationServiceMockUnit();
        
        UserGateway mockUserGateway = mock(UserGateway.class);
        CVManagementService mockCVManagementService = mock(CVManagementService.class);
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        CvJpaController mockCvJpaController = mock(CvJpaController.class);
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
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
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        try
        {
            instance.countTotalPendingApplications(mockSession);
            
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(mockSession.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of finaliseApplication method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testFinaliseApplication() throws Exception {
        GrantHolderFinalisationServiceMockUnit instance = new GrantHolderFinalisationServiceMockUnit();
        
        UserGateway mockUserGateway = mock(UserGateway.class);
        CVManagementService mockCVManagementService = mock(CVManagementService.class);
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        CvJpaController mockCvJpaController = mock(CvJpaController.class);
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Finalised application " + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        
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
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        Application mockApplication = mock(Application.class);
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        try
        {
            instance.finaliseApplication(mockSession, mockApplication);
            
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Finalised application " + Long.MAX_VALUE, new Person("u12236731"));
            //verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: ADD list verfication...
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
}
