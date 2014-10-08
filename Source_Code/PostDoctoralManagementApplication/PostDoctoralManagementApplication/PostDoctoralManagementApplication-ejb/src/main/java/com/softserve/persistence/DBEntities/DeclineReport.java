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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "decline_report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DeclineReport.findAll", query = "SELECT d FROM DeclineReport d"),
    @NamedQuery(name = "DeclineReport.findByReportID", query = "SELECT d FROM DeclineReport d WHERE d.reportID = :reportID"),
    @NamedQuery(name = "DeclineReport.findByTimestamp", query = "SELECT d FROM DeclineReport d WHERE d.timestamp = :timestamp")})
public class DeclineReport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "_reportID")
    private Long reportID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Lob
    @Size(max = 65535)
    @Column(name = "_reason")
    private String reason;
    @JoinColumn(name = "_reportID", referencedColumnName = "_applicationID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Application application;
    @JoinColumn(name = "_creator", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person creator;

    public DeclineReport() {
    }

    public DeclineReport(Long reportID) {
        this.reportID = reportID;
    }

    public DeclineReport(Long reportID, Date timestamp) {
        this.reportID = reportID;
        this.timestamp = timestamp;
    }

    public Long getReportID() {
        return reportID;
    }

    public void setReportID(Long reportID) {
        this.reportID = reportID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
        hash += (reportID != null ? reportID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeclineReport)) {
            return false;
        }
        DeclineReport other = (DeclineReport) object;
        if ((this.reportID == null && other.reportID != null) || (this.reportID != null && !this.reportID.equals(other.reportID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.DeclineReport[ reportID=" + reportID + " ]";
    }
    
}
