/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
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
@Table(name = "notifications")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notifications.findAll", query = "SELECT n FROM Notifications n"),
    @NamedQuery(name = "Notifications.findByNotificationID", query = "SELECT n FROM Notifications n WHERE n.notificationID = :notificationID"),
    @NamedQuery(name = "Notifications.findBySubject", query = "SELECT n FROM Notifications n WHERE n.subject = :subject"),
    @NamedQuery(name = "Notifications.findByTimestamp", query = "SELECT n FROM Notifications n WHERE n.timestamp = :timestamp")})
public class Notifications implements Serializable {
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
    private Persons senderID;
    @JoinColumn(name = "_recieverID", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Persons recieverID;

    public Notifications() {
    }

    public Notifications(Long notificationID) {
        this.notificationID = notificationID;
    }

    public Notifications(Long notificationID, Date timestamp) {
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

    public Persons getSenderID() {
        return senderID;
    }

    public void setSenderID(Persons senderID) {
        this.senderID = senderID;
    }

    public Persons getRecieverID() {
        return recieverID;
    }

    public void setRecieverID(Persons recieverID) {
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
        if (!(object instanceof Notifications)) {
            return false;
        }
        Notifications other = (Notifications) object;
        if ((this.notificationID == null && other.notificationID != null) || (this.notificationID != null && !this.notificationID.equals(other.notificationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.Notifications[ notificationID=" + notificationID + " ]";
    }
    
}
