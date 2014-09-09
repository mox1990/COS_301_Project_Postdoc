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
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.EligiblityReport;
import com.softserve.DBEntities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class EligiblityReportJpaController implements Serializable {

    public EligiblityReportJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(EligiblityReport eligiblityReport) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        create(getEntityManager(), eligiblityReport);
    }

    public void create(EntityManager em, EligiblityReport eligiblityReport) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        List<String> illegalOrphanMessages = null;
        Application applicationOrphanCheck = eligiblityReport.getApplication();
        if (applicationOrphanCheck != null) {
            EligiblityReport oldEligiblityReportOfApplication = applicationOrphanCheck.getEligiblityReport();
            if (oldEligiblityReportOfApplication != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Application " + applicationOrphanCheck + " already has an item of type EligiblityReport whose application column cannot be null. Please make another selection for the application field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }

        Application application = eligiblityReport.getApplication();
        if (application != null) {
            application = em.getReference(application.getClass(), application.getApplicationID());
            eligiblityReport.setApplication(application);
        }
        Person eligiblityChecker = eligiblityReport.getEligiblityChecker();
        if (eligiblityChecker != null) {
            eligiblityChecker = em.getReference(eligiblityChecker.getClass(), eligiblityChecker.getSystemID());
            eligiblityReport.setEligiblityChecker(eligiblityChecker);
        }
        em.persist(eligiblityReport);
        if (application != null) {
            application.setEligiblityReport(eligiblityReport);
            application = em.merge(application);
        }
        if (eligiblityChecker != null) {
            eligiblityChecker.getEligiblityReportList().add(eligiblityReport);
            eligiblityChecker = em.merge(eligiblityChecker);
        }
            
    }
    
    public void edit(EligiblityReport eligiblityReport) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        edit(getEntityManager(), eligiblityReport);
    }

    public void edit(EntityManager em, EligiblityReport eligiblityReport) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        
        Long id = eligiblityReport.getReportID();
        if (findEligiblityReport(id) == null) {
            throw new NonexistentEntityException("The eligiblityReport with id " + id + " no longer exists.");
        }
        
        EligiblityReport persistentEligiblityReport = em.find(EligiblityReport.class, eligiblityReport.getReportID());
        Application applicationOld = persistentEligiblityReport.getApplication();
        Application applicationNew = eligiblityReport.getApplication();
        Person eligiblityCheckerOld = persistentEligiblityReport.getEligiblityChecker();
        Person eligiblityCheckerNew = eligiblityReport.getEligiblityChecker();
        List<String> illegalOrphanMessages = null;
        if (applicationNew != null && !applicationNew.equals(applicationOld)) {
            EligiblityReport oldEligiblityReportOfApplication = applicationNew.getEligiblityReport();
            if (oldEligiblityReportOfApplication != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Application " + applicationNew + " already has an item of type EligiblityReport whose application column cannot be null. Please make another selection for the application field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        if (applicationNew != null) {
            applicationNew = em.getReference(applicationNew.getClass(), applicationNew.getApplicationID());
            eligiblityReport.setApplication(applicationNew);
        }
        if (eligiblityCheckerNew != null) {
            eligiblityCheckerNew = em.getReference(eligiblityCheckerNew.getClass(), eligiblityCheckerNew.getSystemID());
            eligiblityReport.setEligiblityChecker(eligiblityCheckerNew);
        }
        eligiblityReport = em.merge(eligiblityReport);
        if (applicationOld != null && !applicationOld.equals(applicationNew)) {
            applicationOld.setEligiblityReport(null);
            applicationOld = em.merge(applicationOld);
        }
        if (applicationNew != null && !applicationNew.equals(applicationOld)) {
            applicationNew.setEligiblityReport(eligiblityReport);
            applicationNew = em.merge(applicationNew);
        }
        if (eligiblityCheckerOld != null && !eligiblityCheckerOld.equals(eligiblityCheckerNew)) {
            eligiblityCheckerOld.getEligiblityReportList().remove(eligiblityReport);
            eligiblityCheckerOld = em.merge(eligiblityCheckerOld);
        }
        if (eligiblityCheckerNew != null && !eligiblityCheckerNew.equals(eligiblityCheckerOld)) {
            eligiblityCheckerNew.getEligiblityReportList().add(eligiblityReport);
            eligiblityCheckerNew = em.merge(eligiblityCheckerNew);
        }
            

                
          
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        
        EligiblityReport eligiblityReport;
        try {
            eligiblityReport = em.getReference(EligiblityReport.class, id);
            eligiblityReport.getReportID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The eligiblityReport with id " + id + " no longer exists.", enfe);
        }
        Application application = eligiblityReport.getApplication();
        if (application != null) {
            application.setEligiblityReport(null);
            application = em.merge(application);
        }
        Person eligiblityChecker = eligiblityReport.getEligiblityChecker();
        if (eligiblityChecker != null) {
            eligiblityChecker.getEligiblityReportList().remove(eligiblityReport);
            eligiblityChecker = em.merge(eligiblityChecker);
        }
        em.remove(eligiblityReport);
            
    }

    public List<EligiblityReport> findEligiblityReportEntities() {
        return findEligiblityReportEntities(true, -1, -1);
    }

    public List<EligiblityReport> findEligiblityReportEntities(int maxResults, int firstResult) {
        return findEligiblityReportEntities(false, maxResults, firstResult);
    }

    private List<EligiblityReport> findEligiblityReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EligiblityReport.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public EligiblityReport findEligiblityReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EligiblityReport.class, id);
        } finally {
            
        }
    }

    public int getEligiblityReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EligiblityReport> rt = cq.from(EligiblityReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
