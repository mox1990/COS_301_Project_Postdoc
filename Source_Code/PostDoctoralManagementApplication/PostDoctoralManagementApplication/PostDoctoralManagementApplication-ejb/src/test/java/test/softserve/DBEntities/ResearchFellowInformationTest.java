/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package test.softserve.DBEntities;

import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.ResearchFellowInformation;
import com.softserve.persistence.DBEntities.Department;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ResearchFellowInformationTest {
    
    public ResearchFellowInformationTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetSystemAssignedID() 
    {
        ResearchFellowInformation instance = new ResearchFellowInformation();
        instance.setSystemAssignedID("u12019838");
        assertEquals("u12019838",instance.getSystemAssignedID());
    }

    @Test
    public void testSetAndGetInstitutionAssignedID() 
    {
        ResearchFellowInformation instance = new ResearchFellowInformation();
        instance.setInstitutionAssignedID("u12019838");
        assertEquals("u12019838",instance.getInstitutionAssignedID());
    }

    @Test
    public void testSetAndGetInstitutionAssignedEmail() 
    {
        ResearchFellowInformation instance = new ResearchFellowInformation();
        instance.setInstitutionAssignedEmail("test44@gmail.com");
        assertEquals("test44@gmail.com",instance.getInstitutionAssignedEmail());
    }

    @Test
    public void testSetAndGetPerson() 
    {
        ResearchFellowInformation instance = new ResearchFellowInformation();
        Person person = new Person("r12019837");
        instance.setPerson(person);
        assertEquals(person, instance.getPerson());
    }

    @Test
    public void testSetAndGetDepartment() 
    {
        ResearchFellowInformation instance = new ResearchFellowInformation();
        Department department = new Department(Long.MAX_VALUE);
        instance.setDepartment(department);
        assertEquals(department, instance.getDepartment());
    }

    @Test
    public void testEquals() 
    {
        ResearchFellowInformation instance1 = new ResearchFellowInformation("u12019838");
        ResearchFellowInformation instance2 = new ResearchFellowInformation("u12019838");
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        ResearchFellowInformation instance = new ResearchFellowInformation("u12019838");
        assertEquals("com.softserve.DBEntities.ResearchFellowInformation[ systemAssignedID=" + "u12019838" + " ]", instance.toString());
    }
    
}
