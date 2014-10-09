/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package test.softserve.DBEntities;

import com.softserve.persistence.DBEntities.Institution;
import com.softserve.persistence.DBEntities.Faculty;
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
public class InstitutionTest {
    
    public InstitutionTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetInstitutionID() 
    {
        Institution instance = new Institution(Long.MIN_VALUE);
        instance.setInstitutionID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getInstitutionID());
    }

    @Test
    public void testSetAndGetName() 
    {
        Institution instance = new Institution();
        instance.setName("University of pretoria");
        assertEquals("University of pretoria",instance.getName());
    }

    @Test
    public void testSetAndGetFacultyList() 
    {
        Institution instance = new Institution();        
        instance.setFacultyList(new ArrayList<Faculty>());
        Faculty c = new Faculty(Long.MAX_VALUE);
        instance.getFacultyList().add(c);
        assertEquals(c, instance.getFacultyList().get(0));
        assertTrue((instance.getFacultyList().size() == 1));
    }

    @Test
    public void testEquals() 
    {
        Institution instance1 = new Institution(Long.MAX_VALUE);
        Institution instance2 = new Institution(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        Institution instance = new Institution(new Long(1));
        assertEquals("com.softserve.DBEntities.Institution[ institutionID=" + 1 + " ]", instance.toString());
    }
    
}
