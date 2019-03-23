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
import data.crud.Logusuario;
import data.crud.Projeto;
import data.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author padrao
 */
public class LogusuarioJpaController implements Serializable {

    public LogusuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Logusuario logusuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividademembroequipe atividade = logusuario.getAtividade();
            if (atividade != null) {
                atividade = em.getReference(atividade.getClass(), atividade.getId());
                logusuario.setAtividade(atividade);
            }
            Projeto idprojeto = logusuario.getIdprojeto();
            if (idprojeto != null) {
                idprojeto = em.getReference(idprojeto.getClass(), idprojeto.getId());
                logusuario.setIdprojeto(idprojeto);
            }
            em.persist(logusuario);
            if (atividade != null) {
                atividade.getLogusuarioCollection().add(logusuario);
                atividade = em.merge(atividade);
            }
            if (idprojeto != null) {
                idprojeto.getLogusuarioCollection().add(logusuario);
                idprojeto = em.merge(idprojeto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Logusuario logusuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Logusuario persistentLogusuario = em.find(Logusuario.class, logusuario.getId());
            Atividademembroequipe atividadeOld = persistentLogusuario.getAtividade();
            Atividademembroequipe atividadeNew = logusuario.getAtividade();
            Projeto idprojetoOld = persistentLogusuario.getIdprojeto();
            Projeto idprojetoNew = logusuario.getIdprojeto();
            if (atividadeNew != null) {
                atividadeNew = em.getReference(atividadeNew.getClass(), atividadeNew.getId());
                logusuario.setAtividade(atividadeNew);
            }
            if (idprojetoNew != null) {
                idprojetoNew = em.getReference(idprojetoNew.getClass(), idprojetoNew.getId());
                logusuario.setIdprojeto(idprojetoNew);
            }
            logusuario = em.merge(logusuario);
            if (atividadeOld != null && !atividadeOld.equals(atividadeNew)) {
                atividadeOld.getLogusuarioCollection().remove(logusuario);
                atividadeOld = em.merge(atividadeOld);
            }
            if (atividadeNew != null && !atividadeNew.equals(atividadeOld)) {
                atividadeNew.getLogusuarioCollection().add(logusuario);
                atividadeNew = em.merge(atividadeNew);
            }
            if (idprojetoOld != null && !idprojetoOld.equals(idprojetoNew)) {
                idprojetoOld.getLogusuarioCollection().remove(logusuario);
                idprojetoOld = em.merge(idprojetoOld);
            }
            if (idprojetoNew != null && !idprojetoNew.equals(idprojetoOld)) {
                idprojetoNew.getLogusuarioCollection().add(logusuario);
                idprojetoNew = em.merge(idprojetoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = logusuario.getId();
                if (findLogusuario(id) == null) {
                    throw new NonexistentEntityException("The logusuario with id " + id + " no longer exists.");
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
            Logusuario logusuario;
            try {
                logusuario = em.getReference(Logusuario.class, id);
                logusuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logusuario with id " + id + " no longer exists.", enfe);
            }
            Atividademembroequipe atividade = logusuario.getAtividade();
            if (atividade != null) {
                atividade.getLogusuarioCollection().remove(logusuario);
                atividade = em.merge(atividade);
            }
            Projeto idprojeto = logusuario.getIdprojeto();
            if (idprojeto != null) {
                idprojeto.getLogusuarioCollection().remove(logusuario);
                idprojeto = em.merge(idprojeto);
            }
            em.remove(logusuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Logusuario> findLogusuarioEntities() {
        return findLogusuarioEntities(true, -1, -1);
    }

    public List<Logusuario> findLogusuarioEntities(int maxResults, int firstResult) {
        return findLogusuarioEntities(false, maxResults, firstResult);
    }

    private List<Logusuario> findLogusuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Logusuario.class));
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

    public Logusuario findLogusuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Logusuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogusuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Logusuario> rt = cq.from(Logusuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
