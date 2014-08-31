/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.constants;

import com.softserve.DBEntities.SecurityRole;
import com.softserve.ejb.UserAccountManagementService;
import java.util.ArrayList;
import java.util.List;
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
    //Persistence unit name for postdoc databases
    public final static String WORKING_DB_PERSISTENCE_UNIT_NAME = "com.softserve_PostDoctoralManagementApplication-ejb_ejb_0.0PU";
    public final static String BACKUP_DB_PERSISTENCE_UNIT_NAME = "com.softserve_PostDoctoralManagementApplication-ejb_ejb_0.0PU3";
    public final static String ARCHIVE_DB_PERSISTENCE_UNIT_NAME = "com.softserve_PostDoctoralManagementApplication-ejb_ejb_0.0PU2";
    
    //Security role IDs constants
    public final static long SECURITY_ROLE_ID_PROSPECTIVE_FELLOW = 1;
    public final static long SECURITY_ROLE_ID_REFEREE = 2;
    public final static long SECURITY_ROLE_ID_RESEARCH_FELLOW = 3;
    public final static long SECURITY_ROLE_ID_GRANT_HOLDER = 4;
    public final static long SECURITY_ROLE_ID_HOD = 5;
    public final static long SECURITY_ROLE_ID_DEANS_OFFICE_MEMBER = 6;
    public final static long SECURITY_ROLE_ID_DRIS_MEMBER = 7;
    public final static long SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER = 8;
    public final static long SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR = 9;
    
    public final static SecurityRole SECURITY_ROLE_PROSPECTIVE_FELLOW = new SecurityRole((long) 1);
    public final static SecurityRole SECURITY_ROLE_REFEREE = new SecurityRole((long) 2);
    public final static SecurityRole SECURITY_ROLE_RESEARCH_FELLOW = new SecurityRole((long) 3);
    public final static SecurityRole SECURITY_ROLE_GRANT_HOLDER = new SecurityRole((long) 4);
    public final static SecurityRole SECURITY_ROLE_HOD = new SecurityRole((long) 5);
    public final static SecurityRole SECURITY_ROLE_DEANS_OFFICE_MEMBER = new SecurityRole((long) 6);
    public final static SecurityRole SECURITY_ROLE_DRIS_MEMBER = new SecurityRole((long) 7);
    public final static SecurityRole SECURITY_ROLE_POSTDOCTORAL_COMMITTEE_MEMBER = new SecurityRole((long) 8);
    public final static SecurityRole SECURITY_ROLE_SYSTEM_ADMINISTRATOR = new SecurityRole((long) 9);
    
    //Account status constants
    public final static String ACCOUNT_STATUS_ACTIVE = "active";
    public final static String ACCOUNT_STATUS_DISABLED = "disabled";
    public final static String ACCOUNT_STATUS_DORMENT = "dorment";
    public final static String ACCOUNT_STATUS_PENDING = "pending";
    
    //Application status constants
    public final static String APPLICATION_STATUS_OPEN = "open";
    public final static String APPLICATION_STATUS_SUBMITTED = "submitted";
    public final static String APPLICATION_STATUS_DECLINED = "declined";
    public final static String APPLICATION_STATUS_REFERRED = "referred";
    public final static String APPLICATION_STATUS_FINALISED = "finalised";
    public final static String APPLICATION_STATUS_RECOMMENDED = "recommended";
    public final static String APPLICATION_STATUS_ENDORSED = "endorsed";
    public final static String APPLICATION_STATUS_ELIGIBLE = "eligible";
    public final static String APPLICATION_STATUS_FUNDED = "funded";
    public final static String APPLICATION_STATUS_COMPLETED = "completed";
    public final static String APPLICATION_STATUS_TERMINATED = "terminated";
    
    //Application type constants
    public final static String APPLICATION_TYPE_NEW = "new";
    public final static String APPLICATION_TYPE_RENEWAL = "renewal";
    
    //Application funding type constants
    public final static String APPLICATION_FUNDINGTYPE_UPPHDPOSTDOC = "UP PhD Postdoc";
    public final static String APPLICATION_FUNDINGTYPE_UPPOSTDOC = "UP Postdoc";
    public final static String APPLICATION_FUNDINGTYPE_EXTERNALLYFUNDED = "Externally funded";
    
    //CV gender constants
    public final static String CV_GENDER_MALE = "Male";
    public final static String CV_GENDER_FEMALE = "Female";
    public final static String CV_GENDER_OTHER = "Other";
    
    //Application review request type constants
    public final static String APPLICATION_REVIEW_TPYE_HOD = "HOD";
    public final static String APPLICATION_REVIEW_TYPE_DEAN = "DEAN";
    
    //CV nrf ratings constants
    public final static String CV_NRF_RATING_A = "A";
    public final static String CV_NRF_RATING_B1 = "B1";
    public final static String CV_NRF_RATING_B2 = "B2";
    public final static String CV_NRF_RATING_C1 = "C1";
    public final static String CV_NRF_RATING_C2 = "C2";
    public final static String CV_NRF_RATING_C3 = "C3";
    public final static String CV_NRF_RATING_Y = "Y";
    public final static String CV_NRF_RATING_P = "P";
    public final static String CV_NRF_RATING_NA = "N/A";
    
    public final static String CV_REFERENCE_SUBMITTED = "Submitted";
    public final static String CV_REFERENCE_UNDERREVIEW = "Under review";
    public final static String CV_REFERENCE_INPROGRESS = "In progress";
    public final static String CV_REFERENCE_PUBLISHED = "Published";
    
    //Person title constants
    public final static String PERSON_TITLE_MR = "Mr";
    public final static String PERSON_TITLE_MRS = "Mrs";
    public final static String PERSON_TITLE_MS = "Ms";
    public final static String PERSON_TITLE_DR = "Dr";
    public final static String PERSON_TITLE_PROF = "Prof";
    
    //Notification email status
    public final static String NOTIFICATION_EMAIL_STATUS_SENT = "sent";
    public final static String NOTIFICATION_EMAIL_STATUS_QUEUED = "queued";
    public final static String NOTIFICATION_EMAIL_STATUS_DISABLED = "disabled";
    
    //Funding cost type constants
    public final static String FUNDINGCOST_TYPE_FELLOWSHIP = "fellowship";
    public final static String FUNDINGCOST_TYPE_RUNNING = "running";
    public final static String FUNDINGCOST_TYPE_TRAVEL = "travel";
    public final static String FUNDINGCOST_TYPE_EQUIPMENT = "equipment";
    public final static String FUNDINGCOST_TYPE_OPERATING = "operating";
    public final static String FUNDINGCOST_TYPE_CONFERENCE = "conference";
    
    //Forward and rewind report type constants
    public final static String FORWARDREWINREPORT_TYPE_FORWARD = "forward";
    public final static String FORWARDREWINREPORT_TYPE_REWIND = "rewind";
    
    //CV race type constants
    public final static String CV_RACE_WHITE = "White";
    public final static String CV_RACE_BLACK = "Black";
    public final static String CV_RACE_ASIAN = "Asian";
    public final static String CV_RACE_INDIAN = "Indian";
    public final static String CV_RACE_COLOURED = "Coloured";
    public final static String CV_RACE_OTHER = "Other";
    
    //CV race type constants
    public final static String CV_CITIZENSHIP_SOUTHAFRICAN = "South African";
    public final static String CV_CITIZENSHIP_OTHER = "Other";
    
    //Employee appointment status type constants
    public final static String EMPLOYEE_APPOINTMENT_STATUS_FULLTIME = "Full time";
    public final static String EMPLOYEE_APPOINTMENT_STATUS_PARTTIME = "Part time";
    
    public static UserTransaction getUserTransaction()
    {
        try 
        {
            return (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        } 
        catch (NamingException ex) 
        {
            Logger.getLogger(UserAccountManagementService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static List<SecurityRole> getAllSecurityRoles()
    {
        List<SecurityRole> list = new ArrayList<SecurityRole>();
        
        for(long i = 1; i < 10; i++)
        {
            list.add(new SecurityRole((long) i));
        }
        
        return list;
    }
    
    public static String getSecurityRoleName(Long id)
    {
        if(id == SECURITY_ROLE_ID_PROSPECTIVE_FELLOW)
        {
            return "Prospective fellow";
        }
        else if(id == SECURITY_ROLE_ID_RESEARCH_FELLOW)
        {
            return "Research fellow";
        }
        else if(id == SECURITY_ROLE_ID_DEANS_OFFICE_MEMBER)
        {
            return "Dean's office member";
        }
        else if(id == SECURITY_ROLE_ID_DRIS_MEMBER)
        {
            return "DRIS member";
        }
        else if(id == SECURITY_ROLE_ID_GRANT_HOLDER)
        {
            return "Grant holder";
        }
        else if(id == SECURITY_ROLE_ID_HOD)
        {
            return "HOD";
        }
        else if(id == SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER)
        {
            return "Post doctoral Committee member";
        }
        else if(id == SECURITY_ROLE_ID_REFEREE)
        {
            return "Referee";
        }
        else if(id == SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR)
        {
            return "System administrator";
        }
        else
        {
            return "";
        }
    }
}
