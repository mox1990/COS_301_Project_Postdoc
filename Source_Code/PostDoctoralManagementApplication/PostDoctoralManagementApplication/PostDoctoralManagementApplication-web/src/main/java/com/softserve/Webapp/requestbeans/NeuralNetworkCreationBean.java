/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.auxiliary.requestresponseclasses.NeuralNetworkCreationRequest;
import com.softserve.ejb.applicationservices.ApplicationSuccessEvaluationServices;
import com.softserve.ejb.applicationservices.ApplicationSuccessEvaluationServicesLocal;
import com.softserve.persistence.DBEntities.NeuralNetwork;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "neuralNetworkCreationBean")
@RequestScoped
public class NeuralNetworkCreationBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private ApplicationSuccessEvaluationServicesLocal applicationSuccessEvaluationServicesLocal;
    
    private int inumberOfHiddenNeuronsPerLayer;
    
    private NeuralNetworkCreationRequest neuralNetworkCreationRequest;
    /**
     * Creates a new instance of NeuralNetworkCreationBean
     */
    public NeuralNetworkCreationBean() {
    }
    
   @PostConstruct
   public void init()
   {
       neuralNetworkCreationRequest = new NeuralNetworkCreationRequest();
   }

    public NeuralNetworkCreationRequest getNeuralNetworkCreationRequest() {
        return neuralNetworkCreationRequest;
    }

    public void setNeuralNetworkCreationRequest(NeuralNetworkCreationRequest neuralNetworkCreationRequest) {
        this.neuralNetworkCreationRequest = neuralNetworkCreationRequest;
    }

    public int getInumberOfHiddenNeuronsPerLayer() {
        return inumberOfHiddenNeuronsPerLayer;
    }

    public void setInumberOfHiddenNeuronsPerLayer(int inumberOfHiddenNeuronsPerLayer) {
        this.inumberOfHiddenNeuronsPerLayer = inumberOfHiddenNeuronsPerLayer;
    }
    
    
    public String preformNeuralNetworkCreationRequest()
    {        
        try
        {
            List<Integer> nums = new ArrayList<Integer>();
            
            for(int i = 0; i < neuralNetworkCreationRequest.getNumberOfHiddenLayers(); i++)
            {
                nums.add(inumberOfHiddenNeuronsPerLayer);
            }
            
            neuralNetworkCreationRequest.setNumberOfHiddenNeuronsPerLayer(nums);
            
            applicationSuccessEvaluationServicesLocal.createApplicationSucessNeuralNetwork(sessionManagerBean.getSession(), neuralNetworkCreationRequest);
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
