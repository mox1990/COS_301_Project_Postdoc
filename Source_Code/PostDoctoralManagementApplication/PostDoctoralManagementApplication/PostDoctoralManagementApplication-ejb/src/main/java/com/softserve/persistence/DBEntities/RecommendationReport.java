/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBEntities;

import auto.softserve.XMLEntities.CV.AdditionalInformation;
import auto.softserve.XMLEntities.HOD.RecommendationReportContent;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.softserve.auxiliary.XMLUtils.XMLMarshaller;
import com.softserve.auxiliary.XMLUtils.XMLUnmarshaller;
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
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
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
    @JoinColumn(name = "_reportID", referencedColumnName = "_applicationID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Application application;
    @JoinColumn(name = "_hod", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person hod;

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

    public RecommendationReportContent getContentXMLEntity()
    {
        XMLUnmarshaller xmlu = new XMLUnmarshaller();
        
        try 
        {        
            return xmlu.unmarshalRecommendationReportContentString(getContent());
        }
        catch (JAXBException ex) 
        {
            return null;
        }
    }
    
    public void setContentXMLEntity(RecommendationReportContent recommendationReportContent) throws JAXBException
    {
        XMLMarshaller xmlm = new XMLMarshaller();
             
        setContent(xmlm.marshalRecommendationReportContentObject(recommendationReportContent));
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Person getHod() {
        return hod;
    }

    public void setHod(Person hod) {
        this.hod = hod;
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
