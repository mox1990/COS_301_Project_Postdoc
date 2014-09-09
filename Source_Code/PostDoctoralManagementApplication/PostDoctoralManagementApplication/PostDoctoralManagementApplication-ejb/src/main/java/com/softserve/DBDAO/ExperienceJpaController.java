/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Experience;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ExperienceJpaController implements Serializable {

    public ExperienceJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(Experience experience) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), experience);
    }
    

    public void create(EntityManager em, Experience experience) throws RollbackFailureException, Exception 
    {
        Cv cv = experience.getCv();
        if (cv != null) {
            cv = em.getReference(cv.getClass(), cv.getCvID());
            experience.setCv(cv);
        }
        em.persist(experience);
        if (cv != null) {
            cv.getExperienceList().add(experience);
            cv = em.merge(cv);
        }

    }
    
    public void edit(Experience experience) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        edit(getEntityManager(), experience);
    }

    public void edit(EntityManager em, Experience experience) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        
        Long id = experience.getExperienceID();
        if (findExperience(id) == null) {
            throw new NonexistentEntityException("The experience with id " + id + " no longer exists.");
        }
        
        Experience persistentExperience = em.find(Experience.class, experience.getExperienceID());
        Cv cvOld = persistentExperience.getCv();
        Cv cvNew = experience.getCv();
        if (cvNew != null) {
            cvNew = em.getReference(cvNew.getClass(), cvNew.getCvID());
            experience.setCv(cvNew);
        }
        experience = em.merge(experience);
        if (cvOld != null && !cvOld.equals(cvNew)) {
            cvOld.getExperienceList().remove(experience);
            cvOld = em.merge(cvOld);
        }
        if (cvNew != null && !cvNew.equals(cvOld)) {
            cvNew.getExperienceList().add(experience);
            cvNew = em.merge(cvNew);
        }
            
            

    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }
    
    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception {

        Experience experience;
        try {
            experience = em.getReference(Experience.class, id);
            experience.getExperienceID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The experience with id " + id + " no longer exists.", enfe);
        }
        Cv cv = experience.getCv();
        if (cv != null) {
            cv.getExperienceList().remove(experience);
            cv = em.merge(cv);
        }
        em.remove(experience);
            
    }

    public List<Experience> findExperienceEntities() {
        return findExperienceEntities(true, -1, -1);
    }

    public List<Experience> findExperienceEntities(int maxResults, int firstResult) {
        return findExperienceEntities(false, maxResults, firstResult);
    }

    private List<Experience> findExperienceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Experience.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public Experience findExperience(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Experience.class, id);
        } finally {
            
        }
    }

    public int getExperienceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Experience> rt = cq.from(Experience.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
