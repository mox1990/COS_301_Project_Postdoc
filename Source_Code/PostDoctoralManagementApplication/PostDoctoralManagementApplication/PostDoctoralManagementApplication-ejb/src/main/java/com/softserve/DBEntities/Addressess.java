/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBEntities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "addressess")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Addressess.findAll", query = "SELECT a FROM Addressess a"),
    @NamedQuery(name = "Addressess.findByAddressID", query = "SELECT a FROM Addressess a WHERE a.addressID = :addressID"),
    @NamedQuery(name = "Addressess.findByCountry", query = "SELECT a FROM Addressess a WHERE a.country = :country"),
    @NamedQuery(name = "Addressess.findByProvince", query = "SELECT a FROM Addressess a WHERE a.province = :province"),
    @NamedQuery(name = "Addressess.findByTownCity", query = "SELECT a FROM Addressess a WHERE a.townCity = :townCity"),
    @NamedQuery(name = "Addressess.findByStreet", query = "SELECT a FROM Addressess a WHERE a.street = :street"),
    @NamedQuery(name = "Addressess.findByStreeNumber", query = "SELECT a FROM Addressess a WHERE a.streeNumber = :streeNumber"),
    @NamedQuery(name = "Addressess.findByRoomNumber", query = "SELECT a FROM Addressess a WHERE a.roomNumber = :roomNumber"),
    @NamedQuery(name = "Addressess.findByZippostalCode", query = "SELECT a FROM Addressess a WHERE a.zippostalCode = :zippostalCode")})
public class Addressess implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "_addressID")
    private Long addressID;
    @Size(max = 50)
    @Column(name = "_country")
    private String country;
    @Size(max = 50)
    @Column(name = "_province")
    private String province;
    @Size(max = 50)
    @Column(name = "_town_city")
    private String townCity;
    @Size(max = 50)
    @Column(name = "_street")
    private String street;
    @Column(name = "_streeNumber")
    private Integer streeNumber;
    @Size(max = 50)
    @Column(name = "_roomNumber")
    private String roomNumber;
    @Size(max = 6)
    @Column(name = "_zip_postalCode")
    private String zippostalCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "physicalAddress")
    private Collection<UpEmployeeInformation> upEmployeeInformationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "addressLine1")
    private Collection<Persons> personsCollection;

    public Addressess() {
    }

    public Addressess(Long addressID) {
        this.addressID = addressID;
    }

    public Long getAddressID() {
        return addressID;
    }

    public void setAddressID(Long addressID) {
        this.addressID = addressID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTownCity() {
        return townCity;
    }

    public void setTownCity(String townCity) {
        this.townCity = townCity;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getStreeNumber() {
        return streeNumber;
    }

    public void setStreeNumber(Integer streeNumber) {
        this.streeNumber = streeNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getZippostalCode() {
        return zippostalCode;
    }

    public void setZippostalCode(String zippostalCode) {
        this.zippostalCode = zippostalCode;
    }

    @XmlTransient
    public Collection<UpEmployeeInformation> getUpEmployeeInformationCollection() {
        return upEmployeeInformationCollection;
    }

    public void setUpEmployeeInformationCollection(Collection<UpEmployeeInformation> upEmployeeInformationCollection) {
        this.upEmployeeInformationCollection = upEmployeeInformationCollection;
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
        hash += (addressID != null ? addressID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Addressess)) {
            return false;
        }
        Addressess other = (Addressess) object;
        if ((this.addressID == null && other.addressID != null) || (this.addressID != null && !this.addressID.equals(other.addressID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.Addressess[ addressID=" + addressID + " ]";
    }
    
}
