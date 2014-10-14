/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.nonapplicationservices;

import auto.softserve.XMLEntities.PrePostConditional.Prepostconditionalmethods;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.persistence.DBEntities.PrePostConditionMethod;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface PrePostConditionalManagementServicesLocal {
    public List<PrePostConditionMethod> loadPrePostConditionalMethods(Session session) throws Exception;
    public void updatePrePostConditionalMethod(Session session, PrePostConditionMethod prePostConditionMethod) throws Exception;
    public PrePostConditionMethod findPrePostConditionMethodByClassAndName(Session session, String className, String methodName) throws Exception;
    
    public Boolean evaluatePreCondition(Session session, PrePostConditionMethod prePostConditionMethod, List<String> parameterNames, List<Object> parameterValues) throws Exception;
    public Boolean evaluatePostCondition(Session session, PrePostConditionMethod postConditionMethod, List<String> parameterNames, List<Object> parameterValues) throws Exception;
}