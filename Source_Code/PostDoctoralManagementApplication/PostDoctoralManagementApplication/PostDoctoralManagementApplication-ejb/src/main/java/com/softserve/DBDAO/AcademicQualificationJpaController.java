/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
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

    public AcademicQualificationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AcademicQualification academicQualification) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cv cvID = academicQualification.getCvID();
            if (cvID != null) {
                cvID = em.getReference(cvID.getClass(), cvID.getCvID());
                academicQualification.setCvID(cvID);
            }
            em.persist(academicQualification);
            if (cvID != null) {
                cvID.getAcademicQualificationCollection().add(academicQualification);
                cvID = em.merge(cvID);
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

    public void edit(AcademicQualification academicQualification) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AcademicQualification persistentAcademicQualification = em.find(AcademicQualification.class, academicQualification.getQualificationID());
            Cv cvIDOld = persistentAcademicQualification.getCvID();
            Cv cvIDNew = academicQualification.getCvID();
            if (cvIDNew != null) {
                cvIDNew = em.getReference(cvIDNew.getClass(), cvIDNew.getCvID());
                academicQualification.setCvID(cvIDNew);
            }
            academicQualification = em.merge(academicQualification);
            if (cvIDOld != null && !cvIDOld.equals(cvIDNew)) {
                cvIDOld.getAcademicQualificationCollection().remove(academicQualification);
                cvIDOld = em.merge(cvIDOld);
            }
            if (cvIDNew != null && !cvIDNew.equals(cvIDOld)) {
                cvIDNew.getAcademicQualificationCollection().add(academicQualification);
                cvIDNew = em.merge(cvIDNew);
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
                Long id = academicQualification.getQualificationID();
                if (findAcademicQualification(id) == null) {
                    throw new NonexistentEntityException("The academicQualification with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AcademicQualification academicQualification;
            try {
                academicQualification = em.getReference(AcademicQualification.class, id);
                academicQualification.getQualificationID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The academicQualification with id " + id + " no longer exists.", enfe);
            }
            Cv cvID = academicQualification.getCvID();
            if (cvID != null) {
                cvID.getAcademicQualificationCollection().remove(academicQualification);
                cvID = em.merge(cvID);
            }
            em.remove(academicQualification);
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
