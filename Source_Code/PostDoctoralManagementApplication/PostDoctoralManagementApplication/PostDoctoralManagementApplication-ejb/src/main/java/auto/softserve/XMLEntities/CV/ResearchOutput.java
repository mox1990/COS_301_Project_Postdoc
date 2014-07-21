//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.21 at 12:57:26 PM CAT 
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
 *         &lt;element name="journalPublications" type="{}references"/>
 *         &lt;element name="bookChapterPublications" type="{}references"/>
 *         &lt;element name="conferencePaperPublications" type="{}references"/>
 *         &lt;element name="scientificPublicationsArticles" type="{}references"/>
 *         &lt;element name="technicalPolicyReports" type="{}references"/>
 *         &lt;element name="patents" type="{}references"/>
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
    protected References journalPublications;
    @XmlElement(required = true)
    protected References bookChapterPublications;
    @XmlElement(required = true)
    protected References conferencePaperPublications;
    @XmlElement(required = true)
    protected References scientificPublicationsArticles;
    @XmlElement(required = true)
    protected References technicalPolicyReports;
    @XmlElement(required = true)
    protected References patents;

    /**
     * Gets the value of the journalPublications property.
     * 
     * @return
     *     possible object is
     *     {@link References }
     *     
     */
    public References getJournalPublications() {
        return journalPublications;
    }

    /**
     * Sets the value of the journalPublications property.
     * 
     * @param value
     *     allowed object is
     *     {@link References }
     *     
     */
    public void setJournalPublications(References value) {
        this.journalPublications = value;
    }

    /**
     * Gets the value of the bookChapterPublications property.
     * 
     * @return
     *     possible object is
     *     {@link References }
     *     
     */
    public References getBookChapterPublications() {
        return bookChapterPublications;
    }

    /**
     * Sets the value of the bookChapterPublications property.
     * 
     * @param value
     *     allowed object is
     *     {@link References }
     *     
     */
    public void setBookChapterPublications(References value) {
        this.bookChapterPublications = value;
    }

    /**
     * Gets the value of the conferencePaperPublications property.
     * 
     * @return
     *     possible object is
     *     {@link References }
     *     
     */
    public References getConferencePaperPublications() {
        return conferencePaperPublications;
    }

    /**
     * Sets the value of the conferencePaperPublications property.
     * 
     * @param value
     *     allowed object is
     *     {@link References }
     *     
     */
    public void setConferencePaperPublications(References value) {
        this.conferencePaperPublications = value;
    }

    /**
     * Gets the value of the scientificPublicationsArticles property.
     * 
     * @return
     *     possible object is
     *     {@link References }
     *     
     */
    public References getScientificPublicationsArticles() {
        return scientificPublicationsArticles;
    }

    /**
     * Sets the value of the scientificPublicationsArticles property.
     * 
     * @param value
     *     allowed object is
     *     {@link References }
     *     
     */
    public void setScientificPublicationsArticles(References value) {
        this.scientificPublicationsArticles = value;
    }

    /**
     * Gets the value of the technicalPolicyReports property.
     * 
     * @return
     *     possible object is
     *     {@link References }
     *     
     */
    public References getTechnicalPolicyReports() {
        return technicalPolicyReports;
    }

    /**
     * Sets the value of the technicalPolicyReports property.
     * 
     * @param value
     *     allowed object is
     *     {@link References }
     *     
     */
    public void setTechnicalPolicyReports(References value) {
        this.technicalPolicyReports = value;
    }

    /**
     * Gets the value of the patents property.
     * 
     * @return
     *     possible object is
     *     {@link References }
     *     
     */
    public References getPatents() {
        return patents;
    }

    /**
     * Sets the value of the patents property.
     * 
     * @param value
     *     allowed object is
     *     {@link References }
     *     
     */
    public void setPatents(References value) {
        this.patents = value;
    }

}
