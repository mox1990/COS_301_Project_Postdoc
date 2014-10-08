/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBEntities;

import com.softserve.auxillary.util.HashUtil;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "person")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
    @NamedQuery(name = "Person.findBySystemID", query = "SELECT p FROM Person p WHERE p.systemID = :systemID"),
    @NamedQuery(name = "Person.findByPassword", query = "SELECT p FROM Person p WHERE p.password = :password"),
    @NamedQuery(name = "Person.findByTitle", query = "SELECT p FROM Person p WHERE p.title = :title"),
    @NamedQuery(name = "Person.findByFullName", query = "SELECT p FROM Person p WHERE p.fullName = :fullName"),
    @NamedQuery(name = "Person.findBySurname", query = "SELECT p FROM Person p WHERE p.surname = :surname"),
    @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email = :email"),
    @NamedQuery(name = "Person.findByTelephoneNumber", query = "SELECT p FROM Person p WHERE p.telephoneNumber = :telephoneNumber"),
    @NamedQuery(name = "Person.findByWorkNumber", query = "SELECT p FROM Person p WHERE p.workNumber = :workNumber"),
    @NamedQuery(name = "Person.findByFaxNumber", query = "SELECT p FROM Person p WHERE p.faxNumber = :faxNumber"),
    @NamedQuery(name = "Person.findByCellphoneNumber", query = "SELECT p FROM Person p WHERE p.cellphoneNumber = :cellphoneNumber"),
    @NamedQuery(name = "Person.findByUpEmployee", query = "SELECT p FROM Person p WHERE p.upEmployee = :upEmployee"),
    @NamedQuery(name = "Person.findByAccountStatus", query = "SELECT p FROM Person p WHERE p.accountStatus = :accountStatus")})
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "_systemID")
    private String systemID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 130)
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
    @Size(max = 8)
    @Column(name = "_accountStatus")
    private String accountStatus;
    @JoinTable(name = "person_security_role", joinColumns = {
        @JoinColumn(name = "_personID", referencedColumnName = "_systemID")}, inverseJoinColumns = {
        @JoinColumn(name = "_roleID", referencedColumnName = "_roleID")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<SecurityRole> securityRoleList;
    @JoinTable(name = "referee_application", joinColumns = {
        @JoinColumn(name = "_refereeID", referencedColumnName = "_systemID")}, inverseJoinColumns = {
        @JoinColumn(name = "_applicationID", referencedColumnName = "_applicationID")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Application> applicationList;
    @ManyToMany(mappedBy = "personList")
    private List<CommitteeMeeting> committeeMeetingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<DeclineReport> declineReportList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dean")
    private List<Endorsement> endorsementList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person1")
    private List<ApplicationReviewRequest> applicationReviewRequestList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
    private ResearchFellowInformation researchFellowInformation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referee")
    private List<RefereeReport> refereeReportList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
    private EmployeeInformation employeeInformation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private List<Notification> notificationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reciever")
    private List<Notification> notificationList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<AmmendRequest> ammendRequestList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organiser")
    private List<CommitteeMeeting> committeeMeetingList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<AuditLog> auditLogList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hod")
    private List<RecommendationReport> recommendationReportList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dris")
    private List<FundingReport> fundingReportList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
    private Cv cv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fellow", fetch = FetchType.EAGER)
    private List<Application> applicationList1;
    @OneToMany(mappedBy = "grantHolder", fetch = FetchType.EAGER)
    private List<Application> applicationList2;
    @JoinColumn(name = "_addressLine1", referencedColumnName = "_addressID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Address addressLine1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attendee")
    private List<MinuteComment> minuteCommentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eligiblityChecker")
    private List<EligiblityReport> eligiblityReportList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dris")
    private List<ForwardAndRewindReport> forwardAndRewindReportList;

    public Person() {
    }

    public Person(String systemID) {
        this.systemID = systemID;
    }

    public Person(String systemID, String password, String title, String fullName, String surname, String email, boolean upEmployee) {
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

    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = HashUtil.hashInput(password);
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

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getCompleteName()
    {
        return getTitle() + " " + getFullName() + " " + getSurname();
    }

    @XmlTransient
    public List<SecurityRole> getSecurityRoleList() {
        return securityRoleList;
    }

    public void setSecurityRoleList(List<SecurityRole> securityRoleList) {
        this.securityRoleList = securityRoleList;
    }

    @XmlTransient
    public List<Application> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

    @XmlTransient
    public List<CommitteeMeeting> getCommitteeMeetingList() {
        return committeeMeetingList;
    }

    public void setCommitteeMeetingList(List<CommitteeMeeting> committeeMeetingList) {
        this.committeeMeetingList = committeeMeetingList;
    }

    @XmlTransient
    public List<DeclineReport> getDeclineReportList() {
        return declineReportList;
    }

    public void setDeclineReportList(List<DeclineReport> declineReportList) {
        this.declineReportList = declineReportList;
    }

    @XmlTransient
    public List<Endorsement> getEndorsementList() {
        return endorsementList;
    }

    public void setEndorsementList(List<Endorsement> endorsementList) {
        this.endorsementList = endorsementList;
    }

    @XmlTransient
    public List<ApplicationReviewRequest> getApplicationReviewRequestList() {
        return applicationReviewRequestList;
    }

    public void setApplicationReviewRequestList(List<ApplicationReviewRequest> applicationReviewRequestList) {
        this.applicationReviewRequestList = applicationReviewRequestList;
    }

    public ResearchFellowInformation getResearchFellowInformation() {
        return researchFellowInformation;
    }

    public void setResearchFellowInformation(ResearchFellowInformation researchFellowInformation) {
        this.researchFellowInformation = researchFellowInformation;
    }

    @XmlTransient
    public List<RefereeReport> getRefereeReportList() {
        return refereeReportList;
    }

    public void setRefereeReportList(List<RefereeReport> refereeReportList) {
        this.refereeReportList = refereeReportList;
    }

    public EmployeeInformation getEmployeeInformation() {
        return employeeInformation;
    }

    public void setEmployeeInformation(EmployeeInformation employeeInformation) {
        this.employeeInformation = employeeInformation;
    }

    @XmlTransient
    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @XmlTransient
    public List<Notification> getNotificationList1() {
        return notificationList1;
    }

    public void setNotificationList1(List<Notification> notificationList1) {
        this.notificationList1 = notificationList1;
    }

    @XmlTransient
    public List<AmmendRequest> getAmmendRequestList() {
        return ammendRequestList;
    }

    public void setAmmendRequestList(List<AmmendRequest> ammendRequestList) {
        this.ammendRequestList = ammendRequestList;
    }

    @XmlTransient
    public List<CommitteeMeeting> getCommitteeMeetingList1() {
        return committeeMeetingList1;
    }

    public void setCommitteeMeetingList1(List<CommitteeMeeting> committeeMeetingList1) {
        this.committeeMeetingList1 = committeeMeetingList1;
    }

    @XmlTransient
    public List<AuditLog> getAuditLogList() {
        return auditLogList;
    }

    public void setAuditLogList(List<AuditLog> auditLogList) {
        this.auditLogList = auditLogList;
    }

    @XmlTransient
    public List<RecommendationReport> getRecommendationReportList() {
        return recommendationReportList;
    }

    public void setRecommendationReportList(List<RecommendationReport> recommendationReportList) {
        this.recommendationReportList = recommendationReportList;
    }

    @XmlTransient
    public List<FundingReport> getFundingReportList() {
        return fundingReportList;
    }

    public void setFundingReportList(List<FundingReport> fundingReportList) {
        this.fundingReportList = fundingReportList;
    }

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }

    @XmlTransient
    public List<Application> getApplicationList1() {
        return applicationList1;
    }

    public void setApplicationList1(List<Application> applicationList1) {
        this.applicationList1 = applicationList1;
    }

    @XmlTransient
    public List<Application> getApplicationList2() {
        return applicationList2;
    }

    public void setApplicationList2(List<Application> applicationList2) {
        this.applicationList2 = applicationList2;
    }

    public Address getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(Address addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @XmlTransient
    public List<MinuteComment> getMinuteCommentList() {
        return minuteCommentList;
    }

    public void setMinuteCommentList(List<MinuteComment> minuteCommentList) {
        this.minuteCommentList = minuteCommentList;
    }

    @XmlTransient
    public List<EligiblityReport> getEligiblityReportList() {
        return eligiblityReportList;
    }

    public void setEligiblityReportList(List<EligiblityReport> eligiblityReportList) {
        this.eligiblityReportList = eligiblityReportList;
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
        hash += (systemID != null ? systemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.systemID == null && other.systemID != null) || (this.systemID != null && !this.systemID.equals(other.systemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.Person[ systemID=" + systemID + " ]";
    }
    
}
