/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.ResearchFellowInformation;
import com.softserve.DBEntities.EmployeeInformation;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.SecurityRole;
import java.util.ArrayList;
import java.util.List;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.DeclineReport;
import com.softserve.DBEntities.Endorsement;
import com.softserve.DBEntities.ApplicationReviewRequest;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.AmmendRequest;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.RecommendationReport;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.MinuteComment;
import com.softserve.DBEntities.EligiblityReport;
import com.softserve.DBEntities.ForwardAndRewindReport;
import com.softserve.DBEntities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class PersonJpaController implements Serializable {

    public PersonJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EntityManager em, Person person) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (person.getSecurityRoleList() == null) {
            person.setSecurityRoleList(new ArrayList<SecurityRole>());
        }
        if (person.getApplicationList() == null) {
            person.setApplicationList(new ArrayList<Application>());
        }
        if (person.getCommitteeMeetingList() == null) {
            person.setCommitteeMeetingList(new ArrayList<CommitteeMeeting>());
        }
        if (person.getDeclineReportList() == null) {
            person.setDeclineReportList(new ArrayList<DeclineReport>());
        }
        if (person.getEndorsementList() == null) {
            person.setEndorsementList(new ArrayList<Endorsement>());
        }
        if (person.getApplicationReviewRequestList() == null) {
            person.setApplicationReviewRequestList(new ArrayList<ApplicationReviewRequest>());
        }
        if (person.getRefereeReportList() == null) {
            person.setRefereeReportList(new ArrayList<RefereeReport>());
        }
        if (person.getNotificationList() == null) {
            person.setNotificationList(new ArrayList<Notification>());
        }
        if (person.getNotificationList1() == null) {
            person.setNotificationList1(new ArrayList<Notification>());
        }
        if (person.getAmmendRequestList() == null) {
            person.setAmmendRequestList(new ArrayList<AmmendRequest>());
        }
        if (person.getCommitteeMeetingList1() == null) {
            person.setCommitteeMeetingList1(new ArrayList<CommitteeMeeting>());
        }
        if (person.getAuditLogList() == null) {
            person.setAuditLogList(new ArrayList<AuditLog>());
        }
        if (person.getRecommendationReportList() == null) {
            person.setRecommendationReportList(new ArrayList<RecommendationReport>());
        }
        if (person.getFundingReportList() == null) {
            person.setFundingReportList(new ArrayList<FundingReport>());
        }
        if (person.getApplicationList1() == null) {
            person.setApplicationList1(new ArrayList<Application>());
        }
        if (person.getApplicationList2() == null) {
            person.setApplicationList2(new ArrayList<Application>());
        }
        if (person.getMinuteCommentList() == null) {
            person.setMinuteCommentList(new ArrayList<MinuteComment>());
        }
        if (person.getEligiblityReportList() == null) {
            person.setEligiblityReportList(new ArrayList<EligiblityReport>());
        }
        if (person.getForwardAndRewindReportList() == null) {
            person.setForwardAndRewindReportList(new ArrayList<ForwardAndRewindReport>());
        }

        em = getEntityManager();
        ResearchFellowInformation researchFellowInformation = person.getResearchFellowInformation();
        if (researchFellowInformation != null) {
            researchFellowInformation = em.getReference(researchFellowInformation.getClass(), researchFellowInformation.getSystemAssignedID());
            person.setResearchFellowInformation(researchFellowInformation);
        }
        EmployeeInformation employeeInformation = person.getEmployeeInformation();
        if (employeeInformation != null) {
            employeeInformation = em.getReference(employeeInformation.getClass(), employeeInformation.getEmployeeID());
            person.setEmployeeInformation(employeeInformation);
        }
        Cv cv = person.getCv();
        if (cv != null) {
            cv = em.getReference(cv.getClass(), cv.getCvID());
            person.setCv(cv);
        }
        Address addressLine1 = person.getAddressLine1();
        if (addressLine1 != null) {
            addressLine1 = em.getReference(addressLine1.getClass(), addressLine1.getAddressID());
            person.setAddressLine1(addressLine1);
        }
        List<SecurityRole> attachedSecurityRoleList = new ArrayList<SecurityRole>();
        for (SecurityRole securityRoleListSecurityRoleToAttach : person.getSecurityRoleList()) {
            securityRoleListSecurityRoleToAttach = em.getReference(securityRoleListSecurityRoleToAttach.getClass(), securityRoleListSecurityRoleToAttach.getRoleID());
            attachedSecurityRoleList.add(securityRoleListSecurityRoleToAttach);
        }
        person.setSecurityRoleList(attachedSecurityRoleList);
        List<Application> attachedApplicationList = new ArrayList<Application>();
        for (Application applicationListApplicationToAttach : person.getApplicationList()) {
            applicationListApplicationToAttach = em.getReference(applicationListApplicationToAttach.getClass(), applicationListApplicationToAttach.getApplicationID());
            attachedApplicationList.add(applicationListApplicationToAttach);
        }
        person.setApplicationList(attachedApplicationList);
        List<CommitteeMeeting> attachedCommitteeMeetingList = new ArrayList<CommitteeMeeting>();
        for (CommitteeMeeting committeeMeetingListCommitteeMeetingToAttach : person.getCommitteeMeetingList()) {
            committeeMeetingListCommitteeMeetingToAttach = em.getReference(committeeMeetingListCommitteeMeetingToAttach.getClass(), committeeMeetingListCommitteeMeetingToAttach.getMeetingID());
            attachedCommitteeMeetingList.add(committeeMeetingListCommitteeMeetingToAttach);
        }
        person.setCommitteeMeetingList(attachedCommitteeMeetingList);
        List<DeclineReport> attachedDeclineReportList = new ArrayList<DeclineReport>();
        for (DeclineReport declineReportListDeclineReportToAttach : person.getDeclineReportList()) {
            declineReportListDeclineReportToAttach = em.getReference(declineReportListDeclineReportToAttach.getClass(), declineReportListDeclineReportToAttach.getReportID());
            attachedDeclineReportList.add(declineReportListDeclineReportToAttach);
        }
        person.setDeclineReportList(attachedDeclineReportList);
        List<Endorsement> attachedEndorsementList = new ArrayList<Endorsement>();
        for (Endorsement endorsementListEndorsementToAttach : person.getEndorsementList()) {
            endorsementListEndorsementToAttach = em.getReference(endorsementListEndorsementToAttach.getClass(), endorsementListEndorsementToAttach.getEndorsementID());
            attachedEndorsementList.add(endorsementListEndorsementToAttach);
        }
        person.setEndorsementList(attachedEndorsementList);
        List<ApplicationReviewRequest> attachedApplicationReviewRequestList = new ArrayList<ApplicationReviewRequest>();
        for (ApplicationReviewRequest applicationReviewRequestListApplicationReviewRequestToAttach : person.getApplicationReviewRequestList()) {
            applicationReviewRequestListApplicationReviewRequestToAttach = em.getReference(applicationReviewRequestListApplicationReviewRequestToAttach.getClass(), applicationReviewRequestListApplicationReviewRequestToAttach.getApplicationReviewRequestPK());
            attachedApplicationReviewRequestList.add(applicationReviewRequestListApplicationReviewRequestToAttach);
        }
        person.setApplicationReviewRequestList(attachedApplicationReviewRequestList);
        List<RefereeReport> attachedRefereeReportList = new ArrayList<RefereeReport>();
        for (RefereeReport refereeReportListRefereeReportToAttach : person.getRefereeReportList()) {
            refereeReportListRefereeReportToAttach = em.getReference(refereeReportListRefereeReportToAttach.getClass(), refereeReportListRefereeReportToAttach.getReportID());
            attachedRefereeReportList.add(refereeReportListRefereeReportToAttach);
        }
        person.setRefereeReportList(attachedRefereeReportList);
        List<Notification> attachedNotificationList = new ArrayList<Notification>();
        for (Notification notificationListNotificationToAttach : person.getNotificationList()) {
            notificationListNotificationToAttach = em.getReference(notificationListNotificationToAttach.getClass(), notificationListNotificationToAttach.getNotificationID());
            attachedNotificationList.add(notificationListNotificationToAttach);
        }
        person.setNotificationList(attachedNotificationList);
        List<Notification> attachedNotificationList1 = new ArrayList<Notification>();
        for (Notification notificationList1NotificationToAttach : person.getNotificationList1()) {
            notificationList1NotificationToAttach = em.getReference(notificationList1NotificationToAttach.getClass(), notificationList1NotificationToAttach.getNotificationID());
            attachedNotificationList1.add(notificationList1NotificationToAttach);
        }
        person.setNotificationList1(attachedNotificationList1);
        List<AmmendRequest> attachedAmmendRequestList = new ArrayList<AmmendRequest>();
        for (AmmendRequest ammendRequestListAmmendRequestToAttach : person.getAmmendRequestList()) {
            ammendRequestListAmmendRequestToAttach = em.getReference(ammendRequestListAmmendRequestToAttach.getClass(), ammendRequestListAmmendRequestToAttach.getRequestID());
            attachedAmmendRequestList.add(ammendRequestListAmmendRequestToAttach);
        }
        person.setAmmendRequestList(attachedAmmendRequestList);
        List<CommitteeMeeting> attachedCommitteeMeetingList1 = new ArrayList<CommitteeMeeting>();
        for (CommitteeMeeting committeeMeetingList1CommitteeMeetingToAttach : person.getCommitteeMeetingList1()) {
            committeeMeetingList1CommitteeMeetingToAttach = em.getReference(committeeMeetingList1CommitteeMeetingToAttach.getClass(), committeeMeetingList1CommitteeMeetingToAttach.getMeetingID());
            attachedCommitteeMeetingList1.add(committeeMeetingList1CommitteeMeetingToAttach);
        }
        person.setCommitteeMeetingList1(attachedCommitteeMeetingList1);
        List<AuditLog> attachedAuditLogList = new ArrayList<AuditLog>();
        for (AuditLog auditLogListAuditLogToAttach : person.getAuditLogList()) {
            auditLogListAuditLogToAttach = em.getReference(auditLogListAuditLogToAttach.getClass(), auditLogListAuditLogToAttach.getEntryID());
            attachedAuditLogList.add(auditLogListAuditLogToAttach);
        }
        person.setAuditLogList(attachedAuditLogList);
        List<RecommendationReport> attachedRecommendationReportList = new ArrayList<RecommendationReport>();
        for (RecommendationReport recommendationReportListRecommendationReportToAttach : person.getRecommendationReportList()) {
            recommendationReportListRecommendationReportToAttach = em.getReference(recommendationReportListRecommendationReportToAttach.getClass(), recommendationReportListRecommendationReportToAttach.getReportID());
            attachedRecommendationReportList.add(recommendationReportListRecommendationReportToAttach);
        }
        person.setRecommendationReportList(attachedRecommendationReportList);
        List<FundingReport> attachedFundingReportList = new ArrayList<FundingReport>();
        for (FundingReport fundingReportListFundingReportToAttach : person.getFundingReportList()) {
            fundingReportListFundingReportToAttach = em.getReference(fundingReportListFundingReportToAttach.getClass(), fundingReportListFundingReportToAttach.getReportID());
            attachedFundingReportList.add(fundingReportListFundingReportToAttach);
        }
        person.setFundingReportList(attachedFundingReportList);
        List<Application> attachedApplicationList1 = new ArrayList<Application>();
        for (Application applicationList1ApplicationToAttach : person.getApplicationList1()) {
            applicationList1ApplicationToAttach = em.getReference(applicationList1ApplicationToAttach.getClass(), applicationList1ApplicationToAttach.getApplicationID());
            attachedApplicationList1.add(applicationList1ApplicationToAttach);
        }
        person.setApplicationList1(attachedApplicationList1);
        List<Application> attachedApplicationList2 = new ArrayList<Application>();
        for (Application applicationList2ApplicationToAttach : person.getApplicationList2()) {
            applicationList2ApplicationToAttach = em.getReference(applicationList2ApplicationToAttach.getClass(), applicationList2ApplicationToAttach.getApplicationID());
            attachedApplicationList2.add(applicationList2ApplicationToAttach);
        }
        person.setApplicationList2(attachedApplicationList2);
        List<MinuteComment> attachedMinuteCommentList = new ArrayList<MinuteComment>();
        for (MinuteComment minuteCommentListMinuteCommentToAttach : person.getMinuteCommentList()) {
            minuteCommentListMinuteCommentToAttach = em.getReference(minuteCommentListMinuteCommentToAttach.getClass(), minuteCommentListMinuteCommentToAttach.getCommentID());
            attachedMinuteCommentList.add(minuteCommentListMinuteCommentToAttach);
        }
        person.setMinuteCommentList(attachedMinuteCommentList);
        List<EligiblityReport> attachedEligiblityReportList = new ArrayList<EligiblityReport>();
        for (EligiblityReport eligiblityReportListEligiblityReportToAttach : person.getEligiblityReportList()) {
            eligiblityReportListEligiblityReportToAttach = em.getReference(eligiblityReportListEligiblityReportToAttach.getClass(), eligiblityReportListEligiblityReportToAttach.getReportID());
            attachedEligiblityReportList.add(eligiblityReportListEligiblityReportToAttach);
        }
        person.setEligiblityReportList(attachedEligiblityReportList);
        List<ForwardAndRewindReport> attachedForwardAndRewindReportList = new ArrayList<ForwardAndRewindReport>();
        for (ForwardAndRewindReport forwardAndRewindReportListForwardAndRewindReportToAttach : person.getForwardAndRewindReportList()) {
            forwardAndRewindReportListForwardAndRewindReportToAttach = em.getReference(forwardAndRewindReportListForwardAndRewindReportToAttach.getClass(), forwardAndRewindReportListForwardAndRewindReportToAttach.getReportID());
            attachedForwardAndRewindReportList.add(forwardAndRewindReportListForwardAndRewindReportToAttach);
        }
        person.setForwardAndRewindReportList(attachedForwardAndRewindReportList);
        em.persist(person);
        if (researchFellowInformation != null) {
            Person oldPersonOfResearchFellowInformation = researchFellowInformation.getPerson();
            if (oldPersonOfResearchFellowInformation != null) {
                oldPersonOfResearchFellowInformation.setResearchFellowInformation(null);
                oldPersonOfResearchFellowInformation = em.merge(oldPersonOfResearchFellowInformation);
            }
            researchFellowInformation.setPerson(person);
            researchFellowInformation = em.merge(researchFellowInformation);
        }
        if (employeeInformation != null) {
            Person oldPersonOfEmployeeInformation = employeeInformation.getPerson();
            if (oldPersonOfEmployeeInformation != null) {
                oldPersonOfEmployeeInformation.setEmployeeInformation(null);
                oldPersonOfEmployeeInformation = em.merge(oldPersonOfEmployeeInformation);
            }
            employeeInformation.setPerson(person);
            employeeInformation = em.merge(employeeInformation);
        }
        if (cv != null) {
            Person oldPersonOfCv = cv.getPerson();
            if (oldPersonOfCv != null) {
                oldPersonOfCv.setCv(null);
                oldPersonOfCv = em.merge(oldPersonOfCv);
            }
            cv.setPerson(person);
            cv = em.merge(cv);
        }
        if (addressLine1 != null) {
            addressLine1.getPersonList().add(person);
            addressLine1 = em.merge(addressLine1);
        }
        for (SecurityRole securityRoleListSecurityRole : person.getSecurityRoleList()) {
            securityRoleListSecurityRole.getPersonList().add(person);
            securityRoleListSecurityRole = em.merge(securityRoleListSecurityRole);
        }
        for (Application applicationListApplication : person.getApplicationList()) {
            applicationListApplication.getPersonList().add(person);
            applicationListApplication = em.merge(applicationListApplication);
        }
        for (CommitteeMeeting committeeMeetingListCommitteeMeeting : person.getCommitteeMeetingList()) {
            committeeMeetingListCommitteeMeeting.getPersonList().add(person);
            committeeMeetingListCommitteeMeeting = em.merge(committeeMeetingListCommitteeMeeting);
        }
        for (DeclineReport declineReportListDeclineReport : person.getDeclineReportList()) {
            Person oldCreatorOfDeclineReportListDeclineReport = declineReportListDeclineReport.getCreator();
            declineReportListDeclineReport.setCreator(person);
            declineReportListDeclineReport = em.merge(declineReportListDeclineReport);
            if (oldCreatorOfDeclineReportListDeclineReport != null) {
                oldCreatorOfDeclineReportListDeclineReport.getDeclineReportList().remove(declineReportListDeclineReport);
                oldCreatorOfDeclineReportListDeclineReport = em.merge(oldCreatorOfDeclineReportListDeclineReport);
            }
        }
        for (Endorsement endorsementListEndorsement : person.getEndorsementList()) {
            Person oldDeanOfEndorsementListEndorsement = endorsementListEndorsement.getDean();
            endorsementListEndorsement.setDean(person);
            endorsementListEndorsement = em.merge(endorsementListEndorsement);
            if (oldDeanOfEndorsementListEndorsement != null) {
                oldDeanOfEndorsementListEndorsement.getEndorsementList().remove(endorsementListEndorsement);
                oldDeanOfEndorsementListEndorsement = em.merge(oldDeanOfEndorsementListEndorsement);
            }
        }
        for (ApplicationReviewRequest applicationReviewRequestListApplicationReviewRequest : person.getApplicationReviewRequestList()) {
            Person oldPerson1OfApplicationReviewRequestListApplicationReviewRequest = applicationReviewRequestListApplicationReviewRequest.getPerson1();
            applicationReviewRequestListApplicationReviewRequest.setPerson1(person);
            applicationReviewRequestListApplicationReviewRequest = em.merge(applicationReviewRequestListApplicationReviewRequest);
            if (oldPerson1OfApplicationReviewRequestListApplicationReviewRequest != null) {
                oldPerson1OfApplicationReviewRequestListApplicationReviewRequest.getApplicationReviewRequestList().remove(applicationReviewRequestListApplicationReviewRequest);
                oldPerson1OfApplicationReviewRequestListApplicationReviewRequest = em.merge(oldPerson1OfApplicationReviewRequestListApplicationReviewRequest);
            }
        }
        for (RefereeReport refereeReportListRefereeReport : person.getRefereeReportList()) {
            Person oldRefereeOfRefereeReportListRefereeReport = refereeReportListRefereeReport.getReferee();
            refereeReportListRefereeReport.setReferee(person);
            refereeReportListRefereeReport = em.merge(refereeReportListRefereeReport);
            if (oldRefereeOfRefereeReportListRefereeReport != null) {
                oldRefereeOfRefereeReportListRefereeReport.getRefereeReportList().remove(refereeReportListRefereeReport);
                oldRefereeOfRefereeReportListRefereeReport = em.merge(oldRefereeOfRefereeReportListRefereeReport);
            }
        }
        for (Notification notificationListNotification : person.getNotificationList()) {
            Person oldSenderOfNotificationListNotification = notificationListNotification.getSender();
            notificationListNotification.setSender(person);
            notificationListNotification = em.merge(notificationListNotification);
            if (oldSenderOfNotificationListNotification != null) {
                oldSenderOfNotificationListNotification.getNotificationList().remove(notificationListNotification);
                oldSenderOfNotificationListNotification = em.merge(oldSenderOfNotificationListNotification);
            }
        }
        for (Notification notificationList1Notification : person.getNotificationList1()) {
            Person oldRecieverOfNotificationList1Notification = notificationList1Notification.getReciever();
            notificationList1Notification.setReciever(person);
            notificationList1Notification = em.merge(notificationList1Notification);
            if (oldRecieverOfNotificationList1Notification != null) {
                oldRecieverOfNotificationList1Notification.getNotificationList1().remove(notificationList1Notification);
                oldRecieverOfNotificationList1Notification = em.merge(oldRecieverOfNotificationList1Notification);
            }
        }
        for (AmmendRequest ammendRequestListAmmendRequest : person.getAmmendRequestList()) {
            Person oldCreatorOfAmmendRequestListAmmendRequest = ammendRequestListAmmendRequest.getCreator();
            ammendRequestListAmmendRequest.setCreator(person);
            ammendRequestListAmmendRequest = em.merge(ammendRequestListAmmendRequest);
            if (oldCreatorOfAmmendRequestListAmmendRequest != null) {
                oldCreatorOfAmmendRequestListAmmendRequest.getAmmendRequestList().remove(ammendRequestListAmmendRequest);
                oldCreatorOfAmmendRequestListAmmendRequest = em.merge(oldCreatorOfAmmendRequestListAmmendRequest);
            }
        }
        for (CommitteeMeeting committeeMeetingList1CommitteeMeeting : person.getCommitteeMeetingList1()) {
            Person oldOrganiserOfCommitteeMeetingList1CommitteeMeeting = committeeMeetingList1CommitteeMeeting.getOrganiser();
            committeeMeetingList1CommitteeMeeting.setOrganiser(person);
            committeeMeetingList1CommitteeMeeting = em.merge(committeeMeetingList1CommitteeMeeting);
            if (oldOrganiserOfCommitteeMeetingList1CommitteeMeeting != null) {
                oldOrganiserOfCommitteeMeetingList1CommitteeMeeting.getCommitteeMeetingList1().remove(committeeMeetingList1CommitteeMeeting);
                oldOrganiserOfCommitteeMeetingList1CommitteeMeeting = em.merge(oldOrganiserOfCommitteeMeetingList1CommitteeMeeting);
            }
        }
        for (AuditLog auditLogListAuditLog : person.getAuditLogList()) {
            Person oldPersonOfAuditLogListAuditLog = auditLogListAuditLog.getPerson();
            auditLogListAuditLog.setPerson(person);
            auditLogListAuditLog = em.merge(auditLogListAuditLog);
            if (oldPersonOfAuditLogListAuditLog != null) {
                oldPersonOfAuditLogListAuditLog.getAuditLogList().remove(auditLogListAuditLog);
                oldPersonOfAuditLogListAuditLog = em.merge(oldPersonOfAuditLogListAuditLog);
            }
        }
        for (RecommendationReport recommendationReportListRecommendationReport : person.getRecommendationReportList()) {
            Person oldHodOfRecommendationReportListRecommendationReport = recommendationReportListRecommendationReport.getHod();
            recommendationReportListRecommendationReport.setHod(person);
            recommendationReportListRecommendationReport = em.merge(recommendationReportListRecommendationReport);
            if (oldHodOfRecommendationReportListRecommendationReport != null) {
                oldHodOfRecommendationReportListRecommendationReport.getRecommendationReportList().remove(recommendationReportListRecommendationReport);
                oldHodOfRecommendationReportListRecommendationReport = em.merge(oldHodOfRecommendationReportListRecommendationReport);
            }
        }
        for (FundingReport fundingReportListFundingReport : person.getFundingReportList()) {
            Person oldDrisOfFundingReportListFundingReport = fundingReportListFundingReport.getDris();
            fundingReportListFundingReport.setDris(person);
            fundingReportListFundingReport = em.merge(fundingReportListFundingReport);
            if (oldDrisOfFundingReportListFundingReport != null) {
                oldDrisOfFundingReportListFundingReport.getFundingReportList().remove(fundingReportListFundingReport);
                oldDrisOfFundingReportListFundingReport = em.merge(oldDrisOfFundingReportListFundingReport);
            }
        }
        for (Application applicationList1Application : person.getApplicationList1()) {
            Person oldFellowOfApplicationList1Application = applicationList1Application.getFellow();
            applicationList1Application.setFellow(person);
            applicationList1Application = em.merge(applicationList1Application);
            if (oldFellowOfApplicationList1Application != null) {
                oldFellowOfApplicationList1Application.getApplicationList1().remove(applicationList1Application);
                oldFellowOfApplicationList1Application = em.merge(oldFellowOfApplicationList1Application);
            }
        }
        for (Application applicationList2Application : person.getApplicationList2()) {
            Person oldGrantHolderOfApplicationList2Application = applicationList2Application.getGrantHolder();
            applicationList2Application.setGrantHolder(person);
            applicationList2Application = em.merge(applicationList2Application);
            if (oldGrantHolderOfApplicationList2Application != null) {
                oldGrantHolderOfApplicationList2Application.getApplicationList2().remove(applicationList2Application);
                oldGrantHolderOfApplicationList2Application = em.merge(oldGrantHolderOfApplicationList2Application);
            }
        }
        for (MinuteComment minuteCommentListMinuteComment : person.getMinuteCommentList()) {
            Person oldAttendeeOfMinuteCommentListMinuteComment = minuteCommentListMinuteComment.getAttendee();
            minuteCommentListMinuteComment.setAttendee(person);
            minuteCommentListMinuteComment = em.merge(minuteCommentListMinuteComment);
            if (oldAttendeeOfMinuteCommentListMinuteComment != null) {
                oldAttendeeOfMinuteCommentListMinuteComment.getMinuteCommentList().remove(minuteCommentListMinuteComment);
                oldAttendeeOfMinuteCommentListMinuteComment = em.merge(oldAttendeeOfMinuteCommentListMinuteComment);
            }
        }
        for (EligiblityReport eligiblityReportListEligiblityReport : person.getEligiblityReportList()) {
            Person oldEligiblityCheckerOfEligiblityReportListEligiblityReport = eligiblityReportListEligiblityReport.getEligiblityChecker();
            eligiblityReportListEligiblityReport.setEligiblityChecker(person);
            eligiblityReportListEligiblityReport = em.merge(eligiblityReportListEligiblityReport);
            if (oldEligiblityCheckerOfEligiblityReportListEligiblityReport != null) {
                oldEligiblityCheckerOfEligiblityReportListEligiblityReport.getEligiblityReportList().remove(eligiblityReportListEligiblityReport);
                oldEligiblityCheckerOfEligiblityReportListEligiblityReport = em.merge(oldEligiblityCheckerOfEligiblityReportListEligiblityReport);
            }
        }
        for (ForwardAndRewindReport forwardAndRewindReportListForwardAndRewindReport : person.getForwardAndRewindReportList()) {
            Person oldDrisOfForwardAndRewindReportListForwardAndRewindReport = forwardAndRewindReportListForwardAndRewindReport.getDris();
            forwardAndRewindReportListForwardAndRewindReport.setDris(person);
            forwardAndRewindReportListForwardAndRewindReport = em.merge(forwardAndRewindReportListForwardAndRewindReport);
            if (oldDrisOfForwardAndRewindReportListForwardAndRewindReport != null) {
                oldDrisOfForwardAndRewindReportListForwardAndRewindReport.getForwardAndRewindReportList().remove(forwardAndRewindReportListForwardAndRewindReport);
                oldDrisOfForwardAndRewindReportListForwardAndRewindReport = em.merge(oldDrisOfForwardAndRewindReportListForwardAndRewindReport);
            }
        }
            
    }

    public void edit(EntityManager em, Person person) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {

        String id = person.getSystemID();
        if (findPerson(id) == null) {
            throw new NonexistentEntityException("The person with id " + id + " no longer exists.");
        }
        
        Person persistentPerson = em.find(Person.class, person.getSystemID());
        ResearchFellowInformation researchFellowInformationOld = persistentPerson.getResearchFellowInformation();
        ResearchFellowInformation researchFellowInformationNew = person.getResearchFellowInformation();
        EmployeeInformation employeeInformationOld = persistentPerson.getEmployeeInformation();
        EmployeeInformation employeeInformationNew = person.getEmployeeInformation();
        Cv cvOld = persistentPerson.getCv();
        Cv cvNew = person.getCv();
        Address addressLine1Old = persistentPerson.getAddressLine1();
        Address addressLine1New = person.getAddressLine1();
        List<SecurityRole> securityRoleListOld = persistentPerson.getSecurityRoleList();
        List<SecurityRole> securityRoleListNew = person.getSecurityRoleList();
        List<Application> applicationListOld = persistentPerson.getApplicationList();
        List<Application> applicationListNew = person.getApplicationList();
        List<CommitteeMeeting> committeeMeetingListOld = persistentPerson.getCommitteeMeetingList();
        List<CommitteeMeeting> committeeMeetingListNew = person.getCommitteeMeetingList();
        List<DeclineReport> declineReportListOld = persistentPerson.getDeclineReportList();
        List<DeclineReport> declineReportListNew = person.getDeclineReportList();
        List<Endorsement> endorsementListOld = persistentPerson.getEndorsementList();
        List<Endorsement> endorsementListNew = person.getEndorsementList();
        List<ApplicationReviewRequest> applicationReviewRequestListOld = persistentPerson.getApplicationReviewRequestList();
        List<ApplicationReviewRequest> applicationReviewRequestListNew = person.getApplicationReviewRequestList();
        List<RefereeReport> refereeReportListOld = persistentPerson.getRefereeReportList();
        List<RefereeReport> refereeReportListNew = person.getRefereeReportList();
        List<Notification> notificationListOld = persistentPerson.getNotificationList();
        List<Notification> notificationListNew = person.getNotificationList();
        List<Notification> notificationList1Old = persistentPerson.getNotificationList1();
        List<Notification> notificationList1New = person.getNotificationList1();
        List<AmmendRequest> ammendRequestListOld = persistentPerson.getAmmendRequestList();
        List<AmmendRequest> ammendRequestListNew = person.getAmmendRequestList();
        List<CommitteeMeeting> committeeMeetingList1Old = persistentPerson.getCommitteeMeetingList1();
        List<CommitteeMeeting> committeeMeetingList1New = person.getCommitteeMeetingList1();
        List<AuditLog> auditLogListOld = persistentPerson.getAuditLogList();
        List<AuditLog> auditLogListNew = person.getAuditLogList();
        List<RecommendationReport> recommendationReportListOld = persistentPerson.getRecommendationReportList();
        List<RecommendationReport> recommendationReportListNew = person.getRecommendationReportList();
        List<FundingReport> fundingReportListOld = persistentPerson.getFundingReportList();
        List<FundingReport> fundingReportListNew = person.getFundingReportList();
        List<Application> applicationList1Old = persistentPerson.getApplicationList1();
        List<Application> applicationList1New = person.getApplicationList1();
        List<Application> applicationList2Old = persistentPerson.getApplicationList2();
        List<Application> applicationList2New = person.getApplicationList2();
        List<MinuteComment> minuteCommentListOld = persistentPerson.getMinuteCommentList();
        List<MinuteComment> minuteCommentListNew = person.getMinuteCommentList();
        List<EligiblityReport> eligiblityReportListOld = persistentPerson.getEligiblityReportList();
        List<EligiblityReport> eligiblityReportListNew = person.getEligiblityReportList();
        List<ForwardAndRewindReport> forwardAndRewindReportListOld = persistentPerson.getForwardAndRewindReportList();
        List<ForwardAndRewindReport> forwardAndRewindReportListNew = person.getForwardAndRewindReportList();
        List<String> illegalOrphanMessages = null;
        if (researchFellowInformationOld != null && !researchFellowInformationOld.equals(researchFellowInformationNew)) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("You must retain ResearchFellowInformation " + researchFellowInformationOld + " since its person field is not nullable.");
        }
        if (employeeInformationOld != null && !employeeInformationOld.equals(employeeInformationNew)) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("You must retain EmployeeInformation " + employeeInformationOld + " since its person field is not nullable.");
        }
        if (cvOld != null && !cvOld.equals(cvNew)) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("You must retain Cv " + cvOld + " since its person field is not nullable.");
        }
        for (DeclineReport declineReportListOldDeclineReport : declineReportListOld) {
            if (!declineReportListNew.contains(declineReportListOldDeclineReport)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain DeclineReport " + declineReportListOldDeclineReport + " since its creator field is not nullable.");
            }
        }
        for (Endorsement endorsementListOldEndorsement : endorsementListOld) {
            if (!endorsementListNew.contains(endorsementListOldEndorsement)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Endorsement " + endorsementListOldEndorsement + " since its dean field is not nullable.");
            }
        }
        for (ApplicationReviewRequest applicationReviewRequestListOldApplicationReviewRequest : applicationReviewRequestListOld) {
            if (!applicationReviewRequestListNew.contains(applicationReviewRequestListOldApplicationReviewRequest)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ApplicationReviewRequest " + applicationReviewRequestListOldApplicationReviewRequest + " since its person1 field is not nullable.");
            }
        }
        for (RefereeReport refereeReportListOldRefereeReport : refereeReportListOld) {
            if (!refereeReportListNew.contains(refereeReportListOldRefereeReport)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain RefereeReport " + refereeReportListOldRefereeReport + " since its referee field is not nullable.");
            }
        }
        for (Notification notificationListOldNotification : notificationListOld) {
            if (!notificationListNew.contains(notificationListOldNotification)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Notification " + notificationListOldNotification + " since its sender field is not nullable.");
            }
        }
        for (Notification notificationList1OldNotification : notificationList1Old) {
            if (!notificationList1New.contains(notificationList1OldNotification)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Notification " + notificationList1OldNotification + " since its reciever field is not nullable.");
            }
        }
        for (AmmendRequest ammendRequestListOldAmmendRequest : ammendRequestListOld) {
            if (!ammendRequestListNew.contains(ammendRequestListOldAmmendRequest)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain AmmendRequest " + ammendRequestListOldAmmendRequest + " since its creator field is not nullable.");
            }
        }
        for (CommitteeMeeting committeeMeetingList1OldCommitteeMeeting : committeeMeetingList1Old) {
            if (!committeeMeetingList1New.contains(committeeMeetingList1OldCommitteeMeeting)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain CommitteeMeeting " + committeeMeetingList1OldCommitteeMeeting + " since its organiser field is not nullable.");
            }
        }
        for (AuditLog auditLogListOldAuditLog : auditLogListOld) {
            if (!auditLogListNew.contains(auditLogListOldAuditLog)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain AuditLog " + auditLogListOldAuditLog + " since its person field is not nullable.");
            }
        }
        for (RecommendationReport recommendationReportListOldRecommendationReport : recommendationReportListOld) {
            if (!recommendationReportListNew.contains(recommendationReportListOldRecommendationReport)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain RecommendationReport " + recommendationReportListOldRecommendationReport + " since its hod field is not nullable.");
            }
        }
        for (FundingReport fundingReportListOldFundingReport : fundingReportListOld) {
            if (!fundingReportListNew.contains(fundingReportListOldFundingReport)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain FundingReport " + fundingReportListOldFundingReport + " since its dris field is not nullable.");
            }
        }
        for (Application applicationList1OldApplication : applicationList1Old) {
            if (!applicationList1New.contains(applicationList1OldApplication)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Application " + applicationList1OldApplication + " since its fellow field is not nullable.");
            }
        }
        for (MinuteComment minuteCommentListOldMinuteComment : minuteCommentListOld) {
            if (!minuteCommentListNew.contains(minuteCommentListOldMinuteComment)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain MinuteComment " + minuteCommentListOldMinuteComment + " since its attendee field is not nullable.");
            }
        }
        for (EligiblityReport eligiblityReportListOldEligiblityReport : eligiblityReportListOld) {
            if (!eligiblityReportListNew.contains(eligiblityReportListOldEligiblityReport)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain EligiblityReport " + eligiblityReportListOldEligiblityReport + " since its eligiblityChecker field is not nullable.");
            }
        }
        for (ForwardAndRewindReport forwardAndRewindReportListOldForwardAndRewindReport : forwardAndRewindReportListOld) {
            if (!forwardAndRewindReportListNew.contains(forwardAndRewindReportListOldForwardAndRewindReport)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ForwardAndRewindReport " + forwardAndRewindReportListOldForwardAndRewindReport + " since its dris field is not nullable.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        if (researchFellowInformationNew != null) {
            researchFellowInformationNew = em.getReference(researchFellowInformationNew.getClass(), researchFellowInformationNew.getSystemAssignedID());
            person.setResearchFellowInformation(researchFellowInformationNew);
        }
        if (employeeInformationNew != null) {
            employeeInformationNew = em.getReference(employeeInformationNew.getClass(), employeeInformationNew.getEmployeeID());
            person.setEmployeeInformation(employeeInformationNew);
        }
        if (cvNew != null) {
            cvNew = em.getReference(cvNew.getClass(), cvNew.getCvID());
            person.setCv(cvNew);
        }
        if (addressLine1New != null) {
            addressLine1New = em.getReference(addressLine1New.getClass(), addressLine1New.getAddressID());
            person.setAddressLine1(addressLine1New);
        }
        List<SecurityRole> attachedSecurityRoleListNew = new ArrayList<SecurityRole>();
        for (SecurityRole securityRoleListNewSecurityRoleToAttach : securityRoleListNew) {
            securityRoleListNewSecurityRoleToAttach = em.getReference(securityRoleListNewSecurityRoleToAttach.getClass(), securityRoleListNewSecurityRoleToAttach.getRoleID());
            attachedSecurityRoleListNew.add(securityRoleListNewSecurityRoleToAttach);
        }
        securityRoleListNew = attachedSecurityRoleListNew;
        person.setSecurityRoleList(securityRoleListNew);
        List<Application> attachedApplicationListNew = new ArrayList<Application>();
        for (Application applicationListNewApplicationToAttach : applicationListNew) {
            applicationListNewApplicationToAttach = em.getReference(applicationListNewApplicationToAttach.getClass(), applicationListNewApplicationToAttach.getApplicationID());
            attachedApplicationListNew.add(applicationListNewApplicationToAttach);
        }
        applicationListNew = attachedApplicationListNew;
        person.setApplicationList(applicationListNew);
        List<CommitteeMeeting> attachedCommitteeMeetingListNew = new ArrayList<CommitteeMeeting>();
        for (CommitteeMeeting committeeMeetingListNewCommitteeMeetingToAttach : committeeMeetingListNew) {
            committeeMeetingListNewCommitteeMeetingToAttach = em.getReference(committeeMeetingListNewCommitteeMeetingToAttach.getClass(), committeeMeetingListNewCommitteeMeetingToAttach.getMeetingID());
            attachedCommitteeMeetingListNew.add(committeeMeetingListNewCommitteeMeetingToAttach);
        }
        committeeMeetingListNew = attachedCommitteeMeetingListNew;
        person.setCommitteeMeetingList(committeeMeetingListNew);
        List<DeclineReport> attachedDeclineReportListNew = new ArrayList<DeclineReport>();
        for (DeclineReport declineReportListNewDeclineReportToAttach : declineReportListNew) {
            declineReportListNewDeclineReportToAttach = em.getReference(declineReportListNewDeclineReportToAttach.getClass(), declineReportListNewDeclineReportToAttach.getReportID());
            attachedDeclineReportListNew.add(declineReportListNewDeclineReportToAttach);
        }
        declineReportListNew = attachedDeclineReportListNew;
        person.setDeclineReportList(declineReportListNew);
        List<Endorsement> attachedEndorsementListNew = new ArrayList<Endorsement>();
        for (Endorsement endorsementListNewEndorsementToAttach : endorsementListNew) {
            endorsementListNewEndorsementToAttach = em.getReference(endorsementListNewEndorsementToAttach.getClass(), endorsementListNewEndorsementToAttach.getEndorsementID());
            attachedEndorsementListNew.add(endorsementListNewEndorsementToAttach);
        }
        endorsementListNew = attachedEndorsementListNew;
        person.setEndorsementList(endorsementListNew);
        List<ApplicationReviewRequest> attachedApplicationReviewRequestListNew = new ArrayList<ApplicationReviewRequest>();
        for (ApplicationReviewRequest applicationReviewRequestListNewApplicationReviewRequestToAttach : applicationReviewRequestListNew) {
            applicationReviewRequestListNewApplicationReviewRequestToAttach = em.getReference(applicationReviewRequestListNewApplicationReviewRequestToAttach.getClass(), applicationReviewRequestListNewApplicationReviewRequestToAttach.getApplicationReviewRequestPK());
            attachedApplicationReviewRequestListNew.add(applicationReviewRequestListNewApplicationReviewRequestToAttach);
        }
        applicationReviewRequestListNew = attachedApplicationReviewRequestListNew;
        person.setApplicationReviewRequestList(applicationReviewRequestListNew);
        List<RefereeReport> attachedRefereeReportListNew = new ArrayList<RefereeReport>();
        for (RefereeReport refereeReportListNewRefereeReportToAttach : refereeReportListNew) {
            refereeReportListNewRefereeReportToAttach = em.getReference(refereeReportListNewRefereeReportToAttach.getClass(), refereeReportListNewRefereeReportToAttach.getReportID());
            attachedRefereeReportListNew.add(refereeReportListNewRefereeReportToAttach);
        }
        refereeReportListNew = attachedRefereeReportListNew;
        person.setRefereeReportList(refereeReportListNew);
        List<Notification> attachedNotificationListNew = new ArrayList<Notification>();
        for (Notification notificationListNewNotificationToAttach : notificationListNew) {
            notificationListNewNotificationToAttach = em.getReference(notificationListNewNotificationToAttach.getClass(), notificationListNewNotificationToAttach.getNotificationID());
            attachedNotificationListNew.add(notificationListNewNotificationToAttach);
        }
        notificationListNew = attachedNotificationListNew;
        person.setNotificationList(notificationListNew);
        List<Notification> attachedNotificationList1New = new ArrayList<Notification>();
        for (Notification notificationList1NewNotificationToAttach : notificationList1New) {
            notificationList1NewNotificationToAttach = em.getReference(notificationList1NewNotificationToAttach.getClass(), notificationList1NewNotificationToAttach.getNotificationID());
            attachedNotificationList1New.add(notificationList1NewNotificationToAttach);
        }
        notificationList1New = attachedNotificationList1New;
        person.setNotificationList1(notificationList1New);
        List<AmmendRequest> attachedAmmendRequestListNew = new ArrayList<AmmendRequest>();
        for (AmmendRequest ammendRequestListNewAmmendRequestToAttach : ammendRequestListNew) {
            ammendRequestListNewAmmendRequestToAttach = em.getReference(ammendRequestListNewAmmendRequestToAttach.getClass(), ammendRequestListNewAmmendRequestToAttach.getRequestID());
            attachedAmmendRequestListNew.add(ammendRequestListNewAmmendRequestToAttach);
        }
        ammendRequestListNew = attachedAmmendRequestListNew;
        person.setAmmendRequestList(ammendRequestListNew);
        List<CommitteeMeeting> attachedCommitteeMeetingList1New = new ArrayList<CommitteeMeeting>();
        for (CommitteeMeeting committeeMeetingList1NewCommitteeMeetingToAttach : committeeMeetingList1New) {
            committeeMeetingList1NewCommitteeMeetingToAttach = em.getReference(committeeMeetingList1NewCommitteeMeetingToAttach.getClass(), committeeMeetingList1NewCommitteeMeetingToAttach.getMeetingID());
            attachedCommitteeMeetingList1New.add(committeeMeetingList1NewCommitteeMeetingToAttach);
        }
        committeeMeetingList1New = attachedCommitteeMeetingList1New;
        person.setCommitteeMeetingList1(committeeMeetingList1New);
        List<AuditLog> attachedAuditLogListNew = new ArrayList<AuditLog>();
        for (AuditLog auditLogListNewAuditLogToAttach : auditLogListNew) {
            auditLogListNewAuditLogToAttach = em.getReference(auditLogListNewAuditLogToAttach.getClass(), auditLogListNewAuditLogToAttach.getEntryID());
            attachedAuditLogListNew.add(auditLogListNewAuditLogToAttach);
        }
        auditLogListNew = attachedAuditLogListNew;
        person.setAuditLogList(auditLogListNew);
        List<RecommendationReport> attachedRecommendationReportListNew = new ArrayList<RecommendationReport>();
        for (RecommendationReport recommendationReportListNewRecommendationReportToAttach : recommendationReportListNew) {
            recommendationReportListNewRecommendationReportToAttach = em.getReference(recommendationReportListNewRecommendationReportToAttach.getClass(), recommendationReportListNewRecommendationReportToAttach.getReportID());
            attachedRecommendationReportListNew.add(recommendationReportListNewRecommendationReportToAttach);
        }
        recommendationReportListNew = attachedRecommendationReportListNew;
        person.setRecommendationReportList(recommendationReportListNew);
        List<FundingReport> attachedFundingReportListNew = new ArrayList<FundingReport>();
        for (FundingReport fundingReportListNewFundingReportToAttach : fundingReportListNew) {
            fundingReportListNewFundingReportToAttach = em.getReference(fundingReportListNewFundingReportToAttach.getClass(), fundingReportListNewFundingReportToAttach.getReportID());
            attachedFundingReportListNew.add(fundingReportListNewFundingReportToAttach);
        }
        fundingReportListNew = attachedFundingReportListNew;
        person.setFundingReportList(fundingReportListNew);
        List<Application> attachedApplicationList1New = new ArrayList<Application>();
        for (Application applicationList1NewApplicationToAttach : applicationList1New) {
            applicationList1NewApplicationToAttach = em.getReference(applicationList1NewApplicationToAttach.getClass(), applicationList1NewApplicationToAttach.getApplicationID());
            attachedApplicationList1New.add(applicationList1NewApplicationToAttach);
        }
        applicationList1New = attachedApplicationList1New;
        person.setApplicationList1(applicationList1New);
        List<Application> attachedApplicationList2New = new ArrayList<Application>();
        for (Application applicationList2NewApplicationToAttach : applicationList2New) {
            applicationList2NewApplicationToAttach = em.getReference(applicationList2NewApplicationToAttach.getClass(), applicationList2NewApplicationToAttach.getApplicationID());
            attachedApplicationList2New.add(applicationList2NewApplicationToAttach);
        }
        applicationList2New = attachedApplicationList2New;
        person.setApplicationList2(applicationList2New);
        List<MinuteComment> attachedMinuteCommentListNew = new ArrayList<MinuteComment>();
        for (MinuteComment minuteCommentListNewMinuteCommentToAttach : minuteCommentListNew) {
            minuteCommentListNewMinuteCommentToAttach = em.getReference(minuteCommentListNewMinuteCommentToAttach.getClass(), minuteCommentListNewMinuteCommentToAttach.getCommentID());
            attachedMinuteCommentListNew.add(minuteCommentListNewMinuteCommentToAttach);
        }
        minuteCommentListNew = attachedMinuteCommentListNew;
        person.setMinuteCommentList(minuteCommentListNew);
        List<EligiblityReport> attachedEligiblityReportListNew = new ArrayList<EligiblityReport>();
        for (EligiblityReport eligiblityReportListNewEligiblityReportToAttach : eligiblityReportListNew) {
            eligiblityReportListNewEligiblityReportToAttach = em.getReference(eligiblityReportListNewEligiblityReportToAttach.getClass(), eligiblityReportListNewEligiblityReportToAttach.getReportID());
            attachedEligiblityReportListNew.add(eligiblityReportListNewEligiblityReportToAttach);
        }
        eligiblityReportListNew = attachedEligiblityReportListNew;
        person.setEligiblityReportList(eligiblityReportListNew);
        List<ForwardAndRewindReport> attachedForwardAndRewindReportListNew = new ArrayList<ForwardAndRewindReport>();
        for (ForwardAndRewindReport forwardAndRewindReportListNewForwardAndRewindReportToAttach : forwardAndRewindReportListNew) {
            forwardAndRewindReportListNewForwardAndRewindReportToAttach = em.getReference(forwardAndRewindReportListNewForwardAndRewindReportToAttach.getClass(), forwardAndRewindReportListNewForwardAndRewindReportToAttach.getReportID());
            attachedForwardAndRewindReportListNew.add(forwardAndRewindReportListNewForwardAndRewindReportToAttach);
        }
        forwardAndRewindReportListNew = attachedForwardAndRewindReportListNew;
        person.setForwardAndRewindReportList(forwardAndRewindReportListNew);
        person = em.merge(person);
        if (researchFellowInformationNew != null && !researchFellowInformationNew.equals(researchFellowInformationOld)) {
            Person oldPersonOfResearchFellowInformation = researchFellowInformationNew.getPerson();
            if (oldPersonOfResearchFellowInformation != null) {
                oldPersonOfResearchFellowInformation.setResearchFellowInformation(null);
                oldPersonOfResearchFellowInformation = em.merge(oldPersonOfResearchFellowInformation);
            }
            researchFellowInformationNew.setPerson(person);
            researchFellowInformationNew = em.merge(researchFellowInformationNew);
        }
        if (employeeInformationNew != null && !employeeInformationNew.equals(employeeInformationOld)) {
            Person oldPersonOfEmployeeInformation = employeeInformationNew.getPerson();
            if (oldPersonOfEmployeeInformation != null) {
                oldPersonOfEmployeeInformation.setEmployeeInformation(null);
                oldPersonOfEmployeeInformation = em.merge(oldPersonOfEmployeeInformation);
            }
            employeeInformationNew.setPerson(person);
            employeeInformationNew = em.merge(employeeInformationNew);
        }
        if (cvNew != null && !cvNew.equals(cvOld)) {
            Person oldPersonOfCv = cvNew.getPerson();
            if (oldPersonOfCv != null) {
                oldPersonOfCv.setCv(null);
                oldPersonOfCv = em.merge(oldPersonOfCv);
            }
            cvNew.setPerson(person);
            cvNew = em.merge(cvNew);
        }
        if (addressLine1Old != null && !addressLine1Old.equals(addressLine1New)) {
            addressLine1Old.getPersonList().remove(person);
            addressLine1Old = em.merge(addressLine1Old);
        }
        if (addressLine1New != null && !addressLine1New.equals(addressLine1Old)) {
            addressLine1New.getPersonList().add(person);
            addressLine1New = em.merge(addressLine1New);
        }
        for (SecurityRole securityRoleListOldSecurityRole : securityRoleListOld) {
            if (!securityRoleListNew.contains(securityRoleListOldSecurityRole)) {
                securityRoleListOldSecurityRole.getPersonList().remove(person);
                securityRoleListOldSecurityRole = em.merge(securityRoleListOldSecurityRole);
            }
        }
        for (SecurityRole securityRoleListNewSecurityRole : securityRoleListNew) {
            if (!securityRoleListOld.contains(securityRoleListNewSecurityRole)) {
                securityRoleListNewSecurityRole.getPersonList().add(person);
                securityRoleListNewSecurityRole = em.merge(securityRoleListNewSecurityRole);
            }
        }
        for (Application applicationListOldApplication : applicationListOld) {
            if (!applicationListNew.contains(applicationListOldApplication)) {
                applicationListOldApplication.getPersonList().remove(person);
                applicationListOldApplication = em.merge(applicationListOldApplication);
            }
        }
        for (Application applicationListNewApplication : applicationListNew) {
            if (!applicationListOld.contains(applicationListNewApplication)) {
                applicationListNewApplication.getPersonList().add(person);
                applicationListNewApplication = em.merge(applicationListNewApplication);
            }
        }
        for (CommitteeMeeting committeeMeetingListOldCommitteeMeeting : committeeMeetingListOld) {
            if (!committeeMeetingListNew.contains(committeeMeetingListOldCommitteeMeeting)) {
                committeeMeetingListOldCommitteeMeeting.getPersonList().remove(person);
                committeeMeetingListOldCommitteeMeeting = em.merge(committeeMeetingListOldCommitteeMeeting);
            }
        }
        for (CommitteeMeeting committeeMeetingListNewCommitteeMeeting : committeeMeetingListNew) {
            if (!committeeMeetingListOld.contains(committeeMeetingListNewCommitteeMeeting)) {
                committeeMeetingListNewCommitteeMeeting.getPersonList().add(person);
                committeeMeetingListNewCommitteeMeeting = em.merge(committeeMeetingListNewCommitteeMeeting);
            }
        }
        for (DeclineReport declineReportListNewDeclineReport : declineReportListNew) {
            if (!declineReportListOld.contains(declineReportListNewDeclineReport)) {
                Person oldCreatorOfDeclineReportListNewDeclineReport = declineReportListNewDeclineReport.getCreator();
                declineReportListNewDeclineReport.setCreator(person);
                declineReportListNewDeclineReport = em.merge(declineReportListNewDeclineReport);
                if (oldCreatorOfDeclineReportListNewDeclineReport != null && !oldCreatorOfDeclineReportListNewDeclineReport.equals(person)) {
                    oldCreatorOfDeclineReportListNewDeclineReport.getDeclineReportList().remove(declineReportListNewDeclineReport);
                    oldCreatorOfDeclineReportListNewDeclineReport = em.merge(oldCreatorOfDeclineReportListNewDeclineReport);
                }
            }
        }
        for (Endorsement endorsementListNewEndorsement : endorsementListNew) {
            if (!endorsementListOld.contains(endorsementListNewEndorsement)) {
                Person oldDeanOfEndorsementListNewEndorsement = endorsementListNewEndorsement.getDean();
                endorsementListNewEndorsement.setDean(person);
                endorsementListNewEndorsement = em.merge(endorsementListNewEndorsement);
                if (oldDeanOfEndorsementListNewEndorsement != null && !oldDeanOfEndorsementListNewEndorsement.equals(person)) {
                    oldDeanOfEndorsementListNewEndorsement.getEndorsementList().remove(endorsementListNewEndorsement);
                    oldDeanOfEndorsementListNewEndorsement = em.merge(oldDeanOfEndorsementListNewEndorsement);
                }
            }
        }
        for (ApplicationReviewRequest applicationReviewRequestListNewApplicationReviewRequest : applicationReviewRequestListNew) {
            if (!applicationReviewRequestListOld.contains(applicationReviewRequestListNewApplicationReviewRequest)) {
                Person oldPerson1OfApplicationReviewRequestListNewApplicationReviewRequest = applicationReviewRequestListNewApplicationReviewRequest.getPerson1();
                applicationReviewRequestListNewApplicationReviewRequest.setPerson1(person);
                applicationReviewRequestListNewApplicationReviewRequest = em.merge(applicationReviewRequestListNewApplicationReviewRequest);
                if (oldPerson1OfApplicationReviewRequestListNewApplicationReviewRequest != null && !oldPerson1OfApplicationReviewRequestListNewApplicationReviewRequest.equals(person)) {
                    oldPerson1OfApplicationReviewRequestListNewApplicationReviewRequest.getApplicationReviewRequestList().remove(applicationReviewRequestListNewApplicationReviewRequest);
                    oldPerson1OfApplicationReviewRequestListNewApplicationReviewRequest = em.merge(oldPerson1OfApplicationReviewRequestListNewApplicationReviewRequest);
                }
            }
        }
        for (RefereeReport refereeReportListNewRefereeReport : refereeReportListNew) {
            if (!refereeReportListOld.contains(refereeReportListNewRefereeReport)) {
                Person oldRefereeOfRefereeReportListNewRefereeReport = refereeReportListNewRefereeReport.getReferee();
                refereeReportListNewRefereeReport.setReferee(person);
                refereeReportListNewRefereeReport = em.merge(refereeReportListNewRefereeReport);
                if (oldRefereeOfRefereeReportListNewRefereeReport != null && !oldRefereeOfRefereeReportListNewRefereeReport.equals(person)) {
                    oldRefereeOfRefereeReportListNewRefereeReport.getRefereeReportList().remove(refereeReportListNewRefereeReport);
                    oldRefereeOfRefereeReportListNewRefereeReport = em.merge(oldRefereeOfRefereeReportListNewRefereeReport);
                }
            }
        }
        for (Notification notificationListNewNotification : notificationListNew) {
            if (!notificationListOld.contains(notificationListNewNotification)) {
                Person oldSenderOfNotificationListNewNotification = notificationListNewNotification.getSender();
                notificationListNewNotification.setSender(person);
                notificationListNewNotification = em.merge(notificationListNewNotification);
                if (oldSenderOfNotificationListNewNotification != null && !oldSenderOfNotificationListNewNotification.equals(person)) {
                    oldSenderOfNotificationListNewNotification.getNotificationList().remove(notificationListNewNotification);
                    oldSenderOfNotificationListNewNotification = em.merge(oldSenderOfNotificationListNewNotification);
                }
            }
        }
        for (Notification notificationList1NewNotification : notificationList1New) {
            if (!notificationList1Old.contains(notificationList1NewNotification)) {
                Person oldRecieverOfNotificationList1NewNotification = notificationList1NewNotification.getReciever();
                notificationList1NewNotification.setReciever(person);
                notificationList1NewNotification = em.merge(notificationList1NewNotification);
                if (oldRecieverOfNotificationList1NewNotification != null && !oldRecieverOfNotificationList1NewNotification.equals(person)) {
                    oldRecieverOfNotificationList1NewNotification.getNotificationList1().remove(notificationList1NewNotification);
                    oldRecieverOfNotificationList1NewNotification = em.merge(oldRecieverOfNotificationList1NewNotification);
                }
            }
        }
        for (AmmendRequest ammendRequestListNewAmmendRequest : ammendRequestListNew) {
            if (!ammendRequestListOld.contains(ammendRequestListNewAmmendRequest)) {
                Person oldCreatorOfAmmendRequestListNewAmmendRequest = ammendRequestListNewAmmendRequest.getCreator();
                ammendRequestListNewAmmendRequest.setCreator(person);
                ammendRequestListNewAmmendRequest = em.merge(ammendRequestListNewAmmendRequest);
                if (oldCreatorOfAmmendRequestListNewAmmendRequest != null && !oldCreatorOfAmmendRequestListNewAmmendRequest.equals(person)) {
                    oldCreatorOfAmmendRequestListNewAmmendRequest.getAmmendRequestList().remove(ammendRequestListNewAmmendRequest);
                    oldCreatorOfAmmendRequestListNewAmmendRequest = em.merge(oldCreatorOfAmmendRequestListNewAmmendRequest);
                }
            }
        }
        for (CommitteeMeeting committeeMeetingList1NewCommitteeMeeting : committeeMeetingList1New) {
            if (!committeeMeetingList1Old.contains(committeeMeetingList1NewCommitteeMeeting)) {
                Person oldOrganiserOfCommitteeMeetingList1NewCommitteeMeeting = committeeMeetingList1NewCommitteeMeeting.getOrganiser();
                committeeMeetingList1NewCommitteeMeeting.setOrganiser(person);
                committeeMeetingList1NewCommitteeMeeting = em.merge(committeeMeetingList1NewCommitteeMeeting);
                if (oldOrganiserOfCommitteeMeetingList1NewCommitteeMeeting != null && !oldOrganiserOfCommitteeMeetingList1NewCommitteeMeeting.equals(person)) {
                    oldOrganiserOfCommitteeMeetingList1NewCommitteeMeeting.getCommitteeMeetingList1().remove(committeeMeetingList1NewCommitteeMeeting);
                    oldOrganiserOfCommitteeMeetingList1NewCommitteeMeeting = em.merge(oldOrganiserOfCommitteeMeetingList1NewCommitteeMeeting);
                }
            }
        }
        for (AuditLog auditLogListNewAuditLog : auditLogListNew) {
            if (!auditLogListOld.contains(auditLogListNewAuditLog)) {
                Person oldPersonOfAuditLogListNewAuditLog = auditLogListNewAuditLog.getPerson();
                auditLogListNewAuditLog.setPerson(person);
                auditLogListNewAuditLog = em.merge(auditLogListNewAuditLog);
                if (oldPersonOfAuditLogListNewAuditLog != null && !oldPersonOfAuditLogListNewAuditLog.equals(person)) {
                    oldPersonOfAuditLogListNewAuditLog.getAuditLogList().remove(auditLogListNewAuditLog);
                    oldPersonOfAuditLogListNewAuditLog = em.merge(oldPersonOfAuditLogListNewAuditLog);
                }
            }
        }
        for (RecommendationReport recommendationReportListNewRecommendationReport : recommendationReportListNew) {
            if (!recommendationReportListOld.contains(recommendationReportListNewRecommendationReport)) {
                Person oldHodOfRecommendationReportListNewRecommendationReport = recommendationReportListNewRecommendationReport.getHod();
                recommendationReportListNewRecommendationReport.setHod(person);
                recommendationReportListNewRecommendationReport = em.merge(recommendationReportListNewRecommendationReport);
                if (oldHodOfRecommendationReportListNewRecommendationReport != null && !oldHodOfRecommendationReportListNewRecommendationReport.equals(person)) {
                    oldHodOfRecommendationReportListNewRecommendationReport.getRecommendationReportList().remove(recommendationReportListNewRecommendationReport);
                    oldHodOfRecommendationReportListNewRecommendationReport = em.merge(oldHodOfRecommendationReportListNewRecommendationReport);
                }
            }
        }
        for (FundingReport fundingReportListNewFundingReport : fundingReportListNew) {
            if (!fundingReportListOld.contains(fundingReportListNewFundingReport)) {
                Person oldDrisOfFundingReportListNewFundingReport = fundingReportListNewFundingReport.getDris();
                fundingReportListNewFundingReport.setDris(person);
                fundingReportListNewFundingReport = em.merge(fundingReportListNewFundingReport);
                if (oldDrisOfFundingReportListNewFundingReport != null && !oldDrisOfFundingReportListNewFundingReport.equals(person)) {
                    oldDrisOfFundingReportListNewFundingReport.getFundingReportList().remove(fundingReportListNewFundingReport);
                    oldDrisOfFundingReportListNewFundingReport = em.merge(oldDrisOfFundingReportListNewFundingReport);
                }
            }
        }
        for (Application applicationList1NewApplication : applicationList1New) {
            if (!applicationList1Old.contains(applicationList1NewApplication)) {
                Person oldFellowOfApplicationList1NewApplication = applicationList1NewApplication.getFellow();
                applicationList1NewApplication.setFellow(person);
                applicationList1NewApplication = em.merge(applicationList1NewApplication);
                if (oldFellowOfApplicationList1NewApplication != null && !oldFellowOfApplicationList1NewApplication.equals(person)) {
                    oldFellowOfApplicationList1NewApplication.getApplicationList1().remove(applicationList1NewApplication);
                    oldFellowOfApplicationList1NewApplication = em.merge(oldFellowOfApplicationList1NewApplication);
                }
            }
        }
        for (Application applicationList2OldApplication : applicationList2Old) {
            if (!applicationList2New.contains(applicationList2OldApplication)) {
                applicationList2OldApplication.setGrantHolder(null);
                applicationList2OldApplication = em.merge(applicationList2OldApplication);
            }
        }
        for (Application applicationList2NewApplication : applicationList2New) {
            if (!applicationList2Old.contains(applicationList2NewApplication)) {
                Person oldGrantHolderOfApplicationList2NewApplication = applicationList2NewApplication.getGrantHolder();
                applicationList2NewApplication.setGrantHolder(person);
                applicationList2NewApplication = em.merge(applicationList2NewApplication);
                if (oldGrantHolderOfApplicationList2NewApplication != null && !oldGrantHolderOfApplicationList2NewApplication.equals(person)) {
                    oldGrantHolderOfApplicationList2NewApplication.getApplicationList2().remove(applicationList2NewApplication);
                    oldGrantHolderOfApplicationList2NewApplication = em.merge(oldGrantHolderOfApplicationList2NewApplication);
                }
            }
        }
        for (MinuteComment minuteCommentListNewMinuteComment : minuteCommentListNew) {
            if (!minuteCommentListOld.contains(minuteCommentListNewMinuteComment)) {
                Person oldAttendeeOfMinuteCommentListNewMinuteComment = minuteCommentListNewMinuteComment.getAttendee();
                minuteCommentListNewMinuteComment.setAttendee(person);
                minuteCommentListNewMinuteComment = em.merge(minuteCommentListNewMinuteComment);
                if (oldAttendeeOfMinuteCommentListNewMinuteComment != null && !oldAttendeeOfMinuteCommentListNewMinuteComment.equals(person)) {
                    oldAttendeeOfMinuteCommentListNewMinuteComment.getMinuteCommentList().remove(minuteCommentListNewMinuteComment);
                    oldAttendeeOfMinuteCommentListNewMinuteComment = em.merge(oldAttendeeOfMinuteCommentListNewMinuteComment);
                }
            }
        }
        for (EligiblityReport eligiblityReportListNewEligiblityReport : eligiblityReportListNew) {
            if (!eligiblityReportListOld.contains(eligiblityReportListNewEligiblityReport)) {
                Person oldEligiblityCheckerOfEligiblityReportListNewEligiblityReport = eligiblityReportListNewEligiblityReport.getEligiblityChecker();
                eligiblityReportListNewEligiblityReport.setEligiblityChecker(person);
                eligiblityReportListNewEligiblityReport = em.merge(eligiblityReportListNewEligiblityReport);
                if (oldEligiblityCheckerOfEligiblityReportListNewEligiblityReport != null && !oldEligiblityCheckerOfEligiblityReportListNewEligiblityReport.equals(person)) {
                    oldEligiblityCheckerOfEligiblityReportListNewEligiblityReport.getEligiblityReportList().remove(eligiblityReportListNewEligiblityReport);
                    oldEligiblityCheckerOfEligiblityReportListNewEligiblityReport = em.merge(oldEligiblityCheckerOfEligiblityReportListNewEligiblityReport);
                }
            }
        }
        for (ForwardAndRewindReport forwardAndRewindReportListNewForwardAndRewindReport : forwardAndRewindReportListNew) {
            if (!forwardAndRewindReportListOld.contains(forwardAndRewindReportListNewForwardAndRewindReport)) {
                Person oldDrisOfForwardAndRewindReportListNewForwardAndRewindReport = forwardAndRewindReportListNewForwardAndRewindReport.getDris();
                forwardAndRewindReportListNewForwardAndRewindReport.setDris(person);
                forwardAndRewindReportListNewForwardAndRewindReport = em.merge(forwardAndRewindReportListNewForwardAndRewindReport);
                if (oldDrisOfForwardAndRewindReportListNewForwardAndRewindReport != null && !oldDrisOfForwardAndRewindReportListNewForwardAndRewindReport.equals(person)) {
                    oldDrisOfForwardAndRewindReportListNewForwardAndRewindReport.getForwardAndRewindReportList().remove(forwardAndRewindReportListNewForwardAndRewindReport);
                    oldDrisOfForwardAndRewindReportListNewForwardAndRewindReport = em.merge(oldDrisOfForwardAndRewindReportListNewForwardAndRewindReport);
                }
            }
        }
            
                

    }

    public void destroy(EntityManager em, String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {

        Person person;
        try {
            person = em.getReference(Person.class, id);
            person.getSystemID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The person with id " + id + " no longer exists.", enfe);
        }
        List<String> illegalOrphanMessages = null;
        ResearchFellowInformation researchFellowInformationOrphanCheck = person.getResearchFellowInformation();
        if (researchFellowInformationOrphanCheck != null) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the ResearchFellowInformation " + researchFellowInformationOrphanCheck + " in its researchFellowInformation field has a non-nullable person field.");
        }
        EmployeeInformation employeeInformationOrphanCheck = person.getEmployeeInformation();
        if (employeeInformationOrphanCheck != null) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the EmployeeInformation " + employeeInformationOrphanCheck + " in its employeeInformation field has a non-nullable person field.");
        }
        Cv cvOrphanCheck = person.getCv();
        if (cvOrphanCheck != null) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Cv " + cvOrphanCheck + " in its cv field has a non-nullable person field.");
        }
        List<DeclineReport> declineReportListOrphanCheck = person.getDeclineReportList();
        for (DeclineReport declineReportListOrphanCheckDeclineReport : declineReportListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the DeclineReport " + declineReportListOrphanCheckDeclineReport + " in its declineReportList field has a non-nullable creator field.");
        }
        List<Endorsement> endorsementListOrphanCheck = person.getEndorsementList();
        for (Endorsement endorsementListOrphanCheckEndorsement : endorsementListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Endorsement " + endorsementListOrphanCheckEndorsement + " in its endorsementList field has a non-nullable dean field.");
        }
        List<ApplicationReviewRequest> applicationReviewRequestListOrphanCheck = person.getApplicationReviewRequestList();
        for (ApplicationReviewRequest applicationReviewRequestListOrphanCheckApplicationReviewRequest : applicationReviewRequestListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the ApplicationReviewRequest " + applicationReviewRequestListOrphanCheckApplicationReviewRequest + " in its applicationReviewRequestList field has a non-nullable person1 field.");
        }
        List<RefereeReport> refereeReportListOrphanCheck = person.getRefereeReportList();
        for (RefereeReport refereeReportListOrphanCheckRefereeReport : refereeReportListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the RefereeReport " + refereeReportListOrphanCheckRefereeReport + " in its refereeReportList field has a non-nullable referee field.");
        }
        List<Notification> notificationListOrphanCheck = person.getNotificationList();
        for (Notification notificationListOrphanCheckNotification : notificationListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Notification " + notificationListOrphanCheckNotification + " in its notificationList field has a non-nullable sender field.");
        }
        List<Notification> notificationList1OrphanCheck = person.getNotificationList1();
        for (Notification notificationList1OrphanCheckNotification : notificationList1OrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Notification " + notificationList1OrphanCheckNotification + " in its notificationList1 field has a non-nullable reciever field.");
        }
        List<AmmendRequest> ammendRequestListOrphanCheck = person.getAmmendRequestList();
        for (AmmendRequest ammendRequestListOrphanCheckAmmendRequest : ammendRequestListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the AmmendRequest " + ammendRequestListOrphanCheckAmmendRequest + " in its ammendRequestList field has a non-nullable creator field.");
        }
        List<CommitteeMeeting> committeeMeetingList1OrphanCheck = person.getCommitteeMeetingList1();
        for (CommitteeMeeting committeeMeetingList1OrphanCheckCommitteeMeeting : committeeMeetingList1OrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the CommitteeMeeting " + committeeMeetingList1OrphanCheckCommitteeMeeting + " in its committeeMeetingList1 field has a non-nullable organiser field.");
        }
        List<AuditLog> auditLogListOrphanCheck = person.getAuditLogList();
        for (AuditLog auditLogListOrphanCheckAuditLog : auditLogListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the AuditLog " + auditLogListOrphanCheckAuditLog + " in its auditLogList field has a non-nullable person field.");
        }
        List<RecommendationReport> recommendationReportListOrphanCheck = person.getRecommendationReportList();
        for (RecommendationReport recommendationReportListOrphanCheckRecommendationReport : recommendationReportListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the RecommendationReport " + recommendationReportListOrphanCheckRecommendationReport + " in its recommendationReportList field has a non-nullable hod field.");
        }
        List<FundingReport> fundingReportListOrphanCheck = person.getFundingReportList();
        for (FundingReport fundingReportListOrphanCheckFundingReport : fundingReportListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the FundingReport " + fundingReportListOrphanCheckFundingReport + " in its fundingReportList field has a non-nullable dris field.");
        }
        List<Application> applicationList1OrphanCheck = person.getApplicationList1();
        for (Application applicationList1OrphanCheckApplication : applicationList1OrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Application " + applicationList1OrphanCheckApplication + " in its applicationList1 field has a non-nullable fellow field.");
        }
        List<MinuteComment> minuteCommentListOrphanCheck = person.getMinuteCommentList();
        for (MinuteComment minuteCommentListOrphanCheckMinuteComment : minuteCommentListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the MinuteComment " + minuteCommentListOrphanCheckMinuteComment + " in its minuteCommentList field has a non-nullable attendee field.");
        }
        List<EligiblityReport> eligiblityReportListOrphanCheck = person.getEligiblityReportList();
        for (EligiblityReport eligiblityReportListOrphanCheckEligiblityReport : eligiblityReportListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the EligiblityReport " + eligiblityReportListOrphanCheckEligiblityReport + " in its eligiblityReportList field has a non-nullable eligiblityChecker field.");
        }
        List<ForwardAndRewindReport> forwardAndRewindReportListOrphanCheck = person.getForwardAndRewindReportList();
        for (ForwardAndRewindReport forwardAndRewindReportListOrphanCheckForwardAndRewindReport : forwardAndRewindReportListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the ForwardAndRewindReport " + forwardAndRewindReportListOrphanCheckForwardAndRewindReport + " in its forwardAndRewindReportList field has a non-nullable dris field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        Address addressLine1 = person.getAddressLine1();
        if (addressLine1 != null) {
            addressLine1.getPersonList().remove(person);
            addressLine1 = em.merge(addressLine1);
        }
        List<SecurityRole> securityRoleList = person.getSecurityRoleList();
        for (SecurityRole securityRoleListSecurityRole : securityRoleList) {
            securityRoleListSecurityRole.getPersonList().remove(person);
            securityRoleListSecurityRole = em.merge(securityRoleListSecurityRole);
        }
        List<Application> applicationList = person.getApplicationList();
        for (Application applicationListApplication : applicationList) {
            applicationListApplication.getPersonList().remove(person);
            applicationListApplication = em.merge(applicationListApplication);
        }
        List<CommitteeMeeting> committeeMeetingList = person.getCommitteeMeetingList();
        for (CommitteeMeeting committeeMeetingListCommitteeMeeting : committeeMeetingList) {
            committeeMeetingListCommitteeMeeting.getPersonList().remove(person);
            committeeMeetingListCommitteeMeeting = em.merge(committeeMeetingListCommitteeMeeting);
        }
        List<Application> applicationList2 = person.getApplicationList2();
        for (Application applicationList2Application : applicationList2) {
            applicationList2Application.setGrantHolder(null);
            applicationList2Application = em.merge(applicationList2Application);
        }
        em.remove(person);
            
    }

    public List<Person> findPersonEntities() {
        return findPersonEntities(true, -1, -1);
    }

    public List<Person> findPersonEntities(int maxResults, int firstResult) {
        return findPersonEntities(false, maxResults, firstResult);
    }

    private List<Person> findPersonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Person.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Person findPerson(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Person> rt = cq.from(Person.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     *This function searchs the person table in the database inorder to find the
     * maximum value of the systemIDs with the particular prefix and year 
     * combination
     * @param year The year of systemIDs to browse as an integer e.g "2004"
     * @param prefix The character prefix of the systemIDs to search. 
     * note this parameter is case-sensitive e.g. "u"
     * @return The last 5 characters as an int of the most recently added systemID 
     * @throws NumberFormatException This is thrown when the last 5 characters 
     * of the ID retrieved from the database cannot be converted
     */
    public int getMaxSystemIDForYear(int year, char prefix) throws NumberFormatException
    {        
        String tempYear = Integer.toString(year);
        
        //Extract last two digits of year code
        if(tempYear.length() > 1)
        {
            tempYear = String.valueOf(tempYear.charAt(tempYear.length() - 2)) + String.valueOf(tempYear.charAt(tempYear.length() - 1));
        }
        else
        {
            tempYear = "0" + tempYear;
        }
        
        tempYear = String.valueOf(prefix) + tempYear;
        
        EntityManager em = getEntityManager();
        
        String lastID = "";
        //Finds maximum systemID for year and prefix combination
        try
        {                 
            lastID = em.createQuery("SELECT p.systemID FROM Person p WHERE p.systemID LIKE :year ORDER BY p.systemID DESC",String.class).setParameter("year", tempYear + "%").setMaxResults(1).getSingleResult();
        }
        catch(NoResultException exception)
        {
            return 0;
        }
        
        //Extracts count
        lastID = lastID.substring(3);
        
        try
        {
            return Integer.parseInt(lastID);
        }
        catch(NumberFormatException ex)
        {
            throw ex;
        }     
        
    }
    
    /**
     *This function can be used to check if a particular person has the 
     * specified security role.
     * @param person The person object to be checked
     * @param roleID The roleID of the role to look for as a long
     * @return True if the person does have the security role else false
     */
    public boolean doesPersonHaveSecurityRole(Person person, long roleID)
    {
        boolean isFound = false;
        System.out.println(person.getSecurityRoleList().toString());
        for(SecurityRole sr : person.getSecurityRoleList())
        {
            System.out.println(sr.toString());
            if(sr.getRoleID() == roleID)
            {
                isFound = true;
                break;
            }
        }
        
        return isFound;        
    }
    
    public Person findUserBySystemIDOrEmail(String input)
    {
        EntityManager em = getEntityManager();
        System.out.println("Out " + input);
        List<Person> persons = em.createQuery("SELECT p FROM Person p WHERE (p.systemID = :in) OR (p.email = :in)", Person.class).setParameter("in", input).getResultList();
        if(persons.size() > 0)
        {
            return persons.get(0);
        }
        else
        {
            persons = em.createQuery("SELECT r.person FROM ResearchFellowInformation r WHERE  r.institutionAssignedEmail = :in OR r.institutionAssignedID = :in", Person.class).setParameter("in", input).getResultList();
            if(persons.size() > 0)
            {
                return persons.get(0);
            }
            else
            {
                return null;
            }
        }
    }
    
    public List<Person> findUserBySecurityRoleWithAccountStatus(SecurityRole securityRole, String accountStatus)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p WHERE p.accountStatus = :status AND :secRole MEMBER OF p.securityRoleList", Person.class).setParameter("status", accountStatus).setParameter("secRole", securityRole);
        
        return q.getResultList();
    }
    
    public List<Person> findAllUsersWhichHaveAccountStatus(String accountStatus)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p WHERE p.accountStatus = :status", Person.class).setParameter("status", accountStatus);
        
        return q.getResultList();
    }
    
    
}
