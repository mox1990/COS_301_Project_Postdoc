/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
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
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import com.softserve.persistence.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;
import test.softserve.MockEJBClasses.DRISApprovalServiceMockUnit;
import test.softserve.MockEJBClasses.HODRecommendationServicesMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class HODRecommendationUnitTest {
    private HODRecommendationServicesMockUnit instance;
        
    private ApplicationJpaController mockApplicationJpaController;
    private RecommendationReportJpaController mockRecommendationReportJpaController;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private NotificationService mockNotificationService;
    private ApplicationServicesUtil mockApplicationServices;
    private AmmendRequestJpaController mockAmmendRequestJpaController;
    private GregorianCalendar mockCal;
    private TransactionController mockTransactionController;
    private DAOFactory mockDAOFactory;
    private EntityManager mockEntityManager;
    private ApplicationReviewRequestJpaController mockApplicationReviewRequestJpaController;
    private PersonJpaController mockPersonJpaController;
    private UserAccountManagementServiceLocal mockUserAccountManagementServiceLocal;
    
    public HODRecommendationUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new HODRecommendationServicesMockUnit();
        
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockRecommendationReportJpaController = mock(RecommendationReportJpaController.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockNotificationService = mock(NotificationService.class);
        mockApplicationServices = mock(ApplicationServicesUtil.class);
        mockAmmendRequestJpaController = mock(AmmendRequestJpaController.class);
        mockCal = mock(GregorianCalendar.class);
        mockTransactionController = mock(TransactionController.class);
        mockEntityManager = mock(EntityManager.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockApplicationReviewRequestJpaController = mock(ApplicationReviewRequestJpaController.class);
        mockPersonJpaController = mock(PersonJpaController.class);
        mockUserAccountManagementServiceLocal = mock(UserAccountManagementServiceLocal.class);
        
        instance.setaSEJB(mockApplicationServices);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setgCal(mockCal);
        instance.setTransactionController(mockTransactionController);
        instance.setEntityManager(mockEntityManager);
        instance.setdAOFactory(mockDAOFactory);
        instance.setUserAccountManagementServiceLocal(mockUserAccountManagementServiceLocal);
        
        when(mockDAOFactory.createAmmendRequestDAO()).thenReturn(mockAmmendRequestJpaController);
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
        when(mockDAOFactory.createRecommendationReportDAO()).thenReturn(mockRecommendationReportJpaController);
        when(mockDAOFactory.createApplicationReviewRequestDAO()).thenReturn(mockApplicationReviewRequestJpaController);
        when(mockDAOFactory.createPersonDAO()).thenReturn(mockPersonJpaController);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
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
        
        int start = 0;
        int max = 5; 
        
        try
        {
            instance.loadPendingApplications(mockSession, start, max);
            
            verify(mockApplicationServices).loadPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED, start, max);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockRecommendationReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockAmmendRequestJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
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
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        int start = 0;
        int max = 5; 
        
        try
        {
            instance.countTotalPendingApplications(mockSession);
            
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockRecommendationReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockAmmendRequestJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
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
    public void testDeclineAppliction() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(new Person("f12236731"));
        when(mockApplication.getGrantHolder()).thenReturn(new Person("u12345678"));
        
        String reason = "Chilling...";
        
        when(mockDBEntitiesFactory.createNotificationEntity(null, new Person("f12236731"), "Application recommendation declined", "Please note that the recommendation for the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createNotificationEntity(null, new Person("u12345678"), "Application recommendation declined", "Please note that the recommendation for the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the grant holder of. The reason for this is as follows: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        try
        {
            instance.declineAppliction(mockSession, mockApplication, reason);
            
            verify(mockTransactionController, times(2)).StartTransaction();
            verify(mockApplicationServices).declineAppliction(mockSession, mockApplication, reason);
            verify(mockDBEntitiesFactory).createNotificationEntity(null, new Person("f12236731"), "Application recommendation declined", "Please note that the recommendation for the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason);
            verify(mockDBEntitiesFactory).createNotificationEntity(null, new Person("u12345678"), "Application recommendation declined", "Please note that the recommendation for the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the grant holder of. The reason for this is as follows: " + reason);
            verify(mockTransactionController).CommitTransaction();
            verify(mockSession).getUser();
            // TODO: Run test on verify(mockNotificationService).sendBatchNotifications(mockSession, null, true);
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockRecommendationReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockAmmendRequestJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
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
    // TODO: @Test
    public void testAmmendAppliction() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(new Person("f12236731"));
        when(mockApplication.getGrantHolder()).thenReturn(new Person("u12345678"));
        
        String reason = "Chilling...";
        
        when(mockDBEntitiesFactory.createNotificationEntity(null, new Person("f12236731"), "Application ammendment request", "Please note that the HOD has requested ammendment for the application '" + mockApplication.getProjectTitle() + "' for which you are the grant holder of. The reason for this is as follows: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        //when(mockCal.getTime()).thenReturn(new Date());
        
        AmmendRequest mockAmmendRequest = new AmmendRequest(Long.MIN_VALUE);
        when(mockDBEntitiesFactory.createAmmendRequestEntity(mockApplication, new Person("u12345678"), reason, mockCal.getTime())).thenReturn(mockAmmendRequest);
        try
        {
            instance.ammendAppliction(mockSession, mockApplication, reason);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockDAOFactory).createAmmendRequestDAO();
            verify(mockApplication).setStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockDBEntitiesFactory).createAmmendRequestEntity(mockApplication, new Person("u12236731"), reason, mockCal.getTime());
            // TODO: verify(mockAmmendRequestJpaController).create(mockAmmendRequest);
            verify(mockDBEntitiesFactory).createNotificationEntity(null, new Person("f12236731"), "Application ammendment request", "Please note that the grant holder has requested ammendment for the application '" + mockApplication.getProjectTitle() + "' for which you are the fellow of. The reason for this is as follows: " + reason);
            verify(mockTransactionController).CommitTransaction();
            verify(mockSession, times(2)).getUser();
            // TODO: Run test on verify(mockNotificationService).sendBatchNotifications(mockSession, null, true);
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockRecommendationReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockAmmendRequestJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
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
    public void testRecommendApplicationWithoutDeansToEndorse() throws Exception {
        /*Session mockSession = mock(Session.class);
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
        }*/
    }
    
    @Test
    public void testRequestApplicationWithDeansToEndorse() throws Exception {        
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
    
    /**
     * Test of getDeansOfApplication method, of class HODRecommendationServices.
     */
    @Test
    public void testGetDeansOfApplication() throws Exception {        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        
        try
        {
            // Test returned value...
            instance.getDeansOfApplication(mockSession, mockApplication);
            
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplicationJpaController).findAllDeansOfficeMembersWhoCanEndorseApplication(mockApplication);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockRecommendationReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockAmmendRequestJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    /**
     * Test of requestSpecificDeanToReview method, of class HODRecommendationServices.
     */
    // TODO: @Test
    public void testRequestSpecificDeanToReview() throws Exception {        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        
        Person mockHod = new Person("u12345678"); // TODO: make this into a spied object...
        
        when(mockApplicationJpaController.findApplication(mockApplication.getApplicationID())).thenReturn(mockApplication);
        
        try
        {
            // Test returned value...
            instance.requestSpecificDeanToReview(mockSession, mockApplication, mockHod);
            
            verify(mockDAOFactory).createApplicationReviewRequestDAO();
            verify(mockDAOFactory).createPersonDAO();
            verify(mockApplicationReviewRequestJpaController).findAllRequestsThatHaveBeenRequestForApplicationAs(mockApplication, com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD); // TODO: Create test case catering for this return substance
            verify(mockUserAccountManagementServiceLocal).generateOnDemandAccount(mockSession, "You have been requested to review a post doctoral fellowship for recommendation consideration.", true, mockHod);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockRecommendationReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockAmmendRequestJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
}
