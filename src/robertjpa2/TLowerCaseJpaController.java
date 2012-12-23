/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robertjpa2;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import robertjpa2.exceptions.NonexistentEntityException;
import robertjpa2.exceptions.PreexistingEntityException;

/**
 *
 * @author robert
 */
public class TLowerCaseJpaController implements Serializable {

    public TLowerCaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    TLowerCaseJpaController() {
        emf = Persistence.createEntityManagerFactory("RobertJPA2PU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TLowerCase TLowerCase) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(TLowerCase);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTLowerCase(TLowerCase.getId()) != null) {
                throw new PreexistingEntityException("TLowerCase " + TLowerCase + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TLowerCase TLowerCase) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TLowerCase = em.merge(TLowerCase);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = TLowerCase.getId();
                if (findTLowerCase(id) == null) {
                    throw new NonexistentEntityException("The tLowerCase with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TLowerCase TLowerCase;
            try {
                TLowerCase = em.getReference(TLowerCase.class, id);
                TLowerCase.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The TLowerCase with id " + id + " no longer exists.", enfe);
            }
            em.remove(TLowerCase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TLowerCase> findTLowerCaseEntities() {
        return findTLowerCaseEntities(true, -1, -1);
    }

    public List<TLowerCase> findTLowerCaseEntities(int maxResults, int firstResult) {
        return findTLowerCaseEntities(false, maxResults, firstResult);
    }

    private List<TLowerCase> findTLowerCaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TLowerCase.class));
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

    public TLowerCase findTLowerCase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TLowerCase.class, id);
        } finally {
            em.close();
        }
    }

    public int getTLowerCaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TLowerCase> rt = cq.from(TLowerCase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
