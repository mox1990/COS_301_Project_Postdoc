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
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
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
    
    @PersistenceUnit(unitName = "com.softserve_PostDoctoralManagementApplication-ejb_ejb_0.0PU")
    private EntityManagerFactory emf;
    
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

    private String generateSystemID(char prefix)
    {
        String newID = "";
        
        PersonJpaController personJpaController = new PersonJpaController(getUserTransaction(), emf);
        
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
    
    @Override
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
        
        AddressJpaController addressJpaController = new AddressJpaController(getUserTransaction(), emf);
        addressJpaController.create(userAddress);
        
        if(userAddress.getAddressID() == null)
        {
            System.out.println("Null 2");
        }
        else
        {
            System.out.println(userAddress.getAddressID());
        }
 
        user.setAddressLine1(userAddress);        
        
        PersonJpaController personJpaController = new PersonJpaController(getUserTransaction(), emf);
        personJpaController.create(user);        
        
        if(userUPInfo != null)
        {
            UpEmployeeInformationJpaController upEmployeeInformationJpaController = new UpEmployeeInformationJpaController(getUserTransaction(), emf);
            upEmployeeInformationJpaController.create(userUPInfo);
        }
        
    }
    
    @Override
    public void updateUserAccount(HttpSession session, Person user, Address userAddress, UpEmployeeInformation userUPInfo) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        //AuthenticateUser(session);
        
        AddressJpaController addressJpaController = new AddressJpaController(getUserTransaction(), emf);
        addressJpaController.edit(userAddress);
        
                
        UpEmployeeInformationJpaController upEmployeeInformationJpaController = new UpEmployeeInformationJpaController(getUserTransaction(), emf);
        
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
        
        PersonJpaController personJpaController = new PersonJpaController(getUserTransaction(), emf);
        personJpaController.edit(user);
        
    }
    
    @Override
    public void removeUserAccount(HttpSession session, String systemID) throws RollbackFailureException, Exception
    {
        //AuthenticateUser(session);
        
        PersonJpaController personJpaController = new PersonJpaController(getUserTransaction(), emf);
        AddressJpaController addressJpaController = new AddressJpaController(getUserTransaction(), emf);
        
        //Find person object
        Person user = personJpaController.findPerson(systemID);
        
        //Remove primary address line
        addressJpaController.destroy(user.getAddressLine1().getAddressID());        
        
        //Check if is UP employee if is then remove
        if(user.getUpEmployee())
        {
            UpEmployeeInformationJpaController upEmployeeInformationJpaController = new UpEmployeeInformationJpaController(getUserTransaction(), emf);
            upEmployeeInformationJpaController.destroy(user.getUpEmployeeInformation().getEmployeeID());
        }
        
        //Remove person from database
        personJpaController.destroy(user.getSystemID());
    }
    
    @Override
    public List<Person> viewAllUserAccounts(HttpSession session)
    {
        //AuthenticateUser(session);
        PersonJpaController personJpaController = new PersonJpaController(getUserTransaction(), emf);
        return personJpaController.findPersonEntities();        
    }
    

    @Override
    public void testAddresses() 
    {
        for(int i = 0; i < 15; i++ )
        {
            Address ad = new Address();
            
            ad.setCountry("South Africa");
            ad.setProvince("MP");
            ad.setZippostalCode("120" + i);

            try 
            {
                AddressJpaController addressJpaController = new AddressJpaController((UserTransaction) new InitialContext().doLookup("java:comp/UserTransaction"), emf);
                addressJpaController.create(ad);                
            } 
            catch (NamingException ex) 
            {
                Logger.getLogger(UserAccountManagementServices.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (RollbackFailureException ex) 
            {
                Logger.getLogger(UserAccountManagementServices.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (Exception ex) 
            {
                Logger.getLogger(UserAccountManagementServices.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(ad.getAddressID() != null)
            {
                System.out.print("Adress id is " + ad.getAddressID());
            }
            else
            {
                System.out.print("Address " + i + "'s ID is null");
            }
            
        }
    }
    
    
}
