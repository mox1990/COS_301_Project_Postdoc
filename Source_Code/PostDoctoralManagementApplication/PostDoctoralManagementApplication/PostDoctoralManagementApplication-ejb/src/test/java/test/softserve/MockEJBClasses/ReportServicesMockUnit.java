/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.auxiliary.converters.EntityToListConverter;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.AuditLogJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.ejb.nonapplicationservices.ReportServices;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.ejb.nonapplicationservices.UserGatewayLocal;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ReportServicesMockUnit extends ReportServices {
    private DAOFactory dAOFactory;
    private EntityToListConverter entityToListConverter;
    private EntityManager entityManager;

    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }

    public void setEntityToListConverter(EntityToListConverter entityToListConverter) {
        this.entityToListConverter = entityToListConverter;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return dAOFactory;
    }
    
    @Override
    protected EntityToListConverter getEntityToListConverterUTIL()
    {
        return entityToListConverter;
    }
    
    @Override
    protected EntityManager createEntityManager()
    {
        return entityManager;
    }
}
