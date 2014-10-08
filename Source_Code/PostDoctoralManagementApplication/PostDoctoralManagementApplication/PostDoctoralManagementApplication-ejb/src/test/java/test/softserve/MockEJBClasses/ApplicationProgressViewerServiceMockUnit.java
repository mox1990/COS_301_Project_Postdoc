/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.auxillary.factories.DAOFactory;
import com.softserve.ejb.applicationservices.ApplicationProgressViewerService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxillary.util.ApplicationServicesUtil;
import com.softserve.auxillary.factories.DBEntitiesFactory;
import com.softserve.auxillary.transactioncontrollers.TransactionController;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author kgothatso
 */
public class ApplicationProgressViewerServiceMockUnit extends ApplicationProgressViewerService {
    private ApplicationServicesUtil aEJB;
    private UserGateway uEJB;
    private DAOFactory dAOFactory;
    private EntityManager em;
    
    public void setaEJB(ApplicationServicesUtil aEJB) {
        this.aEJB = aEJB;
    }

    public void setuEJB(UserGateway uEJB) {
        this.uEJB = uEJB;
    }
    
    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }
    
    @Override    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return dAOFactory;
    }    
    
    @Override
    protected ApplicationServicesUtil getApplicationServicesUTIL(EntityManager em)
    {
        return aEJB;
    }
    
    @Override
    protected UserGateway getUserGatewayServiceEJB()
    {
        return uEJB;
    }
    
    @Override
    protected EntityManager createEntityManager()
    {
        return em;
    }
}
