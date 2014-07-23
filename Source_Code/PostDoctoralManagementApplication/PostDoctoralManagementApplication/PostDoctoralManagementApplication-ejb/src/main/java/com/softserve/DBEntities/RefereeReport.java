/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import auto.softserve.XMLEntities.referee.ReferalReport;
import com.softserve.XMLUtils.XMLUnmarshaller;
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
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "referee_report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RefereeReport.findAll", query = "SELECT r FROM RefereeReport r"),
    @NamedQuery(name = "RefereeReport.findByReportID", query = "SELECT r FROM RefereeReport r WHERE r.reportID = :reportID"),
    @NamedQuery(name = "RefereeReport.findByTimestamp", query = "SELECT r FROM RefereeReport r WHERE r.timestamp = :timestamp")})
public class RefereeReport implements Serializable {
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
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "_content")
    private String content;
    @JoinColumn(name = "_applicationID", referencedColumnName = "_applicationID")
    @ManyToOne(optional = false)
    private Application applicationID;
    @JoinColumn(name = "_refereeID", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person refereeID;

    public RefereeReport() {
    }

    public RefereeReport(Long reportID) {
        this.reportID = reportID;
    }

    public RefereeReport(Long reportID, Date timestamp, String content) {
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

    public ReferalReport getContentXMLEntity()
    {
        XMLUnmarshaller xmlu = new XMLUnmarshaller();
        
        try 
        {        
            return xmlu.unmarshalReferalReportString(getContent());
        } 
        catch (JAXBException ex) 
        {
            return null;
        }
    }

    public Application getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Application applicationID) {
        this.applicationID = applicationID;
    }

    public Person getRefereeID() {
        return refereeID;
    }

    public void setRefereeID(Person refereeID) {
        this.refereeID = refereeID;
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
        if (!(object instanceof RefereeReport)) {
            return false;
        }
        RefereeReport other = (RefereeReport) object;
        if ((this.reportID == null && other.reportID != null) || (this.reportID != null && !this.reportID.equals(other.reportID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.RefereeReport[ reportID=" + reportID + " ]";
    }
    
}
