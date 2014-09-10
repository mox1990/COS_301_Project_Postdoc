/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.ejb;

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
public interface UserAccountManagementServiceLocal {
    public void createUserAccount(Session session, boolean useManualSystemIDSpecification, Person user) throws Exception;
    public void updateUserAccount(Session session, Person user) throws Exception;
    public void removeUserAccount(Session session, String systemID) throws Exception;
    public List<Person> viewAllUserAccounts(Session session) throws Exception;
    public void generateOnDemandAccount(Session session, String reason, boolean useManualSystemIDSpecification, Person user) throws Exception;
    public void approveOnDemandAccount(Session session, Person account) throws Exception;
    public void declineOnDemandAccount(Session session, Person account) throws Exception;
    public void activateOnDemandAccount(Session session, Person user) throws Exception;
    public List<Person> loadAllPendingOnDemandAccounts(Session session) throws Exception;
    public Person getUserBySystemIDOrEmail(String intput);
    public Person getUserBySystemID(String systemID);
}
