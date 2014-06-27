/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.Location;
import com.softserve.DBEntities.Endorsement;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.CommitteeMeeting;
import java.util.ArrayList;
import java.util.List;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.DBEntities.ProgressReport;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
            Location locationID = application.getLocationID();
            if (locationID != null) {
                locationID = em.getReference(locationID.getClass(), locationID.getLocationID());
                application.setLocationID(locationID);
            }
            Endorsement endorsementID = application.getEndorsementID();
            if (endorsementID != null) {
                endorsementID = em.getReference(endorsementID.getClass(), endorsementID.getEndorsementID());
                application.setEndorsementID(endorsementID);
            }
            FundingReport fundingReportID = application.getFundingReportID();
            if (fundingReportID != null) {
                fundingReportID = em.getReference(fundingReportID.getClass(), fundingReportID.getReportID());
                application.setFundingReportID(fundingReportID);
            }
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
            if (fellow != null) {
                fellow.getApplicationList().add(application);
                fellow = em.merge(fellow);
            }
            if (grantHolderID != null) {
                grantHolderID.getApplicationList().add(application);
                grantHolderID = em.merge(grantHolderID);
            }
            if (locationID != null) {
                locationID.getApplicationList().add(application);
                locationID = em.merge(locationID);
            }
            if (endorsementID != null) {
                endorsementID.getApplicationList().add(application);
                endorsementID = em.merge(endorsementID);
            }
            if (fundingReportID != null) {
                fundingReportID.getApplicationList().add(application);
                fundingReportID = em.merge(fundingReportID);
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
            Person fellowOld = persistentApplication.getFellow();
            Person fellowNew = application.getFellow();
            Person grantHolderIDOld = persistentApplication.getGrantHolderID();
            Person grantHolderIDNew = application.getGrantHolderID();
            Location locationIDOld = persistentApplication.getLocationID();
            Location locationIDNew = application.getLocationID();
            Endorsement endorsementIDOld = persistentApplication.getEndorsementID();
            Endorsement endorsementIDNew = application.getEndorsementID();
            FundingReport fundingReportIDOld = persistentApplication.getFundingReportID();
            FundingReport fundingReportIDNew = application.getFundingReportID();
            List<CommitteeMeeting> committeeMeetingListOld = persistentApplication.getCommitteeMeetingList();
            List<CommitteeMeeting> committeeMeetingListNew = application.getCommitteeMeetingList();
            List<RefereeReport> refereeReportListOld = persistentApplication.getRefereeReportList();
            List<RefereeReport> refereeReportListNew = application.getRefereeReportList();
            List<ProgressReport> progressReportListOld = persistentApplication.getProgressReportList();
            List<ProgressReport> progressReportListNew = application.getProgressReportList();
            List<String> illegalOrphanMessages = null;
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
            if (fellowNew != null) {
                fellowNew = em.getReference(fellowNew.getClass(), fellowNew.getSystemID());
                application.setFellow(fellowNew);
            }
            if (grantHolderIDNew != null) {
                grantHolderIDNew = em.getReference(grantHolderIDNew.getClass(), grantHolderIDNew.getSystemID());
                application.setGrantHolderID(grantHolderIDNew);
            }
            if (locationIDNew != null) {
                locationIDNew = em.getReference(locationIDNew.getClass(), locationIDNew.getLocationID());
                application.setLocationID(locationIDNew);
            }
            if (endorsementIDNew != null) {
                endorsementIDNew = em.getReference(endorsementIDNew.getClass(), endorsementIDNew.getEndorsementID());
                application.setEndorsementID(endorsementIDNew);
            }
            if (fundingReportIDNew != null) {
                fundingReportIDNew = em.getReference(fundingReportIDNew.getClass(), fundingReportIDNew.getReportID());
                application.setFundingReportID(fundingReportIDNew);
            }
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
            if (locationIDOld != null && !locationIDOld.equals(locationIDNew)) {
                locationIDOld.getApplicationList().remove(application);
                locationIDOld = em.merge(locationIDOld);
            }
            if (locationIDNew != null && !locationIDNew.equals(locationIDOld)) {
                locationIDNew.getApplicationList().add(application);
                locationIDNew = em.merge(locationIDNew);
            }
            if (endorsementIDOld != null && !endorsementIDOld.equals(endorsementIDNew)) {
                endorsementIDOld.getApplicationList().remove(application);
                endorsementIDOld = em.merge(endorsementIDOld);
            }
            if (endorsementIDNew != null && !endorsementIDNew.equals(endorsementIDOld)) {
                endorsementIDNew.getApplicationList().add(application);
                endorsementIDNew = em.merge(endorsementIDNew);
            }
            if (fundingReportIDOld != null && !fundingReportIDOld.equals(fundingReportIDNew)) {
                fundingReportIDOld.getApplicationList().remove(application);
                fundingReportIDOld = em.merge(fundingReportIDOld);
            }
            if (fundingReportIDNew != null && !fundingReportIDNew.equals(fundingReportIDOld)) {
                fundingReportIDNew.getApplicationList().add(application);
                fundingReportIDNew = em.merge(fundingReportIDNew);
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
            Location locationID = application.getLocationID();
            if (locationID != null) {
                locationID.getApplicationList().remove(application);
                locationID = em.merge(locationID);
            }
            Endorsement endorsementID = application.getEndorsementID();
            if (endorsementID != null) {
                endorsementID.getApplicationList().remove(application);
                endorsementID = em.merge(endorsementID);
            }
            FundingReport fundingReportID = application.getFundingReportID();
            if (fundingReportID != null) {
                fundingReportID.getApplicationList().remove(application);
                fundingReportID = em.merge(fundingReportID);
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
    
}
