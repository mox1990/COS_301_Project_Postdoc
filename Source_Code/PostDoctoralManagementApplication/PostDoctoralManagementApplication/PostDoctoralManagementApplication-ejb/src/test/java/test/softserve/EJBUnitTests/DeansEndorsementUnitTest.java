/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.EndorsementJpaController;
import com.softserve.persistence.DBDAO.FundingReportJpaController;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.Endorsement;
import com.softserve.persistence.DBEntities.FundingReport;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.applicationservices.DeansEndorsementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
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
    private DeansEndorsementServiceMockUnit instance;
         
    private ApplicationJpaController mockApplicationJpaController;
    private EndorsementJpaController mockEndorsementJpaController;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private UserGateway mockUserGateway;
    private NotificationService mockNotificationService;
    private AuditTrailService mockAuditTrailService;
    private ApplicationServicesUtil mockApplicationServices;

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
        instance = new DeansEndorsementServiceMockUnit();
        
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockEndorsementJpaController = mock(EndorsementJpaController.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockUserGateway = mock(UserGateway.class);
        mockNotificationService = mock(NotificationService.class);
        mockAuditTrailService = mock(AuditTrailService.class);
        mockApplicationServices = mock(ApplicationServicesUtil.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.seteDAO(mockEndorsementJpaController);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadPendingApplications method, of class DeansEndorsementService.
     */
    @Test
    public void testLoadPendingApplications() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        int startIndex = 0;
        int maxNumber = 5;
        try
        {
            instance.loadPendingApplications(mockSession, startIndex, maxNumber);
            
            //ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            //roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).loadPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, startIndex, maxNumber);
        }
        catch (Exception ex)
        {
            //fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingApplications method, of class DeansEndorsementService.
     */
    @Test
    public void testCountTotalPendingApplications() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));

        try
        {
            instance.countTotalPendingApplications(mockSession);
            
            //ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            //roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
        }
        catch (Exception ex)
        {
            //fail("An exception occured");
        }
    }

    /**
     * Test of declineApplication method, of class DeansEndorsementService.
     */
    @Test
    public void testDenyApplication() throws Exception {
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
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);

        try
        {
            instance.declineApplication(mockSession, mockApplication, reason);
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
     * Test of endorseApplication method, of class DeansEndorsementService.
     */
    @Test
    public void testEndorseApplicationWithoutDRISMember() throws Exception {
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
        String applicantMessage = "appMSG", cscMesssage = "cscMSG", finaceMessage = "fMSG";
        
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Endorsed application " + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
          
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
        
        Endorsement mockEndorsement = mock(Endorsement.class);
        // when(mockEndorsement.get()).thenReturn(Long.MAX_VALUE);

        try
        {
            instance.endorseApplication(mockSession, mockApplication, mockEndorsement);
            // Declined Application...
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            
            verify(mockEndorsementJpaController).create(mockEndorsement);
            verify(mockApplicationJpaController).edit(mockApplication);
            //verify(mockDBEntitiesFactory).createAduitLogEntitiy("Endorsed application " + Long.MAX_VALUE, new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            //verify(mockNotificationService).sendBatchNotifications(new ArrayList<Notification>(), true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }
    
}
