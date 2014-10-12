/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.NeuralNetworkCreationRequest;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.NeuralNetworkJpaController;
import com.softserve.persistence.DBDAO.NeuronJpaController;
import com.softserve.persistence.DBDAO.RecommendationReportJpaController;
import com.softserve.persistence.DBDAO.SynapseJpaController;
import com.softserve.persistence.DBEntities.NeuralNetwork;
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
import test.softserve.MockEJBClasses.NeuralNetworkManagementServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NeuralNetworkManagementServicesTest {
    private NeuralNetworkManagementServiceMockUnit instance;
    
    private DBEntitiesFactory mockDBEntitiesFactory;
    private GregorianCalendar mockCal;
    private TransactionController mockTransactionController;
    private DAOFactory mockDAOFactory;
    private EntityManager mockEntityManager;
    private NeuralNetworkJpaController mockNeuralNetworkJpaController;
    private SynapseJpaController mockSynapseJpaController;
    private NeuronJpaController mockNeuronJpaController;
    
    public NeuralNetworkManagementServicesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new NeuralNetworkManagementServiceMockUnit();
        
        mockDBEntitiesFactory = mock(DBEntitiesFactory.class);
        mockCal = mock(GregorianCalendar.class);
        mockTransactionController = mock(TransactionController.class);
        mockEntityManager = mock(EntityManager.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockNeuronJpaController = mock(NeuronJpaController.class);
        mockNeuralNetworkJpaController = mock(NeuralNetworkJpaController.class);
        mockSynapseJpaController = mock(SynapseJpaController.class);
        
        instance.setdBEntities(mockDBEntitiesFactory);
        instance.setgCal(mockCal);
        instance.setTransactionController(mockTransactionController);
        instance.setEntityManager(mockEntityManager);
        instance.setdAOFactory(mockDAOFactory);
        
        when(mockDAOFactory.createNeuralNetworkDAO()).thenReturn(mockNeuralNetworkJpaController);
        when(mockDAOFactory.createNeuronDAO()).thenReturn(mockNeuronJpaController);
        when(mockDAOFactory.createSynapseDAO()).thenReturn(mockSynapseJpaController);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadAllNeuralNetworks method, of class NeuralNetworkManagementServices.
     */
    @Test
    public void testLoadAllNeuralNetworks() throws Exception {
        
    }

    /**
     * Test of getDefaultNeuralNetwork method, of class NeuralNetworkManagementServices.
     */
    @Test
    public void testGetDefaultNeuralNetwork() throws Exception {
        
    }

    /**
     * Test of createNeuralNetwork method, of class NeuralNetworkManagementServices.
     */
    @Test
    public void testCreateNeuralNetwork() throws Exception {
        
    }

    /**
     * Test of makeNeuralNetworkDefaultNetwork method, of class NeuralNetworkManagementServices.
     */
    @Test
    public void testMakeNeuralNetworkDefaultNetwork() throws Exception {
        
    }

    /**
     * Test of updateNeuralNetwork method, of class NeuralNetworkManagementServices.
     */
    @Test
    public void testUpdateNeuralNetwork() throws Exception {
        
    }

    /**
     * Test of updateNeuralNetworkSynapses method, of class NeuralNetworkManagementServices.
     */
    @Test
    public void testUpdateNeuralNetworkSynapses() throws Exception {
        
    }

    /**
     * Test of removeNeuralNetwork method, of class NeuralNetworkManagementServices.
     */
    @Test
    public void testRemoveNeuralNetwork() throws Exception {
        
    }

    /**
     * Test of runNeuralNetwork method, of class NeuralNetworkManagementServices.
     */
    @Test
    public void testRunNeuralNetwork() throws Exception {
        
    }

    /**
     * Test of correctNeuralNetwork method, of class NeuralNetworkManagementServices.
     */
    @Test
    public void testCorrectNeuralNetwork() throws Exception {
        
    }

    /**
     * Test of trainNeuralNetwork method, of class NeuralNetworkManagementServices.
     */
    @Test
    public void testTrainNeuralNetwork() throws Exception {
        
    }
    
}
