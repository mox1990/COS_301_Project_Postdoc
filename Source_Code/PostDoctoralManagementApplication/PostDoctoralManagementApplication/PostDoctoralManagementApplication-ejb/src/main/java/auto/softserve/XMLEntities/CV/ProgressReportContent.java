//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.20 at 02:30:30 AM CAT 
//


package auto.softserve.XMLEntities.CV;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="projectAimsAttainment">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="aimAttainment" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="projectOutcomesAttainment">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="outcomeAttainment" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="researchOutput">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="references" type="{http://CommonTypesNamespace/RefernceTypes}reference" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="selfEvaluation" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "projectAimsAttainment",
    "projectOutcomesAttainment",
    "researchOutput",
    "selfEvaluation"
})
@XmlRootElement(name = "ProgressReportContent")
public class ProgressReportContent {

    @XmlElement(required = true)
    protected ProgressReportContent.ProjectAimsAttainment projectAimsAttainment;
    @XmlElement(required = true)
    protected ProgressReportContent.ProjectOutcomesAttainment projectOutcomesAttainment;
    @XmlElement(required = true)
    protected ProgressReportContent.ResearchOutput researchOutput;
    @XmlElement(required = true)
    protected String selfEvaluation;

    /**
     * Gets the value of the projectAimsAttainment property.
     * 
     * @return
     *     possible object is
     *     {@link ProgressReportContent.ProjectAimsAttainment }
     *     
     */
    public ProgressReportContent.ProjectAimsAttainment getProjectAimsAttainment() {
        return projectAimsAttainment;
    }

    /**
     * Sets the value of the projectAimsAttainment property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProgressReportContent.ProjectAimsAttainment }
     *     
     */
    public void setProjectAimsAttainment(ProgressReportContent.ProjectAimsAttainment value) {
        this.projectAimsAttainment = value;
    }

    /**
     * Gets the value of the projectOutcomesAttainment property.
     * 
     * @return
     *     possible object is
     *     {@link ProgressReportContent.ProjectOutcomesAttainment }
     *     
     */
    public ProgressReportContent.ProjectOutcomesAttainment getProjectOutcomesAttainment() {
        return projectOutcomesAttainment;
    }

    /**
     * Sets the value of the projectOutcomesAttainment property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProgressReportContent.ProjectOutcomesAttainment }
     *     
     */
    public void setProjectOutcomesAttainment(ProgressReportContent.ProjectOutcomesAttainment value) {
        this.projectOutcomesAttainment = value;
    }

    /**
     * Gets the value of the researchOutput property.
     * 
     * @return
     *     possible object is
     *     {@link ProgressReportContent.ResearchOutput }
     *     
     */
    public ProgressReportContent.ResearchOutput getResearchOutput() {
        return researchOutput;
    }

    /**
     * Sets the value of the researchOutput property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProgressReportContent.ResearchOutput }
     *     
     */
    public void setResearchOutput(ProgressReportContent.ResearchOutput value) {
        this.researchOutput = value;
    }

    /**
     * Gets the value of the selfEvaluation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelfEvaluation() {
        return selfEvaluation;
    }

    /**
     * Sets the value of the selfEvaluation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelfEvaluation(String value) {
        this.selfEvaluation = value;
    }


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
     *         &lt;element name="aimAttainment" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "aimAttainment"
    })
    public static class ProjectAimsAttainment {

        @XmlElement(required = true)
        protected List<String> aimAttainment;

        /**
         * Gets the value of the aimAttainment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the aimAttainment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAimAttainment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getAimAttainment() {
            if (aimAttainment == null) {
                aimAttainment = new ArrayList<String>();
            }
            return this.aimAttainment;
        }

    }


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
     *         &lt;element name="outcomeAttainment" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "outcomeAttainment"
    })
    public static class ProjectOutcomesAttainment {

        @XmlElement(required = true)
        protected List<String> outcomeAttainment;

        /**
         * Gets the value of the outcomeAttainment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the outcomeAttainment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOutcomeAttainment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getOutcomeAttainment() {
            if (outcomeAttainment == null) {
                outcomeAttainment = new ArrayList<String>();
            }
            return this.outcomeAttainment;
        }

    }


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
     *         &lt;element name="references" type="{http://CommonTypesNamespace/RefernceTypes}reference" maxOccurs="unbounded"/>
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
        "references"
    })
    public static class ResearchOutput {

        @XmlElement(required = true)
        protected List<Reference> references;

        /**
         * Gets the value of the references property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the references property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReferences().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Reference }
         * 
         * 
         */
        public List<Reference> getReferences() {
            if (references == null) {
                references = new ArrayList<Reference>();
            }
            return this.references;
        }

    }

}
