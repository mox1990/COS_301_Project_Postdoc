/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
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
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.persistence.DBEntities.AmmendRequest;
import java.util.ArrayList;
import java.util.Arrays;
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
import test.softserve.MockEJBClasses.GrantHolderFinalisationServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class GrantHolderFinalisationUnitTest {
    private GrantHolderFinalisationServiceMockUnit instance;
         
    private CVManagementService mockCVManagementService;
    private PersonJpaController mockPersonJpaController;
    private CvJpaController mockCvJpaController;
    private ApplicationJpaController mockApplicationJpaController;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private NotificationService mockNotificationService;
    private ApplicationServicesUtil mockApplicationServices;
    private GregorianCalendar mockCal;
    private ApplicationReviewRequestJpaController mockApplicationReviewRequestJpaController;
    private DAOFactory mockDAOFactory;
    private UserAccountManagementServiceLocal mockUserAccountManagementServiceLocal;
    private TransactionController mockTransactionController;
    private EntityManager mockEntityManager;
    private AmmendRequestJpaController mockAmmendRequestJpaController;
    
    public GrantHolderFinalisationUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new GrantHolderFinalisationServiceMockUnit();
        
        mockCVManagementService = mock(CVManagementService.class);
        mockPersonJpaController = mock(PersonJpaController.class);
        mockCvJpaController = mock(CvJpaController.class);
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockNotificationService = mock(NotificationService.class);
        mockApplicationServices = mock(ApplicationServicesUtil.class);
        mockCal = mock(GregorianCalendar.class);
        mockApplicationReviewRequestJpaController = mock(ApplicationReviewRequestJpaController.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockUserAccountManagementServiceLocal = mock(UserAccountManagementServiceLocal.class);
        mockTransactionController = mock(TransactionController.class);
        mockEntityManager = mock(EntityManager.class);
        mockAmmendRequestJpaController = mock(AmmendRequestJpaController.class);
        
        instance.setaSEJB(mockApplicationServices);
        instance.setcVEJB(mockCVManagementService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setgCal(mockCal);
        instance.setUserAccountManagementServiceLocal(mockUserAccountManagementServiceLocal);
        instance.setEntityManager(mockEntityManager);
        instance.setTransactionController(mockTransactionController);
        instance.setdAOFactory(mockDAOFactory);
        
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
        when(mockDAOFactory.createCvDAO()).thenReturn(mockCvJpaController);
        when(mockDAOFactory.createPersonDAO()).thenReturn(mockPersonJpaController);
        when(mockDAOFactory.createApplicationReviewRequestDAO()).thenReturn(mockApplicationReviewRequestJpaController);
        when(mockDAOFactory.createAmmendRequestDAO()).thenReturn(mockAmmendRequestJpaController);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createGrantHolderCV method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testCreateGrantHolderCVWithoutCV() throws Exception {        
        Cv mockCV = mock(Cv.class);  
        Session mockSession = mock(Session.class);
        
        when(mockCVManagementService.hasCV(mockSession)).thenReturn(Boolean.FALSE);
        
        try
        {
            instance.createGrantHolderCV(mockSession, mockCV);
            
            verify(mockCVManagementService).createCV(mockSession, mockCV);
            verify(mockCVManagementService).hasCV(mockSession);
            
            verifyNoMoreInteractions(mockCV);
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void testCreateGrantHolderCVWithCV() throws Exception {        
        Cv mockCV = mock(Cv.class);  
        Session mockSession = mock(Session.class);
        
        when(mockCVManagementService.hasCV(mockSession)).thenReturn(Boolean.TRUE);
        
        try
        {
            instance.createGrantHolderCV(mockSession, mockCV);
            
            verify(mockCVManagementService).updateCV(mockSession, mockCV);
            verify(mockCVManagementService).hasCV(mockSession);
            
            verifyNoMoreInteractions(mockCV);
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    @Test
    public void testCreateGrantHolderCVNotValid() throws Exception {        
        Session mockSession = mock(Session.class);
        
        try
        {
            instance.createGrantHolderCV(mockSession, null);
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            if(!ex.getMessage().equals("CV is not valid"))
                fail("An exception should have occured");
        }
    }
    
    /**
     * Test of loadPendingApplications method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testLoadPendingApplications() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        int startIndex = 0;
        int max = 5;
        
        try
        {
            // Test returned value...
            instance.loadPendingApplications(mockSession, startIndex, max);
            
            verify(mockApplicationServices).loadPendingApplications(mockSession.getUser(), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED, startIndex, max);
            verify(mockEntityManager).close();
            verify(mockSession, times(2)).getUser();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingApplications method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testCountTotalPendingApplications() throws Exception {        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        try
        {
            instance.countTotalPendingApplications(mockSession);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(mockSession.getUser(), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
            verify(mockEntityManager).close();
            verify(mockSession, times(2)).getUser();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    /**
     * Test of saveChangesToApplication method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testSaveChangesToApplication() throws Exception
    {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(new Person("f12236731"));
         
        try
        {
            instance.saveChangesToApplication(mockSession, mockApplication);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockApplication);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService); 
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    /**
     * Test of declineAppliction method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testDeclineAppliction() throws Exception
    {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(new Person("f12236731"));
        
        String reason = "Chilling...";
        
        when(mockDBEntitiesFactory.createNotificationEntity(null, new Person("f12236731"), "Application finalisation declined", "Please note that the finalisation of the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        
        try
        {
            instance.declineAppliction(mockSession, mockApplication, reason);
            
            verify(mockTransactionController, times(2)).StartTransaction();
            verify(mockApplicationServices).declineAppliction(mockSession, mockApplication, reason);
            verify(mockDBEntitiesFactory).createNotificationEntity(null, new Person("f12236731"), "Application finalisation declined", "Please note that the finalisation of the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason);
            verify(mockTransactionController).CommitTransaction();
            verify(mockSession).getUser();
            // TODO: Run test on verify(mockNotificationService).sendBatchNotifications(mockSession, null, true);
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            // TODO: Notification stuff verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // verifyNoMoreInteractions(mockNotificationService); TODO: Setup class for the test
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    /**
     * Test of ammendAppliction method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testAmmendAppliction() throws Exception
    {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(new Person("f12236731"));
        
        String reason = "Chilling...";
        
        when(mockDBEntitiesFactory.createNotificationEntity(null, new Person("f12236731"), "Application finalisation declined", "Please note that the finalisation of the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        //when(mockCal.getTime()).thenReturn(new Date());
        
        AmmendRequest mockAmmendRequest = new AmmendRequest(Long.MIN_VALUE);
        when(mockDBEntitiesFactory.createAmmendRequestEntity(mockApplication, new Person("u12236731"), reason, mockCal.getTime())).thenReturn(mockAmmendRequest);
        try
        {
            instance.ammendAppliction(mockSession, mockApplication, reason);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockDAOFactory).createAmmendRequestDAO();
            verify(mockApplication).setStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_OPEN);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockDBEntitiesFactory).createAmmendRequestEntity(mockApplication, new Person("u12236731"), reason, mockCal.getTime());
            verify(mockAmmendRequestJpaController).create(mockAmmendRequest);
            verify(mockDBEntitiesFactory).createNotificationEntity(null, new Person("f12236731"), "Application ammendment request", "Please note that the grant holder has requested ammendment for the application '" + mockApplication.getProjectTitle() + "' for which you are the fellow of. The reason for this is as follows: " + reason);
            verify(mockTransactionController).CommitTransaction();
            verify(mockSession, times(2)).getUser();
            // TODO: Run test on verify(mockNotificationService).sendBatchNotifications(mockSession, null, true);
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            // TODO: Notification stuff verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // verifyNoMoreInteractions(mockNotificationService); TODO: Setup class for the test
            verifyNoMoreInteractions(mockApplicationServices);
            //verifyNoMoreInteractions(mockCal); TODO: Calendar checking...
            verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
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
    public void testFinaliseApplication() throws Exception // TODO: Add alternatives to this...
    {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(new Person("f12236731"));
        
        when(mockApplicationJpaController.findApplication(mockApplication.getApplicationID())).thenReturn(mockApplication);
        when(mockDBEntitiesFactory.createNotificationEntity(null, new Person("f12236731"), "Application finalised", "The application " + mockApplication.getProjectTitle() + " has been finalised by " + new Person("u12236731").getCompleteName() + ". Please review the application for recommendation.")).thenReturn(new Notification(Long.MAX_VALUE));
        //when(mockCal.getTime()).thenReturn(new Date());
        
        try
        {
            instance.finaliseApplication(mockSession, mockApplication);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplicationJpaController).findApplication(mockApplication.getApplicationID());
            verify(mockApplication).setFinalisationDate(mockCal.getTime());
            verify(mockApplication).setStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
            verify(mockApplicationJpaController).edit(mockApplication);
            // TODO: verify(mockDBEntitiesFactory).createNotificationEntity(null, new Person("f12236731"), "Application finalised", "The application " + mockApplication.getProjectTitle() + " has been finalised by " + new Person("u12236731").getCompleteName() + ". Please review the application for recommendation.");
            verify(mockTransactionController).CommitTransaction();
            // TODO: verify(mockSession, times(2)).getUser();
            // TODO: Run test on verify(mockNotificationService).sendBatchNotifications(mockSession, null, true);
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            // TODO: Notification stuff verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            // TODO: verifyNoMoreInteractions(mockDBEntitiesFactory);
            // verifyNoMoreInteractions(mockNotificationService); TODO: Setup class for the test
            verifyNoMoreInteractions(mockApplicationServices);
            // TODO: verifyNoMoreInteractions(mockCal); TODO: Calendar checking...
            // TODO: verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            // verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    @Test
    public void testFinaliseApplicationWithNotifications() throws Exception { // TODO: Populate notifications
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(new Person("f12236731"));
        
        when(mockApplicationJpaController.findApplication(mockApplication.getApplicationID())).thenReturn(mockApplication);
        when(mockDBEntitiesFactory.createNotificationEntity(null, new Person("f12236731"), "Application finalised", "The application " + mockApplication.getProjectTitle() + " has been finalised by " + new Person("u12236731").getCompleteName() + ". Please review the application for recommendation.")).thenReturn(new Notification(Long.MAX_VALUE));
        //when(mockCal.getTime()).thenReturn(new Date());
        
        try
        {
            instance.finaliseApplication(mockSession, mockApplication);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplicationJpaController).findApplication(mockApplication.getApplicationID());
            verify(mockApplication).setFinalisationDate(mockCal.getTime());
            verify(mockApplication).setStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
            verify(mockApplicationJpaController).edit(mockApplication);
            // TODO: verify(mockDBEntitiesFactory).createNotificationEntity(null, new Person("f12236731"), "Application finalised", "The application " + mockApplication.getProjectTitle() + " has been finalised by " + new Person("u12236731").getCompleteName() + ". Please review the application for recommendation.");
            verify(mockTransactionController).CommitTransaction();
            // TODO: verify(mockSession, times(2)).getUser();
            // TODO: Run test on verify(mockNotificationService).sendBatchNotifications(mockSession, null, true);
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            // TODO: Notification stuff verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            // TODO: verifyNoMoreInteractions(mockDBEntitiesFactory);
            // verifyNoMoreInteractions(mockNotificationService); TODO: Setup class for the test
            verifyNoMoreInteractions(mockApplicationServices);
            // TODO: verifyNoMoreInteractions(mockCal); TODO: Calendar checking...
            // TODO: verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            // verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    /**
     * Test of getHODsOfApplication method, of class GrantHolderFinalisationService.
     */
    @Test
    public void testGetHODsOfApplication() throws Exception
    {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        
        try
        {
            // Test returned value...
            instance.getHODsOfApplication(mockSession, mockApplication);
            
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplicationJpaController).findAllHODsWhoCanRecommendApplication(mockApplication);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    /**
     * Test of requestSpecificHODtoReview method, of class GrantHolderFinalisationService.
     */
    //@Test
    public void testRequestSpecificHODtoReviewNewUser() throws Exception
    {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        
        Person mockHod = new Person("u12345678"); // TODO: make this into a spied object...
        
        when(mockApplicationJpaController.findApplication(mockApplication.getApplicationID())).thenReturn(mockApplication);
        
        try
        {
            // Test returned value...
            instance.requestSpecificHODtoReview(mockSession, mockApplication, mockHod);
            
            verify(mockDAOFactory).createApplicationReviewRequestDAO();
            verify(mockDAOFactory).createPersonDAO();
            verify(mockApplicationReviewRequestJpaController).findAllRequestsThatHaveBeenRequestForApplicationAs(mockApplication, com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD); // TODO: Create test case catering for this return substance
            verify(mockUserAccountManagementServiceLocal).generateOnDemandAccount(mockSession, "You have been requested to review a post doctoral fellowship for recommendation consideration.", true, mockHod);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    /**
     * Test of requestSpecificHODtoReview method, of class GrantHolderFinalisationService.
     */
    //@Test
    public void testRequestSpecificHODtoReviewAlreadyInSystem() throws Exception
    {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        
        Person mockHod = new Person("u12345678"); // TODO: make this into a spied object...
        
        when(mockApplicationJpaController.findApplication(mockApplication.getApplicationID())).thenReturn(mockApplication);
        // TODO: when(mockPersonJpaController.findUserBySystemIDOrEmail("u12345678")).thenReturn(mockHod);
        try
        {
            // Test returned value...
            instance.requestSpecificHODtoReview(mockSession, mockApplication, mockHod);
            
            verify(mockDAOFactory).createApplicationReviewRequestDAO();
            verify(mockDAOFactory).createPersonDAO();
            verify(mockApplicationReviewRequestJpaController).findAllRequestsThatHaveBeenRequestForApplicationAs(mockApplication, com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD); // TODO: Create test case catering for this return substance
            verify(mockUserAccountManagementServiceLocal).generateOnDemandAccount(mockSession, "You have been requested to review a post doctoral fellowship for recommendation consideration.", true, mockHod);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
}
