/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import auto.softserve.XMLEntities.CV.Item;
import auto.softserve.XMLEntities.CV.Reference;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.NeuralNetworkCreationRequest;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.ejb.nonapplicationservices.NeuralNetworkManagementServicesLocal;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBEntities.AcademicQualification;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.NeuralNetwork;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
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
public class ApplicationSuccessEvaluationServices implements ApplicationSuccessEvaluationServicesLocal {
    
    @PersistenceUnit(unitName = com.softserve.auxiliary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NeuralNetworkManagementServicesLocal neuralNetworkManagementServicesLocal;
    
    @EJB
    private DRISApprovalServiceLocal dRISApprovalServiceLocal;

    public ApplicationSuccessEvaluationServices() {
    }
    
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
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    protected EntityManager createEntityManager()
    {
        return emf.createEntityManager();
    }
    
        
    @Override
    public Double getApplicationSuccessRating(Session session, Application application) throws Exception 
    {
        //Inputs (7 * 2) + 1 + 1 + 1 = 17 + 1 for bias No of work eperience, No of qualifications, No of relevent qualifications, No of References, No of relevent referencesm, No of other contributions, No of relevent other contributions, No of team members, Age, Fellow eligiblity
        NeuralNetwork neuralNetwork = neuralNetworkManagementServicesLocal.getDefaultNeuralNetwork(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE));
        neuralNetworkManagementServicesLocal.runNeuralNetwork(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE), neuralNetwork, extractInputDataFromApplication(application));
        return neuralNetwork.getAllOutputNeurons().get(0).getValue();
    }

    @Override
    public void createApplicationSucessNeuralNetwork(Session session, NeuralNetworkCreationRequest neuralNetworkCreationRequest) throws Exception 
    {
        neuralNetworkCreationRequest.setBiasValue(-1.0);
        neuralNetworkCreationRequest.setNumberOfInputNeurons(17);
        neuralNetworkCreationRequest.setNumberOfOutputNeurons(1);
        neuralNetworkManagementServicesLocal.createNeuralNetwork(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE), neuralNetworkCreationRequest);
    }
    
    @Schedule(second = "*/30", minute = "*", hour = "*", dayOfWeek = "*")
    @Override
    public void trainApplicationSucessNeuralNetworkWithApplicationData() throws Exception 
    {
        EntityManager em = createEntityManager();
        
        try
        {        
            ApplicationJpaController applicationJpaController = getDAOFactory(em).createApplicationDAO();
            
            List<Application> declinedApplications = applicationJpaController.findAllNonImportedApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED, 0, Integer.MAX_VALUE);
            List<Application> fundedApplications = applicationJpaController.findAllNonImportedApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED, 0, Integer.MAX_VALUE);
            fundedApplications.addAll(applicationJpaController.findAllNonImportedApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED, 0, Integer.MAX_VALUE));
            fundedApplications.addAll(applicationJpaController.findAllNonImportedApplicationsWithStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED, 0, Integer.MAX_VALUE));
            System.out.println("Declined applications: " + declinedApplications.toString());
            System.out.println("Funded applications: " + fundedApplications.toString());
            
            List<List<Double>> inputVectorSet = new ArrayList<List<Double>>();
            List<List<Double>> targetVectorSet = new ArrayList<List<Double>>();
            
            for(Application application : declinedApplications)
            {
                inputVectorSet.add(extractInputDataFromApplication(application));
                List<Double> tVec = new ArrayList<Double>();
                tVec.add(0.0);
                targetVectorSet.add(tVec);
            }
            
            for(Application application : fundedApplications)
            {
                inputVectorSet.add(extractInputDataFromApplication(application));
                List<Double> tVec = new ArrayList<Double>();
                tVec.add(1.0);
                targetVectorSet.add(tVec);
            }
            System.out.println("Sizes: " + targetVectorSet.size() + " "+  inputVectorSet.size());
            NeuralNetwork defaultNN = neuralNetworkManagementServicesLocal.getDefaultNeuralNetwork(new Session(null,null, Boolean.TRUE));
            if(defaultNN != null)
            {
                List<Double> trainNeuralNetwork = neuralNetworkManagementServicesLocal.trainNeuralNetwork(new Session(null,null, Boolean.TRUE), defaultNN, inputVectorSet, targetVectorSet, 100);
            }
            
        }
        finally
        {
            em.close();
        }
    }
    
    private List<Double> extractInputDataFromApplication(Application application) throws Exception
    {
        List<Double> inputs = new ArrayList<Double>();
        
        inputs.addAll(extractInputFromCV(application.getFellow().getCv(), application));
        inputs.addAll(extractInputFromCV(application.getGrantHolder().getCv(),application));
        
        //Extact eligiblity of application
        if(dRISApprovalServiceLocal.checkApplicationForEligiblity(new Session(null, null, Boolean.TRUE), application))
        {
            inputs.add(Math.sqrt(3.0));
        }
        else
        {
            inputs.add(-1 * Math.sqrt(3.0));
        }
        
        
        Date dobDate = application.getFellow().getCv().getDateOfBirth();
        GregorianCalendar curCal = getGregorianCalendar();
        GregorianCalendar dobCal = getGregorianCalendar();
        dobCal.setTime(dobDate);
        dobCal.add(GregorianCalendar.MONTH, 1);       
        
        
        //Extract age max is 70 years old
        inputs.add(scaleValue(curCal.get(GregorianCalendar.YEAR) - dobCal.get(GregorianCalendar.YEAR), 70, -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
        
        //Extract team members max 10 members
        if(application.getInformationXMLEntity() == null)
        {
            inputs.add(scaleValue(application.getInformationXMLEntity().getTeamMembers().getMember().size(), 10, -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
        }
        else
        {
            inputs.add(-1 * Math.sqrt(3.0));
        }
        return inputs;
    }
    
    private List<Double> extractInputFromCV(Cv cv, Application application)
    {
        List<Double> inputs = new ArrayList<Double>();
        
        //Extract work experience max 100
        inputs.add(scaleValue(cv.getExperienceList().size(), 100, -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
        
        //Extract Academic qualifications max 20
        inputs.add(scaleValue(cv.getAcademicQualificationList().size(), 20, -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
        
        //Extract references max 50
        inputs.add(scaleValue(cv.getResearchOutputXMLEntity().getReferences().size(), 50, -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
        
        //Extract other contributions max 50 
        inputs.add(scaleValue(cv.getOtherContributionsXMLEntity().getItems().size(), 50, -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
        
        
        //Extract relevent Academic qualifications max 20
        inputs.add(scaleValue(numberOfReleventQualifications(cv,application.getProjectTitle()), cv.getAcademicQualificationList().size(), -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
        
        //Extract relevent references max 50
        inputs.add(scaleValue(numberOfReleventReferences(cv,application.getProjectTitle()), cv.getResearchOutputXMLEntity().getReferences().size(), -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
        
        //Extract relevent other contributions max 50 
        inputs.add(scaleValue(numberOfReleventOtherContributions(cv,application.getProjectTitle()), cv.getOtherContributionsXMLEntity().getItems().size(), -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
        return inputs;
    }
    
    private int numberOfReleventQualifications(Cv cv, String title)
    {
        int count = 0;
        
        for(AcademicQualification academicQualification : cv.getAcademicQualificationList())
        {
            if(isSentenceReleventToString(title, academicQualification.getName()))
            {
                count++;
            }
        }
        
        return count;
    }
    
    private int numberOfReleventReferences(Cv cv, String title)
    {
        int count = 0;
        
        for(Reference reference : cv.getResearchOutputXMLEntity().getReferences())
        {
            if(isSentenceReleventToString(title, reference.getPublicationName()))
            {
                count++;
            }
        }
        
        return count;
    }
    
    private int numberOfReleventOtherContributions(Cv cv, String title)
    {
        int count = 0;
        
        for(Item item : cv.getOtherContributionsXMLEntity().getItems())
        {
            if(isSentenceReleventToString(title, item.getDesciption()))
            {
                count++;
            }
        }
        
        return count;
    }
    
    private boolean isSentenceReleventToString(String sentence, String searchSpace)
    {
        if(getSentenceRelevenceInString(sentence, searchSpace) > 0.7)
        {
            return true;
        }
        return false;
    }
    
    private Double getSentenceRelevenceInString(String sentence, String searchSpace)
    {
        List<String> words = extractWordsFromSenetence(sentence);
        
        List<Integer> noOccurances = new ArrayList<Integer>();
        
        int max = 0;
        for(String word : words)
        {
            noOccurances.add(countWordOccurance(word, searchSpace));
            if(noOccurances.get(noOccurances.size() - 1) > max)
            {
                max = noOccurances.get(noOccurances.size() - 1);
            }
        }
        
        Double maxValue = (double) max * words.size();
        Double total = 0.0;
        
        for(Integer val : noOccurances)
        {
            total += val; 
        }
        
        return total / maxValue;      
    }
    
    private List<String> extractWordsFromSenetence(String sentence)
    {
        List<String> words = new ArrayList<String>();
        int pos = sentence.indexOf(" ");
        
        while(pos > -1 && pos < sentence.length())
        {
            int newpos = sentence.indexOf(" ", pos + 1);
            if(newpos > -1 )
            {
                words.add(sentence.substring(pos, newpos));
            }
            else
            {
                words.add(sentence.substring(pos));
            }
            
            
            pos = newpos;           
        }
        
        return words;
    }
    
   
    
    private int countWordOccurance(String word, String searchSpace)
    {
        int i = 0;
        int pos = searchSpace.indexOf(word);
        
        while(pos > -1)
        {
            i++;            
            pos = searchSpace.indexOf(word, pos + word.length());
        }
        
        return i;
    }
    //Active domain for sigmoid [-sqrt(3); sqrt(3)]
    public Double scaleValue(double val, double maxVal, double min, double max)
    {
        return (((val / (maxVal)) * (max + Math.abs(min))) - ((max + Math.abs(min)) / 2));
    }
    
}
