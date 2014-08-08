/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.AuditLogJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.ejb.ReportServices;

/**
 *
 * @author kgothatso
 */
public class ReportServicesMockUnit extends ReportServices {
    private PersonJpaController aDAO = null;
    
    public void setADAO(PersonJpaController aDAO)
    {
        this.aDAO = aDAO;
    }
        
    @Override
    public  PersonJpaController getPersonDAO()
    {
        return aDAO;
    }
}
