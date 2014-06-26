/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
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

    public ExperienceJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Experience experience) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cv cvID = experience.getCvID();
            if (cvID != null) {
                cvID = em.getReference(cvID.getClass(), cvID.getCvID());
                experience.setCvID(cvID);
            }
            em.persist(experience);
            if (cvID != null) {
                cvID.getExperienceCollection().add(experience);
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

    public void edit(Experience experience) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Experience persistentExperience = em.find(Experience.class, experience.getExperienceID());
            Cv cvIDOld = persistentExperience.getCvID();
            Cv cvIDNew = experience.getCvID();
            if (cvIDNew != null) {
                cvIDNew = em.getReference(cvIDNew.getClass(), cvIDNew.getCvID());
                experience.setCvID(cvIDNew);
            }
            experience = em.merge(experience);
            if (cvIDOld != null && !cvIDOld.equals(cvIDNew)) {
                cvIDOld.getExperienceCollection().remove(experience);
                cvIDOld = em.merge(cvIDOld);
            }
            if (cvIDNew != null && !cvIDNew.equals(cvIDOld)) {
                cvIDNew.getExperienceCollection().add(experience);
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
                Long id = experience.getExperienceID();
                if (findExperience(id) == null) {
                    throw new NonexistentEntityException("The experience with id " + id + " no longer exists.");
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
            Experience experience;
            try {
                experience = em.getReference(Experience.class, id);
                experience.getExperienceID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The experience with id " + id + " no longer exists.", enfe);
            }
            Cv cvID = experience.getCvID();
            if (cvID != null) {
                cvID.getExperienceCollection().remove(experience);
                cvID = em.merge(cvID);
            }
            em.remove(experience);
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
            em.close();
        }
    }

    public Experience findExperience(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Experience.class, id);
        } finally {
            em.close();
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
            em.close();
        }
    }
    
}
