/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.EJB;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This EJB handles the notification services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateful
public class MeetingManagementService implements MeetingManagementServiceLocal {
    @Inject
    private CommitteeMeeting cMeeting;
    
    @PersistenceContext(unitName = "committee_meetings")
    private EntityManager cem;
    
    @PersistenceContext(unitName = "applications")
    EntityManager aem;
    
    @PersistenceContext(unitName = "applications")
    EntityManager rem;
    
    private Collection<Person> inAttendence = new ArrayList();
    
    public CommitteeMeeting startMeeting()
    {
        cem.persist(cMeeting);
        return cMeeting;
    }
    
    // TODO: need to clarify what is wat with regards to renewals and such
    public CommitteeMeeting addEndorsedApplication(/*CommitteeMeetings cMeeting*/) throws Exception
    {
        // TODO: Fix implementation 
        if(cMeeting.getMeetingID() == null)
            throw new Exception("Meeting has not been started.");
        
        List<Application> a = aem.createNamedQuery("Applications.findByType", Application.class).setParameter("type", "endorsed").getResultList();
        cMeeting.getApplicationList().addAll(a);
        
        return cMeeting;
    }
    
    public CommitteeMeeting addEndorsedRenewals(/*CommitteeMeetings cMeeting*/) throws Exception
    {
        // TODO: Fix implementation
        if(cMeeting.getMeetingID() == null)
            throw new Exception("Meeting has not been started.");
        
        List<Application> a = aem.createNamedQuery("Applications.findByType", Application.class).setParameter("type", "endorsed").getResultList();
        cMeeting.getApplicationList().addAll(a);
        
        return cMeeting;
    }
    
    // TODO: Add all the other required functionilty...
}
