/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.auxillary.factories.DAOFactory;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.RefereeReport;
import com.softserve.auxillary.requestresponseclasses.ApplicationStageStatus;
import com.softserve.ejb.applicationservices.ApplicationProgressViewerServiceLocal;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxillary.util.ApplicationServicesUtil;
import com.softserve.auxillary.requestresponseclasses.Session;
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
 * @author kgothatso
 */
public class ApplicationProgressViewerUnitTest {
    private ApplicationProgressViewerServiceMockUnit instance;
    private ApplicationServicesUtil mockApplicationServicesUtil;
    private UserGateway mockUserGateway;
    private DAOFactory mockDAOFactory;
    private EntityManager mockEntityManager;
    
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
        
        Person fellow = new Person("u12236731");
        Person tmp = new Person("uxxxxxxxx");
        
        when(mockApplication.getFellow()).thenReturn(fellow);
        when(mockApplication.getGrantHolder()).thenReturn(tmp);
        when(mockApplication.getTimestamp()).thenReturn(new Date());
        when(mockApplication.getSubmissionDate()).thenReturn(new Date());
        //TODO: Populate the list...
        when(mockApplication.getRefereeReportList()).thenReturn(new ArrayList<RefereeReport>());
        when(mockApplication.getPersonList()).thenReturn(new ArrayList<Person>());
        
        //TODO: Configure all the nullable data...
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAllApplications method, of class ApplicationProgressViewerService.
     */
    @Test
    public void testGetAllApplications() throws Exception {
        
    }

    /**
     * Test of getApplicationProgress method, of class ApplicationProgressViewerService.
     */
    @Test
    public void testGetApplicationProgress() throws Exception {
        
    }
    
}
