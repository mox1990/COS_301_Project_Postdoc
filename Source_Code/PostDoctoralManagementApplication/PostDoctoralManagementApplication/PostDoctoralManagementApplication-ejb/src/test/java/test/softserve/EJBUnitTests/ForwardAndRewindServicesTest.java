/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.auxillary.factories.DAOFactory;
import com.softserve.auxillary.factories.DBEntitiesFactory;
import com.softserve.auxillary.requestresponseclasses.Session;
import com.softserve.auxillary.transactioncontrollers.TransactionController;
import com.softserve.auxillary.util.ApplicationServicesUtil;
import com.softserve.ejb.applicationservices.ForwardAndRewindServicesLocal;
import com.softserve.persistence.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.persistence.DBDAO.RefereeReportJpaController;
import com.softserve.persistence.DBEntities.Application;
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
import test.softserve.MockEJBClasses.ForwardAndRewindServicesMockUnit;

/**
 *
 * @author kgothatso
 */
public class ForwardAndRewindServicesTest {
    private ForwardAndRewindServicesMockUnit instance;
    
    private DBEntitiesFactory mockDBEntitiesFactory;
    private DAOFactory mockDAOFactory;
    private TransactionController mockTransactionController;
    private ApplicationServicesUtil mockApplicationServicesUtil;
    private GregorianCalendar mockGregorianCalendar;
    private EntityManager mockEm; 
    
    private RefereeReportJpaController mockRefereeReportJpaController;
    private ApplicationReviewRequestJpaController mockApplicationReviewRequestJpaController;
    
    public ForwardAndRewindServicesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new ForwardAndRewindServicesMockUnit();
        
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockTransactionController = mock(TransactionController.class);
        mockApplicationServicesUtil = mock(ApplicationServicesUtil.class);
        mockGregorianCalendar = mock(GregorianCalendar.class);
        mockEm = mock(EntityManager.class);
        mockRefereeReportJpaController = mock(RefereeReportJpaController.class);
        mockApplicationReviewRequestJpaController = mock(ApplicationReviewRequestJpaController.class);
        
        instance.setApplicationServicesUtil(mockApplicationServicesUtil);
        instance.setEm(mockEm);
        instance.setGregorianCalendar(mockGregorianCalendar);
        instance.setTransactionController(mockTransactionController);
        instance.setdAOFactory(mockDAOFactory);
        instance.setdBEntitiesFactory(mockDBEntitiesFactory);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
        when(mockDAOFactory.createRefereeReportDAO()).thenReturn(mockRefereeReportJpaController);
        when(mockDAOFactory.createApplicationReviewRequestDAO()).thenReturn(mockApplicationReviewRequestJpaController);
        //TODO: Set all the nullable pieces...
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of forwardApplication method, of class ForwardAndRewindServices.
     */
    @Test
    public void testForwardApplication() throws Exception {
        
    }

    /**
     * Test of rewindApplication method, of class ForwardAndRewindServices.
     */
    @Test
    public void testRewindApplication() throws Exception {
        
    }

    /**
     * Test of loadMovableApplications method, of class ForwardAndRewindServices.
     */
    @Test
    public void testLoadMovableApplications() throws Exception {
        
    }
    
}
