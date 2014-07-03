/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.CommitteeMeetingJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.MinuteComment;
import com.softserve.DBEntities.Person;
import com.softserve.constants.PersistenceConstants;
import com.softserve.system.Session;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * This EJB handles the notification services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateful
public class MeetingManagementService implements MeetingManagementServiceLocal {
    //TODO: @Inject
    private CommitteeMeeting cMeeting = new CommitteeMeeting();
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    /**
     *This function creates an instance of the PersonJpaController. 
     * Note this function's secondary goal is to simplify the subclass mocking 
     * of the UserAccountManagementServices in the unit testing 
     * @return An instance of PersonJpaController
     */
    protected CommitteeMeetingJpaController getCommitteeMeetingDAO()
    {
        return new CommitteeMeetingJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    public CommitteeMeeting startMeeting(Session sess) throws Exception
    {
        if(cMeeting.getMeetingID() == null)
        {
            cMeeting.setStartDate(new Timestamp(new Date().getTime()));
            
            cMeeting.setApplicationList(new ArrayList<Application>());
            cMeeting.setMinuteCommentList(new ArrayList<MinuteComment>());
            cMeeting.setPersonList(new ArrayList<Person>());
            
            getCommitteeMeetingDAO().create(cMeeting);
        }
        return cMeeting;
    }
    
    public CommitteeMeeting endMeeting(Session sess) throws Exception
    {
        cMeeting.setEndDate(new Timestamp(new Date().getTime()));
        
        getCommitteeMeetingDAO().edit(cMeeting);
        return cMeeting;
    }
    
    // TODO: need to clarify what is wat with regards to renewals and such
    public CommitteeMeeting addEndorsedApplication(/*CommitteeMeetings cMeeting*/) throws Exception
    {
        // TODO: Fix implementation 
        if(cMeeting.getMeetingID() == null)
            throw new Exception("Meeting has not been started.");
        
        //List<Application> a = em.createNamedQuery("Applications.findByType", Application.class).setParameter("type", PersistenceConstants.APPLICATION_TYPE_NEW).getResultList();
        //cMeeting.getApplicationList().addAll(a);
        
        getCommitteeMeetingDAO().edit(cMeeting);
        
        return cMeeting;
    }
    
    public CommitteeMeeting addEndorsedRenewals(/*CommitteeMeetings cMeeting*/) throws Exception
    {
        // TODO: Fix implementation
        if(cMeeting.getMeetingID() == null)
            throw new Exception("Meeting has not been started.");
        
        //List<Application> a = em.createNamedQuery("Applications.findByType", Application.class).setParameter("type", PersistenceConstants.APPLICATION_TYPE_RENEWAL).getResultList();
        //cMeeting.getApplicationList().addAll(a);
        
        return cMeeting;
    }
    
    public void addMemberToMeeting(/*CommitteeMeetings cMeeting*/Person p) throws Exception
    {
        // TODO: Fix implementation 
        if(cMeeting.getMeetingID() == null)
            throw new Exception("Meeting has not been started.");
        
        // TODO: Report it into audit trail.
        
        if(p.getSecurityRoleList().contains(PersistenceConstants.SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER)
                && !cMeeting.getPersonList().contains(p))
        {
            cMeeting.getPersonList().add(p);
        }
    }
    
    public void notifyCommitteeMembers(String message, Session s)
    {
        // TODO: Build the notification...
    }
}
