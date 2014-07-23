/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.ejb.ApplicationProgressViewerService;
import com.softserve.ejb.UserGateway;

/**
 *
 * @author kgothatso
 */
public class ApplicationProgressViewerServiceMockUnit extends ApplicationProgressViewerService {
    private ApplicationJpaController aDAO;
    private UserGateway uEJB;

    public void setaDAO(ApplicationJpaController aDAO) {
        this.aDAO = aDAO;
    }

    public void setuEJB(UserGateway uEJB) {
        this.uEJB = uEJB;
    }
    
    
    @Override
    protected ApplicationJpaController getApplicationDAO()
    {
        return aDAO;
    }
    
    @Override
    protected UserGateway getUserGatewayServiceEJB()
    {
        return uEJB;
    }
    
}
