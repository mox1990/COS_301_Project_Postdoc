/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "notification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n"),
    @NamedQuery(name = "Notification.findByNotificationID", query = "SELECT n FROM Notification n WHERE n.notificationID = :notificationID"),
    @NamedQuery(name = "Notification.findBySubject", query = "SELECT n FROM Notification n WHERE n.subject = :subject"),
    @NamedQuery(name = "Notification.findByTimestamp", query = "SELECT n FROM Notification n WHERE n.timestamp = :timestamp"),
    @NamedQuery(name = "Notification.findBetweenRange", query = "SELECT a FROM AuditLog a WHERE a.timestamp BETWEEN :start AND :end")})

public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_notificationID")
    private Long notificationID;
    @Size(max = 200)
    @Column(name = "_subject")
    private String subject;
    @Lob
    @Size(max = 65535)
    @Column(name = "_message")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @JoinColumn(name = "_senderID", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person senderID;
    @JoinColumn(name = "_recieverID", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person recieverID;

    public Notification() {
    }

    public Notification(Long notificationID) {
        this.notificationID = notificationID;
    }

    public Notification(Long notificationID, Date timestamp) {
        this.notificationID = notificationID;
        this.timestamp = timestamp;
    }

    public Long getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(Long notificationID) {
        this.notificationID = notificationID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Person getSenderID() {
        return senderID;
    }

    public void setSenderID(Person senderID) {
        this.senderID = senderID;
    }

    public Person getRecieverID() {
        return recieverID;
    }

    public void setRecieverID(Person recieverID) {
        this.recieverID = recieverID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificationID != null ? notificationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.notificationID == null && other.notificationID != null) || (this.notificationID != null && !this.notificationID.equals(other.notificationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.Notification[ notificationID=" + notificationID + " ]";
    }
    
}
