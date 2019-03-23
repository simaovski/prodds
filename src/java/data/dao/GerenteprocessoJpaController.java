/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Gerenteprocesso;
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
public class GerenteprocessoJpaController implements Serializable {

    public GerenteprocessoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gerenteprocesso gerenteprocesso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Projeto idprojeto = gerenteprocesso.getIdprojeto();
            if (idprojeto != null) {
                idprojeto = em.getReference(idprojeto.getClass(), idprojeto.getId());
                gerenteprocesso.setIdprojeto(idprojeto);
            }
            Usuario idgerente = gerenteprocesso.getIdgerente();
            if (idgerente != null) {
                idgerente = em.getReference(idgerente.getClass(), idgerente.getId());
                gerenteprocesso.setIdgerente(idgerente);
            }
            em.persist(gerenteprocesso);
            if (idprojeto != null) {
                idprojeto.getGerenteprocessoCollection().add(gerenteprocesso);
                idprojeto = em.merge(idprojeto);
            }
            if (idgerente != null) {
                idgerente.getGerenteprocessoCollection().add(gerenteprocesso);
                idgerente = em.merge(idgerente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gerenteprocesso gerenteprocesso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gerenteprocesso persistentGerenteprocesso = em.find(Gerenteprocesso.class, gerenteprocesso.getId());
            Projeto idprojetoOld = persistentGerenteprocesso.getIdprojeto();
            Projeto idprojetoNew = gerenteprocesso.getIdprojeto();
            Usuario idgerenteOld = persistentGerenteprocesso.getIdgerente();
            Usuario idgerenteNew = gerenteprocesso.getIdgerente();
            if (idprojetoNew != null) {
                idprojetoNew = em.getReference(idprojetoNew.getClass(), idprojetoNew.getId());
                gerenteprocesso.setIdprojeto(idprojetoNew);
            }
            if (idgerenteNew != null) {
                idgerenteNew = em.getReference(idgerenteNew.getClass(), idgerenteNew.getId());
                gerenteprocesso.setIdgerente(idgerenteNew);
            }
            gerenteprocesso = em.merge(gerenteprocesso);
            if (idprojetoOld != null && !idprojetoOld.equals(idprojetoNew)) {
                idprojetoOld.getGerenteprocessoCollection().remove(gerenteprocesso);
                idprojetoOld = em.merge(idprojetoOld);
            }
            if (idprojetoNew != null && !idprojetoNew.equals(idprojetoOld)) {
                idprojetoNew.getGerenteprocessoCollection().add(gerenteprocesso);
                idprojetoNew = em.merge(idprojetoNew);
            }
            if (idgerenteOld != null && !idgerenteOld.equals(idgerenteNew)) {
                idgerenteOld.getGerenteprocessoCollection().remove(gerenteprocesso);
                idgerenteOld = em.merge(idgerenteOld);
            }
            if (idgerenteNew != null && !idgerenteNew.equals(idgerenteOld)) {
                idgerenteNew.getGerenteprocessoCollection().add(gerenteprocesso);
                idgerenteNew = em.merge(idgerenteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gerenteprocesso.getId();
                if (findGerenteprocesso(id) == null) {
                    throw new NonexistentEntityException("The gerenteprocesso with id " + id + " no longer exists.");
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
            Gerenteprocesso gerenteprocesso;
            try {
                gerenteprocesso = em.getReference(Gerenteprocesso.class, id);
                gerenteprocesso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gerenteprocesso with id " + id + " no longer exists.", enfe);
            }
            Projeto idprojeto = gerenteprocesso.getIdprojeto();
            if (idprojeto != null) {
                idprojeto.getGerenteprocessoCollection().remove(gerenteprocesso);
                idprojeto = em.merge(idprojeto);
            }
            Usuario idgerente = gerenteprocesso.getIdgerente();
            if (idgerente != null) {
                idgerente.getGerenteprocessoCollection().remove(gerenteprocesso);
                idgerente = em.merge(idgerente);
            }
            em.remove(gerenteprocesso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gerenteprocesso> findGerenteprocessoEntities() {
        return findGerenteprocessoEntities(true, -1, -1);
    }

    public List<Gerenteprocesso> findGerenteprocessoEntities(int maxResults, int firstResult) {
        return findGerenteprocessoEntities(false, maxResults, firstResult);
    }

    private List<Gerenteprocesso> findGerenteprocessoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gerenteprocesso.class));
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

    public Gerenteprocesso findGerenteprocesso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gerenteprocesso.class, id);
        } finally {
            em.close();
        }
    }

    public int getGerenteprocessoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gerenteprocesso> rt = cq.from(Gerenteprocesso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
