/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEnties.Person;
import com.softserve.DBEnties.SecurityRole;
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
public class SecurityRoleJpaController implements Serializable {

    public SecurityRoleJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SecurityRole securityRole) throws RollbackFailureException, Exception {
        if (securityRole.getPersonCollection() == null) {
            securityRole.setPersonCollection(new ArrayList<Person>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Person> attachedPersonCollection = new ArrayList<Person>();
            for (Person personCollectionPersonToAttach : securityRole.getPersonCollection()) {
                personCollectionPersonToAttach = em.getReference(personCollectionPersonToAttach.getClass(), personCollectionPersonToAttach.getSystemID());
                attachedPersonCollection.add(personCollectionPersonToAttach);
            }
            securityRole.setPersonCollection(attachedPersonCollection);
            em.persist(securityRole);
            for (Person personCollectionPerson : securityRole.getPersonCollection()) {
                personCollectionPerson.getSecurityRoleCollection().add(securityRole);
                personCollectionPerson = em.merge(personCollectionPerson);
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

    public void edit(SecurityRole securityRole) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SecurityRole persistentSecurityRole = em.find(SecurityRole.class, securityRole.getRoleID());
            Collection<Person> personCollectionOld = persistentSecurityRole.getPersonCollection();
            Collection<Person> personCollectionNew = securityRole.getPersonCollection();
            Collection<Person> attachedPersonCollectionNew = new ArrayList<Person>();
            for (Person personCollectionNewPersonToAttach : personCollectionNew) {
                personCollectionNewPersonToAttach = em.getReference(personCollectionNewPersonToAttach.getClass(), personCollectionNewPersonToAttach.getSystemID());
                attachedPersonCollectionNew.add(personCollectionNewPersonToAttach);
            }
            personCollectionNew = attachedPersonCollectionNew;
            securityRole.setPersonCollection(personCollectionNew);
            securityRole = em.merge(securityRole);
            for (Person personCollectionOldPerson : personCollectionOld) {
                if (!personCollectionNew.contains(personCollectionOldPerson)) {
                    personCollectionOldPerson.getSecurityRoleCollection().remove(securityRole);
                    personCollectionOldPerson = em.merge(personCollectionOldPerson);
                }
            }
            for (Person personCollectionNewPerson : personCollectionNew) {
                if (!personCollectionOld.contains(personCollectionNewPerson)) {
                    personCollectionNewPerson.getSecurityRoleCollection().add(securityRole);
                    personCollectionNewPerson = em.merge(personCollectionNewPerson);
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
                Long id = securityRole.getRoleID();
                if (findSecurityRole(id) == null) {
                    throw new NonexistentEntityException("The securityRole with id " + id + " no longer exists.");
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
            SecurityRole securityRole;
            try {
                securityRole = em.getReference(SecurityRole.class, id);
                securityRole.getRoleID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The securityRole with id " + id + " no longer exists.", enfe);
            }
            Collection<Person> personCollection = securityRole.getPersonCollection();
            for (Person personCollectionPerson : personCollection) {
                personCollectionPerson.getSecurityRoleCollection().remove(securityRole);
                personCollectionPerson = em.merge(personCollectionPerson);
            }
            em.remove(securityRole);
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

    public List<SecurityRole> findSecurityRoleEntities() {
        return findSecurityRoleEntities(true, -1, -1);
    }

    public List<SecurityRole> findSecurityRoleEntities(int maxResults, int firstResult) {
        return findSecurityRoleEntities(false, maxResults, firstResult);
    }

    private List<SecurityRole> findSecurityRoleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SecurityRole.class));
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

    public SecurityRole findSecurityRole(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SecurityRole.class, id);
        } finally {
            em.close();
        }
    }

    public int getSecurityRoleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SecurityRole> rt = cq.from(SecurityRole.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
