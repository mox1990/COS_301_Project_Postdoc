/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import auto.softserve.XMLEntities.CV.Item;
import auto.softserve.XMLEntities.CV.Reference;
import com.softserve.auxiliary.requestresponseclasses.NeuralNetworkCreationRequest;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.ejb.nonapplicationservices.NeuralNetworkManagementServicesLocal;
import com.softserve.persistence.DBEntities.AcademicQualification;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.NeuralNetwork;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class ApplicationSuccessEvaluationServices implements ApplicationSuccessEvaluationServicesLocal {
    
    @EJB
    private NeuralNetworkManagementServicesLocal neuralNetworkManagementServicesLocal;
    
    @EJB
    private DRISApprovalServiceLocal dRISApprovalServiceLocal;

    public ApplicationSuccessEvaluationServices() {
    }
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
        
    @Override
    public Double getApplicationSuccessRating(Session session, Application application) throws Exception 
    {
        //Inputs (8 * 2) + 1 + 1 No of work eperience, No of qualifications, No of relevent qualifications, No of References, No of relevent referencesm, No of other contributions, No of relevent other contributions, No of team members, Age, Fellow eligiblity
        NeuralNetwork neuralNetwork = neuralNetworkManagementServicesLocal.getDefaultNeuralNetwork(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE));
        neuralNetworkManagementServicesLocal.runNeuralNetwork(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE), neuralNetwork, extractInputDataFromApplication(application));
        return neuralNetwork.getAllOutputNeurons().get(0).getActualOutputValue();
    }

    @Override
    public void createApplicationSucessNeuralNetwork(Session session, NeuralNetworkCreationRequest neuralNetworkCreationRequest) throws Exception 
    {
        neuralNetworkCreationRequest.setBiasValue(-1.0);
        neuralNetworkCreationRequest.setNumberOfInputNeurons(18);
        neuralNetworkCreationRequest.setNumberOfOutputNeurons(1);
        neuralNetworkManagementServicesLocal.createNeuralNetwork(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE), neuralNetworkCreationRequest);
    }

    @Override
    public void trainApplicationSucessNeuralNetworkWithApplicationData() throws Exception 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private List<Double> extractInputDataFromApplication(Application application) throws Exception
    {
        List<Double> inputs = new ArrayList<Double>();
        
        inputs.addAll(extractInputFromCV(application.getFellow().getCv()));
        inputs.addAll(extractInputFromCV(application.getGrantHolder().getCv()));
        
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
        inputs.add(scaleValue(application.getInformationXMLEntity().getTeamMembers().getMember().size(), 10, -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
            
        return inputs;
    }
    
    private List<Double> extractInputFromCV(Cv cv)
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
        inputs.add(scaleValue(numberOfReleventQualifications(cv), cv.getAcademicQualificationList().size(), -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
        
        //Extract relevent references max 50
        inputs.add(scaleValue(numberOfReleventReferences(cv), cv.getResearchOutputXMLEntity().getReferences().size(), -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
        
        //Extract relevent other contributions max 50 
        inputs.add(scaleValue(numberOfReleventOtherContributions(cv), cv.getOtherContributionsXMLEntity().getItems().size(), -1 * Math.sqrt(3.0), Math.sqrt(3.0)));
        return inputs;
    }
    
    private int numberOfReleventQualifications(Cv cv)
    {
        int count = 0;
        
        for(AcademicQualification academicQualification : cv.getAcademicQualificationList())
        {
            
        }
        
        return count;
    }
    
    private int numberOfReleventReferences(Cv cv)
    {
        int count = 0;
        
        for(Reference reference : cv.getResearchOutputXMLEntity().getReferences())
        {
            
        }
        
        return count;
    }
    
    private int numberOfReleventOtherContributions(Cv cv)
    {
        int count = 0;
        
        for(Item item : cv.getOtherContributionsXMLEntity().getItems())
        {
            
        }
        
        return count;
    }
    
    private int countWordOccurance(String word)
    {
        return 0;
    }
    //Active domain for sigmoid [-sqrt(3); sqrt(3)]
    public Double scaleValue(double val, double maxVal, double min, double max)
    {
        return (((val / (maxVal)) * (max + Math.abs(min))) - ((max + Math.abs(min)) / 2));
    }
    
}
