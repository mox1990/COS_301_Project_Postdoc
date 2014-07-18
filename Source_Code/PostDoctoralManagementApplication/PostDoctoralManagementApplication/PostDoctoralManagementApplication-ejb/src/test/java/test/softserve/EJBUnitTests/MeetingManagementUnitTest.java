/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.CommitteeMeetingJpaController;
import com.softserve.DBDAO.MinuteCommentJpaController;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.MinuteComment;
import com.softserve.DBEntities.Person;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.MeetingManagementServiceLocal;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
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
import test.softserve.MockEJBClasses.MeetingManagementServiceMockUnit;

/**
 *
 * @author kgothatso
 */
public class MeetingManagementUnitTest {
    
    public MeetingManagementUnitTest() {
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
     * Test of createMeeting method, of class MeetingManagementService.
     */
    @Test
    public void testCreateMeeting() throws Exception {
        
        MeetingManagementServiceMockUnit instance = new MeetingManagementServiceMockUnit();
        
        //Setup dependices mocks
        CommitteeMeetingJpaController mockCommitteeMeetingJpaController = mock(CommitteeMeetingJpaController.class);
        MinuteCommentJpaController mockMinuteCommentJpaController = mock(MinuteCommentJpaController.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created new user account", new Person("u12019837"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        
        //Load dependices mocks' into instance
        instance.setCMDAO(mockCommitteeMeetingJpaController);
        instance.setMCDAO(mockMinuteCommentJpaController);
        instance.setUserGateway(mockUserGateway);
        instance.setDBEntitiesFactory(mockDBEntitiesFactory);
        instance.setNotificationService(mockNotificationService);
        instance.setAuditTrailService(mockAuditTrailService);
        
        //Setup parameter mocks
        CommitteeMeeting mockCommitteeMeeting = mock(CommitteeMeeting.class);
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
        
        try
        {
            //Execute function
            instance.createMeeting(mockSession, mockCommitteeMeeting);
            
            //Verify correct function behaviour
            verify(mockCommitteeMeetingJpaController).create(mockCommitteeMeeting);           
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Created new user account", new Person("u12019837"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of updateMeeting method, of class MeetingManagementService.
     */
    @Test
    public void testUpdateMeeting() throws Exception {
        
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
    
}
