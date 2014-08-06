/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.MinuteComment;
import com.softserve.DBEntities.Person;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;

/**
 * This EJB handles the notification services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface MeetingManagementServiceLocal {
    public void createMeeting(Session session, CommitteeMeeting committeeMeeting) throws AuthenticationException, Exception;
    public void updateMeeting(Session session, CommitteeMeeting committeeMeeting) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception;
    public void cancelMeeting(Session session, CommitteeMeeting committeeMeeting) throws AuthenticationException, Exception;
    public void startMeeting(Session session, CommitteeMeeting committeeMeeting) throws AuthenticationException, Exception;
    public void endMeeting(Session session, CommitteeMeeting committeeMeeting) throws AuthenticationException, Exception;
    public void addMinuteComment(Session session, MinuteComment minuteComment) throws AuthenticationException, Exception;
    public List<CommitteeMeeting> getAllMeetings(Session session) throws AuthenticationException, Exception;
    public List<CommitteeMeeting> getAllActiveMeetings(Session session) throws AuthenticationException, Exception;
    public List<CommitteeMeeting> getAllStillToBeHeldMeetings(Session session) throws AuthenticationException, Exception;
    public List<CommitteeMeeting> getAllConcludedMeetings(Session session) throws AuthenticationException, Exception;
    public List<Person> getAllPostDocCommitteeMembers(Session session) throws AuthenticationException, Exception;
    /*public CommitteeMeeting startMeeting(Session sess) throws Exception;
    public CommitteeMeeting endMeeting(Session sess) throws Exception;
    public CommitteeMeeting addEndorsedApplication(Session sess) throws Exception;
    public CommitteeMeeting addEndorsedRenewals(Session sess) throws Exception;
    public CommitteeMeeting addMemberToMeeting(Session sess) throws Exception;
    public CommitteeMeeting removeMemberFromMeeting(Session sess) throws Exception;
    public List<Person> getAttendants();
    public List<Application> getApplications();*/
    
}
