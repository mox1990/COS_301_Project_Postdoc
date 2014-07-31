/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.AddressJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.UpEmployeeInformationJpaController;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.ejb.AuditTrailService;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import test.softserve.MockEJBClasses.UserAccountManagementServicesMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@RunWith(MockitoJUnitRunner.class)
public class UserAccountManagmentUnitTest {
      
    
    
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
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void temp()
    {
        
    }
    
    @Test
    public void createUserAccount_Manual_IsUpEmployee_UnitTest() 
    {        
        UserAccountManagementServicesMockUnit instance = new UserAccountManagementServicesMockUnit();
        
        //Setup dependices mocks
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        AddressJpaController mockAddressJpaController = mock(AddressJpaController.class);
        UpEmployeeInformationJpaController mockUpEmployeeInformationJpaController = mock(UpEmployeeInformationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created new user account", new Person("u12019837"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        
        //Load dependices mocks' into instance
        instance.setaDAO(mockAddressJpaController);
        instance.setpDAO(mockPersonJpaController);
        instance.setuDAO(mockUpEmployeeInformationJpaController);
        instance.setDbFactory(mockDBEntitiesFactory);
        instance.setAtsEJB(mockAuditTrailService);
        
        //Setup parameter mocks
        Person mockPerson = mock(Person.class);
        Address mockAddress = mock(Address.class);
        UpEmployeeInformation mockUpInfo = mock(UpEmployeeInformation.class);        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
        
        try
        {
            //Execute function
            instance.createUserAccount(mockSession, true, mockPerson, mockAddress, mockUpInfo);
            
            //Verify correct function behaviour
            verify(mockAddressJpaController).create(mockAddress);
            verify(mockPerson).setAddressLine1(mockAddress);            
            verify(mockPersonJpaController).create(mockPerson);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockPerson);
            verifyNoMoreInteractions(mockAddress);
            verify(mockUpEmployeeInformationJpaController).create(mockUpInfo);
            verifyNoMoreInteractions(mockUpEmployeeInformationJpaController);            
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Created new user account", new Person("u12019837"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void createUserAccount_Manual_NotUpEmployee_UnitTest() 
    {        
        UserAccountManagementServicesMockUnit instance = new UserAccountManagementServicesMockUnit();
        
        //Setup dependices mocks
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        AddressJpaController mockAddressJpaController = mock(AddressJpaController.class);
        UpEmployeeInformationJpaController mockUpEmployeeInformationJpaController = mock(UpEmployeeInformationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created new user account", new Person("u12019837"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        
        //Load dependices mocks' into instance
        instance.setaDAO(mockAddressJpaController);
        instance.setpDAO(mockPersonJpaController);
        instance.setuDAO(mockUpEmployeeInformationJpaController);
        instance.setDbFactory(mockDBEntitiesFactory);
        instance.setAtsEJB(mockAuditTrailService);
        
        //Setup parameter mocks
        Person mockPerson = mock(Person.class);
        Address mockAddress = mock(Address.class);
        UpEmployeeInformation mockUpInfo = mock(UpEmployeeInformation.class);        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
        
        try
        {
            //Execute function
            instance.createUserAccount(mockSession, true, mockPerson, mockAddress, null);
            
            //Verify correct function behaviour
            verifyNoMoreInteractions(mockUpEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockUpInfo);     
            verify(mockAddressJpaController).create(mockAddress);
            verify(mockPerson).setAddressLine1(mockAddress);            
            verify(mockPersonJpaController).create(mockPerson);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockPerson);
            verifyNoMoreInteractions(mockAddress);                   
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Created new user account", new Person("u12019837"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void createUserAccount_Auto_IsSystemAdmin_IsUpEmployee_UnitTest() 
    {        
        UserAccountManagementServicesMockUnit instance = new UserAccountManagementServicesMockUnit();
        
        String expectedSystemID = "a14000006";
        
        //Setup dependices mocks
        GregorianCalendar mockCalander = mock(GregorianCalendar.class);
        when(mockCalander.get(Calendar.YEAR)).thenReturn(2014);
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        when(mockPersonJpaController.getMaxSystemIDForYear(2014, 'a')).thenReturn(5);
        AddressJpaController mockAddressJpaController = mock(AddressJpaController.class);
        UpEmployeeInformationJpaController mockUpEmployeeInformationJpaController = mock(UpEmployeeInformationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created new user account", new Person("u12019837"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        
        //Load dependices mocks' into instance
        instance.setaDAO(mockAddressJpaController);
        instance.setpDAO(mockPersonJpaController);
        instance.setuDAO(mockUpEmployeeInformationJpaController);
        instance.setDbFactory(mockDBEntitiesFactory);
        instance.setAtsEJB(mockAuditTrailService);
        
        //Setup parameter mocks
        Person mockPerson = mock(Person.class);
        when(mockPerson.getSystemID()).thenReturn(expectedSystemID);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR)).thenReturn(Boolean.TRUE);
        Address mockAddress = mock(Address.class);
        UpEmployeeInformation mockUpInfo = mock(UpEmployeeInformation.class);        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
        
        try
        {
            //Execute function
            instance.createUserAccount(mockSession, false, mockPerson, mockAddress, mockUpInfo);
            
            //Verify correct function behaviour
            verify(mockPerson).setSystemID(expectedSystemID);
            verify(mockAddressJpaController).create(mockAddress);
            verify(mockPerson).setAddressLine1(mockAddress); 
            verify(mockPersonJpaController).doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR);
            verify(mockPersonJpaController).getMaxSystemIDForYear(2014, 'a');
            verify(mockPerson).getSystemID();
            verify(mockUpInfo).setEmployeeID(expectedSystemID);
            verify(mockPersonJpaController).create(mockPerson);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockPerson);
            verifyNoMoreInteractions(mockAddress);
            verify(mockUpEmployeeInformationJpaController).create(mockUpInfo);
            verifyNoMoreInteractions(mockUpEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockUpInfo);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Created new user account", new Person("u12019837"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void createUserAccount_Auto_IsSystemAdmin_NotUpEmployee_UnitTest() 
    {        
        UserAccountManagementServicesMockUnit instance = new UserAccountManagementServicesMockUnit();
        
        String expectedSystemID = "a14000006";
        
        //Setup dependices mocks
        GregorianCalendar mockCalander = mock(GregorianCalendar.class);
        when(mockCalander.get(Calendar.YEAR)).thenReturn(2014);
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        when(mockPersonJpaController.getMaxSystemIDForYear(2014, 'a')).thenReturn(5);
        AddressJpaController mockAddressJpaController = mock(AddressJpaController.class);
        UpEmployeeInformationJpaController mockUpEmployeeInformationJpaController = mock(UpEmployeeInformationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created new user account", new Person("u12019837"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        
        //Load dependices mocks' into instance
        instance.setaDAO(mockAddressJpaController);
        instance.setpDAO(mockPersonJpaController);
        instance.setuDAO(mockUpEmployeeInformationJpaController);
        instance.setDbFactory(mockDBEntitiesFactory);
        instance.setAtsEJB(mockAuditTrailService);
        
        //Setup parameter mocks
        Person mockPerson = mock(Person.class);
        when(mockPerson.getSystemID()).thenReturn(expectedSystemID);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR)).thenReturn(Boolean.TRUE);
        Address mockAddress = mock(Address.class);
        UpEmployeeInformation mockUpInfo = mock(UpEmployeeInformation.class);        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
        
        try
        {
            //Execute function
            instance.createUserAccount(mockSession, false, mockPerson, mockAddress, null);
            
            //Verify correct function behaviour
            verifyNoMoreInteractions(mockUpEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockUpInfo);            
            verify(mockPerson).setSystemID(expectedSystemID);
            verify(mockAddressJpaController).create(mockAddress);
            verify(mockPerson).setAddressLine1(mockAddress); 
            verify(mockPersonJpaController).doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR);
            verify(mockPersonJpaController).getMaxSystemIDForYear(2014, 'a');
            verify(mockPersonJpaController).create(mockPerson);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockPerson);
            verifyNoMoreInteractions(mockAddress);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Created new user account", new Person("u12019837"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    @Test
    public void createUserAccount_Auto_IsProspectiveFellow_IsUpEmployee_UnitTest() 
    {        
        UserAccountManagementServicesMockUnit instance = new UserAccountManagementServicesMockUnit();
        
        String expectedSystemID = "f14000006";
        
        //Setup dependices mocks
        GregorianCalendar mockCalander = mock(GregorianCalendar.class);
        when(mockCalander.get(Calendar.YEAR)).thenReturn(2014);
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        when(mockPersonJpaController.getMaxSystemIDForYear(2014, 'f')).thenReturn(5);
        AddressJpaController mockAddressJpaController = mock(AddressJpaController.class);
        UpEmployeeInformationJpaController mockUpEmployeeInformationJpaController = mock(UpEmployeeInformationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created new user account", new Person("u12019837"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        
        //Load dependices mocks' into instance
        instance.setaDAO(mockAddressJpaController);
        instance.setpDAO(mockPersonJpaController);
        instance.setuDAO(mockUpEmployeeInformationJpaController);
        instance.setDbFactory(mockDBEntitiesFactory);
        instance.setAtsEJB(mockAuditTrailService);
        
        //Setup parameter mocks
        Person mockPerson = mock(Person.class);
        when(mockPerson.getSystemID()).thenReturn(expectedSystemID);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR)).thenReturn(Boolean.FALSE);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW)).thenReturn(Boolean.TRUE);
        Address mockAddress = mock(Address.class);
        UpEmployeeInformation mockUpInfo = mock(UpEmployeeInformation.class);        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
        
        try
        {
            //Execute function
            instance.createUserAccount(mockSession, false, mockPerson, mockAddress, mockUpInfo);
            
            //Verify correct function behaviour
            verify(mockPerson).setSystemID(expectedSystemID);
            verify(mockAddressJpaController).create(mockAddress);
            verify(mockPerson).setAddressLine1(mockAddress); 
            verify(mockPersonJpaController).doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR);
            verify(mockPersonJpaController).doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW);
            verify(mockPersonJpaController).getMaxSystemIDForYear(2014, 'f');
            verify(mockPerson).getSystemID();
            verify(mockUpInfo).setEmployeeID(expectedSystemID);
            verify(mockPersonJpaController).create(mockPerson);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockPerson);
            verifyNoMoreInteractions(mockAddress);
            verify(mockUpEmployeeInformationJpaController).create(mockUpInfo);
            verifyNoMoreInteractions(mockUpEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockUpInfo);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Created new user account", new Person("u12019837"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void createUserAccount_Auto_IsProspectiveFellow_NotUpEmployee_UnitTest() 
    {        
        UserAccountManagementServicesMockUnit instance = new UserAccountManagementServicesMockUnit();
        
        String expectedSystemID = "f14000006";
        
        //Setup dependices mocks
        GregorianCalendar mockCalander = mock(GregorianCalendar.class);
        when(mockCalander.get(Calendar.YEAR)).thenReturn(2014);
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        when(mockPersonJpaController.getMaxSystemIDForYear(2014, 'f')).thenReturn(5);
        AddressJpaController mockAddressJpaController = mock(AddressJpaController.class);
        UpEmployeeInformationJpaController mockUpEmployeeInformationJpaController = mock(UpEmployeeInformationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created new user account", new Person("u12019837"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        
        //Load dependices mocks' into instance
        instance.setaDAO(mockAddressJpaController);
        instance.setpDAO(mockPersonJpaController);
        instance.setuDAO(mockUpEmployeeInformationJpaController);
        instance.setDbFactory(mockDBEntitiesFactory);
        instance.setAtsEJB(mockAuditTrailService);
        
        //Setup parameter mocks
        Person mockPerson = mock(Person.class);
        when(mockPerson.getSystemID()).thenReturn(expectedSystemID);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR)).thenReturn(Boolean.FALSE);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW)).thenReturn(Boolean.TRUE);
        Address mockAddress = mock(Address.class);
        UpEmployeeInformation mockUpInfo = mock(UpEmployeeInformation.class);        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
        
        try
        {
            //Execute function
            instance.createUserAccount(mockSession, false, mockPerson, mockAddress, null);
            
            //Verify correct function behaviour
            verifyNoMoreInteractions(mockUpEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockUpInfo);
            verify(mockAddressJpaController).create(mockAddress);
            verify(mockPerson).setAddressLine1(mockAddress); 
            verify(mockPersonJpaController).doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR);
            verify(mockPersonJpaController).doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW);
            verify(mockPersonJpaController).getMaxSystemIDForYear(2014, 'f');
            verify(mockPerson).setSystemID(expectedSystemID);
            verify(mockPersonJpaController).create(mockPerson);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockPerson);
            verifyNoMoreInteractions(mockAddress);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Created new user account", new Person("u12019837"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void createUserAccount_Auto_IsReferee_IsUpEmployee_UnitTest() 
    {        
        UserAccountManagementServicesMockUnit instance = new UserAccountManagementServicesMockUnit();
        
        String expectedSystemID = "r14000006";
        
        //Setup dependices mocks
        GregorianCalendar mockCalander = mock(GregorianCalendar.class);
        when(mockCalander.get(Calendar.YEAR)).thenReturn(2014);
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        when(mockPersonJpaController.getMaxSystemIDForYear(2014, 'r')).thenReturn(5);
        AddressJpaController mockAddressJpaController = mock(AddressJpaController.class);
        UpEmployeeInformationJpaController mockUpEmployeeInformationJpaController = mock(UpEmployeeInformationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created new user account", new Person("u12019837"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        
        //Load dependices mocks' into instance
        instance.setaDAO(mockAddressJpaController);
        instance.setpDAO(mockPersonJpaController);
        instance.setuDAO(mockUpEmployeeInformationJpaController);
        instance.setDbFactory(mockDBEntitiesFactory);
        instance.setAtsEJB(mockAuditTrailService);
        
        //Setup parameter mocks
        Person mockPerson = mock(Person.class);
        when(mockPerson.getSystemID()).thenReturn(expectedSystemID);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR)).thenReturn(Boolean.FALSE);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW)).thenReturn(Boolean.FALSE);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_REFEREE)).thenReturn(Boolean.TRUE);
        Address mockAddress = mock(Address.class);
        UpEmployeeInformation mockUpInfo = mock(UpEmployeeInformation.class);        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
        
        try
        {
            //Execute function
            instance.createUserAccount(mockSession, false, mockPerson, mockAddress, mockUpInfo);
            
            //Verify correct function behaviour
            verify(mockPerson).setSystemID(expectedSystemID);
            verify(mockAddressJpaController).create(mockAddress);
            verify(mockPerson).setAddressLine1(mockAddress); 
            verify(mockPersonJpaController).doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR);
            verify(mockPersonJpaController).doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW);
            verify(mockPersonJpaController).doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_REFEREE);
            verify(mockPersonJpaController).getMaxSystemIDForYear(2014, 'r');
            verify(mockPerson).getSystemID();
            verify(mockUpInfo).setEmployeeID(expectedSystemID);
            verify(mockPersonJpaController).create(mockPerson);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockPerson);
            verifyNoMoreInteractions(mockAddress);
            verify(mockUpEmployeeInformationJpaController).create(mockUpInfo);
            verifyNoMoreInteractions(mockUpEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockUpInfo);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Created new user account", new Person("u12019837"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void createUserAccount_Auto_IsReferee_NotUpEmployee_UnitTest() 
    {        
        UserAccountManagementServicesMockUnit instance = new UserAccountManagementServicesMockUnit();
        
        String expectedSystemID = "r14000006";
        
        //Setup dependices mocks
        GregorianCalendar mockCalander = mock(GregorianCalendar.class);
        when(mockCalander.get(Calendar.YEAR)).thenReturn(2014);
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        when(mockPersonJpaController.getMaxSystemIDForYear(2014, 'r')).thenReturn(5);
        AddressJpaController mockAddressJpaController = mock(AddressJpaController.class);
        UpEmployeeInformationJpaController mockUpEmployeeInformationJpaController = mock(UpEmployeeInformationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created new user account", new Person("u12019837"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        
        //Load dependices mocks' into instance
        instance.setaDAO(mockAddressJpaController);
        instance.setpDAO(mockPersonJpaController);
        instance.setuDAO(mockUpEmployeeInformationJpaController);
        instance.setDbFactory(mockDBEntitiesFactory);
        instance.setAtsEJB(mockAuditTrailService);
        
        //Setup parameter mocks
        Person mockPerson = mock(Person.class);
        when(mockPerson.getSystemID()).thenReturn(expectedSystemID);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR)).thenReturn(Boolean.FALSE);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW)).thenReturn(Boolean.FALSE);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_REFEREE)).thenReturn(Boolean.TRUE);
        Address mockAddress = mock(Address.class);
        UpEmployeeInformation mockUpInfo = mock(UpEmployeeInformation.class);        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
        
        try
        {
            //Execute function
            instance.createUserAccount(mockSession, false, mockPerson, mockAddress, null);
            
            //Verify correct function behaviour
            verifyNoMoreInteractions(mockUpEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockUpInfo);
            verify(mockAddressJpaController).create(mockAddress);
            verify(mockPerson).setAddressLine1(mockAddress); 
            verify(mockPersonJpaController).doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR);
            verify(mockPersonJpaController).doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW);
            verify(mockPersonJpaController).doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_REFEREE);
            verify(mockPersonJpaController).getMaxSystemIDForYear(2014, 'r');
            verify(mockPerson).setSystemID(expectedSystemID);
            verify(mockPersonJpaController).create(mockPerson);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockPerson);
            verifyNoMoreInteractions(mockAddress);
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Created new user account", new Person("u12019837"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE));
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void createUserAccount_Auto_NotReferee_NotSystemAdmin_NotProspectiveFellow_Exception_UnitTest() 
    {        
        UserAccountManagementServicesMockUnit instance = new UserAccountManagementServicesMockUnit();
        
        String expectedSystemID = "r14000006";
        
        //Setup dependices mocks
        GregorianCalendar mockCalander = mock(GregorianCalendar.class);
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        AddressJpaController mockAddressJpaController = mock(AddressJpaController.class);
        UpEmployeeInformationJpaController mockUpEmployeeInformationJpaController = mock(UpEmployeeInformationJpaController.class);
        DBEntitiesFactory mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        AuditTrailService mockAuditTrailService = mock(AuditTrailService.class);
        
        //Load dependices mocks' into instance
        instance.setaDAO(mockAddressJpaController);
        instance.setpDAO(mockPersonJpaController);
        instance.setuDAO(mockUpEmployeeInformationJpaController);
        instance.setDbFactory(mockDBEntitiesFactory);
        instance.setAtsEJB(mockAuditTrailService);
        
        //Setup parameter mocks
        Person mockPerson = mock(Person.class);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR)).thenReturn(Boolean.FALSE);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW)).thenReturn(Boolean.FALSE);
        when(mockPersonJpaController.doesPersonHaveSecurityRole(mockPerson, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_REFEREE)).thenReturn(Boolean.FALSE);
        Address mockAddress = mock(Address.class);
        UpEmployeeInformation mockUpInfo = mock(UpEmployeeInformation.class);        
        Session mockSession = mock(Session.class);
        
        try
        {
            //Execute function
            instance.createUserAccount(mockSession, false, mockPerson, mockAddress, null);
            
            //Verify correct function behaviour            
            fail("AutomaticSystemIDGenerationException expected");
        }
        catch (Exception ex)
        {
            assertTrue(true);
        }
    }

    private void fail(String automaticSystemIDGenerationException_expe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertTrue(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Object verify(AuditTrailService mockAuditTrailService) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void verifyNoMoreInteractions(DBEntitiesFactory mockDBEntitiesFactory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Object verify(PersonJpaController mockPersonJpaController) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Object when(int get) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}