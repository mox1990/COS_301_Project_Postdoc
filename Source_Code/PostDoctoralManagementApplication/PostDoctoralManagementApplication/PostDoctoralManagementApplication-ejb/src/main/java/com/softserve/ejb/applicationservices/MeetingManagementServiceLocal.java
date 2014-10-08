/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.CommitteeMeeting;
import com.softserve.persistence.DBEntities.MinuteComment;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.auxillary.Exceptions.AuthenticationException;
import com.softserve.auxillary.requestresponseclasses.Session;
import java.util.List;
import javax.ejb.Local;

/**
 * This EJB handles the notification services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface MeetingManagementServiceLocal {
    public void createMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception;
    public void updateMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception;
    public void cancelMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception;
    public void startMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception;
    public void endMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception;
    public void addMinuteComment(Session session, MinuteComment minuteComment) throws Exception;
    public List<CommitteeMeeting> getAllMeetings(Session session) throws Exception;
    public List<CommitteeMeeting> getAllActiveMeetings(Session session) throws Exception;
    public List<CommitteeMeeting> getAllStillToBeHeldMeetings(Session session) throws Exception;
    public List<CommitteeMeeting> getAllConcludedMeetings(Session session) throws Exception;
    public List<Person> getAllPostDocCommitteeMembers(Session session) throws Exception;
    public List<CommitteeMeeting> getAllActiveMeetingsForWhichUserIsToAttend(Session session) throws Exception;
    
}
