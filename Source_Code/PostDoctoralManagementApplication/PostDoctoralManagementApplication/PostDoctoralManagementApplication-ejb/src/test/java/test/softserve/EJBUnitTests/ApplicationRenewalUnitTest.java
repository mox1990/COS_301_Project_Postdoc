/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.ProgressReport;
import com.softserve.ejb.applicationservices.ApplicationRenewalService;
import com.softserve.ejb.applicationservices.ApplicationRenewalServiceLocal;
import com.softserve.ejb.applicationservices.CVManagementServiceLocal;
import com.softserve.ejb.applicationservices.ProgressReportManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
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
import test.softserve.MockEJBClasses.ApplicationRenewalServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ApplicationRenewalUnitTest {
    private ApplicationRenewalServiceMockUnit instance;
    
    private NotificationServiceLocal mockNotificationServiceLocal;
    private CVManagementServiceLocal mockCVManagementServiceLocal;
    private ProgressReportManagementServiceLocal mockProgressReportManagementServiceLocal;
    private DAOFactory mockDAOFactory;
    private TransactionController mockTransactionController;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private ApplicationServicesUtil mockApplicationServicesUtil;
    private GregorianCalendar mockGregorianCalendar;
    private EntityManager em;
    
    public ApplicationRenewalUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new ApplicationRenewalServiceMockUnit();
        
        mockNotificationServiceLocal = mock(NotificationServiceLocal.class);
        mockCVManagementServiceLocal = mock(CVManagementServiceLocal.class);
        mockProgressReportManagementServiceLocal = mock(ProgressReportManagementServiceLocal.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockTransactionController = mock(TransactionController.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockApplicationServicesUtil = mock(ApplicationServicesUtil.class);
        mockGregorianCalendar = mock(GregorianCalendar.class);
        
        instance.setNotificationServiceLocal(mockNotificationServiceLocal);
        instance.setcVManagementServiceLocal(mockCVManagementServiceLocal);
        instance.setProgressReportManagementServiceLocal(mockProgressReportManagementServiceLocal);
        instance.setdAOFactory(mockDAOFactory);
        instance.setTransactionController(mockTransactionController);
        instance.setdBEntitiesFactory(mockDBEntitiesFactory);
        instance.setApplicationServicesUtil(mockApplicationServicesUtil);
        instance.setGregorianCalendar(mockGregorianCalendar);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getRenewableApplicationsForFellow method, of class ApplicationRenewalService.
     */
    @Test
    public void testGetRenewableApplicationsForFellow() throws Exception {
        
    }

    /**
     * Test of doesApplicationHaveFinalProgressReport method, of class ApplicationRenewalService.
     */
    @Test
    public void testDoesApplicationHaveFinalProgressReport() throws Exception {
        
    }

    /**
     * Test of updateResearchFellowCV method, of class ApplicationRenewalService.
     */
    @Test
    public void testUpdateResearchFellowCV() throws Exception {
        
    }

    /**
     * Test of createFinalProgressReportForApplication method, of class ApplicationRenewalService.
     */
    @Test
    public void testCreateFinalProgressReportForApplication() throws Exception {
        
    }

    /**
     * Test of createRenewalApplication method, of class ApplicationRenewalService.
     */
    @Test
    public void testCreateRenewalApplication() throws Exception {
        
    }

    /**
     * Test of submitApplication method, of class ApplicationRenewalService.
     */
    @Test
    public void testSubmitApplication() throws Exception {
        
    }
    
}
