/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import ar.com.fdvs.dj.domain.DynamicReport;
import com.softserve.auxiliary.converters.EntityToListConverter;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.requestresponseclasses.DynamicReportCreationRequest;
import com.softserve.auxiliary.requestresponseclasses.DynamicReportExportRequest;
import com.softserve.auxiliary.requestresponseclasses.SelectedColumn;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Person;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import test.softserve.MockEJBClasses.ReportServicesMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ReportUnitTest {
    private ReportServicesMockUnit instance;
    
    private DAOFactory mockDAOFactory;
    private EntityToListConverter mockEntityToListConverter;
    private EntityManager mockEntityManager;
    private ApplicationJpaController mockApplicationJpaController;
    private PersonJpaController mockPersonJpaController;
    
    public ReportUnitTest() {
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
        
        mockEntityManager = mock(EntityManager.class);
        mockEntityToListConverter = mock(EntityToListConverter.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockPersonJpaController = mock(PersonJpaController.class);
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        
        instance.setEntityManager(mockEntityManager);
        instance.setEntityToListConverter(mockEntityToListConverter);
        instance.setdAOFactory(mockDAOFactory);
        
        when(mockDAOFactory.createPersonDAO()).thenReturn(mockPersonJpaController);
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadAllAuditLogEntries method, of class ReportServices.
     */
    @Test
    public void testLoadAllAuditLogEntries() throws Exception {
        
    }

    /**
     * Test of loadAllPersonEntities method, of class ReportServices.
     */
    @Test
    public void testLoadAllPersonEntities() throws Exception {
        
    }

    /**
     * Test of loadAllApplicationEntities method, of class ReportServices.
     */
    @Test
    public void testLoadAllApplicationEntities() throws Exception {
        
    }

    /**
     * Test of getFieldNamesForEntity method, of class ReportServices.
     */
    @Test
    public void testGetFieldNamesForEntity() throws Exception {
        
    }

    /**
     * Test of getConcatenatedFieldNamesForEntities method, of class ReportServices.
     */
    @Test
    public void testGetConcatenatedFieldNamesForEntities() throws Exception {
        
    }

    /**
     * Test of convertEntitiesToRowData method, of class ReportServices.
     */
    @Test
    public void testConvertEntitiesToRowData() throws Exception {
        
    }

    /**
     * Test of createDynamicReport method, of class ReportServices.
     */
    @Test
    public void testCreateDynamicReport() throws Exception {
        
    }

    /**
     * Test of renderReportAsHTML method, of class ReportServices.
     */
    @Test
    public void testRenderReportAsHTML() throws Exception {
        
    }

    /**
     * Test of renderReportAsPDF method, of class ReportServices.
     */
    @Test
    public void testRenderReportAsPDF() throws Exception {
        
    }

    /**
     * Test of renderReportAsMSEXCELSpreadsheet method, of class ReportServices.
     */
    @Test
    public void testRenderReportAsMSEXCELSpreadsheet() throws Exception {
        
    }
    
}
