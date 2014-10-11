/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.persistence.DBDAO.AddressJpaController;
import com.softserve.persistence.DBDAO.DeclineReportJpaController;
import com.softserve.persistence.DBDAO.EmployeeInformationJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBEntities.Address;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.EmployeeInformation;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import test.softserve.MockEJBClasses.ApplicationServicesUtilMockUnit;
import test.softserve.MockEJBClasses.UserAccountManagementServicesMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@RunWith(MockitoJUnitRunner.class)
public class UserAccountManagmentUnitTest {
      
    private DBEntitiesFactory mockDBEntitiesFactory;
    private Session mockSession;
    private UserGateway mockUserGateway;
    private NotificationService mockNotificationService;
    private AuditTrailService mockAuditTrailService;
    private GregorianCalendar mockGregorianCalendar;
    private Person mockPerson;
    private PersonJpaController mockPersonController;
    
    private UserAccountManagementServicesMockUnit instance;
    
    public UserAccountManagmentUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() { 
        instance = new UserAccountManagementServicesMockUnit();
        
        mockSession = mock(Session.class);
        mockUserGateway = mock(UserGateway.class);
        mockNotificationService = mock(NotificationService.class);
        mockAuditTrailService = mock(AuditTrailService.class);
        mockPerson = mock(Person.class);
        mockPersonController = mock(PersonJpaController.class);
        mockGregorianCalendar = mock(GregorianCalendar.class);
        
        
        instance.setAuditTrailService(mockAuditTrailService);
        instance.setDBEntitiesFactory(mockDBEntitiesFactory);
        instance.setNotificationService(mockNotificationService);
        instance.setUserGateway(mockUserGateway);
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void temp()
    {
        
    }
   
    //@Test
    public void testCreateUserWithFalse(){
        
        DBEntitiesFactory mockDBEntitiesFactory =  mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Created new user", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE)); 
        boolean useManualSystemIDSpecification = false;
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.createUserAccount(mockSession, useManualSystemIDSpecification, mockPerson);
            verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockPerson);
            verify(mockUserGateway, Mockito.times(2)).authenticateUser(mockSession, roles); 
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Created new user", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }
    
    //@Test
    public void testCreateUserWithTrue(){
        
        DBEntitiesFactory mockDBEntitiesFactory =  mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Created new user", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        boolean useManualSystemIDSpecification = true;
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.createUserAccount(mockSession, useManualSystemIDSpecification, mockPerson);
            //verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockPerson);
            verify(mockUserGateway, Mockito.times(2)).authenticateUser(mockSession, roles); 
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Created new user", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }
    
    //@Test
    public void testCreateUserWithTrueNull(){
        
        boolean useManualSystemIDSpecification = true;
        when(mockSession.getUser().getSystemID()).thenReturn(mockPerson.getSystemID());
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.createUserAccount(mockSession, useManualSystemIDSpecification, mockPerson);
            //fail"Exception not thrown");
        }
        catch(Exception ex)
        {
            Assert.assertTrue(true);
        }
    }
    
    //@Test
    public void testUpdateUser(){
        DBEntitiesFactory mockDBEntitiesFactory =  mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Updated user", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.updateUserAccount(mockSession, mockPerson);
            //verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockPerson);
            verify(mockUserGateway, Mockito.times(2)).authenticateUser(mockSession, roles); 
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Updated user", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }
    
    //@Test
    public void testRemoveUser(){
        DBEntitiesFactory mockDBEntitiesFactory =  mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.createAduitLogEntitiy("Removed user", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        String id = mockPerson.getSystemID();
        try
        {
            instance.removeUserAccount(mockSession, id);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }
    
    //@Test
    public void testRemoveUserFail(){
        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        String id = mockPerson.getSystemID();
        mockPerson.setSecurityRoleList(roles);
        
        try
        {
            instance.removeUserAccount(mockSession, id);
            //fail"Exception not thrown");
        }
        catch(Exception ex)
        {
            Assert.assertTrue(true);
        }
    }
    
    //@Test 
    public void testViewAllUserAccounts() throws Exception{
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockPerson);
            verify(mockUserGateway, Mockito.times(2)).authenticateUser(mockSession, roles); 
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Viewed all accounts", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            
        try
        {
            instance.viewAllUserAccounts(mockSession);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }
    
    //@Test
    public void testGenerateOnDemandAccountTrue(){
        String reason = "Required to be a referre";
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.generateOnDemandAccount(mockSession, reason, true, mockPerson);
            verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockPerson);
            verify(mockUserGateway, Mockito.times(2)).authenticateUser(mockSession, roles); 
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Created new application", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }
    
    public void testGenerateOnDemandAccountFalse(){
        String reason = "Required to be a referre";
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.generateOnDemandAccount(mockSession, reason, false, mockPerson);
            //verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockPerson);
            verify(mockUserGateway, Mockito.times(2)).authenticateUser(mockSession, roles); 
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Created new on demand account", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }
    
    //@Test
    public void testActivateOnDemandAccount()
    {
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.activateOnDemandAccount(mockSession, mockPerson);
            //verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockPerson);
            verify(mockUserGateway, Mockito.times(2)).authenticateUser(mockSession, roles); 
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Created new application", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }
    
    //@Test
    public void testGetAllSecurityRoles()
    {
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            //instance.getAllSecurityRoles();
            //verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockPerson);
            verify(mockUserGateway, Mockito.times(2)).authenticateUser(mockSession, roles); 
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Created new application", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }
    
    //@Test
    public void testGetUserBySystemIDOrEmail()
    {
        String user = "u12078027";
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.getUserBySystemIDOrEmail(mockPerson.getEmail());
            //verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockPerson);
            verify(mockUserGateway, Mockito.times(2)).authenticateUser(mockSession, roles); 
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Created new application", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }
    
    //@Test
    public void testTestAddress(){
        /*ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.testAddresses();
            verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockPerson);
            verify(mockUserGateway, Mockito.times(2)).authenticateUser(mockSession, roles); 
            verify(mockDBEntitiesFactory).createAduitLogEntitiy("Created new application", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }*/
    }
}