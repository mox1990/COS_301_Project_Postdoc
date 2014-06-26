/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBEntities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "endorsement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Endorsement.findAll", query = "SELECT e FROM Endorsement e"),
    @NamedQuery(name = "Endorsement.findByEndorsementID", query = "SELECT e FROM Endorsement e WHERE e.endorsementID = :endorsementID"),
    @NamedQuery(name = "Endorsement.findByTimestamp", query = "SELECT e FROM Endorsement e WHERE e.timestamp = :timestamp"),
    @NamedQuery(name = "Endorsement.findByRank", query = "SELECT e FROM Endorsement e WHERE e.rank = :rank")})
public class Endorsement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "_endorsementID")
    private Long endorsementID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_rank")
    private int rank;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "_motivation")
    private String motivation;
    @JoinColumn(name = "_deanID", referencedColumnName = "_systemID")
    @ManyToOne(optional = false)
    private Person deanID;
    @OneToMany(mappedBy = "endorsementID")
    private Collection<Application> applicationCollection;

    public Endorsement() {
    }

    public Endorsement(Long endorsementID) {
        this.endorsementID = endorsementID;
    }

    public Endorsement(Long endorsementID, Date timestamp, int rank, String motivation) {
        this.endorsementID = endorsementID;
        this.timestamp = timestamp;
        this.rank = rank;
        this.motivation = motivation;
    }

    public Long getEndorsementID() {
        return endorsementID;
    }

    public void setEndorsementID(Long endorsementID) {
        this.endorsementID = endorsementID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public Person getDeanID() {
        return deanID;
    }

    public void setDeanID(Person deanID) {
        this.deanID = deanID;
    }

    @XmlTransient
    public Collection<Application> getApplicationCollection() {
        return applicationCollection;
    }

    public void setApplicationCollection(Collection<Application> applicationCollection) {
        this.applicationCollection = applicationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (endorsementID != null ? endorsementID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Endorsement)) {
            return false;
        }
        Endorsement other = (Endorsement) object;
        if ((this.endorsementID == null && other.endorsementID != null) || (this.endorsementID != null && !this.endorsementID.equals(other.endorsementID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEnties.Endorsement[ endorsementID=" + endorsementID + " ]";
    }
    
}
