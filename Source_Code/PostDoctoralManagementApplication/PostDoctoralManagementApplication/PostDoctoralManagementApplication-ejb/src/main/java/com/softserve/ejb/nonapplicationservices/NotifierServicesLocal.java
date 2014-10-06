/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.nonapplicationservices;

import com.softserve.DBEntities.Person;
import com.softserve.auxillary.Issue;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface NotifierServicesLocal {
    public List<Issue> loadAllPendingIssuesForSession(Session session) throws Exception;
    public List<Issue> loadAllPendingIssuesForUser(Session session, Person person) throws Exception;
    
    
}
