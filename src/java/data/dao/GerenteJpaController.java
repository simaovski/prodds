/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Gerente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class GerenteJpaController implements Serializable {

    public GerenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gerente gerente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Projeto projeto = gerente.getProjeto();
            if (projeto != null) {
                projeto = em.getReference(projeto.getClass(), projeto.getId());
                gerente.setProjeto(projeto);
            }
            Usuario idusuariogerente = gerente.getIdusuariogerente();
            if (idusuariogerente != null) {
                idusuariogerente = em.getReference(idusuariogerente.getClass(), idusuariogerente.getId());
                gerente.setIdusuariogerente(idusuariogerente);
            }
            Usuario idusuarioadm = gerente.getIdusuarioadm();
            if (idusuarioadm != null) {
                idusuarioadm = em.getReference(idusuarioadm.getClass(), idusuarioadm.getId());
                gerente.setIdusuarioadm(idusuarioadm);
            }
            em.persist(gerente);
            if (projeto != null) {
                projeto.getGerenteCollection().add(gerente);
                projeto = em.merge(projeto);
            }
            if (idusuariogerente != null) {
                idusuariogerente.getGerenteCollection().add(gerente);
                idusuariogerente = em.merge(idusuariogerente);
            }
            if (idusuarioadm != null) {
                idusuarioadm.getGerenteCollection().add(gerente);
                idusuarioadm = em.merge(idusuarioadm);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gerente gerente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gerente persistentGerente = em.find(Gerente.class, gerente.getId());
            Projeto projetoOld = persistentGerente.getProjeto();
            Projeto projetoNew = gerente.getProjeto();
            Usuario idusuariogerenteOld = persistentGerente.getIdusuariogerente();
            Usuario idusuariogerenteNew = gerente.getIdusuariogerente();
            Usuario idusuarioadmOld = persistentGerente.getIdusuarioadm();
            Usuario idusuarioadmNew = gerente.getIdusuarioadm();
            if (projetoNew != null) {
                projetoNew = em.getReference(projetoNew.getClass(), projetoNew.getId());
                gerente.setProjeto(projetoNew);
            }
            if (idusuariogerenteNew != null) {
                idusuariogerenteNew = em.getReference(idusuariogerenteNew.getClass(), idusuariogerenteNew.getId());
                gerente.setIdusuariogerente(idusuariogerenteNew);
            }
            if (idusuarioadmNew != null) {
                idusuarioadmNew = em.getReference(idusuarioadmNew.getClass(), idusuarioadmNew.getId());
                gerente.setIdusuarioadm(idusuarioadmNew);
            }
            gerente = em.merge(gerente);
            if (projetoOld != null && !projetoOld.equals(projetoNew)) {
                projetoOld.getGerenteCollection().remove(gerente);
                projetoOld = em.merge(projetoOld);
            }
            if (projetoNew != null && !projetoNew.equals(projetoOld)) {
                projetoNew.getGerenteCollection().add(gerente);
                projetoNew = em.merge(projetoNew);
            }
            if (idusuariogerenteOld != null && !idusuariogerenteOld.equals(idusuariogerenteNew)) {
                idusuariogerenteOld.getGerenteCollection().remove(gerente);
                idusuariogerenteOld = em.merge(idusuariogerenteOld);
            }
            if (idusuariogerenteNew != null && !idusuariogerenteNew.equals(idusuariogerenteOld)) {
                idusuariogerenteNew.getGerenteCollection().add(gerente);
                idusuariogerenteNew = em.merge(idusuariogerenteNew);
            }
            if (idusuarioadmOld != null && !idusuarioadmOld.equals(idusuarioadmNew)) {
                idusuarioadmOld.getGerenteCollection().remove(gerente);
                idusuarioadmOld = em.merge(idusuarioadmOld);
            }
            if (idusuarioadmNew != null && !idusuarioadmNew.equals(idusuarioadmOld)) {
                idusuarioadmNew.getGerenteCollection().add(gerente);
                idusuarioadmNew = em.merge(idusuarioadmNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gerente.getId();
                if (findGerente(id) == null) {
                    throw new NonexistentEntityException("The gerente with id " + id + " no longer exists.");
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
            Gerente gerente;
            try {
                gerente = em.getReference(Gerente.class, id);
                gerente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gerente with id " + id + " no longer exists.", enfe);
            }
            Projeto projeto = gerente.getProjeto();
            if (projeto != null) {
                projeto.getGerenteCollection().remove(gerente);
                projeto = em.merge(projeto);
            }
            Usuario idusuariogerente = gerente.getIdusuariogerente();
            if (idusuariogerente != null) {
                idusuariogerente.getGerenteCollection().remove(gerente);
                idusuariogerente = em.merge(idusuariogerente);
            }
            Usuario idusuarioadm = gerente.getIdusuarioadm();
            if (idusuarioadm != null) {
                idusuarioadm.getGerenteCollection().remove(gerente);
                idusuarioadm = em.merge(idusuarioadm);
            }
            em.remove(gerente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gerente> findGerenteEntities() {
        return findGerenteEntities(true, -1, -1);
    }

    public List<Gerente> findGerenteEntities(int maxResults, int firstResult) {
        return findGerenteEntities(false, maxResults, firstResult);
    }

    private List<Gerente> findGerenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gerente.class));
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

    public Gerente findGerente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gerente.class, id);
        } finally {
            em.close();
        }
    }

    public int getGerenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gerente> rt = cq.from(Gerente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
