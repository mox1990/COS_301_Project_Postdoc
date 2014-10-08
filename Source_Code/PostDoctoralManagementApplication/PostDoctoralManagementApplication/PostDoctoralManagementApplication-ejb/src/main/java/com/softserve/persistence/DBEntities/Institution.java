/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBEntities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "institution")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Institution.findAll", query = "SELECT i FROM Institution i"),
    @NamedQuery(name = "Institution.findByInstitutionID", query = "SELECT i FROM Institution i WHERE i.institutionID = :institutionID"),
    @NamedQuery(name = "Institution.findByName", query = "SELECT i FROM Institution i WHERE i.name = :name")})
public class Institution implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_institutionID")
    private Long institutionID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "_name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "institution")
    private List<Faculty> facultyList;

    public Institution() {
    }

    public Institution(Long institutionID) {
        this.institutionID = institutionID;
    }

    public Institution(Long institutionID, String name) {
        this.institutionID = institutionID;
        this.name = name;
    }

    public Long getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(Long institutionID) {
        this.institutionID = institutionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Faculty> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(List<Faculty> facultyList) {
        this.facultyList = facultyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (institutionID != null ? institutionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institution)) {
            return false;
        }
        Institution other = (Institution) object;
        if ((this.institutionID == null && other.institutionID != null) || (this.institutionID != null && !this.institutionID.equals(other.institutionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.Institution[ institutionID=" + institutionID + " ]";
    }
    
}
