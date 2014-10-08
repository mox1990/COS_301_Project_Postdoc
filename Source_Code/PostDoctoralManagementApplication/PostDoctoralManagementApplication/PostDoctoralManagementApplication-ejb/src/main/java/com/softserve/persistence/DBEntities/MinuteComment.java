/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBEntities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "minute_comment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MinuteComment.findAll", query = "SELECT m FROM MinuteComment m"),
    @NamedQuery(name = "MinuteComment.findByCommentID", query = "SELECT m FROM MinuteComment m WHERE m.commentID = :commentID"),
    @NamedQuery(name = "MinuteComment.findByTimestamp", query = "SELECT m FROM MinuteComment m WHERE m.timestamp = :timestamp"),
    @NamedQuery(name = "MinuteComment.findByComment", query = "SELECT m FROM MinuteComment m WHERE m.comment = :comment")})
public class MinuteComment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_commentID")
    private Long commentID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "_comment")
    private String comment;
    @JoinColumn(name = "_meeting", referencedColumnName = "_meetingID")
    @ManyToOne(optional = false)
    private CommitteeMeeting meeting;
    @JoinColumn(name = "_attendee", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person attendee;

    public MinuteComment() {
    }

    public MinuteComment(Long commentID) {
        this.commentID = commentID;
    }

    public MinuteComment(Long commentID, Date timestamp, String comment) {
        this.commentID = commentID;
        this.timestamp = timestamp;
        this.comment = comment;
    }

    public Long getCommentID() {
        return commentID;
    }

    public void setCommentID(Long commentID) {
        this.commentID = commentID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CommitteeMeeting getMeeting() {
        return meeting;
    }

    public void setMeeting(CommitteeMeeting meeting) {
        this.meeting = meeting;
    }

    public Person getAttendee() {
        return attendee;
    }

    public void setAttendee(Person attendee) {
        this.attendee = attendee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentID != null ? commentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MinuteComment)) {
            return false;
        }
        MinuteComment other = (MinuteComment) object;
        if ((this.commentID == null && other.commentID != null) || (this.commentID != null && !this.commentID.equals(other.commentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.MinuteComment[ commentID=" + commentID + " ]";
    }
    
}
