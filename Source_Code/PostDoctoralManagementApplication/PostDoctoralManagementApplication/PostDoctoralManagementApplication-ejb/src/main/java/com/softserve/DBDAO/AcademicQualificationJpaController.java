/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.AcademicQualification;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Cv;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class AcademicQualificationJpaController implements Serializable {

    public AcademicQualificationJpaController(EntityManager em) {
        this.emm = em;
    }
    

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(AcademicQualification academicQualification) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), academicQualification);
    }
    
    public void create(EntityManager em, AcademicQualification academicQualification) throws RollbackFailureException, Exception 
    {
        Cv cv = academicQualification.getCv();
        if (cv != null) {
            cv = em.getReference(cv.getClass(), cv.getCvID());
            academicQualification.setCv(cv);
        }
        
        em.persist(academicQualification);
        
        if (cv != null) {
            cv.getAcademicQualificationList().add(academicQualification);
            cv = em.merge(cv);
        }

    }
    
    public void edit(AcademicQualification academicQualification) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        edit(getEntityManager(), academicQualification);
    }
    
    public void edit(EntityManager em, AcademicQualification academicQualification) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = academicQualification.getQualificationID();
        if (findAcademicQualification(id) == null) {
            throw new NonexistentEntityException("The academicQualification with id " + id + " no longer exists.");
        }

        AcademicQualification persistentAcademicQualification = em.find(AcademicQualification.class, academicQualification.getQualificationID());
        Cv cvOld = persistentAcademicQualification.getCv();
        Cv cvNew = academicQualification.getCv();
        if (cvNew != null) {
            cvNew = em.getReference(cvNew.getClass(), cvNew.getCvID());
            academicQualification.setCv(cvNew);
        }

        academicQualification = em.merge(academicQualification);

        if (cvOld != null && !cvOld.equals(cvNew)) {
            cvOld.getAcademicQualificationList().remove(academicQualification);
            cvOld = em.merge(cvOld);
        }
        if (cvNew != null && !cvNew.equals(cvOld)) {
            cvNew.getAcademicQualificationList().add(academicQualification);
            cvNew = em.merge(cvNew);
        }

    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        AcademicQualification academicQualification;
        try {
            academicQualification = em.getReference(AcademicQualification.class, id);
            academicQualification.getQualificationID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The academicQualification with id " + id + " no longer exists.", enfe);
        }
        
        Cv cv = academicQualification.getCv();
        if (cv != null) {
            cv.getAcademicQualificationList().remove(academicQualification);
            cv = em.merge(cv);
        }
        
        em.remove(academicQualification);
    }

    public List<AcademicQualification> findAcademicQualificationEntities() {
        return findAcademicQualificationEntities(true, -1, -1);
    }

    public List<AcademicQualification> findAcademicQualificationEntities(int maxResults, int firstResult) {
        return findAcademicQualificationEntities(false, maxResults, firstResult);
    }

    private List<AcademicQualification> findAcademicQualificationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AcademicQualification.class));
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

    public AcademicQualification findAcademicQualification(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AcademicQualification.class, id);
        } finally {
            em.close();
        }
    }

    public int getAcademicQualificationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AcademicQualification> rt = cq.from(AcademicQualification.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
