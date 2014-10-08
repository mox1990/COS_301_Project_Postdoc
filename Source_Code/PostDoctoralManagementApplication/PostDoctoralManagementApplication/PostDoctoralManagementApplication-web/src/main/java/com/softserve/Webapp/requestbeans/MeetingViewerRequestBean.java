/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.persistence.DBEntities.CommitteeMeeting;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "meetingViewerRequestBean")
@RequestScoped
public class MeetingViewerRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    /**
     * Creates a new instance of MeetingViewerRequestBean
     */
    public MeetingViewerRequestBean() {
    }
    
    public CommitteeMeeting getSelectedCommitteeMeeting()
    {
        return sessionManagerBean.getObjectFromSessionStorage("MEETING", CommitteeMeeting.class);
    }
}
