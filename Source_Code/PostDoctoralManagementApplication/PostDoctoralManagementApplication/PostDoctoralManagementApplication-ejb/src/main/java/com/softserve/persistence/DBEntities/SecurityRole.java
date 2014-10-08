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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "security_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecurityRole.findAll", query = "SELECT s FROM SecurityRole s"),
    @NamedQuery(name = "SecurityRole.findByRoleID", query = "SELECT s FROM SecurityRole s WHERE s.roleID = :roleID"),
    @NamedQuery(name = "SecurityRole.findByName", query = "SELECT s FROM SecurityRole s WHERE s.name = :name"),
    @NamedQuery(name = "SecurityRole.findByRoleMask", query = "SELECT s FROM SecurityRole s WHERE s.roleMask = :roleMask")})
public class SecurityRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_roleID")
    private Long roleID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "_name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_roleMask")
    private long roleMask;
    @ManyToMany(mappedBy = "securityRoleList")
    private List<Person> personList;

    public SecurityRole() {
    }

    public SecurityRole(Long roleID) {
        this.roleID = roleID;
    }

    public SecurityRole(Long roleID, String name, long roleMask) {
        this.roleID = roleID;
        this.name = name;
        this.roleMask = roleMask;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRoleMask() {
        return roleMask;
    }

    public void setRoleMask(long roleMask) {
        this.roleMask = roleMask;
    }

    @XmlTransient
    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleID != null ? roleID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecurityRole)) {
            return false;
        }
        SecurityRole other = (SecurityRole) object;
        if ((this.roleID == null && other.roleID != null) || (this.roleID != null && !this.roleID.equals(other.roleID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.SecurityRole[ roleID=" + roleID + " ]";
    }
    
}
