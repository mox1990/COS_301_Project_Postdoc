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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
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
        
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setgCal(mockCal);
        instance.setTransactionController(mockTransactionController);
        instance.setEntityManager(mockEntityManager);
        instance.setdAOFactory(mockDAOFactory);
        
        when(mockDAOFactory.createCommitteeMeetingDAO()).thenReturn(mockCommitteeMeetingJpaController);
        when(mockDAOFactory.createPersonDAO()).thenReturn(mockPersonJpaController);
        
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
        
    }

    /**
     * Test of updateMeeting method, of class MeetingManagementService.
     */
    @Test
    public void testUpdateMeetingWithoutAttendence() throws Exception {
        
    }

    /**
     * Test of startMeeting method, of class MeetingManagementService.
     */
    @Test
    public void testStartMeeting() throws Exception {
        
    }

    /**
     * Test of endMeeting method, of class MeetingManagementService.
     */
    @Test
    public void testEndMeeting() throws Exception {
        
    }

    /**
     * Test of addMinuteComment method, of class MeetingManagementService.
     */
    @Test
    public void testAddMinuteComment() throws Exception {
        
    }
    
    /**
     * Test of cancleMeeting method, of class MeetingManagementService.
     */
    @Test
    public void testCancleMeeting() throws Exception {
        
    }
    
    /**
     * Test of getAllMeetings method, of class MeetingManagementService.
     */
    @Test
    public void testGetAllMeetings() throws Exception {
        
    }
    
    /**
     * Test of getAllActiveMeetings method, of class MeetingManagementService.
     */
    @Test
    public void testGetAllActiveMeetings() throws Exception {
        
    }
    
    
    /**
     * Test of getAllActiveMeetingsForWhichUserIsToAttend method, of class MeetingManagementService.
     */
    @Test
    public void testGetAllActiveMeetingsForWhichUserIsToAttend() throws Exception {
        
    }
    
    
    /**
     * Test of getAllConcludedMeetings method, of class MeetingManagementService.
     */
    @Test
    public void testGetAllConcludedMeetings() throws Exception {
        
    }
    
    /**
     * Test of getAllPostDocCommitteeMembers method, of class MeetingManagementService.
     */
    @Test
    public void testGetAllPostDocCommitteeMembers() throws Exception {
        
    }
    
    /**
     * Test of getAllStillToBeHeldMeetings method, of class MeetingManagementService.
     */
    @Test
    public void testGetAllStillToBeHeldMeetings() throws Exception {
        
    }
}
