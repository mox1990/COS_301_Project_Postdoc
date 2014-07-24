/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.Endorsement;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.RecommendationReport;
import com.softserve.DBEntities.RefereeReport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ApplicationJpaController implements Serializable {

    public ApplicationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Application application) throws RollbackFailureException, Exception {
        if (application.getPersonList() == null) {
            application.setPersonList(new ArrayList<Person>());
        }
        if (application.getCommitteeMeetingList() == null) {
            application.setCommitteeMeetingList(new ArrayList<CommitteeMeeting>());
        }
        if (application.getRefereeReportList() == null) {
            application.setRefereeReportList(new ArrayList<RefereeReport>());
        }
        if (application.getProgressReportList() == null) {
            application.setProgressReportList(new ArrayList<ProgressReport>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Endorsement endorsement = application.getEndorsement();
            if (endorsement != null) {
                endorsement = em.getReference(endorsement.getClass(), endorsement.getEndorsementID());
                application.setEndorsement(endorsement);
            }
            RecommendationReport recommendationReport = application.getRecommendationReport();
            if (recommendationReport != null) {
                recommendationReport = em.getReference(recommendationReport.getClass(), recommendationReport.getReportID());
                application.setRecommendationReport(recommendationReport);
            }
            FundingReport fundingReport = application.getFundingReport();
            if (fundingReport != null) {
                fundingReport = em.getReference(fundingReport.getClass(), fundingReport.getReportID());
                application.setFundingReport(fundingReport);
            }
            Person fellow = application.getFellow();
            if (fellow != null) {
                fellow = em.getReference(fellow.getClass(), fellow.getSystemID());
                application.setFellow(fellow);
            }
            Person grantHolderID = application.getGrantHolderID();
            if (grantHolderID != null) {
                grantHolderID = em.getReference(grantHolderID.getClass(), grantHolderID.getSystemID());
                application.setGrantHolderID(grantHolderID);
            }
            List<Person> attachedPersonList = new ArrayList<Person>();
            for (Person personListPersonToAttach : application.getPersonList()) {
                personListPersonToAttach = em.getReference(personListPersonToAttach.getClass(), personListPersonToAttach.getSystemID());
                attachedPersonList.add(personListPersonToAttach);
            }
            application.setPersonList(attachedPersonList);
            List<CommitteeMeeting> attachedCommitteeMeetingList = new ArrayList<CommitteeMeeting>();
            for (CommitteeMeeting committeeMeetingListCommitteeMeetingToAttach : application.getCommitteeMeetingList()) {
                committeeMeetingListCommitteeMeetingToAttach = em.getReference(committeeMeetingListCommitteeMeetingToAttach.getClass(), committeeMeetingListCommitteeMeetingToAttach.getMeetingID());
                attachedCommitteeMeetingList.add(committeeMeetingListCommitteeMeetingToAttach);
            }
            application.setCommitteeMeetingList(attachedCommitteeMeetingList);
            List<RefereeReport> attachedRefereeReportList = new ArrayList<RefereeReport>();
            for (RefereeReport refereeReportListRefereeReportToAttach : application.getRefereeReportList()) {
                refereeReportListRefereeReportToAttach = em.getReference(refereeReportListRefereeReportToAttach.getClass(), refereeReportListRefereeReportToAttach.getReportID());
                attachedRefereeReportList.add(refereeReportListRefereeReportToAttach);
            }
            application.setRefereeReportList(attachedRefereeReportList);
            List<ProgressReport> attachedProgressReportList = new ArrayList<ProgressReport>();
            for (ProgressReport progressReportListProgressReportToAttach : application.getProgressReportList()) {
                progressReportListProgressReportToAttach = em.getReference(progressReportListProgressReportToAttach.getClass(), progressReportListProgressReportToAttach.getReportID());
                attachedProgressReportList.add(progressReportListProgressReportToAttach);
            }
            application.setProgressReportList(attachedProgressReportList);
            em.persist(application);
            if (endorsement != null) {
                Application oldApplicationOfEndorsement = endorsement.getApplication();
                if (oldApplicationOfEndorsement != null) {
                    oldApplicationOfEndorsement.setEndorsement(null);
                    oldApplicationOfEndorsement = em.merge(oldApplicationOfEndorsement);
                }
                endorsement.setApplication(application);
                endorsement = em.merge(endorsement);
            }
            if (recommendationReport != null) {
                Application oldApplicationOfRecommendationReport = recommendationReport.getApplication();
                if (oldApplicationOfRecommendationReport != null) {
                    oldApplicationOfRecommendationReport.setRecommendationReport(null);
                    oldApplicationOfRecommendationReport = em.merge(oldApplicationOfRecommendationReport);
                }
                recommendationReport.setApplication(application);
                recommendationReport = em.merge(recommendationReport);
            }
            if (fundingReport != null) {
                Application oldApplicationOfFundingReport = fundingReport.getApplication();
                if (oldApplicationOfFundingReport != null) {
                    oldApplicationOfFundingReport.setFundingReport(null);
                    oldApplicationOfFundingReport = em.merge(oldApplicationOfFundingReport);
                }
                fundingReport.setApplication(application);
                fundingReport = em.merge(fundingReport);
            }
            if (fellow != null) {
                fellow.getApplicationList().add(application);
                fellow = em.merge(fellow);
            }
            if (grantHolderID != null) {
                grantHolderID.getApplicationList().add(application);
                grantHolderID = em.merge(grantHolderID);
            }
            for (Person personListPerson : application.getPersonList()) {
                personListPerson.getApplicationList().add(application);
                personListPerson = em.merge(personListPerson);
            }
            for (CommitteeMeeting committeeMeetingListCommitteeMeeting : application.getCommitteeMeetingList()) {
                committeeMeetingListCommitteeMeeting.getApplicationList().add(application);
                committeeMeetingListCommitteeMeeting = em.merge(committeeMeetingListCommitteeMeeting);
            }
            for (RefereeReport refereeReportListRefereeReport : application.getRefereeReportList()) {
                Application oldApplicationIDOfRefereeReportListRefereeReport = refereeReportListRefereeReport.getApplicationID();
                refereeReportListRefereeReport.setApplicationID(application);
                refereeReportListRefereeReport = em.merge(refereeReportListRefereeReport);
                if (oldApplicationIDOfRefereeReportListRefereeReport != null) {
                    oldApplicationIDOfRefereeReportListRefereeReport.getRefereeReportList().remove(refereeReportListRefereeReport);
                    oldApplicationIDOfRefereeReportListRefereeReport = em.merge(oldApplicationIDOfRefereeReportListRefereeReport);
                }
            }
            for (ProgressReport progressReportListProgressReport : application.getProgressReportList()) {
                Application oldApplicationIDOfProgressReportListProgressReport = progressReportListProgressReport.getApplicationID();
                progressReportListProgressReport.setApplicationID(application);
                progressReportListProgressReport = em.merge(progressReportListProgressReport);
                if (oldApplicationIDOfProgressReportListProgressReport != null) {
                    oldApplicationIDOfProgressReportListProgressReport.getProgressReportList().remove(progressReportListProgressReport);
                    oldApplicationIDOfProgressReportListProgressReport = em.merge(oldApplicationIDOfProgressReportListProgressReport);
                }
            }
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

    public void edit(Application application) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Application persistentApplication = em.find(Application.class, application.getApplicationID());
            Endorsement endorsementOld = persistentApplication.getEndorsement();
            Endorsement endorsementNew = application.getEndorsement();
            RecommendationReport recommendationReportOld = persistentApplication.getRecommendationReport();
            RecommendationReport recommendationReportNew = application.getRecommendationReport();
            FundingReport fundingReportOld = persistentApplication.getFundingReport();
            FundingReport fundingReportNew = application.getFundingReport();
            Person fellowOld = persistentApplication.getFellow();
            Person fellowNew = application.getFellow();
            Person grantHolderIDOld = persistentApplication.getGrantHolderID();
            Person grantHolderIDNew = application.getGrantHolderID();
            List<Person> personListOld = persistentApplication.getPersonList();
            List<Person> personListNew = application.getPersonList();
            List<CommitteeMeeting> committeeMeetingListOld = persistentApplication.getCommitteeMeetingList();
            List<CommitteeMeeting> committeeMeetingListNew = application.getCommitteeMeetingList();
            List<RefereeReport> refereeReportListOld = persistentApplication.getRefereeReportList();
            List<RefereeReport> refereeReportListNew = application.getRefereeReportList();
            List<ProgressReport> progressReportListOld = persistentApplication.getProgressReportList();
            List<ProgressReport> progressReportListNew = application.getProgressReportList();
            List<String> illegalOrphanMessages = null;
            if (endorsementOld != null && !endorsementOld.equals(endorsementNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Endorsement " + endorsementOld + " since its application field is not nullable.");
            }
            if (recommendationReportOld != null && !recommendationReportOld.equals(recommendationReportNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain RecommendationReport " + recommendationReportOld + " since its application field is not nullable.");
            }
            if (fundingReportOld != null && !fundingReportOld.equals(fundingReportNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain FundingReport " + fundingReportOld + " since its application field is not nullable.");
            }
            for (RefereeReport refereeReportListOldRefereeReport : refereeReportListOld) {
                if (!refereeReportListNew.contains(refereeReportListOldRefereeReport)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RefereeReport " + refereeReportListOldRefereeReport + " since its applicationID field is not nullable.");
                }
            }
            for (ProgressReport progressReportListOldProgressReport : progressReportListOld) {
                if (!progressReportListNew.contains(progressReportListOldProgressReport)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProgressReport " + progressReportListOldProgressReport + " since its applicationID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (endorsementNew != null) {
                endorsementNew = em.getReference(endorsementNew.getClass(), endorsementNew.getEndorsementID());
                application.setEndorsement(endorsementNew);
            }
            if (recommendationReportNew != null) {
                recommendationReportNew = em.getReference(recommendationReportNew.getClass(), recommendationReportNew.getReportID());
                application.setRecommendationReport(recommendationReportNew);
            }
            if (fundingReportNew != null) {
                fundingReportNew = em.getReference(fundingReportNew.getClass(), fundingReportNew.getReportID());
                application.setFundingReport(fundingReportNew);
            }
            if (fellowNew != null) {
                fellowNew = em.getReference(fellowNew.getClass(), fellowNew.getSystemID());
                application.setFellow(fellowNew);
            }
            if (grantHolderIDNew != null) {
                grantHolderIDNew = em.getReference(grantHolderIDNew.getClass(), grantHolderIDNew.getSystemID());
                application.setGrantHolderID(grantHolderIDNew);
            }
            List<Person> attachedPersonListNew = new ArrayList<Person>();
            for (Person personListNewPersonToAttach : personListNew) {
                personListNewPersonToAttach = em.getReference(personListNewPersonToAttach.getClass(), personListNewPersonToAttach.getSystemID());
                attachedPersonListNew.add(personListNewPersonToAttach);
            }
            personListNew = attachedPersonListNew;
            application.setPersonList(personListNew);
            List<CommitteeMeeting> attachedCommitteeMeetingListNew = new ArrayList<CommitteeMeeting>();
            for (CommitteeMeeting committeeMeetingListNewCommitteeMeetingToAttach : committeeMeetingListNew) {
                committeeMeetingListNewCommitteeMeetingToAttach = em.getReference(committeeMeetingListNewCommitteeMeetingToAttach.getClass(), committeeMeetingListNewCommitteeMeetingToAttach.getMeetingID());
                attachedCommitteeMeetingListNew.add(committeeMeetingListNewCommitteeMeetingToAttach);
            }
            committeeMeetingListNew = attachedCommitteeMeetingListNew;
            application.setCommitteeMeetingList(committeeMeetingListNew);
            List<RefereeReport> attachedRefereeReportListNew = new ArrayList<RefereeReport>();
            for (RefereeReport refereeReportListNewRefereeReportToAttach : refereeReportListNew) {
                refereeReportListNewRefereeReportToAttach = em.getReference(refereeReportListNewRefereeReportToAttach.getClass(), refereeReportListNewRefereeReportToAttach.getReportID());
                attachedRefereeReportListNew.add(refereeReportListNewRefereeReportToAttach);
            }
            refereeReportListNew = attachedRefereeReportListNew;
            application.setRefereeReportList(refereeReportListNew);
            List<ProgressReport> attachedProgressReportListNew = new ArrayList<ProgressReport>();
            for (ProgressReport progressReportListNewProgressReportToAttach : progressReportListNew) {
                progressReportListNewProgressReportToAttach = em.getReference(progressReportListNewProgressReportToAttach.getClass(), progressReportListNewProgressReportToAttach.getReportID());
                attachedProgressReportListNew.add(progressReportListNewProgressReportToAttach);
            }
            progressReportListNew = attachedProgressReportListNew;
            application.setProgressReportList(progressReportListNew);
            application = em.merge(application);
            if (endorsementNew != null && !endorsementNew.equals(endorsementOld)) {
                Application oldApplicationOfEndorsement = endorsementNew.getApplication();
                if (oldApplicationOfEndorsement != null) {
                    oldApplicationOfEndorsement.setEndorsement(null);
                    oldApplicationOfEndorsement = em.merge(oldApplicationOfEndorsement);
                }
                endorsementNew.setApplication(application);
                endorsementNew = em.merge(endorsementNew);
            }
            if (recommendationReportNew != null && !recommendationReportNew.equals(recommendationReportOld)) {
                Application oldApplicationOfRecommendationReport = recommendationReportNew.getApplication();
                if (oldApplicationOfRecommendationReport != null) {
                    oldApplicationOfRecommendationReport.setRecommendationReport(null);
                    oldApplicationOfRecommendationReport = em.merge(oldApplicationOfRecommendationReport);
                }
                recommendationReportNew.setApplication(application);
                recommendationReportNew = em.merge(recommendationReportNew);
            }
            if (fundingReportNew != null && !fundingReportNew.equals(fundingReportOld)) {
                Application oldApplicationOfFundingReport = fundingReportNew.getApplication();
                if (oldApplicationOfFundingReport != null) {
                    oldApplicationOfFundingReport.setFundingReport(null);
                    oldApplicationOfFundingReport = em.merge(oldApplicationOfFundingReport);
                }
                fundingReportNew.setApplication(application);
                fundingReportNew = em.merge(fundingReportNew);
            }
            if (fellowOld != null && !fellowOld.equals(fellowNew)) {
                fellowOld.getApplicationList().remove(application);
                fellowOld = em.merge(fellowOld);
            }
            if (fellowNew != null && !fellowNew.equals(fellowOld)) {
                fellowNew.getApplicationList().add(application);
                fellowNew = em.merge(fellowNew);
            }
            if (grantHolderIDOld != null && !grantHolderIDOld.equals(grantHolderIDNew)) {
                grantHolderIDOld.getApplicationList().remove(application);
                grantHolderIDOld = em.merge(grantHolderIDOld);
            }
            if (grantHolderIDNew != null && !grantHolderIDNew.equals(grantHolderIDOld)) {
                grantHolderIDNew.getApplicationList().add(application);
                grantHolderIDNew = em.merge(grantHolderIDNew);
            }
            for (Person personListOldPerson : personListOld) {
                if (!personListNew.contains(personListOldPerson)) {
                    personListOldPerson.getApplicationList().remove(application);
                    personListOldPerson = em.merge(personListOldPerson);
                }
            }
            for (Person personListNewPerson : personListNew) {
                if (!personListOld.contains(personListNewPerson)) {
                    personListNewPerson.getApplicationList().add(application);
                    personListNewPerson = em.merge(personListNewPerson);
                }
            }
            for (CommitteeMeeting committeeMeetingListOldCommitteeMeeting : committeeMeetingListOld) {
                if (!committeeMeetingListNew.contains(committeeMeetingListOldCommitteeMeeting)) {
                    committeeMeetingListOldCommitteeMeeting.getApplicationList().remove(application);
                    committeeMeetingListOldCommitteeMeeting = em.merge(committeeMeetingListOldCommitteeMeeting);
                }
            }
            for (CommitteeMeeting committeeMeetingListNewCommitteeMeeting : committeeMeetingListNew) {
                if (!committeeMeetingListOld.contains(committeeMeetingListNewCommitteeMeeting)) {
                    committeeMeetingListNewCommitteeMeeting.getApplicationList().add(application);
                    committeeMeetingListNewCommitteeMeeting = em.merge(committeeMeetingListNewCommitteeMeeting);
                }
            }
            for (RefereeReport refereeReportListNewRefereeReport : refereeReportListNew) {
                if (!refereeReportListOld.contains(refereeReportListNewRefereeReport)) {
                    Application oldApplicationIDOfRefereeReportListNewRefereeReport = refereeReportListNewRefereeReport.getApplicationID();
                    refereeReportListNewRefereeReport.setApplicationID(application);
                    refereeReportListNewRefereeReport = em.merge(refereeReportListNewRefereeReport);
                    if (oldApplicationIDOfRefereeReportListNewRefereeReport != null && !oldApplicationIDOfRefereeReportListNewRefereeReport.equals(application)) {
                        oldApplicationIDOfRefereeReportListNewRefereeReport.getRefereeReportList().remove(refereeReportListNewRefereeReport);
                        oldApplicationIDOfRefereeReportListNewRefereeReport = em.merge(oldApplicationIDOfRefereeReportListNewRefereeReport);
                    }
                }
            }
            for (ProgressReport progressReportListNewProgressReport : progressReportListNew) {
                if (!progressReportListOld.contains(progressReportListNewProgressReport)) {
                    Application oldApplicationIDOfProgressReportListNewProgressReport = progressReportListNewProgressReport.getApplicationID();
                    progressReportListNewProgressReport.setApplicationID(application);
                    progressReportListNewProgressReport = em.merge(progressReportListNewProgressReport);
                    if (oldApplicationIDOfProgressReportListNewProgressReport != null && !oldApplicationIDOfProgressReportListNewProgressReport.equals(application)) {
                        oldApplicationIDOfProgressReportListNewProgressReport.getProgressReportList().remove(progressReportListNewProgressReport);
                        oldApplicationIDOfProgressReportListNewProgressReport = em.merge(oldApplicationIDOfProgressReportListNewProgressReport);
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
                Long id = application.getApplicationID();
                if (findApplication(id) == null) {
                    throw new NonexistentEntityException("The application with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Application application;
            try {
                application = em.getReference(Application.class, id);
                application.getApplicationID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The application with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Endorsement endorsementOrphanCheck = application.getEndorsement();
            if (endorsementOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the Endorsement " + endorsementOrphanCheck + " in its endorsement field has a non-nullable application field.");
            }
            RecommendationReport recommendationReportOrphanCheck = application.getRecommendationReport();
            if (recommendationReportOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the RecommendationReport " + recommendationReportOrphanCheck + " in its recommendationReport field has a non-nullable application field.");
            }
            FundingReport fundingReportOrphanCheck = application.getFundingReport();
            if (fundingReportOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the FundingReport " + fundingReportOrphanCheck + " in its fundingReport field has a non-nullable application field.");
            }
            List<RefereeReport> refereeReportListOrphanCheck = application.getRefereeReportList();
            for (RefereeReport refereeReportListOrphanCheckRefereeReport : refereeReportListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the RefereeReport " + refereeReportListOrphanCheckRefereeReport + " in its refereeReportList field has a non-nullable applicationID field.");
            }
            List<ProgressReport> progressReportListOrphanCheck = application.getProgressReportList();
            for (ProgressReport progressReportListOrphanCheckProgressReport : progressReportListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the ProgressReport " + progressReportListOrphanCheckProgressReport + " in its progressReportList field has a non-nullable applicationID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Person fellow = application.getFellow();
            if (fellow != null) {
                fellow.getApplicationList().remove(application);
                fellow = em.merge(fellow);
            }
            Person grantHolderID = application.getGrantHolderID();
            if (grantHolderID != null) {
                grantHolderID.getApplicationList().remove(application);
                grantHolderID = em.merge(grantHolderID);
            }
            List<Person> personList = application.getPersonList();
            for (Person personListPerson : personList) {
                personListPerson.getApplicationList().remove(application);
                personListPerson = em.merge(personListPerson);
            }
            List<CommitteeMeeting> committeeMeetingList = application.getCommitteeMeetingList();
            for (CommitteeMeeting committeeMeetingListCommitteeMeeting : committeeMeetingList) {
                committeeMeetingListCommitteeMeeting.getApplicationList().remove(application);
                committeeMeetingListCommitteeMeeting = em.merge(committeeMeetingListCommitteeMeeting);
            }
            em.remove(application);
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

    public List<Application> findApplicationEntities() {
        return findApplicationEntities(true, -1, -1);
    }

    public List<Application> findApplicationEntities(int maxResults, int firstResult) {
        return findApplicationEntities(false, maxResults, firstResult);
    }

    private List<Application> findApplicationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Application.class));
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

    public Application findApplication(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Application.class, id);
        } finally {
            em.close();
        }
    }

    public int getApplicationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Application> rt = cq.from(Application.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     *This function is used to return all the applications of a particular status
     * @param applicationStatus The status of the application as a string
     * @return A list of applications with following type
     */
    public List<Application> findAllApplicationsWithStatus(String applicationStatus, int startRecord, int maxRecords)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.status= :status", Application.class).setParameter("status", applicationStatus).setFirstResult(startRecord).setMaxResults(maxRecords);
        return q.getResultList();
}
    
    public List<Application> findAllApplicationsWithStatusAndReferee(String applicationStatus, Person referee, int startRecord, int maxRecords)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.status= :status AND :ref MEMBER OF a.refereeList", Application.class).setParameter("status", applicationStatus).setParameter("ref", referee).setFirstResult(startRecord).setMaxResults(maxRecords);
        return q.getResultList();
    }
    
    public List<Application> findAllApplicationsWithStatusAndGrantHolder(String applicationStatus, Person grantHolder, int startRecord, int maxRecords)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.status= :status AND a.grantHolderID = :grantHolder", Application.class).setParameter("status", applicationStatus).setParameter("grantHolder", grantHolder).setFirstResult(startRecord).setMaxResults(maxRecords);
        return q.getResultList();
    }
    
    public List<Application> findAllApplicationsWithStatusAndDepartment(String applicationStatus, String deparment, int startRecord, int maxRecords)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.status= :status AND a.grantHolderID.locationID.department = :dep", Application.class).setParameter("status", applicationStatus).setParameter("dep", deparment).setFirstResult(startRecord).setMaxResults(maxRecords);
        return q.getResultList();
    }
    
    public List<Application> findAllApplicationsWithStatusAndFaculty(String applicationStatus, String faculty, int startRecord, int maxRecords)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.status= :status AND a.grantHolderID.locationID.faculty = :fac", Application.class).setParameter("status", applicationStatus).setParameter("fac", faculty).setFirstResult(startRecord).setMaxResults(maxRecords);
        return q.getResultList();
    }
    
    
    public List<Person> findAllHODsWhoCanRecommendApplication(Application application)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p WHERE p.locationID = :loc AND p.securityRoleList.roleID = :secRole", Person.class).setParameter("loc", application.getGrantHolderID().getLocationID()).setParameter("secRole", com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD);
        return q.getResultList();
    }
    
    public List<Person> findAllDeansOfficeMembersWhoCanEndorseApplication(Application application)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p WHERE p.locationID.faculty = :loc AND p.securityRoleList.roleID = :secRole", Person.class).setParameter("loc", application.getGrantHolderID().getLocationID().getFaculty()).setParameter("secRole", com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DEANS_OFFICE_MEMBER);
        return q.getResultList();
    }
    
    public List<Person> findAllDRISMembersWhoCanApproveApplication(Application application)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p WHERE p.securityRoleList.roleID = :secRole", Person.class).setParameter("secRole", com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER);
        return q.getResultList();
    }
    
    public List<Application> findAllApplicationsWhosFellowIs(Person fellow)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.fellow = :f", Application.class).setParameter("f", fellow);
        return q.getResultList();
    }
    
    public List<Application> findAllApplicationsWhosGrantHolderIs(Person grantHolder)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.grantHolderID = :gh", Application.class).setParameter("gh", grantHolder);
        return q.getResultList();
    }
    
    
    
    public int countAllApplicationsWithStatus(String applicationStatus)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Integer> q = em.createQuery("SELECT COUNT(a) FROM Application a WHERE a.status= :status", Integer.class).setParameter("status", applicationStatus);
        return q.getSingleResult();
    }
    
    public int countAllApplicationsWithStatusAndReferee(String applicationStatus, Person referee)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Integer> q = em.createQuery("SELECT COUNT(a) FROM Application a WHERE a.status= :status AND :ref MEMBER OF a.refereeList", Integer.class).setParameter("status", applicationStatus).setParameter("ref", referee);
        return q.getSingleResult();
    }
    
    public int countAllApplicationsWithStatusAndGrantHolder(String applicationStatus, Person grantHolder)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Integer> q = em.createQuery("SELECT COUNT(a) FROM Application a WHERE a.status= :status AND a.grantHolderID = :grantHolder", Integer.class).setParameter("status", applicationStatus).setParameter("grantHolder", grantHolder);
        return q.getSingleResult();
    }
    
    public int countAllApplicationsWithStatusAndDepartment(String applicationStatus, String deparment)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Integer> q = em.createQuery("SELECT COUNT(a) FROM Application a WHERE a.status= :status AND a.grantHolderID.locationID.department = :dep", Integer.class).setParameter("status", applicationStatus).setParameter("dep", deparment);
        return q.getSingleResult();
    }
    
    public int countAllApplicationsWithStatusAndFaculty(String applicationStatus, String faculty)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Integer> q = em.createQuery("SELECT COUNT(a) FROM Application a WHERE a.status= :status AND a.grantHolderID.locationID.faculty = :fac", Integer.class).setParameter("status", applicationStatus).setParameter("fac", faculty);
        return q.getSingleResult();
    }
    
    public List<Application> getAllApplicationsForFellowWithEndDateInBetween(Person fellow, Date rangeStart, Date rangeEnd)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE (a.fellow = :fellow) AND (a.endDate BETWEEN :rangeStart AND :rangeEnd)", Application.class).setParameter("rangeStart", rangeStart).setParameter("rangeEnd", rangeEnd).setParameter("fellow", fellow);
        return q.getResultList();
    }
}
