/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.persistence.DBEntities.Application;
import com.softserve.auxillary.Exceptions.AuthenticationException;
import com.softserve.auxillary.requestresponseclasses.ApplicationStageStatus;
import com.softserve.auxillary.requestresponseclasses.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface ApplicationProgressViewerServiceLocal {
    public List<Application> getAllApplications(Session session) throws AuthenticationException, Exception;
    public List<ApplicationStageStatus> getApplicationProgress(Session session, Application application) throws AuthenticationException, Exception;
}
