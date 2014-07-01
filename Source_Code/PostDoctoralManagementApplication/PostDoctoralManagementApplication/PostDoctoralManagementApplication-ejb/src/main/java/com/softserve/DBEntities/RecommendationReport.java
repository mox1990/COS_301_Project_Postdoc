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
@Table(name = "recommendation_report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecommendationReport.findAll", query = "SELECT r FROM RecommendationReport r"),
    @NamedQuery(name = "RecommendationReport.findByReportID", query = "SELECT r FROM RecommendationReport r WHERE r.reportID = :reportID"),
    @NamedQuery(name = "RecommendationReport.findByTimestamp", query = "SELECT r FROM RecommendationReport r WHERE r.timestamp = :timestamp")})
public class RecommendationReport implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "_content")
    private String content;
    @JoinColumn(name = "_hodID", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person hodID;

    public RecommendationReport() {
    }

    public RecommendationReport(Long reportID) {
        this.reportID = reportID;
    }

    public RecommendationReport(Long reportID, Date timestamp, String content) {
        this.reportID = reportID;
        this.timestamp = timestamp;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Person getHodID() {
        return hodID;
    }

    public void setHodID(Person hodID) {
        this.hodID = hodID;
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
        if (!(object instanceof RecommendationReport)) {
            return false;
        }
        RecommendationReport other = (RecommendationReport) object;
        if ((this.reportID == null && other.reportID != null) || (this.reportID != null && !this.reportID.equals(other.reportID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.RecommendationReport[ reportID=" + reportID + " ]";
    }
    
}
