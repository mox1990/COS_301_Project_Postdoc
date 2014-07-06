/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.FundingReport;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface DRISApprovalServiceLocal {
    public List<Application> loadPendingEndorsedApplications(Session session);
    public List<Application> loadPendingEligibleApplications(Session session);
    public void checkApplicationForEligiblity(Session session, Application application) throws NonexistentEntityException, RollbackFailureException, Exception;
    public void denyFunding(Session session, Application application, String reason) throws NonexistentEntityException, RollbackFailureException, Exception;
    public void approveFunding(Session session, Application application, FundingReport fundingReport, String applicantMessage, String cscMesssage, String finaceMessage) throws RollbackFailureException, Exception;
}
