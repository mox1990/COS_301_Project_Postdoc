/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import auto.softserve.XMLEntities.application.ApplicationInformation;
import auto.softserve.XMLEntities.referee.ReferalReport;
import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.RecommendationReportJpaController;
import com.softserve.DBDAO.RefereeReportJpaController;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Experience;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import test.softserve.MockEJBClasses.NewApplicationMockUnit;
import test.softserve.MockEJBClasses.RefereesReportMockUnit;

/**
 *
 * @author User
 */
public class RefereesReportUnitTest {
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
    
    public void testLoadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords)
    {
        RefereesReportMockUnit instance = new RefereesReportMockUnit();
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        Session mockSession = mock(Session.class);
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE);
        int start = 0;
        int max = 5; 
        
        try
        {
            instance.loadPendingApplications(session, StartIndex, maxNumberOfRecords);
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).loadPendingApplications(mockSession.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED, start, max);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    public void testCountTotalPendingApplications(Session session)
    {
        RefereesReportMockUnit instance = new RefereesReportMockUnit();
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        Session mockSession = mock(Session.class);
        
        instance.setaDAO(mockApplicationJpaController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setuEJB(mockUserGateway);
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE);
        
        try
        {
            instance.countTotalPendingApplications(session);
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(mockSession.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
   
    public void testSubmitReferralReport(Session session, Application application, RefereeReport refereeReport)
    {
        RefereesReportMockUnit instance = new RefereesReportMockUnit();
        RefereeReportJpaController refereeReportJpaController = getRefereeReportDAO();
         ApplicationJpaController applicationJpaController = getApplicationDAO();
         NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        Session mockSession = mock(Session.class);
        try
        {
            instance.submitReferralReport(session, application, refereeReport);
            verify(mockUserGateway).authenticateUser(mockSession, roles);
           .
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
}
