/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.RecommendationReport;
import com.softserve.auxiliary.Exceptions.AuthenticationException;
import com.softserve.auxiliary.requestresponseclasses.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface HODRecommendationServicesLocal {
    public List<Application> loadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception;
    public int countTotalPendingApplications(Session session) throws Exception;
    public void declineAppliction(Session session, Application application, String reason) throws Exception;
    public void ammendAppliction(Session session, Application application, String reason) throws Exception;
    public void recommendApplication(Session session, Application application, RecommendationReport recommendationReport) throws Exception;
    public List<Person> getDeansOfApplication(Session session, Application application) throws Exception;
    public void requestSpecificDeanToReview(Session session, Application application, Person dean) throws Exception;
}
