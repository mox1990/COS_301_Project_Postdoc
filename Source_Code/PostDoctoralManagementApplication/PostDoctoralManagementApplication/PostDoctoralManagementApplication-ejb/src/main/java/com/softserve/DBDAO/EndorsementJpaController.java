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
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Endorsement;
import com.softserve.DBEntities.Person;
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

    public void create(Endorsement endorsement) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Application applicationOrphanCheck = endorsement.getApplication();
        if (applicationOrphanCheck != null) {
            Endorsement oldEndorsementOfApplication = applicationOrphanCheck.getEndorsement();
            if (oldEndorsementOfApplication != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Application " + applicationOrphanCheck + " already has an item of type Endorsement whose application column cannot be null. Please make another selection for the application field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Application application = endorsement.getApplication();
            if (application != null) {
                application = em.getReference(application.getClass(), application.getApplicationID());
                endorsement.setApplication(application);
            }
            Person dean = endorsement.getDean();
            if (dean != null) {
                dean = em.getReference(dean.getClass(), dean.getSystemID());
                endorsement.setDean(dean);
            }
            em.persist(endorsement);
            if (application != null) {
                application.setEndorsement(endorsement);
                application = em.merge(application);
            }
            if (dean != null) {
                dean.getEndorsementList().add(endorsement);
                dean = em.merge(dean);
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

    public void edit(Endorsement endorsement) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Endorsement persistentEndorsement = em.find(Endorsement.class, endorsement.getEndorsementID());
            Application applicationOld = persistentEndorsement.getApplication();
            Application applicationNew = endorsement.getApplication();
            Person deanOld = persistentEndorsement.getDean();
            Person deanNew = endorsement.getDean();
            List<String> illegalOrphanMessages = null;
            if (applicationNew != null && !applicationNew.equals(applicationOld)) {
                Endorsement oldEndorsementOfApplication = applicationNew.getEndorsement();
                if (oldEndorsementOfApplication != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Application " + applicationNew + " already has an item of type Endorsement whose application column cannot be null. Please make another selection for the application field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (applicationNew != null) {
                applicationNew = em.getReference(applicationNew.getClass(), applicationNew.getApplicationID());
                endorsement.setApplication(applicationNew);
            }
            if (deanNew != null) {
                deanNew = em.getReference(deanNew.getClass(), deanNew.getSystemID());
                endorsement.setDean(deanNew);
            }
            endorsement = em.merge(endorsement);
            if (applicationOld != null && !applicationOld.equals(applicationNew)) {
                applicationOld.setEndorsement(null);
                applicationOld = em.merge(applicationOld);
            }
            if (applicationNew != null && !applicationNew.equals(applicationOld)) {
                applicationNew.setEndorsement(endorsement);
                applicationNew = em.merge(applicationNew);
            }
            if (deanOld != null && !deanOld.equals(deanNew)) {
                deanOld.getEndorsementList().remove(endorsement);
                deanOld = em.merge(deanOld);
            }
            if (deanNew != null && !deanNew.equals(deanOld)) {
                deanNew.getEndorsementList().add(endorsement);
                deanNew = em.merge(deanNew);
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
            Application application = endorsement.getApplication();
            if (application != null) {
                application.setEndorsement(null);
                application = em.merge(application);
            }
            Person dean = endorsement.getDean();
            if (dean != null) {
                dean.getEndorsementList().remove(endorsement);
                dean = em.merge(dean);
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
