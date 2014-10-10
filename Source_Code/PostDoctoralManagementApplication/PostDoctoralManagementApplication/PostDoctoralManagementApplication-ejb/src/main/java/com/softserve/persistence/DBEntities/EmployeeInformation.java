/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBEntities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Date;
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
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
@Entity
@Table(name = "employee_information")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeeInformation.findAll", query = "SELECT e FROM EmployeeInformation e"),
    @NamedQuery(name = "EmployeeInformation.findByEmployeeID", query = "SELECT e FROM EmployeeInformation e WHERE e.employeeID = :employeeID"),
    @NamedQuery(name = "EmployeeInformation.findByPosition", query = "SELECT e FROM EmployeeInformation e WHERE e.position = :position"),
    @NamedQuery(name = "EmployeeInformation.findByDateOfAppointment", query = "SELECT e FROM EmployeeInformation e WHERE e.dateOfAppointment = :dateOfAppointment"),
    @NamedQuery(name = "EmployeeInformation.findByAppointmentStatus", query = "SELECT e FROM EmployeeInformation e WHERE e.appointmentStatus = :appointmentStatus")})
public class EmployeeInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "_employeeID")
    private String employeeID;
    @Size(max = 50)
    @Column(name = "_position")
    private String position;
    @Column(name = "_dateOfAppointment")
    @Temporal(TemporalType.DATE)
    private Date dateOfAppointment;
    @Size(max = 50)
    @Column(name = "_appointmentStatus")
    private String appointmentStatus;
    @JoinColumn(name = "_employeeID", referencedColumnName = "_systemID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Person person;
    @JoinColumn(name = "_department", referencedColumnName = "_departmentID")
    @ManyToOne
    private Department department;
    @JoinColumn(name = "_physicalAddress", referencedColumnName = "_addressID")
    @ManyToOne
    private Address physicalAddress;

    public EmployeeInformation() {
    }

    public EmployeeInformation(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
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

    public Address getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(Address physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeID != null ? employeeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeInformation)) {
            return false;
        }
        EmployeeInformation other = (EmployeeInformation) object;
        if ((this.employeeID == null && other.employeeID != null) || (this.employeeID != null && !this.employeeID.equals(other.employeeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.EmployeeInformation[ employeeID=" + employeeID + " ]";
    }
    
}
