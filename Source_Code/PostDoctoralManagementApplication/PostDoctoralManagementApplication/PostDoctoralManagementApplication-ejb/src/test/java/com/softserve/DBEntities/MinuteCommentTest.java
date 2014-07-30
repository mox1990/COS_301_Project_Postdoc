/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

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
public class MinuteCommentTest {
    
    public MinuteCommentTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetCommentID() 
    {
        MinuteComment instance = new MinuteComment();
        instance.setCommentID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getCommentID());
    }

    @Test
    public void testSetAndGetTimestamp() 
    {
        MinuteComment instance = new MinuteComment();
        instance.setTimestamp(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getTimestamp());
    }

    @Test
    public void testSetAndGetComment() 
    {
        MinuteComment instance = new MinuteComment();
        instance.setComment("Watch this space");
        assertEquals("Watch this space",instance.getComment());
    }

    @Test
    public void testSetAndGetMeetingID() 
    {
        MinuteComment instance = new MinuteComment();
        CommitteeMeeting meeting = new CommitteeMeeting(Long.MAX_VALUE);
        instance.setMeeting(meeting);
        assertEquals(meeting, instance.getMeeting());
    }

    @Test
    public void testSetAndGetAttendeeID() 
    {
        MinuteComment instance = new MinuteComment();
        Person person = new Person("r12019837");
        instance.setAttendee(person);
        assertEquals(person, instance.getAttendee());
    }

    @Test
    public void testEquals() 
    {
        MinuteComment instance1 = new MinuteComment(Long.MAX_VALUE);
        MinuteComment instance2 = new MinuteComment(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        MinuteComment instance = new MinuteComment(new Long(1));
        assertEquals("com.softserve.DBEntities.MinuteComment[ commentID=" + 1 + " ]", instance.toString());
    }
    
}
