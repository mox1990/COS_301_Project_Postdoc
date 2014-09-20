/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.jasper;

import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class StringListDataSource implements JRDataSource {

    private List<List<String>> data;
    private List<String> currentRow;
    private List<String> fieldNames;
    private int currentRowIndex = 0;
    

    @Override
    public boolean next() throws JRException {
        if(data.size() < currentRowIndex)
        {
            currentRow = data.get(currentRowIndex);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        int i = fieldNames.indexOf(jrf.getName());
        
        if(i > -1)
        {
            return currentRow.get(i);
        }
        else
        {
            throw new JRException("Cannot find field " + jrf.getName() + " in " + fieldNames.toString());
        }        
        
    }
    
}
