//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.17 at 09:21:35 PM CAT 
//


package auto.softserve.XMLEntities.CV;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="conferencesWorkshopsCourses" type="{}items"/>
 *         &lt;element name="teamworkCollaboration" type="{}items"/>
 *         &lt;element name="memberships" type="{}items"/>
 *         &lt;element name="vistsToInsitutions" type="{}items"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "conferencesWorkshopsCourses",
    "teamworkCollaboration",
    "memberships",
    "vistsToInsitutions"
})
@XmlRootElement(name = "otherContributions")
public class OtherContributions {

    @XmlElement(required = true)
    protected Items conferencesWorkshopsCourses;
    @XmlElement(required = true)
    protected Items teamworkCollaboration;
    @XmlElement(required = true)
    protected Items memberships;
    @XmlElement(required = true)
    protected Items vistsToInsitutions;

    /**
     * Gets the value of the conferencesWorkshopsCourses property.
     * 
     * @return
     *     possible object is
     *     {@link Items }
     *     
     */
    public Items getConferencesWorkshopsCourses() {
        return conferencesWorkshopsCourses;
    }

    /**
     * Sets the value of the conferencesWorkshopsCourses property.
     * 
     * @param value
     *     allowed object is
     *     {@link Items }
     *     
     */
    public void setConferencesWorkshopsCourses(Items value) {
        this.conferencesWorkshopsCourses = value;
    }

    /**
     * Gets the value of the teamworkCollaboration property.
     * 
     * @return
     *     possible object is
     *     {@link Items }
     *     
     */
    public Items getTeamworkCollaboration() {
        return teamworkCollaboration;
    }

    /**
     * Sets the value of the teamworkCollaboration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Items }
     *     
     */
    public void setTeamworkCollaboration(Items value) {
        this.teamworkCollaboration = value;
    }

    /**
     * Gets the value of the memberships property.
     * 
     * @return
     *     possible object is
     *     {@link Items }
     *     
     */
    public Items getMemberships() {
        return memberships;
    }

    /**
     * Sets the value of the memberships property.
     * 
     * @param value
     *     allowed object is
     *     {@link Items }
     *     
     */
    public void setMemberships(Items value) {
        this.memberships = value;
    }

    /**
     * Gets the value of the vistsToInsitutions property.
     * 
     * @return
     *     possible object is
     *     {@link Items }
     *     
     */
    public Items getVistsToInsitutions() {
        return vistsToInsitutions;
    }

    /**
     * Sets the value of the vistsToInsitutions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Items }
     *     
     */
    public void setVistsToInsitutions(Items value) {
        this.vistsToInsitutions = value;
    }

}
