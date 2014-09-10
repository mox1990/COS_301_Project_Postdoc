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
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.ResearchFellowInformation;
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
public class ResearchFellowInformationJpaController implements Serializable {

    public ResearchFellowInformationJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(ResearchFellowInformation researchFellowInformation) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        create(getEntityManager(), researchFellowInformation);
    }
    
    public void create(EntityManager em, ResearchFellowInformation researchFellowInformation) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        List<String> illegalOrphanMessages = null;
        Person personOrphanCheck = researchFellowInformation.getPerson();
        if (personOrphanCheck != null) {
            ResearchFellowInformation oldResearchFellowInformationOfPerson = personOrphanCheck.getResearchFellowInformation();
            if (oldResearchFellowInformationOfPerson != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Person " + personOrphanCheck + " already has an item of type ResearchFellowInformation whose person column cannot be null. Please make another selection for the person field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        
        em.persist(researchFellowInformation);
    }
    
    public ResearchFellowInformation edit(ResearchFellowInformation researchFellowInformation) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), researchFellowInformation);
    }

    public ResearchFellowInformation edit(EntityManager em, ResearchFellowInformation researchFellowInformation) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

        String id = researchFellowInformation.getSystemAssignedID();
        if (findResearchFellowInformation(id) == null) {
            throw new NonexistentEntityException("The researchFellowInformation with id " + id + " no longer exists.");
        }
        
        ResearchFellowInformation persistentResearchFellowInformation = em.find(ResearchFellowInformation.class, researchFellowInformation.getSystemAssignedID());
        Person personOld = persistentResearchFellowInformation.getPerson();
        Person personNew = researchFellowInformation.getPerson();
        Department departmentOld = persistentResearchFellowInformation.getDepartment();
        Department departmentNew = researchFellowInformation.getDepartment();
        List<String> illegalOrphanMessages = null;
        if (personNew != null && !personNew.equals(personOld)) {
            ResearchFellowInformation oldResearchFellowInformationOfPerson = personNew.getResearchFellowInformation();
            if (oldResearchFellowInformationOfPerson != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Person " + personNew + " already has an item of type ResearchFellowInformation whose person column cannot be null. Please make another selection for the person field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }

        return em.merge(researchFellowInformation);
    }
    
    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, String id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        ResearchFellowInformation researchFellowInformation;
        try {
            researchFellowInformation = em.getReference(ResearchFellowInformation.class, id);
            researchFellowInformation.getSystemAssignedID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The researchFellowInformation with id " + id + " no longer exists.", enfe);
        }

        em.remove(researchFellowInformation);

    }

    public List<ResearchFellowInformation> findResearchFellowInformationEntities() {
        return findResearchFellowInformationEntities(true, -1, -1);
    }

    public List<ResearchFellowInformation> findResearchFellowInformationEntities(int maxResults, int firstResult) {
        return findResearchFellowInformationEntities(false, maxResults, firstResult);
    }

    private List<ResearchFellowInformation> findResearchFellowInformationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ResearchFellowInformation.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public ResearchFellowInformation findResearchFellowInformation(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResearchFellowInformation.class, id);
        } finally {
            
        }
    }

    public int getResearchFellowInformationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ResearchFellowInformation> rt = cq.from(ResearchFellowInformation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
