/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.DBEntities.Announcement;
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
@Named(value = "announcementFilterDependBean")
@Dependent
public class AnnouncementFilterDependBean implements Serializable {
    
    private String term;
    private List<Announcement> originalList;
    private List<Announcement> filteredList;
    
    /**
     * Creates a new instance of AnnouncementFilterDependBean
     */
    public AnnouncementFilterDependBean() {
    }
    
    public void init(List<Announcement> announcements)
    {
        filteredList = new ArrayList<Announcement>();        
        if(announcements == null)
        {
            originalList = new ArrayList<Announcement>();
        }
        else
        {
            originalList = announcements;
            filteredList.addAll(announcements);
        }
        
    }

    public List<Announcement> getOriginalList() {
        return originalList;
    }

    public void setOriginalList(List<Announcement> originalList) {
        this.originalList = originalList;
    }
    
    public List<Announcement> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<Announcement> filteredList) {
        this.filteredList = filteredList;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
        
    public void filterListBy()
    {
        filteredList.clear();
        System.out.println("=======================" + term);
        for(Announcement announcement : originalList)
        {
            if(announcement.getAnnouncementID().toString().contains(term) || announcement.getHeadline().contains(term) || announcement.getMessage().contains(term))
            {
                filteredList.add(announcement);
            }            
        }
    }
}
