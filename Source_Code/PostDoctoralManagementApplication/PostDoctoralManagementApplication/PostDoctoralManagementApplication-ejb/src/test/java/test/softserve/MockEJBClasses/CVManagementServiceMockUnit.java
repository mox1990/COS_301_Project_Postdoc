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
import javax.persistence.EntityManager;

/**
 *
 * @author kgothatso
 */
public class CVManagementServiceMockUnit extends CVManagementService {
    private CvJpaController cVDAO;
    private UserGateway uEJB;
    private AuditTrailService aTEJB;
    private DBEntitiesFactory dBEntities;
    private AcademicQualificationJpaController aQDAO;
    private ExperienceJpaController eDAO;

    public void seteDAO(ExperienceJpaController eDAO) {
        this.eDAO = eDAO;
    }
    
    public void setaQDAO(AcademicQualificationJpaController aQDAO) {
        this.aQDAO = aQDAO;
    }
    
    public void setcVDAO(CvJpaController cVDAO) {
        this.cVDAO = cVDAO;
    }

    public void setuEJB(UserGateway uEJB) {
        this.uEJB = uEJB;
    }

    public void setaTEJB(AuditTrailService aTEJB) {
        this.aTEJB = aTEJB;
    }

    public void setdBEntities(DBEntitiesFactory dBEntities) {
        this.dBEntities = dBEntities;
    }
    

    
    @Override    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    } 
    
}
