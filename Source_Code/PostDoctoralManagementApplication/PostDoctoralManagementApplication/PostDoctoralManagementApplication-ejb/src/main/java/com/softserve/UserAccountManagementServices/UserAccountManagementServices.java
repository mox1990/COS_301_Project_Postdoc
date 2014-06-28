/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.UserAccountManagementServices;

import com.softserve.DBDAO.*;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.Exceptions.AutomaticSystemIDGenerationException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserAccountManagementServices implements UserAccountManagementServicesLocal {     
    
    private UserTransaction getUserTransaction()
    {
        try 
        {
            return (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        } 
        catch (NamingException ex) 
        {
            Logger.getLogger(UserAccountManagementServices.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private EntityManagerFactory getEntityManagerFactory()
    {
        return Persistence.createEntityManagerFactory(com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME);        
    } 
    
    private String generateSystemID(char prefix)
    {
        String newID = "";
        
        PersonJpaController personJpaController = new PersonJpaController(getUserTransaction(), getEntityManagerFactory());
        
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
        while(newID.length() + Integer.toString(lastestIDValue).length() < 9)
        {
            newID += "0";
        }
        newID += Integer.toString(lastestIDValue);
        
        cal = null;
        personJpaController = null;
        
        return newID;
    }
    
    public void createUserAccount(HttpSession session, boolean manualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo) throws AutomaticSystemIDGenerationException, Exception
    {
        //AuthenticateUser(session);
        
        if(!manualSystemIDSpecification)
        {
            
            if(PersonJpaController.doesPersonHaveSecurityRole(user, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR))
            {
                user.setSystemID(generateSystemID('a'));
            }
            else if(PersonJpaController.doesPersonHaveSecurityRole(user, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW))
            {
                user.setSystemID(generateSystemID('f'));
            }
            else
            {
                throw new AutomaticSystemIDGenerationException("An error occured while generating a systemID for the person " + user.getFullName() + ".");
            }
            
            userUPInfo.setEmployeeID(user.getSystemID());
        }
        
        
        if(userAddress.getAddressID() == null)
        {
            System.out.println("Null 1");
        }
        else
        {
            System.out.println(userAddress.getAddressID());
        }
        
        AddressJpaController addressJpaController = new AddressJpaController(getUserTransaction(), getEntityManagerFactory());
        addressJpaController.create(userAddress);
 
        //user.setAddressLine1(userAddress);        
        
        PersonJpaController personJpaController = new PersonJpaController(getUserTransaction(), getEntityManagerFactory());
        personJpaController.create(user);
        
        if(userAddress.getAddressID() == null)
        {
            System.out.println("Null 2");
        }
        else
        {
            System.out.println(userAddress.getAddressID());
        }
        
        user.setAddressLine1(userAddress);
        personJpaController.edit(user);
        
        
        if(userUPInfo != null)
        {
            UpEmployeeInformationJpaController upEmployeeInformationJpaController = new UpEmployeeInformationJpaController(getUserTransaction(), getEntityManagerFactory());
            upEmployeeInformationJpaController.create(userUPInfo);
        }
        
    }
    
    public void updateUserAccount(HttpSession session, Person user, Address userAddress, UpEmployeeInformation userUPInfo) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        //AuthenticateUser(session);
        EntityManagerFactory tempEMF = getEntityManagerFactory();
        
        AddressJpaController addressJpaController = new AddressJpaController(getUserTransaction(), getEntityManagerFactory());
        addressJpaController.edit(userAddress);
        
                
        UpEmployeeInformationJpaController upEmployeeInformationJpaController = new UpEmployeeInformationJpaController(getUserTransaction(), tempEMF);
        
        if(userUPInfo != null)
        {   
            
            if(upEmployeeInformationJpaController.findUpEmployeeInformation(userUPInfo.getEmployeeID()) != null)
            {
                upEmployeeInformationJpaController.edit(userUPInfo);
            }
            else
            {
                upEmployeeInformationJpaController.create(userUPInfo);
                user.setUpEmployeeInformation(userUPInfo);
            }
        }
        else
        {
            if(upEmployeeInformationJpaController.findUpEmployeeInformation(userUPInfo.getEmployeeID()) != null)
            {
                user.setUpEmployee(false);
                user.setUpEmployeeInformation(null);
                upEmployeeInformationJpaController.destroy(userUPInfo.getEmployeeID());                
            }
        }
        
        PersonJpaController personJpaController = new PersonJpaController(getUserTransaction(), tempEMF);
        personJpaController.edit(user);
        
    }
    
    public void removeUserAccount(HttpSession session, String systemID) throws RollbackFailureException, Exception
    {
        //AuthenticateUser(session);
        EntityManagerFactory tempEMF = getEntityManagerFactory();
        
        PersonJpaController personJpaController = new PersonJpaController(getUserTransaction(), tempEMF);
        AddressJpaController addressJpaController = new AddressJpaController(getUserTransaction(), getEntityManagerFactory());
        
        //Find person object
        Person user = personJpaController.findPerson(systemID);
        
        //Remove primary address line
        addressJpaController.destroy(user.getAddressLine1().getAddressID());        
        
        //Check if is UP employee if is then remove
        if(user.getUpEmployee())
        {
            UpEmployeeInformationJpaController upEmployeeInformationJpaController = new UpEmployeeInformationJpaController(getUserTransaction(), tempEMF);
            upEmployeeInformationJpaController.destroy(user.getUpEmployeeInformation().getEmployeeID());
        }
        
        //Remove person from database
        personJpaController.destroy(user.getSystemID());
    }
    
    public List<Person> viewAllUserAccounts(HttpSession session)
    {
        //AuthenticateUser(session);
        PersonJpaController personJpaController = new PersonJpaController(getUserTransaction(), getEntityManagerFactory());
        return personJpaController.findPersonEntities();        
    }
    
}
