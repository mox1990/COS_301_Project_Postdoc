/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
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
            Person personID = auditLog.getPersonID();
            if (personID != null) {
                personID = em.getReference(personID.getClass(), personID.getSystemID());
                auditLog.setPersonID(personID);
            }
            em.persist(auditLog);
            if (personID != null) {
                personID.getAuditLogCollection().add(auditLog);
                personID = em.merge(personID);
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
            Person personIDOld = persistentAuditLog.getPersonID();
            Person personIDNew = auditLog.getPersonID();
            if (personIDNew != null) {
                personIDNew = em.getReference(personIDNew.getClass(), personIDNew.getSystemID());
                auditLog.setPersonID(personIDNew);
            }
            auditLog = em.merge(auditLog);
            if (personIDOld != null && !personIDOld.equals(personIDNew)) {
                personIDOld.getAuditLogCollection().remove(auditLog);
                personIDOld = em.merge(personIDOld);
            }
            if (personIDNew != null && !personIDNew.equals(personIDOld)) {
                personIDNew.getAuditLogCollection().add(auditLog);
                personIDNew = em.merge(personIDNew);
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
            Person personID = auditLog.getPersonID();
            if (personID != null) {
                personID.getAuditLogCollection().remove(auditLog);
                personID = em.merge(personID);
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
