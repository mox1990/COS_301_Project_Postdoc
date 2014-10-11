/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBEntities;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "synapse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Synapse.findAll", query = "SELECT s FROM Synapse s"),
    @NamedQuery(name = "Synapse.findBySynapseID", query = "SELECT s FROM Synapse s WHERE s.synapseID = :synapseID"),
    @NamedQuery(name = "Synapse.findByWeight", query = "SELECT s FROM Synapse s WHERE s.weight = :weight"),
    @NamedQuery(name = "Synapse.findByPreviousWeightChange", query = "SELECT s FROM Synapse s WHERE s.previousWeightChange = :previousWeightChange")})
public class Synapse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_synapseID")
    private Long synapseID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "_weight")
    private Double weight;
    @Column(name = "_previousWeightChange")
    private Double previousWeightChange;
    @JoinColumn(name = "_neuralnetwork", referencedColumnName = "_neuralnetworkID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private NeuralNetwork neuralnetwork;
    @JoinColumn(name = "_srcNeuron", referencedColumnName = "_neuronID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Neuron srcNeuron;
    @JoinColumn(name = "_destNeuron", referencedColumnName = "_neuronID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Neuron destNeuron;

    public Synapse() {
    }

    public Synapse(Long synapseID) {
        this.synapseID = synapseID;
    }

    public Long getSynapseID() {
        return synapseID;
    }

    public void setSynapseID(Long synapseID) {
        this.synapseID = synapseID;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPreviousWeightChange() {
        return previousWeightChange;
    }

    public void setPreviousWeightChange(Double previousWeightChange) {
        this.previousWeightChange = previousWeightChange;
    }

    public NeuralNetwork getNeuralnetwork() {
        return neuralnetwork;
    }

    public void setNeuralnetwork(NeuralNetwork neuralnetwork) {
        this.neuralnetwork = neuralnetwork;
    }

    public Neuron getSrcNeuron() {
        return srcNeuron;
    }

    public void setSrcNeuron(Neuron srcNeuron) {
        this.srcNeuron = srcNeuron;
    }

    public Neuron getDestNeuron() {
        return destNeuron;
    }

    public void setDestNeuron(Neuron destNeuron) {
        this.destNeuron = destNeuron;
    }

    public void initialiseSynapse() throws Exception
    { 
        initialiseSynapse(getDestNeuron().getSynapseList1().size());
    }
    
    /**
     *This randomly intialises the synapse weight to a value between -1/sqrt(fanmin) and -1/sqrt(fanmin). Where fanmin is the number of synapse leading to the neuron
     * @param count
     * @throws java.lang.Exception
     */
    public void initialiseSynapse(int count) throws Exception
    {   
        Double min = (1.0 / Math.sqrt(count));
        Double max = (1.0 / Math.sqrt(count)) * 2;
        
        weight = (max * Math.random()) - min;
    }
    
    /**
     * This function adjusts the weight according to the error signal of its destination neuron and the source neuron's value
     * @param learningRate
     * @param momentum
     */
    public void adjustWeights(double learningRate, double momentum)
    {
        Double curWeightChange = (-1 * learningRate * (getDestNeuron().getError() * getSrcNeuron().getValue())) + (momentum * (previousWeightChange) );
        
        weight += (-1 * learningRate * (getDestNeuron().getError() * getSrcNeuron().getValue())) + (momentum * (previousWeightChange) ); 
        
        previousWeightChange = curWeightChange;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (synapseID != null ? synapseID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Synapse)) {
            return false;
        }
        Synapse other = (Synapse) object;
        if ((this.synapseID == null && other.synapseID != null) || (this.synapseID != null && !this.synapseID.equals(other.synapseID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.persistence.DBEntities.Synapse[ synapseID=" + synapseID + " ]";
    }
    
}
