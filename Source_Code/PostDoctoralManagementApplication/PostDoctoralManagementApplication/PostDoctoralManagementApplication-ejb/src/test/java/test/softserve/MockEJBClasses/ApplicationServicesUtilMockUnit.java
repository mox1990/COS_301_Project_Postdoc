/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.DeclineReportJpaController;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ApplicationServicesUtilMockUnit extends ApplicationServicesUtil {
    private DAOFactory dAOFactory;
    private DBEntitiesFactory dBEntities;
    private GregorianCalendar gCal;

    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }
    

    public ApplicationServicesUtilMockUnit() {
        super(null);
    }

    public ApplicationServicesUtilMockUnit(EntityManager em) {
        super(em);
    }

    public void setdBEntities(DBEntitiesFactory dBEntities) {
        this.dBEntities = dBEntities;
    }

    public void setgCal(GregorianCalendar gCal) {
        this.gCal = gCal;
    }
    
    @Override    
    protected DAOFactory getDAOFactory()
    {
        return dAOFactory;
    }    
    
    /**
     *
     * @return
     */
    @Override
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return dBEntities;
    }

    
    @Override
    protected GregorianCalendar getGregorianCalendar()
    {
        return gCal;
    }
}
