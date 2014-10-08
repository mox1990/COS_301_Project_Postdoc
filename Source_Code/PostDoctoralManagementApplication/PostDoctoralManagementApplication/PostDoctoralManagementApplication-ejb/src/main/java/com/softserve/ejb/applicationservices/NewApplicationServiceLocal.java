/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.auxillary.Exceptions.AuthenticationException;
import com.softserve.auxillary.Exceptions.CVAlreadExistsException;
import com.softserve.auxillary.Exceptions.UserAlreadyExistsException;
import com.softserve.auxillary.requestresponseclasses.Session;
import java.util.List;
import javax.ejb.Local;

/**
 * 
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */

@Local
public interface NewApplicationServiceLocal
{    
    public void createProspectiveFellowCV(Session session, Cv cv) throws AuthenticationException, CVAlreadExistsException, Exception;
    public void createNewApplication(Session session, Application application) throws AuthenticationException, Exception;
    public void linkGrantHolderToApplication(Session session, Application application, Person grantHolder) throws AuthenticationException, UserAlreadyExistsException, Exception;
    public void linkRefereesToApplication(Session session, Application application, List<Person> referees) throws Exception;
    public void linkRefereeToApplication(Session session, Application application, Person referee) throws AuthenticationException, UserAlreadyExistsException, Exception;
    public void submitApplication(Session session, Application application) throws Exception;
    public boolean canFellowOpenANewApplication(Person fellow);
    public Application getOpenApplication(Session session) throws AuthenticationException, Exception;
}
