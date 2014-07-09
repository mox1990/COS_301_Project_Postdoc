/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CVManagementService implements CVManagementServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    public CVManagementService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    protected CvJpaController getCVDAO()
    {
        return new CvJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected UserGateway getUserGatewayServiceEJB()
    {
        return new UserGateway(emf);
    }
    
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return new AuditTrailService(emf);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    public void createCV(Session session, Cv cv) throws AuthenticationException, Exception
    {
        getUserGatewayServiceEJB().authenticateUserAsOwner(session, cv.getOwnerID());
        
        CvJpaController cvJpaController = getCVDAO();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        cvJpaController.create(cv);
        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Created user cv", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    public void updateCV(Session session, Cv cv) throws AuthenticationException, Exception
    {
        getUserGatewayServiceEJB().authenticateUserAsOwner(session, cv.getOwnerID());
        
        CvJpaController cvJpaController = getCVDAO();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        cvJpaController.edit(cv);
        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Updated user cv", session.getUser());
        auditTrailService.logAction(auditLog);
    }
}
