/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBEntities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = "persons")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persons.findAll", query = "SELECT p FROM Persons p"),
    @NamedQuery(name = "Persons.findBySystemID", query = "SELECT p FROM Persons p WHERE p.systemID = :systemID"),
    @NamedQuery(name = "Persons.findByPassword", query = "SELECT p FROM Persons p WHERE p.password = :password"),
    @NamedQuery(name = "Persons.findByTitle", query = "SELECT p FROM Persons p WHERE p.title = :title"),
    @NamedQuery(name = "Persons.findByFullName", query = "SELECT p FROM Persons p WHERE p.fullName = :fullName"),
    @NamedQuery(name = "Persons.findBySurname", query = "SELECT p FROM Persons p WHERE p.surname = :surname"),
    @NamedQuery(name = "Persons.findByEmail", query = "SELECT p FROM Persons p WHERE p.email = :email"),
    @NamedQuery(name = "Persons.findByTelephoneNumber", query = "SELECT p FROM Persons p WHERE p.telephoneNumber = :telephoneNumber"),
    @NamedQuery(name = "Persons.findByWorkNumber", query = "SELECT p FROM Persons p WHERE p.workNumber = :workNumber"),
    @NamedQuery(name = "Persons.findByFaxNumber", query = "SELECT p FROM Persons p WHERE p.faxNumber = :faxNumber"),
    @NamedQuery(name = "Persons.findByCellphoneNumber", query = "SELECT p FROM Persons p WHERE p.cellphoneNumber = :cellphoneNumber"),
    @NamedQuery(name = "Persons.findByUpEmployee", query = "SELECT p FROM Persons p WHERE p.upEmployee = :upEmployee")})
public class Persons implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "_systemID")
    private String systemID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "_password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "_title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "_fullName")
    private String fullName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "_surname")
    private String surname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "_email")
    private String email;
    @Size(max = 20)
    @Column(name = "_telephoneNumber")
    private String telephoneNumber;
    @Size(max = 20)
    @Column(name = "_workNumber")
    private String workNumber;
    @Size(max = 20)
    @Column(name = "_faxNumber")
    private String faxNumber;
    @Size(max = 20)
    @Column(name = "_cellphoneNumber")
    private String cellphoneNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_upEmployee")
    private boolean upEmployee;
    @ManyToMany(mappedBy = "personsCollection")
    private Collection<CommitteeMeetings> committeeMeetingsCollection;
    @JoinTable(name = "person_security_role", joinColumns = {
        @JoinColumn(name = "_personID", referencedColumnName = "_systemID")}, inverseJoinColumns = {
        @JoinColumn(name = "_roleID", referencedColumnName = "_roleID")})
    @ManyToMany
    private Collection<SecurityRoles> securityRolesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerID")
    private Collection<Cvs> cvsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personID")
    private Collection<AuditLog> auditLogCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persons")
    private UpEmployeeInformation upEmployeeInformation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hodID")
    private Collection<RecommendationReport> recommendationReportCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "drisID")
    private Collection<FundingReports> fundingReportsCollection;
    @JoinColumn(name = "_locationID", referencedColumnName = "_locationID")
    @ManyToOne
    private Locations locationID;
    @JoinColumn(name = "_addressLine1", referencedColumnName = "_addressID")
    @ManyToOne(optional = false)
    private Addressess addressLine1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deanID")
    private Collection<Endorsements> endorsementsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attendeeID")
    private Collection<MinuteComment> minuteCommentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "senderID")
    private Collection<Notifications> notificationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recieverID")
    private Collection<Notifications> notificationsCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fellow")
    private Collection<Applications> applicationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grantHolderID")
    private Collection<Applications> applicationsCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "refereeID")
    private Collection<RefereeReports> refereeReportsCollection;

    public Persons() {
    }

    public Persons(String systemID) {
        this.systemID = systemID;
    }

    public Persons(String systemID, String password, String title, String fullName, String surname, String email, boolean upEmployee) {
        this.systemID = systemID;
        this.password = password;
        this.title = title;
        this.fullName = fullName;
        this.surname = surname;
        this.email = email;
        this.upEmployee = upEmployee;
    }

    public String getSystemID() {
        return systemID;
    }

    public void setSystemID(String systemID) {
        this.systemID = systemID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    public boolean getUpEmployee() {
        return upEmployee;
    }

    public void setUpEmployee(boolean upEmployee) {
        this.upEmployee = upEmployee;
    }

    @XmlTransient
    public Collection<CommitteeMeetings> getCommitteeMeetingsCollection() {
        return committeeMeetingsCollection;
    }

    public void setCommitteeMeetingsCollection(Collection<CommitteeMeetings> committeeMeetingsCollection) {
        this.committeeMeetingsCollection = committeeMeetingsCollection;
    }

    @XmlTransient
    public Collection<SecurityRoles> getSecurityRolesCollection() {
        return securityRolesCollection;
    }

    public void setSecurityRolesCollection(Collection<SecurityRoles> securityRolesCollection) {
        this.securityRolesCollection = securityRolesCollection;
    }

    @XmlTransient
    public Collection<Cvs> getCvsCollection() {
        return cvsCollection;
    }

    public void setCvsCollection(Collection<Cvs> cvsCollection) {
        this.cvsCollection = cvsCollection;
    }

    @XmlTransient
    public Collection<AuditLog> getAuditLogCollection() {
        return auditLogCollection;
    }

    public void setAuditLogCollection(Collection<AuditLog> auditLogCollection) {
        this.auditLogCollection = auditLogCollection;
    }

    public UpEmployeeInformation getUpEmployeeInformation() {
        return upEmployeeInformation;
    }

    public void setUpEmployeeInformation(UpEmployeeInformation upEmployeeInformation) {
        this.upEmployeeInformation = upEmployeeInformation;
    }

    @XmlTransient
    public Collection<RecommendationReport> getRecommendationReportCollection() {
        return recommendationReportCollection;
    }

    public void setRecommendationReportCollection(Collection<RecommendationReport> recommendationReportCollection) {
        this.recommendationReportCollection = recommendationReportCollection;
    }

    @XmlTransient
    public Collection<FundingReports> getFundingReportsCollection() {
        return fundingReportsCollection;
    }

    public void setFundingReportsCollection(Collection<FundingReports> fundingReportsCollection) {
        this.fundingReportsCollection = fundingReportsCollection;
    }

    public Locations getLocationID() {
        return locationID;
    }

    public void setLocationID(Locations locationID) {
        this.locationID = locationID;
    }

    public Addressess getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(Addressess addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @XmlTransient
    public Collection<Endorsements> getEndorsementsCollection() {
        return endorsementsCollection;
    }

    public void setEndorsementsCollection(Collection<Endorsements> endorsementsCollection) {
        this.endorsementsCollection = endorsementsCollection;
    }

    @XmlTransient
    public Collection<MinuteComment> getMinuteCommentCollection() {
        return minuteCommentCollection;
    }

    public void setMinuteCommentCollection(Collection<MinuteComment> minuteCommentCollection) {
        this.minuteCommentCollection = minuteCommentCollection;
    }

    @XmlTransient
    public Collection<Notifications> getNotificationsCollection() {
        return notificationsCollection;
    }

    public void setNotificationsCollection(Collection<Notifications> notificationsCollection) {
        this.notificationsCollection = notificationsCollection;
    }

    @XmlTransient
    public Collection<Notifications> getNotificationsCollection1() {
        return notificationsCollection1;
    }

    public void setNotificationsCollection1(Collection<Notifications> notificationsCollection1) {
        this.notificationsCollection1 = notificationsCollection1;
    }

    @XmlTransient
    public Collection<Applications> getApplicationsCollection() {
        return applicationsCollection;
    }

    public void setApplicationsCollection(Collection<Applications> applicationsCollection) {
        this.applicationsCollection = applicationsCollection;
    }

    @XmlTransient
    public Collection<Applications> getApplicationsCollection1() {
        return applicationsCollection1;
    }

    public void setApplicationsCollection1(Collection<Applications> applicationsCollection1) {
        this.applicationsCollection1 = applicationsCollection1;
    }

    @XmlTransient
    public Collection<RefereeReports> getRefereeReportsCollection() {
        return refereeReportsCollection;
    }

    public void setRefereeReportsCollection(Collection<RefereeReports> refereeReportsCollection) {
        this.refereeReportsCollection = refereeReportsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemID != null ? systemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persons)) {
            return false;
        }
        Persons other = (Persons) object;
        if ((this.systemID == null && other.systemID != null) || (this.systemID != null && !this.systemID.equals(other.systemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.Persons[ systemID=" + systemID + " ]";
    }
    
}
