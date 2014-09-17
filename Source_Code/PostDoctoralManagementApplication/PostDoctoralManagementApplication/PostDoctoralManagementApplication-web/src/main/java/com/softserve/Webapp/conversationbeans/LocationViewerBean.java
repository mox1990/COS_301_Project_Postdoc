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
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    private LocationFinderDependBean locationFinderDependBean;
    
    private TreeNode root;
    private TreeNode selectedNode;
    
    /**
     * Creates a new instance of LocationViewerBean
     */
    public LocationViewerBean() {
    }
    
    public void init()
    {
        root = new DefaultTreeNode("Instiutions", null);
        locationFinderDependBean.init(null);
        List<Institution> institutions = locationFinderDependBean.getInstitutions();
        
        for(Institution institution : institutions)
        {
            TreeNodeWrapper treeNodeWrapper = new TreeNodeWrapper() {

                @Override
                public String toString() {
                    return ((Institution) (getObject())).getName();
                }
            };
            
            treeNodeWrapper.setObject(institution);
            treeNodeWrapper.setLevel(1);
            
            TreeNode node = new DefaultTreeNode(treeNodeWrapper, root);
        }
    }
    
    public void loadFacultyNodes(TreeNode node)
    {
        if(node.getData().getClass() == Institution.class)
        {                        
            locationFinderDependBean.populateFaculties((Institution) ((TreeNodeWrapper)node.getData()).getObject());
            
            for(Faculty faculty : locationFinderDependBean.getFaculties())
            {
                TreeNodeWrapper treeNodeWrapper = new TreeNodeWrapper() {

                    @Override
                    public String toString() {
                        return ((Faculty) (getObject())).getName();
                    }
                };

                treeNodeWrapper.setObject(faculty);
                treeNodeWrapper.setLevel(2);
                
                TreeNode child = new DefaultTreeNode(treeNodeWrapper, node);
            }
            
        }
    }
    
    public void loadDepartmentNodes(TreeNode node)
    {
        if(node.getData().getClass() == Faculty.class)
        {                        
            locationFinderDependBean.populateDepartments((Faculty) ((TreeNodeWrapper)node.getData()).getObject());
            
            for(Department department : locationFinderDependBean.getDepartments())
            {
                TreeNodeWrapper treeNodeWrapper = new TreeNodeWrapper() {

                    @Override
                    public String toString() {
                        return ((Department) (getObject())).getName();
                    }
                };

                treeNodeWrapper.setObject(department);
                treeNodeWrapper.setLevel(3);
                
                TreeNode child = new DefaultTreeNode(treeNodeWrapper, node);
            }            
        }
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
    
    
    
}
