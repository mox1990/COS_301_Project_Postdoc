/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Announcement;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class AnnouncementJpaController implements Serializable {

    public AnnouncementJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(Announcement announcement) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), announcement);
    }

    public void create(EntityManager em, Announcement announcement) throws RollbackFailureException, Exception 
    {
        em.persist(announcement);
    }
    
    public Announcement edit(Announcement announcement) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), announcement);
    }

    public Announcement edit(EntityManager em, Announcement announcement) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        Long id = announcement.getAnnouncementID();
        if (findAnnouncement(id) == null) {
            throw new NonexistentEntityException("The announcement with id " + id + " no longer exists.");
        }        

        return em.merge(announcement);

    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
            Announcement announcement;
            try {
                announcement = em.getReference(Announcement.class, id);
                announcement.getAnnouncementID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The announcement with id " + id + " no longer exists.", enfe);
            }
            em.remove(announcement);

    }

    public List<Announcement> findAnnouncementEntities() {
        return findAnnouncementEntities(true, -1, -1);
    }

    public List<Announcement> findAnnouncementEntities(int maxResults, int firstResult) {
        return findAnnouncementEntities(false, maxResults, firstResult);
    }

    private List<Announcement> findAnnouncementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Announcement.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {

        }
    }

    public Announcement findAnnouncement(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Announcement.class, id);
        } finally {

        }
    }

    public int getAnnouncementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Announcement> rt = cq.from(Announcement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {

        }
    }
    
    public List<Announcement> findAllActiveAnnouncements()
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Announcement> q = em.createQuery("SELECT a FROM Announcement a WHERE a.startDate <= CURRENT_TIMESTAMP AND a.endDate >= CURRENT_TIMESTAMP", Announcement.class);
        
        return q.getResultList();
    }
    
    public List<Announcement> findAllEndedAnnouncements()
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Announcement> q = em.createQuery("SELECT a FROM Announcement a WHERE a.endDate <= CURRENT_TIMESTAMP", Announcement.class);
        
        return q.getResultList();
    }
    
    public List<Announcement> findPendingAnnouncements()
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<Announcement> q = em.createQuery("SELECT a FROM Announcement a WHERE a.startDate > CURRENT_TIMESTAMP", Announcement.class);
        
        return q.getResultList();
    }
    
    
}
