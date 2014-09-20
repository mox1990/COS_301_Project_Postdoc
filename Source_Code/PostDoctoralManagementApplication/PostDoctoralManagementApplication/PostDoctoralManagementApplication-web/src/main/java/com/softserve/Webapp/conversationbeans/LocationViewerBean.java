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
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    
    @Inject
    private LocationFinderDependBean locationFinderDependBean;
    
    private TreeNode root;
    private TreeNode selectedNode;
    
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
        
        root = new DefaultTreeNode("Instiutions", null);
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
        TreeNode node = event.getTreeNode();
        
        if(node.getType().equals("Institution"))
        {
            loadFacultyNodes(node);
        }
        else if(node.getType().equals("Faculty"))
        {
            loadDepartmentNodes(node);
        }
        
        TreeNode parent = node;
        
        while(parent != null)
        {            
            parent.setExpanded(true);
            parent = parent.getParent();
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
    
    
    
}
