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
        instance.setdAOFactory(mockDAOFactory);
        instance.setTransactionController(mockTransactionController);
        
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testCreateProspectiveFellowCV()
    {
        Cv mockCV = mock(Cv.class);  
        Session mockSession = mock(Session.class);
        
        when(mockCVManagementService.hasCV(mockSession)).thenReturn(Boolean.FALSE);
        
        try
        {
            instance.createProspectiveFellowCV(mockSession, mockCV);
            
            verify(mockCVManagementService).createCV(mockSession, mockCV);
            verify(mockCVManagementService).hasCV(mockSession);
            
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void testCreateProspectiveFellowCVWithCV()
    {
        Cv mockCV = mock(Cv.class);  
        Session mockSession = mock(Session.class);
        
        when(mockCVManagementService.hasCV(mockSession)).thenReturn(Boolean.TRUE);
        
        try
        {
            instance.createProspectiveFellowCV(mockSession, mockCV);
            
            verify(mockCVManagementService).updateCV(mockSession, mockCV);
            verify(mockCVManagementService).hasCV(mockSession);
            
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void testCreateProspectiveFellowCVNotValid()
    {
        Session mockSession = mock(Session.class);
        
        try
        {
            instance.createProspectiveFellowCV(mockSession, null);
            
            fail("An exception should have occured");
            
            verify(mockCVManagementService).updateCV(mockSession, null);
            verify(mockCVManagementService).hasCV(mockSession);
            
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            if(!ex.getMessage().equals("CV is not valid"))
                fail("An exception should have occured");
        }
    }
    
    @Test
    public void testCreateNewApplication()
    {
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        
        //when(mockApplicationJpaController.findApplication(mockApplication.getApplicationID())).thenReturn(mockApplication);
        try
        {
            instance.createNewApplication(mockSession, mockApplication);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplicationJpaController).findApplication(mockApplication.getApplicationID());
            verify(mockCal).getTime();
            verify(mockApplicationJpaController).create(mockApplication);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void testCreateNewApplicationNull()
    {
        Session mockSession = mock(Session.class);
        try
        {
            instance.createNewApplication(mockSession, null);
            
            verifyNoMoreInteractions(mockCVManagementService);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verifyNoMoreInteractions(mockNotificationService);
            verifyNoMoreInteractions(mockApplicationServices);
            verifyNoMoreInteractions(mockCal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockEntityManager);
        }
        catch (Exception ex)
        {
            if(!ex.getMessage().equals("Application is not valid"))
                fail("An exception should have occured");
        }
    }
    
    //TODO: The rest of these test...
    @Test
    public void testLinkGrantHolderToApplication()
    {
        
    }
    
    @Test
    public void testLinkGrantHolderToApplicationNull()
    {
        
    }
    
    @Test
    public void testLinkRefereesToApplication() 
    {
        
    }
    
    @Test
    public void testLinkRefereeToApplication() 
    {
        
    }
     
    @Test
    public void testLinkRefereeToApplicationNull() 
    {
        
    }
    
    @Test
    public void testSubmitApplication()
    {
        
    }
    
    @Test
    public void testSubmitApplicationNull()
    {
        
    }
    
    @Test
    public void testCanFellowOpenApplication()
    {
        
    }
    
    @Test
    public void testGetOpenApplication()
    {
        
    }
}
