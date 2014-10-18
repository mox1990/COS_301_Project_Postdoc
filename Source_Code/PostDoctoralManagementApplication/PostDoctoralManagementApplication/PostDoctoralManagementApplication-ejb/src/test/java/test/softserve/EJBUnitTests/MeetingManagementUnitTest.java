/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.CommitteeMeetingJpaController;
import com.softserve.persistence.DBDAO.MinuteCommentJpaController;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.CommitteeMeeting;
import com.softserve.persistence.DBEntities.MinuteComment;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.applicationservices.MeetingManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBDAO.RecommendationReportJpaController;
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
import test.softserve.MockEJBClasses.HODRecommendationServicesMockUnit;
import test.softserve.MockEJBClasses.MeetingManagementServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class MeetingManagementUnitTest {
    private MeetingManagementServiceMockUnit instance;
    
    private DBEntitiesFactory mockDBEntitiesFactory;
    private NotificationService mockNotificationService;
    private GregorianCalendar mockCal;
    private TransactionController mockTransactionController;
    private DAOFactory mockDAOFactory;
    private EntityManager mockEntityManager;
    private CommitteeMeetingJpaController mockCommitteeMeetingJpaController;
    private PersonJpaController mockPersonJpaController;
    private MinuteCommentJpaController mockMinuteCommentJpaController;
    
    public MeetingManagementUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new MeetingManagementServiceMockUnit();
        
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockNotificationService = mock(NotificationService.class);
        mockCal = mock(GregorianCalendar.class);
        mockTransactionController = mock(TransactionController.class);
        mockEntityManager = mock(EntityManager.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockCommitteeMeetingJpaController = mock(CommitteeMeetingJpaController.class);
        mockPersonJpaController = mock(PersonJpaController.class);        
        mockMinuteCommentJpaController = mock(MinuteCommentJpaController.class);
        
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setgCal(mockCal);
        instance.setTransactionController(mockTransactionController);
        instance.setEntityManager(mockEntityManager);
        instance.setdAOFactory(mockDAOFactory);
        
        when(mockDAOFactory.createCommitteeMeetingDAO()).thenReturn(mockCommitteeMeetingJpaController);
        when(mockDAOFactory.createPersonDAO()).thenReturn(mockPersonJpaController);
        when(mockDAOFactory.createMinuteCommentDAO()).thenReturn(mockMinuteCommentJpaController);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createMeeting method, of class MeetingManagementService.
     */
    @Test
    public void testCreateMeeting() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        CommitteeMeeting mockCommitteeMeeting = mock(CommitteeMeeting.class);
        try
        {
            instance.createMeeting(mockSession, mockCommitteeMeeting);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createCommitteeMeetingDAO();
            verify(mockCommitteeMeetingJpaController).create(mockCommitteeMeeting);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCommitteeMeetingJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);     
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    //TODO: write more test cases...
    /**
     * Test of updateMeeting method, of class MeetingManagementService.
     */
    @Test
    public void testUpdateMeetingWithoutAttendence() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        CommitteeMeeting mockCommitteeMeeting = mock(CommitteeMeeting.class);
        when(mockCommitteeMeeting.getStartDate()).thenReturn(mock(Date.class));
        when(mockCommitteeMeetingJpaController.findCommitteeMeeting(mockCommitteeMeeting.getMeetingID())).thenReturn(mockCommitteeMeeting);
        
        try
        {
            instance.updateMeeting(mockSession, mockCommitteeMeeting);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createCommitteeMeetingDAO();
            verify(mockCommitteeMeetingJpaController).findCommitteeMeeting(mockCommitteeMeeting.getMeetingID());
            verify(mockCommitteeMeetingJpaController).edit(mockCommitteeMeeting);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            verify(mockCal).getTime();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCommitteeMeetingJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);     
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of cancelMeeting method, of class MeetingManagementService.
     */
    @Test
    public void testCancelMeeting() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        CommitteeMeeting mockCommitteeMeeting = mock(CommitteeMeeting.class);
        when(mockCommitteeMeeting.getStartDate()).thenReturn(mock(Date.class));
        when(mockCommitteeMeetingJpaController.findCommitteeMeeting(mockCommitteeMeeting.getMeetingID())).thenReturn(mockCommitteeMeeting);
        
        try
        {
            instance.cancelMeeting(mockSession, mockCommitteeMeeting);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createCommitteeMeetingDAO();
            verify(mockCommitteeMeetingJpaController).destroy(mockCommitteeMeeting.getMeetingID());
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            verify(mockCal).getTime();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCommitteeMeetingJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);     
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    /**
     * Test of startMeeting method, of class MeetingManagementService.
     */
    @Test
    public void testStartMeetingSuccess() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        CommitteeMeeting mockCommitteeMeeting = mock(CommitteeMeeting.class);
        when(mockCommitteeMeetingJpaController.findCommitteeMeeting(mockCommitteeMeeting.getMeetingID())).thenReturn(mockCommitteeMeeting);
        
        try
        {
            instance.startMeeting(mockSession, mockCommitteeMeeting);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createCommitteeMeetingDAO();
            verify(mockCommitteeMeetingJpaController).edit(mockCommitteeMeeting);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            verify(mockCal).getTime();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCommitteeMeetingJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);     
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of endMeeting method, of class MeetingManagementService.
     */
    @Test
    public void testEndMeeting() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        CommitteeMeeting mockCommitteeMeeting = mock(CommitteeMeeting.class);
        when(mockCommitteeMeeting.getStartDate()).thenReturn(mock(Date.class));
        when(mockCommitteeMeetingJpaController.findCommitteeMeeting(mockCommitteeMeeting.getMeetingID())).thenReturn(mockCommitteeMeeting);
        
        try
        {
            instance.startMeeting(mockSession, mockCommitteeMeeting);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createCommitteeMeetingDAO();
            // TODO: add parameter control...
            verify(mockCommitteeMeetingJpaController).edit(mockCommitteeMeeting);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            verify(mockCal).getTime();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCommitteeMeetingJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);     
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of addMinuteComment method, of class MeetingManagementService.
     */
    // TODO: @Test
    public void testAddMinuteComment() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        CommitteeMeeting mockCommitteeMeeting = mock(CommitteeMeeting.class);
        mockCal = new GregorianCalendar(2014, 9, 17);
        when(mockCommitteeMeeting.getStartDate()).thenReturn(mockCal.getTime());
        mockCal = new GregorianCalendar(2013, 9, 17);
        when(mockCommitteeMeeting.getEndDate()).thenReturn(null);
        System.out.println(mockCal.getTime().toString() + "vs" + mockCommitteeMeeting.getStartDate());
        MinuteComment mockMinuteComment = mock(MinuteComment.class);
        when(mockMinuteComment.getMeeting()).thenReturn(mockCommitteeMeeting);
        
        when(mockCommitteeMeetingJpaController.findCommitteeMeeting(mockCommitteeMeeting.getMeetingID())).thenReturn(mockCommitteeMeeting);
        
        try
        {
            instance.addMinuteComment(mockSession, mockMinuteComment);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createMinuteCommentDAO();
            // TODO: add parameter control...
            verify(mockMinuteCommentJpaController).create(mockMinuteComment);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            //verify(mockCal).getTime();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            //verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCommitteeMeetingJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);     
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    
    
    /**
     * Test of getAllMeetings method, of class MeetingManagementService.
     */
    @Test
    public void testGetAllMeetings() throws Exception {
        Session mockSession = mock(Session.class);
        try
        {
            instance.getAllMeetings(mockSession);
            
            verify(mockDAOFactory).createCommitteeMeetingDAO();
            verify(mockCommitteeMeetingJpaController).findCommitteeMeetingEntities();
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCommitteeMeetingJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);     
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    /**
     * Test of getAllActiveMeetings method, of class MeetingManagementService.
     */
    @Test
    public void testGetAllActiveMeetings() throws Exception {
        Session mockSession = mock(Session.class);
        try
        {
            instance.getAllActiveMeetings(mockSession);
            
            verify(mockDAOFactory).createCommitteeMeetingDAO();
            verify(mockCommitteeMeetingJpaController).findAllActiveCommitteeMeetings();
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCommitteeMeetingJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);     
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    
    /**
     * Test of getAllActiveMeetingsForWhichUserIsToAttend method, of class MeetingManagementService.
     */
    @Test
    public void testGetAllActiveMeetingsForWhichUserIsToAttend() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        try
        {
            instance.getAllActiveMeetingsForWhichUserIsToAttend(mockSession);
            
            verify(mockDAOFactory).createCommitteeMeetingDAO();
            verify(mockCommitteeMeetingJpaController).findAllActiveCommitteeMeetings();
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCommitteeMeetingJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);     
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    // TODO: Create more test cases for this
    
    /**
     * Test of getAllConcludedMeetings method, of class MeetingManagementService.
     */
    @Test
    public void testGetAllConcludedMeetings() throws Exception {
        Session mockSession = mock(Session.class);
        try
        {
            instance.getAllConcludedMeetings(mockSession);
            
            verify(mockDAOFactory).createCommitteeMeetingDAO();
            verify(mockCommitteeMeetingJpaController).findAllConcludedCommitteeMeetings();
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCommitteeMeetingJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);     
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    /**
     * Test of getAllPostDocCommitteeMembers method, of class MeetingManagementService.
     */
    @Test
    public void testGetAllPostDocCommitteeMembers() throws Exception {
        Session mockSession = mock(Session.class);
        try
        {
            instance.getAllPostDocCommitteeMembers(mockSession);
            
            verify(mockDAOFactory).createPersonDAO();
            verify(mockPersonJpaController).findUserBySecurityRoleWithAccountStatus(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_POSTDOCTORAL_COMMITTEE_MEMBER, com.softserve.auxiliary.constants.PersistenceConstants.ACCOUNT_STATUS_ACTIVE);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCommitteeMeetingJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);     
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    /**
     * Test of getAllStillToBeHeldMeetings method, of class MeetingManagementService.
     */
    @Test
    public void testGetAllStillToBeHeldMeetings() throws Exception {
        Session mockSession = mock(Session.class);
        try
        {
            instance.getAllStillToBeHeldMeetings(mockSession);
            
            verify(mockDAOFactory).createCommitteeMeetingDAO();
            verify(mockCommitteeMeetingJpaController).findAllStillToBeHeldCommitteeMeetings();
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            // TODO: verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCommitteeMeetingJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);     
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
}
