/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.Webapp.depenedentbeans.PrePostConditionMethodFilterBean;
import com.softserve.Webapp.requestbeans.NeuralNetworkCreationBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.nonapplicationservices.NeuralNetworkManagementServicesLocal;
import com.softserve.persistence.DBEntities.NeuralNetwork;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "neuralNetworkEditBean")
@ConversationScoped
public class NeuralNetworkEditBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    
    @EJB
    private NeuralNetworkManagementServicesLocal neuralNetworkManagementServicesLocal;
    
    private NeuralNetwork neuralNetwork; 
    
    /**
     * Creates a new instance of NeuralNetworkEditBean
     */
    public NeuralNetworkEditBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        try
        {
            neuralNetwork = sessionManagerBean.getObjectFromSessionStorage("NEURALNETWORK", NeuralNetwork.class);
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(NeuralNetworkEditBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToPreviousBreadCrumb());
        }
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }
    
    public String preformNeuralNetworkEditRequest()
    {        
        try
        {
            neuralNetworkManagementServicesLocal.updateNeuralNetwork(sessionManagerBean.getSession(), neuralNetwork);
            return navigationManagerBean.goToPreviousBreadCrumb();
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(NeuralNetworkCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    }
    
}
