/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Person;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.CVManagementServiceLocal;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import javax.ejb.embeddable.EJBContainer;
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
import test.softserve.MockEJBClasses.CVManagementServiceMockUnit;

/**
 *
 * @author kgothatso
 */
public class CVManagementUnitTest {
    
    public CVManagementUnitTest() {
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
     * Test of createCV method, of class CVManagementService.
     */
    @Test
    public void testCreateCV() throws Exception {
        CVManagementServiceMockUnit instance = new CVManagementServiceMockUnit();
        
        CvJpaController mockCvJpaController =  mock(CvJpaController.class);
        UserGateway mockUserGateway =  mock(UserGateway.class);
        AuditTrailService mockAuditTrailService =  mock(AuditTrailService.class);
        DBEntitiesFactory mockDBEntitiesFactory =  mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Created user cv", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        
        instance.setcVDAO(mockCvJpaController);
        instance.setuEJB(mockUserGateway);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        
        Cv mockCV = mock(Cv.class);  
        when(mockCV.getPerson()).thenReturn(new Person("u12236731"));
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        
        try
        {
            instance.createCV(mockSession, mockCV);
            
            verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockCV.getPerson());
            verify(mockCvJpaController).create(mockCV);          
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Created user cv", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE)); // TODO: Why is it wrong?
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of updateCV method, of class CVManagementService.
     */
    @Test
    public void testUpdateCV() throws Exception {
        CVManagementServiceMockUnit instance = new CVManagementServiceMockUnit();
        
        CvJpaController mockCvJpaController =  mock(CvJpaController.class);
        UserGateway mockUserGateway =  mock(UserGateway.class);
        AuditTrailService mockAuditTrailService =  mock(AuditTrailService.class);
        DBEntitiesFactory mockDBEntitiesFactory =  mock(DBEntitiesFactory.class);
        when(mockDBEntitiesFactory.buildAduitLogEntitiy("Updated user cv", new Person("u12236731"))).thenReturn(new AuditLog(Long.MAX_VALUE));
        
        instance.setcVDAO(mockCvJpaController);
        instance.setuEJB(mockUserGateway);
        instance.setaTEJB(mockAuditTrailService);
        instance.setdBEntities(mockDBEntitiesFactory);
        
        Cv mockCV = mock(Cv.class);  
        when(mockCV.getPerson()).thenReturn(new Person("u12236731"));
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        try
        {
            instance.updateCV(mockSession, mockCV);
            
            verify(mockUserGateway).authenticateUserAsOwner(mockSession, mockCV.getPerson());
            verify(mockCvJpaController).edit(mockCV);          
            verify(mockDBEntitiesFactory).buildAduitLogEntitiy("Updated user cv", new Person("u12236731"));
            verifyNoMoreInteractions(mockDBEntitiesFactory);
            verify(mockAuditTrailService).logAction(new AuditLog(Long.MAX_VALUE)); // TODO: Why is it wrong?
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
}
