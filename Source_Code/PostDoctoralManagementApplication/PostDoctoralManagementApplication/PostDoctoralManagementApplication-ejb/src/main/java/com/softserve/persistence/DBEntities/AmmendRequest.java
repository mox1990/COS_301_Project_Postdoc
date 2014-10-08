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
@Table(name = "ammend_request")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmmendRequest.findAll", query = "SELECT a FROM AmmendRequest a"),
    @NamedQuery(name = "AmmendRequest.findByRequestID", query = "SELECT a FROM AmmendRequest a WHERE a.requestID = :requestID"),
    @NamedQuery(name = "AmmendRequest.findByTimestamp", query = "SELECT a FROM AmmendRequest a WHERE a.timestamp = :timestamp")})
public class AmmendRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_requestID")
    private Long requestID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Lob
    @Size(max = 65535)
    @Column(name = "_request")
    private String request;
    @JoinColumn(name = "_application", referencedColumnName = "_applicationID")
    @ManyToOne(optional = false)
    private Application application;
    @JoinColumn(name = "_creator", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person creator;

    public AmmendRequest() {
    }

    public AmmendRequest(Long requestID) {
        this.requestID = requestID;
    }

    public AmmendRequest(Long requestID, Date timestamp) {
        this.requestID = requestID;
        this.timestamp = timestamp;
    }

    public Long getRequestID() {
        return requestID;
    }

    public void setRequestID(Long requestID) {
        this.requestID = requestID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requestID != null ? requestID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmmendRequest)) {
            return false;
        }
        AmmendRequest other = (AmmendRequest) object;
        if ((this.requestID == null && other.requestID != null) || (this.requestID != null && !this.requestID.equals(other.requestID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.AmmendRequest[ requestID=" + requestID + " ]";
    }
    
}
