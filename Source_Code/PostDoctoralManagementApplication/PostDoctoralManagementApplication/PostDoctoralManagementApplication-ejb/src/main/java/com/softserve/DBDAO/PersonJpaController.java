/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
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
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.DBEntities.Location;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.CommitteeMeeting;
import java.util.ArrayList;
import java.util.List;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Endorsement;
import com.softserve.DBEntities.RecommendationReport;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.MinuteComment;
import com.softserve.DBEntities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class PersonJpaController implements Serializable {

    public PersonJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Person person) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (person.getCommitteeMeetingList() == null) {
            person.setCommitteeMeetingList(new ArrayList<CommitteeMeeting>());
        }
        if (person.getSecurityRoleList() == null) {
            person.setSecurityRoleList(new ArrayList<SecurityRole>());
        }
        if (person.getAuditLogList() == null) {
            person.setAuditLogList(new ArrayList<AuditLog>());
        }
        if (person.getEndorsementList() == null) {
            person.setEndorsementList(new ArrayList<Endorsement>());
        }
        if (person.getRecommendationReportList() == null) {
            person.setRecommendationReportList(new ArrayList<RecommendationReport>());
        }
        if (person.getRefereeReportList() == null) {
            person.setRefereeReportList(new ArrayList<RefereeReport>());
        }
        if (person.getFundingReportList() == null) {
            person.setFundingReportList(new ArrayList<FundingReport>());
        }
        if (person.getNotificationList() == null) {
            person.setNotificationList(new ArrayList<Notification>());
        }
        if (person.getNotificationList1() == null) {
            person.setNotificationList1(new ArrayList<Notification>());
        }
        if (person.getCvList() == null) {
            person.setCvList(new ArrayList<Cv>());
        }
        if (person.getApplicationList() == null) {
            person.setApplicationList(new ArrayList<Application>());
        }
        if (person.getApplicationList1() == null) {
            person.setApplicationList1(new ArrayList<Application>());
        }
        if (person.getMinuteCommentList() == null) {
            person.setMinuteCommentList(new ArrayList<MinuteComment>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UpEmployeeInformation upEmployeeInformation = person.getUpEmployeeInformation();
            if (upEmployeeInformation != null) {
                upEmployeeInformation = em.getReference(upEmployeeInformation.getClass(), upEmployeeInformation.getEmployeeID());
                person.setUpEmployeeInformation(upEmployeeInformation);
            }
            Location locationID = person.getLocationID();
            if (locationID != null) {
                locationID = em.getReference(locationID.getClass(), locationID.getLocationID());
                person.setLocationID(locationID);
            }
            Address addressLine1 = person.getAddressLine1();
            if (addressLine1 != null) {
                addressLine1 = em.getReference(addressLine1.getClass(), addressLine1.getAddressID());
                person.setAddressLine1(addressLine1);
            }
            List<CommitteeMeeting> attachedCommitteeMeetingList = new ArrayList<CommitteeMeeting>();
            for (CommitteeMeeting committeeMeetingListCommitteeMeetingToAttach : person.getCommitteeMeetingList()) {
                committeeMeetingListCommitteeMeetingToAttach = em.getReference(committeeMeetingListCommitteeMeetingToAttach.getClass(), committeeMeetingListCommitteeMeetingToAttach.getMeetingID());
                attachedCommitteeMeetingList.add(committeeMeetingListCommitteeMeetingToAttach);
            }
            person.setCommitteeMeetingList(attachedCommitteeMeetingList);
            List<SecurityRole> attachedSecurityRoleList = new ArrayList<SecurityRole>();
            for (SecurityRole securityRoleListSecurityRoleToAttach : person.getSecurityRoleList()) {
                securityRoleListSecurityRoleToAttach = em.getReference(securityRoleListSecurityRoleToAttach.getClass(), securityRoleListSecurityRoleToAttach.getRoleID());
                attachedSecurityRoleList.add(securityRoleListSecurityRoleToAttach);
            }
            person.setSecurityRoleList(attachedSecurityRoleList);
            List<AuditLog> attachedAuditLogList = new ArrayList<AuditLog>();
            for (AuditLog auditLogListAuditLogToAttach : person.getAuditLogList()) {
                auditLogListAuditLogToAttach = em.getReference(auditLogListAuditLogToAttach.getClass(), auditLogListAuditLogToAttach.getEntryID());
                attachedAuditLogList.add(auditLogListAuditLogToAttach);
            }
            person.setAuditLogList(attachedAuditLogList);
            List<Endorsement> attachedEndorsementList = new ArrayList<Endorsement>();
            for (Endorsement endorsementListEndorsementToAttach : person.getEndorsementList()) {
                endorsementListEndorsementToAttach = em.getReference(endorsementListEndorsementToAttach.getClass(), endorsementListEndorsementToAttach.getEndorsementID());
                attachedEndorsementList.add(endorsementListEndorsementToAttach);
            }
            person.setEndorsementList(attachedEndorsementList);
            List<RecommendationReport> attachedRecommendationReportList = new ArrayList<RecommendationReport>();
            for (RecommendationReport recommendationReportListRecommendationReportToAttach : person.getRecommendationReportList()) {
                recommendationReportListRecommendationReportToAttach = em.getReference(recommendationReportListRecommendationReportToAttach.getClass(), recommendationReportListRecommendationReportToAttach.getReportID());
                attachedRecommendationReportList.add(recommendationReportListRecommendationReportToAttach);
            }
            person.setRecommendationReportList(attachedRecommendationReportList);
            List<RefereeReport> attachedRefereeReportList = new ArrayList<RefereeReport>();
            for (RefereeReport refereeReportListRefereeReportToAttach : person.getRefereeReportList()) {
                refereeReportListRefereeReportToAttach = em.getReference(refereeReportListRefereeReportToAttach.getClass(), refereeReportListRefereeReportToAttach.getReportID());
                attachedRefereeReportList.add(refereeReportListRefereeReportToAttach);
            }
            person.setRefereeReportList(attachedRefereeReportList);
            List<FundingReport> attachedFundingReportList = new ArrayList<FundingReport>();
            for (FundingReport fundingReportListFundingReportToAttach : person.getFundingReportList()) {
                fundingReportListFundingReportToAttach = em.getReference(fundingReportListFundingReportToAttach.getClass(), fundingReportListFundingReportToAttach.getReportID());
                attachedFundingReportList.add(fundingReportListFundingReportToAttach);
            }
            person.setFundingReportList(attachedFundingReportList);
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
            List<Cv> attachedCvList = new ArrayList<Cv>();
            for (Cv cvListCvToAttach : person.getCvList()) {
                cvListCvToAttach = em.getReference(cvListCvToAttach.getClass(), cvListCvToAttach.getCvID());
                attachedCvList.add(cvListCvToAttach);
            }
            person.setCvList(attachedCvList);
            List<Application> attachedApplicationList = new ArrayList<Application>();
            for (Application applicationListApplicationToAttach : person.getApplicationList()) {
                applicationListApplicationToAttach = em.getReference(applicationListApplicationToAttach.getClass(), applicationListApplicationToAttach.getApplicationID());
                attachedApplicationList.add(applicationListApplicationToAttach);
            }
            person.setApplicationList(attachedApplicationList);
            List<Application> attachedApplicationList1 = new ArrayList<Application>();
            for (Application applicationList1ApplicationToAttach : person.getApplicationList1()) {
                applicationList1ApplicationToAttach = em.getReference(applicationList1ApplicationToAttach.getClass(), applicationList1ApplicationToAttach.getApplicationID());
                attachedApplicationList1.add(applicationList1ApplicationToAttach);
            }
            person.setApplicationList1(attachedApplicationList1);
            List<MinuteComment> attachedMinuteCommentList = new ArrayList<MinuteComment>();
            for (MinuteComment minuteCommentListMinuteCommentToAttach : person.getMinuteCommentList()) {
                minuteCommentListMinuteCommentToAttach = em.getReference(minuteCommentListMinuteCommentToAttach.getClass(), minuteCommentListMinuteCommentToAttach.getCommentID());
                attachedMinuteCommentList.add(minuteCommentListMinuteCommentToAttach);
            }
            person.setMinuteCommentList(attachedMinuteCommentList);
            em.persist(person);
            if (upEmployeeInformation != null) {
                Person oldPersonOfUpEmployeeInformation = upEmployeeInformation.getPerson();
                if (oldPersonOfUpEmployeeInformation != null) {
                    oldPersonOfUpEmployeeInformation.setUpEmployeeInformation(null);
                    oldPersonOfUpEmployeeInformation = em.merge(oldPersonOfUpEmployeeInformation);
                }
                upEmployeeInformation.setPerson(person);
                upEmployeeInformation = em.merge(upEmployeeInformation);
            }
            if (locationID != null) {
                locationID.getPersonList().add(person);
                locationID = em.merge(locationID);
            }
            if (addressLine1 != null) {
                addressLine1.getPersonList().add(person);
                addressLine1 = em.merge(addressLine1);
            }
            for (CommitteeMeeting committeeMeetingListCommitteeMeeting : person.getCommitteeMeetingList()) {
                committeeMeetingListCommitteeMeeting.getPersonList().add(person);
                committeeMeetingListCommitteeMeeting = em.merge(committeeMeetingListCommitteeMeeting);
            }
            for (SecurityRole securityRoleListSecurityRole : person.getSecurityRoleList()) {
                securityRoleListSecurityRole.getPersonList().add(person);
                securityRoleListSecurityRole = em.merge(securityRoleListSecurityRole);
            }
            for (AuditLog auditLogListAuditLog : person.getAuditLogList()) {
                Person oldPersonIDOfAuditLogListAuditLog = auditLogListAuditLog.getPersonID();
                auditLogListAuditLog.setPersonID(person);
                auditLogListAuditLog = em.merge(auditLogListAuditLog);
                if (oldPersonIDOfAuditLogListAuditLog != null) {
                    oldPersonIDOfAuditLogListAuditLog.getAuditLogList().remove(auditLogListAuditLog);
                    oldPersonIDOfAuditLogListAuditLog = em.merge(oldPersonIDOfAuditLogListAuditLog);
                }
            }
            for (Endorsement endorsementListEndorsement : person.getEndorsementList()) {
                Person oldDeanIDOfEndorsementListEndorsement = endorsementListEndorsement.getDeanID();
                endorsementListEndorsement.setDeanID(person);
                endorsementListEndorsement = em.merge(endorsementListEndorsement);
                if (oldDeanIDOfEndorsementListEndorsement != null) {
                    oldDeanIDOfEndorsementListEndorsement.getEndorsementList().remove(endorsementListEndorsement);
                    oldDeanIDOfEndorsementListEndorsement = em.merge(oldDeanIDOfEndorsementListEndorsement);
                }
            }
            for (RecommendationReport recommendationReportListRecommendationReport : person.getRecommendationReportList()) {
                Person oldHodIDOfRecommendationReportListRecommendationReport = recommendationReportListRecommendationReport.getHodID();
                recommendationReportListRecommendationReport.setHodID(person);
                recommendationReportListRecommendationReport = em.merge(recommendationReportListRecommendationReport);
                if (oldHodIDOfRecommendationReportListRecommendationReport != null) {
                    oldHodIDOfRecommendationReportListRecommendationReport.getRecommendationReportList().remove(recommendationReportListRecommendationReport);
                    oldHodIDOfRecommendationReportListRecommendationReport = em.merge(oldHodIDOfRecommendationReportListRecommendationReport);
                }
            }
            for (RefereeReport refereeReportListRefereeReport : person.getRefereeReportList()) {
                Person oldRefereeIDOfRefereeReportListRefereeReport = refereeReportListRefereeReport.getRefereeID();
                refereeReportListRefereeReport.setRefereeID(person);
                refereeReportListRefereeReport = em.merge(refereeReportListRefereeReport);
                if (oldRefereeIDOfRefereeReportListRefereeReport != null) {
                    oldRefereeIDOfRefereeReportListRefereeReport.getRefereeReportList().remove(refereeReportListRefereeReport);
                    oldRefereeIDOfRefereeReportListRefereeReport = em.merge(oldRefereeIDOfRefereeReportListRefereeReport);
                }
            }
            for (FundingReport fundingReportListFundingReport : person.getFundingReportList()) {
                Person oldDrisIDOfFundingReportListFundingReport = fundingReportListFundingReport.getDrisID();
                fundingReportListFundingReport.setDrisID(person);
                fundingReportListFundingReport = em.merge(fundingReportListFundingReport);
                if (oldDrisIDOfFundingReportListFundingReport != null) {
                    oldDrisIDOfFundingReportListFundingReport.getFundingReportList().remove(fundingReportListFundingReport);
                    oldDrisIDOfFundingReportListFundingReport = em.merge(oldDrisIDOfFundingReportListFundingReport);
                }
            }
            for (Notification notificationListNotification : person.getNotificationList()) {
                Person oldSenderIDOfNotificationListNotification = notificationListNotification.getSenderID();
                notificationListNotification.setSenderID(person);
                notificationListNotification = em.merge(notificationListNotification);
                if (oldSenderIDOfNotificationListNotification != null) {
                    oldSenderIDOfNotificationListNotification.getNotificationList().remove(notificationListNotification);
                    oldSenderIDOfNotificationListNotification = em.merge(oldSenderIDOfNotificationListNotification);
                }
            }
            for (Notification notificationList1Notification : person.getNotificationList1()) {
                Person oldRecieverIDOfNotificationList1Notification = notificationList1Notification.getRecieverID();
                notificationList1Notification.setRecieverID(person);
                notificationList1Notification = em.merge(notificationList1Notification);
                if (oldRecieverIDOfNotificationList1Notification != null) {
                    oldRecieverIDOfNotificationList1Notification.getNotificationList1().remove(notificationList1Notification);
                    oldRecieverIDOfNotificationList1Notification = em.merge(oldRecieverIDOfNotificationList1Notification);
                }
            }
            for (Cv cvListCv : person.getCvList()) {
                Person oldOwnerIDOfCvListCv = cvListCv.getOwnerID();
                cvListCv.setOwnerID(person);
                cvListCv = em.merge(cvListCv);
                if (oldOwnerIDOfCvListCv != null) {
                    oldOwnerIDOfCvListCv.getCvList().remove(cvListCv);
                    oldOwnerIDOfCvListCv = em.merge(oldOwnerIDOfCvListCv);
                }
            }
            for (Application applicationListApplication : person.getApplicationList()) {
                Person oldFellowOfApplicationListApplication = applicationListApplication.getFellow();
                applicationListApplication.setFellow(person);
                applicationListApplication = em.merge(applicationListApplication);
                if (oldFellowOfApplicationListApplication != null) {
                    oldFellowOfApplicationListApplication.getApplicationList().remove(applicationListApplication);
                    oldFellowOfApplicationListApplication = em.merge(oldFellowOfApplicationListApplication);
                }
            }
            for (Application applicationList1Application : person.getApplicationList1()) {
                Person oldGrantHolderIDOfApplicationList1Application = applicationList1Application.getGrantHolderID();
                applicationList1Application.setGrantHolderID(person);
                applicationList1Application = em.merge(applicationList1Application);
                if (oldGrantHolderIDOfApplicationList1Application != null) {
                    oldGrantHolderIDOfApplicationList1Application.getApplicationList1().remove(applicationList1Application);
                    oldGrantHolderIDOfApplicationList1Application = em.merge(oldGrantHolderIDOfApplicationList1Application);
                }
            }
            for (MinuteComment minuteCommentListMinuteComment : person.getMinuteCommentList()) {
                Person oldAttendeeIDOfMinuteCommentListMinuteComment = minuteCommentListMinuteComment.getAttendeeID();
                minuteCommentListMinuteComment.setAttendeeID(person);
                minuteCommentListMinuteComment = em.merge(minuteCommentListMinuteComment);
                if (oldAttendeeIDOfMinuteCommentListMinuteComment != null) {
                    oldAttendeeIDOfMinuteCommentListMinuteComment.getMinuteCommentList().remove(minuteCommentListMinuteComment);
                    oldAttendeeIDOfMinuteCommentListMinuteComment = em.merge(oldAttendeeIDOfMinuteCommentListMinuteComment);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPerson(person.getSystemID()) != null) {
                throw new PreexistingEntityException("Person " + person + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Person person) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person persistentPerson = em.find(Person.class, person.getSystemID());
            UpEmployeeInformation upEmployeeInformationOld = persistentPerson.getUpEmployeeInformation();
            UpEmployeeInformation upEmployeeInformationNew = person.getUpEmployeeInformation();
            Location locationIDOld = persistentPerson.getLocationID();
            Location locationIDNew = person.getLocationID();
            Address addressLine1Old = persistentPerson.getAddressLine1();
            Address addressLine1New = person.getAddressLine1();
            List<CommitteeMeeting> committeeMeetingListOld = persistentPerson.getCommitteeMeetingList();
            List<CommitteeMeeting> committeeMeetingListNew = person.getCommitteeMeetingList();
            List<SecurityRole> securityRoleListOld = persistentPerson.getSecurityRoleList();
            List<SecurityRole> securityRoleListNew = person.getSecurityRoleList();
            List<AuditLog> auditLogListOld = persistentPerson.getAuditLogList();
            List<AuditLog> auditLogListNew = person.getAuditLogList();
            List<Endorsement> endorsementListOld = persistentPerson.getEndorsementList();
            List<Endorsement> endorsementListNew = person.getEndorsementList();
            List<RecommendationReport> recommendationReportListOld = persistentPerson.getRecommendationReportList();
            List<RecommendationReport> recommendationReportListNew = person.getRecommendationReportList();
            List<RefereeReport> refereeReportListOld = persistentPerson.getRefereeReportList();
            List<RefereeReport> refereeReportListNew = person.getRefereeReportList();
            List<FundingReport> fundingReportListOld = persistentPerson.getFundingReportList();
            List<FundingReport> fundingReportListNew = person.getFundingReportList();
            List<Notification> notificationListOld = persistentPerson.getNotificationList();
            List<Notification> notificationListNew = person.getNotificationList();
            List<Notification> notificationList1Old = persistentPerson.getNotificationList1();
            List<Notification> notificationList1New = person.getNotificationList1();
            List<Cv> cvListOld = persistentPerson.getCvList();
            List<Cv> cvListNew = person.getCvList();
            List<Application> applicationListOld = persistentPerson.getApplicationList();
            List<Application> applicationListNew = person.getApplicationList();
            List<Application> applicationList1Old = persistentPerson.getApplicationList1();
            List<Application> applicationList1New = person.getApplicationList1();
            List<MinuteComment> minuteCommentListOld = persistentPerson.getMinuteCommentList();
            List<MinuteComment> minuteCommentListNew = person.getMinuteCommentList();
            List<String> illegalOrphanMessages = null;
            if (upEmployeeInformationOld != null && !upEmployeeInformationOld.equals(upEmployeeInformationNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain UpEmployeeInformation " + upEmployeeInformationOld + " since its person field is not nullable.");
            }
            for (AuditLog auditLogListOldAuditLog : auditLogListOld) {
                if (!auditLogListNew.contains(auditLogListOldAuditLog)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AuditLog " + auditLogListOldAuditLog + " since its personID field is not nullable.");
                }
            }
            for (Endorsement endorsementListOldEndorsement : endorsementListOld) {
                if (!endorsementListNew.contains(endorsementListOldEndorsement)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Endorsement " + endorsementListOldEndorsement + " since its deanID field is not nullable.");
                }
            }
            for (RecommendationReport recommendationReportListOldRecommendationReport : recommendationReportListOld) {
                if (!recommendationReportListNew.contains(recommendationReportListOldRecommendationReport)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecommendationReport " + recommendationReportListOldRecommendationReport + " since its hodID field is not nullable.");
                }
            }
            for (RefereeReport refereeReportListOldRefereeReport : refereeReportListOld) {
                if (!refereeReportListNew.contains(refereeReportListOldRefereeReport)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RefereeReport " + refereeReportListOldRefereeReport + " since its refereeID field is not nullable.");
                }
            }
            for (FundingReport fundingReportListOldFundingReport : fundingReportListOld) {
                if (!fundingReportListNew.contains(fundingReportListOldFundingReport)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FundingReport " + fundingReportListOldFundingReport + " since its drisID field is not nullable.");
                }
            }
            for (Notification notificationListOldNotification : notificationListOld) {
                if (!notificationListNew.contains(notificationListOldNotification)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notification " + notificationListOldNotification + " since its senderID field is not nullable.");
                }
            }
            for (Notification notificationList1OldNotification : notificationList1Old) {
                if (!notificationList1New.contains(notificationList1OldNotification)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notification " + notificationList1OldNotification + " since its recieverID field is not nullable.");
                }
            }
            for (Cv cvListOldCv : cvListOld) {
                if (!cvListNew.contains(cvListOldCv)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cv " + cvListOldCv + " since its ownerID field is not nullable.");
                }
            }
            for (Application applicationListOldApplication : applicationListOld) {
                if (!applicationListNew.contains(applicationListOldApplication)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Application " + applicationListOldApplication + " since its fellow field is not nullable.");
                }
            }
            for (Application applicationList1OldApplication : applicationList1Old) {
                if (!applicationList1New.contains(applicationList1OldApplication)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Application " + applicationList1OldApplication + " since its grantHolderID field is not nullable.");
                }
            }
            for (MinuteComment minuteCommentListOldMinuteComment : minuteCommentListOld) {
                if (!minuteCommentListNew.contains(minuteCommentListOldMinuteComment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MinuteComment " + minuteCommentListOldMinuteComment + " since its attendeeID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (upEmployeeInformationNew != null) {
                upEmployeeInformationNew = em.getReference(upEmployeeInformationNew.getClass(), upEmployeeInformationNew.getEmployeeID());
                person.setUpEmployeeInformation(upEmployeeInformationNew);
            }
            if (locationIDNew != null) {
                locationIDNew = em.getReference(locationIDNew.getClass(), locationIDNew.getLocationID());
                person.setLocationID(locationIDNew);
            }
            if (addressLine1New != null) {
                addressLine1New = em.getReference(addressLine1New.getClass(), addressLine1New.getAddressID());
                person.setAddressLine1(addressLine1New);
            }
            List<CommitteeMeeting> attachedCommitteeMeetingListNew = new ArrayList<CommitteeMeeting>();
            for (CommitteeMeeting committeeMeetingListNewCommitteeMeetingToAttach : committeeMeetingListNew) {
                committeeMeetingListNewCommitteeMeetingToAttach = em.getReference(committeeMeetingListNewCommitteeMeetingToAttach.getClass(), committeeMeetingListNewCommitteeMeetingToAttach.getMeetingID());
                attachedCommitteeMeetingListNew.add(committeeMeetingListNewCommitteeMeetingToAttach);
            }
            committeeMeetingListNew = attachedCommitteeMeetingListNew;
            person.setCommitteeMeetingList(committeeMeetingListNew);
            List<SecurityRole> attachedSecurityRoleListNew = new ArrayList<SecurityRole>();
            for (SecurityRole securityRoleListNewSecurityRoleToAttach : securityRoleListNew) {
                securityRoleListNewSecurityRoleToAttach = em.getReference(securityRoleListNewSecurityRoleToAttach.getClass(), securityRoleListNewSecurityRoleToAttach.getRoleID());
                attachedSecurityRoleListNew.add(securityRoleListNewSecurityRoleToAttach);
            }
            securityRoleListNew = attachedSecurityRoleListNew;
            person.setSecurityRoleList(securityRoleListNew);
            List<AuditLog> attachedAuditLogListNew = new ArrayList<AuditLog>();
            for (AuditLog auditLogListNewAuditLogToAttach : auditLogListNew) {
                auditLogListNewAuditLogToAttach = em.getReference(auditLogListNewAuditLogToAttach.getClass(), auditLogListNewAuditLogToAttach.getEntryID());
                attachedAuditLogListNew.add(auditLogListNewAuditLogToAttach);
            }
            auditLogListNew = attachedAuditLogListNew;
            person.setAuditLogList(auditLogListNew);
            List<Endorsement> attachedEndorsementListNew = new ArrayList<Endorsement>();
            for (Endorsement endorsementListNewEndorsementToAttach : endorsementListNew) {
                endorsementListNewEndorsementToAttach = em.getReference(endorsementListNewEndorsementToAttach.getClass(), endorsementListNewEndorsementToAttach.getEndorsementID());
                attachedEndorsementListNew.add(endorsementListNewEndorsementToAttach);
            }
            endorsementListNew = attachedEndorsementListNew;
            person.setEndorsementList(endorsementListNew);
            List<RecommendationReport> attachedRecommendationReportListNew = new ArrayList<RecommendationReport>();
            for (RecommendationReport recommendationReportListNewRecommendationReportToAttach : recommendationReportListNew) {
                recommendationReportListNewRecommendationReportToAttach = em.getReference(recommendationReportListNewRecommendationReportToAttach.getClass(), recommendationReportListNewRecommendationReportToAttach.getReportID());
                attachedRecommendationReportListNew.add(recommendationReportListNewRecommendationReportToAttach);
            }
            recommendationReportListNew = attachedRecommendationReportListNew;
            person.setRecommendationReportList(recommendationReportListNew);
            List<RefereeReport> attachedRefereeReportListNew = new ArrayList<RefereeReport>();
            for (RefereeReport refereeReportListNewRefereeReportToAttach : refereeReportListNew) {
                refereeReportListNewRefereeReportToAttach = em.getReference(refereeReportListNewRefereeReportToAttach.getClass(), refereeReportListNewRefereeReportToAttach.getReportID());
                attachedRefereeReportListNew.add(refereeReportListNewRefereeReportToAttach);
            }
            refereeReportListNew = attachedRefereeReportListNew;
            person.setRefereeReportList(refereeReportListNew);
            List<FundingReport> attachedFundingReportListNew = new ArrayList<FundingReport>();
            for (FundingReport fundingReportListNewFundingReportToAttach : fundingReportListNew) {
                fundingReportListNewFundingReportToAttach = em.getReference(fundingReportListNewFundingReportToAttach.getClass(), fundingReportListNewFundingReportToAttach.getReportID());
                attachedFundingReportListNew.add(fundingReportListNewFundingReportToAttach);
            }
            fundingReportListNew = attachedFundingReportListNew;
            person.setFundingReportList(fundingReportListNew);
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
            List<Cv> attachedCvListNew = new ArrayList<Cv>();
            for (Cv cvListNewCvToAttach : cvListNew) {
                cvListNewCvToAttach = em.getReference(cvListNewCvToAttach.getClass(), cvListNewCvToAttach.getCvID());
                attachedCvListNew.add(cvListNewCvToAttach);
            }
            cvListNew = attachedCvListNew;
            person.setCvList(cvListNew);
            List<Application> attachedApplicationListNew = new ArrayList<Application>();
            for (Application applicationListNewApplicationToAttach : applicationListNew) {
                applicationListNewApplicationToAttach = em.getReference(applicationListNewApplicationToAttach.getClass(), applicationListNewApplicationToAttach.getApplicationID());
                attachedApplicationListNew.add(applicationListNewApplicationToAttach);
            }
            applicationListNew = attachedApplicationListNew;
            person.setApplicationList(applicationListNew);
            List<Application> attachedApplicationList1New = new ArrayList<Application>();
            for (Application applicationList1NewApplicationToAttach : applicationList1New) {
                applicationList1NewApplicationToAttach = em.getReference(applicationList1NewApplicationToAttach.getClass(), applicationList1NewApplicationToAttach.getApplicationID());
                attachedApplicationList1New.add(applicationList1NewApplicationToAttach);
            }
            applicationList1New = attachedApplicationList1New;
            person.setApplicationList1(applicationList1New);
            List<MinuteComment> attachedMinuteCommentListNew = new ArrayList<MinuteComment>();
            for (MinuteComment minuteCommentListNewMinuteCommentToAttach : minuteCommentListNew) {
                minuteCommentListNewMinuteCommentToAttach = em.getReference(minuteCommentListNewMinuteCommentToAttach.getClass(), minuteCommentListNewMinuteCommentToAttach.getCommentID());
                attachedMinuteCommentListNew.add(minuteCommentListNewMinuteCommentToAttach);
            }
            minuteCommentListNew = attachedMinuteCommentListNew;
            person.setMinuteCommentList(minuteCommentListNew);
            person = em.merge(person);
            if (upEmployeeInformationNew != null && !upEmployeeInformationNew.equals(upEmployeeInformationOld)) {
                Person oldPersonOfUpEmployeeInformation = upEmployeeInformationNew.getPerson();
                if (oldPersonOfUpEmployeeInformation != null) {
                    oldPersonOfUpEmployeeInformation.setUpEmployeeInformation(null);
                    oldPersonOfUpEmployeeInformation = em.merge(oldPersonOfUpEmployeeInformation);
                }
                upEmployeeInformationNew.setPerson(person);
                upEmployeeInformationNew = em.merge(upEmployeeInformationNew);
            }
            if (locationIDOld != null && !locationIDOld.equals(locationIDNew)) {
                locationIDOld.getPersonList().remove(person);
                locationIDOld = em.merge(locationIDOld);
            }
            if (locationIDNew != null && !locationIDNew.equals(locationIDOld)) {
                locationIDNew.getPersonList().add(person);
                locationIDNew = em.merge(locationIDNew);
            }
            if (addressLine1Old != null && !addressLine1Old.equals(addressLine1New)) {
                addressLine1Old.getPersonList().remove(person);
                addressLine1Old = em.merge(addressLine1Old);
            }
            if (addressLine1New != null && !addressLine1New.equals(addressLine1Old)) {
                addressLine1New.getPersonList().add(person);
                addressLine1New = em.merge(addressLine1New);
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
            for (AuditLog auditLogListNewAuditLog : auditLogListNew) {
                if (!auditLogListOld.contains(auditLogListNewAuditLog)) {
                    Person oldPersonIDOfAuditLogListNewAuditLog = auditLogListNewAuditLog.getPersonID();
                    auditLogListNewAuditLog.setPersonID(person);
                    auditLogListNewAuditLog = em.merge(auditLogListNewAuditLog);
                    if (oldPersonIDOfAuditLogListNewAuditLog != null && !oldPersonIDOfAuditLogListNewAuditLog.equals(person)) {
                        oldPersonIDOfAuditLogListNewAuditLog.getAuditLogList().remove(auditLogListNewAuditLog);
                        oldPersonIDOfAuditLogListNewAuditLog = em.merge(oldPersonIDOfAuditLogListNewAuditLog);
                    }
                }
            }
            for (Endorsement endorsementListNewEndorsement : endorsementListNew) {
                if (!endorsementListOld.contains(endorsementListNewEndorsement)) {
                    Person oldDeanIDOfEndorsementListNewEndorsement = endorsementListNewEndorsement.getDeanID();
                    endorsementListNewEndorsement.setDeanID(person);
                    endorsementListNewEndorsement = em.merge(endorsementListNewEndorsement);
                    if (oldDeanIDOfEndorsementListNewEndorsement != null && !oldDeanIDOfEndorsementListNewEndorsement.equals(person)) {
                        oldDeanIDOfEndorsementListNewEndorsement.getEndorsementList().remove(endorsementListNewEndorsement);
                        oldDeanIDOfEndorsementListNewEndorsement = em.merge(oldDeanIDOfEndorsementListNewEndorsement);
                    }
                }
            }
            for (RecommendationReport recommendationReportListNewRecommendationReport : recommendationReportListNew) {
                if (!recommendationReportListOld.contains(recommendationReportListNewRecommendationReport)) {
                    Person oldHodIDOfRecommendationReportListNewRecommendationReport = recommendationReportListNewRecommendationReport.getHodID();
                    recommendationReportListNewRecommendationReport.setHodID(person);
                    recommendationReportListNewRecommendationReport = em.merge(recommendationReportListNewRecommendationReport);
                    if (oldHodIDOfRecommendationReportListNewRecommendationReport != null && !oldHodIDOfRecommendationReportListNewRecommendationReport.equals(person)) {
                        oldHodIDOfRecommendationReportListNewRecommendationReport.getRecommendationReportList().remove(recommendationReportListNewRecommendationReport);
                        oldHodIDOfRecommendationReportListNewRecommendationReport = em.merge(oldHodIDOfRecommendationReportListNewRecommendationReport);
                    }
                }
            }
            for (RefereeReport refereeReportListNewRefereeReport : refereeReportListNew) {
                if (!refereeReportListOld.contains(refereeReportListNewRefereeReport)) {
                    Person oldRefereeIDOfRefereeReportListNewRefereeReport = refereeReportListNewRefereeReport.getRefereeID();
                    refereeReportListNewRefereeReport.setRefereeID(person);
                    refereeReportListNewRefereeReport = em.merge(refereeReportListNewRefereeReport);
                    if (oldRefereeIDOfRefereeReportListNewRefereeReport != null && !oldRefereeIDOfRefereeReportListNewRefereeReport.equals(person)) {
                        oldRefereeIDOfRefereeReportListNewRefereeReport.getRefereeReportList().remove(refereeReportListNewRefereeReport);
                        oldRefereeIDOfRefereeReportListNewRefereeReport = em.merge(oldRefereeIDOfRefereeReportListNewRefereeReport);
                    }
                }
            }
            for (FundingReport fundingReportListNewFundingReport : fundingReportListNew) {
                if (!fundingReportListOld.contains(fundingReportListNewFundingReport)) {
                    Person oldDrisIDOfFundingReportListNewFundingReport = fundingReportListNewFundingReport.getDrisID();
                    fundingReportListNewFundingReport.setDrisID(person);
                    fundingReportListNewFundingReport = em.merge(fundingReportListNewFundingReport);
                    if (oldDrisIDOfFundingReportListNewFundingReport != null && !oldDrisIDOfFundingReportListNewFundingReport.equals(person)) {
                        oldDrisIDOfFundingReportListNewFundingReport.getFundingReportList().remove(fundingReportListNewFundingReport);
                        oldDrisIDOfFundingReportListNewFundingReport = em.merge(oldDrisIDOfFundingReportListNewFundingReport);
                    }
                }
            }
            for (Notification notificationListNewNotification : notificationListNew) {
                if (!notificationListOld.contains(notificationListNewNotification)) {
                    Person oldSenderIDOfNotificationListNewNotification = notificationListNewNotification.getSenderID();
                    notificationListNewNotification.setSenderID(person);
                    notificationListNewNotification = em.merge(notificationListNewNotification);
                    if (oldSenderIDOfNotificationListNewNotification != null && !oldSenderIDOfNotificationListNewNotification.equals(person)) {
                        oldSenderIDOfNotificationListNewNotification.getNotificationList().remove(notificationListNewNotification);
                        oldSenderIDOfNotificationListNewNotification = em.merge(oldSenderIDOfNotificationListNewNotification);
                    }
                }
            }
            for (Notification notificationList1NewNotification : notificationList1New) {
                if (!notificationList1Old.contains(notificationList1NewNotification)) {
                    Person oldRecieverIDOfNotificationList1NewNotification = notificationList1NewNotification.getRecieverID();
                    notificationList1NewNotification.setRecieverID(person);
                    notificationList1NewNotification = em.merge(notificationList1NewNotification);
                    if (oldRecieverIDOfNotificationList1NewNotification != null && !oldRecieverIDOfNotificationList1NewNotification.equals(person)) {
                        oldRecieverIDOfNotificationList1NewNotification.getNotificationList1().remove(notificationList1NewNotification);
                        oldRecieverIDOfNotificationList1NewNotification = em.merge(oldRecieverIDOfNotificationList1NewNotification);
                    }
                }
            }
            for (Cv cvListNewCv : cvListNew) {
                if (!cvListOld.contains(cvListNewCv)) {
                    Person oldOwnerIDOfCvListNewCv = cvListNewCv.getOwnerID();
                    cvListNewCv.setOwnerID(person);
                    cvListNewCv = em.merge(cvListNewCv);
                    if (oldOwnerIDOfCvListNewCv != null && !oldOwnerIDOfCvListNewCv.equals(person)) {
                        oldOwnerIDOfCvListNewCv.getCvList().remove(cvListNewCv);
                        oldOwnerIDOfCvListNewCv = em.merge(oldOwnerIDOfCvListNewCv);
                    }
                }
            }
            for (Application applicationListNewApplication : applicationListNew) {
                if (!applicationListOld.contains(applicationListNewApplication)) {
                    Person oldFellowOfApplicationListNewApplication = applicationListNewApplication.getFellow();
                    applicationListNewApplication.setFellow(person);
                    applicationListNewApplication = em.merge(applicationListNewApplication);
                    if (oldFellowOfApplicationListNewApplication != null && !oldFellowOfApplicationListNewApplication.equals(person)) {
                        oldFellowOfApplicationListNewApplication.getApplicationList().remove(applicationListNewApplication);
                        oldFellowOfApplicationListNewApplication = em.merge(oldFellowOfApplicationListNewApplication);
                    }
                }
            }
            for (Application applicationList1NewApplication : applicationList1New) {
                if (!applicationList1Old.contains(applicationList1NewApplication)) {
                    Person oldGrantHolderIDOfApplicationList1NewApplication = applicationList1NewApplication.getGrantHolderID();
                    applicationList1NewApplication.setGrantHolderID(person);
                    applicationList1NewApplication = em.merge(applicationList1NewApplication);
                    if (oldGrantHolderIDOfApplicationList1NewApplication != null && !oldGrantHolderIDOfApplicationList1NewApplication.equals(person)) {
                        oldGrantHolderIDOfApplicationList1NewApplication.getApplicationList1().remove(applicationList1NewApplication);
                        oldGrantHolderIDOfApplicationList1NewApplication = em.merge(oldGrantHolderIDOfApplicationList1NewApplication);
                    }
                }
            }
            for (MinuteComment minuteCommentListNewMinuteComment : minuteCommentListNew) {
                if (!minuteCommentListOld.contains(minuteCommentListNewMinuteComment)) {
                    Person oldAttendeeIDOfMinuteCommentListNewMinuteComment = minuteCommentListNewMinuteComment.getAttendeeID();
                    minuteCommentListNewMinuteComment.setAttendeeID(person);
                    minuteCommentListNewMinuteComment = em.merge(minuteCommentListNewMinuteComment);
                    if (oldAttendeeIDOfMinuteCommentListNewMinuteComment != null && !oldAttendeeIDOfMinuteCommentListNewMinuteComment.equals(person)) {
                        oldAttendeeIDOfMinuteCommentListNewMinuteComment.getMinuteCommentList().remove(minuteCommentListNewMinuteComment);
                        oldAttendeeIDOfMinuteCommentListNewMinuteComment = em.merge(oldAttendeeIDOfMinuteCommentListNewMinuteComment);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = person.getSystemID();
                if (findPerson(id) == null) {
                    throw new NonexistentEntityException("The person with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person person;
            try {
                person = em.getReference(Person.class, id);
                person.getSystemID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The person with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            UpEmployeeInformation upEmployeeInformationOrphanCheck = person.getUpEmployeeInformation();
            if (upEmployeeInformationOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the UpEmployeeInformation " + upEmployeeInformationOrphanCheck + " in its upEmployeeInformation field has a non-nullable person field.");
            }
            List<AuditLog> auditLogListOrphanCheck = person.getAuditLogList();
            for (AuditLog auditLogListOrphanCheckAuditLog : auditLogListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the AuditLog " + auditLogListOrphanCheckAuditLog + " in its auditLogList field has a non-nullable personID field.");
            }
            List<Endorsement> endorsementListOrphanCheck = person.getEndorsementList();
            for (Endorsement endorsementListOrphanCheckEndorsement : endorsementListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Endorsement " + endorsementListOrphanCheckEndorsement + " in its endorsementList field has a non-nullable deanID field.");
            }
            List<RecommendationReport> recommendationReportListOrphanCheck = person.getRecommendationReportList();
            for (RecommendationReport recommendationReportListOrphanCheckRecommendationReport : recommendationReportListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the RecommendationReport " + recommendationReportListOrphanCheckRecommendationReport + " in its recommendationReportList field has a non-nullable hodID field.");
            }
            List<RefereeReport> refereeReportListOrphanCheck = person.getRefereeReportList();
            for (RefereeReport refereeReportListOrphanCheckRefereeReport : refereeReportListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the RefereeReport " + refereeReportListOrphanCheckRefereeReport + " in its refereeReportList field has a non-nullable refereeID field.");
            }
            List<FundingReport> fundingReportListOrphanCheck = person.getFundingReportList();
            for (FundingReport fundingReportListOrphanCheckFundingReport : fundingReportListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the FundingReport " + fundingReportListOrphanCheckFundingReport + " in its fundingReportList field has a non-nullable drisID field.");
            }
            List<Notification> notificationListOrphanCheck = person.getNotificationList();
            for (Notification notificationListOrphanCheckNotification : notificationListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Notification " + notificationListOrphanCheckNotification + " in its notificationList field has a non-nullable senderID field.");
            }
            List<Notification> notificationList1OrphanCheck = person.getNotificationList1();
            for (Notification notificationList1OrphanCheckNotification : notificationList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Notification " + notificationList1OrphanCheckNotification + " in its notificationList1 field has a non-nullable recieverID field.");
            }
            List<Cv> cvListOrphanCheck = person.getCvList();
            for (Cv cvListOrphanCheckCv : cvListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Cv " + cvListOrphanCheckCv + " in its cvList field has a non-nullable ownerID field.");
            }
            List<Application> applicationListOrphanCheck = person.getApplicationList();
            for (Application applicationListOrphanCheckApplication : applicationListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Application " + applicationListOrphanCheckApplication + " in its applicationList field has a non-nullable fellow field.");
            }
            List<Application> applicationList1OrphanCheck = person.getApplicationList1();
            for (Application applicationList1OrphanCheckApplication : applicationList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Application " + applicationList1OrphanCheckApplication + " in its applicationList1 field has a non-nullable grantHolderID field.");
            }
            List<MinuteComment> minuteCommentListOrphanCheck = person.getMinuteCommentList();
            for (MinuteComment minuteCommentListOrphanCheckMinuteComment : minuteCommentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the MinuteComment " + minuteCommentListOrphanCheckMinuteComment + " in its minuteCommentList field has a non-nullable attendeeID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Location locationID = person.getLocationID();
            if (locationID != null) {
                locationID.getPersonList().remove(person);
                locationID = em.merge(locationID);
            }
            Address addressLine1 = person.getAddressLine1();
            if (addressLine1 != null) {
                addressLine1.getPersonList().remove(person);
                addressLine1 = em.merge(addressLine1);
            }
            List<CommitteeMeeting> committeeMeetingList = person.getCommitteeMeetingList();
            for (CommitteeMeeting committeeMeetingListCommitteeMeeting : committeeMeetingList) {
                committeeMeetingListCommitteeMeeting.getPersonList().remove(person);
                committeeMeetingListCommitteeMeeting = em.merge(committeeMeetingListCommitteeMeeting);
            }
            List<SecurityRole> securityRoleList = person.getSecurityRoleList();
            for (SecurityRole securityRoleListSecurityRole : securityRoleList) {
                securityRoleListSecurityRole.getPersonList().remove(person);
                securityRoleListSecurityRole = em.merge(securityRoleListSecurityRole);
            }
            em.remove(person);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
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
            lastID = em.createQuery("SELECT p.systemID FROM Person p WHERE p.systemID LIKE ':year%' ORDER BY p.systemID DESC",String.class).setParameter("year", tempYear).setMaxResults(1).getSingleResult();
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
    public static boolean doesPersonHaveSecurityRole(Person person, long roleID)
    {
        boolean isFound = false;
        
        for(SecurityRole sr : person.getSecurityRoleList())
        {
            if(sr.getRoleID() == roleID)
            {
                isFound = true;
                break;
            }
        }
        
        return isFound;        
    }
    
    
}
