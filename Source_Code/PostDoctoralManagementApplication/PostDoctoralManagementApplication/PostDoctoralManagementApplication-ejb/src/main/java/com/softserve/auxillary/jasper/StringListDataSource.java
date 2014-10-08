/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxillary.jasper;

import java.util.Iterator;
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
    private Iterator<List<String>> iterator;
    private List<String> fieldNames;
    private List<String> currentRow;

    public StringListDataSource(List<List<String>> data, List<String> fieldNames) {
        this.data = data;
        this.fieldNames = fieldNames;
        iterator = data.iterator();
    }
    
    @Override
    public boolean next() throws JRException {
        boolean hasNext = false;

        if (iterator != null)
        {
            hasNext = iterator.hasNext();

            if (hasNext)
            {
                this.currentRow = iterator.next();
            }
        }

        return hasNext;
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        int i = fieldNames.indexOf(jrf.getName());
        System.out.println("=========DATA SOURCE : " + fieldNames.indexOf(jrf.getName()) + " " + jrf.getName() + " " + fieldNames.toString());
        if(i > -1)
        {
            System.out.println("=========DATA SOURCE : " + currentRow.get(i).toString() + " " + i + " " + currentRow.toString());
            return currentRow.get(i).toString();
        }
        else
        {
            throw new JRException("Cannot find field " + jrf.getName() + " in " + fieldNames.toString());
        }        
        
    }
    
}
