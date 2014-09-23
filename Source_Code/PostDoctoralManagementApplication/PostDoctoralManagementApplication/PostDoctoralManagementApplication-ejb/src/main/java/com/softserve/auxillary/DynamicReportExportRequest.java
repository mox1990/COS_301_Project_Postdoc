/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxillary;

import com.softserve.jasper.StringListDataSource;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class DynamicReportExportRequest {
    
    private List<List<String>> rowdata;
    private List<SelectedColumn> selectedColumns;

    public DynamicReportExportRequest() {
    }

    public List<SelectedColumn> getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(List<SelectedColumn> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    

    public List<List<String>> getRowdata() {
        return rowdata;
    }

    public void setRowdata(List<List<String>> rowdata) {
        this.rowdata = rowdata;
    }
                
    public JRDataSource getDataSourceForRowData()
    {
        List<String> columnPropertyName = new ArrayList<String>();
        
        for(SelectedColumn selectedColumn : selectedColumns)
        {
            columnPropertyName.add(selectedColumn.getActualname());
        }
        
        return new StringListDataSource(rowdata, columnPropertyName);
    }
}
