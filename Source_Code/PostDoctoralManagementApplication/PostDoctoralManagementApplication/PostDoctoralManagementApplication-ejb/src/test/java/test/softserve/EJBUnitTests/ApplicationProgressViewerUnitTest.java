/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.ejb.ApplicationProgressViewerServiceLocal;
import com.softserve.ejb.UserGateway;
import com.softserve.system.ApplicationStageStatus;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import test.softserve.MockEJBClasses.ApplicationProgressViewerServiceMockUnit;

/**
 *
 * @author kgothatso
 */
public class ApplicationProgressViewerUnitTest {
    
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAllApplicationsWithUserAsFellow method, of class ApplicationProgressViewerService.
     */
    /*
    @Test
    public void testGetAllApplicationsWithFellow() throws Exception {
        ApplicationProgressViewerServiceMockUnit instance = new ApplicationProgressViewerServiceMockUnit();
        
        //Setup dependices mocks
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        
        
        //Load dependices mocks' into instance
        instance.setaDAO(mockApplicationJpaController);
        instance.setuEJB(mockUserGateway);
        
        //Setup parameter mocks
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
        
        try
        {
            //Execute function
            instance.getAllApplicationsWithUserAsFellow(mockSession);
            
            //Verify correct function behaviour
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationJpaController).findAllApplicationsWhosFellowIs(mockSession.getUser());           
            
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }*/

    /**
     * Test of getAllApplicationsWithUserAsGrantHolder method, of class ApplicationProgressViewerService.
     */
    /*
    @Test
    public void testGetAllApplicationsWithGrantHolder() throws Exception {
        ApplicationProgressViewerServiceMockUnit instance = new ApplicationProgressViewerServiceMockUnit();
        
        //Setup dependices mocks
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        
        
        //Load dependices mocks' into instance
        instance.setaDAO(mockApplicationJpaController);
        instance.setuEJB(mockUserGateway);
        
        //Setup parameter mocks
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
        
        try
        {
            //Execute function
            instance.getAllApplicationsWithUserAsGrantHolder(mockSession);
            
            //Verify correct function behaviour
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            verify(mockUserGateway).authenticateUser(mockSession, roles);
            verify(mockApplicationJpaController).findAllApplicationsWhosGrantHolderIs(mockSession.getUser());           
            
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }*/

    /**
     * Test of getApplicationProgress method, of class ApplicationProgressViewerService.
     */
    @Test
    public void testGetApplicationProgress() throws Exception {
        ApplicationProgressViewerServiceMockUnit instance = new ApplicationProgressViewerServiceMockUnit();
        
        //Setup dependices mocks
        ApplicationJpaController mockApplicationJpaController = mock(ApplicationJpaController.class);
        UserGateway mockUserGateway = mock(UserGateway.class);
        
        
        //Load dependices mocks' into instance
        instance.setaDAO(mockApplicationJpaController);
        instance.setuEJB(mockUserGateway);
        
        //Setup parameter mocks
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12019837"));
        Application mockApplication = mock(Application.class);
        when(mockApplication.getFellow()).thenReturn(new Person("u12019837"));
        
        List<RefereeReport> rrList = new ArrayList<>();
        RefereeReport rr = mock(RefereeReport.class);
        when(rr.getReferee()).thenReturn(new Person("u12019837"));
        //rrList.add(rr);
        when(mockApplication.getRefereeReportList()).thenReturn(rrList);
        when(mockApplication.getRecommendationReport()).thenReturn(null);
        when(mockApplication.getEndorsement()).thenReturn(null);
        when(mockApplication.getStatus()).thenReturn(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN);
        
        try
        {
            //Execute function
            instance.getApplicationProgress(mockSession, mockApplication);
            
            //Verify correct function behaviour
            
            //verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockApplication.getFellow());
            
            // TODO: ADD more of the logic...
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured: " + ex.getCause().toString() + ")$(%)");
        }
    }
    
}
