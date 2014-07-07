/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.Application;
import com.softserve.system.ApplicationStageStatus;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface ApplicationProgressViewerServiceLocal {
    public List<Application> getAllApplicationsWithFellow(Session session);
    public List<Application> getAllApplicationsWithGrantHolder(Session session);
    public List<ApplicationStageStatus> getApplicationProgress(Session session, Application application);
}
