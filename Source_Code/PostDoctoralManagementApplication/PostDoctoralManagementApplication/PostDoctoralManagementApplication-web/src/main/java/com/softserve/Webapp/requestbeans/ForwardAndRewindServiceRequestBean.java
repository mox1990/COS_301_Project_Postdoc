/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.Application;
import com.softserve.Webapp.conversationbeans.ForwardAndRewindServiceApplicationSelectionBean;
import com.softserve.Webapp.depenedentbeans.ApplicationFilterDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.ForwardAndRewindServicesLocal;
import com.softserve.system.ApplicationServicesUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "forwardAndRewindServiceRequestBean")
@RequestScoped
public class ForwardAndRewindServiceRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    
    @EJB
    private ForwardAndRewindServicesLocal forwardAndRewindServicesLocal;
    
    private ApplicationServicesUtil applicationServicesUtil;
    
    private String toStatus;    
    private String reason;
    /**
     * Creates a new instance of ForwardAndRewindServiceRequestBean
     */
    public ForwardAndRewindServiceRequestBean() {
    }
    
    @PostConstruct
    public void init()
    {        
        toStatus = "";
        reason = "";
        applicationServicesUtil = new ApplicationServicesUtil(null);        
    }
    
    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
            
    public List<String> getPossibleStatusChanges(Application application)
    {
        List<String> output = new ArrayList<String>();
        
        if(applicationServicesUtil.getOrderIndexOfApplicationStatus(application.getStatus()) < 8)
        {
            output.add(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN);
            output.add(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
            output.add(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
            output.add(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
            output.add(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
            output.add(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);

            output.remove(application.getStatus());
        }
        
        return output;
    }
    
    public void performApplicationStatusMoveRequest(Application application)
    {
        System.out.println("Preform");
        try
        {
            
            if(applicationServicesUtil.getOrderIndexOfApplicationStatus(toStatus) < 6 && applicationServicesUtil.getOrderIndexOfApplicationStatus(application.getStatus()) < 9)
            {
            
                if(applicationServicesUtil.getOrderIndexOfApplicationStatus(toStatus) > applicationServicesUtil.getOrderIndexOfApplicationStatus(application.getStatus()))
                {
                    forwardAndRewindServicesLocal.forwardApplication(sessionManagerBean.getSession(), application, toStatus, reason);
                }
                else
                {
                    forwardAndRewindServicesLocal.rewindApplication(sessionManagerBean.getSession(), application, toStatus, reason);
                }
                
                reason = "";
            }
            else
            {
                throw new Exception("An error has ocurred with the status specification.");
            }
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(ForwardAndRewindServiceRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }
    
}
