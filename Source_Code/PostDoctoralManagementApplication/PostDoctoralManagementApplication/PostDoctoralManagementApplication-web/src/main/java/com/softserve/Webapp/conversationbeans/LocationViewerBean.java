/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.Institution;
import com.softserve.Webapp.TreeNodeWrapper;
import com.softserve.Webapp.depenedentbeans.LocationFinderDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.LocationManagementServiceLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.tree.Tree;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "locationViewerBean")
@ConversationScoped
public class LocationViewerBean implements Serializable {

    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    @EJB
    private LocationManagementServiceLocal locationManagementServiceLocal;
    
    @Inject
    private LocationFinderDependBean locationFinderDependBean;
    
    private TreeNode root;
    private TreeNode selectedNode;
    private String name;
    
    /**
     * Creates a new instance of LocationViewerBean
     */
    public LocationViewerBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        
        root = new DefaultTreeNode("Institutions","Instiutions", null);
        locationFinderDependBean.init(null);
        List<Institution> institutions = locationFinderDependBean.getInstitutions();
        
        for(Institution institution : institutions)
        {            
            TreeNode node = new DefaultTreeNode("Institution",institution, root);
        }
        
        System.out.println("============================================Location viewer");
        
        /*for(Institution institution : institutions)
        {
            TreeNodeWrapper treeNodeWrapper = new TreeNodeWrapper() {

                @Override
                public String toString() {
                    return ((Institution) (getObject())).getName();
                }
            };
            
            treeNodeWrapper.setObject(institution);
            treeNodeWrapper.setLevel(1);
            
            TreeNode node = new DefaultTreeNode("Institution",treeNodeWrapper, root);
        }*/
    }
    
    public void onNodeSelect(NodeSelectEvent event)
    {
        expand(event.getTreeNode());
    }
    
    private void expand(TreeNode node)
    {
        switch (node.getType()) {
            case "Institution":
                loadFacultyNodes(node);
                break;
            case "Faculty":
                loadDepartmentNodes(node);
                break;
            case "Institutions":
                loadInstitutionNode(node);
                break;
        }
        
        TreeNode parent = node;
        
        while(parent != null)
        {            
            parent.setExpanded(true);
            parent = parent.getParent();
        }
    }
    
    public void loadInstitutionNode(TreeNode node)
    {
        List<Institution> institutions = locationFinderDependBean.loadAllInstitutions();
        
        for(Institution institution : institutions)
        {            
            boolean found = false;
            for(TreeNode childIn : node.getChildren())
            {
                if(childIn.getData().equals(institution))
                {
                    found = true;
                    break;
                }
            }

            if(!found)
            {                
                TreeNode child = new DefaultTreeNode("Institution",institution, root);
            }           
            
        }
    }
    
    public void loadFacultyNodes(TreeNode node)
    {
        if(node.getData().getClass() == Institution.class)
        {                        
            locationFinderDependBean.populateFaculties((Institution) node.getData());
            
            for(Faculty faculty : locationFinderDependBean.getFaculties())
            {
                boolean found = false;
                for(TreeNode childIn : node.getChildren())
                {
                    if(childIn.getData().equals(faculty))
                    {
                        found = true;
                        break;
                    }
                }
                
                if(!found)
                {                
                    TreeNode child = new DefaultTreeNode("Faculty",faculty, node);
                }
                
            }
            
            /*for(Faculty faculty : locationFinderDependBean.getFaculties())
            {
                TreeNodeWrapper treeNodeWrapper = new TreeNodeWrapper() {

                    @Override
                    public String toString() {
                        return ((Faculty) (getObject())).getName();
                    }
                };

                treeNodeWrapper.setObject(faculty);
                treeNodeWrapper.setLevel(2);
                
                TreeNode child = new DefaultTreeNode("Faculty",treeNodeWrapper, node);
            }*/
            
        }
    }
    
    public void loadDepartmentNodes(TreeNode node)
    {
        if(node.getData().getClass() == Faculty.class)
        {                        
            locationFinderDependBean.populateDepartments((Faculty) node.getData());
            
            for(Department department : locationFinderDependBean.getDepartments())
            {
                boolean found = false;
                for(TreeNode childIn : node.getChildren())
                {
                    if(childIn.getData().equals(department))
                    {
                        found = true;
                        break;
                    }
                }
                
                if(!found)
                {
                    TreeNode child = new DefaultTreeNode("Department",department, node);
                }

            } 
            
            
            /*for(Department department : locationFinderDependBean.getDepartments())
            {
                TreeNodeWrapper treeNodeWrapper = new TreeNodeWrapper() {

                    @Override
                    public String toString() {
                        return ((Department) (getObject())).getName();
                    }
                };

                treeNodeWrapper.setObject(department);
                treeNodeWrapper.setLevel(3);
                
                TreeNode child = new DefaultTreeNode("Department",treeNodeWrapper, node);
            }     */       
        }
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }
    
    public Institution getSelectedAsInstitution()
    {
        if(selectedNode.getData().getClass() == Institution.class)
        {
            return (Institution) selectedNode.getData();
        }
        else
        {
            return null;
        }
    }
    
    public Faculty getSelectedAsFaculty()
    {
        if(selectedNode.getData().getClass() == Faculty.class)
        {
            return (Faculty) selectedNode.getData();
        }
        else
        {
            return null;
        }
    }
    
    public Department getSelectedAsDepartment()
    {
        if(selectedNode.getData().getClass() == Department.class)
        {
            return (Department) selectedNode.getData();
        }
        else
        {
            return null;
        }
    }
    
    public String getNextType()
    {
        switch (selectedNode.getType()) {
            case "Institutions":
                return "Institution";
            case "Institution":
                return "Faculty";
            case "Faculty":
                return "Department";
        }
        
        return "";
    }
    
    public boolean nodeBelow()
    {
        return selectedNode != null && !selectedNode.getType().equals("Department");
    }
    
    public boolean currentNodeActive()
    {
        return selectedNode != null && !selectedNode.getType().equals("Institutions");
    }
    
    public void preformUpdateOfSelectedNode()
    {
        try
        {
            if(getSelectedAsDepartment() != null)
            {
                
                Department department = locationManagementServiceLocal.getDepartment(getSelectedAsDepartment().getDepartmentID());
                department.setName(getSelectedAsDepartment().getName());
                locationManagementServiceLocal.updateDepartment(sessionManagerBean.getSession(), department);
            }
            else if (getSelectedAsFaculty() != null)
            {
                Faculty faculty = locationManagementServiceLocal.getFaculty(getSelectedAsFaculty().getFacultyID());
                faculty.setName(getSelectedAsFaculty().getName());
                locationManagementServiceLocal.updateFaculty(sessionManagerBean.getSession(), faculty);
            }
            else if(getSelectedAsInstitution() != null)
            {
                Institution institution = locationManagementServiceLocal.getInstitution(getSelectedAsInstitution().getInstitutionID());
                institution.setName(getSelectedAsFaculty().getName());
                locationManagementServiceLocal.updateInstitution(sessionManagerBean.getSession(),institution);
            }
            else
            {
                throw new Exception("Invalid selection to preform update on");
            }
            
            expand(selectedNode);
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(null, ex);
            ExceptionUtil.logException(LocationViewerBean.class, ex);
        }
    }
    
    public void preformCreationOfNewLocation()
    {
        try
        {
            if(getSelectedAsFaculty() != null)
            {
                Faculty faculty = locationManagementServiceLocal.getFaculty(getSelectedAsFaculty().getFacultyID());
                
                Department department = new Department();
                department.setName(name);
                department.setFaculty(faculty);
                locationManagementServiceLocal.createDepartment(sessionManagerBean.getSession(), department);
            }
            else if (getSelectedAsInstitution() != null)
            {
                Institution institution = locationManagementServiceLocal.getInstitution(getSelectedAsInstitution().getInstitutionID());
                
                Faculty faculty = new Faculty();
                faculty.setInstitution(institution);
                faculty.setName(name);
                locationManagementServiceLocal.createFaculty(sessionManagerBean.getSession(), faculty);
            }
            else if(selectedNode == root)
            {
                Institution institution = new Institution();
                institution.setName(name);
                locationManagementServiceLocal.createInstitution(sessionManagerBean.getSession(),institution);
            }
            else
            {
                throw new Exception("Invalid selection to creation under");
            }
            
            expand(selectedNode);
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(null, ex);
            ExceptionUtil.logException(LocationViewerBean.class, ex);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
