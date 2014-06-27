/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Notification;
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
public class NotificationJpaController implements Serializable {

    public NotificationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notification notification) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person senderID = notification.getSenderID();
            if (senderID != null) {
                senderID = em.getReference(senderID.getClass(), senderID.getSystemID());
                notification.setSenderID(senderID);
            }
            Person recieverID = notification.getRecieverID();
            if (recieverID != null) {
                recieverID = em.getReference(recieverID.getClass(), recieverID.getSystemID());
                notification.setRecieverID(recieverID);
            }
            em.persist(notification);
            if (senderID != null) {
                senderID.getNotificationList().add(notification);
                senderID = em.merge(senderID);
            }
            if (recieverID != null) {
                recieverID.getNotificationList().add(notification);
                recieverID = em.merge(recieverID);
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

    public void edit(Notification notification) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Notification persistentNotification = em.find(Notification.class, notification.getNotificationID());
            Person senderIDOld = persistentNotification.getSenderID();
            Person senderIDNew = notification.getSenderID();
            Person recieverIDOld = persistentNotification.getRecieverID();
            Person recieverIDNew = notification.getRecieverID();
            if (senderIDNew != null) {
                senderIDNew = em.getReference(senderIDNew.getClass(), senderIDNew.getSystemID());
                notification.setSenderID(senderIDNew);
            }
            if (recieverIDNew != null) {
                recieverIDNew = em.getReference(recieverIDNew.getClass(), recieverIDNew.getSystemID());
                notification.setRecieverID(recieverIDNew);
            }
            notification = em.merge(notification);
            if (senderIDOld != null && !senderIDOld.equals(senderIDNew)) {
                senderIDOld.getNotificationList().remove(notification);
                senderIDOld = em.merge(senderIDOld);
            }
            if (senderIDNew != null && !senderIDNew.equals(senderIDOld)) {
                senderIDNew.getNotificationList().add(notification);
                senderIDNew = em.merge(senderIDNew);
            }
            if (recieverIDOld != null && !recieverIDOld.equals(recieverIDNew)) {
                recieverIDOld.getNotificationList().remove(notification);
                recieverIDOld = em.merge(recieverIDOld);
            }
            if (recieverIDNew != null && !recieverIDNew.equals(recieverIDOld)) {
                recieverIDNew.getNotificationList().add(notification);
                recieverIDNew = em.merge(recieverIDNew);
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
                Long id = notification.getNotificationID();
                if (findNotification(id) == null) {
                    throw new NonexistentEntityException("The notification with id " + id + " no longer exists.");
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
            Notification notification;
            try {
                notification = em.getReference(Notification.class, id);
                notification.getNotificationID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notification with id " + id + " no longer exists.", enfe);
            }
            Person senderID = notification.getSenderID();
            if (senderID != null) {
                senderID.getNotificationList().remove(notification);
                senderID = em.merge(senderID);
            }
            Person recieverID = notification.getRecieverID();
            if (recieverID != null) {
                recieverID.getNotificationList().remove(notification);
                recieverID = em.merge(recieverID);
            }
            em.remove(notification);
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

    public List<Notification> findNotificationEntities() {
        return findNotificationEntities(true, -1, -1);
    }

    public List<Notification> findNotificationEntities(int maxResults, int firstResult) {
        return findNotificationEntities(false, maxResults, firstResult);
    }

    private List<Notification> findNotificationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notification.class));
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

    public Notification findNotification(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notification.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notification> rt = cq.from(Notification.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
