/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 *
 * @author Carlo
 */

public class UserGateway implements UserGatewayLocal
 { // TODO: Finalize the local or remote spec

    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return new AuditTrailService();
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    /**
     *
     * @param session
     * @param user
     * @param role
     * @return int which states the authentication value
     */
    @Override
    public int authenticateUser(Session session ,Person user, List<SecurityRole> role)
    {
        UserAccountManagementServices accounts = new UserAccountManagementServices();
        List<Person> allPersons = accounts.viewAllUserAccounts(session);
        int check = 0;
        while (check == 0)
        {
            for (Person allPerson : allPersons)
            {
                if (allPerson.getSystemID().equalsIgnoreCase(user.getSystemID()))
                {
                    if (allPerson.getPassword().equals(user.getPassword())) 
                    {
                        //What if the roles are in a different order???
                        //Hard coded the size of the array...
                        long[] roleID = new long[10];
                        long[] roleIDSentIN = new long[10];
                        List<SecurityRole> personRole = allPerson.getSecurityRoleList();
                        for(int j = 0; j < personRole.size(); j++)
                        {
                            roleID[j] = personRole.get(j).getRoleID();
                            roleIDSentIN[j] = role.get(j).getRoleID();                            
                        }
                        Arrays.sort(roleID);
                        Arrays.sort(roleIDSentIN);
                        for(int k = 0; k < roleID.length; k++)
                        {
                            if(roleID[k] != roleIDSentIN[k])
                            {
                                //incorrect security role
                                check = 3;
                            }
                        }
                    } 
                    else
                    {
                        //Incorrect password
                        check = 2;
                    }
                }
                else
                {
                    //Incorrect username
                    check = 1;
                }
            }
        }
        
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Generated on demand account", session.getUser());
        try
        {
            auditTrailService.logAction(auditLog);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(UserGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
            //else return 0
        return check;
    }
    
    
    @Override
    public void generateOnDemandAccount(Session session, boolean useManualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo)
    {
        //Use this to create a new account
        UserAccountManagementServices newUser = new UserAccountManagementServices();
        
        try
        {
            newUser.createUserAccount(session, useManualSystemIDSpecification, user, userAddress, userUPInfo);
        } 
        catch (PreexistingEntityException ex) 
        {
            Logger.getLogger(UserGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (RollbackFailureException ex)
        {
            Logger.getLogger(UserGateway.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(UserGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Generated on demand account", session.getUser());
        try
        {
            auditTrailService.logAction(auditLog);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(UserGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    /**
     *
     * @param user
     * @param active
     */
    @Override
    public void activateOnDemandAccount(Session session, Person user)
    {
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        user.setAccountStatus("active");
        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Activated on demand account", session.getUser());
        try
        {
            auditTrailService.logAction(auditLog);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(UserGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
