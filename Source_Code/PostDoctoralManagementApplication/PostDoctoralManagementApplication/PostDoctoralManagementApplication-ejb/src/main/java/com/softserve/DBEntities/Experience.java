/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
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
@Table(name = "experience")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Experience.findAll", query = "SELECT e FROM Experience e"),
    @NamedQuery(name = "Experience.findByExperienceID", query = "SELECT e FROM Experience e WHERE e.experienceID = :experienceID"),
    @NamedQuery(name = "Experience.findByCapacity", query = "SELECT e FROM Experience e WHERE e.capacity = :capacity"),
    @NamedQuery(name = "Experience.findByOrganisation", query = "SELECT e FROM Experience e WHERE e.organisation = :organisation"),
    @NamedQuery(name = "Experience.findByStartDate", query = "SELECT e FROM Experience e WHERE e.startDate = :startDate"),
    @NamedQuery(name = "Experience.findByEndDate", query = "SELECT e FROM Experience e WHERE e.endDate = :endDate")})
public class Experience implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_experienceID")
    private Long experienceID;
    @Size(max = 100)
    @Column(name = "_capacity")
    private String capacity;
    @Size(max = 100)
    @Column(name = "_organisation")
    private String organisation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @JoinColumn(name = "_cvID", referencedColumnName = "_cvID")
    @ManyToOne(optional = false)
    private Cv cvID;

    public Experience() {
    }

    public Experience(Long experienceID) {
        this.experienceID = experienceID;
    }

    public Experience(Long experienceID, Date startDate, Date endDate) {
        this.experienceID = experienceID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getExperienceID() {
        return experienceID;
    }

    public void setExperienceID(Long experienceID) {
        this.experienceID = experienceID;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Cv getCvID() {
        return cvID;
    }

    public void setCvID(Cv cvID) {
        this.cvID = cvID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (experienceID != null ? experienceID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Experience)) {
            return false;
        }
        Experience other = (Experience) object;
        if ((this.experienceID == null && other.experienceID != null) || (this.experienceID != null && !this.experienceID.equals(other.experienceID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEnties.Experience[ experienceID=" + experienceID + " ]";
    }
    
}
