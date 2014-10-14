/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.startupshutdownclasses;

import auto.softserve.XMLEntities.PrePostConditional.Methodinfo;
import auto.softserve.XMLEntities.PrePostConditional.Prepostconditionalmethods;
import com.softserve.auxiliary.XMLUtils.XMLMarshaller;
import com.softserve.auxiliary.XMLUtils.XMLUnmarshaller;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.auxiliary.util.ClassMethodVerificationUtil;
import com.softserve.persistence.DBDAO.PrePostConditionMethodJpaController;
import com.softserve.persistence.DBEntities.PrePostConditionMethod;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class PerPostConditionalSync {
    @PersistenceUnit(unitName = com.softserve.auxiliary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
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
    
    protected XMLUnmarshaller getXMLUnmarshaller()
    {
        return new XMLUnmarshaller();
    }
    
    protected ClassMethodVerificationUtil getClassMethodVerificationUtil()
    {
        return new ClassMethodVerificationUtil();
    }
    
    @PostConstruct
    private void syncPerPostConditionsWithDB()
    {
        try
        {
            System.out.println("======== syncPerPostConditionsWithDB: Starting pre post condition synchronisation..." );
            File configFile = null;
            try
            {
                System.out.println("======== syncPerPostConditionsWithDB: working directory = " + System.getProperty("user.dir"));
                File file = new File("..");
                System.out.println("======== syncPerPostConditionsWithDB: working directory = " + System.getProperty("user.dir"));
                configFile = searchForFileInDirectoryRecursively(file, com.softserve.auxiliary.constants.SystemConstants.PREPOSTCONDITIONALCONFIG_FILE_NAME);
            }
            catch(Exception ex)
            {
                System.out.println("======== syncPerPostConditionsWithDB: exception occured during file search continuing " + ex.getMessage());
            }

            if(configFile != null)
            {
                System.out.println("======== syncPerPostConditionsWithDB: Config file found " + configFile.toString());

                XMLUnmarshaller xMLUnmarshaller = getXMLUnmarshaller();

                byte[] configFileContents = Files.readAllBytes(configFile.toPath());

                String contents = new String(configFileContents, Charset.forName("UTF-8"));

                Prepostconditionalmethods prepostconditionalmethods = xMLUnmarshaller.unmarshalPrepostconditionalmethodsString(contents);

                System.out.println("======== syncPerPostConditionsWithDB: Config file unmarshalled");

                TransactionController transactionController = getTransactionController();
                transactionController.StartTransaction();

                try
                {               

                    PrePostConditionMethodJpaController prePostConditionMethodJpaController = transactionController.getDAOFactoryForTransaction().createPrePostConditionMethodDAO();
                    System.out.println("======== syncPerPostConditionsWithDB: Starting to synchronise file entries with database ");

                    List<PrePostConditionMethod> dbPrePostConditionMethods = prePostConditionMethodJpaController.findPrePostConditionMethodEntities();

                    for(Methodinfo methodinfo : prepostconditionalmethods.getMethod())
                    {
                        boolean skip = false;
                        for(PrePostConditionMethod prePostConditionMethod : dbPrePostConditionMethods)
                        {
                            if(prePostConditionMethod.getMethodClassName().equals(methodinfo.getClazz()) && prePostConditionMethod.getMethodName().equals(methodinfo.getName()))
                            {
                                skip = true;
                                break;
                            }
                        }

                        if(!skip)
                        {
                            try
                            {
                                PrePostConditionMethod p = getDBEntitiesFactory().createPrePostConditionMethod(methodinfo.getClazz(), methodinfo.getName(), methodinfo.getParameters(), com.softserve.auxiliary.constants.SystemConstants.SCRIPT_ENGINE_NAME_JAVASCRIPT, null, null);

                                if(!getClassMethodVerificationUtil().doesMethodExist(p.getMethodClassName(), p.getMethodName(), p.getMethodParametersDecode()))
                                {
                                    throw new Exception("Method does not exist.");
                                }

                                prePostConditionMethodJpaController.create(p);
                            }
                            catch(Exception ex)
                            {
                                System.out.println("======== syncPerPostConditionsWithDB: Excpetion occured while pre post condition synchronisation for '" + methodinfo.getClazz() + "." + methodinfo.getName() + "' it has not been added. Message = " + ex.toString() );
                                System.out.println("======== syncPerPostConditionsWithDB: Continuing with rest of methods...");
                            }

                        }
                    }

                    transactionController.CommitTransaction();
                }
                catch(Exception ex)
                {
                    System.out.println("syncPerPostConditionsWithDB: Excpetion occured during pre post condition synchronisation. Message = " + ex.toString() );
                    transactionController.RollbackTransaction();
                    throw ex;
                }
                finally
                {
                    transactionController.CloseEntityManagerForTransaction();
                    System.out.println("syncPerPostConditionsWithDB: Stoping pre post condition synchronisation..." );
                }
            }
            else
            {
                System.out.println("syncPerPostConditionsWithDB: No config file called " + com.softserve.auxiliary.constants.SystemConstants.PREPOSTCONDITIONALCONFIG_FILE_NAME + " found !");
                System.out.println("syncPerPostConditionsWithDB: Stoping pre post condition synchronisation..." );
            }
        }
        catch(Exception ex)
        {
            
        }
        
    }
    
    private File searchForFileInDirectoryRecursively(File path, String name)
    {        
        System.out.println("Searching [ " + path.listFiles().length +" files ] in " + path + " for file " + name +  " ...");
        for(int i = 0; i < path.listFiles().length; i++)
        {
            File file = path.listFiles()[i];
            System.out.println("Inspecting file - " + file.getName());
            if(file.getName().equals(name))
            {
                return file;
            }
            
            if(file.isDirectory() && !file.isFile() && !file.equals(path) && !file.getName().equals("."))
            {
                System.out.println("Preparing to scan sub dir - " + file.getName());
                File result = searchForFileInDirectoryRecursively(file, name);
                
                if(result != null)
                {
                    return result;
                }
                System.out.println("Completed scaning sub dir - " + file.getName());
            }
        }                 
        
        return null;
    }
    
    
}
