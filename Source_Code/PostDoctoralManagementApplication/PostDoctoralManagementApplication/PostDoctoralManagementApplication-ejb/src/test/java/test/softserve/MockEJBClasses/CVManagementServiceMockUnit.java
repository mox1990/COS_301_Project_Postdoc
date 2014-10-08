/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.persistence.DBDAO.AcademicQualificationJpaController;
import com.softserve.persistence.DBDAO.CvJpaController;
import com.softserve.auxillary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.ExperienceJpaController;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.applicationservices.CVManagementService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxillary.factories.DBEntitiesFactory;
import com.softserve.auxillary.requestresponseclasses.Session;
import com.softserve.auxillary.transactioncontrollers.TransactionController;
import javax.persistence.EntityManager;

/**
 *
 * @author kgothatso
 */
public class CVManagementServiceMockUnit extends CVManagementService {
    private DAOFactory dAOFactory;
    private TransactionController transactionController;

    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }
    
    public void setTransactionController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }
    
    @Override    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return dAOFactory;
    } 
    
    @Override
    protected TransactionController getTransactionController()
    {
        return transactionController;
    }
    
}
