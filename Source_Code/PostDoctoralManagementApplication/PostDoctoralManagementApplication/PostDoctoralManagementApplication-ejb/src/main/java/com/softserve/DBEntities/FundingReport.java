/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBEntities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "FundingReport.findByTimestamp", query = "SELECT f FROM FundingReport f WHERE f.timestamp = :timestamp"),
    @NamedQuery(name = "FundingReport.findByFellowshipCost", query = "SELECT f FROM FundingReport f WHERE f.fellowshipCost = :fellowshipCost"),
    @NamedQuery(name = "FundingReport.findByTravelCost", query = "SELECT f FROM FundingReport f WHERE f.travelCost = :travelCost"),
    @NamedQuery(name = "FundingReport.findByRunningCost", query = "SELECT f FROM FundingReport f WHERE f.runningCost = :runningCost"),
    @NamedQuery(name = "FundingReport.findByOperatingCost", query = "SELECT f FROM FundingReport f WHERE f.operatingCost = :operatingCost"),
    @NamedQuery(name = "FundingReport.findByEquipmentCost", query = "SELECT f FROM FundingReport f WHERE f.equipmentCost = :equipmentCost"),
    @NamedQuery(name = "FundingReport.findByConferenceCost", query = "SELECT f FROM FundingReport f WHERE f.conferenceCost = :conferenceCost")})
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "_fellowshipCost")
    private Float fellowshipCost;
    @Column(name = "_travelCost")
    private Float travelCost;
    @Column(name = "_runningCost")
    private Float runningCost;
    @Column(name = "_operatingCost")
    private Float operatingCost;
    @Column(name = "_equipmentCost")
    private Float equipmentCost;
    @Column(name = "_conferenceCost")
    private Float conferenceCost;
    @JoinColumn(name = "_drisID", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person drisID;
    @OneToMany(mappedBy = "fundingReportID")
    private Collection<Application> applicationCollection;

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

    public Float getFellowshipCost() {
        return fellowshipCost;
    }

    public void setFellowshipCost(Float fellowshipCost) {
        this.fellowshipCost = fellowshipCost;
    }

    public Float getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(Float travelCost) {
        this.travelCost = travelCost;
    }

    public Float getRunningCost() {
        return runningCost;
    }

    public void setRunningCost(Float runningCost) {
        this.runningCost = runningCost;
    }

    public Float getOperatingCost() {
        return operatingCost;
    }

    public void setOperatingCost(Float operatingCost) {
        this.operatingCost = operatingCost;
    }

    public Float getEquipmentCost() {
        return equipmentCost;
    }

    public void setEquipmentCost(Float equipmentCost) {
        this.equipmentCost = equipmentCost;
    }

    public Float getConferenceCost() {
        return conferenceCost;
    }

    public void setConferenceCost(Float conferenceCost) {
        this.conferenceCost = conferenceCost;
    }

    public Person getDrisID() {
        return drisID;
    }

    public void setDrisID(Person drisID) {
        this.drisID = drisID;
    }

    @XmlTransient
    public Collection<Application> getApplicationCollection() {
        return applicationCollection;
    }

    public void setApplicationCollection(Collection<Application> applicationCollection) {
        this.applicationCollection = applicationCollection;
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
        return "com.softserve.DBEnties.FundingReport[ reportID=" + reportID + " ]";
    }
    
}
