/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.AnnouncementJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBEntities.Announcement;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.ejb.nonapplicationservices.AnnouncementManagementServiceLocal;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import com.softserve.transactioncontrollers.TransactionController;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import test.softserve.MockEJBClasses.AnnouncementManagementMockUnit;

/**
 *
 * @author kgothatso
 */
public class AnnouncementManagementUnitTest {
    private AnnouncementManagementMockUnit instance;
    private DAOFactory mockDAOFactory;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private TransactionController mockTransactionController;
    private AnnouncementJpaController mockAnnouncementJpaController;
    private Session mockSession;
    private EntityManager mockEntityManager;
    
    public AnnouncementManagementUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new AnnouncementManagementMockUnit();
        
        // Application based entities
        mockDAOFactory = mock(DAOFactory.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockTransactionController = mock(TransactionController.class);
        mockEntityManager = mock(EntityManager.class);
        
        //Client based entities
        mockAnnouncementJpaController = mock(AnnouncementJpaController.class);
        mockSession = mock(Session.class);
        
        when(mockDAOFactory.createAnnouncementDAO()).thenReturn(mockAnnouncementJpaController);
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
        
        instance.setdAOFactory(mockDAOFactory);
        instance.setdBEntitiesFactory(mockDBEntitiesFactory);
        instance.setTransactionController(mockTransactionController);
        instance.setEm(mockEntityManager);
        
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadAllActiveAnnouncements method, of class AnnouncementManagementService.
     */
    @Test
    public void testLoadAllActiveAnnouncementsReturnEmpty() throws Exception {
        Announcement mockAnnouncement = mock(Announcement.class);
        
        //when(new ApplicationStageStatus(mockApplication.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, mockApplication.getFellow())).thenReturn(mockApplicationStageStatus);
        try
        {
            //Execute function
            List<Announcement> announcements = instance.loadAllActiveAnnouncements();
            
            verify(mockAnnouncementJpaController).findAllActiveAnnouncements();
            if(!announcements.isEmpty())
            {
                fail("Announcements were not empty");
            }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }
    
    @Test
    public void testLoadAllActiveAnnouncements() throws Exception {
        Announcement mockAnnouncement = mock(Announcement.class);
        List<Announcement> a = new ArrayList<>();
        a.add(new Announcement(Long.MIN_VALUE)); a.add(new Announcement(Long.MAX_VALUE));
        when(mockAnnouncementJpaController.findAllActiveAnnouncements()).thenReturn(a);
        //when(new ApplicationStageStatus(mockApplication.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, mockApplication.getFellow())).thenReturn(mockApplicationStageStatus);
        try
        {
            //Execute function
            List<Announcement> announcements = instance.loadAllActiveAnnouncements();
            
            verify(mockAnnouncementJpaController).findAllActiveAnnouncements();
            if(announcements.size() != a.size())
            {
                fail("Announcements were not required amount");
            }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }

    /**
     * Test of loadAllPendingAnnouncements method, of class AnnouncementManagementService.
     */
    @Test
    public void testLoadAllPendingAnnouncements() throws Exception {
        Announcement mockAnnouncement = mock(Announcement.class);
        List<Announcement> a = new ArrayList<>();
        a.add(new Announcement(Long.MIN_VALUE)); a.add(new Announcement(Long.MAX_VALUE));
        when(mockAnnouncementJpaController.findPendingAnnouncements()).thenReturn(a);
        //when(new ApplicationStageStatus(mockApplication.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, mockApplication.getFellow())).thenReturn(mockApplicationStageStatus);
        try
        {
            //Execute function
            List<Announcement> announcements = instance.loadAllPendingAnnouncements();
            
            verify(mockAnnouncementJpaController).findPendingAnnouncements();
            if(announcements.size() != a.size())
            {
                fail("Announcements were not required amount");
            }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }

    /**
     * Test of createAnnouncement method, of class AnnouncementManagementService.
     */
    @Test
    public void testCreateAnnouncement() throws Exception {
        Announcement mockAnnouncement = mock(Announcement.class);
        
        //when(new ApplicationStageStatus(mockApplication.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, mockApplication.getFellow())).thenReturn(mockApplicationStageStatus);
        try
        {
            //Execute function
            instance.createAnnouncement(mockSession, mockAnnouncement);
            
            verify(mockAnnouncementJpaController).create(mockAnnouncement);
            verifyNoMoreInteractions(mockAnnouncementJpaController);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }

    /**
     * Test of updateAnnouncement method, of class AnnouncementManagementService.
     */
    @Test
    public void testUpdateAnnouncement() throws Exception {
        Announcement mockAnnouncement = mock(Announcement.class);
        
        //when(new ApplicationStageStatus(mockApplication.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, mockApplication.getFellow())).thenReturn(mockApplicationStageStatus);
        try
        {
            //Execute function
            instance.updateAnnouncement(mockSession, mockAnnouncement);
            
            verify(mockAnnouncementJpaController).edit(mockAnnouncement);
            verifyNoMoreInteractions(mockAnnouncementJpaController);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }

    /**
     * Test of removeAnnouncement method, of class AnnouncementManagementService.
     */
    @Test
    public void testRemoveAnnouncement() throws Exception {
        Announcement mockAnnouncement = new Announcement(Long.MAX_VALUE);
        
        //when(new ApplicationStageStatus(mockApplication.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, mockApplication.getFellow())).thenReturn(mockApplicationStageStatus);
        try
        {
            //Execute function
            instance.removeAnnouncement(mockSession, mockAnnouncement);
            
            verify(mockAnnouncementJpaController).destroy(mockAnnouncement.getAnnouncementID());
            verifyNoMoreInteractions(mockAnnouncementJpaController);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }
    
}
