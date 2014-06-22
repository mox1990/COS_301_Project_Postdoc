/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBEntities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "renewal_applications")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RenewalApplications.findAll", query = "SELECT r FROM RenewalApplications r"),
    @NamedQuery(name = "RenewalApplications.findByApplicationID", query = "SELECT r FROM RenewalApplications r WHERE r.applicationID = :applicationID")})
public class RenewalApplications implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "_applicationID")
    private Long applicationID;
    @JoinColumn(name = "_applicationID", referencedColumnName = "_applicationID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Applications applications;
    @JoinColumn(name = "_progressReportID", referencedColumnName = "_reportID")
    @OneToOne
    private ProgressReports progressReportID;

    public RenewalApplications() {
    }

    public RenewalApplications(Long applicationID) {
        this.applicationID = applicationID;
    }

    public Long getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }

    public Applications getApplications() {
        return applications;
    }

    public void setApplications(Applications applications) {
        this.applications = applications;
    }

    public ProgressReports getProgressReportID() {
        return progressReportID;
    }

    public void setProgressReportID(ProgressReports progressReportID) {
        this.progressReportID = progressReportID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (applicationID != null ? applicationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RenewalApplications)) {
            return false;
        }
        RenewalApplications other = (RenewalApplications) object;
        if ((this.applicationID == null && other.applicationID != null) || (this.applicationID != null && !this.applicationID.equals(other.applicationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.RenewalApplications[ applicationID=" + applicationID + " ]";
    }
    
}
