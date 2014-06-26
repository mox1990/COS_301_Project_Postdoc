//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.26 at 10:51:58 AM CAT 
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
 *         &lt;element name="journalPublications" type="{}reference"/>
 *         &lt;element name="bookChapterPublications" type="{}reference"/>
 *         &lt;element name="conferencePaperPublications" type="{}reference"/>
 *         &lt;element name="scientificPublicationsArticles" type="{}reference"/>
 *         &lt;element name="technicalPolicyReports" type="{}reference"/>
 *         &lt;element name="patents" type="{}reference"/>
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
    "journalPublications",
    "bookChapterPublications",
    "conferencePaperPublications",
    "scientificPublicationsArticles",
    "technicalPolicyReports",
    "patents"
})
@XmlRootElement(name = "researchOutput")
public class ResearchOutput {

    @XmlElement(required = true)
    protected Reference journalPublications;
    @XmlElement(required = true)
    protected Reference bookChapterPublications;
    @XmlElement(required = true)
    protected Reference conferencePaperPublications;
    @XmlElement(required = true)
    protected Reference scientificPublicationsArticles;
    @XmlElement(required = true)
    protected Reference technicalPolicyReports;
    @XmlElement(required = true)
    protected Reference patents;

    /**
     * Gets the value of the journalPublications property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getJournalPublications() {
        return journalPublications;
    }

    /**
     * Sets the value of the journalPublications property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setJournalPublications(Reference value) {
        this.journalPublications = value;
    }

    /**
     * Gets the value of the bookChapterPublications property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getBookChapterPublications() {
        return bookChapterPublications;
    }

    /**
     * Sets the value of the bookChapterPublications property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setBookChapterPublications(Reference value) {
        this.bookChapterPublications = value;
    }

    /**
     * Gets the value of the conferencePaperPublications property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getConferencePaperPublications() {
        return conferencePaperPublications;
    }

    /**
     * Sets the value of the conferencePaperPublications property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setConferencePaperPublications(Reference value) {
        this.conferencePaperPublications = value;
    }

    /**
     * Gets the value of the scientificPublicationsArticles property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getScientificPublicationsArticles() {
        return scientificPublicationsArticles;
    }

    /**
     * Sets the value of the scientificPublicationsArticles property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setScientificPublicationsArticles(Reference value) {
        this.scientificPublicationsArticles = value;
    }

    /**
     * Gets the value of the technicalPolicyReports property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getTechnicalPolicyReports() {
        return technicalPolicyReports;
    }

    /**
     * Sets the value of the technicalPolicyReports property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setTechnicalPolicyReports(Reference value) {
        this.technicalPolicyReports = value;
    }

    /**
     * Gets the value of the patents property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getPatents() {
        return patents;
    }

    /**
     * Sets the value of the patents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setPatents(Reference value) {
        this.patents = value;
    }

}
