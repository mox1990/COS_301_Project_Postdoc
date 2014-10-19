/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.auxiliary.util.ClassMethodVerificationUtil;
import com.softserve.ejb.nonapplicationservices.PrePostConditionalManagementServicesLocal;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.PrePostConditionMethod;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "prePostConditionEditBean")
@ConversationScoped
public class PrePostConditionEditBean implements Serializable {

    private PrePostConditionMethod prePostConditionMethod;
    private TreeNode parameterMethodsRoot;
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    
    @EJB
    private PrePostConditionalManagementServicesLocal prePostConditionalManagementServicesLocal;
    
    /**
     * Creates a new instance of PrePostConditionEditRequestBean
     */
    public PrePostConditionEditBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        try 
        {

            prePostConditionMethod = sessionManagerBean.getObjectFromSessionStorage("PREPOSTCONDITION", PrePostConditionMethod.class);
            
            List<String> params = prePostConditionMethod.getMethodParametersDecode();

            parameterMethodsRoot = new DefaultTreeNode("ROOT", "Parameter tree", null);
            ClassMethodVerificationUtil classMethodVerificationUtil = new ClassMethodVerificationUtil();
            Method cc = classMethodVerificationUtil.findMethod(prePostConditionMethod.getMethodClassName(), prePostConditionMethod.getMethodName(), params);

            for(int i = 0; i < cc.getParameterTypes().length; i++)
            {
                System.out.println(cc);
                System.out.println(cc.getParameters()[i]);
                TreeNode treeNode = new DefaultTreeNode("PARAM", cc.getParameters()[i].getName(), parameterMethodsRoot);
                findAllMethods(treeNode, cc.getParameterTypes()[i], 1, 3);
            }

        } 
        catch (Exception e) 
        {
            ExceptionUtil.handleException(null, e);
            ExceptionUtil.logException(PrePostConditionEditBean.class, e);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToPreviousBreadCrumb());
        }
    }
    
    private void findAllMethods(TreeNode parent, Class c, int curDepth, int depthLimit)
    {
        if(c != null && (curDepth < depthLimit))
        {
            System.out.println("is a method");
            for(Method method : c.getMethods())
            {                 
                if(method.getModifiers() == Method.DECLARED)
                {
                    TreeNode child = new DefaultTreeNode("METHOD", method.getName(), parent);
                    findAllMethods(child, method.getReturnType(),curDepth + 1,depthLimit);
                }
            }
        }
        else
        {
            System.out.println("Not a method");
        }
    }

    public PrePostConditionMethod getPrePostConditionMethod() {
        return prePostConditionMethod;
    }

    public void setPrePostConditionMethod(PrePostConditionMethod prePostConditionMethod) {
        this.prePostConditionMethod = prePostConditionMethod;
    }

    public TreeNode getParameterMethodsRoot() {
        return parameterMethodsRoot;
    }
    
    
    public String preformPrePostCondtionUpdate()
    {
        try
        {
            PrePostConditionMethod prePostConditionMethod1 = sessionManagerBean.getObjectFromSessionStorage("PREPOSTCONDITION", PrePostConditionMethod.class);
            
            prePostConditionMethod1.setPreConditionScript(prePostConditionMethod.getPreConditionScript());
            prePostConditionMethod1.setPostConditionScript(prePostConditionMethod.getPostConditionScript());
            
            prePostConditionalManagementServicesLocal.updatePrePostConditionalMethod(sessionManagerBean.getSession(), prePostConditionMethod);
            
            return navigationManagerBean.goToPreviousBreadCrumb();
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(null, ex);
            ExceptionUtil.logException(PrePostConditionEditBean.class, ex);
            return "";
        }
    }
    
    
}
