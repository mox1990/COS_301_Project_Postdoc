/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBEntities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "research_fellow_information")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResearchFellowInformation.findAll", query = "SELECT r FROM ResearchFellowInformation r"),
    @NamedQuery(name = "ResearchFellowInformation.findBySystemAssignedID", query = "SELECT r FROM ResearchFellowInformation r WHERE r.systemAssignedID = :systemAssignedID"),
    @NamedQuery(name = "ResearchFellowInformation.findByInstitutionAssignedID", query = "SELECT r FROM ResearchFellowInformation r WHERE r.institutionAssignedID = :institutionAssignedID"),
    @NamedQuery(name = "ResearchFellowInformation.findByInstitutionAssignedEmail", query = "SELECT r FROM ResearchFellowInformation r WHERE r.institutionAssignedEmail = :institutionAssignedEmail")})
public class ResearchFellowInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "_systemAssignedID")
    private String systemAssignedID;
    @Size(max = 9)
    @Column(name = "_institutionAssignedID")
    private String institutionAssignedID;
    @Size(max = 50)
    @Column(name = "_institutionAssignedEmail")
    private String institutionAssignedEmail;
    @JoinColumn(name = "_systemAssignedID", referencedColumnName = "_systemID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Person person;
    @JoinColumn(name = "_department", referencedColumnName = "_departmentID")
    @ManyToOne
    private Department department;

    public ResearchFellowInformation() {
    }

    public ResearchFellowInformation(String systemAssignedID) {
        this.systemAssignedID = systemAssignedID;
    }

    public String getSystemAssignedID() {
        return systemAssignedID;
    }

    public void setSystemAssignedID(String systemAssignedID) {
        this.systemAssignedID = systemAssignedID;
    }

    public String getInstitutionAssignedID() {
        return institutionAssignedID;
    }

    public void setInstitutionAssignedID(String institutionAssignedID) {
        this.institutionAssignedID = institutionAssignedID;
    }

    public String getInstitutionAssignedEmail() {
        return institutionAssignedEmail;
    }

    public void setInstitutionAssignedEmail(String institutionAssignedEmail) {
        this.institutionAssignedEmail = institutionAssignedEmail;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemAssignedID != null ? systemAssignedID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResearchFellowInformation)) {
            return false;
        }
        ResearchFellowInformation other = (ResearchFellowInformation) object;
        if ((this.systemAssignedID == null && other.systemAssignedID != null) || (this.systemAssignedID != null && !this.systemAssignedID.equals(other.systemAssignedID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.ResearchFellowInformation[ systemAssignedID=" + systemAssignedID + " ]";
    }
    
}
