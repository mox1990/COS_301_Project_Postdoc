/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package test.softserve.DBEntities;

import com.softserve.persistence.DBEntities.EmployeeInformation;
import com.softserve.persistence.DBEntities.ResearchFellowInformation;
import com.softserve.persistence.DBEntities.Faculty;
import com.softserve.persistence.DBEntities.Department;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class DepartmentTest {
    
    public DepartmentTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetDepartmentID() 
    {
        Department instance = new Department(Long.MIN_VALUE);
        instance.setDepartmentID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getDepartmentID());
    }

    @Test
    public void testSetAndGetName() 
    {
        Department instance = new Department();
        instance.setName("Computer science");
        assertEquals("Computer science",instance.getName());
    }

    @Test
    public void testSetAndGetResearchFellowInformationList() 
    {
        Department instance = new Department();        
        instance.setResearchFellowInformationList(new ArrayList<ResearchFellowInformation>());
        ResearchFellowInformation c = new ResearchFellowInformation("u12019837");
        instance.getResearchFellowInformationList().add(c);
        assertEquals(c, instance.getResearchFellowInformationList().get(0));
        assertTrue((instance.getResearchFellowInformationList().size() == 1));
    }

    @Test
    public void testSetAndGetEmployeeInformationList() 
    {
        Department instance = new Department();        
        instance.setEmployeeInformationList(new ArrayList<EmployeeInformation>());
        EmployeeInformation c = new EmployeeInformation("u12019837");
        instance.getEmployeeInformationList().add(c);
        assertEquals(c, instance.getEmployeeInformationList().get(0));
        assertTrue((instance.getEmployeeInformationList().size() == 1));
    }

    @Test
    public void testSetAndGetFaculty() 
    {
        Department instance = new Department(Long.MIN_VALUE);
        instance.setFaculty(new Faculty(Long.MIN_VALUE));
        assertEquals(new Faculty(Long.MIN_VALUE),instance.getFaculty());
    }


    @Test
    public void testEquals() 
    {
        Department instance1 = new Department(Long.MAX_VALUE);
        Department instance2 = new Department(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        Department instance = new Department(new Long(1));
        assertEquals("com.softserve.DBEntities.Department[ departmentID=" + 1 + " ]", instance.toString());
    }
    
}
