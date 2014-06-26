/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.UserAccountManagementServices;

import com.softserve.DBEnties.*;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class UserAccountManagementServices implements UserAccountManagementServicesLocal {

    public void createUserAccount(HttpSession session, boolean manualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo)
    {
        
    }
    
    public void updateUserAccount(HttpSession session, Person user, Address userAddress, UpEmployeeInformation useUPInfo)
    {
        
    }
    
    public void removeUserAccount(HttpSession session, String systemID)
    {
        
    }
    
    public ArrayList<Person> viewAllUserAccounts(HttpSession session)
    {
        return null;
    }
    
}
