/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.ResourceEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author kgothatso
 */
public class ResourceEntityJpaController implements Serializable {

    public ResourceEntityJpaController(EntityManager em) {
        this.em = em;
    }
    private EntityManager em = null;

    public EntityManager getEntityManager() {
        return em;
    }

    public void create(ResourceEntity resourceEntity) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.persist(resourceEntity);
        } catch (Exception ex) {
            try {
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

    public void edit(ResourceEntity resourceEntity) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            resourceEntity = em.merge(resourceEntity);
        } catch (Exception ex) {
            
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = resourceEntity.getId();
                if (findResourceEntity(id) == null) {
                    throw new NonexistentEntityException("The resourceEntity with id " + id + " no longer exists.");
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
            em = getEntityManager();
            ResourceEntity resourceEntity;
            try {
                resourceEntity = em.getReference(ResourceEntity.class, id);
                resourceEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resourceEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(resourceEntity);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ResourceEntity> findResourceEntityEntities() {
        return findResourceEntityEntities(true, -1, -1);
    }

    public List<ResourceEntity> findResourceEntityEntities(int maxResults, int firstResult) {
        return findResourceEntityEntities(false, maxResults, firstResult);
    }

    private List<ResourceEntity> findResourceEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ResourceEntity.class));
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

    public ResourceEntity findResourceEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResourceEntity.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<ResourceEntity> findForLocale(String locale)
    {
        System.out.println("Why...?");
        return getEntityManager().createNamedQuery("ResourceEntity.findForLocale").setParameter("locale", locale).getResultList();
    }

    public int getResourceEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ResourceEntity> rt = cq.from(ResourceEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
