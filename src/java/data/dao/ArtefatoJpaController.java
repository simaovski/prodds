/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Artefato;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import data.crud.Atividademembroequipe;
import data.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author padrao
 */
public class ArtefatoJpaController implements Serializable {

    public ArtefatoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Artefato artefato) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividademembroequipe idatividade = artefato.getIdatividade();
            if (idatividade != null) {
                idatividade = em.getReference(idatividade.getClass(), idatividade.getId());
                artefato.setIdatividade(idatividade);
            }
            em.persist(artefato);
            if (idatividade != null) {
                idatividade.getArtefatoCollection().add(artefato);
                idatividade = em.merge(idatividade);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Artefato artefato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Artefato persistentArtefato = em.find(Artefato.class, artefato.getId());
            Atividademembroequipe idatividadeOld = persistentArtefato.getIdatividade();
            Atividademembroequipe idatividadeNew = artefato.getIdatividade();
            if (idatividadeNew != null) {
                idatividadeNew = em.getReference(idatividadeNew.getClass(), idatividadeNew.getId());
                artefato.setIdatividade(idatividadeNew);
            }
            artefato = em.merge(artefato);
            if (idatividadeOld != null && !idatividadeOld.equals(idatividadeNew)) {
                idatividadeOld.getArtefatoCollection().remove(artefato);
                idatividadeOld = em.merge(idatividadeOld);
            }
            if (idatividadeNew != null && !idatividadeNew.equals(idatividadeOld)) {
                idatividadeNew.getArtefatoCollection().add(artefato);
                idatividadeNew = em.merge(idatividadeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = artefato.getId();
                if (findArtefato(id) == null) {
                    throw new NonexistentEntityException("The artefato with id " + id + " no longer exists.");
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
            Artefato artefato;
            try {
                artefato = em.getReference(Artefato.class, id);
                artefato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The artefato with id " + id + " no longer exists.", enfe);
            }
            Atividademembroequipe idatividade = artefato.getIdatividade();
            if (idatividade != null) {
                idatividade.getArtefatoCollection().remove(artefato);
                idatividade = em.merge(idatividade);
            }
            em.remove(artefato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Artefato> findArtefatoEntities() {
        return findArtefatoEntities(true, -1, -1);
    }

    public List<Artefato> findArtefatoEntities(int maxResults, int firstResult) {
        return findArtefatoEntities(false, maxResults, firstResult);
    }

    private List<Artefato> findArtefatoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Artefato.class));
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

    public Artefato findArtefato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Artefato.class, id);
        } finally {
            em.close();
        }
    }

    public int getArtefatoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Artefato> rt = cq.from(Artefato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
