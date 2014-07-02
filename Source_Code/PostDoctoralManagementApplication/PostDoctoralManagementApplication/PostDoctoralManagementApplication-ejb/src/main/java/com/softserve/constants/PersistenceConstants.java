/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.constants;

import com.softserve.ejb.UserAccountManagementServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class PersistenceConstants {
    //Persistence unit name for primary postdoc_db
    public final static String PERSISTENCE_UNIT_NAME = "com.softserve_PostDoctoralManagementApplication-ejb_ejb_0.0PU";
    
    //Security role IDs constants
    public final static long SECURITY_ROLE_ID_PROSPECTIVE_FELLOW = 0;
    public final static long SECURITY_ROLE_ID_REFEREE = 1;
    public final static long SECURITY_ROLE_ID_RESEARCH_FELLOW = 2;
    public final static long SECURITY_ROLE_ID_GRANT_HOLDER = 3;
    public final static long SECURITY_ROLE_ID_HOD = 4;
    public final static long SECURITY_ROLE_ID_DEANS_OFFICE_MEMBER = 5;
    public final static long SECURITY_ROLE_ID_DRIS_MEMBER = 6;
    public final static long SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER = 7;
    public final static long SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR = 8;
    
    //Application status constants
    public final static String APPLICATION_STATUS_OPEN = "open";
    public final static String APPLICATION_STATUS_DECLINED = "declined";
    public final static String APPLICATION_STATUS_REFEREED = "refereed";
    public final static String APPLICATION_STATUS_FINALISED = "finalised";
    public final static String APPLICATION_STATUS_RECOMMENDED = "recommended";
    public final static String APPLICATION_STATUS_ENDORSED = "endorsed";
    public final static String APPLICATION_STATUS_ELIGIBLE = "eligible";
    public final static String APPLICATION_STATUS_FUNDED = "funded";
    public final static String APPLICATION_STATUS_COMPLETED = "completed";
    public final static String APPLICATION_STATUS_TERMINATED = "terminated";
    
    
    
    public static UserTransaction getUserTransaction()
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
}
