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
import data.crud.Equipe;
import data.crud.Notificacaogerente;
import data.crud.Projeto;
import data.crud.Usuario;
import data.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author padrao
 */
public class NotificacaogerenteJpaController implements Serializable {

    public NotificacaogerenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notificacaogerente notificacaogerente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe idequipe = notificacaogerente.getIdequipe();
            if (idequipe != null) {
                idequipe = em.getReference(idequipe.getClass(), idequipe.getId());
                notificacaogerente.setIdequipe(idequipe);
            }
            Projeto idprojeto = notificacaogerente.getIdprojeto();
            if (idprojeto != null) {
                idprojeto = em.getReference(idprojeto.getClass(), idprojeto.getId());
                notificacaogerente.setIdprojeto(idprojeto);
            }
            Usuario idusuario = notificacaogerente.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getId());
                notificacaogerente.setIdusuario(idusuario);
            }
            Usuario idgerente = notificacaogerente.getIdgerente();
            if (idgerente != null) {
                idgerente = em.getReference(idgerente.getClass(), idgerente.getId());
                notificacaogerente.setIdgerente(idgerente);
            }
            em.persist(notificacaogerente);
            if (idequipe != null) {
                idequipe.getNotificacaogerenteCollection().add(notificacaogerente);
                idequipe = em.merge(idequipe);
            }
            if (idprojeto != null) {
                idprojeto.getNotificacaogerenteCollection().add(notificacaogerente);
                idprojeto = em.merge(idprojeto);
            }
            if (idusuario != null) {
                idusuario.getNotificacaogerenteCollection().add(notificacaogerente);
                idusuario = em.merge(idusuario);
            }
            if (idgerente != null) {
                idgerente.getNotificacaogerenteCollection().add(notificacaogerente);
                idgerente = em.merge(idgerente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notificacaogerente notificacaogerente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notificacaogerente persistentNotificacaogerente = em.find(Notificacaogerente.class, notificacaogerente.getId());
            Equipe idequipeOld = persistentNotificacaogerente.getIdequipe();
            Equipe idequipeNew = notificacaogerente.getIdequipe();
            Projeto idprojetoOld = persistentNotificacaogerente.getIdprojeto();
            Projeto idprojetoNew = notificacaogerente.getIdprojeto();
            Usuario idusuarioOld = persistentNotificacaogerente.getIdusuario();
            Usuario idusuarioNew = notificacaogerente.getIdusuario();
            Usuario idgerenteOld = persistentNotificacaogerente.getIdgerente();
            Usuario idgerenteNew = notificacaogerente.getIdgerente();
            if (idequipeNew != null) {
                idequipeNew = em.getReference(idequipeNew.getClass(), idequipeNew.getId());
                notificacaogerente.setIdequipe(idequipeNew);
            }
            if (idprojetoNew != null) {
                idprojetoNew = em.getReference(idprojetoNew.getClass(), idprojetoNew.getId());
                notificacaogerente.setIdprojeto(idprojetoNew);
            }
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getId());
                notificacaogerente.setIdusuario(idusuarioNew);
            }
            if (idgerenteNew != null) {
                idgerenteNew = em.getReference(idgerenteNew.getClass(), idgerenteNew.getId());
                notificacaogerente.setIdgerente(idgerenteNew);
            }
            notificacaogerente = em.merge(notificacaogerente);
            if (idequipeOld != null && !idequipeOld.equals(idequipeNew)) {
                idequipeOld.getNotificacaogerenteCollection().remove(notificacaogerente);
                idequipeOld = em.merge(idequipeOld);
            }
            if (idequipeNew != null && !idequipeNew.equals(idequipeOld)) {
                idequipeNew.getNotificacaogerenteCollection().add(notificacaogerente);
                idequipeNew = em.merge(idequipeNew);
            }
            if (idprojetoOld != null && !idprojetoOld.equals(idprojetoNew)) {
                idprojetoOld.getNotificacaogerenteCollection().remove(notificacaogerente);
                idprojetoOld = em.merge(idprojetoOld);
            }
            if (idprojetoNew != null && !idprojetoNew.equals(idprojetoOld)) {
                idprojetoNew.getNotificacaogerenteCollection().add(notificacaogerente);
                idprojetoNew = em.merge(idprojetoNew);
            }
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getNotificacaogerenteCollection().remove(notificacaogerente);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getNotificacaogerenteCollection().add(notificacaogerente);
                idusuarioNew = em.merge(idusuarioNew);
            }
            if (idgerenteOld != null && !idgerenteOld.equals(idgerenteNew)) {
                idgerenteOld.getNotificacaogerenteCollection().remove(notificacaogerente);
                idgerenteOld = em.merge(idgerenteOld);
            }
            if (idgerenteNew != null && !idgerenteNew.equals(idgerenteOld)) {
                idgerenteNew.getNotificacaogerenteCollection().add(notificacaogerente);
                idgerenteNew = em.merge(idgerenteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = notificacaogerente.getId();
                if (findNotificacaogerente(id) == null) {
                    throw new NonexistentEntityException("The notificacaogerente with id " + id + " no longer exists.");
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
            Notificacaogerente notificacaogerente;
            try {
                notificacaogerente = em.getReference(Notificacaogerente.class, id);
                notificacaogerente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notificacaogerente with id " + id + " no longer exists.", enfe);
            }
            Equipe idequipe = notificacaogerente.getIdequipe();
            if (idequipe != null) {
                idequipe.getNotificacaogerenteCollection().remove(notificacaogerente);
                idequipe = em.merge(idequipe);
            }
            Projeto idprojeto = notificacaogerente.getIdprojeto();
            if (idprojeto != null) {
                idprojeto.getNotificacaogerenteCollection().remove(notificacaogerente);
                idprojeto = em.merge(idprojeto);
            }
            Usuario idusuario = notificacaogerente.getIdusuario();
            if (idusuario != null) {
                idusuario.getNotificacaogerenteCollection().remove(notificacaogerente);
                idusuario = em.merge(idusuario);
            }
            Usuario idgerente = notificacaogerente.getIdgerente();
            if (idgerente != null) {
                idgerente.getNotificacaogerenteCollection().remove(notificacaogerente);
                idgerente = em.merge(idgerente);
            }
            em.remove(notificacaogerente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notificacaogerente> findNotificacaogerenteEntities() {
        return findNotificacaogerenteEntities(true, -1, -1);
    }

    public List<Notificacaogerente> findNotificacaogerenteEntities(int maxResults, int firstResult) {
        return findNotificacaogerenteEntities(false, maxResults, firstResult);
    }

    private List<Notificacaogerente> findNotificacaogerenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notificacaogerente.class));
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

    public Notificacaogerente findNotificacaogerente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notificacaogerente.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificacaogerenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notificacaogerente> rt = cq.from(Notificacaogerente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public boolean verificaNotificacao(Equipe eqp, int id){
        List<Notificacaogerente> lista = findNotificacaogerenteEntities();
        for(Notificacaogerente n : lista){
            int a = eqp.getIdprojeto().getId();
            int b = n.getIdprojeto().getId();
            
            int c = n.getIdgerente().getId();
            int d = eqp.getIdgerente().getId();
            
            int e = n.getIdequipe().getId();
            int f = eqp.getId();
            
            int g = n.getIdusuario().getId();
            if(a == b && c == d && e == f && g == id){
                return true;
            }
        }
        return false;
    }
    
}
