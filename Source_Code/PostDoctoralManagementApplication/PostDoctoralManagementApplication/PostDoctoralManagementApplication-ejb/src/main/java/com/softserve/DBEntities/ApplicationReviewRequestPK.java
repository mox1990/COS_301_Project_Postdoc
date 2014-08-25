/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Embeddable
public class ApplicationReviewRequestPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "_person")
    private String person;
    @Basic(optional = false)
    @NotNull
    @Column(name = "_application")
    private long application;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "_type")
    private String type;

    public ApplicationReviewRequestPK() {
    }

    public ApplicationReviewRequestPK(String person, long application, String type) {
        this.person = person;
        this.application = application;
        this.type = type;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public long getApplication() {
        return application;
    }

    public void setApplication(long application) {
        this.application = application;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (person != null ? person.hashCode() : 0);
        hash += (int) application;
        hash += (type != null ? type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApplicationReviewRequestPK)) {
            return false;
        }
        ApplicationReviewRequestPK other = (ApplicationReviewRequestPK) object;
        if ((this.person == null && other.person != null) || (this.person != null && !this.person.equals(other.person))) {
            return false;
        }
        if (this.application != other.application) {
            return false;
        }
        if ((this.type == null && other.type != null) || (this.type != null && !this.type.equals(other.type))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.ApplicationReviewRequestPK[ person=" + person + ", application=" + application + ", type=" + type + " ]";
    }
    
}
