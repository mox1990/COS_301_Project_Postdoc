/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
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
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.Experience;
import java.util.ArrayList;
import java.util.Collection;
import com.softserve.DBEntities.AcademicQualification;
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
public class CvJpaController implements Serializable {

    public CvJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cv cv) throws RollbackFailureException, Exception {
        if (cv.getExperienceCollection() == null) {
            cv.setExperienceCollection(new ArrayList<Experience>());
        }
        if (cv.getAcademicQualificationCollection() == null) {
            cv.setAcademicQualificationCollection(new ArrayList<AcademicQualification>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person ownerID = cv.getOwnerID();
            if (ownerID != null) {
                ownerID = em.getReference(ownerID.getClass(), ownerID.getSystemID());
                cv.setOwnerID(ownerID);
            }
            Collection<Experience> attachedExperienceCollection = new ArrayList<Experience>();
            for (Experience experienceCollectionExperienceToAttach : cv.getExperienceCollection()) {
                experienceCollectionExperienceToAttach = em.getReference(experienceCollectionExperienceToAttach.getClass(), experienceCollectionExperienceToAttach.getExperienceID());
                attachedExperienceCollection.add(experienceCollectionExperienceToAttach);
            }
            cv.setExperienceCollection(attachedExperienceCollection);
            Collection<AcademicQualification> attachedAcademicQualificationCollection = new ArrayList<AcademicQualification>();
            for (AcademicQualification academicQualificationCollectionAcademicQualificationToAttach : cv.getAcademicQualificationCollection()) {
                academicQualificationCollectionAcademicQualificationToAttach = em.getReference(academicQualificationCollectionAcademicQualificationToAttach.getClass(), academicQualificationCollectionAcademicQualificationToAttach.getQualificationID());
                attachedAcademicQualificationCollection.add(academicQualificationCollectionAcademicQualificationToAttach);
            }
            cv.setAcademicQualificationCollection(attachedAcademicQualificationCollection);
            em.persist(cv);
            if (ownerID != null) {
                ownerID.getCvCollection().add(cv);
                ownerID = em.merge(ownerID);
            }
            for (Experience experienceCollectionExperience : cv.getExperienceCollection()) {
                Cv oldCvIDOfExperienceCollectionExperience = experienceCollectionExperience.getCvID();
                experienceCollectionExperience.setCvID(cv);
                experienceCollectionExperience = em.merge(experienceCollectionExperience);
                if (oldCvIDOfExperienceCollectionExperience != null) {
                    oldCvIDOfExperienceCollectionExperience.getExperienceCollection().remove(experienceCollectionExperience);
                    oldCvIDOfExperienceCollectionExperience = em.merge(oldCvIDOfExperienceCollectionExperience);
                }
            }
            for (AcademicQualification academicQualificationCollectionAcademicQualification : cv.getAcademicQualificationCollection()) {
                Cv oldCvIDOfAcademicQualificationCollectionAcademicQualification = academicQualificationCollectionAcademicQualification.getCvID();
                academicQualificationCollectionAcademicQualification.setCvID(cv);
                academicQualificationCollectionAcademicQualification = em.merge(academicQualificationCollectionAcademicQualification);
                if (oldCvIDOfAcademicQualificationCollectionAcademicQualification != null) {
                    oldCvIDOfAcademicQualificationCollectionAcademicQualification.getAcademicQualificationCollection().remove(academicQualificationCollectionAcademicQualification);
                    oldCvIDOfAcademicQualificationCollectionAcademicQualification = em.merge(oldCvIDOfAcademicQualificationCollectionAcademicQualification);
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

    public void edit(Cv cv) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cv persistentCv = em.find(Cv.class, cv.getCvID());
            Person ownerIDOld = persistentCv.getOwnerID();
            Person ownerIDNew = cv.getOwnerID();
            Collection<Experience> experienceCollectionOld = persistentCv.getExperienceCollection();
            Collection<Experience> experienceCollectionNew = cv.getExperienceCollection();
            Collection<AcademicQualification> academicQualificationCollectionOld = persistentCv.getAcademicQualificationCollection();
            Collection<AcademicQualification> academicQualificationCollectionNew = cv.getAcademicQualificationCollection();
            List<String> illegalOrphanMessages = null;
            for (Experience experienceCollectionOldExperience : experienceCollectionOld) {
                if (!experienceCollectionNew.contains(experienceCollectionOldExperience)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Experience " + experienceCollectionOldExperience + " since its cvID field is not nullable.");
                }
            }
            for (AcademicQualification academicQualificationCollectionOldAcademicQualification : academicQualificationCollectionOld) {
                if (!academicQualificationCollectionNew.contains(academicQualificationCollectionOldAcademicQualification)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AcademicQualification " + academicQualificationCollectionOldAcademicQualification + " since its cvID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ownerIDNew != null) {
                ownerIDNew = em.getReference(ownerIDNew.getClass(), ownerIDNew.getSystemID());
                cv.setOwnerID(ownerIDNew);
            }
            Collection<Experience> attachedExperienceCollectionNew = new ArrayList<Experience>();
            for (Experience experienceCollectionNewExperienceToAttach : experienceCollectionNew) {
                experienceCollectionNewExperienceToAttach = em.getReference(experienceCollectionNewExperienceToAttach.getClass(), experienceCollectionNewExperienceToAttach.getExperienceID());
                attachedExperienceCollectionNew.add(experienceCollectionNewExperienceToAttach);
            }
            experienceCollectionNew = attachedExperienceCollectionNew;
            cv.setExperienceCollection(experienceCollectionNew);
            Collection<AcademicQualification> attachedAcademicQualificationCollectionNew = new ArrayList<AcademicQualification>();
            for (AcademicQualification academicQualificationCollectionNewAcademicQualificationToAttach : academicQualificationCollectionNew) {
                academicQualificationCollectionNewAcademicQualificationToAttach = em.getReference(academicQualificationCollectionNewAcademicQualificationToAttach.getClass(), academicQualificationCollectionNewAcademicQualificationToAttach.getQualificationID());
                attachedAcademicQualificationCollectionNew.add(academicQualificationCollectionNewAcademicQualificationToAttach);
            }
            academicQualificationCollectionNew = attachedAcademicQualificationCollectionNew;
            cv.setAcademicQualificationCollection(academicQualificationCollectionNew);
            cv = em.merge(cv);
            if (ownerIDOld != null && !ownerIDOld.equals(ownerIDNew)) {
                ownerIDOld.getCvCollection().remove(cv);
                ownerIDOld = em.merge(ownerIDOld);
            }
            if (ownerIDNew != null && !ownerIDNew.equals(ownerIDOld)) {
                ownerIDNew.getCvCollection().add(cv);
                ownerIDNew = em.merge(ownerIDNew);
            }
            for (Experience experienceCollectionNewExperience : experienceCollectionNew) {
                if (!experienceCollectionOld.contains(experienceCollectionNewExperience)) {
                    Cv oldCvIDOfExperienceCollectionNewExperience = experienceCollectionNewExperience.getCvID();
                    experienceCollectionNewExperience.setCvID(cv);
                    experienceCollectionNewExperience = em.merge(experienceCollectionNewExperience);
                    if (oldCvIDOfExperienceCollectionNewExperience != null && !oldCvIDOfExperienceCollectionNewExperience.equals(cv)) {
                        oldCvIDOfExperienceCollectionNewExperience.getExperienceCollection().remove(experienceCollectionNewExperience);
                        oldCvIDOfExperienceCollectionNewExperience = em.merge(oldCvIDOfExperienceCollectionNewExperience);
                    }
                }
            }
            for (AcademicQualification academicQualificationCollectionNewAcademicQualification : academicQualificationCollectionNew) {
                if (!academicQualificationCollectionOld.contains(academicQualificationCollectionNewAcademicQualification)) {
                    Cv oldCvIDOfAcademicQualificationCollectionNewAcademicQualification = academicQualificationCollectionNewAcademicQualification.getCvID();
                    academicQualificationCollectionNewAcademicQualification.setCvID(cv);
                    academicQualificationCollectionNewAcademicQualification = em.merge(academicQualificationCollectionNewAcademicQualification);
                    if (oldCvIDOfAcademicQualificationCollectionNewAcademicQualification != null && !oldCvIDOfAcademicQualificationCollectionNewAcademicQualification.equals(cv)) {
                        oldCvIDOfAcademicQualificationCollectionNewAcademicQualification.getAcademicQualificationCollection().remove(academicQualificationCollectionNewAcademicQualification);
                        oldCvIDOfAcademicQualificationCollectionNewAcademicQualification = em.merge(oldCvIDOfAcademicQualificationCollectionNewAcademicQualification);
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
                Long id = cv.getCvID();
                if (findCv(id) == null) {
                    throw new NonexistentEntityException("The cv with id " + id + " no longer exists.");
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
            Cv cv;
            try {
                cv = em.getReference(Cv.class, id);
                cv.getCvID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cv with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Experience> experienceCollectionOrphanCheck = cv.getExperienceCollection();
            for (Experience experienceCollectionOrphanCheckExperience : experienceCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cv (" + cv + ") cannot be destroyed since the Experience " + experienceCollectionOrphanCheckExperience + " in its experienceCollection field has a non-nullable cvID field.");
            }
            Collection<AcademicQualification> academicQualificationCollectionOrphanCheck = cv.getAcademicQualificationCollection();
            for (AcademicQualification academicQualificationCollectionOrphanCheckAcademicQualification : academicQualificationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cv (" + cv + ") cannot be destroyed since the AcademicQualification " + academicQualificationCollectionOrphanCheckAcademicQualification + " in its academicQualificationCollection field has a non-nullable cvID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Person ownerID = cv.getOwnerID();
            if (ownerID != null) {
                ownerID.getCvCollection().remove(cv);
                ownerID = em.merge(ownerID);
            }
            em.remove(cv);
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
            em.close();
        }
    }

    public Cv findCv(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cv.class, id);
        } finally {
            em.close();
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
            em.close();
        }
    }
    
}
