/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.requestresponseclasses;

import com.softserve.auxiliary.jasper.StringListDataSource;
import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class DynamicReportCreationRequest  implements Serializable{
    private String title;
    private String subtitle;
    private boolean useFullPageWidth;

    private List<SelectedColumn> selectedColumns;

    
    public DynamicReportCreationRequest() {
    }

    public DynamicReportCreationRequest(String title, String subtitle, boolean useFullPageWidth) {
        this.title = title;
        this.subtitle = subtitle;
        this.useFullPageWidth = useFullPageWidth;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUseFullPageWidth(boolean useFullPageWidth) {
        this.useFullPageWidth = useFullPageWidth;
    }

    public boolean isUseFullPageWidth() {
        return useFullPageWidth;
    }
        
    public String getSubtitle() {
        return subtitle;
    }

    public String getTitle() {
        return title;
    }

    public List<SelectedColumn> getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(List<SelectedColumn> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    
    
}
