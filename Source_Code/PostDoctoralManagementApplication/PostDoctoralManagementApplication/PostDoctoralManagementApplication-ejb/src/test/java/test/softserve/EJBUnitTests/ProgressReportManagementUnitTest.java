/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.ProgressReportJpaController;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.ProgressReportManagementService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import test.softserve.MockEJBClasses.ProgressReportManagementMockUnit;

/**
 *
 * @author User
 */
public class ProgressReportManagementUnitTest
{
    public ProgressReportManagementUnitTest() {
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
    
    @Test
    void testCreateProgressReport() throws Exception
    {
        ProgressReportManagementMockUnit instance = new ProgressReportManagementMockUnit();
        
        Application mockApplication = mock(Application.class);
        UserGateway mockUserGateway = mock(UserGateway.class); 
        //ApplicationInformation mockApplicationInformation = mock(ApplicationInformation.class);
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        ProgressReport mockProgress = mock(ProgressReport.class);
        ProgressReportJpaController mockProgressReportController = mock(ProgressReportJpaController.class);
      //  ApplicationJpaController mockApplication = mock(ApplicationJpaController.class);
        
        Session mockSession = mock(Session.class);
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        instance.setaDAO(mockApplication);
        instance.setnEJB(mockNotificationService);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setpDAO(mockPersonJpaController);
        instance.setuEJB(mockUserGateway);
        instance.setaSEJB(mockApplicationServices);
        
        try
        {
            instance.createProgressReport(mockSession, mockApplication, mockProgress);
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockProgressReportController).create(mockProgress);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
        
    }
    
    @Test
    void testUpdateProgressReport() throws Exception
    {
        UserGateway mockUserGateway = mock(UserGateway.class); 
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        NotificationService mockNotificationService = mock(NotificationService.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        ProgressReportManagementService mockProgress = mock(ProgressReport.class);
        ProgressReportJpaController mockProgressReportController = mock(ProgressReportJpaController.class);
        ApplicationJpaController mockApplication = mock(ApplicationJpaController.class);
        
        Session mockSession = mock(Session.class);
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        instance.setaDAO(mockApplication);
        instance.setnEJB(mockNotificationService);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setpDAO(mockPersonJpaController);
        instance.setuEJB(mockUserGateway);
        instance.setaSEJB(mockApplicationServices);
        
          
        try
        {
            instance.updateProgressReport(mockSession, mockProgress);
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockProgressReportController).edit(mockProgress);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
        
    }


}
