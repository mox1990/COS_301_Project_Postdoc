/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.AuditLog;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class AuditLogJpaController implements Serializable {

    public AuditLogJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AuditLog auditLog) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person person = auditLog.getPerson();
            if (person != null) {
                person = em.getReference(person.getClass(), person.getSystemID());
                auditLog.setPerson(person);
            }
            em.persist(auditLog);
            if (person != null) {
                person.getAuditLogList().add(auditLog);
                person = em.merge(person);
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

    public void edit(AuditLog auditLog) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AuditLog persistentAuditLog = em.find(AuditLog.class, auditLog.getEntryID());
            Person personOld = persistentAuditLog.getPerson();
            Person personNew = auditLog.getPerson();
            if (personNew != null) {
                personNew = em.getReference(personNew.getClass(), personNew.getSystemID());
                auditLog.setPerson(personNew);
            }
            auditLog = em.merge(auditLog);
            if (personOld != null && !personOld.equals(personNew)) {
                personOld.getAuditLogList().remove(auditLog);
                personOld = em.merge(personOld);
            }
            if (personNew != null && !personNew.equals(personOld)) {
                personNew.getAuditLogList().add(auditLog);
                personNew = em.merge(personNew);
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
                Long id = auditLog.getEntryID();
                if (findAuditLog(id) == null) {
                    throw new NonexistentEntityException("The auditLog with id " + id + " no longer exists.");
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
            AuditLog auditLog;
            try {
                auditLog = em.getReference(AuditLog.class, id);
                auditLog.getEntryID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The auditLog with id " + id + " no longer exists.", enfe);
            }
            Person person = auditLog.getPerson();
            if (person != null) {
                person.getAuditLogList().remove(auditLog);
                person = em.merge(person);
            }
            em.remove(auditLog);
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

    public List<AuditLog> findAuditLogEntities() {
        return findAuditLogEntities(true, -1, -1);
    }

    public List<AuditLog> findAuditLogEntities(int maxResults, int firstResult) {
        return findAuditLogEntities(false, maxResults, firstResult);
    }

    private List<AuditLog> findAuditLogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AuditLog.class));
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

    public AuditLog findAuditLog(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AuditLog.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuditLogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AuditLog> rt = cq.from(AuditLog.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
