/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.ejb.applicationservices.ForwardAndRewindServicesLocal;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.persistence.DBDAO.ForwardAndRewindReportJpaController;
import com.softserve.persistence.DBDAO.RefereeReportJpaController;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.ForwardAndRewindReport;
import com.softserve.persistence.DBEntities.Person;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import test.softserve.MockEJBClasses.ForwardAndRewindServicesMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ForwardAndRewindServicesTest {
    private ForwardAndRewindServicesMockUnit instance;
    
    private DBEntitiesFactory mockDBEntitiesFactory;
    private DAOFactory mockDAOFactory;
    private TransactionController mockTransactionController;
    private ApplicationServicesUtil mockApplicationServicesUtil;
    private GregorianCalendar mockGregorianCalendar;
    private EntityManager mockEm; 
    private ApplicationJpaController mockApplicationJpaController;
    private ForwardAndRewindReportJpaController mockForwardAndRewindReportJpaController;
    private RefereeReportJpaController mockRefereeReportJpaController;
    private ApplicationReviewRequestJpaController mockApplicationReviewRequestJpaController;
    private NotificationServiceLocal mockNotificationServiceLocal;
    
    public ForwardAndRewindServicesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new ForwardAndRewindServicesMockUnit();
        
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockTransactionController = mock(TransactionController.class);
        mockApplicationServicesUtil = mock(ApplicationServicesUtil.class);
        mockGregorianCalendar = mock(GregorianCalendar.class);
        mockEm = mock(EntityManager.class);
        mockRefereeReportJpaController = mock(RefereeReportJpaController.class);
        mockApplicationReviewRequestJpaController = mock(ApplicationReviewRequestJpaController.class);
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockForwardAndRewindReportJpaController = mock(ForwardAndRewindReportJpaController.class);
        mockNotificationServiceLocal = mock(NotificationServiceLocal.class);
        
        instance.setApplicationServicesUtil(mockApplicationServicesUtil);
        instance.setEm(mockEm);
        instance.setGregorianCalendar(mockGregorianCalendar);
        instance.setTransactionController(mockTransactionController);
        instance.setdAOFactory(mockDAOFactory);
        instance.setdBEntitiesFactory(mockDBEntitiesFactory);
        instance.setNotificationServiceLocal(mockNotificationServiceLocal);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
        when(mockDAOFactory.createRefereeReportDAO()).thenReturn(mockRefereeReportJpaController);
        when(mockDAOFactory.createApplicationReviewRequestDAO()).thenReturn(mockApplicationReviewRequestJpaController);
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
        when(mockDAOFactory.createForwardAndRewindReportDAO()).thenReturn(mockForwardAndRewindReportJpaController);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of forwardApplication method, of class ForwardAndRewindServices.
     */
    @Test
    public void testForwardApplicationToSubmitted() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getPersonList()).thenReturn(new ArrayList());
        String toStatus = com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED;
        String reason = "Running test...";
        try
        {
            instance.forwardApplication(mockSession, mockApplication, toStatus, reason);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of rewindApplication method, of class ForwardAndRewindServices.
     */
    @Test
    public void testRewindApplication() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getPersonList()).thenReturn(new ArrayList());
        String toStatus = com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED;
        String reason = "Running test...";
        try
        {
            instance.rewindApplication(mockSession, mockApplication, toStatus, reason);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of loadMovableApplications method, of class ForwardAndRewindServices.
     */
    @Test
    public void testLoadMovableApplications() throws Exception {
        Session mockSession = mock(Session.class);
        
        try
        {
            List<Application> applications = instance.loadMovableApplications(mockSession);
            
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplicationJpaController).findAllApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, 0, Integer.MAX_VALUE);
            verify(mockApplicationJpaController).findAllApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED, 0, Integer.MAX_VALUE);
            verify(mockApplicationJpaController).findAllApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED, 0, Integer.MAX_VALUE);
            verify(mockApplicationJpaController).findAllApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED, 0, Integer.MAX_VALUE);
            verify(mockApplicationJpaController).findAllApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, 0, Integer.MAX_VALUE);
            verify(mockApplicationJpaController).findAllApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED, 0, Integer.MAX_VALUE);
            verify(mockApplicationJpaController).findAllApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE, 0, Integer.MAX_VALUE);
            verify(mockApplicationJpaController).findAllApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED, 0, Integer.MAX_VALUE);
            verify(mockEm).close();
            
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockEm);
            verifyNoMoreInteractions(mockRefereeReportJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockApplicationReviewRequestJpaController);
            verifyNoMoreInteractions(mockForwardAndRewindReportJpaController);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
}
