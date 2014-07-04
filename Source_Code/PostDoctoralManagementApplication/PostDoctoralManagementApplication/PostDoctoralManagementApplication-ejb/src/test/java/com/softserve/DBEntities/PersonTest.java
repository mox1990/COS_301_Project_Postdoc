/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

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
public class PersonTest {
    
    public PersonTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetSystemID() 
    {
        Person instance = new Person();
        instance.setSystemID("u12019838");
        assertEquals("u12019838",instance.getSystemID());
    }

    @Test
    public void testSetAndGetPassword() 
    {
        Person instance = new Person();
        instance.setPassword("Very very very good");
        assertEquals("Very very very good",instance.getPassword());
    }

    @Test
    public void testSetAndGetTitle() 
    {
        Person instance = new Person();
        instance.setPassword("Mr");
        assertEquals("Mr",instance.getPassword());
    }

    @Test
    public void testSetAndGetFullName() 
    {
        Person instance = new Person();
        instance.setFullName("Bobby");
        assertEquals("Bobby",instance.getFullName());
    }

    @Test
    public void testSetAndGetSurname() 
    {
        Person instance = new Person();
        instance.setSurname("Test");
        assertEquals("Test",instance.getSurname());
    }

    @Test
    public void testSetAndGetEmail() 
    {
        Person instance = new Person();
        instance.setEmail("test@fmail.com");
        assertEquals("test@fmail.com",instance.getEmail());
    }

    @Test
    public void testSetAndGetTelephoneNumber() 
    {
        Person instance = new Person();
        instance.setTelephoneNumber("0834036598");
        assertEquals("0834036598",instance.getTelephoneNumber());
    }

    @Test
    public void testSetAndGetWorkNumber() 
    {
        Person instance = new Person();
        instance.setWorkNumber("0834036598");
        assertEquals("0834036598",instance.getWorkNumber());
    }

    @Test
    public void testSetAndGetFaxNumber() 
    {
        Person instance = new Person();
        instance.setFaxNumber("0834036598");
        assertEquals("0834036598",instance.getFaxNumber());
    }

    @Test
    public void testSetAndGetCellphoneNumber() 
    {
        Person instance = new Person();
        instance.setCellphoneNumber("0834036598");
        assertEquals("0834036598",instance.getCellphoneNumber());
    }

    @Test
    public void testSetAndGetUpEmployee() 
    {
        Person instance = new Person();
        instance.setUpEmployee(true);
        assertEquals(true,instance.getUpEmployee());
    }

    @Test
    public void testSetAndGetCommitteeMeetingList() 
    {
        Person instance = new Person();
        instance.setCommitteeMeetingList(new ArrayList<CommitteeMeeting>());
        CommitteeMeeting c = new CommitteeMeeting(new Long(1));
        instance.getCommitteeMeetingList().add(c);
        assertEquals(c, instance.getCommitteeMeetingList().get(0));
        assertTrue((instance.getCommitteeMeetingList().size() == 1));
    }

    @Test
    public void testSetAndGetSecurityRoleList() 
    {
        Person instance = new Person();
        instance.setSecurityRoleList(new ArrayList<SecurityRole>());
        SecurityRole s = new SecurityRole(new Long(1));
        instance.getSecurityRoleList().add(s);
        assertEquals(s, instance.getSecurityRoleList().get(0));
        assertTrue((instance.getSecurityRoleList().size() == 1));
    }

    @Test
    public void testSetAndGetAuditLogList() 
    {
        Person instance = new Person();
        instance.setAuditLogList(new ArrayList<AuditLog>());
        AuditLog a = new AuditLog(new Long(1));
        instance.getAuditLogList().add(a);
        assertEquals(a, instance.getAuditLogList().get(0));
        assertTrue((instance.getAuditLogList().size() == 1));
    }

    @Test
    public void testSetAndGetEndorsementList() 
    {
        Person instance = new Person();
        instance.setEndorsementList(new ArrayList<Endorsement>());
        Endorsement e = new Endorsement(new Long(1));
        instance.getEndorsementList().add(e);
        assertEquals(e, instance.getEndorsementList().get(0));
        assertTrue((instance.getEndorsementList().size() == 1));
    }

    @Test
    public void testSetAndGetUpEmployeeInformation() 
    {
        Person instance = new Person();
        UpEmployeeInformation upinfo = new UpEmployeeInformation("r12019837");
        instance.setUpEmployeeInformation(upinfo);
        assertEquals(upinfo, instance.getUpEmployeeInformation());
    }

    @Test
    public void testSetAndGetRecommendationReportList() 
    {
        Person instance = new Person();
        instance.setRecommendationReportList(new ArrayList<RecommendationReport>());
        RecommendationReport r = new RecommendationReport(new Long(1));
        instance.getRecommendationReportList().add(r);
        assertEquals(r, instance.getRecommendationReportList().get(0));
        assertTrue((instance.getRecommendationReportList().size() == 1));
    }

    @Test
    public void testSetAndGetRefereeReportList() 
    {
        Person instance = new Person();
        instance.setRefereeReportList(new ArrayList<RefereeReport>());
        RefereeReport r = new RefereeReport(new Long(1));
        instance.getRefereeReportList().add(r);
        assertEquals(r, instance.getRefereeReportList().get(0));
        assertTrue((instance.getRefereeReportList().size() == 1));
    }

    @Test
    public void testSetAndGetFundingReportList() 
    {
        Person instance = new Person();
        instance.setFundingReportList(new ArrayList<FundingReport>());
        FundingReport r = new FundingReport(new Long(1));
        instance.getFundingReportList().add(r);
        assertEquals(r, instance.getFundingReportList().get(0));
        assertTrue((instance.getFundingReportList().size() == 1));
    }

    @Test
    public void testSetAndGetNotificationList() 
    {
        Person instance = new Person();
        instance.setNotificationList(new ArrayList<Notification>());
        Notification n = new Notification(new Long(1));
        instance.getNotificationList().add(n);
        assertEquals(n, instance.getNotificationList().get(0));
        assertTrue((instance.getNotificationList().size() == 1));
    }

    @Test
    public void testSetAndGetNotificationList1() 
    {
        Person instance = new Person();
        instance.setNotificationList1(new ArrayList<Notification>());
        Notification n = new Notification(new Long(1));
        instance.getNotificationList1().add(n);
        assertEquals(n, instance.getNotificationList1().get(0));
        assertTrue((instance.getNotificationList1().size() == 1));
    }

    @Test
    public void testSetAndGetCvList() 
    {
        Person instance = new Person();
        instance.setCvList(new ArrayList<Cv>());
        Cv c = new Cv(new Long(1));
        instance.getCvList().add(c);
        assertEquals(c, instance.getCvList().get(0));
        assertTrue((instance.getCvList().size() == 1));
    }

    @Test
    public void testSetAndGetApplicationList() 
    {
        Person instance = new Person();
        instance.setApplicationList(new ArrayList<Application>());
        Application a = new Application(new Long(1));
        instance.getApplicationList().add(a);
        assertEquals(a, instance.getApplicationList().get(0));
        assertTrue((instance.getApplicationList().size() == 1));
    }

    @Test
    public void testSetAndGetApplicationList1() 
    {
        Person instance = new Person();
        instance.setApplicationList1(new ArrayList<Application>());
        Application a = new Application(new Long(1));
        instance.getApplicationList1().add(a);
        assertEquals(a, instance.getApplicationList1().get(0));
        assertTrue((instance.getApplicationList1().size() == 1));
    }

    @Test
    public void testSetAndGetLocationID() 
    {
        Person instance = new Person();
        Location loc = new Location(Long.MAX_VALUE);
        instance.setLocationID(loc);
        assertEquals(loc, instance.getLocationID());
    }

    @Test
    public void testSetAndGetAddressLine1() 
    {
        Person instance = new Person();
        Address address = new Address(Long.MAX_VALUE);
        instance.setAddressLine1(address);
        assertEquals(address, instance.getAddressLine1());
    }

    @Test
    public void testSetAndGetMinuteCommentList() 
    {
        Person instance = new Person();
        instance.setMinuteCommentList(new ArrayList<MinuteComment>());
        MinuteComment mc = new MinuteComment(new Long(1));
        instance.getMinuteCommentList().add(mc);
        assertEquals(mc, instance.getMinuteCommentList().get(0));
        assertTrue((instance.getMinuteCommentList().size() == 1));
    }
    
    @Test
    public void testEquals() 
    {
        Person instance1 = new Person("u12019838");
        Person instance2 = new Person("u12019838");
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        Person instance = new Person("u12019838");
        assertEquals("com.softserve.DBEntities.Person[ systemID=" + "u12019838" + " ]", instance.toString());
    }
    
}
