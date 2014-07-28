/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import auto.softserve.XMLEntities.CV.AdditionalInformation;
import auto.softserve.XMLEntities.CV.OtherContributions;
import auto.softserve.XMLEntities.CV.ResearchOutput;
import com.softserve.XMLUtils.XMLMarshaller;
import com.softserve.XMLUtils.XMLUnmarshaller;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "cv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cv.findAll", query = "SELECT c FROM Cv c"),
    @NamedQuery(name = "Cv.findByCvID", query = "SELECT c FROM Cv c WHERE c.cvID = :cvID"),
    @NamedQuery(name = "Cv.findByIdNumber", query = "SELECT c FROM Cv c WHERE c.idNumber = :idNumber"),
    @NamedQuery(name = "Cv.findByDateOfBirth", query = "SELECT c FROM Cv c WHERE c.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "Cv.findByGender", query = "SELECT c FROM Cv c WHERE c.gender = :gender"),
    @NamedQuery(name = "Cv.findByCitizenship", query = "SELECT c FROM Cv c WHERE c.citizenship = :citizenship"),
    @NamedQuery(name = "Cv.findByNrfRating", query = "SELECT c FROM Cv c WHERE c.nrfRating = :nrfRating"),
    @NamedQuery(name = "Cv.findByRace", query = "SELECT c FROM Cv c WHERE c.race = :race"),
    @NamedQuery(name = "Cv.findByRecentInstitution", query = "SELECT c FROM Cv c WHERE c.recentInstitution = :recentInstitution")})
public class Cv implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "_cvID")
    private String cvID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "_idNumber")
    private String idNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_dateOfBirth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Size(max = 6)
    @Column(name = "_gender")
    private String gender;
    @Size(max = 50)
    @Column(name = "_citizenship")
    private String citizenship;
    @Size(max = 4)
    @Column(name = "_nrfRating")
    private String nrfRating;
    @Size(max = 20)
    @Column(name = "_race")
    private String race;
    @Size(max = 50)
    @Column(name = "_recentInstitution")
    private String recentInstitution;
    @Lob
    @Size(max = 65535)
    @Column(name = "_researchOutput")
    private String researchOutput;
    @Lob
    @Size(max = 65535)
    @Column(name = "_otherContributions")
    private String otherContributions;
    @Lob
    @Size(max = 65535)
    @Column(name = "_additionalInformation")
    private String additionalInformation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cvID")
    private List<Experience> experienceList;
    @JoinColumn(name = "_cvID", referencedColumnName = "_systemID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Person person;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cvID")
    private List<AcademicQualification> academicQualificationList;

    public Cv() {
    }

    public Cv(String cvID) {
        this.cvID = cvID;
    }

    public Cv(String cvID, String idNumber, Date dateOfBirth) {
        this.cvID = cvID;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public String getCvID() {
        return cvID;
    }

    public void setCvID(String cvID) {
        this.cvID = cvID;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getNrfRating() {
        return nrfRating;
    }

    public void setNrfRating(String nrfRating) {
        this.nrfRating = nrfRating;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getRecentInstitution() {
        return recentInstitution;
    }

    public void setRecentInstitution(String recentInstitution) {
        this.recentInstitution = recentInstitution;
    }

    public String getResearchOutput() {
        return researchOutput;
    }

    public void setResearchOutput(String researchOutput) {
        this.researchOutput = researchOutput;
    }

    public ResearchOutput getResearchOutputXMLEntity()
    {
        XMLUnmarshaller xmlu = new XMLUnmarshaller();
        
        try 
        {        
            return xmlu.unmarshalResearchOutputString(getResearchOutput());
        } 
        catch (JAXBException ex) 
        {
            return null;
        }
    }
    
    public void setResearchOutputXMLEntity(ResearchOutput researchOutput) throws JAXBException
    {
        XMLMarshaller xmlm = new XMLMarshaller();
             
        setResearchOutput(xmlm.marshalResearchOutputObject(researchOutput));
    }
    
    public String getOtherContributions() {
        return otherContributions;
    }

    public void setOtherContributions(String otherContributions) {
        this.otherContributions = otherContributions;
    }

    public OtherContributions getOtherContributionsXMLEntity()
    {
        XMLUnmarshaller xmlu = new XMLUnmarshaller();
        
        try 
        {        
            return xmlu.unmarshalOtherContributionsString(getOtherContributions());
        } 
        catch (JAXBException ex) 
        {
            return null;
        }
    }
    
    public void setOtherContributionsXMLEntity(OtherContributions otherContributions) throws JAXBException
    {
        XMLMarshaller xmlm = new XMLMarshaller();
             
        setOtherContributions(xmlm.marshalOtherContributionsObject(otherContributions));
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public AdditionalInformation getAdditionalInformationXMLEntity()
    {
        XMLUnmarshaller xmlu = new XMLUnmarshaller();
        
        try 
        {        
            return xmlu.unmarshalAdditionalInformationString(getAdditionalInformation());
        } 
        catch (JAXBException ex) 
        {
            return null;
        }
    }
    
    public void setAdditionalInformationXMLEntity(AdditionalInformation additionalInformation) throws JAXBException
    {
        XMLMarshaller xmlm = new XMLMarshaller();
             
        setAdditionalInformation(xmlm.marshalAdditionalInformationObject(additionalInformation));
    }
    
    @XmlTransient
    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @XmlTransient
    public List<AcademicQualification> getAcademicQualificationList() {
        return academicQualificationList;
    }

    public void setAcademicQualificationList(List<AcademicQualification> academicQualificationList) {
        this.academicQualificationList = academicQualificationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cvID != null ? cvID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cv)) {
            return false;
        }
        Cv other = (Cv) object;
        if ((this.cvID == null && other.cvID != null) || (this.cvID != null && !this.cvID.equals(other.cvID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.Cv[ cvID=" + cvID + " ]";
    }
    
}
