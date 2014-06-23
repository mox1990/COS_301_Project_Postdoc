/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBEnties;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "security_roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecurityRoles.findAll", query = "SELECT s FROM SecurityRoles s"),
    @NamedQuery(name = "SecurityRoles.findByRoleID", query = "SELECT s FROM SecurityRoles s WHERE s.roleID = :roleID"),
    @NamedQuery(name = "SecurityRoles.findByName", query = "SELECT s FROM SecurityRoles s WHERE s.name = :name"),
    @NamedQuery(name = "SecurityRoles.findByRoleMask", query = "SELECT s FROM SecurityRoles s WHERE s.roleMask = :roleMask")})
public class SecurityRoles implements Serializable {
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
    @ManyToMany(mappedBy = "securityRolesCollection")
    private Collection<Persons> personsCollection;

    public SecurityRoles() {
    }

    public SecurityRoles(Long roleID) {
        this.roleID = roleID;
    }

    public SecurityRoles(Long roleID, String name, long roleMask) {
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
    public Collection<Persons> getPersonsCollection() {
        return personsCollection;
    }

    public void setPersonsCollection(Collection<Persons> personsCollection) {
        this.personsCollection = personsCollection;
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
        if (!(object instanceof SecurityRoles)) {
            return false;
        }
        SecurityRoles other = (SecurityRoles) object;
        if ((this.roleID == null && other.roleID != null) || (this.roleID != null && !this.roleID.equals(other.roleID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEnties.SecurityRoles[ roleID=" + roleID + " ]";
    }
    
}
