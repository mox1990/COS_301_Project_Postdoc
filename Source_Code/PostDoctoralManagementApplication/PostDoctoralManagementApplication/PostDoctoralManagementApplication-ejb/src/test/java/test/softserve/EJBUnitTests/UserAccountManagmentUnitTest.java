/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.AddressJpaController;
import com.softserve.DBDAO.DeclineReportJpaController;
import com.softserve.DBDAO.EmployeeInformationJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.EmployeeInformation;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
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
   
    @Test
    public void testCreateUserWithFalse(){
        
        boolean useManualSystemIDSpecification = false;
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.createUserAccount(mockSession, useManualSystemIDSpecification, mockPerson);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
        @Test
    public void testCreateUserWithTrue(){
        
        boolean useManualSystemIDSpecification = true;
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.createUserAccount(mockSession, useManualSystemIDSpecification, mockPerson);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    @Test
    public void testUpdateUser(){
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.updateUserAccount(mockSession, mockPerson);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    @Test
    public void testRemoveUser(){
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        String id = mockPerson.getSystemID();
        try
        {
            instance.removeUserAccount(mockSession, id);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    @Test 
    public void testViewAllUserAccounts(){
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.viewAllUserAccounts(mockSession);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    @Test
    public void testGenerateOnDemandAccountTrue(){
        String reason = "Required to be a referre";
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.generateOnDemandAccount(mockSession, reason, true, mockPerson);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    public void testGenerateOnDemandAccountFalse(){
        String reason = "Required to be a referre";
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.generateOnDemandAccount(mockSession, reason, false, mockPerson);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    @Test
    public void testActivateOnDemandAccount()
    {
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.activateOnDemandAccount(mockSession, mockPerson);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    @Test
    public void testGetAllSecurityRoles()
    {
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.getAllSecurityRoles();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    @Test
    public void testGetUserBySystemIDOrEmail()
    {
        String user = "u12078027";
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.getUserBySystemIDOrEmail(mockPerson.getEmail());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    
    @Test
    public void testTestAddress(){
         ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try
        {
            instance.testAddresses();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
}