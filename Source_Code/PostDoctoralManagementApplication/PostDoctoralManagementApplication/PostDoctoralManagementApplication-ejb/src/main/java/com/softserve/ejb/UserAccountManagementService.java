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
import com.softserve.DBEntities.EmployeeInformation;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ResearchFellowInformation;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Exceptions.AutomaticSystemIDGenerationException;
import com.softserve.Exceptions.UserAlreadyExistsException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Generator;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserAccountManagementService implements UserAccountManagementServiceLocal {     
    
    /**
     * This injection provides a container-managed entitymanagerfactory. This
     * is used to give the DAOs the ability to use application managed 
     * entity managers in JTA context so that manual transaction demarcation.
     */
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    @EJB
    private AuditTrailServiceLocal auditTrailServiceLocal;
    @EJB
    private UserGatewayLocal userGatewayLocal;
    
    /**
     *
     * @return
     */
    protected UserGatewayLocal getUserGatewayServiceEJB()
    {
        return userGatewayLocal;
    }
    
    /**
     *
     * @return
     */
    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }
    
    /**
     *
     * @return
     */
    protected AuditTrailServiceLocal getAuditTrailServiceEJB()
    {
        return auditTrailServiceLocal;
    }
    
    
    
    /**
     *
     */
    public UserAccountManagementService() {
    }

    /**
     *
     * @param emf
     */
    public UserAccountManagementService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /**
     *This function creates an instance of the PersonJpaController. 
     * Note this function's secondary goal is to simplify the subclass mocking 
 of the UserAccountManagementService in the unit testing 
     * @return An instance of PersonJpaController
     */
    protected PersonJpaController getPersonDAO()
    {
        return new PersonJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *This function creates an instance of the AddressJpaController. 
     * Note this function's secondary goal is to simplify the subclass mocking 
 of the UserAccountManagementService in the unit testing 
     * @return An instance of AddressJpaController
     */
    protected AddressJpaController getAddressDAO()
    {
        return new AddressJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *This function creates an instance of the UpEmployeeInformationJpaController. 
     * Note this function's secondary goal is to simplify the subclass mocking 
 of the UserAccountManagementService in the unit testing 
     * @return An instance of UpEmployeeInformationJpaController
     */
    protected EmployeeInformationJpaController getEmployeeInfoDAO()
    {
        return new EmployeeInformationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ResearchFellowInformationJpaController getResearchFellowInformationDAO()
    {
        return new ResearchFellowInformationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected SecurityRoleJpaController getSecurityRoleDAO()
    {
        return new SecurityRoleJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected NotificationJpaController getNotificationDAO()
    {
        return new NotificationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected AuditLogJpaController getAuditLogDAO()
    {
        return new AuditLogJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *
     * @return
     */
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    /**
     *
     * @return
     */
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    /**
     *
     * @return
     */
    protected Generator getGeneratorUTIL()
    {
        return new Generator();
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
    public void createUserAccount(Session session, boolean useManualSystemIDSpecification, Person user) throws AutomaticSystemIDGenerationException, PreexistingEntityException, RollbackFailureException, Exception
    {     
        //Authenticate user privliges
        UserGatewayLocal userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        userGateway.authenticateUser(session, roles);
        /* NB DISABLED FOR TESTING PURPOSE
        if(findUserBySystemIDOrEmail(user.getEmail()) != null)
        {
            throw new UserAlreadyExistsException("This email is already in use by a user account");
        }*/
        
        if(useManualSystemIDSpecification && getUserBySystemIDOrEmail(user.getSystemID()) != null)
        {
            throw new UserAlreadyExistsException("This employee ID is already in use by a user account");
        }
        
        //Prep the DAOs
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();  
        PersonJpaController personJpaController = getPersonDAO();
        EmployeeInformationJpaController employeeInformationJpaController = getEmployeeInfoDAO();
        AddressJpaController addressJpaController = getAddressDAO();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        Address userAddress = user.getAddressLine1(); 
        EmployeeInformation userEmployeeInfo = user.getEmployeeInformation();
        Address upAddress = (userEmployeeInfo != null)?userEmployeeInfo.getPhysicalAddress():null;
        ResearchFellowInformation researchFellowInformation = user.getResearchFellowInformation();
        
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
            
            if(userEmployeeInfo != null)
            {
                userEmployeeInfo.setEmployeeID(user.getSystemID());
            }
        }
        
        if(userAddress == null)
        {
            userAddress = new Address();
        }
        
        //Store address in database
        addressJpaController.create(userAddress);
        //Link to unpersisted person
        user.setAddressLine1(userAddress);       
        user.setEmployeeInformation(null);
        user.setResearchFellowInformation(null);
        //******Possible concurrency issue if multiple automaic System IDs are generated******
        personJpaController.create(user);        
        
        if(userEmployeeInfo != null)
        {
            addressJpaController.create(upAddress);
            userEmployeeInfo.setPhysicalAddress(upAddress);
            userEmployeeInfo.setPerson(user);
            userEmployeeInfo.setEmployeeID(user.getSystemID());
            employeeInformationJpaController.create(userEmployeeInfo);
        }
        
        
        if(researchFellowInformation != null)
        {
            researchFellowInformation.setPerson(user);
            researchFellowInformation.setSystemAssignedID(user.getSystemID());
            getResearchFellowInformationDAO().create(researchFellowInformation);
        }
        
        //Log action
        AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Created new user account", (session.getUser() == null && session.isSystem())  ? user : session.getUser()); //This is a isolated instance when a prospective fellow creates a new account
        auditTrailService.logAction(auditLog);
        
        Notification notification = dBEntitiesFactory.createNotificationEntity(user, user, "Created account", "Account has been created for you");
        notificationService.sendNotification(notification, true);
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
    public void updateUserAccount(Session session, Person user) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        
        UserGatewayLocal userGateway = getUserGatewayServiceEJB();
        try
        {
            //Authenticate user ownership of account
            userGateway.authenticateUserAsOwner(session, user);
        } 
        catch(AuthenticationException ex)
        {
            //Authenticate user privliges
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            userGateway.authenticateUser(session, roles);
        } 
        
        PersonJpaController personJpaController = getPersonDAO();
        AddressJpaController addressJpaController = getAddressDAO();
        ResearchFellowInformationJpaController researchFellowInformationJpaController = getResearchFellowInformationDAO();
        EmployeeInformationJpaController employeeInformationJpaController = getEmployeeInfoDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        
        Address userAddress = user.getAddressLine1(); 
        EmployeeInformation userUPInfo = user.getEmployeeInformation();
        Address upAddress = (userUPInfo != null)?userUPInfo.getPhysicalAddress():null;
        ResearchFellowInformation researchFellowInformation = user.getResearchFellowInformation();
        System.out.println("=====" + user.getEmail() + " " + user.toString() + " " + getUserBySystemIDOrEmail(user.getEmail()));
        if(!getUserBySystemIDOrEmail(user.getEmail()).equals(user))
        {
            throw new UserAlreadyExistsException("This email is already in use by a user account");
        }
        
        if(getUserBySystemIDOrEmail(user.getSystemID()) == null)
        {
            throw new Exception("There is no user account for the specified user.");
        }
        
        if(userAddress != null)
        {
            addressJpaController.edit(userAddress); 
        }
        
        if(userUPInfo != null)
        {   
            
            if(employeeInformationJpaController.findEmployeeInformation(userUPInfo.getEmployeeID()) != null)
            {
                addressJpaController.edit(userUPInfo.getPhysicalAddress());
                userUPInfo.setPhysicalAddress(upAddress);
                employeeInformationJpaController.edit(userUPInfo);
            }
            else
            {
                addressJpaController.create(upAddress);
                userUPInfo.setPhysicalAddress(upAddress);
                employeeInformationJpaController.create(userUPInfo);
                user.setEmployeeInformation(userUPInfo);
            }
        }
        else
        {
            if(employeeInformationJpaController.findEmployeeInformation(user.getSystemID()) != null)
            {
                user.setUpEmployee(false);
                user.setEmployeeInformation(null);
                userUPInfo = employeeInformationJpaController.findEmployeeInformation(user.getSystemID());
                Long id = userUPInfo.getPhysicalAddress().getAddressID();
                employeeInformationJpaController.destroy(userUPInfo.getEmployeeID());
                addressJpaController.destroy(id);
            }
        }
        
        if(researchFellowInformation != null)
        {   
            
            if(researchFellowInformationJpaController.findResearchFellowInformation(researchFellowInformation.getSystemAssignedID()) != null)
            {
                researchFellowInformationJpaController.edit(researchFellowInformation);
            }
            else
            {
                researchFellowInformation.setPerson(user);
                researchFellowInformation.setSystemAssignedID(user.getSystemID());
                researchFellowInformationJpaController.create(researchFellowInformation);
                user.setResearchFellowInformation(researchFellowInformation);
            }
        }
        else
        {
            if(researchFellowInformationJpaController.findResearchFellowInformation(user.getSystemID()) != null)
            {
                user.setResearchFellowInformation(null);
                researchFellowInformation = researchFellowInformationJpaController.findResearchFellowInformation(user.getSystemID());
                researchFellowInformationJpaController.destroy(researchFellowInformation.getSystemAssignedID());
            }
        }
        
        
        personJpaController.edit(user);
        
        //Log action
        AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Updated user account", session.getUser());
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
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        if(session.getUser().getSystemID().equals(systemID))
        {
            throw new Exception("You cannot delete your own account");
        }
        
        PersonJpaController personJpaController = getPersonDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        
        //Find person object
        Person user = personJpaController.findPerson(systemID);
        
        user.setAccountStatus(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_DISABLED);
        personJpaController.edit(user);
        
        //Log action
        AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Removed user account", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    /**
     *
     * @param session
     * @param reason
     * @param useManualSystemIDSpecification
     * @param user
     * @param userAddress
     * @param userUPInfo
     * @throws Exception
     */
    @Override
    public void generateOnDemandAccount(Session session, String reason, boolean useManualSystemIDSpecification, Person user) throws Exception
    {
        //Use this to create a new account
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        if(getUserBySystemIDOrEmail(user.getEmail()) != null)
        {
            throw new UserAlreadyExistsException("This email is already in use by a user account");
        }
        
        //Set account to dorment
        user.setAccountStatus(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_PENDING);
        //Set new random password
        user.setPassword(getGeneratorUTIL().generateRandomHexString());
        
        //Create a user account using a system level system authentication
        createUserAccount(new Session(session.getHttpSession(), session.getUser(), true), useManualSystemIDSpecification, user);
        
        //Notify the new user
        Notification notification = dBEntitiesFactory.createNotificationEntity(session.getUser(), user, "Automatic account creation", "The user " + session.getUser().getCompleteName() + " has requested that a account be created for you for the following reasons: " + reason + ". Please visit inorder to activate your account. Log in with your email address and the following password " + user.getPassword());
        notificationService.sendNotification(notification, true);
        
        //Log this action
        AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Generated on demand account", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    /**
     *
     * @param session
     * @param user
     * @throws java.lang.Exception
     */
    @Override
    public void activateOnDemandAccount(Session session, Person user) throws Exception
    {
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        user.setAccountStatus(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_ACTIVE);
        updateUserAccount(session, user);
        
        AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Activated on demand account", session.getUser());
        auditTrailService.logAction(auditLog);

    }    
    
    /**
     *This function is used to retreive all the users stored in the database.
     * @param session The current HttpSession that is used for user authentication
     * @return A list of Person objects representing the user accounts
     */
    @Override
    public List<Person> viewAllUserAccounts(Session session) throws AuthenticationException, Exception
    {
        UserGatewayLocal userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        userGateway.authenticateUser(session, roles);
        
        PersonJpaController personJpaController = getPersonDAO();
        
        return personJpaController.findPersonEntities();        
    }
    
    /**
     *
     * @param intput
     * @return
     */
    @Override
    public Person getUserBySystemIDOrEmail(String intput)
    {
        PersonJpaController personJpaController = getPersonDAO();
        
        return personJpaController.findUserBySystemIDOrEmail(intput);
    }
    
    /**
     *
     * @param systemID
     * @return
     */
    public Person getUserBySystemID(String systemID)
    {
        return getPersonDAO().findPerson(systemID);
    }
    
    @Override
    public List<SecurityRole> getAllSecurityRoles()
    {
        try
        {
            return getSecurityRoleDAO().findSecurityRoleEntities();
        }
        catch(Exception ex)
        {
            return new ArrayList<SecurityRole>();
        }
    }

    @Override
    public void approveOnDemandAccount(Session session, Person account) throws Exception 
    {
        UserGatewayLocal userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        userGateway.authenticateUser(session, roles);
        
        PersonJpaController personJpaController = getPersonDAO();
        
        account.setAccountStatus(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_DORMENT);
        
        personJpaController.edit(account);
    }

    @Override
    public void declineOnDemandAccount(Session session, Person account) throws Exception 
    {
        UserGatewayLocal userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        userGateway.authenticateUser(session, roles);
        
        PersonJpaController personJpaController = getPersonDAO();
        NotificationJpaController notificationJpaController = getNotificationDAO();
        AuditLogJpaController auditLogJpaController = getAuditLogDAO();
        
        account.setAccountStatus(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_DISABLED);
        
        for(Notification notification: account.getNotificationList())
        {
            try
            {
                notificationJpaController.destroy(notification.getNotificationID());
            }
            catch(Exception ex)
            {
                
            }
        }
        
        for(Notification notification: account.getNotificationList1())
        {
            try
            {
                notificationJpaController.destroy(notification.getNotificationID());
            }
            catch(Exception ex)
            {
                
            }
        }
        
        for(AuditLog auditLog : account.getAuditLogList())
        {
            auditLogJpaController.destroy(auditLog.getEntryID());
        }
        
        if(account.getEmployeeInformation()!= null)
        {
            Address address = account.getEmployeeInformation().getPhysicalAddress();
            
            getEmployeeInfoDAO().destroy(account.getEmployeeInformation().getEmployeeID());
            
            if(address != null)
            {
                getAddressDAO().destroy(account.getEmployeeInformation().getPhysicalAddress().getAddressID());
            }
        }
        
        if(account.getAddressLine1() != null)
        {
            getAddressDAO().destroy(account.getAddressLine1().getAddressID());
        }
        
        personJpaController.destroy(account.getSystemID());
    }

    @Override
    public List<Person> loadAllPendingOnDemandAccounts(Session session) throws Exception 
    {
        UserGatewayLocal userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        userGateway.authenticateUser(session, roles);
        
        return getPersonDAO().findAllUsersWhichHaveAccountStatus(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_PENDING);
    }
    
    
}
