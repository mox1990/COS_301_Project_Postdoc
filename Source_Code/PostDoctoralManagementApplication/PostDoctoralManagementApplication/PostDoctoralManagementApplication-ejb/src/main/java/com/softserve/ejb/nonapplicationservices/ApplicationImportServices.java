/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.nonapplicationservices;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.FundingCostJpaController;
import com.softserve.persistence.DBDAO.FundingReportJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBDAO.ResearchFellowInformationJpaController;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.FundingCost;
import com.softserve.persistence.DBEntities.FundingReport;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class ApplicationImportServices implements ApplicationImportServicesLocal {    
    @PersistenceUnit(unitName = com.softserve.auxiliary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;    
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }
    /**
     *
     * @return
     */
    protected TransactionController getTransactionController()
    {
        return new TransactionController(emf);
    }
    
    protected GregorianCalendar getGregorianCalendarUTIL()
    {
        return new GregorianCalendar();
    }
    
    @Override
    public void importApplication(Session session, Application application, FundingReport fundingReport) throws Exception 
    {
        if(application.getGrantHolder() == null || application.getFellow() == null)
        {
            throw new Exception("Application needs a grant holder and a fellow");
        }
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();
        try
        {
            PersonJpaController personJpaController = transactionController.getDAOFactoryForTransaction().createPersonDAO();
            ApplicationJpaController applicationJpaController = transactionController.getDAOFactoryForTransaction().createApplicationDAO();
            FundingReportJpaController fundingReportJpaController = transactionController.getDAOFactoryForTransaction().createFundingReportDAO();
            FundingCostJpaController fundingCostJpaController = transactionController.getDAOFactoryForTransaction().createFundingCostJpaController();
            ResearchFellowInformationJpaController researchFellowInformationJpaController = transactionController.getDAOFactoryForTransaction().createResearchFellowInformationDAO();
            
            application.setImported(Boolean.TRUE);
            application.setStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED);
            application.setTimestamp(getGregorianCalendarUTIL().getTime());
            
            Person fellow = userAccountManagementServiceLocal.getUserBySystemIDOrEmail(application.getFellow().getEmail());
            Person grantHolder = userAccountManagementServiceLocal.getUserBySystemIDOrEmail(application.getGrantHolder().getEmail());
            if(fellow == null)
            {
                application.getFellow().setSecurityRoleList(new ArrayList<SecurityRole>());
                application.getFellow().getSecurityRoleList().add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
                
                userAccountManagementServiceLocal.generateOnDemandAccount(session, "Imported your application", false, application.getFellow()); 
                
                application.setFellow(userAccountManagementServiceLocal.getUserBySystemIDOrEmail(application.getFellow().getEmail()));
                
                if(application.getFellow().getResearchFellowInformation() != null)
                { 
                    application.getFellow().getResearchFellowInformation().setSystemAssignedID(application.getFellow().getSystemID());
                    researchFellowInformationJpaController.create(application.getFellow().getResearchFellowInformation());
                }                
            }
            else
            {
                if(!fellow.getSecurityRoleList().contains(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW))
                {
                    fellow.getSecurityRoleList().add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);                    
                    fellow = personJpaController.edit(fellow);                    
                }
                
                if(application.getFellow().getResearchFellowInformation() != null)
                { 
                    application.getFellow().getResearchFellowInformation().setSystemAssignedID(application.getFellow().getSystemID());
                    if(fellow.getResearchFellowInformation() != null)
                    {                        
                        researchFellowInformationJpaController.create(application.getFellow().getResearchFellowInformation());
                        fellow.setResearchFellowInformation(application.getFellow().getResearchFellowInformation());
                    }
                    else
                    {
                        fellow.setResearchFellowInformation(researchFellowInformationJpaController.edit(application.getFellow().getResearchFellowInformation()));
                    }
                }
                
                application.setFellow(fellow);
            }

            
            if(grantHolder == null)
            {
                application.getGrantHolder().setSecurityRoleList(new ArrayList<SecurityRole>());
                application.getGrantHolder().getSecurityRoleList().add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
                userAccountManagementServiceLocal.generateOnDemandAccount(session, "Imported your application", true, application.getGrantHolder());
                
                application.setGrantHolder(userAccountManagementServiceLocal.getUserBySystemIDOrEmail(application.getGrantHolder().getEmail()));
            }
            else
            {
                if(!grantHolder.getSecurityRoleList().contains(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER))
                {
                    grantHolder.getSecurityRoleList().add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
                    grantHolder = personJpaController.edit(grantHolder);   
                    
                    application.setGrantHolder(grantHolder);
                }
            }
            
            applicationJpaController.create(application);
            
            List<FundingCost> fundingCosts = fundingReport.getFundingCostList();

            //Create funding report
            fundingReport.setApplication(application);
            fundingReport.setReportID(application.getApplicationID());
            fundingReport.setDris(session.getUser());
            fundingReport.setTimestamp(getGregorianCalendarUTIL().getTime());
            fundingReport.setFundingCostList(null);
            fundingReportJpaController.create(fundingReport);

            for(FundingCost fundingCost : fundingCosts)
            {
                fundingCost.setFundingReport(fundingReport);
                fundingCostJpaController.create(fundingCost);
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
    
    
}
