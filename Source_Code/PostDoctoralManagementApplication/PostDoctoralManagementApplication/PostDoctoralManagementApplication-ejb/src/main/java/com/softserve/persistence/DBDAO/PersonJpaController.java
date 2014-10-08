/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.persistence.DBEntities.ResearchFellowInformation;
import com.softserve.persistence.DBEntities.EmployeeInformation;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.Address;
import com.softserve.persistence.DBEntities.SecurityRole;
import java.util.ArrayList;
import java.util.List;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.CommitteeMeeting;
import com.softserve.persistence.DBEntities.DeclineReport;
import com.softserve.persistence.DBEntities.Endorsement;
import com.softserve.persistence.DBEntities.ApplicationReviewRequest;
import com.softserve.persistence.DBEntities.RefereeReport;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.AmmendRequest;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.RecommendationReport;
import com.softserve.persistence.DBEntities.FundingReport;
import com.softserve.persistence.DBEntities.MinuteComment;
import com.softserve.persistence.DBEntities.EligiblityReport;
import com.softserve.persistence.DBEntities.ForwardAndRewindReport;
import com.softserve.persistence.DBEntities.Person;
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

    public PersonJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(Person person) throws PreexistingEntityException, RollbackFailureException, Exception 
    {
        create(getEntityManager(), person);
    }

    public void create(EntityManager em, Person person) throws PreexistingEntityException, RollbackFailureException, Exception 
    {
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

        em.persist(person);   
    }
    
    public Person edit(Person person) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), person);
    }

    public Person edit(EntityManager em, Person person) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

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
        
        return em.merge(person);

    }
    
    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

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
            
        }
    }

    public Person findPerson(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            
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
