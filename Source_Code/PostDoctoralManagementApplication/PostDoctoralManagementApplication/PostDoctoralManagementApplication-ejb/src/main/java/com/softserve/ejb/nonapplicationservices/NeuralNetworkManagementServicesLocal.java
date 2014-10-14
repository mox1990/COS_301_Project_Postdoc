/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.nonapplicationservices;

import com.softserve.auxiliary.requestresponseclasses.NeuralNetworkCreationRequest;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.persistence.DBEntities.NeuralNetwork;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface NeuralNetworkManagementServicesLocal {    
    public List<NeuralNetwork> loadAllNeuralNetworks(Session session) throws Exception;
    public NeuralNetwork getDefaultNeuralNetwork(Session session) throws Exception;
    public void createNeuralNetwork(Session session, NeuralNetworkCreationRequest neuralNetworkCreationRequest) throws Exception;
    public void makeNeuralNetworkDefaultNetwork(Session session, NeuralNetwork neuralnetwork) throws Exception;
    public void updateNeuralNetwork(Session session, NeuralNetwork neuralNetwork) throws Exception;
    public void updateNeuralNetworkSynapses(Session session, NeuralNetwork neuralNetwork) throws Exception;
    public void removeNeuralNetwork(Session session, NeuralNetwork neuralNetwork) throws Exception;
    public void runNeuralNetwork(Session session, NeuralNetwork neuralNetwork, List<Double> inputVector) throws Exception;
    public void correctNeuralNetwork(Session session, NeuralNetwork neuralNetwork, List<Double> targetVector) throws Exception;
    public List<Double> trainNeuralNetwork(Session session, NeuralNetwork neuralNetwork, List<List<Double>> inputVectorSet, List<List<Double>> targetVectorSet, int noOfEpochs) throws Exception;    
}
