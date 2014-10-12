/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Issue;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.ejb.applicationservices.ApplicationRenewalServiceLocal;
import com.softserve.ejb.applicationservices.MeetingManagementServiceLocal;
import com.softserve.ejb.applicationservices.ProgressReportManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
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
import static org.mockito.Mockito.*;
import test.softserve.MockEJBClasses.NotifierServicesMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NotifierUnitTest {
    private NotifierServicesMockUnit instance;
    
    private DBEntitiesFactory mockDBEntities;
    private NotificationServiceLocal mockNotificationServiceLocal;
    private ProgressReportManagementServiceLocal mockProgressReportManagementServiceLocal;
    private ApplicationRenewalServiceLocal mockApplicationRenewalServiceLocal;
    private MeetingManagementServiceLocal mockMeetingManagementServiceLocal;
    private UserAccountManagementServiceLocal mockUserAccountManagementServiceLocal;
    private ApplicationServicesUtil mockApplicationServicesUtil;
    private DAOFactory mockDAOFactory;
    private EntityManager mockEntityManager;
    
    public NotifierUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new NotifierServicesMockUnit();
        
        mockDBEntities = mock(DBEntitiesFactory.class);
        mockNotificationServiceLocal = mock(NotificationServiceLocal.class);
        mockProgressReportManagementServiceLocal = mock(ProgressReportManagementServiceLocal.class);
        mockApplicationRenewalServiceLocal = mock(ApplicationRenewalServiceLocal.class);
        mockMeetingManagementServiceLocal = mock(MeetingManagementServiceLocal.class);
        mockUserAccountManagementServiceLocal = mock(UserAccountManagementServiceLocal.class);
        mockApplicationServicesUtil = mock(ApplicationServicesUtil.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockEntityManager = mock(EntityManager.class);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadAllPendingIssuesForSession method, of class NotifierServices.
     */
    @Test
    public void testLoadAllPendingIssuesForSession() throws Exception {
        
    }

    /**
     * Test of loadAllPendingIssuesForUser method, of class NotifierServices.
     */
    @Test
    public void testLoadAllPendingIssuesForUser() throws Exception {
        
    }

    /**
     * Test of sendDailyReminders method, of class NotifierServices.
     */
    @Test
    public void testSendDailyReminders() throws Exception {
        
    }
    
}
