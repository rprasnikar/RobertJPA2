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

/**
 *
 * @author robert
 */
public class tlcJpaController implements Serializable {



    public tlcJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    tlcJpaController() {
        emf = Persistence.createEntityManagerFactory("RobertJPA2PU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(tlc tlc) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tlc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(tlc tlc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tlc = em.merge(tlc);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tlc.getId();
                if (findtlc(id) == null) {
                    throw new NonexistentEntityException("The tlc with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tlc tlc;
            try {
                tlc = em.getReference(tlc.class, id);
                tlc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tlc with id " + id + " no longer exists.", enfe);
            }
            em.remove(tlc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<tlc> findtlcEntities() {
        return findtlcEntities(true, -1, -1);
    }

    public List<tlc> findtlcEntities(int maxResults, int firstResult) {
        return findtlcEntities(false, maxResults, firstResult);
    }

    private List<tlc> findtlcEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(tlc.class));
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

    public tlc findtlc(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(tlc.class, id);
        } finally {
            em.close();
        }
    }

    public int gettlcCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<tlc> rt = cq.from(tlc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
