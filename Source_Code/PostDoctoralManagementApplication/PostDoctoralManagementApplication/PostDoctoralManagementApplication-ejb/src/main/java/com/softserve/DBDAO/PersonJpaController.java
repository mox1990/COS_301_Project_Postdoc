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
import com.softserve.DBEnties.UpEmployeeInformation;
import com.softserve.DBEnties.Location;
import com.softserve.DBEnties.Address;
import com.softserve.DBEnties.CommitteeMeeting;
import java.util.ArrayList;
import java.util.Collection;
import com.softserve.DBEnties.SecurityRole;
import com.softserve.DBEnties.AuditLog;
import com.softserve.DBEnties.Endorsement;
import com.softserve.DBEnties.RecommendationReport;
import com.softserve.DBEnties.RefereeReport;
import com.softserve.DBEnties.FundingReport;
import com.softserve.DBEnties.Notification;
import com.softserve.DBEnties.Cv;
import com.softserve.DBEnties.Application;
import com.softserve.DBEnties.MinuteComment;
import com.softserve.DBEnties.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
        if (person.getCommitteeMeetingCollection() == null) {
            person.setCommitteeMeetingCollection(new ArrayList<CommitteeMeeting>());
        }
        if (person.getSecurityRoleCollection() == null) {
            person.setSecurityRoleCollection(new ArrayList<SecurityRole>());
        }
        if (person.getAuditLogCollection() == null) {
            person.setAuditLogCollection(new ArrayList<AuditLog>());
        }
        if (person.getEndorsementCollection() == null) {
            person.setEndorsementCollection(new ArrayList<Endorsement>());
        }
        if (person.getRecommendationReportCollection() == null) {
            person.setRecommendationReportCollection(new ArrayList<RecommendationReport>());
        }
        if (person.getRefereeReportCollection() == null) {
            person.setRefereeReportCollection(new ArrayList<RefereeReport>());
        }
        if (person.getFundingReportCollection() == null) {
            person.setFundingReportCollection(new ArrayList<FundingReport>());
        }
        if (person.getNotificationCollection() == null) {
            person.setNotificationCollection(new ArrayList<Notification>());
        }
        if (person.getNotificationCollection1() == null) {
            person.setNotificationCollection1(new ArrayList<Notification>());
        }
        if (person.getCvCollection() == null) {
            person.setCvCollection(new ArrayList<Cv>());
        }
        if (person.getApplicationCollection() == null) {
            person.setApplicationCollection(new ArrayList<Application>());
        }
        if (person.getApplicationCollection1() == null) {
            person.setApplicationCollection1(new ArrayList<Application>());
        }
        if (person.getMinuteCommentCollection() == null) {
            person.setMinuteCommentCollection(new ArrayList<MinuteComment>());
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
            Collection<CommitteeMeeting> attachedCommitteeMeetingCollection = new ArrayList<CommitteeMeeting>();
            for (CommitteeMeeting committeeMeetingCollectionCommitteeMeetingToAttach : person.getCommitteeMeetingCollection()) {
                committeeMeetingCollectionCommitteeMeetingToAttach = em.getReference(committeeMeetingCollectionCommitteeMeetingToAttach.getClass(), committeeMeetingCollectionCommitteeMeetingToAttach.getMeetingID());
                attachedCommitteeMeetingCollection.add(committeeMeetingCollectionCommitteeMeetingToAttach);
            }
            person.setCommitteeMeetingCollection(attachedCommitteeMeetingCollection);
            Collection<SecurityRole> attachedSecurityRoleCollection = new ArrayList<SecurityRole>();
            for (SecurityRole securityRoleCollectionSecurityRoleToAttach : person.getSecurityRoleCollection()) {
                securityRoleCollectionSecurityRoleToAttach = em.getReference(securityRoleCollectionSecurityRoleToAttach.getClass(), securityRoleCollectionSecurityRoleToAttach.getRoleID());
                attachedSecurityRoleCollection.add(securityRoleCollectionSecurityRoleToAttach);
            }
            person.setSecurityRoleCollection(attachedSecurityRoleCollection);
            Collection<AuditLog> attachedAuditLogCollection = new ArrayList<AuditLog>();
            for (AuditLog auditLogCollectionAuditLogToAttach : person.getAuditLogCollection()) {
                auditLogCollectionAuditLogToAttach = em.getReference(auditLogCollectionAuditLogToAttach.getClass(), auditLogCollectionAuditLogToAttach.getEntryID());
                attachedAuditLogCollection.add(auditLogCollectionAuditLogToAttach);
            }
            person.setAuditLogCollection(attachedAuditLogCollection);
            Collection<Endorsement> attachedEndorsementCollection = new ArrayList<Endorsement>();
            for (Endorsement endorsementCollectionEndorsementToAttach : person.getEndorsementCollection()) {
                endorsementCollectionEndorsementToAttach = em.getReference(endorsementCollectionEndorsementToAttach.getClass(), endorsementCollectionEndorsementToAttach.getEndorsementID());
                attachedEndorsementCollection.add(endorsementCollectionEndorsementToAttach);
            }
            person.setEndorsementCollection(attachedEndorsementCollection);
            Collection<RecommendationReport> attachedRecommendationReportCollection = new ArrayList<RecommendationReport>();
            for (RecommendationReport recommendationReportCollectionRecommendationReportToAttach : person.getRecommendationReportCollection()) {
                recommendationReportCollectionRecommendationReportToAttach = em.getReference(recommendationReportCollectionRecommendationReportToAttach.getClass(), recommendationReportCollectionRecommendationReportToAttach.getReportID());
                attachedRecommendationReportCollection.add(recommendationReportCollectionRecommendationReportToAttach);
            }
            person.setRecommendationReportCollection(attachedRecommendationReportCollection);
            Collection<RefereeReport> attachedRefereeReportCollection = new ArrayList<RefereeReport>();
            for (RefereeReport refereeReportCollectionRefereeReportToAttach : person.getRefereeReportCollection()) {
                refereeReportCollectionRefereeReportToAttach = em.getReference(refereeReportCollectionRefereeReportToAttach.getClass(), refereeReportCollectionRefereeReportToAttach.getReportID());
                attachedRefereeReportCollection.add(refereeReportCollectionRefereeReportToAttach);
            }
            person.setRefereeReportCollection(attachedRefereeReportCollection);
            Collection<FundingReport> attachedFundingReportCollection = new ArrayList<FundingReport>();
            for (FundingReport fundingReportCollectionFundingReportToAttach : person.getFundingReportCollection()) {
                fundingReportCollectionFundingReportToAttach = em.getReference(fundingReportCollectionFundingReportToAttach.getClass(), fundingReportCollectionFundingReportToAttach.getReportID());
                attachedFundingReportCollection.add(fundingReportCollectionFundingReportToAttach);
            }
            person.setFundingReportCollection(attachedFundingReportCollection);
            Collection<Notification> attachedNotificationCollection = new ArrayList<Notification>();
            for (Notification notificationCollectionNotificationToAttach : person.getNotificationCollection()) {
                notificationCollectionNotificationToAttach = em.getReference(notificationCollectionNotificationToAttach.getClass(), notificationCollectionNotificationToAttach.getNotificationID());
                attachedNotificationCollection.add(notificationCollectionNotificationToAttach);
            }
            person.setNotificationCollection(attachedNotificationCollection);
            Collection<Notification> attachedNotificationCollection1 = new ArrayList<Notification>();
            for (Notification notificationCollection1NotificationToAttach : person.getNotificationCollection1()) {
                notificationCollection1NotificationToAttach = em.getReference(notificationCollection1NotificationToAttach.getClass(), notificationCollection1NotificationToAttach.getNotificationID());
                attachedNotificationCollection1.add(notificationCollection1NotificationToAttach);
            }
            person.setNotificationCollection1(attachedNotificationCollection1);
            Collection<Cv> attachedCvCollection = new ArrayList<Cv>();
            for (Cv cvCollectionCvToAttach : person.getCvCollection()) {
                cvCollectionCvToAttach = em.getReference(cvCollectionCvToAttach.getClass(), cvCollectionCvToAttach.getCvID());
                attachedCvCollection.add(cvCollectionCvToAttach);
            }
            person.setCvCollection(attachedCvCollection);
            Collection<Application> attachedApplicationCollection = new ArrayList<Application>();
            for (Application applicationCollectionApplicationToAttach : person.getApplicationCollection()) {
                applicationCollectionApplicationToAttach = em.getReference(applicationCollectionApplicationToAttach.getClass(), applicationCollectionApplicationToAttach.getApplicationID());
                attachedApplicationCollection.add(applicationCollectionApplicationToAttach);
            }
            person.setApplicationCollection(attachedApplicationCollection);
            Collection<Application> attachedApplicationCollection1 = new ArrayList<Application>();
            for (Application applicationCollection1ApplicationToAttach : person.getApplicationCollection1()) {
                applicationCollection1ApplicationToAttach = em.getReference(applicationCollection1ApplicationToAttach.getClass(), applicationCollection1ApplicationToAttach.getApplicationID());
                attachedApplicationCollection1.add(applicationCollection1ApplicationToAttach);
            }
            person.setApplicationCollection1(attachedApplicationCollection1);
            Collection<MinuteComment> attachedMinuteCommentCollection = new ArrayList<MinuteComment>();
            for (MinuteComment minuteCommentCollectionMinuteCommentToAttach : person.getMinuteCommentCollection()) {
                minuteCommentCollectionMinuteCommentToAttach = em.getReference(minuteCommentCollectionMinuteCommentToAttach.getClass(), minuteCommentCollectionMinuteCommentToAttach.getCommentID());
                attachedMinuteCommentCollection.add(minuteCommentCollectionMinuteCommentToAttach);
            }
            person.setMinuteCommentCollection(attachedMinuteCommentCollection);
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
                locationID.getPersonCollection().add(person);
                locationID = em.merge(locationID);
            }
            if (addressLine1 != null) {
                addressLine1.getPersonCollection().add(person);
                addressLine1 = em.merge(addressLine1);
            }
            for (CommitteeMeeting committeeMeetingCollectionCommitteeMeeting : person.getCommitteeMeetingCollection()) {
                committeeMeetingCollectionCommitteeMeeting.getPersonCollection().add(person);
                committeeMeetingCollectionCommitteeMeeting = em.merge(committeeMeetingCollectionCommitteeMeeting);
            }
            for (SecurityRole securityRoleCollectionSecurityRole : person.getSecurityRoleCollection()) {
                securityRoleCollectionSecurityRole.getPersonCollection().add(person);
                securityRoleCollectionSecurityRole = em.merge(securityRoleCollectionSecurityRole);
            }
            for (AuditLog auditLogCollectionAuditLog : person.getAuditLogCollection()) {
                Person oldPersonIDOfAuditLogCollectionAuditLog = auditLogCollectionAuditLog.getPersonID();
                auditLogCollectionAuditLog.setPersonID(person);
                auditLogCollectionAuditLog = em.merge(auditLogCollectionAuditLog);
                if (oldPersonIDOfAuditLogCollectionAuditLog != null) {
                    oldPersonIDOfAuditLogCollectionAuditLog.getAuditLogCollection().remove(auditLogCollectionAuditLog);
                    oldPersonIDOfAuditLogCollectionAuditLog = em.merge(oldPersonIDOfAuditLogCollectionAuditLog);
                }
            }
            for (Endorsement endorsementCollectionEndorsement : person.getEndorsementCollection()) {
                Person oldDeanIDOfEndorsementCollectionEndorsement = endorsementCollectionEndorsement.getDeanID();
                endorsementCollectionEndorsement.setDeanID(person);
                endorsementCollectionEndorsement = em.merge(endorsementCollectionEndorsement);
                if (oldDeanIDOfEndorsementCollectionEndorsement != null) {
                    oldDeanIDOfEndorsementCollectionEndorsement.getEndorsementCollection().remove(endorsementCollectionEndorsement);
                    oldDeanIDOfEndorsementCollectionEndorsement = em.merge(oldDeanIDOfEndorsementCollectionEndorsement);
                }
            }
            for (RecommendationReport recommendationReportCollectionRecommendationReport : person.getRecommendationReportCollection()) {
                Person oldHodIDOfRecommendationReportCollectionRecommendationReport = recommendationReportCollectionRecommendationReport.getHodID();
                recommendationReportCollectionRecommendationReport.setHodID(person);
                recommendationReportCollectionRecommendationReport = em.merge(recommendationReportCollectionRecommendationReport);
                if (oldHodIDOfRecommendationReportCollectionRecommendationReport != null) {
                    oldHodIDOfRecommendationReportCollectionRecommendationReport.getRecommendationReportCollection().remove(recommendationReportCollectionRecommendationReport);
                    oldHodIDOfRecommendationReportCollectionRecommendationReport = em.merge(oldHodIDOfRecommendationReportCollectionRecommendationReport);
                }
            }
            for (RefereeReport refereeReportCollectionRefereeReport : person.getRefereeReportCollection()) {
                Person oldRefereeIDOfRefereeReportCollectionRefereeReport = refereeReportCollectionRefereeReport.getRefereeID();
                refereeReportCollectionRefereeReport.setRefereeID(person);
                refereeReportCollectionRefereeReport = em.merge(refereeReportCollectionRefereeReport);
                if (oldRefereeIDOfRefereeReportCollectionRefereeReport != null) {
                    oldRefereeIDOfRefereeReportCollectionRefereeReport.getRefereeReportCollection().remove(refereeReportCollectionRefereeReport);
                    oldRefereeIDOfRefereeReportCollectionRefereeReport = em.merge(oldRefereeIDOfRefereeReportCollectionRefereeReport);
                }
            }
            for (FundingReport fundingReportCollectionFundingReport : person.getFundingReportCollection()) {
                Person oldDrisIDOfFundingReportCollectionFundingReport = fundingReportCollectionFundingReport.getDrisID();
                fundingReportCollectionFundingReport.setDrisID(person);
                fundingReportCollectionFundingReport = em.merge(fundingReportCollectionFundingReport);
                if (oldDrisIDOfFundingReportCollectionFundingReport != null) {
                    oldDrisIDOfFundingReportCollectionFundingReport.getFundingReportCollection().remove(fundingReportCollectionFundingReport);
                    oldDrisIDOfFundingReportCollectionFundingReport = em.merge(oldDrisIDOfFundingReportCollectionFundingReport);
                }
            }
            for (Notification notificationCollectionNotification : person.getNotificationCollection()) {
                Person oldSenderIDOfNotificationCollectionNotification = notificationCollectionNotification.getSenderID();
                notificationCollectionNotification.setSenderID(person);
                notificationCollectionNotification = em.merge(notificationCollectionNotification);
                if (oldSenderIDOfNotificationCollectionNotification != null) {
                    oldSenderIDOfNotificationCollectionNotification.getNotificationCollection().remove(notificationCollectionNotification);
                    oldSenderIDOfNotificationCollectionNotification = em.merge(oldSenderIDOfNotificationCollectionNotification);
                }
            }
            for (Notification notificationCollection1Notification : person.getNotificationCollection1()) {
                Person oldRecieverIDOfNotificationCollection1Notification = notificationCollection1Notification.getRecieverID();
                notificationCollection1Notification.setRecieverID(person);
                notificationCollection1Notification = em.merge(notificationCollection1Notification);
                if (oldRecieverIDOfNotificationCollection1Notification != null) {
                    oldRecieverIDOfNotificationCollection1Notification.getNotificationCollection1().remove(notificationCollection1Notification);
                    oldRecieverIDOfNotificationCollection1Notification = em.merge(oldRecieverIDOfNotificationCollection1Notification);
                }
            }
            for (Cv cvCollectionCv : person.getCvCollection()) {
                Person oldOwnerIDOfCvCollectionCv = cvCollectionCv.getOwnerID();
                cvCollectionCv.setOwnerID(person);
                cvCollectionCv = em.merge(cvCollectionCv);
                if (oldOwnerIDOfCvCollectionCv != null) {
                    oldOwnerIDOfCvCollectionCv.getCvCollection().remove(cvCollectionCv);
                    oldOwnerIDOfCvCollectionCv = em.merge(oldOwnerIDOfCvCollectionCv);
                }
            }
            for (Application applicationCollectionApplication : person.getApplicationCollection()) {
                Person oldFellowOfApplicationCollectionApplication = applicationCollectionApplication.getFellow();
                applicationCollectionApplication.setFellow(person);
                applicationCollectionApplication = em.merge(applicationCollectionApplication);
                if (oldFellowOfApplicationCollectionApplication != null) {
                    oldFellowOfApplicationCollectionApplication.getApplicationCollection().remove(applicationCollectionApplication);
                    oldFellowOfApplicationCollectionApplication = em.merge(oldFellowOfApplicationCollectionApplication);
                }
            }
            for (Application applicationCollection1Application : person.getApplicationCollection1()) {
                Person oldGrantHolderIDOfApplicationCollection1Application = applicationCollection1Application.getGrantHolderID();
                applicationCollection1Application.setGrantHolderID(person);
                applicationCollection1Application = em.merge(applicationCollection1Application);
                if (oldGrantHolderIDOfApplicationCollection1Application != null) {
                    oldGrantHolderIDOfApplicationCollection1Application.getApplicationCollection1().remove(applicationCollection1Application);
                    oldGrantHolderIDOfApplicationCollection1Application = em.merge(oldGrantHolderIDOfApplicationCollection1Application);
                }
            }
            for (MinuteComment minuteCommentCollectionMinuteComment : person.getMinuteCommentCollection()) {
                Person oldAttendeeIDOfMinuteCommentCollectionMinuteComment = minuteCommentCollectionMinuteComment.getAttendeeID();
                minuteCommentCollectionMinuteComment.setAttendeeID(person);
                minuteCommentCollectionMinuteComment = em.merge(minuteCommentCollectionMinuteComment);
                if (oldAttendeeIDOfMinuteCommentCollectionMinuteComment != null) {
                    oldAttendeeIDOfMinuteCommentCollectionMinuteComment.getMinuteCommentCollection().remove(minuteCommentCollectionMinuteComment);
                    oldAttendeeIDOfMinuteCommentCollectionMinuteComment = em.merge(oldAttendeeIDOfMinuteCommentCollectionMinuteComment);
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
            Collection<CommitteeMeeting> committeeMeetingCollectionOld = persistentPerson.getCommitteeMeetingCollection();
            Collection<CommitteeMeeting> committeeMeetingCollectionNew = person.getCommitteeMeetingCollection();
            Collection<SecurityRole> securityRoleCollectionOld = persistentPerson.getSecurityRoleCollection();
            Collection<SecurityRole> securityRoleCollectionNew = person.getSecurityRoleCollection();
            Collection<AuditLog> auditLogCollectionOld = persistentPerson.getAuditLogCollection();
            Collection<AuditLog> auditLogCollectionNew = person.getAuditLogCollection();
            Collection<Endorsement> endorsementCollectionOld = persistentPerson.getEndorsementCollection();
            Collection<Endorsement> endorsementCollectionNew = person.getEndorsementCollection();
            Collection<RecommendationReport> recommendationReportCollectionOld = persistentPerson.getRecommendationReportCollection();
            Collection<RecommendationReport> recommendationReportCollectionNew = person.getRecommendationReportCollection();
            Collection<RefereeReport> refereeReportCollectionOld = persistentPerson.getRefereeReportCollection();
            Collection<RefereeReport> refereeReportCollectionNew = person.getRefereeReportCollection();
            Collection<FundingReport> fundingReportCollectionOld = persistentPerson.getFundingReportCollection();
            Collection<FundingReport> fundingReportCollectionNew = person.getFundingReportCollection();
            Collection<Notification> notificationCollectionOld = persistentPerson.getNotificationCollection();
            Collection<Notification> notificationCollectionNew = person.getNotificationCollection();
            Collection<Notification> notificationCollection1Old = persistentPerson.getNotificationCollection1();
            Collection<Notification> notificationCollection1New = person.getNotificationCollection1();
            Collection<Cv> cvCollectionOld = persistentPerson.getCvCollection();
            Collection<Cv> cvCollectionNew = person.getCvCollection();
            Collection<Application> applicationCollectionOld = persistentPerson.getApplicationCollection();
            Collection<Application> applicationCollectionNew = person.getApplicationCollection();
            Collection<Application> applicationCollection1Old = persistentPerson.getApplicationCollection1();
            Collection<Application> applicationCollection1New = person.getApplicationCollection1();
            Collection<MinuteComment> minuteCommentCollectionOld = persistentPerson.getMinuteCommentCollection();
            Collection<MinuteComment> minuteCommentCollectionNew = person.getMinuteCommentCollection();
            List<String> illegalOrphanMessages = null;
            if (upEmployeeInformationOld != null && !upEmployeeInformationOld.equals(upEmployeeInformationNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain UpEmployeeInformation " + upEmployeeInformationOld + " since its person field is not nullable.");
            }
            for (AuditLog auditLogCollectionOldAuditLog : auditLogCollectionOld) {
                if (!auditLogCollectionNew.contains(auditLogCollectionOldAuditLog)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AuditLog " + auditLogCollectionOldAuditLog + " since its personID field is not nullable.");
                }
            }
            for (Endorsement endorsementCollectionOldEndorsement : endorsementCollectionOld) {
                if (!endorsementCollectionNew.contains(endorsementCollectionOldEndorsement)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Endorsement " + endorsementCollectionOldEndorsement + " since its deanID field is not nullable.");
                }
            }
            for (RecommendationReport recommendationReportCollectionOldRecommendationReport : recommendationReportCollectionOld) {
                if (!recommendationReportCollectionNew.contains(recommendationReportCollectionOldRecommendationReport)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecommendationReport " + recommendationReportCollectionOldRecommendationReport + " since its hodID field is not nullable.");
                }
            }
            for (RefereeReport refereeReportCollectionOldRefereeReport : refereeReportCollectionOld) {
                if (!refereeReportCollectionNew.contains(refereeReportCollectionOldRefereeReport)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RefereeReport " + refereeReportCollectionOldRefereeReport + " since its refereeID field is not nullable.");
                }
            }
            for (FundingReport fundingReportCollectionOldFundingReport : fundingReportCollectionOld) {
                if (!fundingReportCollectionNew.contains(fundingReportCollectionOldFundingReport)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FundingReport " + fundingReportCollectionOldFundingReport + " since its drisID field is not nullable.");
                }
            }
            for (Notification notificationCollectionOldNotification : notificationCollectionOld) {
                if (!notificationCollectionNew.contains(notificationCollectionOldNotification)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notification " + notificationCollectionOldNotification + " since its senderID field is not nullable.");
                }
            }
            for (Notification notificationCollection1OldNotification : notificationCollection1Old) {
                if (!notificationCollection1New.contains(notificationCollection1OldNotification)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notification " + notificationCollection1OldNotification + " since its recieverID field is not nullable.");
                }
            }
            for (Cv cvCollectionOldCv : cvCollectionOld) {
                if (!cvCollectionNew.contains(cvCollectionOldCv)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cv " + cvCollectionOldCv + " since its ownerID field is not nullable.");
                }
            }
            for (Application applicationCollectionOldApplication : applicationCollectionOld) {
                if (!applicationCollectionNew.contains(applicationCollectionOldApplication)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Application " + applicationCollectionOldApplication + " since its fellow field is not nullable.");
                }
            }
            for (Application applicationCollection1OldApplication : applicationCollection1Old) {
                if (!applicationCollection1New.contains(applicationCollection1OldApplication)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Application " + applicationCollection1OldApplication + " since its grantHolderID field is not nullable.");
                }
            }
            for (MinuteComment minuteCommentCollectionOldMinuteComment : minuteCommentCollectionOld) {
                if (!minuteCommentCollectionNew.contains(minuteCommentCollectionOldMinuteComment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MinuteComment " + minuteCommentCollectionOldMinuteComment + " since its attendeeID field is not nullable.");
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
            Collection<CommitteeMeeting> attachedCommitteeMeetingCollectionNew = new ArrayList<CommitteeMeeting>();
            for (CommitteeMeeting committeeMeetingCollectionNewCommitteeMeetingToAttach : committeeMeetingCollectionNew) {
                committeeMeetingCollectionNewCommitteeMeetingToAttach = em.getReference(committeeMeetingCollectionNewCommitteeMeetingToAttach.getClass(), committeeMeetingCollectionNewCommitteeMeetingToAttach.getMeetingID());
                attachedCommitteeMeetingCollectionNew.add(committeeMeetingCollectionNewCommitteeMeetingToAttach);
            }
            committeeMeetingCollectionNew = attachedCommitteeMeetingCollectionNew;
            person.setCommitteeMeetingCollection(committeeMeetingCollectionNew);
            Collection<SecurityRole> attachedSecurityRoleCollectionNew = new ArrayList<SecurityRole>();
            for (SecurityRole securityRoleCollectionNewSecurityRoleToAttach : securityRoleCollectionNew) {
                securityRoleCollectionNewSecurityRoleToAttach = em.getReference(securityRoleCollectionNewSecurityRoleToAttach.getClass(), securityRoleCollectionNewSecurityRoleToAttach.getRoleID());
                attachedSecurityRoleCollectionNew.add(securityRoleCollectionNewSecurityRoleToAttach);
            }
            securityRoleCollectionNew = attachedSecurityRoleCollectionNew;
            person.setSecurityRoleCollection(securityRoleCollectionNew);
            Collection<AuditLog> attachedAuditLogCollectionNew = new ArrayList<AuditLog>();
            for (AuditLog auditLogCollectionNewAuditLogToAttach : auditLogCollectionNew) {
                auditLogCollectionNewAuditLogToAttach = em.getReference(auditLogCollectionNewAuditLogToAttach.getClass(), auditLogCollectionNewAuditLogToAttach.getEntryID());
                attachedAuditLogCollectionNew.add(auditLogCollectionNewAuditLogToAttach);
            }
            auditLogCollectionNew = attachedAuditLogCollectionNew;
            person.setAuditLogCollection(auditLogCollectionNew);
            Collection<Endorsement> attachedEndorsementCollectionNew = new ArrayList<Endorsement>();
            for (Endorsement endorsementCollectionNewEndorsementToAttach : endorsementCollectionNew) {
                endorsementCollectionNewEndorsementToAttach = em.getReference(endorsementCollectionNewEndorsementToAttach.getClass(), endorsementCollectionNewEndorsementToAttach.getEndorsementID());
                attachedEndorsementCollectionNew.add(endorsementCollectionNewEndorsementToAttach);
            }
            endorsementCollectionNew = attachedEndorsementCollectionNew;
            person.setEndorsementCollection(endorsementCollectionNew);
            Collection<RecommendationReport> attachedRecommendationReportCollectionNew = new ArrayList<RecommendationReport>();
            for (RecommendationReport recommendationReportCollectionNewRecommendationReportToAttach : recommendationReportCollectionNew) {
                recommendationReportCollectionNewRecommendationReportToAttach = em.getReference(recommendationReportCollectionNewRecommendationReportToAttach.getClass(), recommendationReportCollectionNewRecommendationReportToAttach.getReportID());
                attachedRecommendationReportCollectionNew.add(recommendationReportCollectionNewRecommendationReportToAttach);
            }
            recommendationReportCollectionNew = attachedRecommendationReportCollectionNew;
            person.setRecommendationReportCollection(recommendationReportCollectionNew);
            Collection<RefereeReport> attachedRefereeReportCollectionNew = new ArrayList<RefereeReport>();
            for (RefereeReport refereeReportCollectionNewRefereeReportToAttach : refereeReportCollectionNew) {
                refereeReportCollectionNewRefereeReportToAttach = em.getReference(refereeReportCollectionNewRefereeReportToAttach.getClass(), refereeReportCollectionNewRefereeReportToAttach.getReportID());
                attachedRefereeReportCollectionNew.add(refereeReportCollectionNewRefereeReportToAttach);
            }
            refereeReportCollectionNew = attachedRefereeReportCollectionNew;
            person.setRefereeReportCollection(refereeReportCollectionNew);
            Collection<FundingReport> attachedFundingReportCollectionNew = new ArrayList<FundingReport>();
            for (FundingReport fundingReportCollectionNewFundingReportToAttach : fundingReportCollectionNew) {
                fundingReportCollectionNewFundingReportToAttach = em.getReference(fundingReportCollectionNewFundingReportToAttach.getClass(), fundingReportCollectionNewFundingReportToAttach.getReportID());
                attachedFundingReportCollectionNew.add(fundingReportCollectionNewFundingReportToAttach);
            }
            fundingReportCollectionNew = attachedFundingReportCollectionNew;
            person.setFundingReportCollection(fundingReportCollectionNew);
            Collection<Notification> attachedNotificationCollectionNew = new ArrayList<Notification>();
            for (Notification notificationCollectionNewNotificationToAttach : notificationCollectionNew) {
                notificationCollectionNewNotificationToAttach = em.getReference(notificationCollectionNewNotificationToAttach.getClass(), notificationCollectionNewNotificationToAttach.getNotificationID());
                attachedNotificationCollectionNew.add(notificationCollectionNewNotificationToAttach);
            }
            notificationCollectionNew = attachedNotificationCollectionNew;
            person.setNotificationCollection(notificationCollectionNew);
            Collection<Notification> attachedNotificationCollection1New = new ArrayList<Notification>();
            for (Notification notificationCollection1NewNotificationToAttach : notificationCollection1New) {
                notificationCollection1NewNotificationToAttach = em.getReference(notificationCollection1NewNotificationToAttach.getClass(), notificationCollection1NewNotificationToAttach.getNotificationID());
                attachedNotificationCollection1New.add(notificationCollection1NewNotificationToAttach);
            }
            notificationCollection1New = attachedNotificationCollection1New;
            person.setNotificationCollection1(notificationCollection1New);
            Collection<Cv> attachedCvCollectionNew = new ArrayList<Cv>();
            for (Cv cvCollectionNewCvToAttach : cvCollectionNew) {
                cvCollectionNewCvToAttach = em.getReference(cvCollectionNewCvToAttach.getClass(), cvCollectionNewCvToAttach.getCvID());
                attachedCvCollectionNew.add(cvCollectionNewCvToAttach);
            }
            cvCollectionNew = attachedCvCollectionNew;
            person.setCvCollection(cvCollectionNew);
            Collection<Application> attachedApplicationCollectionNew = new ArrayList<Application>();
            for (Application applicationCollectionNewApplicationToAttach : applicationCollectionNew) {
                applicationCollectionNewApplicationToAttach = em.getReference(applicationCollectionNewApplicationToAttach.getClass(), applicationCollectionNewApplicationToAttach.getApplicationID());
                attachedApplicationCollectionNew.add(applicationCollectionNewApplicationToAttach);
            }
            applicationCollectionNew = attachedApplicationCollectionNew;
            person.setApplicationCollection(applicationCollectionNew);
            Collection<Application> attachedApplicationCollection1New = new ArrayList<Application>();
            for (Application applicationCollection1NewApplicationToAttach : applicationCollection1New) {
                applicationCollection1NewApplicationToAttach = em.getReference(applicationCollection1NewApplicationToAttach.getClass(), applicationCollection1NewApplicationToAttach.getApplicationID());
                attachedApplicationCollection1New.add(applicationCollection1NewApplicationToAttach);
            }
            applicationCollection1New = attachedApplicationCollection1New;
            person.setApplicationCollection1(applicationCollection1New);
            Collection<MinuteComment> attachedMinuteCommentCollectionNew = new ArrayList<MinuteComment>();
            for (MinuteComment minuteCommentCollectionNewMinuteCommentToAttach : minuteCommentCollectionNew) {
                minuteCommentCollectionNewMinuteCommentToAttach = em.getReference(minuteCommentCollectionNewMinuteCommentToAttach.getClass(), minuteCommentCollectionNewMinuteCommentToAttach.getCommentID());
                attachedMinuteCommentCollectionNew.add(minuteCommentCollectionNewMinuteCommentToAttach);
            }
            minuteCommentCollectionNew = attachedMinuteCommentCollectionNew;
            person.setMinuteCommentCollection(minuteCommentCollectionNew);
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
                locationIDOld.getPersonCollection().remove(person);
                locationIDOld = em.merge(locationIDOld);
            }
            if (locationIDNew != null && !locationIDNew.equals(locationIDOld)) {
                locationIDNew.getPersonCollection().add(person);
                locationIDNew = em.merge(locationIDNew);
            }
            if (addressLine1Old != null && !addressLine1Old.equals(addressLine1New)) {
                addressLine1Old.getPersonCollection().remove(person);
                addressLine1Old = em.merge(addressLine1Old);
            }
            if (addressLine1New != null && !addressLine1New.equals(addressLine1Old)) {
                addressLine1New.getPersonCollection().add(person);
                addressLine1New = em.merge(addressLine1New);
            }
            for (CommitteeMeeting committeeMeetingCollectionOldCommitteeMeeting : committeeMeetingCollectionOld) {
                if (!committeeMeetingCollectionNew.contains(committeeMeetingCollectionOldCommitteeMeeting)) {
                    committeeMeetingCollectionOldCommitteeMeeting.getPersonCollection().remove(person);
                    committeeMeetingCollectionOldCommitteeMeeting = em.merge(committeeMeetingCollectionOldCommitteeMeeting);
                }
            }
            for (CommitteeMeeting committeeMeetingCollectionNewCommitteeMeeting : committeeMeetingCollectionNew) {
                if (!committeeMeetingCollectionOld.contains(committeeMeetingCollectionNewCommitteeMeeting)) {
                    committeeMeetingCollectionNewCommitteeMeeting.getPersonCollection().add(person);
                    committeeMeetingCollectionNewCommitteeMeeting = em.merge(committeeMeetingCollectionNewCommitteeMeeting);
                }
            }
            for (SecurityRole securityRoleCollectionOldSecurityRole : securityRoleCollectionOld) {
                if (!securityRoleCollectionNew.contains(securityRoleCollectionOldSecurityRole)) {
                    securityRoleCollectionOldSecurityRole.getPersonCollection().remove(person);
                    securityRoleCollectionOldSecurityRole = em.merge(securityRoleCollectionOldSecurityRole);
                }
            }
            for (SecurityRole securityRoleCollectionNewSecurityRole : securityRoleCollectionNew) {
                if (!securityRoleCollectionOld.contains(securityRoleCollectionNewSecurityRole)) {
                    securityRoleCollectionNewSecurityRole.getPersonCollection().add(person);
                    securityRoleCollectionNewSecurityRole = em.merge(securityRoleCollectionNewSecurityRole);
                }
            }
            for (AuditLog auditLogCollectionNewAuditLog : auditLogCollectionNew) {
                if (!auditLogCollectionOld.contains(auditLogCollectionNewAuditLog)) {
                    Person oldPersonIDOfAuditLogCollectionNewAuditLog = auditLogCollectionNewAuditLog.getPersonID();
                    auditLogCollectionNewAuditLog.setPersonID(person);
                    auditLogCollectionNewAuditLog = em.merge(auditLogCollectionNewAuditLog);
                    if (oldPersonIDOfAuditLogCollectionNewAuditLog != null && !oldPersonIDOfAuditLogCollectionNewAuditLog.equals(person)) {
                        oldPersonIDOfAuditLogCollectionNewAuditLog.getAuditLogCollection().remove(auditLogCollectionNewAuditLog);
                        oldPersonIDOfAuditLogCollectionNewAuditLog = em.merge(oldPersonIDOfAuditLogCollectionNewAuditLog);
                    }
                }
            }
            for (Endorsement endorsementCollectionNewEndorsement : endorsementCollectionNew) {
                if (!endorsementCollectionOld.contains(endorsementCollectionNewEndorsement)) {
                    Person oldDeanIDOfEndorsementCollectionNewEndorsement = endorsementCollectionNewEndorsement.getDeanID();
                    endorsementCollectionNewEndorsement.setDeanID(person);
                    endorsementCollectionNewEndorsement = em.merge(endorsementCollectionNewEndorsement);
                    if (oldDeanIDOfEndorsementCollectionNewEndorsement != null && !oldDeanIDOfEndorsementCollectionNewEndorsement.equals(person)) {
                        oldDeanIDOfEndorsementCollectionNewEndorsement.getEndorsementCollection().remove(endorsementCollectionNewEndorsement);
                        oldDeanIDOfEndorsementCollectionNewEndorsement = em.merge(oldDeanIDOfEndorsementCollectionNewEndorsement);
                    }
                }
            }
            for (RecommendationReport recommendationReportCollectionNewRecommendationReport : recommendationReportCollectionNew) {
                if (!recommendationReportCollectionOld.contains(recommendationReportCollectionNewRecommendationReport)) {
                    Person oldHodIDOfRecommendationReportCollectionNewRecommendationReport = recommendationReportCollectionNewRecommendationReport.getHodID();
                    recommendationReportCollectionNewRecommendationReport.setHodID(person);
                    recommendationReportCollectionNewRecommendationReport = em.merge(recommendationReportCollectionNewRecommendationReport);
                    if (oldHodIDOfRecommendationReportCollectionNewRecommendationReport != null && !oldHodIDOfRecommendationReportCollectionNewRecommendationReport.equals(person)) {
                        oldHodIDOfRecommendationReportCollectionNewRecommendationReport.getRecommendationReportCollection().remove(recommendationReportCollectionNewRecommendationReport);
                        oldHodIDOfRecommendationReportCollectionNewRecommendationReport = em.merge(oldHodIDOfRecommendationReportCollectionNewRecommendationReport);
                    }
                }
            }
            for (RefereeReport refereeReportCollectionNewRefereeReport : refereeReportCollectionNew) {
                if (!refereeReportCollectionOld.contains(refereeReportCollectionNewRefereeReport)) {
                    Person oldRefereeIDOfRefereeReportCollectionNewRefereeReport = refereeReportCollectionNewRefereeReport.getRefereeID();
                    refereeReportCollectionNewRefereeReport.setRefereeID(person);
                    refereeReportCollectionNewRefereeReport = em.merge(refereeReportCollectionNewRefereeReport);
                    if (oldRefereeIDOfRefereeReportCollectionNewRefereeReport != null && !oldRefereeIDOfRefereeReportCollectionNewRefereeReport.equals(person)) {
                        oldRefereeIDOfRefereeReportCollectionNewRefereeReport.getRefereeReportCollection().remove(refereeReportCollectionNewRefereeReport);
                        oldRefereeIDOfRefereeReportCollectionNewRefereeReport = em.merge(oldRefereeIDOfRefereeReportCollectionNewRefereeReport);
                    }
                }
            }
            for (FundingReport fundingReportCollectionNewFundingReport : fundingReportCollectionNew) {
                if (!fundingReportCollectionOld.contains(fundingReportCollectionNewFundingReport)) {
                    Person oldDrisIDOfFundingReportCollectionNewFundingReport = fundingReportCollectionNewFundingReport.getDrisID();
                    fundingReportCollectionNewFundingReport.setDrisID(person);
                    fundingReportCollectionNewFundingReport = em.merge(fundingReportCollectionNewFundingReport);
                    if (oldDrisIDOfFundingReportCollectionNewFundingReport != null && !oldDrisIDOfFundingReportCollectionNewFundingReport.equals(person)) {
                        oldDrisIDOfFundingReportCollectionNewFundingReport.getFundingReportCollection().remove(fundingReportCollectionNewFundingReport);
                        oldDrisIDOfFundingReportCollectionNewFundingReport = em.merge(oldDrisIDOfFundingReportCollectionNewFundingReport);
                    }
                }
            }
            for (Notification notificationCollectionNewNotification : notificationCollectionNew) {
                if (!notificationCollectionOld.contains(notificationCollectionNewNotification)) {
                    Person oldSenderIDOfNotificationCollectionNewNotification = notificationCollectionNewNotification.getSenderID();
                    notificationCollectionNewNotification.setSenderID(person);
                    notificationCollectionNewNotification = em.merge(notificationCollectionNewNotification);
                    if (oldSenderIDOfNotificationCollectionNewNotification != null && !oldSenderIDOfNotificationCollectionNewNotification.equals(person)) {
                        oldSenderIDOfNotificationCollectionNewNotification.getNotificationCollection().remove(notificationCollectionNewNotification);
                        oldSenderIDOfNotificationCollectionNewNotification = em.merge(oldSenderIDOfNotificationCollectionNewNotification);
                    }
                }
            }
            for (Notification notificationCollection1NewNotification : notificationCollection1New) {
                if (!notificationCollection1Old.contains(notificationCollection1NewNotification)) {
                    Person oldRecieverIDOfNotificationCollection1NewNotification = notificationCollection1NewNotification.getRecieverID();
                    notificationCollection1NewNotification.setRecieverID(person);
                    notificationCollection1NewNotification = em.merge(notificationCollection1NewNotification);
                    if (oldRecieverIDOfNotificationCollection1NewNotification != null && !oldRecieverIDOfNotificationCollection1NewNotification.equals(person)) {
                        oldRecieverIDOfNotificationCollection1NewNotification.getNotificationCollection1().remove(notificationCollection1NewNotification);
                        oldRecieverIDOfNotificationCollection1NewNotification = em.merge(oldRecieverIDOfNotificationCollection1NewNotification);
                    }
                }
            }
            for (Cv cvCollectionNewCv : cvCollectionNew) {
                if (!cvCollectionOld.contains(cvCollectionNewCv)) {
                    Person oldOwnerIDOfCvCollectionNewCv = cvCollectionNewCv.getOwnerID();
                    cvCollectionNewCv.setOwnerID(person);
                    cvCollectionNewCv = em.merge(cvCollectionNewCv);
                    if (oldOwnerIDOfCvCollectionNewCv != null && !oldOwnerIDOfCvCollectionNewCv.equals(person)) {
                        oldOwnerIDOfCvCollectionNewCv.getCvCollection().remove(cvCollectionNewCv);
                        oldOwnerIDOfCvCollectionNewCv = em.merge(oldOwnerIDOfCvCollectionNewCv);
                    }
                }
            }
            for (Application applicationCollectionNewApplication : applicationCollectionNew) {
                if (!applicationCollectionOld.contains(applicationCollectionNewApplication)) {
                    Person oldFellowOfApplicationCollectionNewApplication = applicationCollectionNewApplication.getFellow();
                    applicationCollectionNewApplication.setFellow(person);
                    applicationCollectionNewApplication = em.merge(applicationCollectionNewApplication);
                    if (oldFellowOfApplicationCollectionNewApplication != null && !oldFellowOfApplicationCollectionNewApplication.equals(person)) {
                        oldFellowOfApplicationCollectionNewApplication.getApplicationCollection().remove(applicationCollectionNewApplication);
                        oldFellowOfApplicationCollectionNewApplication = em.merge(oldFellowOfApplicationCollectionNewApplication);
                    }
                }
            }
            for (Application applicationCollection1NewApplication : applicationCollection1New) {
                if (!applicationCollection1Old.contains(applicationCollection1NewApplication)) {
                    Person oldGrantHolderIDOfApplicationCollection1NewApplication = applicationCollection1NewApplication.getGrantHolderID();
                    applicationCollection1NewApplication.setGrantHolderID(person);
                    applicationCollection1NewApplication = em.merge(applicationCollection1NewApplication);
                    if (oldGrantHolderIDOfApplicationCollection1NewApplication != null && !oldGrantHolderIDOfApplicationCollection1NewApplication.equals(person)) {
                        oldGrantHolderIDOfApplicationCollection1NewApplication.getApplicationCollection1().remove(applicationCollection1NewApplication);
                        oldGrantHolderIDOfApplicationCollection1NewApplication = em.merge(oldGrantHolderIDOfApplicationCollection1NewApplication);
                    }
                }
            }
            for (MinuteComment minuteCommentCollectionNewMinuteComment : minuteCommentCollectionNew) {
                if (!minuteCommentCollectionOld.contains(minuteCommentCollectionNewMinuteComment)) {
                    Person oldAttendeeIDOfMinuteCommentCollectionNewMinuteComment = minuteCommentCollectionNewMinuteComment.getAttendeeID();
                    minuteCommentCollectionNewMinuteComment.setAttendeeID(person);
                    minuteCommentCollectionNewMinuteComment = em.merge(minuteCommentCollectionNewMinuteComment);
                    if (oldAttendeeIDOfMinuteCommentCollectionNewMinuteComment != null && !oldAttendeeIDOfMinuteCommentCollectionNewMinuteComment.equals(person)) {
                        oldAttendeeIDOfMinuteCommentCollectionNewMinuteComment.getMinuteCommentCollection().remove(minuteCommentCollectionNewMinuteComment);
                        oldAttendeeIDOfMinuteCommentCollectionNewMinuteComment = em.merge(oldAttendeeIDOfMinuteCommentCollectionNewMinuteComment);
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
            Collection<AuditLog> auditLogCollectionOrphanCheck = person.getAuditLogCollection();
            for (AuditLog auditLogCollectionOrphanCheckAuditLog : auditLogCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the AuditLog " + auditLogCollectionOrphanCheckAuditLog + " in its auditLogCollection field has a non-nullable personID field.");
            }
            Collection<Endorsement> endorsementCollectionOrphanCheck = person.getEndorsementCollection();
            for (Endorsement endorsementCollectionOrphanCheckEndorsement : endorsementCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Endorsement " + endorsementCollectionOrphanCheckEndorsement + " in its endorsementCollection field has a non-nullable deanID field.");
            }
            Collection<RecommendationReport> recommendationReportCollectionOrphanCheck = person.getRecommendationReportCollection();
            for (RecommendationReport recommendationReportCollectionOrphanCheckRecommendationReport : recommendationReportCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the RecommendationReport " + recommendationReportCollectionOrphanCheckRecommendationReport + " in its recommendationReportCollection field has a non-nullable hodID field.");
            }
            Collection<RefereeReport> refereeReportCollectionOrphanCheck = person.getRefereeReportCollection();
            for (RefereeReport refereeReportCollectionOrphanCheckRefereeReport : refereeReportCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the RefereeReport " + refereeReportCollectionOrphanCheckRefereeReport + " in its refereeReportCollection field has a non-nullable refereeID field.");
            }
            Collection<FundingReport> fundingReportCollectionOrphanCheck = person.getFundingReportCollection();
            for (FundingReport fundingReportCollectionOrphanCheckFundingReport : fundingReportCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the FundingReport " + fundingReportCollectionOrphanCheckFundingReport + " in its fundingReportCollection field has a non-nullable drisID field.");
            }
            Collection<Notification> notificationCollectionOrphanCheck = person.getNotificationCollection();
            for (Notification notificationCollectionOrphanCheckNotification : notificationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Notification " + notificationCollectionOrphanCheckNotification + " in its notificationCollection field has a non-nullable senderID field.");
            }
            Collection<Notification> notificationCollection1OrphanCheck = person.getNotificationCollection1();
            for (Notification notificationCollection1OrphanCheckNotification : notificationCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Notification " + notificationCollection1OrphanCheckNotification + " in its notificationCollection1 field has a non-nullable recieverID field.");
            }
            Collection<Cv> cvCollectionOrphanCheck = person.getCvCollection();
            for (Cv cvCollectionOrphanCheckCv : cvCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Cv " + cvCollectionOrphanCheckCv + " in its cvCollection field has a non-nullable ownerID field.");
            }
            Collection<Application> applicationCollectionOrphanCheck = person.getApplicationCollection();
            for (Application applicationCollectionOrphanCheckApplication : applicationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Application " + applicationCollectionOrphanCheckApplication + " in its applicationCollection field has a non-nullable fellow field.");
            }
            Collection<Application> applicationCollection1OrphanCheck = person.getApplicationCollection1();
            for (Application applicationCollection1OrphanCheckApplication : applicationCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Application " + applicationCollection1OrphanCheckApplication + " in its applicationCollection1 field has a non-nullable grantHolderID field.");
            }
            Collection<MinuteComment> minuteCommentCollectionOrphanCheck = person.getMinuteCommentCollection();
            for (MinuteComment minuteCommentCollectionOrphanCheckMinuteComment : minuteCommentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the MinuteComment " + minuteCommentCollectionOrphanCheckMinuteComment + " in its minuteCommentCollection field has a non-nullable attendeeID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Location locationID = person.getLocationID();
            if (locationID != null) {
                locationID.getPersonCollection().remove(person);
                locationID = em.merge(locationID);
            }
            Address addressLine1 = person.getAddressLine1();
            if (addressLine1 != null) {
                addressLine1.getPersonCollection().remove(person);
                addressLine1 = em.merge(addressLine1);
            }
            Collection<CommitteeMeeting> committeeMeetingCollection = person.getCommitteeMeetingCollection();
            for (CommitteeMeeting committeeMeetingCollectionCommitteeMeeting : committeeMeetingCollection) {
                committeeMeetingCollectionCommitteeMeeting.getPersonCollection().remove(person);
                committeeMeetingCollectionCommitteeMeeting = em.merge(committeeMeetingCollectionCommitteeMeeting);
            }
            Collection<SecurityRole> securityRoleCollection = person.getSecurityRoleCollection();
            for (SecurityRole securityRoleCollectionSecurityRole : securityRoleCollection) {
                securityRoleCollectionSecurityRole.getPersonCollection().remove(person);
                securityRoleCollectionSecurityRole = em.merge(securityRoleCollectionSecurityRole);
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
    
}
