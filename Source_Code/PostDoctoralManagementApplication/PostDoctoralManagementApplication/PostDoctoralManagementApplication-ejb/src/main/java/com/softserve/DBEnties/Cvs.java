/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBEnties;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "cvs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cvs.findAll", query = "SELECT c FROM Cvs c"),
    @NamedQuery(name = "Cvs.findByCvID", query = "SELECT c FROM Cvs c WHERE c.cvID = :cvID"),
    @NamedQuery(name = "Cvs.findByIdNumber", query = "SELECT c FROM Cvs c WHERE c.idNumber = :idNumber"),
    @NamedQuery(name = "Cvs.findByDateOfBirth", query = "SELECT c FROM Cvs c WHERE c.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "Cvs.findByGender", query = "SELECT c FROM Cvs c WHERE c.gender = :gender"),
    @NamedQuery(name = "Cvs.findByCitizenship", query = "SELECT c FROM Cvs c WHERE c.citizenship = :citizenship"),
    @NamedQuery(name = "Cvs.findByNrfRating", query = "SELECT c FROM Cvs c WHERE c.nrfRating = :nrfRating"),
    @NamedQuery(name = "Cvs.findByRace", query = "SELECT c FROM Cvs c WHERE c.race = :race"),
    @NamedQuery(name = "Cvs.findByRecentInstitution", query = "SELECT c FROM Cvs c WHERE c.recentInstitution = :recentInstitution")})
public class Cvs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_cvID")
    private Long cvID;
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
    @JoinColumn(name = "_ownerID", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Persons ownerID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cvID")
    private Collection<Experience> experienceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cvID")
    private Collection<AcademicQualification> academicQualificationCollection;

    public Cvs() {
    }

    public Cvs(Long cvID) {
        this.cvID = cvID;
    }

    public Cvs(Long cvID, String idNumber, Date dateOfBirth) {
        this.cvID = cvID;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getCvID() {
        return cvID;
    }

    public void setCvID(Long cvID) {
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

    public String getOtherContributions() {
        return otherContributions;
    }

    public void setOtherContributions(String otherContributions) {
        this.otherContributions = otherContributions;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Persons getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Persons ownerID) {
        this.ownerID = ownerID;
    }

    @XmlTransient
    public Collection<Experience> getExperienceCollection() {
        return experienceCollection;
    }

    public void setExperienceCollection(Collection<Experience> experienceCollection) {
        this.experienceCollection = experienceCollection;
    }

    @XmlTransient
    public Collection<AcademicQualification> getAcademicQualificationCollection() {
        return academicQualificationCollection;
    }

    public void setAcademicQualificationCollection(Collection<AcademicQualification> academicQualificationCollection) {
        this.academicQualificationCollection = academicQualificationCollection;
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
        if (!(object instanceof Cvs)) {
            return false;
        }
        Cvs other = (Cvs) object;
        if ((this.cvID == null && other.cvID != null) || (this.cvID != null && !this.cvID.equals(other.cvID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEnties.Cvs[ cvID=" + cvID + " ]";
    }
    
}
