/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softserve.ejb.nonapplicationservices;

import com.softserve.auxillary.factories.DAOFactory;
import com.softserve.auxillary.factories.DBEntitiesFactory;
import com.softserve.auxillary.transactioncontrollers.TransactionController;
import com.softserve.persistence.DBEntities.ResourceEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author kgothatso
 */
@TransactionManagement(TransactionManagementType.BEAN)
@Stateless
public class ResourceBundleService implements ResourceBundleServiceLocal {
    @PersistenceUnit(unitName = com.softserve.auxillary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected TransactionController getTransactionController()
    {
        return new TransactionController(emf);
    }
    
    protected EntityManager createEntityManager()
    {
        return emf.createEntityManager();
    }
    
    @Override
    public List<ResourceEntity> loadAllResourceEntities() throws Exception 
    {
        EntityManager em = createEntityManager();
        try
        {
            return getDAOFactory(em).createResourceEntityDAO().findResourceEntityEntities();
        }
        finally
        {
            em.close();
        }
    }
    
    @Override
    public List<ResourceEntity> loadResourceEntitiesForLocale(String locale) throws Exception 
    {
        System.out.println("Let's go...");
        EntityManager em = createEntityManager();
        try
        {
            System.out.println("We are moving...");
            return getDAOFactory(em).createResourceEntityDAO().findForLocale(locale);
        }
        finally
        {
            em.close();
        }
    }
}

    
