/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "funding_report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FundingReport.findAll", query = "SELECT f FROM FundingReport f"),
    @NamedQuery(name = "FundingReport.findByReportID", query = "SELECT f FROM FundingReport f WHERE f.reportID = :reportID"),
    @NamedQuery(name = "FundingReport.findByTimestamp", query = "SELECT f FROM FundingReport f WHERE f.timestamp = :timestamp")})
public class FundingReport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "_reportID")
    private Long reportID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fundingReport")
    private List<FundingCost> fundingCostList;
    @JoinColumn(name = "_reportID", referencedColumnName = "_applicationID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Application application;
    @JoinColumn(name = "_dris", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person dris;

    public FundingReport() {
    }

    public FundingReport(Long reportID) {
        this.reportID = reportID;
    }

    public FundingReport(Long reportID, Date timestamp) {
        this.reportID = reportID;
        this.timestamp = timestamp;
    }

    public Long getReportID() {
        return reportID;
    }

    public void setReportID(Long reportID) {
        this.reportID = reportID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @XmlTransient
    public List<FundingCost> getFundingCostList() {
        return fundingCostList;
    }

    public void setFundingCostList(List<FundingCost> fundingCostList) {
        this.fundingCostList = fundingCostList;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Person getDris() {
        return dris;
    }

    public void setDris(Person dris) {
        this.dris = dris;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportID != null ? reportID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FundingReport)) {
            return false;
        }
        FundingReport other = (FundingReport) object;
        if ((this.reportID == null && other.reportID != null) || (this.reportID != null && !this.reportID.equals(other.reportID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.FundingReport[ reportID=" + reportID + " ]";
    }
    
}
