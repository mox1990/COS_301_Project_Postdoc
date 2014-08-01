/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.SecurityRoleJpaController;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.Session;
import java.util.List;
import test.softserve.MockEJBClasses.UserGatewayMockUnit;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
public class UserGatewayUnitTests {
    
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
    
    @Test
    public void testAuthenticateUser(Session session, List<SecurityRole> allowedRoles) 
    {
        
        UserGatewayMockUnit instance = new UserGatewayMockUnit();
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        
        try
        {
            instance.authenticateUser(session, allowedRoles);
            verify
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
        
    }
    
    @Test
    public void testAuthenticateUserAsOwner(Session session, Person person)
    {
        UserGatewayMockUnit instance = new UserGatewayMockUnit();
        HttpSession httpsession = new HttpSession();
        Session mockSession = new Session(httpsession, person);
        
        try
        {
            instance.authenticateUserAsOwner(session, person);
            verify(mockSession).doesHttpSessionUsernameMatchUserUsername();
            verify(mockSession).doesHttpSessionPasswordMatchUserPassword();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
    @Test
    public void testLogin(Session httpSession) 
    {
        UserGatewayMockUnit instance = new UserGatewayMockUnit();
        HttpSession httpsession = new HttpSession();
        Person person = new Person();
        Session mockSession = new Session(httpsession, person);
        
        try
        {
            instance.login(httpSession);
            verify(mockSession).getHttpSessionUsername();
            verify(mockSession).getHttpSessionPassword();
            verify(mockSession).getLoggedInStatus();
            verify(mockSession).doesHttpSessionUsernameMatchUserUsername();
            verify(mockSession).doesHttpSessionUsernameMatchUserEmail();
            verify(mockSession).doesHttpSessionPasswordMatchUserPassword();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    public void testLogout(Session session) throws Exception
    {
        UserGatewayMockUnit instance = new UserGatewayMockUnit();
        HttpSession httpsession = new HttpSession();
        Person person = new Person();
        Session mockSession = new Session(httpsession, person);
        
        try
        {
            instance.logout(session);
            verify(session).getLoggedInStatus();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     *
     * @param httpSession
     */
    public void testGetSessionFromHttpSession(HttpSession httpSession)
    {
        UserGatewayMockUnit instance = new UserGatewayMockUnit();
        Session mockSession = new Session(httpsession, person);
        try
        {
            instance.getSessionFromHttpSession(httpSession);
            
            verify(mockSession).getHttpSession();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
}
