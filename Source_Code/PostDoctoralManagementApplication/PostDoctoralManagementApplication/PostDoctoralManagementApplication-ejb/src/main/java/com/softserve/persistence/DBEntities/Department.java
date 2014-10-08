/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBEntities;

import java.io.Serializable;
import java.util.List;
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
@Table(name = "department")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d"),
    @NamedQuery(name = "Department.findByDepartmentID", query = "SELECT d FROM Department d WHERE d.departmentID = :departmentID"),
    @NamedQuery(name = "Department.findByName", query = "SELECT d FROM Department d WHERE d.name = :name")})
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_departmentID")
    private Long departmentID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "_name")
    private String name;
    @OneToMany(mappedBy = "department")
    private List<ResearchFellowInformation> researchFellowInformationList;
    @OneToMany(mappedBy = "department")
    private List<EmployeeInformation> employeeInformationList;
    @JoinColumn(name = "_faculty", referencedColumnName = "_facultyID")
    @ManyToOne(optional = false)
    private Faculty faculty;

    public Department() {
    }

    public Department(Long departmentID) {
        this.departmentID = departmentID;
    }

    public Department(Long departmentID, String name) {
        this.departmentID = departmentID;
        this.name = name;
    }

    public Long getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Long departmentID) {
        this.departmentID = departmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<ResearchFellowInformation> getResearchFellowInformationList() {
        return researchFellowInformationList;
    }

    public void setResearchFellowInformationList(List<ResearchFellowInformation> researchFellowInformationList) {
        this.researchFellowInformationList = researchFellowInformationList;
    }

    @XmlTransient
    public List<EmployeeInformation> getEmployeeInformationList() {
        return employeeInformationList;
    }

    public void setEmployeeInformationList(List<EmployeeInformation> employeeInformationList) {
        this.employeeInformationList = employeeInformationList;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (departmentID != null ? departmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.departmentID == null && other.departmentID != null) || (this.departmentID != null && !this.departmentID.equals(other.departmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.Department[ departmentID=" + departmentID + " ]";
    }
    
}
