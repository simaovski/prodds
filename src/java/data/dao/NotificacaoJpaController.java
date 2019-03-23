/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Notificacao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import data.crud.Usuario;
import data.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author padrao
 */
public class NotificacaoJpaController implements Serializable {

    public NotificacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notificacao notificacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idusuario = notificacao.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getId());
                notificacao.setIdusuario(idusuario);
            }
            em.persist(notificacao);
            if (idusuario != null) {
                idusuario.getNotificacaoCollection().add(notificacao);
                idusuario = em.merge(idusuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notificacao notificacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notificacao persistentNotificacao = em.find(Notificacao.class, notificacao.getId());
            Usuario idusuarioOld = persistentNotificacao.getIdusuario();
            Usuario idusuarioNew = notificacao.getIdusuario();
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getId());
                notificacao.setIdusuario(idusuarioNew);
            }
            notificacao = em.merge(notificacao);
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getNotificacaoCollection().remove(notificacao);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getNotificacaoCollection().add(notificacao);
                idusuarioNew = em.merge(idusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = notificacao.getId();
                if (findNotificacao(id) == null) {
                    throw new NonexistentEntityException("The notificacao with id " + id + " no longer exists.");
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
            Notificacao notificacao;
            try {
                notificacao = em.getReference(Notificacao.class, id);
                notificacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notificacao with id " + id + " no longer exists.", enfe);
            }
            Usuario idusuario = notificacao.getIdusuario();
            if (idusuario != null) {
                idusuario.getNotificacaoCollection().remove(notificacao);
                idusuario = em.merge(idusuario);
            }
            em.remove(notificacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notificacao> findNotificacaoEntities() {
        return findNotificacaoEntities(true, -1, -1);
    }

    public List<Notificacao> findNotificacaoEntities(int maxResults, int firstResult) {
        return findNotificacaoEntities(false, maxResults, firstResult);
    }

    private List<Notificacao> findNotificacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notificacao.class));
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

    public Notificacao findNotificacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notificacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notificacao> rt = cq.from(Notificacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
