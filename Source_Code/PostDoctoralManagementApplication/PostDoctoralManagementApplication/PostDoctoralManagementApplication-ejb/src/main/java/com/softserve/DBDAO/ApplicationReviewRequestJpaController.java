/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
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
import com.softserve.DBEntities.ApplicationReviewRequest;
import com.softserve.DBEntities.ApplicationReviewRequestPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ApplicationReviewRequestJpaController implements Serializable {

    public ApplicationReviewRequestJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ApplicationReviewRequest applicationReviewRequest) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (applicationReviewRequest.getApplicationReviewRequestPK() == null) {
            applicationReviewRequest.setApplicationReviewRequestPK(new ApplicationReviewRequestPK());
        }
        applicationReviewRequest.getApplicationReviewRequestPK().setPerson(applicationReviewRequest.getPerson1().getSystemID());
        applicationReviewRequest.getApplicationReviewRequestPK().setApplication(applicationReviewRequest.getApplication1().getApplicationID());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person person1 = applicationReviewRequest.getPerson1();
            if (person1 != null) {
                person1 = em.getReference(person1.getClass(), person1.getSystemID());
                applicationReviewRequest.setPerson1(person1);
            }
            Application application1 = applicationReviewRequest.getApplication1();
            if (application1 != null) {
                application1 = em.getReference(application1.getClass(), application1.getApplicationID());
                applicationReviewRequest.setApplication1(application1);
            }
            em.persist(applicationReviewRequest);
            if (person1 != null) {
                person1.getApplicationReviewRequestList().add(applicationReviewRequest);
                person1 = em.merge(person1);
            }
            if (application1 != null) {
                application1.getApplicationReviewRequestList().add(applicationReviewRequest);
                application1 = em.merge(application1);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findApplicationReviewRequest(applicationReviewRequest.getApplicationReviewRequestPK()) != null) {
                throw new PreexistingEntityException("ApplicationReviewRequest " + applicationReviewRequest + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ApplicationReviewRequest applicationReviewRequest) throws NonexistentEntityException, RollbackFailureException, Exception {
        applicationReviewRequest.getApplicationReviewRequestPK().setPerson(applicationReviewRequest.getPerson1().getSystemID());
        applicationReviewRequest.getApplicationReviewRequestPK().setApplication(applicationReviewRequest.getApplication1().getApplicationID());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ApplicationReviewRequest persistentApplicationReviewRequest = em.find(ApplicationReviewRequest.class, applicationReviewRequest.getApplicationReviewRequestPK());
            Person person1Old = persistentApplicationReviewRequest.getPerson1();
            Person person1New = applicationReviewRequest.getPerson1();
            Application application1Old = persistentApplicationReviewRequest.getApplication1();
            Application application1New = applicationReviewRequest.getApplication1();
            if (person1New != null) {
                person1New = em.getReference(person1New.getClass(), person1New.getSystemID());
                applicationReviewRequest.setPerson1(person1New);
            }
            if (application1New != null) {
                application1New = em.getReference(application1New.getClass(), application1New.getApplicationID());
                applicationReviewRequest.setApplication1(application1New);
            }
            applicationReviewRequest = em.merge(applicationReviewRequest);
            if (person1Old != null && !person1Old.equals(person1New)) {
                person1Old.getApplicationReviewRequestList().remove(applicationReviewRequest);
                person1Old = em.merge(person1Old);
            }
            if (person1New != null && !person1New.equals(person1Old)) {
                person1New.getApplicationReviewRequestList().add(applicationReviewRequest);
                person1New = em.merge(person1New);
            }
            if (application1Old != null && !application1Old.equals(application1New)) {
                application1Old.getApplicationReviewRequestList().remove(applicationReviewRequest);
                application1Old = em.merge(application1Old);
            }
            if (application1New != null && !application1New.equals(application1Old)) {
                application1New.getApplicationReviewRequestList().add(applicationReviewRequest);
                application1New = em.merge(application1New);
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
                ApplicationReviewRequestPK id = applicationReviewRequest.getApplicationReviewRequestPK();
                if (findApplicationReviewRequest(id) == null) {
                    throw new NonexistentEntityException("The applicationReviewRequest with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ApplicationReviewRequestPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ApplicationReviewRequest applicationReviewRequest;
            try {
                applicationReviewRequest = em.getReference(ApplicationReviewRequest.class, id);
                applicationReviewRequest.getApplicationReviewRequestPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The applicationReviewRequest with id " + id + " no longer exists.", enfe);
            }
            Person person1 = applicationReviewRequest.getPerson1();
            if (person1 != null) {
                person1.getApplicationReviewRequestList().remove(applicationReviewRequest);
                person1 = em.merge(person1);
            }
            Application application1 = applicationReviewRequest.getApplication1();
            if (application1 != null) {
                application1.getApplicationReviewRequestList().remove(applicationReviewRequest);
                application1 = em.merge(application1);
            }
            em.remove(applicationReviewRequest);
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

    public List<ApplicationReviewRequest> findApplicationReviewRequestEntities() {
        return findApplicationReviewRequestEntities(true, -1, -1);
    }

    public List<ApplicationReviewRequest> findApplicationReviewRequestEntities(int maxResults, int firstResult) {
        return findApplicationReviewRequestEntities(false, maxResults, firstResult);
    }

    private List<ApplicationReviewRequest> findApplicationReviewRequestEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ApplicationReviewRequest.class));
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

    public ApplicationReviewRequest findApplicationReviewRequest(ApplicationReviewRequestPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ApplicationReviewRequest.class, id);
        } finally {
            em.close();
        }
    }

    public int getApplicationReviewRequestCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ApplicationReviewRequest> rt = cq.from(ApplicationReviewRequest.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
