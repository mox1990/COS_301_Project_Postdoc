/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.AuditLogJpaController;
import com.softserve.DBEntities.AuditLog;
import com.softserve.ejb.AuditTrailServiceLocal;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import test.softserve.MockEJBClasses.AuditTrailServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@RunWith(MockitoJUnitRunner.class)
public class AuditTrailUnitTest {
    
    public AuditTrailUnitTest() {
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

    /**
     * Test of logAction method, of class AuditTrailService.
     */
    @Test
    public void testLogAction() throws Exception {
        AuditTrailServiceMockUnit instance = new AuditTrailServiceMockUnit();
        
        AuditLogJpaController mockAuditLogJpaController = mock(AuditLogJpaController.class);
        
        instance.setADAO(mockAuditLogJpaController);
        
        AuditLog mockAuditLog = mock(AuditLog.class);        
        try
        {
            instance.logAction(mockAuditLog);
            verify(mockAuditLogJpaController).create(mockAuditLog);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
}
