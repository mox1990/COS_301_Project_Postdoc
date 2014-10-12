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
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.ejb.applicationservices.RefereeReportServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.RefereeReportJpaController;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.RefereeReport;
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
import test.softserve.MockEJBClasses.RefereeReportMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class RefereeReportUnitTest {
    private RefereeReportMockUnit instance;
    
    private GregorianCalendar mockGregorianCalendar;
    private NotificationService mockNotificationService;
    private DBEntitiesFactory mockDBEntitiesFactory;
    private ApplicationServicesUtil mockApplicationServicesUtil;
    private DAOFactory mockDAOFactory;
    private TransactionController mockTransactionController;
    private EntityManager mockEntityManager;
    private RefereeReportJpaController mockRefereeReportJpaController;
    private ApplicationJpaController mockApplicationJpaController;
    
    public RefereeReportUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new RefereeReportMockUnit();
        
        mockGregorianCalendar = mock(GregorianCalendar.class);
        mockNotificationService = mock(NotificationService.class);
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockApplicationServicesUtil = mock(ApplicationServicesUtil.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockTransactionController = mock(TransactionController.class);
        mockEntityManager = mock(EntityManager.class);
        mockRefereeReportJpaController = mock(RefereeReportJpaController.class);
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        
        instance.setCal(mockGregorianCalendar);
        instance.setnEJB(mockNotificationService);
        instance.setdBEntitities(mockDBEntitiesFactory);
        instance.setaSEJB(mockApplicationServicesUtil);
        instance.setdAOFactory(mockDAOFactory);
        instance.setTransactionController(mockTransactionController);
        instance.setEntityManager(mockEntityManager);
        
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
        when(mockDAOFactory.createRefereeReportDAO()).thenReturn(mockRefereeReportJpaController);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadPendingApplications method, of class RefereeReportService.
     */
    @Test
    public void testLoadPendingApplications() throws Exception {
        
    }

    /**
     * Test of countTotalPendingApplications method, of class RefereeReportService.
     */
    @Test
    public void testCountTotalPendingApplications() throws Exception {
        
    }

    /**
     * Test of submitReferralReport method, of class RefereeReportService.
     */
    @Test
    public void testSubmitReferralReport() throws Exception {
        
    }
    
}
