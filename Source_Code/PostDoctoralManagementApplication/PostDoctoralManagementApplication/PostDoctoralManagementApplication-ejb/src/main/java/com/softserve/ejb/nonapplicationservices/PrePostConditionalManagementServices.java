/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.nonapplicationservices;

import auto.softserve.XMLEntities.PrePostConditional.Prepostconditionalmethods;
import com.softserve.auxiliary.annotations.AuditableMethod;
import com.softserve.auxiliary.annotations.SecuredMethod;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.interceptors.AuditTrailInterceptor;
import com.softserve.auxiliary.interceptors.AuthenticationInterceptor;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.auxiliary.util.ClassMethodVerificationUtil;
import com.softserve.persistence.DBDAO.PrePostConditionMethodJpaController;
import com.softserve.persistence.DBEntities.PrePostConditionMethod;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuditTrailInterceptor.class, AuthenticationInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class PrePostConditionalManagementServices implements PrePostConditionalManagementServicesLocal {
    
    @PersistenceUnit(unitName = com.softserve.auxiliary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }

    public PrePostConditionalManagementServices(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected TransactionController getTransactionController()
    {
        return new TransactionController(emf);
    }
    
    protected EntityManager createEntityManager()
    {
        return emf.createEntityManager();
    }
    
    protected ClassMethodVerificationUtil getClassMethodVerificationUtil()
    {
        return new ClassMethodVerificationUtil();
    }
    
    
    public PrePostConditionalManagementServices() {
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @Override
    public List<PrePostConditionMethod> loadPrePostConditionalMethods(Session session) throws Exception 
    {
        EntityManager em = createEntityManager();
        try
        {
            return getDAOFactory(em).createPrePostConditionMethodDAO().findPrePostConditionMethodEntities();
        }
        finally
        {
            em.close();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public void updatePrePostConditionalMethod(Session session, PrePostConditionMethod prePostConditionMethod) throws Exception 
    {
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            PrePostConditionMethodJpaController prePostConditionMethodJpaController = dAOFactory.createPrePostConditionMethodDAO();
            
            PrePostConditionMethod p = prePostConditionMethodJpaController.findPrePostConditionMethod(prePostConditionMethod.getPrePostConditionMethodID());
            
            if(!getClassMethodVerificationUtil().doesMethodExist(p.getMethodClassName(), p.getMethodName(), p.getMethodParametersDecode()))
            {
                throw new Exception("Method does not exist.");
            }
            
            if(prePostConditionMethod.getScriptLangName() == null || prePostConditionMethod.getScriptLangName().equals(""))
            {
                p.setScriptLangName(com.softserve.auxiliary.constants.SystemConstants.SCRIPT_ENGINE_NAME_JAVASCRIPT);
            }
            else
            {
                p.setScriptLangName(prePostConditionMethod.getScriptLangName());
            }
            
            
            
            p.setPreConditionScript(prePostConditionMethod.getPreConditionScript());
            p.setPostConditionScript(prePostConditionMethod.getPostConditionScript());
            dAOFactory.createPrePostConditionMethodDAO().edit(p);
            
            transactionController.CommitTransaction();
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {})
    @Override
    public PrePostConditionMethod findPrePostConditionMethodByClassAndName(Session session, String className, String methodName, List<String> parameters) throws Exception 
    {
        EntityManager em = createEntityManager();
        try
        {            
            if(getClassMethodVerificationUtil().doesMethodExist(className, methodName, parameters))
            {
                return getDAOFactory(em).createPrePostConditionMethodDAO().findPrePostConditionByClassAndMethodName(methodName, className, parameters);
            }
            else
            {
                return null;
            }
        }
        finally
        {
            em.close();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {})
    @Override
    public Boolean evaluatePreCondition(Session session, PrePostConditionMethod prePostConditionMethod, List<String> parameterNames, List<Object> parameterValues) throws Exception 
    {
        return evaluateScript(prePostConditionMethod.getMethodClassName(), prePostConditionMethod.getMethodName(), prePostConditionMethod.getMethodParametersDecode(), prePostConditionMethod.getPostConditionScript(), prePostConditionMethod.getScriptLangName(), parameterNames, parameterValues);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {})
    @Override
    public Boolean evaluatePostCondition(Session session, PrePostConditionMethod prePostConditionMethod, List<String> parameterNames, List<Object> parameterValues) throws Exception 
    {
        return evaluateScript(prePostConditionMethod.getMethodClassName(), prePostConditionMethod.getMethodName(), prePostConditionMethod.getMethodParametersDecode(), prePostConditionMethod.getPostConditionScript(), prePostConditionMethod.getScriptLangName(), parameterNames, parameterValues);
    }
    
    private Boolean evaluateScript(String className, String methodName, List<String> parameters, String script, String lang, List<String> parameterNames, List<Object> parameterValues) throws Exception
    {
        if(!(parameterNames.size() == parameterValues.size() && parameters.size() == parameterNames.size()))
        {
            throw new Exception("Parameter sizes dont match.");
        }
        
        if(getClassMethodVerificationUtil().doesMethodExist(className, methodName, parameters))
        {
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            if(lang == null || lang.equals(""))
            {
                lang = com.softserve.auxiliary.constants.SystemConstants.SCRIPT_ENGINE_NAME_JAVASCRIPT;
            }
            
            ScriptEngine scriptEngine = scriptEngineManager.getEngineByName(lang);
            Bindings bindings = new SimpleBindings();
            
            for(int i = 0; i < parameterNames.size(); i++)
            {
                bindings.put(parameterNames.get(i),parameterValues.get(i));
            } 
            
            Object result = scriptEngine.eval(script, bindings);
            
            if(result == null || !result.getClass().equals(Boolean.class))
            {
                return false;
            }
            else
            {
                return (Boolean) result;
            }
        }
        else
        {
            return false;
        }
        
    }
    
    

    
    
}
