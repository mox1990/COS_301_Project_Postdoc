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
import java.util.List;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Cv;
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
        if (cv.getExperienceList() == null) {
            cv.setExperienceList(new ArrayList<Experience>());
        }
        if (cv.getAcademicQualificationList() == null) {
            cv.setAcademicQualificationList(new ArrayList<AcademicQualification>());
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
            List<Experience> attachedExperienceList = new ArrayList<Experience>();
            for (Experience experienceListExperienceToAttach : cv.getExperienceList()) {
                experienceListExperienceToAttach = em.getReference(experienceListExperienceToAttach.getClass(), experienceListExperienceToAttach.getExperienceID());
                attachedExperienceList.add(experienceListExperienceToAttach);
            }
            cv.setExperienceList(attachedExperienceList);
            List<AcademicQualification> attachedAcademicQualificationList = new ArrayList<AcademicQualification>();
            for (AcademicQualification academicQualificationListAcademicQualificationToAttach : cv.getAcademicQualificationList()) {
                academicQualificationListAcademicQualificationToAttach = em.getReference(academicQualificationListAcademicQualificationToAttach.getClass(), academicQualificationListAcademicQualificationToAttach.getQualificationID());
                attachedAcademicQualificationList.add(academicQualificationListAcademicQualificationToAttach);
            }
            cv.setAcademicQualificationList(attachedAcademicQualificationList);
            em.persist(cv);
            if (ownerID != null) {
                ownerID.getCvList().add(cv);
                ownerID = em.merge(ownerID);
            }
            for (Experience experienceListExperience : cv.getExperienceList()) {
                Cv oldCvIDOfExperienceListExperience = experienceListExperience.getCvID();
                experienceListExperience.setCvID(cv);
                experienceListExperience = em.merge(experienceListExperience);
                if (oldCvIDOfExperienceListExperience != null) {
                    oldCvIDOfExperienceListExperience.getExperienceList().remove(experienceListExperience);
                    oldCvIDOfExperienceListExperience = em.merge(oldCvIDOfExperienceListExperience);
                }
            }
            for (AcademicQualification academicQualificationListAcademicQualification : cv.getAcademicQualificationList()) {
                Cv oldCvIDOfAcademicQualificationListAcademicQualification = academicQualificationListAcademicQualification.getCvID();
                academicQualificationListAcademicQualification.setCvID(cv);
                academicQualificationListAcademicQualification = em.merge(academicQualificationListAcademicQualification);
                if (oldCvIDOfAcademicQualificationListAcademicQualification != null) {
                    oldCvIDOfAcademicQualificationListAcademicQualification.getAcademicQualificationList().remove(academicQualificationListAcademicQualification);
                    oldCvIDOfAcademicQualificationListAcademicQualification = em.merge(oldCvIDOfAcademicQualificationListAcademicQualification);
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
            List<Experience> experienceListOld = persistentCv.getExperienceList();
            List<Experience> experienceListNew = cv.getExperienceList();
            List<AcademicQualification> academicQualificationListOld = persistentCv.getAcademicQualificationList();
            List<AcademicQualification> academicQualificationListNew = cv.getAcademicQualificationList();
            List<String> illegalOrphanMessages = null;
            for (Experience experienceListOldExperience : experienceListOld) {
                if (!experienceListNew.contains(experienceListOldExperience)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Experience " + experienceListOldExperience + " since its cvID field is not nullable.");
                }
            }
            for (AcademicQualification academicQualificationListOldAcademicQualification : academicQualificationListOld) {
                if (!academicQualificationListNew.contains(academicQualificationListOldAcademicQualification)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AcademicQualification " + academicQualificationListOldAcademicQualification + " since its cvID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ownerIDNew != null) {
                ownerIDNew = em.getReference(ownerIDNew.getClass(), ownerIDNew.getSystemID());
                cv.setOwnerID(ownerIDNew);
            }
            List<Experience> attachedExperienceListNew = new ArrayList<Experience>();
            for (Experience experienceListNewExperienceToAttach : experienceListNew) {
                experienceListNewExperienceToAttach = em.getReference(experienceListNewExperienceToAttach.getClass(), experienceListNewExperienceToAttach.getExperienceID());
                attachedExperienceListNew.add(experienceListNewExperienceToAttach);
            }
            experienceListNew = attachedExperienceListNew;
            cv.setExperienceList(experienceListNew);
            List<AcademicQualification> attachedAcademicQualificationListNew = new ArrayList<AcademicQualification>();
            for (AcademicQualification academicQualificationListNewAcademicQualificationToAttach : academicQualificationListNew) {
                academicQualificationListNewAcademicQualificationToAttach = em.getReference(academicQualificationListNewAcademicQualificationToAttach.getClass(), academicQualificationListNewAcademicQualificationToAttach.getQualificationID());
                attachedAcademicQualificationListNew.add(academicQualificationListNewAcademicQualificationToAttach);
            }
            academicQualificationListNew = attachedAcademicQualificationListNew;
            cv.setAcademicQualificationList(academicQualificationListNew);
            cv = em.merge(cv);
            if (ownerIDOld != null && !ownerIDOld.equals(ownerIDNew)) {
                ownerIDOld.getCvList().remove(cv);
                ownerIDOld = em.merge(ownerIDOld);
            }
            if (ownerIDNew != null && !ownerIDNew.equals(ownerIDOld)) {
                ownerIDNew.getCvList().add(cv);
                ownerIDNew = em.merge(ownerIDNew);
            }
            for (Experience experienceListNewExperience : experienceListNew) {
                if (!experienceListOld.contains(experienceListNewExperience)) {
                    Cv oldCvIDOfExperienceListNewExperience = experienceListNewExperience.getCvID();
                    experienceListNewExperience.setCvID(cv);
                    experienceListNewExperience = em.merge(experienceListNewExperience);
                    if (oldCvIDOfExperienceListNewExperience != null && !oldCvIDOfExperienceListNewExperience.equals(cv)) {
                        oldCvIDOfExperienceListNewExperience.getExperienceList().remove(experienceListNewExperience);
                        oldCvIDOfExperienceListNewExperience = em.merge(oldCvIDOfExperienceListNewExperience);
                    }
                }
            }
            for (AcademicQualification academicQualificationListNewAcademicQualification : academicQualificationListNew) {
                if (!academicQualificationListOld.contains(academicQualificationListNewAcademicQualification)) {
                    Cv oldCvIDOfAcademicQualificationListNewAcademicQualification = academicQualificationListNewAcademicQualification.getCvID();
                    academicQualificationListNewAcademicQualification.setCvID(cv);
                    academicQualificationListNewAcademicQualification = em.merge(academicQualificationListNewAcademicQualification);
                    if (oldCvIDOfAcademicQualificationListNewAcademicQualification != null && !oldCvIDOfAcademicQualificationListNewAcademicQualification.equals(cv)) {
                        oldCvIDOfAcademicQualificationListNewAcademicQualification.getAcademicQualificationList().remove(academicQualificationListNewAcademicQualification);
                        oldCvIDOfAcademicQualificationListNewAcademicQualification = em.merge(oldCvIDOfAcademicQualificationListNewAcademicQualification);
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
            List<Experience> experienceListOrphanCheck = cv.getExperienceList();
            for (Experience experienceListOrphanCheckExperience : experienceListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cv (" + cv + ") cannot be destroyed since the Experience " + experienceListOrphanCheckExperience + " in its experienceList field has a non-nullable cvID field.");
            }
            List<AcademicQualification> academicQualificationListOrphanCheck = cv.getAcademicQualificationList();
            for (AcademicQualification academicQualificationListOrphanCheckAcademicQualification : academicQualificationListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cv (" + cv + ") cannot be destroyed since the AcademicQualification " + academicQualificationListOrphanCheckAcademicQualification + " in its academicQualificationList field has a non-nullable cvID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Person ownerID = cv.getOwnerID();
            if (ownerID != null) {
                ownerID.getCvList().remove(cv);
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
