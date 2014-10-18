/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.persistence.DBDAO.AuditLogJpaController;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.ejb.nonapplicationservices.AuditTrailServiceLocal;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import test.softserve.MockEJBClasses.AuditTrailServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@RunWith(MockitoJUnitRunner.class)
public class AuditTrailUnitTest {
    private AuditTrailServiceMockUnit instance;
    
    private DAOFactory mockDAOFactory;
    private TransactionController mockTransactionController;
    private EntityManager mockEm;
    private AuditLogJpaController mockAuditLogJpaController;
    
    public AuditTrailUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new AuditTrailServiceMockUnit();
        
        mockDAOFactory = mock(DAOFactory.class);
        mockTransactionController = mock(TransactionController.class);
        mockEm = mock(EntityManager.class);
        mockAuditLogJpaController = mock(AuditLogJpaController.class);
        
        instance.setEm(mockEm);
        instance.setTransactionController(mockTransactionController);
        instance.setdAOFactory(mockDAOFactory);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
        when(mockDAOFactory.createAuditLogDAO()).thenReturn(mockAuditLogJpaController);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of logAction method, of class AuditTrailService.
     */
    @Test
    public void testLogAction() throws Exception {        
        AuditLog mockAuditLog = mock(AuditLog.class);
        when(mockAuditLog.getAction()).thenReturn("Defualt action");
        try
        {
            instance.logAction(null, mockAuditLog);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createAuditLogDAO();
            verify(mockAuditLogJpaController).create(mockAuditLog);
            
            // TODO: Verfiy work on mock application...
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockAuditLogJpaController);
            verifyNoMoreInteractions(mockEm);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void testLogActionWithLongActionLength() throws Exception {        
        AuditLog mockAuditLog = mock(AuditLog.class);
        when(mockAuditLog.getAction()).thenReturn(new String(new char[550])); 
        //when(mockAuditLog.getAction().length()).thenReturn(550);
        when(mockAuditLog.getAction().substring(0, 500)).thenReturn("Shortened action");
        String s = new String(new char[550]);
        try
        {
            instance.logAction(null, mockAuditLog);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createAuditLogDAO();
            verify(mockAuditLogJpaController).create(mockAuditLog);
            
            // TODO: Verfiy work on mock application...
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockAuditLogJpaController);
            verifyNoMoreInteractions(mockEm);
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    /**
     * Test of loadAllLogActions method, of class AuditTrailService.
     */
    @Test
    public void loadAllLogActions() throws Exception {        
        AuditLog mockAuditLog = mock(AuditLog.class);
        when(mockAuditLog.getAction()).thenReturn("Defualt action");
        try
        {
            // TODO: Implement...
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }
}
