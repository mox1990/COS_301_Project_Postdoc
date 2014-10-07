/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.ResearchFellowInformation;
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
public interface DRISApprovalServiceLocal {
    public List<Application> loadPendingEndorsedApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception;
    public int countTotalPendingEndorsedApplications(Session session) throws Exception;
    public List<Application> loadPendingEligibleApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception;
    public int countTotalPendingEligibleApplications(Session session) throws Exception;
    public List<Application> loadFundedApplications(Session session,int StartIndex, int maxNumberOfRecords) throws Exception;
    public boolean checkApplicationForEligiblity(Session session, Application application) throws Exception;
    public void denyFunding(Session session, Application application, String reason) throws Exception;
    public void approveFunding(Session session, Application application, ResearchFellowInformation researchFellowInformation, FundingReport fundingReport, String applicantMessage, Notification cscMesssage, Notification finaceMessage) throws Exception;
    public void setApplicationEligibleStatus(Session session, Application application, boolean isElgible) throws Exception;
    public void updateFundingInformation(Session session, Application application) throws Exception;
    public void terminateApplication(Session session, Application application) throws Exception;
    public void abandonApplication(Session session, Application application) throws Exception;
}
