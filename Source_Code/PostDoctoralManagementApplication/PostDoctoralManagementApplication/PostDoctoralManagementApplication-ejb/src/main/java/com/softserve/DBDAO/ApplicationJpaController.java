/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEnties.Application;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEnties.Person;
import com.softserve.DBEnties.Location;
import com.softserve.DBEnties.Endorsement;
import com.softserve.DBEnties.FundingReport;
import com.softserve.DBEnties.CommitteeMeeting;
import java.util.ArrayList;
import java.util.Collection;
import com.softserve.DBEnties.RefereeReport;
import com.softserve.DBEnties.ProgressReport;
import java.util.List;
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
        if (application.getCommitteeMeetingCollection() == null) {
            application.setCommitteeMeetingCollection(new ArrayList<CommitteeMeeting>());
        }
        if (application.getRefereeReportCollection() == null) {
            application.setRefereeReportCollection(new ArrayList<RefereeReport>());
        }
        if (application.getProgressReportCollection() == null) {
            application.setProgressReportCollection(new ArrayList<ProgressReport>());
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
            Collection<CommitteeMeeting> attachedCommitteeMeetingCollection = new ArrayList<CommitteeMeeting>();
            for (CommitteeMeeting committeeMeetingCollectionCommitteeMeetingToAttach : application.getCommitteeMeetingCollection()) {
                committeeMeetingCollectionCommitteeMeetingToAttach = em.getReference(committeeMeetingCollectionCommitteeMeetingToAttach.getClass(), committeeMeetingCollectionCommitteeMeetingToAttach.getMeetingID());
                attachedCommitteeMeetingCollection.add(committeeMeetingCollectionCommitteeMeetingToAttach);
            }
            application.setCommitteeMeetingCollection(attachedCommitteeMeetingCollection);
            Collection<RefereeReport> attachedRefereeReportCollection = new ArrayList<RefereeReport>();
            for (RefereeReport refereeReportCollectionRefereeReportToAttach : application.getRefereeReportCollection()) {
                refereeReportCollectionRefereeReportToAttach = em.getReference(refereeReportCollectionRefereeReportToAttach.getClass(), refereeReportCollectionRefereeReportToAttach.getReportID());
                attachedRefereeReportCollection.add(refereeReportCollectionRefereeReportToAttach);
            }
            application.setRefereeReportCollection(attachedRefereeReportCollection);
            Collection<ProgressReport> attachedProgressReportCollection = new ArrayList<ProgressReport>();
            for (ProgressReport progressReportCollectionProgressReportToAttach : application.getProgressReportCollection()) {
                progressReportCollectionProgressReportToAttach = em.getReference(progressReportCollectionProgressReportToAttach.getClass(), progressReportCollectionProgressReportToAttach.getReportID());
                attachedProgressReportCollection.add(progressReportCollectionProgressReportToAttach);
            }
            application.setProgressReportCollection(attachedProgressReportCollection);
            em.persist(application);
            if (fellow != null) {
                fellow.getApplicationCollection().add(application);
                fellow = em.merge(fellow);
            }
            if (grantHolderID != null) {
                grantHolderID.getApplicationCollection().add(application);
                grantHolderID = em.merge(grantHolderID);
            }
            if (locationID != null) {
                locationID.getApplicationCollection().add(application);
                locationID = em.merge(locationID);
            }
            if (endorsementID != null) {
                endorsementID.getApplicationCollection().add(application);
                endorsementID = em.merge(endorsementID);
            }
            if (fundingReportID != null) {
                fundingReportID.getApplicationCollection().add(application);
                fundingReportID = em.merge(fundingReportID);
            }
            for (CommitteeMeeting committeeMeetingCollectionCommitteeMeeting : application.getCommitteeMeetingCollection()) {
                committeeMeetingCollectionCommitteeMeeting.getApplicationCollection().add(application);
                committeeMeetingCollectionCommitteeMeeting = em.merge(committeeMeetingCollectionCommitteeMeeting);
            }
            for (RefereeReport refereeReportCollectionRefereeReport : application.getRefereeReportCollection()) {
                Application oldApplicationIDOfRefereeReportCollectionRefereeReport = refereeReportCollectionRefereeReport.getApplicationID();
                refereeReportCollectionRefereeReport.setApplicationID(application);
                refereeReportCollectionRefereeReport = em.merge(refereeReportCollectionRefereeReport);
                if (oldApplicationIDOfRefereeReportCollectionRefereeReport != null) {
                    oldApplicationIDOfRefereeReportCollectionRefereeReport.getRefereeReportCollection().remove(refereeReportCollectionRefereeReport);
                    oldApplicationIDOfRefereeReportCollectionRefereeReport = em.merge(oldApplicationIDOfRefereeReportCollectionRefereeReport);
                }
            }
            for (ProgressReport progressReportCollectionProgressReport : application.getProgressReportCollection()) {
                Application oldApplicationIDOfProgressReportCollectionProgressReport = progressReportCollectionProgressReport.getApplicationID();
                progressReportCollectionProgressReport.setApplicationID(application);
                progressReportCollectionProgressReport = em.merge(progressReportCollectionProgressReport);
                if (oldApplicationIDOfProgressReportCollectionProgressReport != null) {
                    oldApplicationIDOfProgressReportCollectionProgressReport.getProgressReportCollection().remove(progressReportCollectionProgressReport);
                    oldApplicationIDOfProgressReportCollectionProgressReport = em.merge(oldApplicationIDOfProgressReportCollectionProgressReport);
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
            Collection<CommitteeMeeting> committeeMeetingCollectionOld = persistentApplication.getCommitteeMeetingCollection();
            Collection<CommitteeMeeting> committeeMeetingCollectionNew = application.getCommitteeMeetingCollection();
            Collection<RefereeReport> refereeReportCollectionOld = persistentApplication.getRefereeReportCollection();
            Collection<RefereeReport> refereeReportCollectionNew = application.getRefereeReportCollection();
            Collection<ProgressReport> progressReportCollectionOld = persistentApplication.getProgressReportCollection();
            Collection<ProgressReport> progressReportCollectionNew = application.getProgressReportCollection();
            List<String> illegalOrphanMessages = null;
            for (RefereeReport refereeReportCollectionOldRefereeReport : refereeReportCollectionOld) {
                if (!refereeReportCollectionNew.contains(refereeReportCollectionOldRefereeReport)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RefereeReport " + refereeReportCollectionOldRefereeReport + " since its applicationID field is not nullable.");
                }
            }
            for (ProgressReport progressReportCollectionOldProgressReport : progressReportCollectionOld) {
                if (!progressReportCollectionNew.contains(progressReportCollectionOldProgressReport)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProgressReport " + progressReportCollectionOldProgressReport + " since its applicationID field is not nullable.");
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
            Collection<CommitteeMeeting> attachedCommitteeMeetingCollectionNew = new ArrayList<CommitteeMeeting>();
            for (CommitteeMeeting committeeMeetingCollectionNewCommitteeMeetingToAttach : committeeMeetingCollectionNew) {
                committeeMeetingCollectionNewCommitteeMeetingToAttach = em.getReference(committeeMeetingCollectionNewCommitteeMeetingToAttach.getClass(), committeeMeetingCollectionNewCommitteeMeetingToAttach.getMeetingID());
                attachedCommitteeMeetingCollectionNew.add(committeeMeetingCollectionNewCommitteeMeetingToAttach);
            }
            committeeMeetingCollectionNew = attachedCommitteeMeetingCollectionNew;
            application.setCommitteeMeetingCollection(committeeMeetingCollectionNew);
            Collection<RefereeReport> attachedRefereeReportCollectionNew = new ArrayList<RefereeReport>();
            for (RefereeReport refereeReportCollectionNewRefereeReportToAttach : refereeReportCollectionNew) {
                refereeReportCollectionNewRefereeReportToAttach = em.getReference(refereeReportCollectionNewRefereeReportToAttach.getClass(), refereeReportCollectionNewRefereeReportToAttach.getReportID());
                attachedRefereeReportCollectionNew.add(refereeReportCollectionNewRefereeReportToAttach);
            }
            refereeReportCollectionNew = attachedRefereeReportCollectionNew;
            application.setRefereeReportCollection(refereeReportCollectionNew);
            Collection<ProgressReport> attachedProgressReportCollectionNew = new ArrayList<ProgressReport>();
            for (ProgressReport progressReportCollectionNewProgressReportToAttach : progressReportCollectionNew) {
                progressReportCollectionNewProgressReportToAttach = em.getReference(progressReportCollectionNewProgressReportToAttach.getClass(), progressReportCollectionNewProgressReportToAttach.getReportID());
                attachedProgressReportCollectionNew.add(progressReportCollectionNewProgressReportToAttach);
            }
            progressReportCollectionNew = attachedProgressReportCollectionNew;
            application.setProgressReportCollection(progressReportCollectionNew);
            application = em.merge(application);
            if (fellowOld != null && !fellowOld.equals(fellowNew)) {
                fellowOld.getApplicationCollection().remove(application);
                fellowOld = em.merge(fellowOld);
            }
            if (fellowNew != null && !fellowNew.equals(fellowOld)) {
                fellowNew.getApplicationCollection().add(application);
                fellowNew = em.merge(fellowNew);
            }
            if (grantHolderIDOld != null && !grantHolderIDOld.equals(grantHolderIDNew)) {
                grantHolderIDOld.getApplicationCollection().remove(application);
                grantHolderIDOld = em.merge(grantHolderIDOld);
            }
            if (grantHolderIDNew != null && !grantHolderIDNew.equals(grantHolderIDOld)) {
                grantHolderIDNew.getApplicationCollection().add(application);
                grantHolderIDNew = em.merge(grantHolderIDNew);
            }
            if (locationIDOld != null && !locationIDOld.equals(locationIDNew)) {
                locationIDOld.getApplicationCollection().remove(application);
                locationIDOld = em.merge(locationIDOld);
            }
            if (locationIDNew != null && !locationIDNew.equals(locationIDOld)) {
                locationIDNew.getApplicationCollection().add(application);
                locationIDNew = em.merge(locationIDNew);
            }
            if (endorsementIDOld != null && !endorsementIDOld.equals(endorsementIDNew)) {
                endorsementIDOld.getApplicationCollection().remove(application);
                endorsementIDOld = em.merge(endorsementIDOld);
            }
            if (endorsementIDNew != null && !endorsementIDNew.equals(endorsementIDOld)) {
                endorsementIDNew.getApplicationCollection().add(application);
                endorsementIDNew = em.merge(endorsementIDNew);
            }
            if (fundingReportIDOld != null && !fundingReportIDOld.equals(fundingReportIDNew)) {
                fundingReportIDOld.getApplicationCollection().remove(application);
                fundingReportIDOld = em.merge(fundingReportIDOld);
            }
            if (fundingReportIDNew != null && !fundingReportIDNew.equals(fundingReportIDOld)) {
                fundingReportIDNew.getApplicationCollection().add(application);
                fundingReportIDNew = em.merge(fundingReportIDNew);
            }
            for (CommitteeMeeting committeeMeetingCollectionOldCommitteeMeeting : committeeMeetingCollectionOld) {
                if (!committeeMeetingCollectionNew.contains(committeeMeetingCollectionOldCommitteeMeeting)) {
                    committeeMeetingCollectionOldCommitteeMeeting.getApplicationCollection().remove(application);
                    committeeMeetingCollectionOldCommitteeMeeting = em.merge(committeeMeetingCollectionOldCommitteeMeeting);
                }
            }
            for (CommitteeMeeting committeeMeetingCollectionNewCommitteeMeeting : committeeMeetingCollectionNew) {
                if (!committeeMeetingCollectionOld.contains(committeeMeetingCollectionNewCommitteeMeeting)) {
                    committeeMeetingCollectionNewCommitteeMeeting.getApplicationCollection().add(application);
                    committeeMeetingCollectionNewCommitteeMeeting = em.merge(committeeMeetingCollectionNewCommitteeMeeting);
                }
            }
            for (RefereeReport refereeReportCollectionNewRefereeReport : refereeReportCollectionNew) {
                if (!refereeReportCollectionOld.contains(refereeReportCollectionNewRefereeReport)) {
                    Application oldApplicationIDOfRefereeReportCollectionNewRefereeReport = refereeReportCollectionNewRefereeReport.getApplicationID();
                    refereeReportCollectionNewRefereeReport.setApplicationID(application);
                    refereeReportCollectionNewRefereeReport = em.merge(refereeReportCollectionNewRefereeReport);
                    if (oldApplicationIDOfRefereeReportCollectionNewRefereeReport != null && !oldApplicationIDOfRefereeReportCollectionNewRefereeReport.equals(application)) {
                        oldApplicationIDOfRefereeReportCollectionNewRefereeReport.getRefereeReportCollection().remove(refereeReportCollectionNewRefereeReport);
                        oldApplicationIDOfRefereeReportCollectionNewRefereeReport = em.merge(oldApplicationIDOfRefereeReportCollectionNewRefereeReport);
                    }
                }
            }
            for (ProgressReport progressReportCollectionNewProgressReport : progressReportCollectionNew) {
                if (!progressReportCollectionOld.contains(progressReportCollectionNewProgressReport)) {
                    Application oldApplicationIDOfProgressReportCollectionNewProgressReport = progressReportCollectionNewProgressReport.getApplicationID();
                    progressReportCollectionNewProgressReport.setApplicationID(application);
                    progressReportCollectionNewProgressReport = em.merge(progressReportCollectionNewProgressReport);
                    if (oldApplicationIDOfProgressReportCollectionNewProgressReport != null && !oldApplicationIDOfProgressReportCollectionNewProgressReport.equals(application)) {
                        oldApplicationIDOfProgressReportCollectionNewProgressReport.getProgressReportCollection().remove(progressReportCollectionNewProgressReport);
                        oldApplicationIDOfProgressReportCollectionNewProgressReport = em.merge(oldApplicationIDOfProgressReportCollectionNewProgressReport);
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
            Collection<RefereeReport> refereeReportCollectionOrphanCheck = application.getRefereeReportCollection();
            for (RefereeReport refereeReportCollectionOrphanCheckRefereeReport : refereeReportCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the RefereeReport " + refereeReportCollectionOrphanCheckRefereeReport + " in its refereeReportCollection field has a non-nullable applicationID field.");
            }
            Collection<ProgressReport> progressReportCollectionOrphanCheck = application.getProgressReportCollection();
            for (ProgressReport progressReportCollectionOrphanCheckProgressReport : progressReportCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the ProgressReport " + progressReportCollectionOrphanCheckProgressReport + " in its progressReportCollection field has a non-nullable applicationID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Person fellow = application.getFellow();
            if (fellow != null) {
                fellow.getApplicationCollection().remove(application);
                fellow = em.merge(fellow);
            }
            Person grantHolderID = application.getGrantHolderID();
            if (grantHolderID != null) {
                grantHolderID.getApplicationCollection().remove(application);
                grantHolderID = em.merge(grantHolderID);
            }
            Location locationID = application.getLocationID();
            if (locationID != null) {
                locationID.getApplicationCollection().remove(application);
                locationID = em.merge(locationID);
            }
            Endorsement endorsementID = application.getEndorsementID();
            if (endorsementID != null) {
                endorsementID.getApplicationCollection().remove(application);
                endorsementID = em.merge(endorsementID);
            }
            FundingReport fundingReportID = application.getFundingReportID();
            if (fundingReportID != null) {
                fundingReportID.getApplicationCollection().remove(application);
                fundingReportID = em.merge(fundingReportID);
            }
            Collection<CommitteeMeeting> committeeMeetingCollection = application.getCommitteeMeetingCollection();
            for (CommitteeMeeting committeeMeetingCollectionCommitteeMeeting : committeeMeetingCollection) {
                committeeMeetingCollectionCommitteeMeeting.getApplicationCollection().remove(application);
                committeeMeetingCollectionCommitteeMeeting = em.merge(committeeMeetingCollectionCommitteeMeeting);
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
