/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Endorsement;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;

package com.softserve.ejb;

/**
 *
 * @author Carlo
 */
public interface RefereeReportLocal 
{
    public List<Application> loadPendingReports(Session session);
    public void createReport(Session session, Application application, String report) throws NonexistentEntityException, RollbackFailureException, Exception;
    public void submitReport(Session session, Application application, String report) throws NonexistentEntityException, RollbackFailureException, Exception; 
}
