/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBEnties;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "application")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a"),
    @NamedQuery(name = "Application.findByApplicationID", query = "SELECT a FROM Application a WHERE a.applicationID = :applicationID"),
    @NamedQuery(name = "Application.findByType", query = "SELECT a FROM Application a WHERE a.type = :type"),
    @NamedQuery(name = "Application.findByStatus", query = "SELECT a FROM Application a WHERE a.status = :status"),
    @NamedQuery(name = "Application.findByTimestamp", query = "SELECT a FROM Application a WHERE a.timestamp = :timestamp"),
    @NamedQuery(name = "Application.findByAwardDate", query = "SELECT a FROM Application a WHERE a.awardDate = :awardDate"),
    @NamedQuery(name = "Application.findByStartDate", query = "SELECT a FROM Application a WHERE a.startDate = :startDate"),
    @NamedQuery(name = "Application.findByEndDate", query = "SELECT a FROM Application a WHERE a.endDate = :endDate"),
    @NamedQuery(name = "Application.findByProjectTitle", query = "SELECT a FROM Application a WHERE a.projectTitle = :projectTitle")})
public class Application implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_applicationID")
    private Long applicationID;
    @Size(max = 7)
    @Column(name = "_type")
    private String type;
    @Size(max = 11)
    @Column(name = "_status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Column(name = "_awardDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date awardDate;
    @Column(name = "_startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "_endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Size(max = 250)
    @Column(name = "_projectTitle")
    private String projectTitle;
    @Lob
    @Size(max = 65535)
    @Column(name = "_information")
    private String information;
    @ManyToMany(mappedBy = "applicationCollection")
    private Collection<CommitteeMeeting> committeeMeetingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "applicationID")
    private Collection<RefereeReport> refereeReportCollection;
    @JoinColumn(name = "_fellow", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person fellow;
    @JoinColumn(name = "_grantHolderID", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person grantHolderID;
    @JoinColumn(name = "_locationID", referencedColumnName = "_locationID")
    @ManyToOne(optional = false)
    private Location locationID;
    @JoinColumn(name = "_endorsementID", referencedColumnName = "_endorsementID")
    @ManyToOne
    private Endorsement endorsementID;
    @JoinColumn(name = "_fundingReportID", referencedColumnName = "_reportID")
    @ManyToOne
    private FundingReport fundingReportID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "applicationID")
    private Collection<ProgressReport> progressReportCollection;

    public Application() {
    }

    public Application(Long applicationID) {
        this.applicationID = applicationID;
    }

    public Application(Long applicationID, Date timestamp) {
        this.applicationID = applicationID;
        this.timestamp = timestamp;
    }

    public Long getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @XmlTransient
    public Collection<CommitteeMeeting> getCommitteeMeetingCollection() {
        return committeeMeetingCollection;
    }

    public void setCommitteeMeetingCollection(Collection<CommitteeMeeting> committeeMeetingCollection) {
        this.committeeMeetingCollection = committeeMeetingCollection;
    }

    @XmlTransient
    public Collection<RefereeReport> getRefereeReportCollection() {
        return refereeReportCollection;
    }

    public void setRefereeReportCollection(Collection<RefereeReport> refereeReportCollection) {
        this.refereeReportCollection = refereeReportCollection;
    }

    public Person getFellow() {
        return fellow;
    }

    public void setFellow(Person fellow) {
        this.fellow = fellow;
    }

    public Person getGrantHolderID() {
        return grantHolderID;
    }

    public void setGrantHolderID(Person grantHolderID) {
        this.grantHolderID = grantHolderID;
    }

    public Location getLocationID() {
        return locationID;
    }

    public void setLocationID(Location locationID) {
        this.locationID = locationID;
    }

    public Endorsement getEndorsementID() {
        return endorsementID;
    }

    public void setEndorsementID(Endorsement endorsementID) {
        this.endorsementID = endorsementID;
    }

    public FundingReport getFundingReportID() {
        return fundingReportID;
    }

    public void setFundingReportID(FundingReport fundingReportID) {
        this.fundingReportID = fundingReportID;
    }

    @XmlTransient
    public Collection<ProgressReport> getProgressReportCollection() {
        return progressReportCollection;
    }

    public void setProgressReportCollection(Collection<ProgressReport> progressReportCollection) {
        this.progressReportCollection = progressReportCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (applicationID != null ? applicationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.applicationID == null && other.applicationID != null) || (this.applicationID != null && !this.applicationID.equals(other.applicationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEnties.Application[ applicationID=" + applicationID + " ]";
    }
    
}
