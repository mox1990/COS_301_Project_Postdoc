/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.nonapplicationservices;

import com.softserve.persistence.DBEntities.Announcement;
import com.softserve.auxillary.requestresponseclasses.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface AnnouncementManagementServiceLocal {
    
    public List<Announcement> loadAllActiveAnnouncements() throws Exception;
    public List<Announcement> loadAllPendingAnnouncements() throws Exception;    
    public void createAnnouncement(Session session, Announcement announcement) throws Exception;
    public void updateAnnouncement(Session session, Announcement announcement) throws Exception;
    public void removeAnnouncement(Session session, Announcement announcement) throws Exception;
}
