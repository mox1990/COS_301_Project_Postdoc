/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface ProgressReportManagementServiceLocal {
    public void createProgressReport(Session session, Application application, ProgressReport progressReport) throws AuthenticationException, Exception;
    public void updateProgressReport(Session session, ProgressReport progressReport) throws AuthenticationException, Exception;
    public List<Application> allApplicationsWithPendingReportsForUser(Session session) throws Exception;
}
