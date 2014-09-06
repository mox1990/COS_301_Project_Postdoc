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
@Table(name = "forward_and_rewind_report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ForwardAndRewindReport.findAll", query = "SELECT f FROM ForwardAndRewindReport f"),
    @NamedQuery(name = "ForwardAndRewindReport.findByReportID", query = "SELECT f FROM ForwardAndRewindReport f WHERE f.reportID = :reportID"),
    @NamedQuery(name = "ForwardAndRewindReport.findByTimestamp", query = "SELECT f FROM ForwardAndRewindReport f WHERE f.timestamp = :timestamp"),
    @NamedQuery(name = "ForwardAndRewindReport.findByType", query = "SELECT f FROM ForwardAndRewindReport f WHERE f.type = :type"),
    @NamedQuery(name = "ForwardAndRewindReport.findByFromStatus", query = "SELECT f FROM ForwardAndRewindReport f WHERE f.fromStatus = :fromStatus"),
    @NamedQuery(name = "ForwardAndRewindReport.findByToStatus", query = "SELECT f FROM ForwardAndRewindReport f WHERE f.toStatus = :toStatus")})
public class ForwardAndRewindReport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_reportID")
    private Long reportID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "_type")
    private String type;
    @Lob
    @Size(max = 65535)
    @Column(name = "_reason")
    private String reason;
    @Size(max = 11)
    @Column(name = "_fromStatus")
    private String fromStatus;
    @Size(max = 11)
    @Column(name = "_toStatus")
    private String toStatus;
    @JoinColumn(name = "_application", referencedColumnName = "_applicationID")
    @ManyToOne(optional = false)
    private Application application;
    @JoinColumn(name = "_dris", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person dris;

    public ForwardAndRewindReport() {
    }

    public ForwardAndRewindReport(Long reportID) {
        this.reportID = reportID;
    }

    public ForwardAndRewindReport(Long reportID, Date timestamp, String type) {
        this.reportID = reportID;
        this.timestamp = timestamp;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Person getDris() {
        return dris;
    }

    public void setDris(Person dris) {
        this.dris = dris;
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
        if (!(object instanceof ForwardAndRewindReport)) {
            return false;
        }
        ForwardAndRewindReport other = (ForwardAndRewindReport) object;
        if ((this.reportID == null && other.reportID != null) || (this.reportID != null && !this.reportID.equals(other.reportID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.ForwardAndRewindReport[ reportID=" + reportID + " ]";
    }
    
}
