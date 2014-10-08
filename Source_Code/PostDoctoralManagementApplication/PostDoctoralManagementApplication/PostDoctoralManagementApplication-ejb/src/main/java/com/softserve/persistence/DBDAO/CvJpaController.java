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
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.Experience;
import java.util.ArrayList;
import java.util.List;
import com.softserve.persistence.DBEntities.AcademicQualification;
import com.softserve.persistence.DBEntities.Cv;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class CvJpaController implements Serializable {

    public CvJpaController(EntityManager em) {
        this.emm = em;
    }
    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(Cv cv) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        create(getEntityManager(), cv);
    }

    public void create(EntityManager em, Cv cv) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        if (cv.getExperienceList() == null) {
            cv.setExperienceList(new ArrayList<Experience>());
        }
        if (cv.getAcademicQualificationList() == null) {
            cv.setAcademicQualificationList(new ArrayList<AcademicQualification>());
        }
        List<String> illegalOrphanMessages = null;
        Person personOrphanCheck = cv.getPerson();
        if (personOrphanCheck != null) {
            Cv oldCvOfPerson = personOrphanCheck.getCv();
            if (oldCvOfPerson != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Person " + personOrphanCheck + " already has an item of type Cv whose person column cannot be null. Please make another selection for the person field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        
        em.persist(cv);        
 
    }
    
    public Cv edit(Cv cv) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), cv);
    }

    public Cv edit(EntityManager em, Cv cv) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {

        String id = cv.getCvID();
        if (findCv(id) == null) {
            throw new NonexistentEntityException("The cv with id " + id + " no longer exists.");
        }
        
        Cv persistentCv = em.find(Cv.class, cv.getCvID());
        Person personOld = persistentCv.getPerson();
        Person personNew = cv.getPerson();
        List<Experience> experienceListOld = persistentCv.getExperienceList();
        List<Experience> experienceListNew = cv.getExperienceList();
        List<AcademicQualification> academicQualificationListOld = persistentCv.getAcademicQualificationList();
        List<AcademicQualification> academicQualificationListNew = cv.getAcademicQualificationList();
        List<String> illegalOrphanMessages = null;
        if (personNew != null && !personNew.equals(personOld)) {
            Cv oldCvOfPerson = personNew.getCv();
            if (oldCvOfPerson != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Person " + personNew + " already has an item of type Cv whose person column cannot be null. Please make another selection for the person field.");
            }
        }
        for (Experience experienceListOldExperience : experienceListOld) {
            if (!experienceListNew.contains(experienceListOldExperience)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Experience " + experienceListOldExperience + " since its cv field is not nullable.");
            }
        }
        for (AcademicQualification academicQualificationListOldAcademicQualification : academicQualificationListOld) {
            if (!academicQualificationListNew.contains(academicQualificationListOldAcademicQualification)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain AcademicQualification " + academicQualificationListOldAcademicQualification + " since its cv field is not nullable.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        
        return em.merge(cv);

    }
    
    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }
        
    public void destroy(EntityManager em, String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {

        Cv cv;
        try {
            cv = em.getReference(Cv.class, id);
            cv.getCvID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The cv with id " + id + " no longer exists.", enfe);
        }
        List<String> illegalOrphanMessages = null;
        List<Experience> experienceListOrphanCheck = cv.getExperienceList();
        for (Experience experienceListOrphanCheckExperience : experienceListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Cv (" + cv + ") cannot be destroyed since the Experience " + experienceListOrphanCheckExperience + " in its experienceList field has a non-nullable cv field.");
        }
        List<AcademicQualification> academicQualificationListOrphanCheck = cv.getAcademicQualificationList();
        for (AcademicQualification academicQualificationListOrphanCheckAcademicQualification : academicQualificationListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Cv (" + cv + ") cannot be destroyed since the AcademicQualification " + academicQualificationListOrphanCheckAcademicQualification + " in its academicQualificationList field has a non-nullable cv field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }

        em.remove(cv);
    }

    public List<Cv> findCvEntities() {
        return findCvEntities(true, -1, -1);
    }

    public List<Cv> findCvEntities(int maxResults, int firstResult) {
        return findCvEntities(false, maxResults, firstResult);
    }

    private List<Cv> findCvEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cv.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public Cv findCv(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cv.class, id);
        } finally {
            
        }
    }

    public int getCvCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cv> rt = cq.from(Cv.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
