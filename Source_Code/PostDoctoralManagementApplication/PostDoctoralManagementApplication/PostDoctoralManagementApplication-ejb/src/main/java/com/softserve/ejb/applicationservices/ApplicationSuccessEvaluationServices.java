/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.persistence.DBEntities.Application;
import javax.ejb.Stateless;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class ApplicationSuccessEvaluationServices implements ApplicationSuccessEvaluationServicesLocal {

    @Override
    public Double getApplicationSuccessRating(Session session, Application application) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createApplicationSucessNeuralNetwork(Session session) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void trainApplicationSucessNeuralNetworkWithApplicationData() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
