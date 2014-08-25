/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import auto.softserve.XMLEntities.application.ApplicationInformation;
import com.softserve.XMLUtils.XMLMarshaller;
import com.softserve.XMLUtils.XMLUnmarshaller;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.JAXBException;
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
    @NamedQuery(name = "Application.findByFundingType", query = "SELECT a FROM Application a WHERE a.fundingType = :fundingType"),
    @NamedQuery(name = "Application.findByTimestamp", query = "SELECT a FROM Application a WHERE a.timestamp = :timestamp"),
    @NamedQuery(name = "Application.findBySubmissionDate", query = "SELECT a FROM Application a WHERE a.submissionDate = :submissionDate"),
    @NamedQuery(name = "Application.findByFinalisationDate", query = "SELECT a FROM Application a WHERE a.finalisationDate = :finalisationDate"),
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
    @Size(max = 17)
    @Column(name = "_fundingType")
    private String fundingType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Column(name = "_submissionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submissionDate;
    @Column(name = "_finalisationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finalisationDate;
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
    @ManyToMany(mappedBy = "applicationList")
    private List<Person> personList;
    @ManyToMany(mappedBy = "applicationList")
    private List<CommitteeMeeting> committeeMeetingList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "application")
    private DeclineReport declineReport;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "application")
    private Endorsement endorsement;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "application1")
    private List<ApplicationReviewRequest> applicationReviewRequestList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "applicationID")
    private List<RefereeReport> refereeReportList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "application")
    private List<AmmendRequest> ammendRequestList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "application")
    private RecommendationReport recommendationReport;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "application")
    private FundingReport fundingReport;
    @JoinColumn(name = "_fellow", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person fellow;
    @JoinColumn(name = "_grantHolder", referencedColumnName = "_systemID")
    @ManyToOne
    private Person grantHolder;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "application")
    private List<ProgressReport> progressReportList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "application")
    private EligiblityReport eligiblityReport;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "application")
    private List<ForwardAndRewindReport> forwardAndRewindReportList;

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

    public String getFundingType() {
        return fundingType;
    }

    public void setFundingType(String fundingType) {
        this.fundingType = fundingType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Date getFinalisationDate() {
        return finalisationDate;
    }

    public void setFinalisationDate(Date finalisationDate) {
        this.finalisationDate = finalisationDate;
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

    public ApplicationInformation getInformationXMLEntity()
    {
        XMLUnmarshaller xmlu = new XMLUnmarshaller();        
        try 
        {        
            return xmlu.unmarshalApplicationInformationString(getInformation());
        } 
        catch (JAXBException ex) 
        {
            return null;
        }
    }
    
    public void setInformationXMLEntity(ApplicationInformation applicationInformation) throws JAXBException
    {
        XMLMarshaller xmlm = new XMLMarshaller();
             
        setInformation(xmlm.marshalApplicationInformationObject(applicationInformation));
    }

    @XmlTransient
    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @XmlTransient
    public List<CommitteeMeeting> getCommitteeMeetingList() {
        return committeeMeetingList;
    }

    public void setCommitteeMeetingList(List<CommitteeMeeting> committeeMeetingList) {
        this.committeeMeetingList = committeeMeetingList;
    }

    public DeclineReport getDeclineReport() {
        return declineReport;
    }

    public void setDeclineReport(DeclineReport declineReport) {
        this.declineReport = declineReport;
    }

    public Endorsement getEndorsement() {
        return endorsement;
    }

    public void setEndorsement(Endorsement endorsement) {
        this.endorsement = endorsement;
    }

    @XmlTransient
    public List<ApplicationReviewRequest> getApplicationReviewRequestList() {
        return applicationReviewRequestList;
    }

    public void setApplicationReviewRequestList(List<ApplicationReviewRequest> applicationReviewRequestList) {
        this.applicationReviewRequestList = applicationReviewRequestList;
    }

    @XmlTransient
    public List<RefereeReport> getRefereeReportList() {
        return refereeReportList;
    }

    public void setRefereeReportList(List<RefereeReport> refereeReportList) {
        this.refereeReportList = refereeReportList;
    }

    @XmlTransient
    public List<AmmendRequest> getAmmendRequestList() {
        return ammendRequestList;
    }

    public void setAmmendRequestList(List<AmmendRequest> ammendRequestList) {
        this.ammendRequestList = ammendRequestList;
    }

    public RecommendationReport getRecommendationReport() {
        return recommendationReport;
    }

    public void setRecommendationReport(RecommendationReport recommendationReport) {
        this.recommendationReport = recommendationReport;
    }

    public FundingReport getFundingReport() {
        return fundingReport;
    }

    public void setFundingReport(FundingReport fundingReport) {
        this.fundingReport = fundingReport;
    }

    public Person getFellow() {
        return fellow;
    }

    public void setFellow(Person fellow) {
        this.fellow = fellow;
    }

    public Person getGrantHolder() {
        return grantHolder;
    }

    public void setGrantHolder(Person grantHolder) {
        this.grantHolder = grantHolder;
    }

    @XmlTransient
    public List<ProgressReport> getProgressReportList() {
        return progressReportList;
    }

    public void setProgressReportList(List<ProgressReport> progressReportList) {
        this.progressReportList = progressReportList;
    }

    public EligiblityReport getEligiblityReport() {
        return eligiblityReport;
    }

    public void setEligiblityReport(EligiblityReport eligiblityReport) {
        this.eligiblityReport = eligiblityReport;
    }

    @XmlTransient
    public List<ForwardAndRewindReport> getForwardAndRewindReportList() {
        return forwardAndRewindReportList;
    }

    public void setForwardAndRewindReportList(List<ForwardAndRewindReport> forwardAndRewindReportList) {
        this.forwardAndRewindReportList = forwardAndRewindReportList;
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
        return "com.softserve.DBEntities.Application[ applicationID=" + applicationID + " ]";
    }
    
}
