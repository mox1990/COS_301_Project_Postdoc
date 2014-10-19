/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.webservices.services;

import com.softserve.auxiliary.requestresponseclasses.*;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.util.HashUtil;
import com.softserve.ejb.applicationservices.*;
import com.softserve.ejb.nonapplicationservices.UserGatewayLocal;
import com.softserve.persistence.DBEntities.*;
import com.softserve.webservices.util.JSONConverterUtil;
import com.softserve.webservices.util.PayloadUtil;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Path("/ApplicationServices")
public class ApplicationServices {
    
    @Context
    private HttpServletRequest httpServletRequest;
    
    @EJB
    private ApplicationProgressViewerServiceLocal applicationProgressViewerServiceLocal;    
    @EJB
    private ApplicationRenewalServiceLocal applicationRenewalServiceLocal;
    @EJB
    private ApplicationSuccessEvaluationServicesLocal applicationSuccessEvaluationServicesLocal;
    @EJB
    private CVManagementServiceLocal cVManagementServiceLocal;
    @EJB
    private DRISApprovalServiceLocal dRISApprovalServiceLocal;
    @EJB
    private DeansEndorsementServiceLocal deansEndorsementServiceLocal;
    @EJB
    private ForwardAndRewindServicesLocal forwardAndRewindServicesLocal;
    @EJB
    private GrantHolderFinalisationServiceLocal grantHolderFinalisationServiceLocal;    
    @EJB
    private HODRecommendationServicesLocal hODRecommendationServicesLocal;
    @EJB
    private MeetingManagementServiceLocal meetingManagementServiceLocal;
    @EJB
    private NewApplicationServiceLocal newApplicationServiceLocal;    
    @EJB
    private ProgressReportManagementServiceLocal progressReportManagementServiceLocal;
    @EJB
    private RefereeReportServiceLocal refereeReportServiceLocal;    
    
    @EJB
    private UserGatewayLocal userGatewayLocal; 
    
    
    @Path("/applicationProgressViewerService/getAllApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String applicationProgressViewerService_getAllApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = applicationProgressViewerServiceLocal.getAllApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/applicationProgressViewerService/getApplicationProgress")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String applicationProgressViewerService_getApplicationProgress(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = applicationProgressViewerServiceLocal.getApplicationProgress(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class),JSONConverterUtil.JSONToObject_JACKSON(application, Application.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    //Application renewal services
    
    @Path("/applicationRenewalService/getRenewableApplicationsForFellow")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String applicationRenewalService_getRenewableApplicationsForFellow(@HeaderParam("session") String Session, @HeaderParam("fellow") String fellow)
    {        
        try
        {
            Object[] payLoad = applicationRenewalServiceLocal.getRenewableApplicationsForFellow(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class),JSONConverterUtil.JSONToObject_JACKSON(fellow, Person.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/applicationRenewalService/doesApplicationHaveFinalProgressReport")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String applicationRenewalService_doesApplicationHaveFinalProgressReport(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = applicationRenewalServiceLocal.doesApplicationHaveFinalProgressReport(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class),JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    
    @Path("/applicationRenewalService/createFinalProgressReportForApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String applicationRenewalService_createFinalProgressReportForApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("progressreport") String progressreport)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            applicationRenewalServiceLocal.createFinalProgressReportForApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(progressreport, ProgressReport.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/applicationRenewalService/createRenewalApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String applicationRenewalService_createRenewalApplication(@HeaderParam("session") String Session, @HeaderParam("oldApplication") String oldapplication, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            applicationRenewalServiceLocal.createRenewalApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(oldapplication, Application.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/applicationRenewalService/submitApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String applicationRenewalService_submitApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            applicationRenewalServiceLocal.submitApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/applicationRenewalService/updateResearchFellowCV")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String applicationRenewalService_updateResearchFellowCV(@HeaderParam("session") String Session, @HeaderParam("application") String cv)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            applicationRenewalServiceLocal.updateResearchFellowCV(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(cv, Cv.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    //ApplicationSuccessEvaluationServicesLocal
    @Path("/ApplicationSuccessEvaluationServicesLocal/getApplicationSuccessRating")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String applicationSuccessEvaluationServicesLocal_getApplicationSuccessRating(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = applicationSuccessEvaluationServicesLocal.getApplicationSuccessRating(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/ApplicationSuccessEvaluationServicesLocal/createApplicationSucessNeuralNetwork")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String applicationSuccessEvaluationServicesLocal_createApplicationSucessNeuralNetwork(@HeaderParam("session") String Session, @HeaderParam("neuralNetworkCreationRequest") String neuralNetworkCreationRequest)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            applicationSuccessEvaluationServicesLocal.createApplicationSucessNeuralNetwork(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(neuralNetworkCreationRequest, NeuralNetworkCreationRequest.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    ///DeansEndorsementServiceLocal
    @Path("/DeansEndorsementServiceLocal/loadPendingApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String deansEndorsementServiceLocal_loadPendingApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = deansEndorsementServiceLocal.loadPendingApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), 0, Integer.MAX_VALUE).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DeansEndorsementServiceLocal/countTotalPendingApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String deansEndorsementServiceLocal_countTotalPendingApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = deansEndorsementServiceLocal.countTotalPendingApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DeansEndorsementServiceLocal/declineApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String deansEndorsementServiceLocal_declineApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("reason") String reason)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            deansEndorsementServiceLocal.declineApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(reason, String.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DeansEndorsementServiceLocal/endorseApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String deansEndorsementServiceLocal_endorseApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("endorsementReport") String endorsementReport)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            deansEndorsementServiceLocal.endorseApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(endorsementReport, Endorsement.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    //DRISApprovalServiceLocal
    
    @Path("/DRISApprovalServiceLocal/loadPendingEndorsedApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String dRISApprovalServiceLocal_loadPendingEndorsedApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = dRISApprovalServiceLocal.loadPendingEndorsedApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), 0, Integer.MAX_VALUE).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DRISApprovalServiceLocal/countTotalPendingApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String dRISApprovalServiceLocal_countTotalPendingEndorsedApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = dRISApprovalServiceLocal.countTotalPendingEndorsedApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DRISApprovalServiceLocal/loadPendingEligibleApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String dRISApprovalServiceLocal_loadPendingEligibleApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = dRISApprovalServiceLocal.loadPendingEligibleApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), 0, Integer.MAX_VALUE).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DRISApprovalServiceLocal/countTotalPendingEligibleApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String dRISApprovalServiceLocal_countTotalPendingEligibleApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = dRISApprovalServiceLocal.countTotalPendingEligibleApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DRISApprovalServiceLocal/loadFundedApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String dRISApprovalServiceLocal_loadFundedApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = dRISApprovalServiceLocal.loadFundedApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), 0, Integer.MAX_VALUE).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DRISApprovalServiceLocal/checkApplicationForEligiblity")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String dRISApprovalServiceLocal_checkApplicationForEligiblity(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = dRISApprovalServiceLocal.checkApplicationForEligiblity(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DRISApprovalServiceLocal/denyFunding")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String dRISApprovalServiceLocal_denyFunding(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("reason") String reason)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            dRISApprovalServiceLocal.denyFunding(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(reason, String.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DRISApprovalServiceLocal/approveFunding")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String dRISApprovalServiceLocal_approveFunding(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("researchFellowInformation") String researchFellowInformation, @HeaderParam("fundingReport") String fundingReport, @HeaderParam("applicantMessage") String applicantMessage, @HeaderParam("cscMesssage") String cscMesssage, @HeaderParam("finaceMessage") String finaceMessage)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            dRISApprovalServiceLocal.approveFunding(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(researchFellowInformation, ResearchFellowInformation.class), JSONConverterUtil.JSONToObject_JACKSON(fundingReport, FundingReport.class), JSONConverterUtil.JSONToObject_JACKSON(applicantMessage, String.class), JSONConverterUtil.JSONToObject_JACKSON(cscMesssage, Notification.class), JSONConverterUtil.JSONToObject_JACKSON(finaceMessage, Notification.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DRISApprovalServiceLocal/setApplicationEligibleStatus")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String dRISApprovalServiceLocal_setApplicationEligibleStatus(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("isElgible") String isElgible)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            dRISApprovalServiceLocal.setApplicationEligibleStatus(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(isElgible, Boolean.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DRISApprovalServiceLocal/updateFundingInformation")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String dRISApprovalServiceLocal_updateFundingInformation(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            dRISApprovalServiceLocal.updateFundingInformation(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DRISApprovalServiceLocal/terminateApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String dRISApprovalServiceLocal_terminateApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            dRISApprovalServiceLocal.terminateApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/DRISApprovalServiceLocal/abandonApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String dRISApprovalServiceLocal_abandonApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            dRISApprovalServiceLocal.abandonApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    //GrantHolderFinalisationServiceLocal
    
    @Path("/GrantHolderFinalisationServiceLocal/createGrantHolderCV")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String grantHolderFinalisationServiceLocal_createGrantHolderCV(@HeaderParam("session") String Session, @HeaderParam("cv") String cv)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            grantHolderFinalisationServiceLocal.createGrantHolderCV(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(cv, Cv.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    
    @Path("/GrantHolderFinalisationServiceLocal/loadPendingEndorsedApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String grantHolderFinalisationServiceLocal_loadPendingApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = grantHolderFinalisationServiceLocal.loadPendingApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), 0, Integer.MAX_VALUE).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/GrantHolderFinalisationServiceLocal/countTotalPendingApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String grantHolderFinalisationServiceLocal_countTotalPendingEndorsedApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = grantHolderFinalisationServiceLocal.countTotalPendingApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/GrantHolderFinalisationServiceLocal/saveChangesToApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String grantHolderFinalisationServiceLocal_saveChangesToApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            grantHolderFinalisationServiceLocal.saveChangesToApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/GrantHolderFinalisationServiceLocal/getHODsOfApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String grantHolderFinalisationServiceLocal_getHODsOfApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = grantHolderFinalisationServiceLocal.getHODsOfApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/GrantHolderFinalisationServiceLocal/requestSpecificHODtoReview")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String grantHolderFinalisationServiceLocal_requestSpecificHODtoReview(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("hod") String hod)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            grantHolderFinalisationServiceLocal.requestSpecificHODtoReview(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(hod, Person.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/GrantHolderFinalisationServiceLocal/declineAppliction")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String grantHolderFinalisationServiceLocal_declineAppliction(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("reason") String reason)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            grantHolderFinalisationServiceLocal.declineAppliction(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(reason, String.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/GrantHolderFinalisationServiceLocal/ammendAppliction")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String grantHolderFinalisationServiceLocal_ammendAppliction(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("reason") String reason)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            grantHolderFinalisationServiceLocal.ammendAppliction(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(reason, String.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/GrantHolderFinalisationServiceLocal/finaliseApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String grantHolderFinalisationServiceLocal_finaliseApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            grantHolderFinalisationServiceLocal.finaliseApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    //ForwardAndRewindServicesLocal
    
    
    @Path("/ForwardAndRewindServicesLocal/loadMovableApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String forwardAndRewindServicesLocal_loadMovableApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = forwardAndRewindServicesLocal.loadMovableApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/ForwardAndRewindServicesLocal/forwardApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String forwardAndRewindServicesLocal_forwardApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("toStatus") String toStatus, @HeaderParam("reason") String reason)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            forwardAndRewindServicesLocal.forwardApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(toStatus, String.class), JSONConverterUtil.JSONToObject_JACKSON(reason, String.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/ForwardAndRewindServicesLocal/rewindApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String forwardAndRewindServicesLocal_rewindApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("toStatus") String toStatus, @HeaderParam("reason") String reason)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            forwardAndRewindServicesLocal.rewindApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(toStatus, String.class), JSONConverterUtil.JSONToObject_JACKSON(reason, String.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    //HODRecommendationServicesLocal
    
    @Path("/HODRecommendationServicesLocal/loadPendingEndorsedApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String hODRecommendationServicesLocal_loadPendingApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = hODRecommendationServicesLocal.loadPendingApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), 0, Integer.MAX_VALUE).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/HODRecommendationServicesLocal/countTotalPendingApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String hODRecommendationServicesLocal_countTotalPendingEndorsedApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = hODRecommendationServicesLocal.countTotalPendingApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }

    
    @Path("/HODRecommendationServicesLocal/getDeansOfApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String hODRecommendationServicesLocal_getDeansOfApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = hODRecommendationServicesLocal.getDeansOfApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/HODRecommendationServicesLocal/requestSpecificDeanToReview")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String hODRecommendationServicesLocal_requestSpecificDeanToReview(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("dean") String dean)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            hODRecommendationServicesLocal.requestSpecificDeanToReview(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(dean, Person.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/HODRecommendationServicesLocal/declineAppliction")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String hODRecommendationServicesLocal_declineAppliction(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("reason") String reason)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            hODRecommendationServicesLocal.declineAppliction(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(reason, String.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/HODRecommendationServicesLocal/ammendAppliction")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String hODRecommendationServicesLocal_ammendAppliction(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("reason") String reason)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            hODRecommendationServicesLocal.ammendAppliction(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(reason, String.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/HODRecommendationServicesLocal/recommendApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String hODRecommendationServicesLocal_recommendApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("recommendationReport") String recommendationReport)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            hODRecommendationServicesLocal.recommendApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(recommendationReport, RecommendationReport.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    //MeetingManagementServiceLocal
    
    @Path("/MeetingManagementServiceLocal/createMeeting")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String meetingManagementServiceLocal_createMeeting(@HeaderParam("session") String Session, @HeaderParam("committeeMeeting") String committeeMeeting)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            meetingManagementServiceLocal.createMeeting(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(committeeMeeting, CommitteeMeeting.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/MeetingManagementServiceLocal/updateMeeting")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String meetingManagementServiceLocal_updateMeeting(@HeaderParam("session") String Session, @HeaderParam("committeeMeeting") String committeeMeeting)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            meetingManagementServiceLocal.updateMeeting(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(committeeMeeting, CommitteeMeeting.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/MeetingManagementServiceLocal/cancelMeeting")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String meetingManagementServiceLocal_cancelMeeting(@HeaderParam("session") String Session, @HeaderParam("committeeMeeting") String committeeMeeting)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            meetingManagementServiceLocal.cancelMeeting(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(committeeMeeting, CommitteeMeeting.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/MeetingManagementServiceLocal/startMeeting")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String meetingManagementServiceLocal_startMeeting(@HeaderParam("session") String Session, @HeaderParam("committeeMeeting") String committeeMeeting)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            meetingManagementServiceLocal.startMeeting(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(committeeMeeting, CommitteeMeeting.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/MeetingManagementServiceLocal/endMeeting")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String meetingManagementServiceLocal_endMeeting(@HeaderParam("session") String Session, @HeaderParam("committeeMeeting") String committeeMeeting)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            meetingManagementServiceLocal.endMeeting(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(committeeMeeting, CommitteeMeeting.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/MeetingManagementServiceLocal/addMinuteComment")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String meetingManagementServiceLocal_addMinuteComment(@HeaderParam("session") String Session, @HeaderParam("minuteComment") String minuteComment)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            meetingManagementServiceLocal.addMinuteComment(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(minuteComment, MinuteComment.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    
    @Path("/MeetingManagementServiceLocal/getAllMeetings")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String meetingManagementServiceLocal_getAllMeetings(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = meetingManagementServiceLocal.getAllMeetings(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/MeetingManagementServiceLocal/getAllActiveMeetings")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String meetingManagementServiceLocal_getAllActiveMeetings(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = meetingManagementServiceLocal.getAllActiveMeetings(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/MeetingManagementServiceLocal/getAllStillToBeHeldMeetings")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String meetingManagementServiceLocal_getAllStillToBeHeldMeetings(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = meetingManagementServiceLocal.getAllStillToBeHeldMeetings(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/MeetingManagementServiceLocal/getAllConcludedMeetings")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String meetingManagementServiceLocal_getAllConcludedMeetings(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = meetingManagementServiceLocal.getAllConcludedMeetings(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/MeetingManagementServiceLocal/getAllPostDocCommitteeMembers")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String meetingManagementServiceLocal_getAllPostDocCommitteeMembers(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = meetingManagementServiceLocal.getAllPostDocCommitteeMembers(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/MeetingManagementServiceLocal/getAllActiveMeetingsForWhichUserIsToAttend")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String meetingManagementServiceLocal_getAllActiveMeetingsForWhichUserIsToAttend(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = meetingManagementServiceLocal.getAllActiveMeetingsForWhichUserIsToAttend(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    //NewApplicationServiceLocal
    
    @Path("/NewApplicationServiceLocal/createProspectiveFellowCV")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String newApplicationServiceLocal_cancelMeeting(@HeaderParam("session") String Session, @HeaderParam("cv") String cv)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            newApplicationServiceLocal.createProspectiveFellowCV(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(cv, Cv.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/NewApplicationServiceLocal/createNewApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String newApplicationServiceLocal_createNewApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            newApplicationServiceLocal.createNewApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/NewApplicationServiceLocal/linkGrantHolderToApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String newApplicationServiceLocal_linkGrantHolderToApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("grantHolder") String grantHolder)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            newApplicationServiceLocal.linkGrantHolderToApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(grantHolder, Person.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/NewApplicationServiceLocal/linkRefereesToApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String newApplicationServiceLocal_linkRefereesToApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("referees") String referees)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            newApplicationServiceLocal.linkRefereesToApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), (List<Person>) JSONConverterUtil.JSONToObject_JACKSON(referees, List.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/NewApplicationServiceLocal/linkRefereeToApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String newApplicationServiceLocal_linkRefereeToApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("referee") String referee)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            newApplicationServiceLocal.linkRefereeToApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(referee, Person.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/NewApplicationServiceLocal/submitApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String newApplicationServiceLocal_submitApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            newApplicationServiceLocal.submitApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/NewApplicationServiceLocal/canFellowOpenANewApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String newApplicationServiceLocal_canFellowOpenANewApplication(@HeaderParam("fellow") String fellow)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = newApplicationServiceLocal.canFellowOpenANewApplication(JSONConverterUtil.JSONToObject_JACKSON(fellow, Person.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/NewApplicationServiceLocal/getOpenApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String newApplicationServiceLocal_getOpenApplication(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = newApplicationServiceLocal.getOpenApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    //ProgressReportManagementServiceLocal
    
    @Path("/ProgressReportManagementServiceLocal/createProgressReport")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String progressReportManagementServiceLocal_createProgressReport(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("progressReport") String progressReport)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            progressReportManagementServiceLocal.createProgressReport(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(progressReport, ProgressReport.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/ProgressReportManagementServiceLocal/updateProgressReport")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String progressReportManagementServiceLocal_updateProgressReport(@HeaderParam("session") String Session, @HeaderParam("progressReport") String progressReport)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            progressReportManagementServiceLocal.updateProgressReport(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(progressReport, ProgressReport.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/ProgressReportManagementServiceLocal/allApplicationsWithPendingReportsForUser")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String progressReportManagementServiceLocal_allApplicationsWithPendingReportsForUser(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = progressReportManagementServiceLocal.allApplicationsWithPendingReportsForUser(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    
    @Path("/ProgressReportManagementServiceLocal/doesApplicationHaveFinalProgressReport")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String progressReportManagementServiceLocal_doesApplicationHaveFinalProgressReport(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = progressReportManagementServiceLocal.doesApplicationHaveFinalProgressReport(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/ProgressReportManagementServiceLocal/getNumberOfProgressReportsRequiredByApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String progressReportManagementServiceLocal_getNumberOfProgressReportsRequiredByApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = progressReportManagementServiceLocal.getNumberOfProgressReportsRequiredByApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    //RefereeReportServiceLocal
    
    @Path("/RefereeReportServiceLocal/loadPendingApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String refereeReportServiceLocal_loadPendingApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad = refereeReportServiceLocal.loadPendingApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class),0,Integer.MAX_VALUE).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/RefereeReportServiceLocal/countTotalPendingApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String refereeReportServiceLocal_countTotalPendingApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = refereeReportServiceLocal.countTotalPendingApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/RefereeReportServiceLocal/submitReferralReport")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String refereeReportServiceLocal_submitReferralReport(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("refereeReport") String refereeReport)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            refereeReportServiceLocal.submitReferralReport(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(refereeReport, RefereeReport.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    
    
    
    
    
    
    
    
    
}
