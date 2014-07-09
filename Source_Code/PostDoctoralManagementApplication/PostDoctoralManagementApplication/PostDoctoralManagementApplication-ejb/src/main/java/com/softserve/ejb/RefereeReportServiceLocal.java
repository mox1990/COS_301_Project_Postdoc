/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softserve.ejb;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Endorsement;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;



/**
 *
 * @author Carlo
 */
public interface RefereeReportServiceLocal 
{
    public List<Application> loadPendingReports(Session session);
    public void createReport(Session session, Application application, String report) throws NonexistentEntityException, RollbackFailureException, Exception;
    public void submitReport(Session session, Application application, String report) throws NonexistentEntityException, RollbackFailureException, Exception; 
}
