/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package test.softserve.DBEntities;

import com.softserve.persistence.DBEntities.RefereeReport;
import com.softserve.persistence.DBEntities.CommitteeMeeting;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.FundingReport;
import com.softserve.persistence.DBEntities.RecommendationReport;
import com.softserve.persistence.DBEntities.ProgressReport;
import com.softserve.persistence.DBEntities.Endorsement;
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
public class ApplicationTest {
    
    public ApplicationTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetAndGetApplicationID() 
    {         
        Application instance = new Application();
        instance.setApplicationID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE), instance.getApplicationID());
    }

    @Test
    public void testSetAndGetAndGetType() 
    {
        Application instance = new Application();
        instance.setType("renewal");
        assertEquals("renewal", instance.getType());
    }

    @Test
    public void testSetAndGetAndGetStatus() 
    {
        Application instance = new Application();
        instance.setStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED);
        assertEquals(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED, instance.getStatus());
    }

    @Test
    public void testSetAndGetAndGetTimestamp() 
    {
        Application instance = new Application();
        instance.setTimestamp(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11), instance.getTimestamp());
    }

    @Test
    public void testSetAndGetAndGetFinalisationDate() 
    {
        Application instance = new Application();
        instance.setFinalisationDate(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11), instance.getFinalisationDate());
    }
    
    @Test
    public void testSetAndGetAndGetSubmissionDate() 
    {
        Application instance = new Application();
        instance.setSubmissionDate(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11), instance.getSubmissionDate());
    }
    
    @Test
    public void testSetAndGetAndGetStartDate() 
    {
        Application instance = new Application();
        instance.setStartDate(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11), instance.getStartDate());
    }

    @Test
    public void testSetAndGetAndGetEndDate() 
    {
        Application instance = new Application();
        instance.setEndDate(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11), instance.getEndDate());
    }

    @Test
    public void testSetAndGetAndGetProjectTitle() 
    {
        Application instance = new Application();
        instance.setProjectTitle("WMD Research");
        assertEquals("WMD Research", instance.getProjectTitle());
    }

    @Test
    public void testSetAndGetInformation() 
    {
        Application instance = new Application();
        instance.setInformation("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>");
        assertEquals("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>", instance.getInformation());
    }

    @Test
    public void testSetAndGetPersonList() 
    {
        Application instance = new Application();
        instance.setPersonList(new ArrayList<Person>());
        Person person = new Person("r12019837");
        instance.getPersonList().add(person);
        assertEquals(person, instance.getPersonList().get(0));
        assertTrue((instance.getPersonList().size() == 1));
    }

    @Test
    public void testSetAndGetCommitteeMeetingList() 
    {
        Application instance = new Application();
        instance.setCommitteeMeetingList(new ArrayList<CommitteeMeeting>());
        CommitteeMeeting meeting = new CommitteeMeeting(Long.MAX_VALUE);
        instance.getCommitteeMeetingList().add(meeting);
        assertEquals(meeting, instance.getCommitteeMeetingList().get(0));
        assertTrue((instance.getCommitteeMeetingList().size() == 1));
    }

    @Test
    public void testSetAndGetRefereeReportList() 
    {
        Application instance = new Application();
        instance.setRefereeReportList(new ArrayList<RefereeReport>());
        RefereeReport report = new RefereeReport(Long.MAX_VALUE);
        instance.getRefereeReportList().add(report);
        assertEquals(report, instance.getRefereeReportList().get(0));
        assertTrue((instance.getRefereeReportList().size() == 1));
    }

    @Test
    public void testSetAndGetFellow() 
    {
        Application instance = new Application();
        Person person = new Person("r12019837");
        instance.setFellow(person);
        assertEquals(person, instance.getFellow());
    }

    @Test
    public void testSetAndGetGrantHolderID() 
    {
        Application instance = new Application();
        Person person = new Person("p12019837");
        instance.setGrantHolder(person);
        assertEquals(person, instance.getGrantHolder());
    }

    @Test
    public void testSetAndGetRecommendationReportID() 
    {
        Application instance = new Application();
        RecommendationReport report = new RecommendationReport(Long.MAX_VALUE);
        instance.setRecommendationReport(report);
        assertEquals(report, instance.getRecommendationReport());
    }

    @Test
    public void testSetAndGetEndorsementID() 
    {
        Application instance = new Application();
        Endorsement report = new Endorsement(Long.MAX_VALUE);
        instance.setEndorsement(report);
        assertEquals(report, instance.getEndorsement());
    }

    @Test
    public void testSetAndGetFundingReportID() 
    {
        Application instance = new Application();
        FundingReport report = new FundingReport(Long.MAX_VALUE);
        instance.setFundingReport(report);
        assertEquals(report, instance.getFundingReport());
    }

    @Test
    public void testSetAndGetProgressReportList() 
    {
        Application instance = new Application();
        ProgressReport report = new ProgressReport(Long.MAX_VALUE);
        instance.setProgressReportList(new ArrayList<ProgressReport> ());
        instance.getProgressReportList().add(report);
        assertEquals(report, instance.getProgressReportList().get(0));
        assertTrue((instance.getProgressReportList().size() == 1));
    }

    @Test
    public void testEquals() 
    {
        Application instance1 = new Application(new Long(1));
        Application instance2 = new Application(new Long(1));
        
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        Application instance = new Application(new Long(1));
        assertEquals("com.softserve.DBEntities.Application[ applicationID=" + 1 + " ]", instance.toString());
    }
    
}
