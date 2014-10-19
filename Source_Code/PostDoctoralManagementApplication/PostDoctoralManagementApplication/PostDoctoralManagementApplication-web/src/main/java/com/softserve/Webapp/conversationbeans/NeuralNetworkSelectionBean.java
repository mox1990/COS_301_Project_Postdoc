/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.Webapp.depenedentbeans.NeuralNetworkFilterDependBean;
import com.softserve.Webapp.depenedentbeans.PrePostConditionMethodFilterBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.nonapplicationservices.NeuralNetworkManagementServicesLocal;
import com.softserve.persistence.DBEntities.NeuralNetwork;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "neuralNetworkSelectionBean")
@Dependent
public class NeuralNetworkSelectionBean {
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private NeuralNetworkFilterDependBean neuralNetworkFilterDependBean;
    @Inject
    private Conversation conversation;
    
    @EJB
    private NeuralNetworkManagementServicesLocal neuralNetworkManagementServicesLocal;
    /**
     * Creates a new instance of NeuralNetworkSelectionBean
     */
    public NeuralNetworkSelectionBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        try
        {
            neuralNetworkFilterDependBean.init(neuralNetworkManagementServicesLocal.loadAllNeuralNetworks(sessionManagerBean.getSession()));
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(PendingUserAccountsSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToUserAccountManagementServicesHomeView());
        }
    }

    public NeuralNetworkFilterDependBean getNeuralNetworkFilterDependBean() {
        return neuralNetworkFilterDependBean;
    }

    public void setNeuralNetworkFilterDependBean(NeuralNetworkFilterDependBean neuralNetworkFilterDependBean) {
        this.neuralNetworkFilterDependBean = neuralNetworkFilterDependBean;
    }
    
    private void selectNeuralNetwork(NeuralNetwork neuralNetwork)
    {
        sessionManagerBean.addObjectToSessionStorage("NEURALNETWORK", neuralNetwork);
    }
    
    public String viewNeuralNetwork(NeuralNetwork neuralNetwork)
    {
        selectNeuralNetwork(neuralNetwork);
        
        return navigationManagerBean.goToNeuralNetworkManagementServicesViewer();
    }
    
    public String editNeuralNetwork(NeuralNetwork neuralNetwork)
    {
        selectNeuralNetwork(neuralNetwork);
        
        return navigationManagerBean.goToNeuralNetworkManagementServicesEditor();
    }
    
    public void makeNeuralNetworkDefault(NeuralNetwork neuralNetwork)
    {
        try
        {
            neuralNetworkManagementServicesLocal.makeNeuralNetworkDefaultNetwork(sessionManagerBean.getSession(), neuralNetwork); 
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(PendingUserAccountsSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }
    
}
