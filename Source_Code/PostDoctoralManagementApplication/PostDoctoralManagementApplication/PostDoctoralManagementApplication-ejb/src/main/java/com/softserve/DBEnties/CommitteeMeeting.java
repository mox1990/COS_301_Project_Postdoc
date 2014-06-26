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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "committee_meeting")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CommitteeMeeting.findAll", query = "SELECT c FROM CommitteeMeeting c"),
    @NamedQuery(name = "CommitteeMeeting.findByMeetingID", query = "SELECT c FROM CommitteeMeeting c WHERE c.meetingID = :meetingID"),
    @NamedQuery(name = "CommitteeMeeting.findByStartDate", query = "SELECT c FROM CommitteeMeeting c WHERE c.startDate = :startDate"),
    @NamedQuery(name = "CommitteeMeeting.findByEndDate", query = "SELECT c FROM CommitteeMeeting c WHERE c.endDate = :endDate")})
public class CommitteeMeeting implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_meetingID")
    private Long meetingID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @JoinTable(name = "attendence_list", joinColumns = {
        @JoinColumn(name = "_meetingID", referencedColumnName = "_meetingID")}, inverseJoinColumns = {
        @JoinColumn(name = "_attendeeID", referencedColumnName = "_systemID")})
    @ManyToMany
    private Collection<Person> personCollection;
    @JoinTable(name = "committee_meetings_applications", joinColumns = {
        @JoinColumn(name = "_meetingID", referencedColumnName = "_meetingID")}, inverseJoinColumns = {
        @JoinColumn(name = "_applicationID", referencedColumnName = "_applicationID")})
    @ManyToMany
    private Collection<Application> applicationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meetingID")
    private Collection<MinuteComment> minuteCommentCollection;

    public CommitteeMeeting() {
    }

    public CommitteeMeeting(Long meetingID) {
        this.meetingID = meetingID;
    }

    public CommitteeMeeting(Long meetingID, Date startDate, Date endDate) {
        this.meetingID = meetingID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(Long meetingID) {
        this.meetingID = meetingID;
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

    @XmlTransient
    public Collection<Person> getPersonCollection() {
        return personCollection;
    }

    public void setPersonCollection(Collection<Person> personCollection) {
        this.personCollection = personCollection;
    }

    @XmlTransient
    public Collection<Application> getApplicationCollection() {
        return applicationCollection;
    }

    public void setApplicationCollection(Collection<Application> applicationCollection) {
        this.applicationCollection = applicationCollection;
    }

    @XmlTransient
    public Collection<MinuteComment> getMinuteCommentCollection() {
        return minuteCommentCollection;
    }

    public void setMinuteCommentCollection(Collection<MinuteComment> minuteCommentCollection) {
        this.minuteCommentCollection = minuteCommentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meetingID != null ? meetingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommitteeMeeting)) {
            return false;
        }
        CommitteeMeeting other = (CommitteeMeeting) object;
        if ((this.meetingID == null && other.meetingID != null) || (this.meetingID != null && !this.meetingID.equals(other.meetingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEnties.CommitteeMeeting[ meetingID=" + meetingID + " ]";
    }
    
}
