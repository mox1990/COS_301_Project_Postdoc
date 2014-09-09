/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.Institution;
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
public class InstitutionJpaController implements Serializable {

    public InstitutionJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(Institution institution) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), institution);
    }

    public void create(EntityManager em, Institution institution) throws RollbackFailureException, Exception 
    {
        if (institution.getFacultyList() == null) {
            institution.setFacultyList(new ArrayList<Faculty>());
        }

        List<Faculty> attachedFacultyList = new ArrayList<Faculty>();
        for (Faculty facultyListFacultyToAttach : institution.getFacultyList()) {
            facultyListFacultyToAttach = em.getReference(facultyListFacultyToAttach.getClass(), facultyListFacultyToAttach.getFacultyID());
            attachedFacultyList.add(facultyListFacultyToAttach);
        }
        institution.setFacultyList(attachedFacultyList);
        em.persist(institution);
        for (Faculty facultyListFaculty : institution.getFacultyList()) {
            Institution oldInstitutionOfFacultyListFaculty = facultyListFaculty.getInstitution();
            facultyListFaculty.setInstitution(institution);
            facultyListFaculty = em.merge(facultyListFaculty);
            if (oldInstitutionOfFacultyListFaculty != null) {
                oldInstitutionOfFacultyListFaculty.getFacultyList().remove(facultyListFaculty);
                oldInstitutionOfFacultyListFaculty = em.merge(oldInstitutionOfFacultyListFaculty);
            }
        }
            
    }
    
    public void edit(Institution institution) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        edit(getEntityManager(), institution);
    }

    public void edit(EntityManager em, Institution institution) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        
        Long id = institution.getInstitutionID();
        if (findInstitution(id) == null) {
            throw new NonexistentEntityException("The institution with id " + id + " no longer exists.");
        }
        
        Institution persistentInstitution = em.find(Institution.class, institution.getInstitutionID());
        List<Faculty> facultyListOld = persistentInstitution.getFacultyList();
        List<Faculty> facultyListNew = institution.getFacultyList();
        List<String> illegalOrphanMessages = null;
        for (Faculty facultyListOldFaculty : facultyListOld) {
            if (!facultyListNew.contains(facultyListOldFaculty)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Faculty " + facultyListOldFaculty + " since its institution field is not nullable.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        List<Faculty> attachedFacultyListNew = new ArrayList<Faculty>();
        for (Faculty facultyListNewFacultyToAttach : facultyListNew) {
            facultyListNewFacultyToAttach = em.getReference(facultyListNewFacultyToAttach.getClass(), facultyListNewFacultyToAttach.getFacultyID());
            attachedFacultyListNew.add(facultyListNewFacultyToAttach);
        }
        facultyListNew = attachedFacultyListNew;
        institution.setFacultyList(facultyListNew);
        institution = em.merge(institution);
        for (Faculty facultyListNewFaculty : facultyListNew) {
            if (!facultyListOld.contains(facultyListNewFaculty)) {
                Institution oldInstitutionOfFacultyListNewFaculty = facultyListNewFaculty.getInstitution();
                facultyListNewFaculty.setInstitution(institution);
                facultyListNewFaculty = em.merge(facultyListNewFaculty);
                if (oldInstitutionOfFacultyListNewFaculty != null && !oldInstitutionOfFacultyListNewFaculty.equals(institution)) {
                    oldInstitutionOfFacultyListNewFaculty.getFacultyList().remove(facultyListNewFaculty);
                    oldInstitutionOfFacultyListNewFaculty = em.merge(oldInstitutionOfFacultyListNewFaculty);
                }
            }
        }
            
                

    }
    
    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

        Institution institution;
        try {
            institution = em.getReference(Institution.class, id);
            institution.getInstitutionID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The institution with id " + id + " no longer exists.", enfe);
        }
        List<String> illegalOrphanMessages = null;
        List<Faculty> facultyListOrphanCheck = institution.getFacultyList();
        for (Faculty facultyListOrphanCheckFaculty : facultyListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Institution (" + institution + ") cannot be destroyed since the Faculty " + facultyListOrphanCheckFaculty + " in its facultyList field has a non-nullable institution field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        em.remove(institution);
           
    }

    public List<Institution> findInstitutionEntities() {
        return findInstitutionEntities(true, -1, -1);
    }

    public List<Institution> findInstitutionEntities(int maxResults, int firstResult) {
        return findInstitutionEntities(false, maxResults, firstResult);
    }

    private List<Institution> findInstitutionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Institution.class));
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

    public Institution findInstitution(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Institution.class, id);
        } finally {
            em.close();
        }
    }

    public int getInstitutionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Institution> rt = cq.from(Institution.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
