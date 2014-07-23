/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.Session;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author K
 */
@Stateless
public class ApplicationRenewalService implements ApplicationRenewalServiceLocal { // TODO: Finalize the local or remote spec
    
    
    @Override
    public List<Application> getRenewableApplicationsFor(Session session, Person fellow) throws AuthenticationException, Exception
    {
        return null;
    }
    
    
    @Override
    public void doesApplicationHaveFinalProgressReport(Session session, Application application) throws AuthenticationException, Exception
    {
        
    }
        
    @Override
    public void createFinalProgressReportFor(Session session, Application application, String report) throws AuthenticationException, Exception
    {
        
    }
    
    @Override
    public void createRenewalApplication(Session session, Application application) throws AuthenticationException, Exception
    {
        
    }
}
