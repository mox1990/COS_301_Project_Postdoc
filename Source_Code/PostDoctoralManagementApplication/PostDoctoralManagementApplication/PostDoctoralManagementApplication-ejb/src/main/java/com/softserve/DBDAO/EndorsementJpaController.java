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
import java.util.Collection;
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
        if (endorsement.getApplicationCollection() == null) {
            endorsement.setApplicationCollection(new ArrayList<Application>());
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
            Collection<Application> attachedApplicationCollection = new ArrayList<Application>();
            for (Application applicationCollectionApplicationToAttach : endorsement.getApplicationCollection()) {
                applicationCollectionApplicationToAttach = em.getReference(applicationCollectionApplicationToAttach.getClass(), applicationCollectionApplicationToAttach.getApplicationID());
                attachedApplicationCollection.add(applicationCollectionApplicationToAttach);
            }
            endorsement.setApplicationCollection(attachedApplicationCollection);
            em.persist(endorsement);
            if (deanID != null) {
                deanID.getEndorsementCollection().add(endorsement);
                deanID = em.merge(deanID);
            }
            for (Application applicationCollectionApplication : endorsement.getApplicationCollection()) {
                Endorsement oldEndorsementIDOfApplicationCollectionApplication = applicationCollectionApplication.getEndorsementID();
                applicationCollectionApplication.setEndorsementID(endorsement);
                applicationCollectionApplication = em.merge(applicationCollectionApplication);
                if (oldEndorsementIDOfApplicationCollectionApplication != null) {
                    oldEndorsementIDOfApplicationCollectionApplication.getApplicationCollection().remove(applicationCollectionApplication);
                    oldEndorsementIDOfApplicationCollectionApplication = em.merge(oldEndorsementIDOfApplicationCollectionApplication);
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
            Collection<Application> applicationCollectionOld = persistentEndorsement.getApplicationCollection();
            Collection<Application> applicationCollectionNew = endorsement.getApplicationCollection();
            if (deanIDNew != null) {
                deanIDNew = em.getReference(deanIDNew.getClass(), deanIDNew.getSystemID());
                endorsement.setDeanID(deanIDNew);
            }
            Collection<Application> attachedApplicationCollectionNew = new ArrayList<Application>();
            for (Application applicationCollectionNewApplicationToAttach : applicationCollectionNew) {
                applicationCollectionNewApplicationToAttach = em.getReference(applicationCollectionNewApplicationToAttach.getClass(), applicationCollectionNewApplicationToAttach.getApplicationID());
                attachedApplicationCollectionNew.add(applicationCollectionNewApplicationToAttach);
            }
            applicationCollectionNew = attachedApplicationCollectionNew;
            endorsement.setApplicationCollection(applicationCollectionNew);
            endorsement = em.merge(endorsement);
            if (deanIDOld != null && !deanIDOld.equals(deanIDNew)) {
                deanIDOld.getEndorsementCollection().remove(endorsement);
                deanIDOld = em.merge(deanIDOld);
            }
            if (deanIDNew != null && !deanIDNew.equals(deanIDOld)) {
                deanIDNew.getEndorsementCollection().add(endorsement);
                deanIDNew = em.merge(deanIDNew);
            }
            for (Application applicationCollectionOldApplication : applicationCollectionOld) {
                if (!applicationCollectionNew.contains(applicationCollectionOldApplication)) {
                    applicationCollectionOldApplication.setEndorsementID(null);
                    applicationCollectionOldApplication = em.merge(applicationCollectionOldApplication);
                }
            }
            for (Application applicationCollectionNewApplication : applicationCollectionNew) {
                if (!applicationCollectionOld.contains(applicationCollectionNewApplication)) {
                    Endorsement oldEndorsementIDOfApplicationCollectionNewApplication = applicationCollectionNewApplication.getEndorsementID();
                    applicationCollectionNewApplication.setEndorsementID(endorsement);
                    applicationCollectionNewApplication = em.merge(applicationCollectionNewApplication);
                    if (oldEndorsementIDOfApplicationCollectionNewApplication != null && !oldEndorsementIDOfApplicationCollectionNewApplication.equals(endorsement)) {
                        oldEndorsementIDOfApplicationCollectionNewApplication.getApplicationCollection().remove(applicationCollectionNewApplication);
                        oldEndorsementIDOfApplicationCollectionNewApplication = em.merge(oldEndorsementIDOfApplicationCollectionNewApplication);
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
                deanID.getEndorsementCollection().remove(endorsement);
                deanID = em.merge(deanID);
            }
            Collection<Application> applicationCollection = endorsement.getApplicationCollection();
            for (Application applicationCollectionApplication : applicationCollection) {
                applicationCollectionApplication.setEndorsementID(null);
                applicationCollectionApplication = em.merge(applicationCollectionApplication);
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
