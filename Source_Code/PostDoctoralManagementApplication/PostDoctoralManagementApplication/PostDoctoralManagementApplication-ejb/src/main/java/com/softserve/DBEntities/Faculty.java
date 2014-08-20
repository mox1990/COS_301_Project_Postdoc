/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "faculty")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faculty.findAll", query = "SELECT f FROM Faculty f"),
    @NamedQuery(name = "Faculty.findByFacultyID", query = "SELECT f FROM Faculty f WHERE f.facultyID = :facultyID"),
    @NamedQuery(name = "Faculty.findByName", query = "SELECT f FROM Faculty f WHERE f.name = :name")})
public class Faculty implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_facultyID")
    private Long facultyID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "_name")
    private String name;
    @JoinColumn(name = "_institution", referencedColumnName = "_institutionID")
    @ManyToOne(optional = false)
    private Institution institution;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "faculty")
    private List<Department> departmentList;

    public Faculty() {
    }

    public Faculty(Long facultyID) {
        this.facultyID = facultyID;
    }

    public Faculty(Long facultyID, String name) {
        this.facultyID = facultyID;
        this.name = name;
    }

    public Long getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(Long facultyID) {
        this.facultyID = facultyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    @XmlTransient
    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facultyID != null ? facultyID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Faculty)) {
            return false;
        }
        Faculty other = (Faculty) object;
        if ((this.facultyID == null && other.facultyID != null) || (this.facultyID != null && !this.facultyID.equals(other.facultyID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.Faculty[ facultyID=" + facultyID + " ]";
    }
    
}
