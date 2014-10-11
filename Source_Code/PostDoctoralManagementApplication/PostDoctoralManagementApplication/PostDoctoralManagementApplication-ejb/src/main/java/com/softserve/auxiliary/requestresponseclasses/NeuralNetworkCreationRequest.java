/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.requestresponseclasses;

import java.util.List;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NeuralNetworkCreationRequest {
    private int numberOfInputNeurons;
    private int numberOfHiddenLayers;
    private List<Integer> numberOfHiddenNeuronsPerLayer;
    private int numberOfOutputNeurons;
    private Double biasValue;
    private Double learningRate;
    private Double momentum; 
    private Double lowerCertaintyBound; 
    private Double upperCertiantyBound;
    private String type;
    private String name;
    private Double smoothingParameter;

    public NeuralNetworkCreationRequest() {
    }

    public Double getSmoothingParameter() {
        return smoothingParameter;
    }

    public void setSmoothingParameter(Double smoothingParameter) {
        this.smoothingParameter = smoothingParameter;
    }

    
        
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(Double learningRate) {
        this.learningRate = learningRate;
    }

    public Double getMomentum() {
        return momentum;
    }

    public void setMomentum(Double momentum) {
        this.momentum = momentum;
    }
            
    public Double getBiasValue() {
        return biasValue;
    }

    public void setBiasValue(Double biasValue) {
        this.biasValue = biasValue;
    }

    public Double getLowerCertaintyBound() {
        return lowerCertaintyBound;
    }

    public void setLowerCertaintyBound(Double lowerCertaintyBound) {
        this.lowerCertaintyBound = lowerCertaintyBound;
    }

    public int getNumberOfHiddenLayers() {
        return numberOfHiddenLayers;
    }

    public void setNumberOfHiddenLayers(int numberOfHiddenLayers) {
        this.numberOfHiddenLayers = numberOfHiddenLayers;
    }

    public List<Integer> getNumberOfHiddenNeuronsPerLayer() {
        return numberOfHiddenNeuronsPerLayer;
    }

    public void setNumberOfHiddenNeuronsPerLayer(List<Integer> numberOfHiddenNeuronsPerLayer) {
        this.numberOfHiddenNeuronsPerLayer = numberOfHiddenNeuronsPerLayer;
    }

    public int getNumberOfInputNeurons() {
        return numberOfInputNeurons;
    }

    public void setNumberOfInputNeurons(int numberOfInputNeurons) {
        this.numberOfInputNeurons = numberOfInputNeurons;
    }

    public int getNumberOfOutputNeurons() {
        return numberOfOutputNeurons;
    }

    public void setNumberOfOutputNeurons(int numberOfOutputNeurons) {
        this.numberOfOutputNeurons = numberOfOutputNeurons;
    }

    public Double getUpperCertiantyBound() {
        return upperCertiantyBound;
    }

    public void setUpperCertiantyBound(Double upperCertiantyBound) {
        this.upperCertiantyBound = upperCertiantyBound;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }        
}
