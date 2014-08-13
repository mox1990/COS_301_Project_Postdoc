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
import com.softserve.system.Session;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
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
    
    public ReportServicesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() 
    {
        instance = new ReportServicesMockUnit();
        
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
        
        Person a = new Person("u12236731");
        a.setEmail("It Works...");
        a.setTitle("Mr.");
        a.setAccountStatus("Chilled");
        
        Person b = new Person("qwrituqw3oty");
        b.setEmail("Damn");
        b.setTitle("Sir");
        
        List<Person> p = new ArrayList<>();
        p.add(a);
        p.add(b);
        
        try
        {
            instance.exportPersonsToPdf(session, p);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
           //fail("An exception occured");
        }
    }
    
}
