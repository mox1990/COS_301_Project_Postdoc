/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "application_review_request")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ApplicationReviewRequest.findAll", query = "SELECT a FROM ApplicationReviewRequest a"),
    @NamedQuery(name = "ApplicationReviewRequest.findByPerson", query = "SELECT a FROM ApplicationReviewRequest a WHERE a.applicationReviewRequestPK.person = :person"),
    @NamedQuery(name = "ApplicationReviewRequest.findByApplication", query = "SELECT a FROM ApplicationReviewRequest a WHERE a.applicationReviewRequestPK.application = :application"),
    @NamedQuery(name = "ApplicationReviewRequest.findByType", query = "SELECT a FROM ApplicationReviewRequest a WHERE a.applicationReviewRequestPK.type = :type")})
public class ApplicationReviewRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ApplicationReviewRequestPK applicationReviewRequestPK;
    @JoinColumn(name = "_person", referencedColumnName = "_systemID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Person person1;
    @JoinColumn(name = "_application", referencedColumnName = "_applicationID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Application application1;

    public ApplicationReviewRequest() {
    }

    public ApplicationReviewRequest(ApplicationReviewRequestPK applicationReviewRequestPK) {
        this.applicationReviewRequestPK = applicationReviewRequestPK;
    }

    public ApplicationReviewRequest(String person, long application, String type) {
        this.applicationReviewRequestPK = new ApplicationReviewRequestPK(person, application, type);
    }

    public ApplicationReviewRequestPK getApplicationReviewRequestPK() {
        return applicationReviewRequestPK;
    }

    public void setApplicationReviewRequestPK(ApplicationReviewRequestPK applicationReviewRequestPK) {
        this.applicationReviewRequestPK = applicationReviewRequestPK;
    }

    public Person getPerson1() {
        return person1;
    }

    public void setPerson1(Person person1) {
        this.person1 = person1;
    }

    public Application getApplication1() {
        return application1;
    }

    public void setApplication1(Application application1) {
        this.application1 = application1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (applicationReviewRequestPK != null ? applicationReviewRequestPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApplicationReviewRequest)) {
            return false;
        }
        ApplicationReviewRequest other = (ApplicationReviewRequest) object;
        if ((this.applicationReviewRequestPK == null && other.applicationReviewRequestPK != null) || (this.applicationReviewRequestPK != null && !this.applicationReviewRequestPK.equals(other.applicationReviewRequestPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.DBEntities.ApplicationReviewRequest[ applicationReviewRequestPK=" + applicationReviewRequestPK + " ]";
    }
    
}
