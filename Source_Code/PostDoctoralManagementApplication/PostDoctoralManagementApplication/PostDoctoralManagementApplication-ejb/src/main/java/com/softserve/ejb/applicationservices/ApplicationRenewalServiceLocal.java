/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.ProgressReport;
import com.softserve.auxiliary.Exceptions.AuthenticationException;
import com.softserve.auxiliary.Exceptions.CVAlreadExistsException;
import com.softserve.auxiliary.requestresponseclasses.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlo
 */
@Local
public interface ApplicationRenewalServiceLocal
{
    public List<Application> getRenewableApplicationsForFellow(Session session, Person fellow) throws Exception;
    public boolean doesApplicationHaveFinalProgressReport(Session session, Application application) throws Exception;
    public void createFinalProgressReportForApplication(Session session, Application application, ProgressReport progressReport) throws Exception;
    public void createRenewalApplication(Session session, Application oldApplication, Application application) throws Exception;
    public void submitApplication(Session session, Application application) throws Exception;
    public void updateResearchFellowCV(Session session, Cv cv) throws Exception;
}
