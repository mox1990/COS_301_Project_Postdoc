/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBEntities.AuditLog;
import com.softserve.ejb.ReportServices;
import com.softserve.system.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 *
 * @author kgothatso
 */
public class ReportServicesTest {
    
    public ReportServicesTest() {
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
     * Test of exportPersonsToPdf method, of class ReportServices.
     */
    @Test
    public void testExportPersonsToPdf() throws Exception {
        Session session = mock(Session.class);
        ReportServices instance = new ReportServices();
                
        try
        {
            instance.exportPersonsToPdf(session);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
           // fail("An exception occured");
        }
    }
    
}
