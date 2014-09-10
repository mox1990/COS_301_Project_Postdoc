/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.AcademicQualificationJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBDAO.ExperienceJpaController;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Experience;
import com.softserve.Exceptions.*;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import com.softserve.transactioncontrollers.TransactionController;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CVManagementService implements CVManagementServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    public CVManagementService() {
    }
    
    public CVManagementService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
	return new DAOFactory(em);
    }

    protected TransactionController getTransactionController()
    {
        return new TransactionController(emf);
    }
    
    @Override
    public boolean hasCV(Session session)
    {
        return (session.getUser().getCv() != null);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod(message = "Created CV")
    @Override
    public void createCV(Session session, Cv cv) throws AuthenticationException, Exception
    {        
        if(hasCV(session))
        {
            throw new CVAlreadExistsException("The user already has a cv");
        }
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            AcademicQualificationJpaController academicQualificationJpaController = dAOFactory.createAcademicQualificationDAO();
            ExperienceJpaController experienceJpaController = dAOFactory.createExperienceDAO();
            CvJpaController cvJpaController = dAOFactory.createCvDAO();

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
            
            transactionController.CommitTransaction();
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        }
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod(message = "Updated CV")
    @Override
    public void updateCV(Session session, Cv cv) throws AuthenticationException, Exception
    {
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            AcademicQualificationJpaController academicQualificationJpaController = dAOFactory.createAcademicQualificationDAO();
            ExperienceJpaController experienceJpaController = dAOFactory.createExperienceDAO();
            CvJpaController cvJpaController = dAOFactory.createCvDAO();   
            
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

            transactionController.CommitTransaction();
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        }
        
    }
}
