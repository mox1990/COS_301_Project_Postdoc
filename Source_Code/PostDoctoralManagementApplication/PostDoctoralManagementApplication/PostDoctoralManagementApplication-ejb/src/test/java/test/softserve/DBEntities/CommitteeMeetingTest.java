/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package test.softserve.DBEntities;

import com.softserve.persistence.DBEntities.MinuteComment;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.CommitteeMeeting;
import com.softserve.persistence.DBEntities.Application;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class CommitteeMeetingTest {
    
    public CommitteeMeetingTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetMeetingID() 
    {
        CommitteeMeeting instance = new CommitteeMeeting();
        instance.setMeetingID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE), instance.getMeetingID());
    }

    @Test
    public void testSetAndGetStartDate() 
    {
        CommitteeMeeting instance = new CommitteeMeeting();
        instance.setStartDate(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11), instance.getStartDate());
    }

    @Test
    public void testSetAndGetEndDate() 
    {
        CommitteeMeeting instance = new CommitteeMeeting();
        instance.setEndDate(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11), instance.getEndDate());
    }

    @Test
    public void testSetAndGetPersonList() 
    {
        CommitteeMeeting instance = new CommitteeMeeting();
        instance.setPersonList(new ArrayList<Person>());
        Person person = new Person("r12019837");
        instance.getPersonList().add(person);
        assertEquals(person, instance.getPersonList().get(0));
        assertTrue((instance.getPersonList().size() == 1));
    }

    @Test
    public void testSetAndGetApplicationList() 
    {
        CommitteeMeeting instance = new CommitteeMeeting();
        instance.setApplicationList(new ArrayList<Application>());
        Application application = new Application(Long.MAX_VALUE);
        instance.getApplicationList().add(application);
        assertEquals(application, instance.getApplicationList().get(0));
        assertTrue((instance.getApplicationList().size() == 1));
    }

    @Test
    public void testSetAndGetMinuteCommentList() 
    {
        CommitteeMeeting instance = new CommitteeMeeting();
        instance.setMinuteCommentList(new ArrayList<MinuteComment>());
        MinuteComment comment = new MinuteComment(Long.MAX_VALUE);
        instance.getMinuteCommentList().add(comment);
        assertEquals(comment, instance.getMinuteCommentList().get(0));
        assertTrue((instance.getMinuteCommentList().size() == 1));
    }

    @Test
    public void testEquals() 
    {
        CommitteeMeeting instance1 = new CommitteeMeeting(new Long(1));
        CommitteeMeeting instance2 = new CommitteeMeeting(new Long(1));
        
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        CommitteeMeeting instance = new CommitteeMeeting(new Long(1));
        assertEquals("com.softserve.DBEntities.CommitteeMeeting[ meetingID=" + 1 + " ]", instance.toString());
    }
    
}
