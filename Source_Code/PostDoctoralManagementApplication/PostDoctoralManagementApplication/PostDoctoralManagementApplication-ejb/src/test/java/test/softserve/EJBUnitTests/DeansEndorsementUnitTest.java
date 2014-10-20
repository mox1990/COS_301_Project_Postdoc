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
import com.softserve.ejb.applicationservices.DeansEndorsementServiceLocal;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;

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
import test.softserve.MockEJBClasses.DRISApprovalServiceMockUnit;
import test.softserve.MockEJBClasses.DeansEndorsementServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class DeansEndorsementUnitTest {
    private DeansEndorsementServiceMockUnit instance;
         
    private ApplicationJpaController mockApplicationJpaController;
    private EndorsementJpaController mockEndorsementJpaController;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private NotificationService mockNotificationService;
    private ApplicationServicesUtil mockApplicationServices;
    private TransactionController mockTransactionController;
    private DAOFactory mockDAOFactory;
    private EntityManager mockEntityManager;
    private GregorianCalendar mockGregorianCalendar;
    
    public DeansEndorsementUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new DeansEndorsementServiceMockUnit();
        
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockEndorsementJpaController = mock(EndorsementJpaController.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockNotificationService = mock(NotificationService.class);
        mockApplicationServices = mock(ApplicationServicesUtil.class);
        mockTransactionController = mock(TransactionController.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockEntityManager = mock(EntityManager.class);
        mockGregorianCalendar = mock(GregorianCalendar.class);
        
        instance.setaSEJB(mockApplicationServices);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setdAOFactory(mockDAOFactory);
        instance.setTransactionController(mockTransactionController);
        instance.setEm(mockEntityManager);
        instance.setgCal(mockGregorianCalendar);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
        when(mockDAOFactory.createEndorsementDAO()).thenReturn(mockEndorsementJpaController);
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
            
            verify(mockApplicationServices).loadPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, startIndex, maxNumber);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockEndorsementJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockTransactionController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
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
            
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockEndorsementJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockTransactionController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of declineApplication method, of class DeansEndorsementService.
     */
    @Test
    public void testDeclineApplication() throws Exception {
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
        
        when(mockDBEntitiesFactory.createNotificationEntity(null, mockPerson, "Application endorsement declined", "Please note that the endorsement of the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createNotificationEntity(null, mockApplication.getGrantHolder(), "Application endorsement declined", "Please note that the endorsement of the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the grant holder of. The reason for this is as follows: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);

        try
        {
            instance.declineApplication(mockSession, mockApplication, reason);
            
            verify(mockTransactionController, times(2)).StartTransaction();
            verify(mockApplicationServices).declineAppliction(mockSession, mockApplication, reason);
            verify(mockDBEntitiesFactory).createNotificationEntity(null, mockPerson, "Application endorsement declined", "Please note that the endorsement of the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason);
            verify(mockDBEntitiesFactory).createNotificationEntity(null, new Person("s25030403"), "Application endorsement declined", "Please note that the endorsement of the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the grant holder of. The reason for this is as follows: " + reason);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockEndorsementJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockTransactionController);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
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
        
        Endorsement mockEndorsement = mock(Endorsement.class);
        // when(mockEndorsement.get()).thenReturn(Long.MAX_VALUE);

        try
        {
            instance.endorseApplication(mockSession, mockApplication, mockEndorsement);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
}