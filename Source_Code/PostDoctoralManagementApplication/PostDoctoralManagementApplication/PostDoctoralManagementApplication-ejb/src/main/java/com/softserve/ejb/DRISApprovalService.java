/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.FundingReportJpaController;
import com.softserve.DBDAO.RecommendationReportJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.FundingReport;
import com.softserve.system.Session;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class DRISApprovalService implements DRISApprovalServiceLocal {
    
    @PersistenceUnit(unitName=com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected FundingReportJpaController getFundingReportDAO()
    {
        return new FundingReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *This function loads all the applications that need to approved/declined by the 
     * specified HOD
     * @param session The session object used to authenticate the user
     * @return A list of all the applications that user can Approved/declined
     */
    public List<Application> loadPendingEndorsedApplications(Session session)
    {
        //AuthenticUser(session, list of privliges)
        
        ApplicationServices applicationServices = new ApplicationServices();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
    }
    
    /**
     *This function loads all the applications that need to approved/declined by the 
     * specified HOD
     * @param session The session object used to authenticate the user
     * @return A list of all the applications that user can Approved/declined
     */
    public List<Application> loadPendingEligibleApplications(Session session)
    {
        //AuthenticUser(session, list of privliges)
        
        ApplicationServices applicationServices = new ApplicationServices();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE);
    }
    
    private boolean hasPhD(Application application)
    {
        List<AcademicQualification> aqList = application.getFellow().getCvList().get(0).getAcademicQualificationList();
        
        for(AcademicQualification aq : aqList)
        {
            if(aq.getName().toUpperCase().contains("PHD"))
            {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean hasObtainedPhDInLast5Years(Application application)
    {     
        
        List<AcademicQualification> aqList = application.getFellow().getCvList().get(0).getAcademicQualificationList();
        
        GregorianCalendar curCal = new GregorianCalendar();
        GregorianCalendar obtainCal = new GregorianCalendar();
        
        
        for(AcademicQualification aq : aqList)
        {
            if(aq.getName().toUpperCase().contains("PHD"))
            {
                obtainCal.setTimeInMillis(aq.getYearObtained().getTime());
                if(curCal.get(GregorianCalendar.YEAR) - obtainCal.get(GregorianCalendar.YEAR) <= 5)
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public void checkApplicationForEligiblity(Session session, Application application) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        //AuthenticUser(session, list of privliges)
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        Date dobDate = application.getFellow().getCvList().get(0).getDateOfBirth();
        GregorianCalendar curCal = new GregorianCalendar();
        GregorianCalendar dobCal = new GregorianCalendar();
        dobCal.setTime(dobDate);
        
        
        if((curCal.get(GregorianCalendar.YEAR) - dobCal.get(GregorianCalendar.YEAR) <= 40 && hasPhD(application)) || (hasObtainedPhDInLast5Years(application)))
        {
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE);
            
            applicationJpaController.edit(application);
            
            //Log action
        }
        else
        {
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED);
            
            applicationJpaController.edit(application);
            
            //Log action
            
            //Send notification with reason to grant holder and applicant
        }
    }
    
    public void denyFunding(Session session, Application application, String reason) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        //AuthenticUser(session, list of privliges)
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED);
        
        applicationJpaController.edit(application);
        
        //Log action
        
        //Send notification to grant holder and applicatant
    }
    
    public void approveFunding(Session session, Application application, FundingReport fundingReport, String cscMesssage, String finaceMessage) throws RollbackFailureException, Exception
    {
        //AuthenticUser(session, list of privliges)
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        FundingReportJpaController fundingReportJpaController = getFundingReportDAO();
        
        fundingReportJpaController.create(fundingReport);
        
        application.setFundingReportID(fundingReport);
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED);
        
        try
        {
            applicationJpaController.edit(application);
        }
        catch(Exception ex)
        {
            //If an error occurs during update of application the recommendation report must be removed as well
            fundingReportJpaController.destroy(fundingReport.getReportID());
            throw ex;
        }
                
        //Log action
        
        //Send notification to CSC, Finance, grant holder and applicatant
    }
}
