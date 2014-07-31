/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import auto.softserve.XMLEntities.application.ApplicationInformation;
import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Experience;
import com.softserve.DBEntities.Person;
import auto.softserve.XMLEntities.referee.ReferalReport;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import test.softserve.MockEJBClasses.NewApplicationMockUnit;

/**
 *
 * @author User
 */
public class NewApplicationUnitTest {
    
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
    
    public void testCreateProspectiveFellowCV(Session session, Cv cv)
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        
        UserGateway mockUserGateway =  mock(UserGateway.class);
        CvJpaController mockCvJpaController =  mock(CvJpaController.class);
        instance.setcVDAO(mockCvJpaController);
        DBEntitiesFactory mockDBEntitiesFactory =  mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created user cv", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        Cv mockCV = mock(Cv.class);  
        when(mockCV.getPerson()).thenReturn(new Person("u12236731"));
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        AuditTrailService mockAuditTrailService =  mock(AuditTrailService.class);
        DBEntitiesFactory mockDBEntitiesFactory =  mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created user cv", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
   
        try
        {
             instance.createProspectiveFellowCV(mockSession, mockCV);
             verify(mockCvJpaController).create(mockCV);
             
             verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockCV.getPerson());
              verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Created user cv", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE)); 
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    public void testCreateNewApplication(Session session, Application application)
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        
        Application mockApplication =  mock(Application.class);
        ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        instance.setaDAO(mockApplicationController);
         ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
          instance.setaSEJB(mockApplicationServices);
        DBEntitiesFactory mockDBEntitiesFactory =  mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created new application", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        
        UserGateway mockUserGateway =  mock(UserGateway.class);
        AuditTrailService mockAuditTrailService =  mock(AuditTrailService.class);
        
       
        instance.setuEJB(mockUserGateway);
        instance.setaTEJB(mockAuditTrailService);
       
        
        
        try
        {
            instance.createNewApplication(session, application);
            verify(mockApplicationController).create(application);
            verify(mockUserGateway).authenticateUserAsOwner(session,application.getFellow());
             verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Created new application", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(mockSession.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
             
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    public void testLinkGrantHolderToApplication(Session session, Application application, Person grantHolder)
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        UserGateway mockUserGateway = mock(UserGateway.class);
         ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
         PersonJpaController mockPersonController = mock(PersonJpaController.class);
        
         instance.setpDAO(mockPersonController);
        instance.setaDAO(mockApplicationController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setuEJB(mockUserGateway);
        
        try
        {
            instance.linkGrantHolderToApplication(session, application, grantHolder);
            verify(mockUserGateway).authenticateUserAsOwner(session,application.getFellow());
            verify(mockPersonController).findPerson(referee.getFullName());
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    public void testLinkRefereeToApplication(Session session, Application application, Person referee) 
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        UserGateway mockUserGateway = mock(UserGateway.class);
        ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        PersonJpaController mockPersonController = mock(PersonJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        
        instance.setaDAO(mockApplicationController);
        instance.setpDAO(mockPersonController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setuEJB(mockUserGateway);
        
        try
        {
            instance.linkRefereeToApplication(session, application, referee);
            verify(mockUserGateway).authenticateUserAsOwner(session,application.getFellow());
            
            verify(mockPersonController).findPerson(referee.getFullName());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    public void testSubmitApplication(Session session, Application application)
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        UserGateway mockUserGateway = mock(UserGateway.class);
        ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        PersonJpaController mockPersonController = mock(PersonJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        ApplicationServices mockApplicationServices = mock(ApplicationServices.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        
        instance.setaDAO(mockApplicationController);
        instance.setpDAO(mockPersonController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setuEJB(mockUserGateway);
        
        try
        {
            instance.submitApplication(session, application);
            verify(mockUserGateway).authenticateUserAsOwner(session,application.getFellow());
            verify(mockApplicationServices).getApplicationDAO();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
}
