/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.persistence.DBDAO.SecurityRoleJpaController;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.auxiliary.Exceptions.AuthenticationException;
import com.softserve.auxiliary.requestresponseclasses.Session;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import test.softserve.MockEJBClasses.UserGatewayMockUnit;
import javax.servlet.http.HttpSession;
import org.junit.*;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

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
    public void testAuthenticateUser() 
    {
        
        UserGatewayMockUnit instance = new UserGatewayMockUnit();
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        Person mockPerson = mock(Person.class);
        roles = (ArrayList<SecurityRole>) mockPerson.getSecurityRoleList();
        
        try
        {
            instance.authenticateUser(mockSession, roles);
            verify(mockSession).doesHttpSessionUsernameMatchUserUsername();
            verify(mockSession).doesHttpSessionPasswordMatchUserPassword();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
        
    }
    
    @Test
    public void testAuthenticateUserAsOwner()
    {
        UserGatewayMockUnit instance = new UserGatewayMockUnit();
        HttpSession mockHttpSession = mock(HttpSession.class);
        Person mockPerson = mock(Person.class);
        Session mockSession = mock(Session.class);
        
        try
        {
            instance.authenticateUserAsOwner(mockSession, mockPerson);
            verify(mockSession).doesHttpSessionUsernameMatchUserUsername();
            verify(mockSession).doesHttpSessionPasswordMatchUserPassword();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }
    
    @Test
    public void testLogin() 
    {
        UserGatewayMockUnit instance = new UserGatewayMockUnit();
        HttpSession mockHttpSession = mock(HttpSession.class);
        Person person = new Person();
        Session mockSession = mock(Session.class);
        
        try
        {
            instance.login(mockSession);
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
            //fail"An exception occured");
        }
    }
    public void testLogout() throws Exception
    {
        UserGatewayMockUnit instance = new UserGatewayMockUnit();
         HttpSession mockHttpSession = mock(HttpSession.class);
        Person person = new Person();
        Session mockSession = new Session(mockHttpSession, person);
        
        try
        {
            instance.logout(mockSession);
            verify(mockSession).getLoggedInStatus();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }

    /**
     *
     * @param httpSession
     */
    public void testGetSessionFromHttpSession() throws NoSuchAlgorithmException
    {
        UserGatewayMockUnit instance = new UserGatewayMockUnit();
        HttpSession mockHttpSession = mock(HttpSession.class);
        Person mockPerson = mock(Person.class);
        Session mockSession = new Session(mockHttpSession, mockPerson);
        try
        {
            instance.getSessionFromHttpSession(mockHttpSession);
            
            verify(mockSession).getHttpSession();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //fail"An exception occured");
        }
    }
    
}
