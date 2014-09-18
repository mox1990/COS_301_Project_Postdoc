/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import com.softserve.ejb.ReportServices;
import com.softserve.ejb.UserGateway;
import com.softserve.system.Session;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import test.softserve.MockEJBClasses.ReportServicesMockUnit;

/**
 *
 * @author kgothatso
 */
public class ReportServicesTest {
    private ReportServicesMockUnit instance;
    private UserGateway mockUserGateway;
    
    public ReportServicesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception 
    {
        instance = new ReportServicesMockUnit();
        mockUserGateway = mock(UserGateway.class);
        
        instance.setuEJB(mockUserGateway);
    }
    
    @After
    public void tearDown() {
    }

    
    /**
     * Test of exportPersonsToPdf method, of class ReportServices.
     */
    //@Test
    public void testDynamicPdfReport() throws Exception {
        Session session = mock(Session.class);
        
        List<String> columnHeaders = Arrays.asList(new String[] {"Col1", "Col2", "Col3", "Col4"});
        List<List<String>> rows = new ArrayList<List<String>>();
        List<String> row1 = Arrays.asList(new String[] {"Data1", "Data2", "Data3", "Data4"});
        List<String> row2 = Arrays.asList(new String[] {"Data5", "Data6", "Data7", "Data8"});
        List<String> row3 = Arrays.asList(new String[] {"Data9", "Data10", "Data11", "Data12"});

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        
        try
        {
            instance.dynamicReport(session, columnHeaders, rows, "Sample Report Title");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
           //fail("An exception occured");
        }
    }
    
}
