/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.webservices.services;

import ar.com.fdvs.dj.domain.DynamicReport;
import com.softserve.auxiliary.HTTPScrapers.GoogleScholarQuery;
import com.softserve.auxiliary.requestresponseclasses.DynamicReportCreationRequest;
import com.softserve.auxiliary.requestresponseclasses.DynamicReportExportRequest;
import com.softserve.auxiliary.requestresponseclasses.NeuralNetworkCreationRequest;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.util.HashUtil;
import com.softserve.ejb.nonapplicationservices.AnnouncementManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.ApplicationImportServicesLocal;
import com.softserve.ejb.nonapplicationservices.AuditTrailServiceLocal;
import com.softserve.ejb.nonapplicationservices.GoogleScholarServicesLocal;
import com.softserve.ejb.nonapplicationservices.LocationManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NeuralNetworkManagementServicesLocal;
import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotifierServicesLocal;
import com.softserve.ejb.nonapplicationservices.PrePostConditionalManagementServicesLocal;
import com.softserve.ejb.nonapplicationservices.ReportServicesLocal;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.UserGatewayLocal;
import com.softserve.persistence.DBEntities.Announcement;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Department;
import com.softserve.persistence.DBEntities.Faculty;
import com.softserve.persistence.DBEntities.FundingReport;
import com.softserve.persistence.DBEntities.Institution;
import com.softserve.persistence.DBEntities.NeuralNetwork;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.PrePostConditionMethod;
import com.softserve.persistence.DBEntities.ProgressReport;
import com.softserve.webservices.util.JSONConverterUtil;
import com.softserve.webservices.util.PayloadUtil;
import java.util.List;
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
@Path("/NonApplicationServices")
public class NonApplicationServices {
    @Context
    private HttpServletRequest httpServletRequest;
    
    @EJB 
    private AnnouncementManagementServiceLocal announcementManagementServiceLocal;
    
    @EJB
    private ApplicationImportServicesLocal applicationImportServicesLocal;
    
    @EJB
    private AuditTrailServiceLocal auditTrailServiceLocal;
    
    @EJB 
    private GoogleScholarServicesLocal googleScholarServicesLocal;
    
    @EJB
    private LocationManagementServiceLocal locationManagementServiceLocal;
    
    @EJB 
    private NeuralNetworkManagementServicesLocal neuralNetworkManagementServicesLocal;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    
    @EJB
    private NotifierServicesLocal notifierServicesLocal;
    
    @EJB 
    private PrePostConditionalManagementServicesLocal prePostConditionalManagementServicesLocal;
    
    @EJB
    private ReportServicesLocal reportServicesLocal;
    
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    
    @Path("/AnnouncementManagementService/loadAllActiveAnnouncements")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String announcementManagementService_loadAllActiveAnnouncements()
    {        
        try
        {
            Object[] payLoad = announcementManagementServiceLocal.loadAllActiveAnnouncements().toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/AnnouncementManagementService/loadAllPendingAnnouncements")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String announcementManagementService_loadAllPendingAnnouncements()
    {        
        try
        {
            Object[] payLoad = announcementManagementServiceLocal.loadAllPendingAnnouncements().toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/AnnouncementManagementService/createAnnouncement")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String announcementManagementService_createAnnouncement(@HeaderParam("session") String Session, @HeaderParam("announcement") String announcement)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            announcementManagementServiceLocal.createAnnouncement(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(announcement, Announcement.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/AnnouncementManagementService/updateAnnouncement")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String announcementManagementService_updateAnnouncement(@HeaderParam("session") String Session, @HeaderParam("announcement") String announcement)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            announcementManagementServiceLocal.updateAnnouncement(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(announcement, Announcement.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/AnnouncementManagementService/removeAnnouncement")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String announcementManagementService_removeAnnouncement(@HeaderParam("session") String Session, @HeaderParam("announcement") String announcement)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            announcementManagementServiceLocal.removeAnnouncement(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(announcement, Announcement.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/ApplicationImportServicesLocal/importApplication")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String applicationImportServicesLocal_importApplication(@HeaderParam("session") String Session, @HeaderParam("application") String application, @HeaderParam("fundingReport") String fundingReport)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            applicationImportServicesLocal.importApplication(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(application, Application.class), JSONConverterUtil.JSONToObject_JACKSON(fundingReport, FundingReport.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/AuditTrailService/logAction")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String auditTrailService_logAction(@HeaderParam("session") String Session, @HeaderParam("auditLog") String auditLog)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            auditTrailServiceLocal.logAction(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(auditLog, AuditLog.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/AuditTrailService/loadAllAuditLogEntries")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String auditTrailService_loadAllAuditLogEntries(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = auditTrailServiceLocal.loadAllAuditLogEntries(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/GoogleScholarServices/searchGoogleScholarUsing")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String googleScholarServices_searchGoogleScholarUsing(@HeaderParam("session") String session, @HeaderParam("googleScholarQuery") String googleScholarQuery)
    {        
        try
        {
            Object[] payLoad = googleScholarServicesLocal.searchGoogleScholarUsing(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(googleScholarQuery, GoogleScholarQuery.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/LocationManagementService/createInstitution")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String locationManagementService_createInstitution(@HeaderParam("session") String session, @HeaderParam("institution") String institution)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            locationManagementServiceLocal.createInstitution(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(institution, Institution.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/LocationManagementService/createFaculty")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String locationManagementService_createFaculty(@HeaderParam("session") String session, @HeaderParam("faculty") String faculty)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            locationManagementServiceLocal.createFaculty(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(faculty, Faculty.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/LocationManagementService/createDepartment")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String locationManagementService_createDepartment(@HeaderParam("session") String session, @HeaderParam("department") String department)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            locationManagementServiceLocal.createDepartment(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(department, Department.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/LocationManagementService/updateInstitution")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String locationManagementService_updateInstitution(@HeaderParam("session") String session, @HeaderParam("institution") String institution)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            locationManagementServiceLocal.updateInstitution(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(institution, Institution.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/LocationManagementService/updateFaculty")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String locationManagementService_updateFaculty(@HeaderParam("session") String session, @HeaderParam("faculty") String faculty)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            locationManagementServiceLocal.updateFaculty(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(faculty, Faculty.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/LocationManagementService/updateDepartment")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String locationManagementService_updateDepartment(@HeaderParam("session") String session, @HeaderParam("department") String department)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            locationManagementServiceLocal.updateDepartment(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(department, Department.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/LocationManagementService/getAllInstitutions")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String locationManagementService_getAllInstitutions()
    {        
        try
        {
            Object[] payLoad = locationManagementServiceLocal.getAllInstitutions().toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/LocationManagementService/getAllFacultiesInInstitution")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String locationManagementService_getAllFacultiesInInstitution(@HeaderParam("institution") String institution)
    {        
        try
        {
            Object[] payLoad = locationManagementServiceLocal.getAllFacultiesInInstitution(JSONConverterUtil.JSONToObject_JACKSON(institution, Institution.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/LocationManagementService/getAllDepartmentForFaculty")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String locationManagementService_getAllDepartmentForFaculty(@HeaderParam("faculty") String faculty)
    {        
        try
        {
            Object[] payLoad = locationManagementServiceLocal.getAllDepartmentForFaculty(JSONConverterUtil.JSONToObject_JACKSON(faculty, Faculty.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/LocationManagementService/getInstitution")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String locationManagementService_getInstitution(@HeaderParam("institution") String institution)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = locationManagementServiceLocal.getInstitution(JSONConverterUtil.JSONToObject_JACKSON(institution, Long.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/LocationManagementService/getFaculty")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String locationManagementService_getFaculty(@HeaderParam("institution") String institution)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = locationManagementServiceLocal.getFaculty(JSONConverterUtil.JSONToObject_JACKSON(institution, Long.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/LocationManagementService/getDepartment")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String locationManagementService_getDepartment(@HeaderParam("department") String department)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = locationManagementServiceLocal.getDepartment(JSONConverterUtil.JSONToObject_JACKSON(department, Long.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NeuralNetworkManagementServices/loadAllNeuralNetworks")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String neuralNetworkManagementServices_loadAllNeuralNetworks(@HeaderParam("session") String session)
    {        
        try
        {
            Object[] payLoad = neuralNetworkManagementServicesLocal.loadAllNeuralNetworks(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NeuralNetworkManagementServices/getDefaultNeuralNetwork")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String neuralNetworkManagementServices_getDefaultNeuralNetwork(@HeaderParam("session") String session)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = neuralNetworkManagementServicesLocal.getDefaultNeuralNetwork(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NeuralNetworkManagementServices/createNeuralNetwork")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String neuralNetworkManagementServices_createNeuralNetwork(@HeaderParam("session") String session, @HeaderParam("neuralNetworkCreationRequest") String neuralNetworkCreationRequest)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            neuralNetworkManagementServicesLocal.createNeuralNetwork(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(neuralNetworkCreationRequest, NeuralNetworkCreationRequest.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NeuralNetworkManagementServices/makeNeuralNetworkDefaultNetwork")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String neuralNetworkManagementServices_makeNeuralNetworkDefaultNetwork(@HeaderParam("session") String session, @HeaderParam("neuralnetwork") String neuralnetwork)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            neuralNetworkManagementServicesLocal.makeNeuralNetworkDefaultNetwork(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(neuralnetwork, NeuralNetwork.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NeuralNetworkManagementServices/updateNeuralNetwork")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String neuralNetworkManagementServices_updateNeuralNetwork(@HeaderParam("session") String session, @HeaderParam("neuralnetwork") String neuralnetwork)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            neuralNetworkManagementServicesLocal.updateNeuralNetwork(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(neuralnetwork, NeuralNetwork.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NeuralNetworkManagementServices/updateNeuralNetworkSynapses")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String neuralNetworkManagementServices_updateNeuralNetworkSynapses(@HeaderParam("session") String session, @HeaderParam("neuralnetwork") String neuralnetwork)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            neuralNetworkManagementServicesLocal.updateNeuralNetworkSynapses(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(neuralnetwork, NeuralNetwork.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NeuralNetworkManagementServices/removeNeuralNetwork")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String neuralNetworkManagementServices_removeNeuralNetwork(@HeaderParam("session") String session, @HeaderParam("neuralnetwork") String neuralnetwork)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            neuralNetworkManagementServicesLocal.removeNeuralNetwork(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(neuralnetwork, NeuralNetwork.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NeuralNetworkManagementServices/runNeuralNetwork")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String neuralNetworkManagementServices_runNeuralNetwork(@HeaderParam("session") String session, @HeaderParam("neuralnetwork") String neuralnetwork, @HeaderParam("inputvector") String inputVector)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            neuralNetworkManagementServicesLocal.runNeuralNetwork(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(neuralnetwork, NeuralNetwork.class), (List<Double>) JSONConverterUtil.JSONToObject_JACKSON(inputVector, List.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NeuralNetworkManagementServices/correctNeuralNetwork")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String neuralNetworkManagementServices_correctNeuralNetwork(@HeaderParam("session") String session, @HeaderParam("neuralnetwork") String neuralnetwork, @HeaderParam("targetvector") String targetVector)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            neuralNetworkManagementServicesLocal.correctNeuralNetwork(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(neuralnetwork, NeuralNetwork.class), (List<Double>) JSONConverterUtil.JSONToObject_JACKSON(targetVector, List.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NeuralNetworkManagementServices/trainNeuralNetwork")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String neuralNetworkManagementServices_trainNeuralNetwork(@HeaderParam("session") String session, @HeaderParam("neuralnetwork") String neuralnetwork, @HeaderParam("inputVectorSet") String inputVectorSet, @HeaderParam("targetVectorSet") String targetVectorSet, @HeaderParam("noOfEpochs") String noOfEpochs)
    {        
        try
        {
            Object[] payLoad = neuralNetworkManagementServicesLocal.trainNeuralNetwork(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(neuralnetwork, NeuralNetwork.class), JSONConverterUtil.JSONToObject_JACKSON(inputVectorSet, List.class), JSONConverterUtil.JSONToObject_JACKSON(targetVectorSet, List.class), JSONConverterUtil.JSONToObject_JACKSON(noOfEpochs, int.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NotificationService/sendBatchNotifications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String notificationService_sendBatchNotifications(@HeaderParam("session") String session, @HeaderParam("notifications") String notifications, @HeaderParam("sendEmail") String sendEmail)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            notificationServiceLocal.sendBatchNotifications(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(notifications, List.class), JSONConverterUtil.JSONToObject_JACKSON(sendEmail, boolean.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NotificationService/sendNotification")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String notificationService_sendNotification(@HeaderParam("session") String session, @HeaderParam("notification") String notification, @HeaderParam("sendEmail") String sendEmail)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            notificationServiceLocal.sendNotification(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(notification, Notification.class), JSONConverterUtil.JSONToObject_JACKSON(sendEmail, boolean.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NotificationService/sendOnlyEmail")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String notificationService_sendOnlyEmail(@HeaderParam("session") String session, @HeaderParam("notification") String notification)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            notificationServiceLocal.sendOnlyEmail(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(notification, Notification.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NotificationService/getAllNotificationsForPerson")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String notificationService_getAllNotificationsForPerson(@HeaderParam("session") String session, @HeaderParam("person") String person)
    {        
        try
        {
            Object[] payLoad = notificationServiceLocal.getAllNotificationsForPerson(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(person, Person.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NotificationService/getAllNotificationsFromPerson")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String notificationService_getAllNotificationsFromPerson(@HeaderParam("session") String session, @HeaderParam("person") String person)
    {        
        try
        {
            Object[] payLoad = notificationServiceLocal.getAllNotificationsFromPerson(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(person, Person.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NotifierServices/loadAllPendingIssuesForSession")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String notifierServices_loadAllPendingIssuesForSession(@HeaderParam("session") String session)
    {        
        try
        {
            Object[] payLoad = notifierServicesLocal.loadAllPendingIssuesForSession(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/NotifierServices/loadAllPendingIssuesForUser")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String notifierServices_loadAllPendingIssuesForSession(@HeaderParam("session") String session, @HeaderParam("person") String person)
    {        
        try
        {
            Object[] payLoad = notifierServicesLocal.loadAllPendingIssuesForUser(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(person, Person.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/PrePostConditionalManagementServices/loadPrePostConditionalMethods")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String prePostConditionalManagementServices_loadPrePostConditionalMethods(@HeaderParam("session") String session)
    {        
        try
        {
            Object[] payLoad = prePostConditionalManagementServicesLocal.loadPrePostConditionalMethods(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/PrePostConditionalManagementServices/updatePrePostConditionalMethod")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String prePostConditionalManagementServices_updatePrePostConditionalMethod(@HeaderParam("session") String session, @HeaderParam("prePostConditionMethod") String prePostConditionMethod)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            prePostConditionalManagementServicesLocal.updatePrePostConditionalMethod(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(prePostConditionMethod, PrePostConditionMethod.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/PrePostConditionalManagementServices/findPrePostConditionMethodByClassAndName")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String prePostConditionalManagementServices_findPrePostConditionMethodByClassAndName(@HeaderParam("session") String session, @HeaderParam("className") String className, @HeaderParam("methodName") String methodName, @HeaderParam("parameters") String parameters)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = prePostConditionalManagementServicesLocal.findPrePostConditionMethodByClassAndName(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), className, methodName, JSONConverterUtil.JSONToObject_JACKSON(parameters, List.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/PrePostConditionalManagementServices/evaluatePreCondition")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String prePostConditionalManagementServices_evaluatePreCondition(@HeaderParam("session") String session, @HeaderParam("prePostConditionMethod") String prePostConditionMethod, @HeaderParam("parameterNames") String parameterNames, @HeaderParam("parameterValues") String parameterValues)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = prePostConditionalManagementServicesLocal.evaluatePreCondition(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(prePostConditionMethod, PrePostConditionMethod.class), JSONConverterUtil.JSONToObject_JACKSON(parameterNames, List.class), JSONConverterUtil.JSONToObject_JACKSON(parameterValues, List.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/PrePostConditionalManagementServices/evaluatePostCondition")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String prePostConditionalManagementServices_evaluatePostCondition(@HeaderParam("session") String session, @HeaderParam("prePostConditionMethod") String prePostConditionMethod, @HeaderParam("parameterNames") String parameterNames, @HeaderParam("parameterValues") String parameterValues)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = prePostConditionalManagementServicesLocal.evaluatePostCondition(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(prePostConditionMethod, PrePostConditionMethod.class), JSONConverterUtil.JSONToObject_JACKSON(parameterNames, List.class), JSONConverterUtil.JSONToObject_JACKSON(parameterValues, List.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/ReportServices/loadAllAuditLogEntries")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String reportServices_loadAllAuditLogEntries(@HeaderParam("session") String session)
    {        
        try
        {
            Object[] payLoad = reportServicesLocal.loadAllAuditLogEntries(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/ReportServices/loadAllPersonEntities")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String reportServices_loadAllPersonEntities(@HeaderParam("session") String session)
    {        
        try
        {
            Object[] payLoad = reportServicesLocal.loadAllPersonEntities(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/ReportServices/loadAllApplicationEntities")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String reportServices_loadAllApplicationEntities(@HeaderParam("session") String session)
    {        
        try
        {
            Object[] payLoad = reportServicesLocal.loadAllApplicationEntities(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/ReportServices/getFieldNamesForEntity")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String reportServices_getFieldNamesForEntity(@HeaderParam("entity") String entity)
    {        
        try
        {
            Object[] payLoad = reportServicesLocal.getFieldNamesForEntity(JSONConverterUtil.JSONToObject_JACKSON(entity, Object.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/ReportServices/getConcatenatedFieldNamesForEntities")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String reportServices_getConcatenatedFieldNamesForEntities(@HeaderParam("entity") String entity)
    {        
        try
        {
            Object[] payLoad = reportServicesLocal.getConcatenatedFieldNamesForEntities(JSONConverterUtil.JSONToObject_JACKSON(entity, List.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/ReportServices/convertEntitiesToRowData")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String reportServices_convertEntitiesToRowData(@HeaderParam("entities") String entities, @HeaderParam("propertyMaps") String propertyMaps)
    {        
        try
        {
            Object[] payLoad = reportServicesLocal.convertEntitiesToRowData(JSONConverterUtil.JSONToObject_JACKSON(entities, List.class), JSONConverterUtil.JSONToObject_JACKSON(propertyMaps, List.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/ReportServices/createDynamicReport")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String reportServices_createDynamicReport(@HeaderParam("session") String session, @HeaderParam("dynamicReportCreationRequest") String dynamicReportCreationRequest)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = reportServicesLocal.createDynamicReport(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(dynamicReportCreationRequest, DynamicReportCreationRequest.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/ReportServices/renderReportAsHTML")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String reportServices_renderReportAsHTML(@HeaderParam("session") String session, @HeaderParam("dynamicReport") String dynamicReport, @HeaderParam("dynamicReportCreationRequest") String dynamicReportExportRequest)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = reportServicesLocal.renderReportAsHTML(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(dynamicReport, DynamicReport.class), JSONConverterUtil.JSONToObject_JACKSON(dynamicReportExportRequest, DynamicReportExportRequest.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/ReportServices/renderReportAsPDF")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String reportServices_renderReportAsPDF(@HeaderParam("session") String session, @HeaderParam("dynamicReport") String dynamicReport, @HeaderParam("dynamicReportCreationRequest") String dynamicReportExportRequest)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = reportServicesLocal.renderReportAsPDF(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(dynamicReport, DynamicReport.class), JSONConverterUtil.JSONToObject_JACKSON(dynamicReportExportRequest, DynamicReportExportRequest.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/ReportServices/renderReportAsMSEXCELSpreadsheet")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String reportServices_renderReportAsMSEXCELSpreadsheet(@HeaderParam("session") String session, @HeaderParam("dynamicReport") String dynamicReport, @HeaderParam("dynamicReportCreationRequest") String dynamicReportExportRequest)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = reportServicesLocal.renderReportAsMSEXCELSpreadsheet(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(dynamicReport, DynamicReport.class), JSONConverterUtil.JSONToObject_JACKSON(dynamicReportExportRequest, DynamicReportExportRequest.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/UserAccountManagementService/createUserAccount")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userAccountManagementService_createUserAccount(@HeaderParam("session") String session, @HeaderParam("useManualSystemIDSpecification") String useManualSystemIDSpecification, @HeaderParam("user") String user)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            userAccountManagementServiceLocal.createUserAccount(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(useManualSystemIDSpecification, boolean.class), JSONConverterUtil.JSONToObject_JACKSON(user, Person.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/UserAccountManagementService/updateUserAccount")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userAccountManagementService_updateUserAccount(@HeaderParam("session") String session, @HeaderParam("user") String user)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            userAccountManagementServiceLocal.updateUserAccount(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(user, Person.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/UserAccountManagementService/removeUserAccount")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userAccountManagementService_removeUserAccount(@HeaderParam("session") String session, @HeaderParam("systemID") String systemID)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            userAccountManagementServiceLocal.removeUserAccount(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), systemID);
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/UserAccountManagementService/accountReset")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userAccountManagementService_accountReset(@HeaderParam("user") String user)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            userAccountManagementServiceLocal.accountReset(JSONConverterUtil.JSONToObject_JACKSON(user, Person.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/UserAccountManagementService/viewAllUserAccounts")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userAccountManagementService_viewAllUserAccounts(@HeaderParam("session") String session)
    {        
        try
        {
            Object[] payLoad = userAccountManagementServiceLocal.viewAllUserAccounts(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/UserAccountManagementService/generateOnDemandAccount")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userAccountManagementService_generateOnDemandAccount(@HeaderParam("session") String session, @HeaderParam("reason") String reason, @HeaderParam("useManualSystemIDSpecification") String useManualSystemIDSpecification, @HeaderParam("user") String user)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            userAccountManagementServiceLocal.generateOnDemandAccount(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), reason, JSONConverterUtil.JSONToObject_JACKSON(useManualSystemIDSpecification, boolean.class), JSONConverterUtil.JSONToObject_JACKSON(user, Person.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/UserAccountManagementService/approveOnDemandAccount")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userAccountManagementService_approveOnDemandAccount(@HeaderParam("session") String session, @HeaderParam("user") String user)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            userAccountManagementServiceLocal.approveOnDemandAccount(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(user, Person.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/UserAccountManagementService/approveOnDemandAccount")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userAccountManagementService_declineOnDemandAccount(@HeaderParam("session") String session, @HeaderParam("user") String user)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            userAccountManagementServiceLocal.declineOnDemandAccount(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(user, Person.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/UserAccountManagementService/approveOnDemandAccount")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userAccountManagementService_activateOnDemandAccount(@HeaderParam("session") String session, @HeaderParam("user") String user)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            userAccountManagementServiceLocal.activateOnDemandAccount(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class), JSONConverterUtil.JSONToObject_JACKSON(user, Person.class));
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/UserAccountManagementService/loadAllPendingOnDemandAccounts")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userAccountManagementService_loadAllPendingOnDemandAccounts(@HeaderParam("session") String session)
    {        
        try
        {
            Object[] payLoad = userAccountManagementServiceLocal.loadAllPendingOnDemandAccounts(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/UserAccountManagementService/getUserBySystemIDOrEmail")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userAccountManagementService_getUserBySystemIDOrEmail(@HeaderParam("input") String input)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = userAccountManagementServiceLocal.getUserBySystemIDOrEmail(input);
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
    
    @Path("/UserAccountManagementService/getUserBySystemID")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userAccountManagementService_getUserBySystemID(@HeaderParam("input") String input)
    {        
        try
        {
            Object[] payLoad = new Object[1];
            payLoad[0] = userAccountManagementServiceLocal.getUserBySystemID(input);
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(NonApplicationServices.class, exception);
        }
    }
}
