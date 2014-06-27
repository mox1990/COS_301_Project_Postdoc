/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Endorsement;
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
public class EndorsementJpaController implements Serializable {

    public EndorsementJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Endorsement endorsement) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (endorsement.getApplicationList() == null) {
            endorsement.setApplicationList(new ArrayList<Application>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person deanID = endorsement.getDeanID();
            if (deanID != null) {
                deanID = em.getReference(deanID.getClass(), deanID.getSystemID());
                endorsement.setDeanID(deanID);
            }
            List<Application> attachedApplicationList = new ArrayList<Application>();
            for (Application applicationListApplicationToAttach : endorsement.getApplicationList()) {
                applicationListApplicationToAttach = em.getReference(applicationListApplicationToAttach.getClass(), applicationListApplicationToAttach.getApplicationID());
                attachedApplicationList.add(applicationListApplicationToAttach);
            }
            endorsement.setApplicationList(attachedApplicationList);
            em.persist(endorsement);
            if (deanID != null) {
                deanID.getEndorsementList().add(endorsement);
                deanID = em.merge(deanID);
            }
            for (Application applicationListApplication : endorsement.getApplicationList()) {
                Endorsement oldEndorsementIDOfApplicationListApplication = applicationListApplication.getEndorsementID();
                applicationListApplication.setEndorsementID(endorsement);
                applicationListApplication = em.merge(applicationListApplication);
                if (oldEndorsementIDOfApplicationListApplication != null) {
                    oldEndorsementIDOfApplicationListApplication.getApplicationList().remove(applicationListApplication);
                    oldEndorsementIDOfApplicationListApplication = em.merge(oldEndorsementIDOfApplicationListApplication);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEndorsement(endorsement.getEndorsementID()) != null) {
                throw new PreexistingEntityException("Endorsement " + endorsement + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Endorsement endorsement) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Endorsement persistentEndorsement = em.find(Endorsement.class, endorsement.getEndorsementID());
            Person deanIDOld = persistentEndorsement.getDeanID();
            Person deanIDNew = endorsement.getDeanID();
            List<Application> applicationListOld = persistentEndorsement.getApplicationList();
            List<Application> applicationListNew = endorsement.getApplicationList();
            if (deanIDNew != null) {
                deanIDNew = em.getReference(deanIDNew.getClass(), deanIDNew.getSystemID());
                endorsement.setDeanID(deanIDNew);
            }
            List<Application> attachedApplicationListNew = new ArrayList<Application>();
            for (Application applicationListNewApplicationToAttach : applicationListNew) {
                applicationListNewApplicationToAttach = em.getReference(applicationListNewApplicationToAttach.getClass(), applicationListNewApplicationToAttach.getApplicationID());
                attachedApplicationListNew.add(applicationListNewApplicationToAttach);
            }
            applicationListNew = attachedApplicationListNew;
            endorsement.setApplicationList(applicationListNew);
            endorsement = em.merge(endorsement);
            if (deanIDOld != null && !deanIDOld.equals(deanIDNew)) {
                deanIDOld.getEndorsementList().remove(endorsement);
                deanIDOld = em.merge(deanIDOld);
            }
            if (deanIDNew != null && !deanIDNew.equals(deanIDOld)) {
                deanIDNew.getEndorsementList().add(endorsement);
                deanIDNew = em.merge(deanIDNew);
            }
            for (Application applicationListOldApplication : applicationListOld) {
                if (!applicationListNew.contains(applicationListOldApplication)) {
                    applicationListOldApplication.setEndorsementID(null);
                    applicationListOldApplication = em.merge(applicationListOldApplication);
                }
            }
            for (Application applicationListNewApplication : applicationListNew) {
                if (!applicationListOld.contains(applicationListNewApplication)) {
                    Endorsement oldEndorsementIDOfApplicationListNewApplication = applicationListNewApplication.getEndorsementID();
                    applicationListNewApplication.setEndorsementID(endorsement);
                    applicationListNewApplication = em.merge(applicationListNewApplication);
                    if (oldEndorsementIDOfApplicationListNewApplication != null && !oldEndorsementIDOfApplicationListNewApplication.equals(endorsement)) {
                        oldEndorsementIDOfApplicationListNewApplication.getApplicationList().remove(applicationListNewApplication);
                        oldEndorsementIDOfApplicationListNewApplication = em.merge(oldEndorsementIDOfApplicationListNewApplication);
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
                Long id = endorsement.getEndorsementID();
                if (findEndorsement(id) == null) {
                    throw new NonexistentEntityException("The endorsement with id " + id + " no longer exists.");
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
            Endorsement endorsement;
            try {
                endorsement = em.getReference(Endorsement.class, id);
                endorsement.getEndorsementID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The endorsement with id " + id + " no longer exists.", enfe);
            }
            Person deanID = endorsement.getDeanID();
            if (deanID != null) {
                deanID.getEndorsementList().remove(endorsement);
                deanID = em.merge(deanID);
            }
            List<Application> applicationList = endorsement.getApplicationList();
            for (Application applicationListApplication : applicationList) {
                applicationListApplication.setEndorsementID(null);
                applicationListApplication = em.merge(applicationListApplication);
            }
            em.remove(endorsement);
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

    public List<Endorsement> findEndorsementEntities() {
        return findEndorsementEntities(true, -1, -1);
    }

    public List<Endorsement> findEndorsementEntities(int maxResults, int firstResult) {
        return findEndorsementEntities(false, maxResults, firstResult);
    }

    private List<Endorsement> findEndorsementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endorsement.class));
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

    public Endorsement findEndorsement(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endorsement.class, id);
        } finally {
            em.close();
        }
    }

    public int getEndorsementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Endorsement> rt = cq.from(Endorsement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
