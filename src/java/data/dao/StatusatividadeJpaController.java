/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import data.crud.Atividademembroequipe;
import data.crud.Statusatividade;
import data.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author padrao
 */
public class StatusatividadeJpaController implements Serializable {

    public StatusatividadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Statusatividade statusatividade) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividademembroequipe idatividade = statusatividade.getIdatividade();
            if (idatividade != null) {
                idatividade = em.getReference(idatividade.getClass(), idatividade.getId());
                statusatividade.setIdatividade(idatividade);
            }
            em.persist(statusatividade);
            if (idatividade != null) {
                idatividade.getStatusatividadeCollection().add(statusatividade);
                idatividade = em.merge(idatividade);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Statusatividade statusatividade) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Statusatividade persistentStatusatividade = em.find(Statusatividade.class, statusatividade.getId());
            Atividademembroequipe idatividadeOld = persistentStatusatividade.getIdatividade();
            Atividademembroequipe idatividadeNew = statusatividade.getIdatividade();
            if (idatividadeNew != null) {
                idatividadeNew = em.getReference(idatividadeNew.getClass(), idatividadeNew.getId());
                statusatividade.setIdatividade(idatividadeNew);
            }
            statusatividade = em.merge(statusatividade);
            if (idatividadeOld != null && !idatividadeOld.equals(idatividadeNew)) {
                idatividadeOld.getStatusatividadeCollection().remove(statusatividade);
                idatividadeOld = em.merge(idatividadeOld);
            }
            if (idatividadeNew != null && !idatividadeNew.equals(idatividadeOld)) {
                idatividadeNew.getStatusatividadeCollection().add(statusatividade);
                idatividadeNew = em.merge(idatividadeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = statusatividade.getId();
                if (findStatusatividade(id) == null) {
                    throw new NonexistentEntityException("The statusatividade with id " + id + " no longer exists.");
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
            Statusatividade statusatividade;
            try {
                statusatividade = em.getReference(Statusatividade.class, id);
                statusatividade.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The statusatividade with id " + id + " no longer exists.", enfe);
            }
            Atividademembroequipe idatividade = statusatividade.getIdatividade();
            if (idatividade != null) {
                idatividade.getStatusatividadeCollection().remove(statusatividade);
                idatividade = em.merge(idatividade);
            }
            em.remove(statusatividade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Statusatividade> findStatusatividadeEntities() {
        return findStatusatividadeEntities(true, -1, -1);
    }

    public List<Statusatividade> findStatusatividadeEntities(int maxResults, int firstResult) {
        return findStatusatividadeEntities(false, maxResults, firstResult);
    }

    private List<Statusatividade> findStatusatividadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Statusatividade.class));
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

    public Statusatividade findStatusatividade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Statusatividade.class, id);
        } finally {
            em.close();
        }
    }

    public int getStatusatividadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Statusatividade> rt = cq.from(Statusatividade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
