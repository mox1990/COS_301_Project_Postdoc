/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBEntities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "neural_network")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NeuralNetwork.findAll", query = "SELECT n FROM NeuralNetwork n"),
    @NamedQuery(name = "NeuralNetwork.findByNeuralnetworkID", query = "SELECT n FROM NeuralNetwork n WHERE n.neuralnetworkID = :neuralnetworkID"),
    @NamedQuery(name = "NeuralNetwork.findByTimestamp", query = "SELECT n FROM NeuralNetwork n WHERE n.timestamp = :timestamp"),
    @NamedQuery(name = "NeuralNetwork.findByDefaultNetwork", query = "SELECT n FROM NeuralNetwork n WHERE n.defaultNetwork = :defaultNetwork"),
    @NamedQuery(name = "NeuralNetwork.findByName", query = "SELECT n FROM NeuralNetwork n WHERE n.name = :name"),
    @NamedQuery(name = "NeuralNetwork.findByType", query = "SELECT n FROM NeuralNetwork n WHERE n.type = :type"),
    @NamedQuery(name = "NeuralNetwork.findByLearningRate", query = "SELECT n FROM NeuralNetwork n WHERE n.learningRate = :learningRate"),
    @NamedQuery(name = "NeuralNetwork.findByMomentum", query = "SELECT n FROM NeuralNetwork n WHERE n.momentum = :momentum"),
    @NamedQuery(name = "NeuralNetwork.findByBiasThreshold", query = "SELECT n FROM NeuralNetwork n WHERE n.biasThreshold = :biasThreshold"),
    @NamedQuery(name = "NeuralNetwork.findBySmoothingParameterT", query = "SELECT n FROM NeuralNetwork n WHERE n.smoothingParameterT = :smoothingParameterT"),
    @NamedQuery(name = "NeuralNetwork.findByLowerCertaintyBound", query = "SELECT n FROM NeuralNetwork n WHERE n.lowerCertaintyBound = :lowerCertaintyBound"),
    @NamedQuery(name = "NeuralNetwork.findByUpperCertaintyBound", query = "SELECT n FROM NeuralNetwork n WHERE n.upperCertaintyBound = :upperCertaintyBound")})
public class NeuralNetwork implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_neuralnetworkID")
    private Long neuralnetworkID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Column(name = "_defaultNetwork")
    private Boolean defaultNetwork;
    @Size(max = 100)
    @Column(name = "_name")
    private String name;
    @Size(max = 100)
    @Column(name = "_type")
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "_learningRate")
    private Double learningRate;
    @Column(name = "_momentum")
    private Double momentum;
    @Column(name = "_bias_threshold")
    private Double biasThreshold;
    @Column(name = "_smoothingParameterT")
    private Double smoothingParameterT;
    @Column(name = "_lowerCertaintyBound")
    private Double lowerCertaintyBound;
    @Column(name = "_upperCertaintyBound")
    private Double upperCertaintyBound;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "neuralnetwork", fetch = FetchType.EAGER)
    private List<Neuron> neuronList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "neuralnetwork", fetch = FetchType.EAGER)
    private List<Synapse> synapseList;

    public NeuralNetwork() {
    }

    public NeuralNetwork(Long neuralnetworkID) {
        this.neuralnetworkID = neuralnetworkID;
    }

    public NeuralNetwork(Long neuralnetworkID, Date timestamp) {
        this.neuralnetworkID = neuralnetworkID;
        this.timestamp = timestamp;
    }

    public Long getNeuralnetworkID() {
        return neuralnetworkID;
    }

    public void setNeuralnetworkID(Long neuralnetworkID) {
        this.neuralnetworkID = neuralnetworkID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getDefaultNetwork() {
        return defaultNetwork;
    }

    public void setDefaultNetwork(Boolean defaultNetwork) {
        this.defaultNetwork = defaultNetwork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Double getBiasThreshold() {
        return biasThreshold;
    }

    public void setBiasThreshold(Double biasThreshold) {
        this.biasThreshold = biasThreshold;
    }

    public Double getSmoothingParameterT() {
        return smoothingParameterT;
    }

    public void setSmoothingParameterT(Double smoothingParameterT) {
        this.smoothingParameterT = smoothingParameterT;
    }

    public Double getLowerCertaintyBound() {
        return lowerCertaintyBound;
    }

    public void setLowerCertaintyBound(Double lowerCertaintyBound) {
        this.lowerCertaintyBound = lowerCertaintyBound;
    }

    public Double getUpperCertaintyBound() {
        return upperCertaintyBound;
    }

    public void setUpperCertaintyBound(Double upperCertaintyBound) {
        this.upperCertaintyBound = upperCertaintyBound;
    }

    @XmlTransient
    public List<Neuron> getNeuronList() {
        return neuronList;
    }

    public void setNeuronList(List<Neuron> neuronList) {
        this.neuronList = neuronList;
    }

    @XmlTransient
    public List<Synapse> getSynapseList() {
        return synapseList;
    }

    public void setSynapseList(List<Synapse> synapseList) {
        this.synapseList = synapseList;
    }
    
    public List<Neuron> getAllBiasNeurons()
    {
        List<Neuron> biasNeurons = new ArrayList<Neuron>();
        
        for(Neuron neuron : getNeuronList())
        {
            if(neuron.getBiasNeuron() == true)
            {
                biasNeurons.add(neuron);
            }
        }
        
        return biasNeurons; 
    }
    
    public List<Neuron> getAllInputNeurons()
    {
        List<Neuron> inputNeurons = new ArrayList<Neuron>();
        
        for(Neuron neuron : getNeuronList())
        {
            if(neuron.getBiasNeuron() == false && neuron.getSynapseList1().isEmpty())
            {
                inputNeurons.add(neuron);
            }
        }
        
        return sortNeuronListByOrderIndexAscending(inputNeurons); 
    }
    
    public List<Neuron> getAllOutputNeurons()
    {
        List<Neuron> outputNeurons = new ArrayList<Neuron>();
        
        for(Neuron neuron : getNeuronList())
        {
            if(neuron.getBiasNeuron() == false && neuron.getSynapseList().isEmpty())
            {
                outputNeurons.add(neuron);
            }
        }
        
        return sortNeuronListByOrderIndexAscending(outputNeurons); 
    }
    
    public List<Neuron> sortNeuronListByOrderIndexAscending(List<Neuron> list)
    {
        List<Neuron> newList = new ArrayList<Neuron>();
        newList.addAll(list);        
        
        for(int i = 0; i < list.size() - 1; i++)
        {
            for(int j = i + 1; j < list.size(); j++)
            {
                if(newList.get(j).getNeuronOrderIndex().compareTo(newList.get(i).getNeuronOrderIndex()) == 1)
                {
                    Neuron temp = newList.get(i);
                    newList.set(i, newList.get(j));
                    newList.set(j, temp);
                }
            }
        }
        
        return newList;
    }
    
    public int getNumberOfNeuronLayers()
    {
        int count = 0;
        List<Neuron> inputs = getAllInputNeurons();
        
        if(!inputs.isEmpty())
        {
            Neuron neuron = inputs.get(0);        
            count = 1;
            
            while(!neuron.getSynapseList().isEmpty())
            {
                count++;
                neuron = neuron.getSynapseList().get(0).getDestNeuron();
            }
        }
        
        return count;
    }
    
    public List<Neuron> getNeuronLayerN(int N)
    {        
        int count = 0;
        List<Neuron> inputs = getAllInputNeurons();
        
        if(!inputs.isEmpty())
        {
            Neuron neuron = inputs.get(0);        
            count = 1;
            
            if(count == N)
            {
                return inputs;
            }
            
            while(!neuron.getSynapseList().isEmpty())
            {
                count++;
                neuron = neuron.getSynapseList().get(0).getDestNeuron();
            }
        }
        
        return null;
    }
    
    
    /**
     *
     */
    public void initaliseNetwork() throws Exception
    {
        List<Neuron> inputNeurons = getAllInputNeurons();
        
        if(inputNeurons.isEmpty())
        {
            throw  new Exception("Unable to initialise network since no input nodes");
        }
        
        //Set bias unit for all bias neurons
        for(Neuron neuron : getAllBiasNeurons())
        {
            neuron.setValue(biasThreshold);
        }
        
        //Initialise synapses
        //Starts at 2nd layer as first does not have input
        List<Neuron> curNeuronLayer = getAllInputNeurons().get(0).getAllDestinationNeurons();
        
        while(curNeuronLayer != null && !curNeuronLayer.isEmpty())
        {
            for(Neuron neuron : curNeuronLayer)
            {
                neuron.initialiseAllInputSynapses();
            }
            
            curNeuronLayer = curNeuronLayer.get(0).getAllDestinationNeurons();
        }        
    }

    /**
     * This function is used to set the input values of the neural networks
     * @param inputVec
     * @throws ExceptionMessage
     */
    public void setInputNeuronValues(List<Double> inputVector) throws Exception
    {
        List<Neuron> inputNeurons = getAllInputNeurons();
        
        if(inputNeurons.size() - 1 != inputVector.size())
        {
            throw new Exception("Neural network: Input vector size " + inputVector.size() + "  does not match that of the input layer size " + inputNeurons.size());
        }
        
        for(int i = 0; i < inputVector.size(); i++)
        {
            inputNeurons.get(i).setValue(inputVector.get(i));
        }
    }
    
    /**
     *This checks if the values of the out put nodes macth that of the target vector with regards to the certainty bounds
     * @param targetVector
     * @return
     * @throws ExceptionMessage
     */
    public boolean isOutputCorrect(List<Double> targetVector) throws Exception
    {        
        List<Neuron> outputNeurons = getAllOutputNeurons();
        
        boolean correct = true;

        for(int i = 0; i < outputNeurons.size(); i++)
        {            
            if(!outputNeurons.get(i).getActualOutputValue().equals(targetVector.get(i)))
            {
                correct = false;
            }
        }
        
        return correct;
    }
    
    public void feedForward() throws Exception
    {
        feedForward(smoothingParameterT);
    }
    
    public void feedForward(List<Double> inputVector) throws Exception
    {
        setInputNeuronValues(inputVector);
        feedForward(smoothingParameterT);
    }    
    
    /**
     * THis sets up the input values and feeds them forward through the network
     * @param inputVec
     * @param inputVector
     * @param T
     * @throws ExceptionMessage
     */
    public void feedForward(Double T, List<Double> inputVector) throws Exception
    {
        setInputNeuronValues(inputVector);
        feedForward(T);
    }

    /**
     * This funcation feeds the input node values forward until the output nodes
     * @param T
     * @throws ExceptionMessage
     */
    public void feedForward(Double T) throws Exception
    {
        List<Neuron> inputNeurons = getAllInputNeurons();
        
        if(inputNeurons.isEmpty())
        {
            throw  new Exception("Unable to feed forward since no input nodes");
        }
        
        //Calculates all neuron values
        //Starts at 2nd layer as first is input
        List<Neuron> curNeuronLayer = getAllInputNeurons().get(0).getAllDestinationNeurons();
        
        while(curNeuronLayer != null && !curNeuronLayer.isEmpty())
        {
            for(Neuron neuron : curNeuronLayer)
            {
                neuron.calculateNodeValue(smoothingParameterT);
            }
            
            curNeuronLayer = curNeuronLayer.get(0).getAllDestinationNeurons();
        }
    }
    
    /**
     *This funcation back propagates error values from the output nodes and adjusts the weights accordingly using the specified parameters below.
     * @param momentum
     * @param learningRate
     * @param targetVector
     * @return
     * @throws ExceptionMessage
     */
    public void backPropagate(List<Double> targetVector) throws Exception
    {
        List<Neuron> outputNeurons = getAllOutputNeurons();
        
        if(outputNeurons.isEmpty())
        {
            throw  new Exception("Unable to back propagate since no output neurons");
        }
        
        boolean correct = true;

        //Calculate output layer error signals
        for(int i = 0; i < outputNeurons.size(); i++)
        {
            outputNeurons.get(i).calculateErrorSignalOfOutputNeuron(targetVector.get(i));
        }
        
        //Calculate non-output layer error signals
        //Starts at N - 1 layer as last is output and Output already calculated
        List<Neuron> curNeuronLayer = outputNeurons.get(0).getAllSourceNeurons();
        
        while(curNeuronLayer != null && !curNeuronLayer.isEmpty())
        {
            for(Neuron neuron : curNeuronLayer)
            {
                //Avoid bais unit as no inputs
                if(!neuron.getBiasNeuron())
                {
                    neuron.calculateErrorSignalOfNonOutputNeuron();
                }
            }
            
            curNeuronLayer = curNeuronLayer.get(0).getAllSourceNeurons();
        }        
        
        //Adust weights
        //Starts at N - 1 layer as last is output and Output already calculated
        curNeuronLayer = outputNeurons;
        
        while(curNeuronLayer != null && !curNeuronLayer.isEmpty())
        {
            for(Neuron neuron : curNeuronLayer)
            {
                //Avoid bais unit as no inputs
                if(!neuron.getBiasNeuron())
                {
                    neuron.adjustWeightsOfInputSynapses(learningRate, momentum);
                }
            }
            
            curNeuronLayer = curNeuronLayer.get(0).getAllSourceNeurons();
        }                               
    }
        
    /**
     *This function is used to capture the black box of the neural network to a string
     * @return
     */
    public String blackBoxToString()
    {
        String temp = "Bais value: " + biasThreshold + "\nNeurons: \n   Input Layer: \n     ";
        
        temp += getAllInputNeurons().toString() + "\n";
        
        for(int i = 0; i < getNumberOfNeuronLayers() - 2; i++)
        {
            temp += "   Hidden layer " + i + ":\n       " + getNeuronLayerN(i + 1).toString() + "\n";
        }
        
        temp += "   Output layer: \n        ";
        temp += getAllOutputNeurons().toString() + "\n\n";
        
        temp += "Synapses:\n Input layer: \n        ";
        
        for(Neuron neuron : getAllInputNeurons())
        {
            temp += neuron.getSynapseList1().toString();
        }
        
        for(int i = 0; i < getNumberOfNeuronLayers() - 2; i++)
        {
            temp += "   Hidden layer " + i + ":\n";
            for(Neuron neuron : getNeuronLayerN(i + 1))
            {
                temp += "       " + neuron.getSynapseList1().toString() + "\n";
            }
        }
        
        return temp;  
    }        

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (neuralnetworkID != null ? neuralnetworkID.hashCode() : 0);
        return hash;
    }
    
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NeuralNetwork)) {
            return false;
        }
        NeuralNetwork other = (NeuralNetwork) object;
        if ((this.neuralnetworkID == null && other.neuralnetworkID != null) || (this.neuralnetworkID != null && !this.neuralnetworkID.equals(other.neuralnetworkID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.persistence.DBEntities.NeuralNetwork[ neuralnetworkID=" + neuralnetworkID + " ]";
    }
    
}
