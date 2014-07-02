/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package test.softserve.BusinessLogicTests;

import com.softserve.DBDAO.AddressJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.UpEmployeeInformationJpaController;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.system.Session;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import test.softserve.MockEJBClasses.UserAccountManagementServicesMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@RunWith(MockitoJUnitRunner.class)
public class UserAccountManagmentUnitTest {
      
    
    
    public UserAccountManagmentUnitTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void userAccountCreate_Manual_UnitTest() 
    {        
        UserAccountManagementServicesMockUnit instance = new UserAccountManagementServicesMockUnit();
        PersonJpaController mockPersonJpaController = mock(PersonJpaController.class);
        AddressJpaController mockAddressJpaController = mock(AddressJpaController.class);
        UpEmployeeInformationJpaController mockUpEmployeeInformationJpaController = mock(UpEmployeeInformationJpaController.class);
        
        instance.setaDAO(mockAddressJpaController);
        instance.setpDAO(mockPersonJpaController);
        instance.setuDAO(mockUpEmployeeInformationJpaController);
        
        Person mockPerson = mock(Person.class);
        Address mockAddress = mock(Address.class);
        UpEmployeeInformation mockUpInfo = mock(UpEmployeeInformation.class);        
        Session mockSession = mock(Session.class);
        
        try
        {
            instance.createUserAccount(mockSession, true, mockPerson, mockAddress, mockUpInfo);
            verify(mockAddressJpaController).create(mockAddress);
            verify(mockPerson).setAddressLine1(mockAddress);
            verify(mockPersonJpaController).create(mockPerson);
            verify(mockUpEmployeeInformationJpaController).create(mockUpInfo);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
        
        
    }
}
