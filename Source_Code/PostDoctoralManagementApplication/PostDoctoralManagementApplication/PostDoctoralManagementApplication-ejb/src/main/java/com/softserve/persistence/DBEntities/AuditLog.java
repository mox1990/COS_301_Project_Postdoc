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
@Table(name = "audit_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditLog.findAll", query = "SELECT a FROM AuditLog a"),
    @NamedQuery(name = "AuditLog.findByEntryID", query = "SELECT a FROM AuditLog a WHERE a.entryID = :entryID"),
    @NamedQuery(name = "AuditLog.findByTimestamp", query = "SELECT a FROM AuditLog a WHERE a.timestamp = :timestamp"),
    @NamedQuery(name = "AuditLog.findByAction", query = "SELECT a FROM AuditLog a WHERE a.action = :action")})
public class AuditLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_entryID")
    private Long entryID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "_action")
    private String action;
    @JoinColumn(name = "_person", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person person;

    public AuditLog() {
    }

    public AuditLog(Long entryID) {
        this.entryID = entryID;
    }

    public AuditLog(Long entryID, Date timestamp, String action) {
        this.entryID = entryID;
        this.timestamp = timestamp;
        this.action = action;
    }

    public Long getEntryID() {
        return entryID;
    }

    public void setEntryID(Long entryID) {
        this.entryID = entryID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entryID != null ? entryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditLog)) {
            return false;
        }
        AuditLog other = (AuditLog) object;
        if ((this.entryID == null && other.entryID != null) || (this.entryID != null && !this.entryID.equals(other.entryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.AuditLog[ entryID=" + entryID + " ]";
    }
    
}
