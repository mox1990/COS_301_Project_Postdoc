/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBEntities;

import auto.softserve.XMLEntities.CV.ProgressReportContent;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.softserve.auxiliary.XMLUtils.XMLMarshaller;
import com.softserve.auxiliary.XMLUtils.XMLUnmarshaller;
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
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
@Entity
@Table(name = "progress_report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProgressReport.findAll", query = "SELECT p FROM ProgressReport p"),
    @NamedQuery(name = "ProgressReport.findByReportID", query = "SELECT p FROM ProgressReport p WHERE p.reportID = :reportID"),
    @NamedQuery(name = "ProgressReport.findByTimestamp", query = "SELECT p FROM ProgressReport p WHERE p.timestamp = :timestamp")})
public class ProgressReport implements Serializable {
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
    @JoinColumn(name = "_application", referencedColumnName = "_applicationID")
    @ManyToOne(optional = false)
    private Application application;

    public ProgressReport() {
    }

    public ProgressReport(Long reportID) {
        this.reportID = reportID;
    }

    public ProgressReport(Long reportID, Date timestamp, String content) {
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

    public Application getApplication() {
        return application;
    }

    public ProgressReportContent getContentXMLEntity()
    {
        XMLUnmarshaller xmlu = new XMLUnmarshaller();
        
        try 
        {        
            return xmlu.unmarshalProgressReportContentString(getContent());
        }
        catch (JAXBException ex) 
        {
            return null;
        }
    }
    
    public void setContentXMLEntity(ProgressReportContent progressReportContent) throws JAXBException
    {
        XMLMarshaller xmlm = new XMLMarshaller();
             
        setContent(xmlm.marshalProgressReportContentObject(progressReportContent));
    }

    public void setApplication(Application application) {
        this.application = application;
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
        if (!(object instanceof ProgressReport)) {
            return false;
        }
        ProgressReport other = (ProgressReport) object;
        if ((this.reportID == null && other.reportID != null) || (this.reportID != null && !this.reportID.equals(other.reportID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.ProgressReport[ reportID=" + reportID + " ]";
    }
    
}
