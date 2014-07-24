/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.system.Session;
import javax.ejb.Local;

/**
 *
 * @author Carlo
 */
@Local
public interface ApplicationRenewalServiceLocal
{
    public List<Application> getRenewableApplicationsForFellow(Session session, Person fellow) throws AuthenticationException, Exception;
    public boolean doesApplicationHaveFinalProgressReport(Session session, Application application) throws AuthenticationException, Exception;
    public void createFinalProgressReportForApplication(Session session, Application application, String report) throws AuthenticationException, Exception;
    public void createRenewalApplication(Session session, Application oldApplication, Application application) throws AuthenticationException, Exception;
}
