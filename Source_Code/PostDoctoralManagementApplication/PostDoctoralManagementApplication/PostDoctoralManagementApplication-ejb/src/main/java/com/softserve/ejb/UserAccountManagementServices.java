/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.*;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.Exceptions.AutomaticSystemIDGenerationException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserAccountManagementServices implements UserAccountManagementServicesLocal {     
    
    /**
     * This injection provides a container-managed entitymanagerfactory. This
     * is used to give the DAOs the ability to use application managed 
     * entity managers in JTA context so that manual transaction demarcation.
     */
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    /**
     *This function creates an instance of the PersonJpaController. 
     * Note this function's secondary goal is to simplify the subclass mocking 
     * of the UserAccountManagementServices in the unit testing 
     * @return An instance of PersonJpaController
     */
    protected PersonJpaController getPersonDAO()
    {
        return new PersonJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *This function creates an instance of the AddressJpaController. 
     * Note this function's secondary goal is to simplify the subclass mocking 
     * of the UserAccountManagementServices in the unit testing 
     * @return An instance of AddressJpaController
     */
    protected AddressJpaController getAddressDAO()
    {
        return new AddressJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *This function creates an instance of the UpEmployeeInformationJpaController. 
     * Note this function's secondary goal is to simplify the subclass mocking 
     * of the UserAccountManagementServices in the unit testing 
     * @return An instance of UpEmployeeInformationJpaController
     */
    protected UpEmployeeInformationJpaController getUPEmployeeInfoDAO()
    {
        return new UpEmployeeInformationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return new AuditTrailService();
    }
    
    protected UserGateway getUserGatewayEJB()
    {
        return new UserGateway();
    }
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    /**
     *This function is used to automatically generate a new valid SystemID
     * @param prefix The prefix to be used for the 9 character systemID
     * @return The newly generated SystemID as a string
     */
    protected String generateSystemID(char prefix)
    {
        PersonJpaController personJpaController = getPersonDAO();
        GregorianCalendar cal = new GregorianCalendar();        
        
        String newID = "";
        
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
        
        return newID;
    }
    
    /**
     *This function is used to create a new user account and its associated 
     * account information.
     * @param session The current HttpSession that is used for user authentication
     * @param useManualSystemIDSpecification A boolean indicating if the systemID
     * has been manually specified in the user or if the system needs to 
     * generate one automatically
     * @param user A Person object which contains the user's information
     * @param userAddress A Address object which contains the user's primary 
     * address line information
     * @param userUPInfo A UpEmployeeInformation object which contains the 
     * user's UP employee information if they are a UP employee
     * @throws AutomaticSystemIDGenerationException Is thrown if the system has to generate a systemID for a user that is not an administrator nor fellow
     * @throws com.softserve.DBDAO.exceptions.PreexistingEntityException Is thrown if an address, person or employeeinfo does already exist in the database
     * @throws com.softserve.DBDAO.exceptions.RollbackFailureException Is thrown if an address, person or employeeinfo failed to rollback due to some error 
     * @throws Exception Is thrown if an unknown error has occurred
     */
    @Override
    public void createUserAccount(Session session, boolean useManualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo) throws AutomaticSystemIDGenerationException, PreexistingEntityException, RollbackFailureException, Exception
    {
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();        
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(dBEntitiesFactory.bulidSecurityRoleEntity(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, null, null));
        getUserGatewayEJB().authenticateUser(session, roles);
        
        //Prep the DAOs
        PersonJpaController personJpaController = getPersonDAO();
        UpEmployeeInformationJpaController upEmployeeInformationJpaController = getUPEmployeeInfoDAO();
        AddressJpaController addressJpaController = getAddressDAO();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        
        //Check if automatic systemID generation is required
        if(!useManualSystemIDSpecification)
        {
            
            if(personJpaController.doesPersonHaveSecurityRole(user, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR))
            {
                user.setSystemID(generateSystemID('a'));
            }
            else if(personJpaController.doesPersonHaveSecurityRole(user, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW))
            {
                user.setSystemID(generateSystemID('f'));
            }
            else if(personJpaController.doesPersonHaveSecurityRole(user, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_REFEREE))
            {
                user.setSystemID(generateSystemID('r'));
            }
            else
            {
                throw new AutomaticSystemIDGenerationException("An error occured while generating a systemID for the person " + user.getFullName() + ".");
            }
            
            if(userUPInfo != null)
            {
                userUPInfo.setEmployeeID(user.getSystemID());
            }
        } 
        
        //Store address in database
        addressJpaController.create(userAddress);
        //Link to unpersisted person
        user.setAddressLine1(userAddress);       
        
        //******Possible concurrency issue if multiple automaic System IDs are generated******
        personJpaController.create(user);        
        
        if(userUPInfo != null)
        {
            upEmployeeInformationJpaController.create(userUPInfo);
        }
        
        //Log action
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Created new user account", session.getUser());
        auditTrailService.logAction(auditLog);
        
    }
    
    /**
     *This function is used to update a existing user account and its associated 
     * account information.
     * @param session The current HttpSession that is used for user authentication
     * @param user A Person object which contains the user's information
     * @param userAddress A Address object which contains the user's primary 
     * address line information
     * @param userUPInfo A UpEmployeeInformation object which contains the 
     * user's UP employee information if they are a UP employee
     * @throws NonexistentEntityException Is thrown if any of the person or address entities don't exist in the database
     * @throws com.softserve.DBDAO.exceptions.RollbackFailureException Is thrown if an address, person or employeeinfo failed to rollback due to some error 
     * @throws Exception Is thrown if an unknown error has occurred
     */
    @Override
    public void updateUserAccount(Session session, Person user, Address userAddress, UpEmployeeInformation userUPInfo) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        //AuthenticUser(session, list of privliges)
        
        PersonJpaController personJpaController = getPersonDAO();
        AddressJpaController addressJpaController = getAddressDAO();
        UpEmployeeInformationJpaController upEmployeeInformationJpaController = getUPEmployeeInfoDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        
        if(userAddress != null)
        {
            addressJpaController.edit(userAddress); 
        }
        
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
        
        personJpaController.edit(user);
        
        //Log action
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Updated user account", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    /**
     *This function is used to remove the specified user account from the database
     * @param session The current HttpSession that is used for user authentication
     * @param systemID The systemID of the user account to be removed
     * @throws RollbackFailureException
     * @throws Exception Is thrown if an unknown error has occurred
     */
    @Override
    public void removeUserAccount(Session session, String systemID) throws RollbackFailureException, Exception
    {
        //AuthenticUser(session, list of privliges)
        
        PersonJpaController personJpaController = getPersonDAO();
        AddressJpaController addressJpaController = getAddressDAO();
        UpEmployeeInformationJpaController upEmployeeInformationJpaController = getUPEmployeeInfoDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        
        //Find person object
        Person user = personJpaController.findPerson(systemID);
        
        //Remove primary address line
        addressJpaController.destroy(user.getAddressLine1().getAddressID());        
        
        //Check if is UP employee if is then remove
        if(user.getUpEmployee())
        {
            upEmployeeInformationJpaController.destroy(user.getUpEmployeeInformation().getEmployeeID());
        }
        
        //Remove person from database
        personJpaController.destroy(user.getSystemID());
        
        //Log action
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Removed user account", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    /**
     *This function is used to retreive all the users stored in the database.
     * @param session The current HttpSession that is used for user authentication
     * @return A list of Person objects representing the user accounts
     */
    @Override
    public List<Person> viewAllUserAccounts(Session session)
    {
        //AuthenticUser(session, list of privliges)
        
        PersonJpaController personJpaController = getPersonDAO();
        
        return personJpaController.findPersonEntities();        
    }
    
    public Person getUserBySystemIDOrEmail(String intput)
    {
        PersonJpaController personJpaController = getPersonDAO();
        
        return personJpaController.getUserBySystemIDOrEmail(intput);
    }
    
    /**
     *This is a simple example and testing function. It will not be deployed 
     * in final production.
     */
    @Override
    public void testAddresses() 
    {
        AddressJpaController addressJpaController = getAddressDAO();
        
        for(int i = 0; i < 15; i++ )
        {
            Address ad = new Address();
            
            ad.setCountry("South Africa");
            ad.setProvince("MP");
            ad.setZippostalCode("120" + i);

            try 
            {
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
