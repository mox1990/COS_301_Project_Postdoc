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
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
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
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
                
        try
        {
            // Test returned value...
            instance.loadAllApplicationEntities(mockSession);
            
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplicationJpaController).findApplicationEntities();
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockEntityToListConverter);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of getFieldNamesForEntity method, of class ReportServices.
     */
    @Test
    public void testGetFieldNamesForEntity() throws Exception {
        Object mockEntity = mock(Object.class);
           
        List<String> concat = new ArrayList<>();
        concat.add("int"); concat.add("double");
        when(mockEntityToListConverter.getEntityFieldNames(mockEntity)).thenReturn(concat);
        try
        {
            instance.getFieldNamesForEntity(mockEntity);
            
            verify(mockEntityToListConverter).getEntityFieldNames(mockEntity);
            
            verifyNoMoreInteractions(mockEntity);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockEntityToListConverter);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of getConcatenatedFieldNamesForEntities method, of class ReportServices.
     */
    @Test
    public void testGetConcatenatedFieldNamesForEntities() throws Exception {
        List<Object> mockEntities = mock(List.class);
           
        List<String> concat = new ArrayList<>();
        concat.add("int"); concat.add("double");
        when(mockEntityToListConverter.getConcatenatedEntityFieldNames(mockEntities)).thenReturn(concat);
        try
        {
            instance.getConcatenatedFieldNamesForEntities(mockEntities);
            
            verify(mockEntityToListConverter).getConcatenatedEntityFieldNames(mockEntities);
            
            verifyNoMoreInteractions(mockEntities);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockEntityToListConverter);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of convertEntitiesToRowData method, of class ReportServices.
     */
    @Test
    public void testConvertEntitiesToRowData() throws Exception {
        List<List<Object>> mockEntities = mock(List.class);
        List<SelectedColumn> mockPropertyMaps = mock(List.class);
        
        List<List<String>> concat = new ArrayList<>();
        when(mockEntityToListConverter.convertConcatenatedEntitiesListToListString(mockEntities, mockPropertyMaps)).thenReturn(concat);
        try
        {
            instance.convertEntitiesToRowData(mockEntities, mockPropertyMaps);
            
            verify(mockEntityToListConverter).convertConcatenatedEntitiesListToListString(mockEntities, mockPropertyMaps);
            
            verifyNoMoreInteractions(mockEntities);
            verifyNoMoreInteractions(mockPersonJpaController);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockEntityToListConverter);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /** Tested in UI and Providers part...
     * Test of createDynamicReport method, of class ReportServices.
     *
    @Test
    public void testCreateDynamicReport() throws Exception {
        
    }

    /** 
     * Test of renderReportAsHTML method, of class ReportServices.
     *
    @Test
    public void testRenderReportAsHTML() throws Exception {
        
    }

    /**
     * Test of renderReportAsPDF method, of class ReportServices.
     *
    @Test
    public void testRenderReportAsPDF() throws Exception {
        
    }

    /**
     * Test of renderReportAsMSEXCELSpreadsheet method, of class ReportServices.
     *
    @Test
    public void testRenderReportAsMSEXCELSpreadsheet() throws Exception {
        
    }*/
    
}
