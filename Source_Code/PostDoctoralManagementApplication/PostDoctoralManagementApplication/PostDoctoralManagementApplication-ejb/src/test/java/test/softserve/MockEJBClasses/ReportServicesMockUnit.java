/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.AuditLogJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.ejb.nonapplicationservices.ReportServices;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.ejb.nonapplicationservices.UserGatewayLocal;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author kgothatso
 */
public class ReportServicesMockUnit extends ReportServices {
    UserGateway uEJB;
    
    public ReportServicesMockUnit() throws Exception
    {
        
    }
    
    public UserGateway getuEJB() {
        return uEJB;
    }

    public void setuEJB(UserGateway uEJB) {
        this.uEJB = uEJB;
    }
}
