/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Gerenteprojeto;
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
public class GerenteprojetoJpaController implements Serializable {

    public GerenteprojetoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gerenteprojeto gerenteprojeto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Projeto idprojeto = gerenteprojeto.getIdprojeto();
            if (idprojeto != null) {
                idprojeto = em.getReference(idprojeto.getClass(), idprojeto.getId());
                gerenteprojeto.setIdprojeto(idprojeto);
            }
            Usuario idgerente = gerenteprojeto.getIdgerente();
            if (idgerente != null) {
                idgerente = em.getReference(idgerente.getClass(), idgerente.getId());
                gerenteprojeto.setIdgerente(idgerente);
            }
            em.persist(gerenteprojeto);
            if (idprojeto != null) {
                idprojeto.getGerenteprojetoCollection().add(gerenteprojeto);
                idprojeto = em.merge(idprojeto);
            }
            if (idgerente != null) {
                idgerente.getGerenteprojetoCollection().add(gerenteprojeto);
                idgerente = em.merge(idgerente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gerenteprojeto gerenteprojeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gerenteprojeto persistentGerenteprojeto = em.find(Gerenteprojeto.class, gerenteprojeto.getId());
            Projeto idprojetoOld = persistentGerenteprojeto.getIdprojeto();
            Projeto idprojetoNew = gerenteprojeto.getIdprojeto();
            Usuario idgerenteOld = persistentGerenteprojeto.getIdgerente();
            Usuario idgerenteNew = gerenteprojeto.getIdgerente();
            if (idprojetoNew != null) {
                idprojetoNew = em.getReference(idprojetoNew.getClass(), idprojetoNew.getId());
                gerenteprojeto.setIdprojeto(idprojetoNew);
            }
            if (idgerenteNew != null) {
                idgerenteNew = em.getReference(idgerenteNew.getClass(), idgerenteNew.getId());
                gerenteprojeto.setIdgerente(idgerenteNew);
            }
            gerenteprojeto = em.merge(gerenteprojeto);
            if (idprojetoOld != null && !idprojetoOld.equals(idprojetoNew)) {
                idprojetoOld.getGerenteprojetoCollection().remove(gerenteprojeto);
                idprojetoOld = em.merge(idprojetoOld);
            }
            if (idprojetoNew != null && !idprojetoNew.equals(idprojetoOld)) {
                idprojetoNew.getGerenteprojetoCollection().add(gerenteprojeto);
                idprojetoNew = em.merge(idprojetoNew);
            }
            if (idgerenteOld != null && !idgerenteOld.equals(idgerenteNew)) {
                idgerenteOld.getGerenteprojetoCollection().remove(gerenteprojeto);
                idgerenteOld = em.merge(idgerenteOld);
            }
            if (idgerenteNew != null && !idgerenteNew.equals(idgerenteOld)) {
                idgerenteNew.getGerenteprojetoCollection().add(gerenteprojeto);
                idgerenteNew = em.merge(idgerenteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gerenteprojeto.getId();
                if (findGerenteprojeto(id) == null) {
                    throw new NonexistentEntityException("The gerenteprojeto with id " + id + " no longer exists.");
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
            Gerenteprojeto gerenteprojeto;
            try {
                gerenteprojeto = em.getReference(Gerenteprojeto.class, id);
                gerenteprojeto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gerenteprojeto with id " + id + " no longer exists.", enfe);
            }
            Projeto idprojeto = gerenteprojeto.getIdprojeto();
            if (idprojeto != null) {
                idprojeto.getGerenteprojetoCollection().remove(gerenteprojeto);
                idprojeto = em.merge(idprojeto);
            }
            Usuario idgerente = gerenteprojeto.getIdgerente();
            if (idgerente != null) {
                idgerente.getGerenteprojetoCollection().remove(gerenteprojeto);
                idgerente = em.merge(idgerente);
            }
            em.remove(gerenteprojeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gerenteprojeto> findGerenteprojetoEntities() {
        return findGerenteprojetoEntities(true, -1, -1);
    }

    public List<Gerenteprojeto> findGerenteprojetoEntities(int maxResults, int firstResult) {
        return findGerenteprojetoEntities(false, maxResults, firstResult);
    }

    private List<Gerenteprojeto> findGerenteprojetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gerenteprojeto.class));
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

    public Gerenteprojeto findGerenteprojeto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gerenteprojeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getGerenteprojetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gerenteprojeto> rt = cq.from(Gerenteprojeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
