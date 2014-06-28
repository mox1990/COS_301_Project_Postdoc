/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.UserAccountManagementServices;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.Exceptions.AutomaticSystemIDGenerationException;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface UserAccountManagementServicesLocal {
    public void createUserAccount(HttpSession session, boolean manualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo) throws AutomaticSystemIDGenerationException, Exception;
    public void updateUserAccount(HttpSession session, Person user, Address userAddress, UpEmployeeInformation userUPInfo) throws NonexistentEntityException, RollbackFailureException, Exception;
    public void removeUserAccount(HttpSession session, String systemID) throws RollbackFailureException, Exception;
    public List<Person> viewAllUserAccounts(HttpSession session);
}
