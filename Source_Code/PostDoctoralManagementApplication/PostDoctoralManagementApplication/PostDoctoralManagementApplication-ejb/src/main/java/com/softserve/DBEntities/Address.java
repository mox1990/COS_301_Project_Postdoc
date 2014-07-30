/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "address")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a"),
    @NamedQuery(name = "Address.findByAddressID", query = "SELECT a FROM Address a WHERE a.addressID = :addressID"),
    @NamedQuery(name = "Address.findByCountry", query = "SELECT a FROM Address a WHERE a.country = :country"),
    @NamedQuery(name = "Address.findByProvince", query = "SELECT a FROM Address a WHERE a.province = :province"),
    @NamedQuery(name = "Address.findByTownCity", query = "SELECT a FROM Address a WHERE a.townCity = :townCity"),
    @NamedQuery(name = "Address.findByStreet", query = "SELECT a FROM Address a WHERE a.street = :street"),
    @NamedQuery(name = "Address.findByStreeNumber", query = "SELECT a FROM Address a WHERE a.streeNumber = :streeNumber"),
    @NamedQuery(name = "Address.findByRoomNumber", query = "SELECT a FROM Address a WHERE a.roomNumber = :roomNumber"),
    @NamedQuery(name = "Address.findByZippostalCode", query = "SELECT a FROM Address a WHERE a.zippostalCode = :zippostalCode")})
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
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
    @OneToMany(mappedBy = "physicalAddress")
    private List<EmployeeInformation> employeeInformationList;
    @OneToMany(mappedBy = "addressLine1")
    private List<Person> personList;

    public Address() {
    }

    public Address(Long addressID) {
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
    public List<EmployeeInformation> getEmployeeInformationList() {
        return employeeInformationList;
    }

    public void setEmployeeInformationList(List<EmployeeInformation> employeeInformationList) {
        this.employeeInformationList = employeeInformationList;
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
        hash += (addressID != null ? addressID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.addressID == null && other.addressID != null) || (this.addressID != null && !this.addressID.equals(other.addressID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.Address[ addressID=" + addressID + " ]";
    }
    
}
