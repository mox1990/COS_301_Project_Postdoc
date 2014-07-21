/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Exceptions.AutomaticSystemIDGenerationException;
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
    public void createUserAccount(Session session, boolean useManualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo, Address upAddress) throws AutomaticSystemIDGenerationException, Exception;
    public void updateUserAccount(Session session, Person user, Address userAddress, UpEmployeeInformation userUPInfo) throws NonexistentEntityException, RollbackFailureException, Exception;
    public void removeUserAccount(Session session, String systemID) throws RollbackFailureException, Exception;
    public List<Person> viewAllUserAccounts(Session session) throws AuthenticationException, Exception;
    public void generateOnDemandAccount(Session session, String reason, boolean useManualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo, Address UpAddress) throws Exception;
    public void activateOnDemandAccount(Session session, Person user) throws Exception;
    public List<SecurityRole> getAllSecurityRoles();
    public void testAddresses();
}
