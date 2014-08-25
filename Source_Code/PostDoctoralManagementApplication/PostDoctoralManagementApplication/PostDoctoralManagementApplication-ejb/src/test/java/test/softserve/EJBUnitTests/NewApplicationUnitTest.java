/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import auto.softserve.XMLEntities.application.ApplicationInformation;
import auto.softserve.XMLEntities.referee.ReferalReport;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Experience;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import org.junit.*;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Created user cv", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        Cv mockCV = mock(Cv.class);  
        when(mockCV.getPerson()).thenReturn(new Person("u12236731"));
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        AuditTrailService mockAuditTrailService =  mock(AuditTrailService.class);
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Created user cv", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
   
        try
        {
             instance.createProspectiveFellowCV(mockSession, mockCV);
             verify(mockCvJpaController).create(mockCV);
             
             verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockCV.getPerson());
              verify(mockDBEntitiesFactory).createAduitLogEntitiy("Created user cv", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE)); 
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    public void testCreateNewApplication()
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        
        Application mockApplication =  mock(Application.class);
        ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        instance.setaDAO(mockApplicationController);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        instance.setaSEJB(mockApplicationServices);
        DBEntitiesFactory mockDBEntitiesFactory =  mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Created new application", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        Session mockSession = mock(Session.class);
        Person mockPerson = mock(Person.class);
        Cv mockCV = mock(Cv.class);
        
        UserGateway mockUserGateway =  mock(UserGateway.class);
        AuditTrailService mockAuditTrailService =  mock(AuditTrailService.class);
        when(mockPerson.getCv()).thenReturn(mockCV);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
       
        instance.setuEJB(mockUserGateway);
        instance.setaTEJB(mockAuditTrailService);
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        
        
        try
        {
            instance.createNewApplication(mockSession, mockApplication);
            verify(mockApplicationController).create(mockApplication);
            verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockApplication.getFellow());
            verify(mockUserGateway, Mockito.times(2)).authenticateUser(mockSession, roles); 
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Created new application", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            verify(mockApplicationServices).getTotalNumberOfPendingApplications(mockSession.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_TYPE_NEW);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    public void testCreateNewApplicationNull()
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        
        Application mockApplication =  null;
        ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        instance.setaDAO(mockApplicationController);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        instance.setaSEJB(mockApplicationServices);
        DBEntitiesFactory mockDBEntitiesFactory =  mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Created new application", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        Session mockSession = mock(Session.class);
        Person mockPerson = mock(Person.class);
        Cv mockCV = mock(Cv.class);
        
        UserGateway mockUserGateway =  mock(UserGateway.class);
        AuditTrailService mockAuditTrailService =  mock(AuditTrailService.class);
        when(mockPerson.getCv()).thenReturn(mockCV);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
       
        instance.setuEJB(mockUserGateway);
        instance.setaTEJB(mockAuditTrailService);
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        
        
       try
        {
            instance.createNewApplication(mockSession, mockApplication);
            fail("Exception not thrown");
        }
        catch(Exception ex)
        {
            Assert.assertTrue(true);
        }
    }
    
    public void testLinkGrantHolderToApplication()
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        UserGateway mockUserGateway = mock(UserGateway.class);
         ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        PersonJpaController mockPersonController = mock(PersonJpaController.class);
        
        instance.setpDAO(mockPersonController);
        instance.setaDAO(mockApplicationController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setuEJB(mockUserGateway);
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        Person mockPerson = mock(Person.class);
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        try
        {
            instance.linkGrantHolderToApplication(mockSession, mockApplication, mockPerson);
            verify(mockPersonController).findPerson(mockPerson.getFullName());
            verify(mockApplication).getGrantHolder();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    public void testLinkGrantHolderToApplicationNull()
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        UserGateway mockUserGateway = mock(UserGateway.class);
         ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        PersonJpaController mockPersonController = mock(PersonJpaController.class);
        
        instance.setpDAO(mockPersonController);
        instance.setaDAO(mockApplicationController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setuEJB(mockUserGateway);
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        Person mockPerson = null;
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        try
        {
            instance.linkGrantHolderToApplication(mockSession, mockApplication, mockPerson);
            fail("Exception not thrown");
        }
        catch(Exception ex)
        {
            Assert.assertTrue(true);
        }
    }
    
    public void testLinkRefereeToApplication() 
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        UserGateway mockUserGateway = mock(UserGateway.class);
        ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        PersonJpaController mockPersonController = mock(PersonJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        Person mockPerson = mock(Person.class);
        
        
        instance.setaDAO(mockApplicationController);
        instance.setpDAO(mockPersonController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setuEJB(mockUserGateway);
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE);
        try
        {
            instance.linkRefereeToApplication(mockSession, mockApplication, mockPerson);
            verify(mockApplication).getRefereeReportList();
            verify(mockPersonController).findPerson(mockPerson.getFullName());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
     
    public void testLinkRefereeToApplicationNull() 
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        UserGateway mockUserGateway = mock(UserGateway.class);
        ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        PersonJpaController mockPersonController = mock(PersonJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        Person mockPerson = null;
        
        
        instance.setaDAO(mockApplicationController);
        instance.setpDAO(mockPersonController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setuEJB(mockUserGateway);
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE);
       
        try
        {
            instance.linkRefereeToApplication(mockSession, mockApplication, mockPerson);
            fail("Exception not thrown");
        }
        catch(Exception ex)
        {
            Assert.assertTrue(true);
        }
    }
    
    public void testSubmitApplication()
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        UserGateway mockUserGateway = mock(UserGateway.class);
        ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        PersonJpaController mockPersonController = mock(PersonJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        Person mockPerson = mock(Person.class);
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        
        instance.setaDAO(mockApplicationController);
        instance.setpDAO(mockPersonController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setuEJB(mockUserGateway);
        
        try
        {
            instance.submitApplication(mockSession, mockApplication);
            verify(mockApplicationJpaController).edit(mockApplication);
            
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Finalised application " + Long.MAX_VALUE, new Person("u12236731"));
            
            verify(mockDBEntitiesFactory).createNotificationEntity(new Person("u12236731"), mockPerson, "Application finalised", "The following application has been submitted by " + mockSession.getUser().getCompleteName() +  ". Please complete referee report.");
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockApplication.getFellow()); 
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    public void testSubmitApplicationNull()
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        UserGateway mockUserGateway = mock(UserGateway.class);
        ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        PersonJpaController mockPersonController = mock(PersonJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        Session mockSession = mock(Session.class);
        Application mockApplication = null;
        Person mockPerson = mock(Person.class);
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        
        instance.setaDAO(mockApplicationController);
        instance.setpDAO(mockPersonController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setuEJB(mockUserGateway);
        
        try
        {
            instance.linkRefereeToApplication(mockSession, mockApplication, mockPerson);
            fail("Exception not thrown");
        }
        catch(Exception ex)
        {
            Assert.assertTrue(true);
        }
    }
    
    public void testCanFellowOpenApplication()
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        UserGateway mockUserGateway = mock(UserGateway.class);
        ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        PersonJpaController mockPersonController = mock(PersonJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        when(mockApplication.getStatus()).thenReturn(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
        ArrayList<Application> mockAppList = mock(ArrayList.class);
        when(mockAppList.get(0)).thenReturn(mockApplication);
        
        Person mockPerson = mock(Person.class);
        when(mockPerson.getApplicationList()).thenReturn(mockAppList);
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        
        instance.setaDAO(mockApplicationController);
        instance.setpDAO(mockPersonController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setuEJB(mockUserGateway);
        
        try
        {
            instance.canFellowOpenANewApplication(mockPerson);
            verify(mockApplication).getStatus();
            
        }
        catch(Exception ex)
        {
            Assert.assertTrue(true);
        }
    }
    
    public void testGetOpenApplication()
    {
        NewApplicationMockUnit instance = new NewApplicationMockUnit();
        UserGateway mockUserGateway = mock(UserGateway.class);
        ApplicationJpaController mockApplicationController = mock(ApplicationJpaController.class);
        PersonJpaController mockPersonController = mock(PersonJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        ApplicationServicesUtil mockApplicationServices = mock(ApplicationServicesUtil.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        when(mockApplication.getStatus()).thenReturn(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
        ArrayList<Application> mockAppList = mock(ArrayList.class);
        when(mockAppList.get(0)).thenReturn(mockApplication);
        
        Person mockPerson = mock(Person.class);
        when(mockPerson.getApplicationList()).thenReturn(mockAppList);
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        
        instance.setaDAO(mockApplicationController);
        instance.setpDAO(mockPersonController);
        instance.setaSEJB(mockApplicationServices);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setuEJB(mockUserGateway);
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        
        
        try
        {
            instance.getOpenApplication(mockSession);
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplication).getStatus();
        }
        catch(Exception ex)
        {
            
        }
    }
}
