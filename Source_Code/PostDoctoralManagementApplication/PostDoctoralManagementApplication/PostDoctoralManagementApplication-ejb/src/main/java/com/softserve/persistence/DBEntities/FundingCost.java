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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "funding_cost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FundingCost.findAll", query = "SELECT f FROM FundingCost f"),
    @NamedQuery(name = "FundingCost.findByCostID", query = "SELECT f FROM FundingCost f WHERE f.costID = :costID"),
    @NamedQuery(name = "FundingCost.findByAmount", query = "SELECT f FROM FundingCost f WHERE f.amount = :amount"),
    @NamedQuery(name = "FundingCost.findByProvider", query = "SELECT f FROM FundingCost f WHERE f.provider = :provider"),
    @NamedQuery(name = "FundingCost.findByType", query = "SELECT f FROM FundingCost f WHERE f.type = :type")})
public class FundingCost implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_costID")
    private Long costID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "_amount")
    private Float amount;
    @Size(max = 100)
    @Column(name = "_provider")
    private String provider;
    @Size(max = 40)
    @Column(name = "_type")
    private String type;
    @JoinColumn(name = "_fundingReport", referencedColumnName = "_reportID")
    @ManyToOne(optional = false)
    private FundingReport fundingReport;

    public FundingCost() {
    }

    public FundingCost(Long costID) {
        this.costID = costID;
    }

    public Long getCostID() {
        return costID;
    }

    public void setCostID(Long costID) {
        this.costID = costID;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FundingReport getFundingReport() {
        return fundingReport;
    }

    public void setFundingReport(FundingReport fundingReport) {
        this.fundingReport = fundingReport;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (costID != null ? costID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FundingCost)) {
            return false;
        }
        FundingCost other = (FundingCost) object;
        if ((this.costID == null && other.costID != null) || (this.costID != null && !this.costID.equals(other.costID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.FundingCost[ costID=" + costID + " ]";
    }
    
}
