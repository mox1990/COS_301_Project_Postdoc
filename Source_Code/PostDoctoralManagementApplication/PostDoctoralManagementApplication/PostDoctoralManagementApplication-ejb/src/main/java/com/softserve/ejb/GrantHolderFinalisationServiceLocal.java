/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Person;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface GrantHolderFinalisationServiceLocal {
    public void createGrantHolderCV(Session session, Cv cv) throws Exception;
    public List<Application> loadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception;
    public int countTotalPendingApplications(Session session) throws Exception;
    public void saveChangesToApplication(Session session, Application application) throws Exception;
    public List<Person> getHODsOfApplication(Session session, Application application) throws Exception;
    public void requestSpecificHODtoReview(Session session, Application application, Person hod) throws Exception;
    public void declineAppliction(Session session, Application application, String reason) throws Exception;
    public void ammendAppliction(Session session, Application application, String reason) throws Exception;
    public void finaliseApplication(Session session, Application application) throws Exception;
    
}
