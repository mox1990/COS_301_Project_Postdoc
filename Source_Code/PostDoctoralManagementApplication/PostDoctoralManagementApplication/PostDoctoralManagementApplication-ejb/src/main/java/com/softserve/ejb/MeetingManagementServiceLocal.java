/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.MinuteComment;
import com.softserve.DBEntities.Person;
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
    public CommitteeMeeting startMeeting(Session sess) throws Exception;
    public CommitteeMeeting endMeeting(Session sess) throws Exception;
    public CommitteeMeeting addEndorsedApplication(Session sess) throws Exception;
    public CommitteeMeeting addEndorsedRenewals(Session sess) throws Exception;
    public CommitteeMeeting addMemberToMeeting(Session sess) throws Exception;
    public CommitteeMeeting removeMemberFromMeeting(Session sess) throws Exception;
    public List<Person> getAttendants();
    public List<Application> getApplications();
    public MinuteComment addMinuteComment(Session sess, String comment) throws Exception;
}
