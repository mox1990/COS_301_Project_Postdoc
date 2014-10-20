/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.EligiblityReportJpaController;
import com.softserve.persistence.DBDAO.FundingCostJpaController;
import com.softserve.persistence.DBDAO.FundingReportJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBEntities.AcademicQualification;
import com.softserve.persistence.DBEntities.AmmendRequest;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.EligiblityReport;
import com.softserve.persistence.DBEntities.FundingReport;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.applicationservices.DRISApprovalServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.persistence.DBDAO.ResearchFellowInformationJpaController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import test.softserve.MockEJBClasses.DRISApprovalServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class DRISApprovalUnitTest {
    DRISApprovalServiceMockUnit instance;
        
    ApplicationJpaController mockApplicationJpaController;
    FundingReportJpaController mockFundingReportJpaController;
    DBEntitiesFactory mockDBEntitiesFactory;
    NotificationService mockNotificationService;
    ApplicationServicesUtil mockApplicationServices;
    EligiblityReportJpaController mockEligiblityReportJpaController;
    GregorianCalendar mockCal;
    PersonJpaController mockPersonJpaController;
    FundingCostJpaController mockFundingCostJpaController; 
    TransactionController mockTransactionController;
    DAOFactory mockDAOFactory;
    EntityManager mockEntityManager;
    ResearchFellowInformationJpaController mockResearchFellowInformationJpaController;
    
    public DRISApprovalUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new DRISApprovalServiceMockUnit();
        
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockFundingReportJpaController = mock(FundingReportJpaController.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockNotificationService = mock(NotificationService.class);
        mockApplicationServices = mock(ApplicationServicesUtil.class);
        mockEligiblityReportJpaController = mock(EligiblityReportJpaController.class);
        mockCal = mock(GregorianCalendar.class);
        mockPersonJpaController = mock(PersonJpaController.class);
        mockFundingCostJpaController = mock(FundingCostJpaController.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockTransactionController = mock(TransactionController.class);
        mockEntityManager = mock(EntityManager.class);
        mockResearchFellowInformationJpaController = mock(ResearchFellowInformationJpaController.class);
        
        instance.setaSEJB(mockApplicationServices);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setgCal(mockCal);
        instance.setTransactionController(mockTransactionController);
        instance.setdAOFactory(mockDAOFactory);
        instance.setEm(mockEntityManager);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
        when(mockDAOFactory.createFundingCostJpaController()).thenReturn(mockFundingCostJpaController);
        when(mockDAOFactory.createEligiblityReportDAO()).thenReturn(mockEligiblityReportJpaController);
        when(mockDAOFactory.createPersonDAO()).thenReturn(mockPersonJpaController);
        when(mockDAOFactory.createFundingReportDAO()).thenReturn(mockFundingReportJpaController);
        when(mockDAOFactory.createResearchFellowInformationDAO()).thenReturn(mockResearchFellowInformationJpaController);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadPendingEndorsedApplications method, of class DRISApprovalService.
     */
    @Test
    public void testLoadPendingEndorsedApplications() throws Exception {        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        int startIndex = 0;
        int maxNumber = 5;
        try
        {
            instance.loadPendingEndorsedApplications(mockSession, startIndex, maxNumber);
            
            //verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).loadPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED, startIndex, maxNumber);
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockFundingReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockEligiblityReportJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockFundingCostJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingEndorsedApplications method, of class DRISApprovalService.
     */
    @Test
    public void testCountTotalPendingEndorsedApplications() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));

        try
        {
            instance.countTotalPendingEndorsedApplications(mockSession);
            
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockFundingReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockEligiblityReportJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockFundingCostJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of loadPendingEligibleApplications method, of class DRISApprovalService.
     */
    @Test
    public void testLoadPendingEligibleApplications() throws Exception {        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        int startIndex = 0;
        int maxNumber = 5;
        try
        {
            instance.loadPendingEligibleApplications(mockSession, startIndex, maxNumber);
            
            verify(mockApplicationServices).loadPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE, startIndex, maxNumber);
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockFundingReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockEligiblityReportJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockFundingCostJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of countTotalPendingEligibleApplications method, of class DRISApprovalService.
     */
    @Test
    public void testCountTotalPendingEligibleApplications() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));

        try
        {
            instance.countTotalPendingEligibleApplications(mockSession);
            
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE);
        
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockFundingReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockEligiblityReportJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockFundingCostJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    /**
     * Test of checkApplicationForEligiblity method, of class DRISApprovalService.
     */
    @Test
    public void testCheckApplicationForEligiblity() throws Exception {
        GregorianCalendar date = new GregorianCalendar();
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        Application mockApplication = mock(Application.class);
        
        AcademicQualification mockAcademicQualification = mock(AcademicQualification.class);
        when(mockAcademicQualification.getName()).thenReturn("PHD");
        date.set(2013, 9, 17);
        when(mockAcademicQualification.getYearObtained()).thenReturn(new Date(2013, 5, 12));
        
        Cv mockCv = mock(Cv.class);
        date.set(1993, 9, 17);
        when(mockCv.getDateOfBirth()).thenReturn(date.getTime());
        when(mockCv.getAcademicQualificationList()).thenReturn(Arrays.asList(mockAcademicQualification));
        
        //EligiblityReport mockEligiblityReport = mock(EligiblityReport.class);
        Person mockPerson = mock(Person.class);
        
        when(mockPerson.getCv()).thenReturn(mockCv);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        when(mockDBEntitiesFactory.createEligiblityReportEntity(mockApplication, new Person("u12236731"), mockCal.getTime())).thenReturn(new EligiblityReport(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Application made eligible" + Long.MAX_VALUE, new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);

        try
        {
            boolean status = instance.checkApplicationForEligiblity(mockSession, mockApplication);
            
            verify(mockApplication, atLeastOnce()).getFellow();
            verify(mockCal).setTime(date.getTime());
            verify(mockCal).add(GregorianCalendar.MONTH, 1);
            verify(mockCal, times(2)).get(GregorianCalendar.YEAR);
            verify(mockCal, times(2)).get(GregorianCalendar.YEAR);
            
            verifyNoMoreInteractions(mockApplication);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockFundingReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockEligiblityReportJpaController);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockFundingCostJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of denyFunding method, of class DRISApprovalService.
     */
    @Test
    public void testDenyFunding() throws Exception {
        Session mockSession = mock(Session.class);
        HttpSession mockHttpSession = mock(HttpSession.class);
        
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        when(mockSession.getHttpSession()).thenReturn(mockHttpSession);
        
        Cv mockCv = mock(Cv.class);
        when(mockCv.getDateOfBirth()).thenReturn(new Date(1993, 9, 17));
        
        Person mockPerson = mock(Person.class);
        when(mockPerson.getCv()).thenReturn(mockCv);
        
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockApplication.getGrantHolder()).thenReturn(new Person("s25030403"));
        when(mockApplication.getApplicationID()).thenReturn(Long.MAX_VALUE);
        
        String reason = "Prospective fellow does not meet the eligiblity requirement";
        
        when(mockDBEntitiesFactory.createNotificationEntity(null, mockPerson, "Application funding declined", "Please note that the funding for the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason)).thenReturn(new Notification(Long.MAX_VALUE));
        when(mockDBEntitiesFactory.createNotificationEntity(null, mockApplication.getGrantHolder(), "Application funding declined", "Please note that the funding for the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the grant holder of. The reason for this is as follows: " + reason)).thenReturn(new Notification(Long.MIN_VALUE));
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);

        try
        {
            instance.denyFunding(mockSession, mockApplication, reason);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getEntityManager();
            verify(mockApplicationServices).declineAppliction(mockSession, mockApplication, reason);
            
            verify(mockDBEntitiesFactory).createNotificationEntity(null, mockPerson, "Application funding declined", "Please note that the funding for the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason);
            verify(mockDBEntitiesFactory).createNotificationEntity(null, new Person("s25030403"), "Application funding declined", "Please note that the funding for the application '" + mockApplication.getProjectTitle() + "' has been declined for which you are the grant holder of. The reason for this is as follows: " + reason);
            
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockFundingReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockEligiblityReportJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockFundingCostJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    /**
     * Test of loadFundedApplications method, of class DRISApprovalService.
     */
    @Test
    public void testloadFundedApplications() throws Exception {        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        int startIndex = 0;
        int maxNumber = 5;
        try
        {
            instance.loadFundedApplications(mockSession, startIndex, maxNumber);
            
            verify(mockApplicationServices).loadPendingApplications(new Person("u12236731"), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED, startIndex, maxNumber);
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockFundingReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockEligiblityReportJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockFundingCostJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    /**
     * Test of terminateApplication method, of class DRISApprovalService.
     */
    @Test
    public void testTerminateApplication() throws Exception {        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        Application mockApplication = mock(Application.class);
        when(mockApplicationJpaController.findApplication(mockApplication.getApplicationID())).thenReturn(mockApplication);
        try
        {
            instance.terminateApplication(mockSession, mockApplication);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplication, atLeastOnce()).getApplicationID();
            verify(mockApplicationJpaController).findApplication(mockApplication.getApplicationID());
            verify(mockApplication).setStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockFundingReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockEligiblityReportJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockFundingCostJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    /**
     * Test of abandonApplication method, of class DRISApprovalService.
     */
    @Test
    public void testAbandonApplication() throws Exception {        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        Application mockApplication = mock(Application.class);
        when(mockApplicationJpaController.findApplication(mockApplication.getApplicationID())).thenReturn(mockApplication);
        try
        {
            instance.abandonApplication(mockSession, mockApplication);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplication, atLeastOnce()).getApplicationID();
            verify(mockApplicationJpaController).findApplication(mockApplication.getApplicationID());
            verify(mockApplication).setStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_ABANDONED);
            verify(mockApplicationJpaController).edit(mockApplication);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockFundingReportJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockEligiblityReportJpaController);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockFundingCostJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
}
