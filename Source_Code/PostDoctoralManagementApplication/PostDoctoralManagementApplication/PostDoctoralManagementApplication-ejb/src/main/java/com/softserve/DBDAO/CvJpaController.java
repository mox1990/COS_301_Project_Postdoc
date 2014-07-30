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

    public void create(Cv cv) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
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
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person person = cv.getPerson();
            if (person != null) {
                person = em.getReference(person.getClass(), person.getSystemID());
                cv.setPerson(person);
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
            if (person != null) {
                person.setCv(cv);
                person = em.merge(person);
            }
            for (Experience experienceListExperience : cv.getExperienceList()) {
                Cv oldCvOfExperienceListExperience = experienceListExperience.getCv();
                experienceListExperience.setCv(cv);
                experienceListExperience = em.merge(experienceListExperience);
                if (oldCvOfExperienceListExperience != null) {
                    oldCvOfExperienceListExperience.getExperienceList().remove(experienceListExperience);
                    oldCvOfExperienceListExperience = em.merge(oldCvOfExperienceListExperience);
                }
            }
            for (AcademicQualification academicQualificationListAcademicQualification : cv.getAcademicQualificationList()) {
                Cv oldCvOfAcademicQualificationListAcademicQualification = academicQualificationListAcademicQualification.getCv();
                academicQualificationListAcademicQualification.setCv(cv);
                academicQualificationListAcademicQualification = em.merge(academicQualificationListAcademicQualification);
                if (oldCvOfAcademicQualificationListAcademicQualification != null) {
                    oldCvOfAcademicQualificationListAcademicQualification.getAcademicQualificationList().remove(academicQualificationListAcademicQualification);
                    oldCvOfAcademicQualificationListAcademicQualification = em.merge(oldCvOfAcademicQualificationListAcademicQualification);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCv(cv.getCvID()) != null) {
                throw new PreexistingEntityException("Cv " + cv + " already exists.", ex);
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
            if (personNew != null) {
                personNew = em.getReference(personNew.getClass(), personNew.getSystemID());
                cv.setPerson(personNew);
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
            if (personOld != null && !personOld.equals(personNew)) {
                personOld.setCv(null);
                personOld = em.merge(personOld);
            }
            if (personNew != null && !personNew.equals(personOld)) {
                personNew.setCv(cv);
                personNew = em.merge(personNew);
            }
            for (Experience experienceListNewExperience : experienceListNew) {
                if (!experienceListOld.contains(experienceListNewExperience)) {
                    Cv oldCvOfExperienceListNewExperience = experienceListNewExperience.getCv();
                    experienceListNewExperience.setCv(cv);
                    experienceListNewExperience = em.merge(experienceListNewExperience);
                    if (oldCvOfExperienceListNewExperience != null && !oldCvOfExperienceListNewExperience.equals(cv)) {
                        oldCvOfExperienceListNewExperience.getExperienceList().remove(experienceListNewExperience);
                        oldCvOfExperienceListNewExperience = em.merge(oldCvOfExperienceListNewExperience);
                    }
                }
            }
            for (AcademicQualification academicQualificationListNewAcademicQualification : academicQualificationListNew) {
                if (!academicQualificationListOld.contains(academicQualificationListNewAcademicQualification)) {
                    Cv oldCvOfAcademicQualificationListNewAcademicQualification = academicQualificationListNewAcademicQualification.getCv();
                    academicQualificationListNewAcademicQualification.setCv(cv);
                    academicQualificationListNewAcademicQualification = em.merge(academicQualificationListNewAcademicQualification);
                    if (oldCvOfAcademicQualificationListNewAcademicQualification != null && !oldCvOfAcademicQualificationListNewAcademicQualification.equals(cv)) {
                        oldCvOfAcademicQualificationListNewAcademicQualification.getAcademicQualificationList().remove(academicQualificationListNewAcademicQualification);
                        oldCvOfAcademicQualificationListNewAcademicQualification = em.merge(oldCvOfAcademicQualificationListNewAcademicQualification);
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
                String id = cv.getCvID();
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
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
            Person person = cv.getPerson();
            if (person != null) {
                person.setCv(null);
                person = em.merge(person);
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

    public Cv findCv(String id) {
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
