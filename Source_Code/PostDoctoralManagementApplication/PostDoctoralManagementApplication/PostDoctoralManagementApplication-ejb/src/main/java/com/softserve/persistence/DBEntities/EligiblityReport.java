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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "eligiblity_report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EligiblityReport.findAll", query = "SELECT e FROM EligiblityReport e"),
    @NamedQuery(name = "EligiblityReport.findByReportID", query = "SELECT e FROM EligiblityReport e WHERE e.reportID = :reportID"),
    @NamedQuery(name = "EligiblityReport.findByEligiblityCheckDate", query = "SELECT e FROM EligiblityReport e WHERE e.eligiblityCheckDate = :eligiblityCheckDate")})
public class EligiblityReport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "_reportID")
    private Long reportID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_eligiblityCheckDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eligiblityCheckDate;
    @JoinColumn(name = "_reportID", referencedColumnName = "_applicationID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Application application;
    @JoinColumn(name = "_eligiblityChecker", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person eligiblityChecker;

    public EligiblityReport() {
    }

    public EligiblityReport(Long reportID) {
        this.reportID = reportID;
    }

    public EligiblityReport(Long reportID, Date eligiblityCheckDate) {
        this.reportID = reportID;
        this.eligiblityCheckDate = eligiblityCheckDate;
    }

    public Long getReportID() {
        return reportID;
    }

    public void setReportID(Long reportID) {
        this.reportID = reportID;
    }

    public Date getEligiblityCheckDate() {
        return eligiblityCheckDate;
    }

    public void setEligiblityCheckDate(Date eligiblityCheckDate) {
        this.eligiblityCheckDate = eligiblityCheckDate;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Person getEligiblityChecker() {
        return eligiblityChecker;
    }

    public void setEligiblityChecker(Person eligiblityChecker) {
        this.eligiblityChecker = eligiblityChecker;
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
        if (!(object instanceof EligiblityReport)) {
            return false;
        }
        EligiblityReport other = (EligiblityReport) object;
        if ((this.reportID == null && other.reportID != null) || (this.reportID != null && !this.reportID.equals(other.reportID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.EligiblityReport[ reportID=" + reportID + " ]";
    }
    
}
