/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.DeclineReport;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Endorsement;
import com.softserve.DBEntities.RecommendationReport;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.EligiblityReport;
import com.softserve.DBEntities.Application;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.ApplicationReviewRequest;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.DBEntities.AmmendRequest;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.ForwardAndRewindReport;
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

    public ApplicationJpaController(EntityManager em) {
        this.emm = em;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(Application application) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), application);
    }

    public void create(EntityManager em, Application application) throws RollbackFailureException, Exception 
    {
        if (application.getPersonList() == null) {
            application.setPersonList(new ArrayList<Person>());
        }
        if (application.getCommitteeMeetingList() == null) {
            application.setCommitteeMeetingList(new ArrayList<CommitteeMeeting>());
        }
        if (application.getApplicationReviewRequestList() == null) {
            application.setApplicationReviewRequestList(new ArrayList<ApplicationReviewRequest>());
        }
        if (application.getRefereeReportList() == null) {
            application.setRefereeReportList(new ArrayList<RefereeReport>());
        }
        if (application.getAmmendRequestList() == null) {
            application.setAmmendRequestList(new ArrayList<AmmendRequest>());
        }
        if (application.getProgressReportList() == null) {
            application.setProgressReportList(new ArrayList<ProgressReport>());
        }
        if (application.getForwardAndRewindReportList() == null) {
            application.setForwardAndRewindReportList(new ArrayList<ForwardAndRewindReport>());
        }        
        
        em.persist(application);

    }
    
    public Application edit(Application application) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), application);
    }

    public Application edit(EntityManager em, Application application) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        Long id = application.getApplicationID();
        if (findApplication(id) == null) {
            throw new NonexistentEntityException("The application with id " + id + " no longer exists.");
        }
        
        Application persistentApplication = em.find(Application.class, application.getApplicationID());
        DeclineReport declineReportOld = persistentApplication.getDeclineReport();
        DeclineReport declineReportNew = application.getDeclineReport();
        Endorsement endorsementOld = persistentApplication.getEndorsement();
        Endorsement endorsementNew = application.getEndorsement();
        RecommendationReport recommendationReportOld = persistentApplication.getRecommendationReport();
        RecommendationReport recommendationReportNew = application.getRecommendationReport();
        FundingReport fundingReportOld = persistentApplication.getFundingReport();
        FundingReport fundingReportNew = application.getFundingReport();
        Person fellowOld = persistentApplication.getFellow();
        Person fellowNew = application.getFellow();
        Person grantHolderOld = persistentApplication.getGrantHolder();
        Person grantHolderNew = application.getGrantHolder();
        EligiblityReport eligiblityReportOld = persistentApplication.getEligiblityReport();
        EligiblityReport eligiblityReportNew = application.getEligiblityReport();
        Application parentApplicationOld = persistentApplication.getParentApplication();
        Application parentApplicationNew = application.getParentApplication();
        Application childApplicationOld = persistentApplication.getChildApplication();
        Application childApplicationNew = application.getChildApplication();
        List<Person> personListOld = persistentApplication.getPersonList();
        List<Person> personListNew = application.getPersonList();
        List<CommitteeMeeting> committeeMeetingListOld = persistentApplication.getCommitteeMeetingList();
        List<CommitteeMeeting> committeeMeetingListNew = application.getCommitteeMeetingList();
        List<ApplicationReviewRequest> applicationReviewRequestListOld = persistentApplication.getApplicationReviewRequestList();
        List<ApplicationReviewRequest> applicationReviewRequestListNew = application.getApplicationReviewRequestList();
        List<RefereeReport> refereeReportListOld = persistentApplication.getRefereeReportList();
        List<RefereeReport> refereeReportListNew = application.getRefereeReportList();
        List<AmmendRequest> ammendRequestListOld = persistentApplication.getAmmendRequestList();
        List<AmmendRequest> ammendRequestListNew = application.getAmmendRequestList();
        List<ProgressReport> progressReportListOld = persistentApplication.getProgressReportList();
        List<ProgressReport> progressReportListNew = application.getProgressReportList();
        List<ForwardAndRewindReport> forwardAndRewindReportListOld = persistentApplication.getForwardAndRewindReportList();
        List<ForwardAndRewindReport> forwardAndRewindReportListNew = application.getForwardAndRewindReportList();
        List<String> illegalOrphanMessages = null;
        if (declineReportOld != null && !declineReportOld.equals(declineReportNew)) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("You must retain DeclineReport " + declineReportOld + " since its application field is not nullable.");
        }
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
        if (eligiblityReportOld != null && !eligiblityReportOld.equals(eligiblityReportNew)) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("You must retain EligiblityReport " + eligiblityReportOld + " since its application field is not nullable.");
        }
        for (ApplicationReviewRequest applicationReviewRequestListOldApplicationReviewRequest : applicationReviewRequestListOld) {
            if (!applicationReviewRequestListNew.contains(applicationReviewRequestListOldApplicationReviewRequest)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ApplicationReviewRequest " + applicationReviewRequestListOldApplicationReviewRequest + " since its application1 field is not nullable.");
            }
        }
        for (RefereeReport refereeReportListOldRefereeReport : refereeReportListOld) {
            if (!refereeReportListNew.contains(refereeReportListOldRefereeReport)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain RefereeReport " + refereeReportListOldRefereeReport + " since its applicationID field is not nullable.");
            }
        }
        for (AmmendRequest ammendRequestListOldAmmendRequest : ammendRequestListOld) {
            if (!ammendRequestListNew.contains(ammendRequestListOldAmmendRequest)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain AmmendRequest " + ammendRequestListOldAmmendRequest + " since its application field is not nullable.");
            }
        }
        for (ProgressReport progressReportListOldProgressReport : progressReportListOld) {
            if (!progressReportListNew.contains(progressReportListOldProgressReport)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ProgressReport " + progressReportListOldProgressReport + " since its application field is not nullable.");
            }
        }
        for (ForwardAndRewindReport forwardAndRewindReportListOldForwardAndRewindReport : forwardAndRewindReportListOld) {
            if (!forwardAndRewindReportListNew.contains(forwardAndRewindReportListOldForwardAndRewindReport)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ForwardAndRewindReport " + forwardAndRewindReportListOldForwardAndRewindReport + " since its application field is not nullable.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }        
        
        return em.merge(application);        

    }
    
    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

        Application application;
        try {
            application = em.getReference(Application.class, id);
            application.getApplicationID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The application with id " + id + " no longer exists.", enfe);
        }
        List<String> illegalOrphanMessages = null;
        DeclineReport declineReportOrphanCheck = application.getDeclineReport();
        if (declineReportOrphanCheck != null) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the DeclineReport " + declineReportOrphanCheck + " in its declineReport field has a non-nullable application field.");
        }
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
        EligiblityReport eligiblityReportOrphanCheck = application.getEligiblityReport();
        if (eligiblityReportOrphanCheck != null) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the EligiblityReport " + eligiblityReportOrphanCheck + " in its eligiblityReport field has a non-nullable application field.");
        }
        List<ApplicationReviewRequest> applicationReviewRequestListOrphanCheck = application.getApplicationReviewRequestList();
        for (ApplicationReviewRequest applicationReviewRequestListOrphanCheckApplicationReviewRequest : applicationReviewRequestListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the ApplicationReviewRequest " + applicationReviewRequestListOrphanCheckApplicationReviewRequest + " in its applicationReviewRequestList field has a non-nullable application1 field.");
        }
        List<RefereeReport> refereeReportListOrphanCheck = application.getRefereeReportList();
        for (RefereeReport refereeReportListOrphanCheckRefereeReport : refereeReportListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the RefereeReport " + refereeReportListOrphanCheckRefereeReport + " in its refereeReportList field has a non-nullable applicationID field.");
        }
        List<AmmendRequest> ammendRequestListOrphanCheck = application.getAmmendRequestList();
        for (AmmendRequest ammendRequestListOrphanCheckAmmendRequest : ammendRequestListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the AmmendRequest " + ammendRequestListOrphanCheckAmmendRequest + " in its ammendRequestList field has a non-nullable application field.");
        }
        List<ProgressReport> progressReportListOrphanCheck = application.getProgressReportList();
        for (ProgressReport progressReportListOrphanCheckProgressReport : progressReportListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the ProgressReport " + progressReportListOrphanCheckProgressReport + " in its progressReportList field has a non-nullable application field.");
        }
        List<ForwardAndRewindReport> forwardAndRewindReportListOrphanCheck = application.getForwardAndRewindReportList();
        for (ForwardAndRewindReport forwardAndRewindReportListOrphanCheckForwardAndRewindReport : forwardAndRewindReportListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Application (" + application + ") cannot be destroyed since the ForwardAndRewindReport " + forwardAndRewindReportListOrphanCheckForwardAndRewindReport + " in its forwardAndRewindReportList field has a non-nullable application field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        
        em.remove(application);

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
            
        }
    }

    public Application findApplication(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Application.class, id);
        } finally {
            
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
        
        List<Application> applications = q.getResultList();
        return (applications != null)?applications:new ArrayList<Application>();
    }
    
    public List<Application> findAllNewApplicationsWithStatus(String applicationStatus, int startRecord, int maxRecords)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.status= :status AND a.type = :type", Application.class).setParameter("status", applicationStatus).setParameter("type", com.softserve.constants.PersistenceConstants.APPLICATION_TYPE_NEW).setFirstResult(startRecord).setMaxResults(maxRecords);
        
        List<Application> applications = q.getResultList();
        return (applications != null)?applications:new ArrayList<Application>();
    }
    
    public List<Application> findAllRenewalApplicationsWithStatus(String applicationStatus, int startRecord, int maxRecords)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.status= :status AND a.type = :type", Application.class).setParameter("status", applicationStatus).setParameter("type", com.softserve.constants.PersistenceConstants.APPLICATION_TYPE_RENEWAL).setFirstResult(startRecord).setMaxResults(maxRecords);
        
        List<Application> applications = q.getResultList();
        return (applications != null)?applications:new ArrayList<Application>();
    }
    
    public List<Application> findAllApplicationsWithStatusAndReferee(String applicationStatus, Person referee, int startRecord, int maxRecords)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.status= :status AND :ref MEMBER OF a.personList", Application.class).setParameter("status", applicationStatus).setParameter("ref", referee).setFirstResult(startRecord).setMaxResults(maxRecords);
        
        List<Application> applications = q.getResultList();
        return (applications != null)?applications:new ArrayList<Application>();
    }
    
    public List<Application> findAllApplicationsWithStatusAndGrantHolder(String applicationStatus, Person grantHolder, int startRecord, int maxRecords)
    {
        EntityManager em = getEntityManager();
        System.out.println("===calling2 ");
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.status= :status AND a.grantHolder = :grantHolder", Application.class).setParameter("status", applicationStatus).setParameter("grantHolder", grantHolder).setFirstResult(startRecord).setMaxResults(maxRecords);
        
        List<Application> applications = q.getResultList();
        return (applications != null)?applications:new ArrayList<Application>();
    }
    
    public List<Application> findAllApplicationsWithStatusAndDepartment(String applicationStatus, Department deparment, int startRecord, int maxRecords)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.status= :status AND a.grantHolder.employeeInformation.department = :dep", Application.class).setParameter("status", applicationStatus).setParameter("dep", deparment).setFirstResult(startRecord).setMaxResults(maxRecords);
        
        List<Application> applications = q.getResultList();
        return (applications != null)?applications:new ArrayList<Application>();
    }
    
    public List<Application> findAllApplicationsWithStatusAndFaculty(String applicationStatus, Faculty faculty, int startRecord, int maxRecords)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.status= :status AND a.grantHolder.employeeInformation.department.faculty = :fac", Application.class).setParameter("status", applicationStatus).setParameter("fac", faculty).setFirstResult(startRecord).setMaxResults(maxRecords);
        
        List<Application> applications = q.getResultList();
        return (applications != null)?applications:new ArrayList<Application>();
    }
        
    public List<Person> findAllHODsWhoCanRecommendApplication(Application application)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p WHERE p.upEmployee = true AND p.employeeInformation.department = :loc AND :secRole MEMBER OF p.securityRoleList", Person.class).setParameter("loc", application.getGrantHolder().getEmployeeInformation().getDepartment()).setParameter("secRole", com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        List<Person> people = q.getResultList();
        return (people != null)?people:new ArrayList<Person>();
    }
    
    public List<Person> findAllDeansOfficeMembersWhoCanEndorseApplication(Application application)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p WHERE p.upEmployee = true AND p.employeeInformation.department.faculty = :loc AND :secRole MEMBER OF p.securityRoleList ", Person.class).setParameter("loc", application.getGrantHolder().getEmployeeInformation().getDepartment().getFaculty()).setParameter("secRole", com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
        
        List<Person> people = q.getResultList();
        return (people != null)?people:new ArrayList<Person>();
    }
    
    public List<Person> findAllDRISMembersWhoCanApproveApplication(Application application)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p WHERE :secRole MEMBER OF p.securityRoleList", Person.class).setParameter("secRole", com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        
        List<Person> people = q.getResultList();
        return (people != null)?people:new ArrayList<Person>();
    }
    
    public List<Application> findAllApplicationsWhosFellowIs(Person fellow)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.fellow = :f", Application.class).setParameter("f", fellow);
        
        List<Application> applications = q.getResultList();
        return (applications != null)?applications:new ArrayList<Application>();
    }
    
    public List<Application> findAllApplicationsWhosGrantHolderIs(Person grantHolder)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE a.grantHolder = :gh", Application.class).setParameter("gh", grantHolder);
        
        List<Application> applications = q.getResultList();
        return (applications != null)?applications:new ArrayList<Application>();
    }
    
    public long countAllApplicationsWithStatus(String applicationStatus)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(a) FROM Application a WHERE a.status= :status", Long.class).setParameter("status", applicationStatus);
        return q.getSingleResult();
    }
    
    public long countAllApplicationsWithStatusAndReferee(String applicationStatus, Person referee)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(a) FROM Application a WHERE a.status= :status AND :ref MEMBER OF a.personList", Long.class).setParameter("status", applicationStatus).setParameter("ref", referee);
        return q.getSingleResult();
    }
    
    public long countAllApplicationsWithStatusAndGrantHolder(String applicationStatus, Person grantHolder)
    {
        EntityManager em = getEntityManager();
        System.out.println("===calling3 ");
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(a) FROM Application a WHERE a.status= :status AND a.grantHolder = :grantHolder", Long.class).setParameter("status", applicationStatus).setParameter("grantHolder", grantHolder);
        return q.getSingleResult();
    }
    
    public long countAllApplicationsWithStatusAndDepartment(String applicationStatus, Department deparment)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(a) FROM Application a WHERE a.status= :status AND a.grantHolder.employeeInformation.department = :dep", Long.class).setParameter("status", applicationStatus).setParameter("dep", deparment);
        return q.getSingleResult();
    }
    
    public long countAllApplicationsWithStatusAndFaculty(String applicationStatus, Faculty faculty)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(a) FROM Application a WHERE a.status= :status AND a.grantHolder.employeeInformation.department.faculty = :fac", Long.class).setParameter("status", applicationStatus).setParameter("fac", faculty);
        return q.getSingleResult();
    }
    
    public List<Application> getAllNewApplicationsForFellowWithEndDateInBetween(Person fellow, Date rangeStart, Date rangeEnd)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Application> q = em.createQuery("SELECT a FROM Application a WHERE (a.fellow = :fellow) AND (a.type = :type) AND (a.endDate BETWEEN :rangeStart AND :rangeEnd)", Application.class).setParameter("rangeStart", rangeStart).setParameter("rangeEnd", rangeEnd).setParameter("fellow", fellow).setParameter("type", com.softserve.constants.PersistenceConstants.APPLICATION_TYPE_NEW);
        
        List<Application> applications = q.getResultList();
        return (applications != null)?applications:new ArrayList<Application>();
    }
}
