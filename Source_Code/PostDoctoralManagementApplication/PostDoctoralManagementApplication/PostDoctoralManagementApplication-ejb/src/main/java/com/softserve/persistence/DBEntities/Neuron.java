/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBEntities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "neuron")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Neuron.findAll", query = "SELECT n FROM Neuron n"),
    @NamedQuery(name = "Neuron.findByNeuronID", query = "SELECT n FROM Neuron n WHERE n.neuronID = :neuronID"),
    @NamedQuery(name = "Neuron.findByNeuronOrderIndex", query = "SELECT n FROM Neuron n WHERE n.neuronOrderIndex = :neuronOrderIndex"),
    @NamedQuery(name = "Neuron.findByValue", query = "SELECT n FROM Neuron n WHERE n.value = :value"),
    @NamedQuery(name = "Neuron.findByError", query = "SELECT n FROM Neuron n WHERE n.error = :error"),
    @NamedQuery(name = "Neuron.findByBiasNeuron", query = "SELECT n FROM Neuron n WHERE n.biasNeuron = :biasNeuron")})
public class Neuron implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_neuronID")
    private Long neuronID;
    @Column(name = "_neuronOrderIndex")
    private BigInteger neuronOrderIndex;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "_value")
    private Double value;
    @Column(name = "_error")
    private Double error;
    @Column(name = "_biasNeuron")
    private Boolean biasNeuron;
    @JoinColumn(name = "_neuralnetwork", referencedColumnName = "_neuralnetworkID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private NeuralNetwork neuralnetwork;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "srcNeuron", fetch = FetchType.EAGER)
    private List<Synapse> synapseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destNeuron", fetch = FetchType.EAGER)
    private List<Synapse> synapseList1;

    public Neuron() {
    }

    public Neuron(Long neuronID) {
        this.neuronID = neuronID;
    }

    public Long getNeuronID() {
        return neuronID;
    }

    public void setNeuronID(Long neuronID) {
        this.neuronID = neuronID;
    }

    public BigInteger getNeuronOrderIndex() {
        return neuronOrderIndex;
    }

    public void setNeuronOrderIndex(BigInteger neuronOrderIndex) {
        this.neuronOrderIndex = neuronOrderIndex;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }

    public Boolean getBiasNeuron() {
        return biasNeuron;
    }

    public void setBiasNeuron(Boolean biasNeuron) {
        this.biasNeuron = biasNeuron;
    }

    public NeuralNetwork getNeuralnetwork() {
        return neuralnetwork;
    }

    public void setNeuralnetwork(NeuralNetwork neuralnetwork) {
        this.neuralnetwork = neuralnetwork;
    }

    @XmlTransient
    public List<Synapse> getSynapseList() {
        return synapseList;
    }

    public void setSynapseList(List<Synapse> synapseList) {
        this.synapseList = synapseList;
    }

    @XmlTransient
    public List<Synapse> getSynapseList1() {
        return synapseList1;
    }

    public void setSynapseList1(List<Synapse> synapseList1) {
        this.synapseList1 = synapseList1;
    }

    public List<Neuron> getAllDestinationNeurons()
    {
        List<Neuron> neurons = new ArrayList<Neuron>();
        
        for(Synapse synapse : synapseList)
        {
            neurons.add(synapse.getDestNeuron());
        }
        
        return neurons;
    }
    
    public List<Neuron> getAllSourceNeurons()
    {
        List<Neuron> neurons = new ArrayList<Neuron>();
        
        for(Synapse synapse : synapseList1)
        {
            neurons.add(synapse.getSrcNeuron());
        }
        
        return neurons;
    }
    
    public Synapse getBiasSynapseForNeuron()
    {        
        for(Synapse synapse : synapseList1)
        {
            if(synapse.getSrcNeuron().getBiasNeuron())
            {
                return synapse;
            }
        }
        
        return null;
    }
    
    /**
     * This randomly intialises all the input synapse weights
     * @throws ExceptionMessage
     */
    public void initialiseAllInputSynapses() throws Exception
    {
        for(Synapse synapse : synapseList1)
        {
            synapse.initialiseSynapse();
        }
    }
    
    public void calculateNodeValue() throws Exception
    {
        calculateNodeValue(neuralnetwork.getSmoothingParameterT());
    }
    
    /**
     * This function gets is used to calculate the activation of the neuron  
     * @param T Smoothing parameter for sigmoid activation function 0 = discrete jumps, 1 = smooth f(x) = (0,1), > 1 = elongated
     * @throws ExceptionMessage
     */
    public void calculateNodeValue(Double T) throws Exception
    {        
        if(getSynapseList1().isEmpty())
        {
            throw new Exception("Cannot calculate nueron value since no input synapses specified");
            
        }        
        
        value = calculateActivationValueOfNeuron(calculateWeightedSumOfInputs(),getThreshold(),T);
    }
    
    //This function retrives the the treshold value which is the last input synapse weight * its connected neurons values (the bias value) 
    private Double getThreshold() throws Exception
    {   
        Synapse biasSynapse = getBiasSynapseForNeuron();
        if(biasSynapse != null)
        {            
            return biasSynapse.getWeight() * biasSynapse.getSrcNeuron().getValue();
        }
        else
        {
            throw new Exception("Cannot calculate threshold for nueron since no input synapse specified");
        }        
    }
    
    //This function calculates all the weighted sum of all the input neurons and their related synapse
    private Double calculateWeightedSumOfInputs()
    {               
        Double result = 0.0;
        
        for(Synapse synapse : getSynapseList1())
        { 
            result += synapse.getWeight() * synapse.getSrcNeuron().getValue();
        }        
        
        return result;
    }
    
    //This funcation calculates the activation value using the sigmoid function. 1 / [ 1 + e^( -( (x - theta) / smoothing parameter T ) ) ]
    private Double calculateActivationValueOfNeuron(Double inputWeightedSum, Double threshold, Double T) throws Exception
    {
        if(T == 0)
        {
            throw new Exception("T cannot be zero.");
        }
        
        Double result = 0.0;
        
        result = ( -1.0 * ( ( (inputWeightedSum - threshold) / T) ) );
        
        result = 1.0 / (1 + Math.exp(result));

        return result;
    }
    
    public Double getActualOutputValue()
    {
        return getActualOutputValue(neuralnetwork.getLowerCertaintyBound(), neuralnetwork.getUpperCertaintyBound());
    }
    
    /**
     * THis returns the actual value of the neuron based on the certainty bounds
     * @param lower
     * @param upper
     * @return
     */
    public Double getActualOutputValue(Double lower, Double upper)
    {
        if(value >= lower)
        {
            return 1.0;
        }
        else if (value <= upper)
        {
            return 0.0;
        }
        else
        {
            return value;
        }
    }
    
    /**
     *  This funcation is used to calculated the output nodes error signal
     * @param target
     */
    public void calculateErrorSignalOfOutputNeuron(Double target)
    {        
        error = (-1 * (target - value) * (1 - value) ) * value; 
    }
    
    /**
     * This function is used to calculate the nodes that are not output nodes error signal based on the error signal contribution of the layer in front of it
     */
    public void calculateErrorSignalOfNonOutputNeuron()
    {
        Double result = 0.0;
        
        for(Synapse synapse : synapseList)
        {
            result += synapse.getDestNeuron().getError() * synapse.getWeight() * (1 - value) * value;
        }     
        
        error = result;
    }
    
    //This calculates the weighted sum of the error signals from the output synapse neurons
    private Double calculateWeightedSumOfErrorSignalFromOutputs()
    {
        Double result = 0.0;
        
        for(Synapse synapse : synapseList)
        {
            result += synapse.getWeight() * synapse.getDestNeuron().getError();
        }        
        
        return result;
    }
    
    public void adjustWeightsOfInputSynapses()
    {
        adjustWeightsOfInputSynapses(neuralnetwork.getLearningRate(), neuralnetwork.getMomentum());
    }
    
    /**
     * This function adjusts all the weights of the input synapses
     * @param learningRate
     * @param momentum
     */
    public void adjustWeightsOfInputSynapses(double learningRate, double momentum)
    {        
        for(Synapse synapse : synapseList1)
        {
            synapse.adjustWeights(learningRate, momentum);
        }
    }

    public String neuronToString()
    {
       return "Value: " + Double.toString(value) + " Error: " + Double.toString(error);  
    }
    
    public String inputSynapsesToString()
    {
        String result = "";
        
        for(int i = 0; i < getSynapseList1().size(); i++)
        {
            result += getSynapseList1().get(i).toString() + " ";
        }        
        
        return result;
    }

    public String outputSynapsesToString()
    {
        String result = "";
        
        for(int i = 0; i < getSynapseList().size(); i++)
        {
            result += getSynapseList().get(i).toString() + " ";
        }        
        
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (neuronID != null ? neuronID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Neuron)) {
            return false;
        }
        Neuron other = (Neuron) object;
        if ((this.neuronID == null && other.neuronID != null) || (this.neuronID != null && !this.neuronID.equals(other.neuronID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.persistence.DBEntities.Neuron[ neuronID=" + neuronID + " ]";
    }
    
}
