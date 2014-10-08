/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import ar.com.fdvs.dj.domain.DynamicReport;
import com.softserve.Webapp.depenedentbeans.ApplicationFilterDependBean;
import com.softserve.Webapp.depenedentbeans.PersonFilterDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.auxillary.requestresponseclasses.DynamicReportCreationRequest;
import com.softserve.auxillary.requestresponseclasses.DynamicReportExportRequest;
import com.softserve.auxillary.requestresponseclasses.SelectedColumn;
import com.softserve.ejb.nonapplicationservices.ReportServicesLocal;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "reportGeneratorBean")
@ConversationScoped
public class ReportGeneratorBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    
    @Inject 
    private Conversation conversation;
    
    @Inject
    private PersonFilterDependBean personFilterDependBean;
    @Inject
    private ApplicationFilterDependBean applicationFilterDependBean;
    
    @EJB
    private ReportServicesLocal reportServicesLocal;
    
    private int wizardTabIndex;
    private final int MAX_TAB_INDEX = 4;
    
    private DynamicReport dynamicReport;
    private DynamicReportCreationRequest dynamicReportCreationRequest; 
    private DynamicReportExportRequest dynamicReportExportRequest;
    
    private String selectedQueryType;
    private List<List<Object>> selectedData;
    private List<String> selectedColumns;
    
    private TreeNode entityColumnSelectionRoot;
    private TreeNode entityDataSelectionRoot;
    
    private TreeNode[] selectedEntityColumns;
    private TreeNode[] selectedEntityData;
    
    /**
     * Creates a new instance of ReportGeneratorBean
     */
    public ReportGeneratorBean() {
    }
    
    @PostConstruct
    public void init()
    {
        
        try
        {
            conversationManagerBean.registerConversation(conversation);
            conversationManagerBean.startConversation(conversation);
            
            wizardTabIndex = 0;
            dynamicReport = null;
            dynamicReportCreationRequest = null;
            dynamicReportExportRequest = null;

            selectedQueryType = com.softserve.Webapp.constants.WebappConstants.QUERY_ALL_USERS;
            selectedEntityData = new TreeNode[0];
            selectedEntityColumns = new TreeNode[0];
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(null, ex);
            ExceptionUtil.logException(ReportGeneratorBean.class, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToWelcomeView());
        }
    }

    public PersonFilterDependBean getPersonFilterDependBean() {
        return personFilterDependBean;
    }

    public int getWizardTabIndex() {
        return wizardTabIndex;
    }

    public String getSelectedQueryType() {
        return selectedQueryType;
    }

    public ApplicationFilterDependBean getApplicationFilterDependBean() {
        return applicationFilterDependBean;
    }

    public DynamicReportCreationRequest getDynamicReportCreationRequest() {
        return dynamicReportCreationRequest;
    }

    public TreeNode getEntityColumnSelectionRoot() {
        return entityColumnSelectionRoot;
    }

    public TreeNode getEntityDataSelectionRoot() {
        return entityDataSelectionRoot;
    }

    public TreeNode[] getSelectedEntityColumns() {
        return selectedEntityColumns;
    }

    public TreeNode[] getSelectedEntityData() {
        return selectedEntityData;
    }
    
    
    public List<String> getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(List<String> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public void setSelectedData(ArrayList<List<Object>> selectedData) {
        this.selectedData = selectedData;
    }

    public void setSelectedQueryType(String selectedQueryType) {
        this.selectedQueryType = selectedQueryType;
    }

    public void setSelectedEntityColumns(TreeNode[] selectedEntityColumns) {
        this.selectedEntityColumns = selectedEntityColumns;
    }

    public void setSelectedEntityData(TreeNode[] selectedEntityData) {
        this.selectedEntityData = selectedEntityData;
    }

    public void setWizardTabIndex(int wizardTabIndex) {
        this.wizardTabIndex = wizardTabIndex;
    }
    
        
    
    public void goBack()
    {
        if(wizardTabIndex > 0 && wizardTabIndex <= MAX_TAB_INDEX)
        {
            wizardTabIndex--;
        }
    }
    
    public void completeQuerySelectionStep()
    {   
        try 
        {
            entityDataSelectionRoot = new DefaultTreeNode("Root", "Data selection", null);
            List<Object> data = null;
            
            switch (selectedQueryType)
            {
                case com.softserve.Webapp.constants.WebappConstants.QUERY_ALL_APPLICATION:                                         
                    data = Arrays.asList(reportServicesLocal.loadAllApplicationEntities(sessionManagerBean.getSession()).toArray());
                    break;
                    
                case com.softserve.Webapp.constants.WebappConstants.QUERY_ALL_USERS:
                    data = Arrays.asList(reportServicesLocal.loadAllPersonEntities(sessionManagerBean.getSession()).toArray());
                    break;
                    
                case com.softserve.Webapp.constants.WebappConstants.QUERY_ALL_AUDIT_LOG_ENTRIES:
                    data = Arrays.asList(reportServicesLocal.loadAllAuditLogEntries(sessionManagerBean.getSession()).toArray());
                    break;
                    
                default:
                    break;
            }
            
            for(Object entity : data)
            {
                boolean found = false;
                TreeNode parent = null;
                for(TreeNode node : entityDataSelectionRoot.getChildren())
                {
                    if(node.getData().equals(entity.getClass().getSimpleName() + "s"))
                    {
                        parent = node;
                        found = true;
                        break;
                    }
                }
                
                if(!found)
                {
                    parent = new DefaultTreeNode("Data group", entity.getClass().getSimpleName() + "s", entityDataSelectionRoot);
                }

                DefaultTreeNode defaultTreeNode = new DefaultTreeNode(entity.getClass().getSimpleName(), entity, parent);
                
            }
              
            wizardTabIndex = 1;
        } 
        catch (Exception e) 
        {
            ExceptionUtil.handleException(null, e);
            ExceptionUtil.logException(ReportGeneratorBean.class, e);
        }
    }
    
    public void completeSelectionRefineStep()
    {
        try 
        {            
            selectedData = null;
            selectedData = new ArrayList<List<Object>>();            
            
            List<String> alreadyFoundDataGroups = new ArrayList<String>();
            
            for(TreeNode treeNode : selectedEntityData)
            {
                if(!treeNode.getType().equals("Data group") && !treeNode.getType().equals("Root"))
                {
                    int index = alreadyFoundDataGroups.indexOf(treeNode.getData().getClass().getSimpleName() + "s");

                    if(index > -1)
                    {
                        selectedData.get(index).add(treeNode.getData());
                    }
                    else
                    {                    
                        List<Object> entities = new ArrayList<Object>();
                        entities.add(treeNode.getData());
                        selectedData.add(entities);
                        alreadyFoundDataGroups.add(treeNode.getData().getClass().getSimpleName() + "s");
                    }
                }
            }
            
            entityColumnSelectionRoot = new DefaultTreeNode("Root", "Column selection", null);
            
            for(List<Object> column : selectedData)
            {
                Object entity = column.get(0);
                List<SelectedColumn> fields = convertColumnNamesToSelectedColumns(reportServicesLocal.getFieldNamesForEntity(entity), entity.getClass().getName());
                TreeNode parent = new DefaultTreeNode("Data group", entity.getClass().getSimpleName(), entityColumnSelectionRoot);
                
                for(SelectedColumn selectedColumn : fields)
                {
                    DefaultTreeNode defaultTreeNode = new DefaultTreeNode("Column", selectedColumn, parent);
                }
            }            
            
            
            wizardTabIndex = 2;
        } 
        catch (Exception e) 
        {
            ExceptionUtil.handleException(null, e);
            ExceptionUtil.logException(ReportGeneratorBean.class, e);
        }
    }
    
    
    
    private List<SelectedColumn> convertColumnNamesToSelectedColumns(List<String> columnNames, String classType)
    {
        List<SelectedColumn> names = new ArrayList<SelectedColumn>();
        
        for(String coloumn : columnNames)
        {
            String s = coloumn;
            
            s = s.trim();
            s = s.replaceAll("_", "");
            s = Character.toUpperCase(s.charAt(0))  + s.substring(1);
            
            String out = "";
            
            for(int i = 0; i < s.length(); i++)
            {
                out += s.charAt(i);
                if(s.length() > i + 1 && Character.isLetter(s.charAt(i)) && Character.isUpperCase(s.charAt(i + 1)) && Character.isLowerCase(s.charAt(i)))
                {
                    out += " ";
                }
            }
            
            names.add(new SelectedColumn(out, coloumn, classType));
        }
        
        return names;
    }
    
    
    public void completeColumnSelectionStep()
    {
        try 
        {            
            dynamicReportCreationRequest = new DynamicReportCreationRequest();
            dynamicReportCreationRequest.setSelectedColumns( new ArrayList<SelectedColumn>());
            
            for(TreeNode treeNode : selectedEntityColumns)
            {
                if(!treeNode.getType().equals("Data group") && !treeNode.getType().equals("Root"))
                {
                    dynamicReportCreationRequest.getSelectedColumns().add((SelectedColumn) treeNode.getData());                    
                }
            }
            
            wizardTabIndex = 3;
        } 
        catch (Exception e) 
        {
            ExceptionUtil.handleException(null, e);
            ExceptionUtil.logException(ReportGeneratorBean.class, e);
        }
    }
    
    public void completeReportCustomizationStep()
    {
        try 
        {            
            dynamicReportExportRequest = new DynamicReportExportRequest();
            dynamicReportCreationRequest.setUseFullPageWidth(true);
            dynamicReport = reportServicesLocal.createDynamicReport(sessionManagerBean.getSession(), dynamicReportCreationRequest);
            dynamicReportExportRequest.setSelectedColumns(dynamicReportCreationRequest.getSelectedColumns());
            dynamicReportExportRequest.setRowdata(reportServicesLocal.convertEntitiesToRowData(selectedData, dynamicReportCreationRequest.getSelectedColumns()));
            
            wizardTabIndex = 4;
        } 
        catch (Exception e) 
        {
            ExceptionUtil.handleException(null, e);
            ExceptionUtil.logException(ReportGeneratorBean.class, e);
        }
    }
    
    public String getHTMLReport()
    {
        try 
        {            
            return reportServicesLocal.renderReportAsHTML(sessionManagerBean.getSession(), dynamicReport, dynamicReportExportRequest);

        } 
        catch (Exception e) 
        {
            ExceptionUtil.handleException(null, e);
            ExceptionUtil.logException(ReportGeneratorBean.class, e);
            return "";
        }
        
    }
    
    public StreamedContent exportReportToPDF()
    {
        
        try 
        {            
            return new DefaultStreamedContent(new ByteArrayInputStream(reportServicesLocal.renderReportAsPDF(sessionManagerBean.getSession(), dynamicReport, dynamicReportExportRequest)),"application/pdf",dynamicReportCreationRequest.getTitle() + ".pdf");
        } 
        catch (Exception e) 
        {
            ExceptionUtil.handleException(null, e);
            ExceptionUtil.logException(ReportGeneratorBean.class, e);
            return new DefaultStreamedContent(new ByteArrayInputStream(new byte[0]));
        }
    }
    
    public StreamedContent exportReportToMSExcel()
    {
        try 
        {            
            return new DefaultStreamedContent(new ByteArrayInputStream(reportServicesLocal.renderReportAsMSEXCELSpreadsheet(sessionManagerBean.getSession(), dynamicReport, dynamicReportExportRequest)), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",dynamicReportCreationRequest.getTitle() + ".xlsx");

        } 
        catch (Exception e) 
        {
            ExceptionUtil.handleException(null, e);
            ExceptionUtil.logException(ReportGeneratorBean.class, e);
            return new DefaultStreamedContent(new ByteArrayInputStream(new byte[0]));
        }
    }
}
