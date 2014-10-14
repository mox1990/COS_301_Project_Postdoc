/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBEntities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.auxiliary.requestresponseclasses.MethodParameters;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Entity
@Table(name = "pre_post_condition_method")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrePostConditionMethod.findAll", query = "SELECT p FROM PrePostConditionMethod p"),
    @NamedQuery(name = "PrePostConditionMethod.findByPrePostConditionMethodID", query = "SELECT p FROM PrePostConditionMethod p WHERE p.prePostConditionMethodID = :prePostConditionMethodID"),
    @NamedQuery(name = "PrePostConditionMethod.findByMethodName", query = "SELECT p FROM PrePostConditionMethod p WHERE p.methodName = :methodName"),
    @NamedQuery(name = "PrePostConditionMethod.findByMethodClassName", query = "SELECT p FROM PrePostConditionMethod p WHERE p.methodClassName = :methodClassName"),
    @NamedQuery(name = "PrePostConditionMethod.findByScriptLangName", query = "SELECT p FROM PrePostConditionMethod p WHERE p.scriptLangName = :scriptLangName")})
public class PrePostConditionMethod implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_prePostConditionMethodID")
    private Long prePostConditionMethodID;
    @Size(max = 250)
    @Column(name = "_methodName")
    private String methodName;
    @Size(max = 250)
    @Column(name = "_methodClassName")
    private String methodClassName;
    @Lob
    @Size(max = 65535)
    @Column(name = "_methodParameters")
    private String methodParameters;
    @Size(max = 250)
    @Column(name = "_scriptLangName")
    private String scriptLangName;
    @Lob
    @Size(max = 65535)
    @Column(name = "_preConditionScript")
    private String preConditionScript;
    @Lob
    @Size(max = 65535)
    @Column(name = "_postConditionScript")
    private String postConditionScript;

    public PrePostConditionMethod() {
    }

    public PrePostConditionMethod(Long prePostConditionMethodID) {
        this.prePostConditionMethodID = prePostConditionMethodID;
    }

    public Long getPrePostConditionMethodID() {
        return prePostConditionMethodID;
    }

    public void setPrePostConditionMethodID(Long prePostConditionMethodID) {
        this.prePostConditionMethodID = prePostConditionMethodID;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodClassName() {
        return methodClassName;
    }

    public void setMethodClassName(String methodClassName) {
        this.methodClassName = methodClassName;
    }

    public String getMethodParameters() {
        return methodParameters;
    }
    
    public void setMethodParametersEncode(List<String> parameters) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String[] s = new String[1];
        MethodParameters mp = new MethodParameters(parameters.toArray(s));
        
        setMethodParameters(objectMapper.writeValueAsString(mp));
        
    }
    
    public List<String> getMethodParametersDecode() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        MethodParameters mp = objectMapper.readValue(methodParameters, MethodParameters.class);
        
        return Arrays.asList(mp.getParameters());
    }

    public void setMethodParameters(String methodParameters) {
        this.methodParameters = methodParameters;
    }

    public String getScriptLangName() {
        return scriptLangName;
    }

    public void setScriptLangName(String scriptLangName) {
        this.scriptLangName = scriptLangName;
    }

    public String getPreConditionScript() {
        return preConditionScript;
    }

    public void setPreConditionScript(String preConditionScript) {
        this.preConditionScript = preConditionScript;
    }

    public String getPostConditionScript() {
        return postConditionScript;
    }

    public void setPostConditionScript(String postConditionScript) {
        this.postConditionScript = postConditionScript;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prePostConditionMethodID != null ? prePostConditionMethodID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrePostConditionMethod)) {
            return false;
        }
        PrePostConditionMethod other = (PrePostConditionMethod) object;
        if ((this.prePostConditionMethodID == null && other.prePostConditionMethodID != null) || (this.prePostConditionMethodID != null && !this.prePostConditionMethodID.equals(other.prePostConditionMethodID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softserve.persistence.DBEntities.PrePostConditionMethod[ prePostConditionMethodID=" + prePostConditionMethodID + " ]";
    }
    
}
