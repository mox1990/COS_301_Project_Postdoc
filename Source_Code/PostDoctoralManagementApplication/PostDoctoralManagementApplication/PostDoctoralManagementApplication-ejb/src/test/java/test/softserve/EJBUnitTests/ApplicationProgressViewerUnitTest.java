/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.RefereeReport;
import com.softserve.auxiliary.requestresponseclasses.ApplicationStageStatus;
import com.softserve.ejb.applicationservices.ApplicationProgressViewerServiceLocal;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
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
import test.softserve.MockEJBClasses.ApplicationProgressViewerServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ApplicationProgressViewerUnitTest {
    private ApplicationProgressViewerServiceMockUnit instance;
    
    private ApplicationServicesUtil mockApplicationServicesUtil;
    private UserGateway mockUserGateway;
    private DAOFactory mockDAOFactory;
    private EntityManager mockEntityManager;
    private ApplicationJpaController mockApplicationJpaController;
    
    private Session mockSession;
    private Application mockApplication;
    
    public ApplicationProgressViewerUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new ApplicationProgressViewerServiceMockUnit();
        
        mockApplicationServicesUtil = mock(ApplicationServicesUtil.class);
        mockUserGateway = mock(UserGateway.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockEntityManager = mock(EntityManager.class);
        mockApplication = mock(Application.class);
        mockSession = mock(Session.class);
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        
        instance.setaEJB(mockApplicationServicesUtil);
        instance.setuEJB(mockUserGateway);
        instance.setdAOFactory(mockDAOFactory);
        instance.setEm(mockEntityManager);
        
        Person fellow = new Person("u12236731");
        Person tmp = new Person("uxxxxxxxx");
        
        when(mockApplication.getFellow()).thenReturn(fellow);
        when(mockApplication.getGrantHolder()).thenReturn(tmp);
        when(mockApplication.getTimestamp()).thenReturn(new Date());
        when(mockApplication.getSubmissionDate()).thenReturn(new Date());
        when(mockApplication.getRefereeReportList()).thenReturn(new ArrayList<RefereeReport>());
        when(mockApplication.getPersonList()).thenReturn(new ArrayList<Person>());
        
        when(mockDAOFactory.createApplicationDAO()).thenReturn(mockApplicationJpaController);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAllApplications method, of class ApplicationProgressViewerService.
     */
    @Test
    public void testGetAllApplications() throws Exception {
        List<Application> a = new ArrayList<>();
        a.add(new Application(Long.MAX_VALUE)); a.add(new Application(Long.MIN_VALUE));
        
        when(mockApplicationJpaController.findApplicationEntities()).thenReturn(a);
        try
        {
            List<Application> applications = instance.getAllApplications(mockSession);
            
            verify(mockDAOFactory).createApplicationDAO();
            verify(mockApplicationJpaController).findApplicationEntities();
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockApplicationJpaController);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockUserGateway);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockApplication);
            verifyNoMoreInteractions(mockSession);
            
            assertArrayEquals(applications.toArray(), a.toArray());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }

    /**
     * Test of getApplicationProgress method, of class ApplicationProgressViewerService.
     */
    @Test
    public void testGetApplicationProgress() throws Exception {
        
        try
        {
            instance.getApplicationProgress(mockSession, mockApplication);
            
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }
    
}
