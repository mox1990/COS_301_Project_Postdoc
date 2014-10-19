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
import com.softserve.auxiliary.util.GeneratorUtil;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import com.softserve.persistence.DBDAO.AddressJpaController;
import com.softserve.persistence.DBDAO.AuditLogJpaController;
import com.softserve.persistence.DBDAO.EmployeeInformationJpaController;
import com.softserve.persistence.DBDAO.NotificationJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBDAO.ResearchFellowInformationJpaController;
import com.softserve.persistence.DBEntities.Address;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Person;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import test.softserve.MockEJBClasses.UserAccountManagementServicesMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class UserAccountManagementUnitTest {
    private UserAccountManagementServicesMockUnit instance;
    
    private AuditTrailService mockAuditTrailService;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private NotificationService mockNotificationService;
    private GregorianCalendar mockGregorianCalendar;
    private DAOFactory mockDAOFactory;
    private TransactionController mockTransactionController;
    private EntityManager mockEntityManager;
    private GeneratorUtil mockGeneratorUtil;
    
    private PersonJpaController mockPersonJpaController;
    private NotificationJpaController mockNotificationJpaController;
    private AuditLogJpaController mockAuditLogJpaController;
    private EmployeeInformationJpaController mockEmployeeInformationJpaController;
    private AddressJpaController mockAddressJpaController;
    private ResearchFellowInformationJpaController mockResearchFellowInformationJpaController;
    
    
    public UserAccountManagementUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new UserAccountManagementServicesMockUnit();
        
        mockAuditTrailService = mock(AuditTrailService.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockNotificationService = mock(NotificationService.class);
        mockGregorianCalendar = mock(GregorianCalendar.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockTransactionController = mock(TransactionController.class);
        mockEntityManager = mock(EntityManager.class);
        mockGeneratorUtil = mock(GeneratorUtil.class);
        
        mockPersonJpaController = mock(PersonJpaController.class);
        mockNotificationJpaController = mock(NotificationJpaController.class);
        mockAuditLogJpaController = mock(AuditLogJpaController.class);
        mockEmployeeInformationJpaController = mock(EmployeeInformationJpaController.class);
        mockAddressJpaController = mock(AddressJpaController.class);
        mockResearchFellowInformationJpaController = mock(ResearchFellowInformationJpaController.class);
        
        instance.setaService(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setgCal(mockGregorianCalendar);
        instance.setdAOFactory(mockDAOFactory);
        instance.setTransactionController(mockTransactionController);
        instance.setEntityManager(mockEntityManager);
        instance.setGeneratorUtil(mockGeneratorUtil);
        
        when(mockDAOFactory.createPersonDAO()).thenReturn(mockPersonJpaController);
        when(mockDAOFactory.createNotificationDAO()).thenReturn(mockNotificationJpaController);
        when(mockDAOFactory.createAuditLogDAO()).thenReturn(mockAuditLogJpaController);
        when(mockDAOFactory.createEmployeeInformationDAO()).thenReturn(mockEmployeeInformationJpaController);
        when(mockDAOFactory.createAddressDAO()).thenReturn(mockAddressJpaController);
        when(mockDAOFactory.createResearchFellowInformationDAO()).thenReturn(mockResearchFellowInformationJpaController);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createUserAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testCreateUserAccountOfFellow() throws Exception {
        Session mockSession = mock(Session.class);
        Person mockUser = mock(Person.class);
        
        Address mockAddress = mock(Address.class);
        //AuditLog mockAuditLog = mock(AuditLog.class);
        
        when(mockUser.getSystemID()).thenReturn("u12236731");
        when(mockUser.getAddressLine1()).thenReturn(mockAddress);
        when(mockPersonJpaController.findUserBySystemIDOrEmail("u12236731")).thenReturn(null);
        try
        {
            instance.createUserAccount(mockSession, true, mockUser);
            
            verify(mockDAOFactory, times(2)).createPersonDAO();
            verify(mockPersonJpaController).findUserBySystemIDOrEmail("u12236731");
            verify(mockEntityManager).close();
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createEmployeeInformationDAO();
            verify(mockDAOFactory).createAddressDAO();
            
            verify(mockAddressJpaController).create(mockAddress);
            // TODO: User interactions...
            verify(mockPersonJpaController).create(mockUser);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            //verifyNoMoreInteractions(mockAuditTrailService);
            //verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGeneratorUtil);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockNotificationJpaController);
            verifyNoMoreInteractions(mockAuditLogJpaController);
            verifyNoMoreInteractions(mockEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockAddressJpaController);
            verifyNoMoreInteractions(mockResearchFellowInformationJpaController);
                    
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of updateUserAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testUpdateUserAccount() throws Exception {
        Session mockSession = mock(Session.class);
        Person mockUser = mock(Person.class);
        
        Address mockAddress = mock(Address.class);
        //AuditLog mockAuditLog = mock(AuditLog.class);
        
        when(mockUser.getSystemID()).thenReturn("u12236731");
        when(mockUser.getEmail()).thenReturn("kngako@ymail.com");
        when(mockUser.getAddressLine1()).thenReturn(mockAddress);
        when(mockPersonJpaController.findUserBySystemIDOrEmail("u12236731")).thenReturn(mockUser);
        when(mockPersonJpaController.findUserBySystemIDOrEmail("kngako@ymail.com")).thenReturn(mockUser);
        try
        {
            instance.updateUserAccount(mockSession, mockUser);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createEmployeeInformationDAO();
            verify(mockDAOFactory).createAddressDAO();
            verify(mockDAOFactory).createResearchFellowInformationDAO();
            
            verify(mockDAOFactory, times(4)).createPersonDAO();
            verify(mockPersonJpaController, times(2)).findUserBySystemIDOrEmail("kngako@ymail.com");
            verify(mockPersonJpaController).findUserBySystemIDOrEmail("u12236731");
            verify(mockEntityManager, times(3)).close();
            
            verify(mockEmployeeInformationJpaController).findEmployeeInformation("u12236731");
            verify(mockResearchFellowInformationJpaController).findResearchFellowInformation("u12236731");
            verify(mockAddressJpaController).edit(mockAddress);
            // TODO: User interactions...
            verify(mockPersonJpaController).edit(mockUser);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockAuditTrailService);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGeneratorUtil);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockNotificationJpaController);
            verifyNoMoreInteractions(mockAuditLogJpaController);
            verifyNoMoreInteractions(mockEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockAddressJpaController);
            verifyNoMoreInteractions(mockResearchFellowInformationJpaController);
                    
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of removeUserAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testRemoveUserAccount() throws Exception {
        Session mockSession = mock(Session.class);
        Person mockUser = mock(Person.class);
        
        Address mockAddress = mock(Address.class);
        //AuditLog mockAuditLog = mock(AuditLog.class);
        
        when(mockUser.getSystemID()).thenReturn("u12236731");
        when(mockUser.getEmail()).thenReturn("kngako@ymail.com");
        when(mockUser.getAddressLine1()).thenReturn(mockAddress);
        when(mockPersonJpaController.findPerson("u12236731")).thenReturn(mockUser);
        when(mockSession.getUser()).thenReturn(new Person("u12345678"));
        try
        {
            instance.removeUserAccount(mockSession, "u12236731");
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createPersonDAO();
            
            verify(mockPersonJpaController).findPerson("u12236731");
            verify(mockUser).setAccountStatus(com.softserve.auxiliary.constants.PersistenceConstants.ACCOUNT_STATUS_DISABLED);
            verify(mockPersonJpaController).edit(mockUser);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockAuditTrailService);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGeneratorUtil);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockNotificationJpaController);
            verifyNoMoreInteractions(mockAuditLogJpaController);
            verifyNoMoreInteractions(mockEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockAddressJpaController);
            verifyNoMoreInteractions(mockResearchFellowInformationJpaController);
                    
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of accountReset method, of class UserAccountManagementService.
     */
    @Test
    public void testAccountReset() throws Exception {
        Person mockUser = mock(Person.class);
        
        Address mockAddress = mock(Address.class);
        //AuditLog mockAuditLog = mock(AuditLog.class);
        
        when(mockUser.getSystemID()).thenReturn("u12236731");
        when(mockUser.getEmail()).thenReturn("kngako@ymail.com");
        when(mockUser.getAddressLine1()).thenReturn(mockAddress);
        when(mockPersonJpaController.findPerson("u12236731")).thenReturn(mockUser);
        when(mockGeneratorUtil.generateRandomHexString()).thenReturn("u87654321");
        try
        {
            instance.accountReset(mockUser);
            
            verify(mockDAOFactory, times(2)).createPersonDAO();
            verify(mockPersonJpaController).findPerson("u12236731");
            verify(mockEntityManager).close();
            
            verify(mockTransactionController).StartTransaction();
            verify(mockGeneratorUtil).generateRandomHexString();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory, times(2)).createPersonDAO();
            
            verify(mockUser).setPassword("u87654321");
            verify(mockPersonJpaController).edit(mockUser);
            // TODO: Notifications...
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockAuditTrailService);
            //TODO: verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGeneratorUtil);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockNotificationJpaController);
            verifyNoMoreInteractions(mockAuditLogJpaController);
            verifyNoMoreInteractions(mockEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockAddressJpaController);
            verifyNoMoreInteractions(mockResearchFellowInformationJpaController);
                    
        }   
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of generateOnDemandAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testGenerateOnDemandAccount() throws Exception {
        Session mockSession = mock(Session.class);
        Person mockUser = mock(Person.class);
        
        Address mockAddress = mock(Address.class);
        //AuditLog mockAuditLog = mock(AuditLog.class);
        
        when(mockUser.getSystemID()).thenReturn("u12236731");
        when(mockUser.getAddressLine1()).thenReturn(mockAddress);
        when(mockPersonJpaController.findUserBySystemIDOrEmail("u12236731")).thenReturn(null);
        when(mockSession.getUser()).thenReturn(new Person("uxxxxxxxx"));
        when(mockGeneratorUtil.generateRandomHexString()).thenReturn("u87654321");
        try
        {
            instance.generateOnDemandAccount(mockSession, "Testing", true, mockUser);
            
            verify(mockDAOFactory, times(3)).createPersonDAO();
            verify(mockPersonJpaController).findUserBySystemIDOrEmail("u12236731");
            verify(mockEntityManager, times(2)).close();
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createEmployeeInformationDAO();
            verify(mockDAOFactory).createAddressDAO();
            verify(mockGeneratorUtil).generateRandomHexString();
            
            verify(mockAddressJpaController).create(mockAddress);
            // TODO: User interactions...
            verify(mockPersonJpaController).create(mockUser);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            //verifyNoMoreInteractions(mockAuditTrailService);
            //verifyNoMoreInteractions(mockDBEntitiesFactory);
            //verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGeneratorUtil);
            // TODO: verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockNotificationJpaController);
            verifyNoMoreInteractions(mockAuditLogJpaController);
            verifyNoMoreInteractions(mockEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockAddressJpaController);
            verifyNoMoreInteractions(mockResearchFellowInformationJpaController);
                    
        }   
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of activateOnDemandAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testActivateOnDemandAccount() throws Exception {
        Session mockSession = mock(Session.class);
        Person mockUser = mock(Person.class);
        
        Address mockAddress = mock(Address.class);
        //AuditLog mockAuditLog = mock(AuditLog.class);
        
        when(mockUser.getSystemID()).thenReturn("u12236731");
        when(mockUser.getEmail()).thenReturn("kngako@ymail.com");
        when(mockUser.getAddressLine1()).thenReturn(mockAddress);
        when(mockPersonJpaController.findUserBySystemIDOrEmail("u12236731")).thenReturn(mockUser);
        when(mockPersonJpaController.findUserBySystemIDOrEmail("kngako@ymail.com")).thenReturn(mockUser);
        try
        {
            instance.activateOnDemandAccount(mockSession, mockUser);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createEmployeeInformationDAO();
            verify(mockDAOFactory).createAddressDAO();
            verify(mockDAOFactory).createResearchFellowInformationDAO();
            
            verify(mockDAOFactory, times(4)).createPersonDAO();
            verify(mockPersonJpaController, times(2)).findUserBySystemIDOrEmail("kngako@ymail.com");
            verify(mockPersonJpaController).findUserBySystemIDOrEmail("u12236731");
            verify(mockEntityManager, times(3)).close();
            
            verify(mockEmployeeInformationJpaController).findEmployeeInformation("u12236731");
            verify(mockResearchFellowInformationJpaController).findResearchFellowInformation("u12236731");
            verify(mockAddressJpaController).edit(mockAddress);
            // TODO: User interactions...
            verify(mockPersonJpaController).edit(mockUser);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            // TODO: verifyNoMoreInteractions(mockAuditTrailService);
            // verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGeneratorUtil);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockNotificationJpaController);
            verifyNoMoreInteractions(mockAuditLogJpaController);
            verifyNoMoreInteractions(mockEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockAddressJpaController);
            verifyNoMoreInteractions(mockResearchFellowInformationJpaController);
                    
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of viewAllUserAccounts method, of class UserAccountManagementService.
     */
    @Test
    public void testViewAllUserAccounts() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        try
        {
            instance.viewAllUserAccounts(mockSession);
            
            verify(mockDAOFactory).createPersonDAO();
            verify(mockPersonJpaController).findPersonEntities();
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockAuditTrailService);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGeneratorUtil);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockNotificationJpaController);
            verifyNoMoreInteractions(mockAuditLogJpaController);
            verifyNoMoreInteractions(mockEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockAddressJpaController);
            verifyNoMoreInteractions(mockResearchFellowInformationJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of getUserBySystemIDOrEmail method, of class UserAccountManagementService.
     */
    @Test
    public void testGetUserBySystemIDOrEmail() throws Exception {
        when(mockPersonJpaController.findUserBySystemIDOrEmail("u12236731")).thenReturn(new Person("u12236731"));
        try
        {
            Person result = instance.getUserBySystemIDOrEmail("u12236731");
            
            assertEquals(new Person("u12236731"), result);
            verify(mockDAOFactory).createPersonDAO();
            verify(mockPersonJpaController).findUserBySystemIDOrEmail("u12236731");
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockAuditTrailService);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGeneratorUtil);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockNotificationJpaController);
            verifyNoMoreInteractions(mockAuditLogJpaController);
            verifyNoMoreInteractions(mockEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockAddressJpaController);
            verifyNoMoreInteractions(mockResearchFellowInformationJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of getUserBySystemID method, of class UserAccountManagementService.
     */
    @Test
    public void testGetUserBySystemID() throws Exception {
        when(mockPersonJpaController.findUserBySystemIDOrEmail("u12236731")).thenReturn(new Person("u12236731"));
        try
        {
            Person result = instance.getUserBySystemIDOrEmail("u12236731");
            
            assertEquals(new Person("u12236731"), result);
            verify(mockDAOFactory).createPersonDAO();
            verify(mockPersonJpaController).findUserBySystemIDOrEmail("u12236731");
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockAuditTrailService);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGeneratorUtil);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockNotificationJpaController);
            verifyNoMoreInteractions(mockAuditLogJpaController);
            verifyNoMoreInteractions(mockEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockAddressJpaController);
            verifyNoMoreInteractions(mockResearchFellowInformationJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of approveOnDemandAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testApproveOnDemandAccount() throws Exception {
        Session mockSession = mock(Session.class);
        Person mockUser = mock(Person.class);
        
        Address mockAddress = mock(Address.class);
        //AuditLog mockAuditLog = mock(AuditLog.class);
        
        when(mockUser.getSystemID()).thenReturn("u12236731");
        when(mockUser.getEmail()).thenReturn("kngako@ymail.com");
        when(mockUser.getAddressLine1()).thenReturn(mockAddress);
        when(mockPersonJpaController.findPerson("u12236731")).thenReturn(mockUser);
        try
        {
            instance.approveOnDemandAccount(mockSession, mockUser);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();            
            verify(mockDAOFactory).createPersonDAO();
            verify(mockPersonJpaController).findPerson("u12236731");
            verify(mockPersonJpaController).edit(mockUser);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            // TODO: verifyNoMoreInteractions(mockAuditTrailService);
            // verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockGregorianCalendar);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockGeneratorUtil);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockNotificationJpaController);
            verifyNoMoreInteractions(mockAuditLogJpaController);
            verifyNoMoreInteractions(mockEmployeeInformationJpaController);
            verifyNoMoreInteractions(mockAddressJpaController);
            verifyNoMoreInteractions(mockResearchFellowInformationJpaController);
                    
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of declineOnDemandAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testDeclineOnDemandAccount() throws Exception {
        // TODO:
    }

    /**
     * Test of loadAllPendingOnDemandAccounts method, of class UserAccountManagementService.
     */
    @Test
    public void testLoadAllPendingOnDemandAccounts() throws Exception {
        // TODO:
    }
    
}
