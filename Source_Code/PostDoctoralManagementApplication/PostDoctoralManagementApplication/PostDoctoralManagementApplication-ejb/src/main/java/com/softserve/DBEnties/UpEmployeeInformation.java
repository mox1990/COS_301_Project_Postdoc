/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBEnties;

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
@Entity
@Table(name = "up_employee_information")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UpEmployeeInformation.findAll", query = "SELECT u FROM UpEmployeeInformation u"),
    @NamedQuery(name = "UpEmployeeInformation.findByEmployeeID", query = "SELECT u FROM UpEmployeeInformation u WHERE u.employeeID = :employeeID"),
    @NamedQuery(name = "UpEmployeeInformation.findByPosition", query = "SELECT u FROM UpEmployeeInformation u WHERE u.position = :position"),
    @NamedQuery(name = "UpEmployeeInformation.findByDateOfAppointment", query = "SELECT u FROM UpEmployeeInformation u WHERE u.dateOfAppointment = :dateOfAppointment"),
    @NamedQuery(name = "UpEmployeeInformation.findByAppointmentStatus", query = "SELECT u FROM UpEmployeeInformation u WHERE u.appointmentStatus = :appointmentStatus")})
public class UpEmployeeInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "_employeeID")
    private String employeeID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "_position")
    private String position;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_dateOfAppointment")
    @Temporal(TemporalType.DATE)
    private Date dateOfAppointment;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "_appointmentStatus")
    private String appointmentStatus;
    @JoinColumn(name = "_employeeID", referencedColumnName = "_systemID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persons persons;
    @JoinColumn(name = "_physicalAddress", referencedColumnName = "_addressID")
    @ManyToOne(optional = false)
    private Addressess physicalAddress;

    public UpEmployeeInformation() {
    }

    public UpEmployeeInformation(String employeeID) {
        this.employeeID = employeeID;
    }

    public UpEmployeeInformation(String employeeID, String position, Date dateOfAppointment, String appointmentStatus) {
        this.employeeID = employeeID;
        this.position = position;
        this.dateOfAppointment = dateOfAppointment;
        this.appointmentStatus = appointmentStatus;
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

    public Persons getPersons() {
        return persons;
    }

    public void setPersons(Persons persons) {
        this.persons = persons;
    }

    public Addressess getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(Addressess physicalAddress) {
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
        if (!(object instanceof UpEmployeeInformation)) {
            return false;
        }
        UpEmployeeInformation other = (UpEmployeeInformation) object;
        if ((this.employeeID == null && other.employeeID != null) || (this.employeeID != null && !this.employeeID.equals(other.employeeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEnties.UpEmployeeInformation[ employeeID=" + employeeID + " ]";
    }
    
}
