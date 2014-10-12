/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import auto.softserve.XMLEntities.application.ApplicationInformation;
import auto.softserve.XMLEntities.referee.ReferalReport;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.CvJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBEntities.AcademicQualification;
import com.softserve.persistence.DBEntities.Address;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.Experience;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.ejb.applicationservices.CVManagementService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import com.softserve.persistence.DBDAO.ApplicationReviewRequestJpaController;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import org.junit.*;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import test.softserve.MockEJBClasses.GrantHolderFinalisationServiceMockUnit;
import test.softserve.MockEJBClasses.NewApplicationMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NewApplicationUnitTest {
    private NewApplicationMockUnit instance;
    
    private CVManagementService mockCVManagementService;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private NotificationService mockNotificationService;
    private ApplicationServicesUtil mockApplicationServices;
    private GregorianCalendar mockCal;
    private DAOFactory mockDAOFactory;
    private UserAccountManagementServiceLocal mockUserAccountManagementServiceLocal;
    private TransactionController mockTransactionController;
    private EntityManager mockEntityManager;
    private ApplicationJpaController mockApplicationJpaController;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new NewApplicationMockUnit();
        
        mockCVManagementService = mock(CVManagementService.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockNotificationService = mock(NotificationService.class);
        mockApplicationServices = mock(ApplicationServicesUtil.class);
        mockCal = mock(GregorianCalendar.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockUserAccountManagementServiceLocal = mock(UserAccountManagementServiceLocal.class);
        mockTransactionController = mock(TransactionController.class);
        mockEntityManager = mock(EntityManager.class);
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        
        instance.setcVEJB(mockCVManagementService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setnEJB(mockNotificationService);
        instance.setUserAccountManagementServiceLocal(mockUserAccountManagementServiceLocal);
        instance.setEntityManager(mockEntityManager);
        instance.setGregorianCalendar(mockCal);
        instance.setApplicationServicesUtil(mockApplicationServices);
        
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
    }
    
    @After
    public void tearDown() {
    }
    
    public void testCreateProspectiveFellowCV(Session session, Cv cv)
    {
        
    }
    
    public void testCreateNewApplication()
    {
        
    }
    
    public void testCreateNewApplicationNull()
    {
        
    }
    
    public void testLinkGrantHolderToApplication()
    {
        
    }
    
    public void testLinkGrantHolderToApplicationNull()
    {
        
    }
    
    public void testLinkRefereesToApplication() 
    {
        
    }
    
    public void testLinkRefereeToApplication() 
    {
        
    }
     
    public void testLinkRefereeToApplicationNull() 
    {
        
    }
    
    public void testSubmitApplication()
    {
        
    }
    
    public void testSubmitApplicationNull()
    {
        
    }
    
    public void testCanFellowOpenApplication()
    {
        
    }
    
    public void testGetOpenApplication()
    {
        
    }
}
