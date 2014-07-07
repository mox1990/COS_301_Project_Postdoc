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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlo
 */

@Local
public interface NewApplicationLocal
{
    public Application createNewApplication(Session session);
    public void generateOnDemandUser();
    public void specifyReferee(String name, String email);
    public void specifyGrantHolder(String name, String email);
    public void createCV();
    public void submitReport(Session session, Application application, String report) throws NonexistentEntityException, RollbackFailureException, Exception;
    
   
}
