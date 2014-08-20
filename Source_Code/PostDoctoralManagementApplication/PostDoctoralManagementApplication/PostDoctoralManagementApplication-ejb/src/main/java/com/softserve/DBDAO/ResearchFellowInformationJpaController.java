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

    public ResearchFellowInformationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ResearchFellowInformation researchFellowInformation) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
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
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person person = researchFellowInformation.getPerson();
            if (person != null) {
                person = em.getReference(person.getClass(), person.getSystemID());
                researchFellowInformation.setPerson(person);
            }
            Department department = researchFellowInformation.getDepartment();
            if (department != null) {
                department = em.getReference(department.getClass(), department.getDepartmentID());
                researchFellowInformation.setDepartment(department);
            }
            em.persist(researchFellowInformation);
            if (person != null) {
                person.setResearchFellowInformation(researchFellowInformation);
                person = em.merge(person);
            }
            if (department != null) {
                department.getResearchFellowInformationList().add(researchFellowInformation);
                department = em.merge(department);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findResearchFellowInformation(researchFellowInformation.getSystemAssignedID()) != null) {
                throw new PreexistingEntityException("ResearchFellowInformation " + researchFellowInformation + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ResearchFellowInformation researchFellowInformation) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
            if (personNew != null) {
                personNew = em.getReference(personNew.getClass(), personNew.getSystemID());
                researchFellowInformation.setPerson(personNew);
            }
            if (departmentNew != null) {
                departmentNew = em.getReference(departmentNew.getClass(), departmentNew.getDepartmentID());
                researchFellowInformation.setDepartment(departmentNew);
            }
            researchFellowInformation = em.merge(researchFellowInformation);
            if (personOld != null && !personOld.equals(personNew)) {
                personOld.setResearchFellowInformation(null);
                personOld = em.merge(personOld);
            }
            if (personNew != null && !personNew.equals(personOld)) {
                personNew.setResearchFellowInformation(researchFellowInformation);
                personNew = em.merge(personNew);
            }
            if (departmentOld != null && !departmentOld.equals(departmentNew)) {
                departmentOld.getResearchFellowInformationList().remove(researchFellowInformation);
                departmentOld = em.merge(departmentOld);
            }
            if (departmentNew != null && !departmentNew.equals(departmentOld)) {
                departmentNew.getResearchFellowInformationList().add(researchFellowInformation);
                departmentNew = em.merge(departmentNew);
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
                String id = researchFellowInformation.getSystemAssignedID();
                if (findResearchFellowInformation(id) == null) {
                    throw new NonexistentEntityException("The researchFellowInformation with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ResearchFellowInformation researchFellowInformation;
            try {
                researchFellowInformation = em.getReference(ResearchFellowInformation.class, id);
                researchFellowInformation.getSystemAssignedID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The researchFellowInformation with id " + id + " no longer exists.", enfe);
            }
            Person person = researchFellowInformation.getPerson();
            if (person != null) {
                person.setResearchFellowInformation(null);
                person = em.merge(person);
            }
            Department department = researchFellowInformation.getDepartment();
            if (department != null) {
                department.getResearchFellowInformationList().remove(researchFellowInformation);
                department = em.merge(department);
            }
            em.remove(researchFellowInformation);
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
            em.close();
        }
    }

    public ResearchFellowInformation findResearchFellowInformation(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResearchFellowInformation.class, id);
        } finally {
            em.close();
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
            em.close();
        }
    }
    
}
