/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.UserAccountManagementServices;

import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.UpEmployeeInformation;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class UserAccountManagementServices implements UserAccountManagementServicesLocal {
    
    @Resource
    private UserTransaction utx;
    
    private EntityManagerFactory getEntityManagerFactory()
    {
        return Persistence.createEntityManagerFactory(com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME);        
    }       
    
    
    private String generateSystemID(char prefix)
    {
        String newID = "";
        
        PersonJpaController personJpaController = new PersonJpaController(utx, getEntityManagerFactory());
        
        GregorianCalendar cal = new GregorianCalendar();
        
        int curYear = cal.get(Calendar.YEAR);        
        
        int lastestIDValue = personJpaController.getMaxSystemIDForYear(curYear, prefix);
        
        lastestIDValue++;
        
        newID += prefix;        
        
        if(curYear > 9)
        {
            String sYear = Integer.toString(curYear);
            newID += sYear.charAt(sYear.length() - 2);
            newID += sYear.charAt(sYear.length() - 1);
        }
        else
        {
            newID += "0";
            newID += Integer.toString(curYear);
        }
        
        newID += Integer.toString(lastestIDValue);
        
        cal = null;
        personJpaController = null;
        
        return newID;
    }
    
    public void createUserAccount(HttpSession session, boolean manualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo)
    {
        //AuthenticateUser(session);
        
        if(!manualSystemIDSpecification)
        {
            //user.setSystemID(generateSystemID());
        }
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
