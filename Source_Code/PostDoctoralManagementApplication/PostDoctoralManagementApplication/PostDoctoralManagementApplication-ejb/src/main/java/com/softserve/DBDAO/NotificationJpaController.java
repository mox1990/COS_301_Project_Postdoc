/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
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
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NotificationJpaController implements Serializable {

    public NotificationJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(Notification notification) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), notification);
    }

    public void create(EntityManager em, Notification notification) throws RollbackFailureException, Exception 
    {

        Person sender = notification.getSender();
        if (sender != null) {
            sender = em.getReference(sender.getClass(), sender.getSystemID());
            notification.setSender(sender);
        }
        Person reciever = notification.getReciever();
        if (reciever != null) {
            reciever = em.getReference(reciever.getClass(), reciever.getSystemID());
            notification.setReciever(reciever);
        }
        em.persist(notification);
        if (sender != null) {
            sender.getNotificationList().add(notification);
            sender = em.merge(sender);
        }
        if (reciever != null) {
            reciever.getNotificationList1().add(notification);
            reciever = em.merge(reciever);
        }
            
    }
    
    public void edit(Notification notification) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        edit(getEntityManager(), notification);
    }

    public void edit(EntityManager em, Notification notification) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = notification.getNotificationID();
        if (findNotification(id) == null) {
            throw new NonexistentEntityException("The notification with id " + id + " no longer exists.");
        }
        
        Notification persistentNotification = em.find(Notification.class, notification.getNotificationID());
        Person senderOld = persistentNotification.getSender();
        Person senderNew = notification.getSender();
        Person recieverOld = persistentNotification.getReciever();
        Person recieverNew = notification.getReciever();
        if (senderNew != null) {
            senderNew = em.getReference(senderNew.getClass(), senderNew.getSystemID());
            notification.setSender(senderNew);
        }
        if (recieverNew != null) {
            recieverNew = em.getReference(recieverNew.getClass(), recieverNew.getSystemID());
            notification.setReciever(recieverNew);
        }
        notification = em.merge(notification);
        if (senderOld != null && !senderOld.equals(senderNew)) {
            senderOld.getNotificationList().remove(notification);
            senderOld = em.merge(senderOld);
        }
        if (senderNew != null && !senderNew.equals(senderOld)) {
            senderNew.getNotificationList().add(notification);
            senderNew = em.merge(senderNew);
        }
        if (recieverOld != null && !recieverOld.equals(recieverNew)) {
            recieverOld.getNotificationList1().remove(notification);
            recieverOld = em.merge(recieverOld);
        }
        if (recieverNew != null && !recieverNew.equals(recieverOld)) {
            recieverNew.getNotificationList1().add(notification);
            recieverNew = em.merge(recieverNew);
        }
            
                
           
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        
        Notification notification;
        try {
            notification = em.getReference(Notification.class, id);
            notification.getNotificationID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The notification with id " + id + " no longer exists.", enfe);
        }
        Person sender = notification.getSender();
        if (sender != null) {
            sender.getNotificationList().remove(notification);
            sender = em.merge(sender);
        }
        Person reciever = notification.getReciever();
        if (reciever != null) {
            reciever.getNotificationList1().remove(notification);
            reciever = em.merge(reciever);
        }
        em.remove(notification);
            
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
            
        }
    }

    public Notification findNotification(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notification.class, id);
        } finally {
            
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
            
        }
    }
    
    public List<Notification> findAllNotificationsWhosRecieverIs(Person person)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Notification> q = em.createQuery("SELECT n FROM Notification n WHERE n.reciever = :person", Notification.class).setParameter("person", person);
        
        return q.getResultList();
    }
    
    public List<Notification> findAllNotificationsWhosSenderIs(Person person)
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Notification> q = em.createQuery("SELECT n FROM Notification n WHERE n.sender = :person", Notification.class).setParameter("person", person);
        
        return q.getResultList();
    }
}
