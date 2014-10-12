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
    public void testCreateUserAccount() throws Exception {
        
    }

    /**
     * Test of updateUserAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testUpdateUserAccount() throws Exception {
        
    }

    /**
     * Test of removeUserAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testRemoveUserAccount() throws Exception {
        
    }

    /**
     * Test of accountReset method, of class UserAccountManagementService.
     */
    @Test
    public void testAccountReset() throws Exception {
        
    }

    /**
     * Test of generateOnDemandAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testGenerateOnDemandAccount() throws Exception {
        
    }

    /**
     * Test of activateOnDemandAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testActivateOnDemandAccount() throws Exception {
        
    }

    /**
     * Test of viewAllUserAccounts method, of class UserAccountManagementService.
     */
    @Test
    public void testViewAllUserAccounts() throws Exception {
        
    }

    /**
     * Test of getUserBySystemIDOrEmail method, of class UserAccountManagementService.
     */
    @Test
    public void testGetUserBySystemIDOrEmail() throws Exception {
        
    }

    /**
     * Test of getUserBySystemID method, of class UserAccountManagementService.
     */
    @Test
    public void testGetUserBySystemID() throws Exception {
        
    }

    /**
     * Test of approveOnDemandAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testApproveOnDemandAccount() throws Exception {
        
    }

    /**
     * Test of declineOnDemandAccount method, of class UserAccountManagementService.
     */
    @Test
    public void testDeclineOnDemandAccount() throws Exception {
        
    }

    /**
     * Test of loadAllPendingOnDemandAccounts method, of class UserAccountManagementService.
     */
    @Test
    public void testLoadAllPendingOnDemandAccounts() throws Exception {
        
    }
    
}
