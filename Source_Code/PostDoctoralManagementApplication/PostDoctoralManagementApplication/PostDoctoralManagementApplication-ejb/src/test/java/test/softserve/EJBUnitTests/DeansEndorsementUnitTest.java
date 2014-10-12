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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
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
        //instance.setTransactionController(mockTransactionController);
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
            //verify(mockApplicationServices).loadPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, startIndex, maxNumber);

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
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }
    
}

            //ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            //roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            //verify(mockApplicationServices).getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);

