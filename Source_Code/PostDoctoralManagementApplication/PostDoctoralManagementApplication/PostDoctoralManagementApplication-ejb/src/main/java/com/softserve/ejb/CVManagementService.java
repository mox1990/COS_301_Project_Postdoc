/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.AcademicQualificationJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBDAO.ExperienceJpaController;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Experience;
import com.softserve.Exceptions.*;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.List;
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

    public CVManagementService() {
    }
    
    public CVManagementService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    protected CvJpaController getCVDAO()
    {
        return new CvJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    } 
    
    protected AcademicQualificationJpaController getAcademicQualificationDAO()
    {
        return new AcademicQualificationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ExperienceJpaController getExperienceDAO()
    {
        return new ExperienceJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
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
    
    protected boolean hasCV(Session session)
    {
        return (session.getUser().getCv() != null);
    }
    
    @Override
    public void createCV(Session session, Cv cv) throws AuthenticationException, Exception
    {
        getUserGatewayServiceEJB().authenticateUserAsOwner(session, cv.getPerson());
        
        if(hasCV(session))
        {
            throw new CVAlreadExistsException("The user already has a cv");
        }
        
        AcademicQualificationJpaController academicQualificationJpaController = getAcademicQualificationDAO();
        ExperienceJpaController experienceJpaController = getExperienceDAO();
        CvJpaController cvJpaController = getCVDAO();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        List<Experience> experienceList = cv.getExperienceList();
        List<AcademicQualification> academicQualificationsList = cv.getAcademicQualificationList();
        
        cv.setAcademicQualificationList(null);
        cv.setExperienceList(null);
        
        cv.setCvID(session.getUser().getSystemID());
        cv.setPerson(session.getUser());
        cvJpaController.create(cv);
        
        for(Experience experience : experienceList)
        {
            experience.setCv(cv);
            experienceJpaController.create(experience);
        }
        
        for(AcademicQualification academicQualification : academicQualificationsList)
        {
            academicQualification.setCv(cv);
            academicQualificationJpaController.create(academicQualification);
        }
        
        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Created user cv", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    @Override
    public void updateCV(Session session, Cv cv) throws AuthenticationException, Exception
    {
        getUserGatewayServiceEJB().authenticateUserAsOwner(session, cv.getPerson());
        
        AcademicQualificationJpaController academicQualificationJpaController = getAcademicQualificationDAO();
        ExperienceJpaController experienceJpaController = getExperienceDAO();
        
        CvJpaController cvJpaController = getCVDAO();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        List<Experience> experienceList = cv.getExperienceList();
        List<AcademicQualification> academicQualificationsList = cv.getAcademicQualificationList();

        for(Experience experience : cv.getExperienceList())
        {
            try
            {
                experienceJpaController.findExperience(experience.getExperienceID());
            }
            catch(Exception ex)
            {
                experience.setCv(cv);
                experienceJpaController.create(experience);
            }
        }
        
        for(AcademicQualification academicQualification : cv.getAcademicQualificationList())
        {
            try
            {
                academicQualificationJpaController.findAcademicQualification(academicQualification.getQualificationID());
            }
            catch(Exception ex)
            {
                academicQualification.setCv(cv);
                academicQualificationJpaController.create(academicQualification);
            }
        }
        
        cv.setCvID(session.getUser().getSystemID());
        cvJpaController.edit(cv);
        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Updated user cv", session.getUser());
        auditTrailService.logAction(auditLog);
    }
}
