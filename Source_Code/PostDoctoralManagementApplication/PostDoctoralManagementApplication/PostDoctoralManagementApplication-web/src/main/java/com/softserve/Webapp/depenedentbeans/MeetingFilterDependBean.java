/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.DBEntities.CommitteeMeeting;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "meetingFilterDependBean")
@Dependent
public class MeetingFilterDependBean implements Serializable {
    
    private String term;
    private List<CommitteeMeeting> originalList;
    private List<CommitteeMeeting> filteredList;
    
    /**
     * Creates a new instance of MeetingFilterDependBean
     */
    public MeetingFilterDependBean() {
    }
    
    
    public void init(List<CommitteeMeeting> committeeMeetings)
    {
        filteredList = new ArrayList<CommitteeMeeting>();
        if(committeeMeetings == null)
        {
            originalList = new ArrayList<CommitteeMeeting>();
        }
        else
        {
            originalList = committeeMeetings;
            filteredList.addAll(committeeMeetings);
        }
        
        
    }

    public List<CommitteeMeeting> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<CommitteeMeeting> filteredList) {
        this.filteredList = filteredList;
    }

    public List<CommitteeMeeting> getOriginalList() {
        return originalList;
    }

    public void setOriginalList(List<CommitteeMeeting> originalList) {
        this.originalList = originalList;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }
                    
    public void filterListBy()
    {
        filteredList.clear();
        System.out.println("=======================" + term);
        for(CommitteeMeeting committeeMeeting : originalList)
        {
            if(committeeMeeting.getMeetingID().toString().contains(term) || committeeMeeting.getName().contains(term) || committeeMeeting.getVenue().contains(term))
            {
                filteredList.add(committeeMeeting);
            }            
        }
    }
}
