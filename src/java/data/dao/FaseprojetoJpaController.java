/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Faseprojeto;
import data.crud.Fasesprocesso;
import data.crud.Gerenteprocesso;
import data.crud.Projeto;
import data.crud.Usuario;
import data.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author padrao
 */
public class FaseprojetoJpaController implements Serializable {

    public FaseprojetoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Faseprojeto faseprojeto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fasesprocesso idfase = faseprojeto.getIdfase();
            if (idfase != null) {
                idfase = em.getReference(idfase.getClass(), idfase.getId());
                faseprojeto.setIdfase(idfase);
            }
            Usuario idgerenteprocesso = faseprojeto.getIdusuariogerenteprocesso();
            if (idgerenteprocesso != null) {
                idgerenteprocesso = em.getReference(idgerenteprocesso.getClass(), idgerenteprocesso.getId());
                faseprojeto.setIdusuariogerenteprocesso(idgerenteprocesso);
            }
            Projeto idprojeto = faseprojeto.getIdprojeto();
            if (idprojeto != null) {
                idprojeto = em.getReference(idprojeto.getClass(), idprojeto.getId());
                faseprojeto.setIdprojeto(idprojeto);
            }
            em.persist(faseprojeto);
            if (idfase != null) {
                idfase.getFaseprojetoCollection().add(faseprojeto);
                idfase = em.merge(idfase);
            }
//            if (idgerenteprocesso != null) {
//                idgerenteprocesso.getFaseprojetoCollection().add(faseprojeto);
//                idgerenteprocesso = em.merge(idgerenteprocesso);
//            }
            if (idprojeto != null) {
                idprojeto.getFaseprojetoCollection().add(faseprojeto);
                idprojeto = em.merge(idprojeto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Faseprojeto faseprojeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faseprojeto persistentFaseprojeto = em.find(Faseprojeto.class, faseprojeto.getId());
            Fasesprocesso idfaseOld = persistentFaseprojeto.getIdfase();
            Fasesprocesso idfaseNew = faseprojeto.getIdfase();
            Usuario idgerenteprocessoOld = persistentFaseprojeto.getIdusuariogerenteprocesso();
            Usuario idgerenteprocessoNew = faseprojeto.getIdusuariogerenteprocesso();
            Projeto idprojetoOld = persistentFaseprojeto.getIdprojeto();
            Projeto idprojetoNew = faseprojeto.getIdprojeto();
            if (idfaseNew != null) {
                idfaseNew = em.getReference(idfaseNew.getClass(), idfaseNew.getId());
                faseprojeto.setIdfase(idfaseNew);
            }
            if (idgerenteprocessoNew != null) {
                idgerenteprocessoNew = em.getReference(idgerenteprocessoNew.getClass(), idgerenteprocessoNew.getId());
                faseprojeto.setIdusuariogerenteprocesso(idgerenteprocessoNew);
            }
            if (idprojetoNew != null) {
                idprojetoNew = em.getReference(idprojetoNew.getClass(), idprojetoNew.getId());
                faseprojeto.setIdprojeto(idprojetoNew);
            }
            faseprojeto = em.merge(faseprojeto);
            if (idfaseOld != null && !idfaseOld.equals(idfaseNew)) {
                idfaseOld.getFaseprojetoCollection().remove(faseprojeto);
                idfaseOld = em.merge(idfaseOld);
            }
            if (idfaseNew != null && !idfaseNew.equals(idfaseOld)) {
                idfaseNew.getFaseprojetoCollection().add(faseprojeto);
                idfaseNew = em.merge(idfaseNew);
            }
//            if (idgerenteprocessoOld != null && !idgerenteprocessoOld.equals(idgerenteprocessoNew)) {
//                idgerenteprocessoOld.getFaseprojetoCollection().remove(faseprojeto);
//                idgerenteprocessoOld = em.merge(idgerenteprocessoOld);
//            }
//            if (idgerenteprocessoNew != null && !idgerenteprocessoNew.equals(idgerenteprocessoOld)) {
//                idgerenteprocessoNew.getFaseprojetoCollection().add(faseprojeto);
//                idgerenteprocessoNew = em.merge(idgerenteprocessoNew);
//            }
            if (idprojetoOld != null && !idprojetoOld.equals(idprojetoNew)) {
                idprojetoOld.getFaseprojetoCollection().remove(faseprojeto);
                idprojetoOld = em.merge(idprojetoOld);
            }
            if (idprojetoNew != null && !idprojetoNew.equals(idprojetoOld)) {
                idprojetoNew.getFaseprojetoCollection().add(faseprojeto);
                idprojetoNew = em.merge(idprojetoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = faseprojeto.getId();
                if (findFaseprojeto(id) == null) {
                    throw new NonexistentEntityException("The faseprojeto with id " + id + " no longer exists.");
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
            Faseprojeto faseprojeto;
            try {
                faseprojeto = em.getReference(Faseprojeto.class, id);
                faseprojeto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The faseprojeto with id " + id + " no longer exists.", enfe);
            }
            Fasesprocesso idfase = faseprojeto.getIdfase();
            if (idfase != null) {
                idfase.getFaseprojetoCollection().remove(faseprojeto);
                idfase = em.merge(idfase);
            }
//            Gerenteprocesso idgerenteprocesso = faseprojeto.getIdgerenteprocesso();
//            if (idgerenteprocesso != null) {
//                idgerenteprocesso.getFaseprojetoCollection().remove(faseprojeto);
//                idgerenteprocesso = em.merge(idgerenteprocesso);
//            }
            Projeto idprojeto = faseprojeto.getIdprojeto();
            if (idprojeto != null) {
                idprojeto.getFaseprojetoCollection().remove(faseprojeto);
                idprojeto = em.merge(idprojeto);
            }
            em.remove(faseprojeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Faseprojeto> findFaseprojetoEntities() {
        return findFaseprojetoEntities(true, -1, -1);
    }

    public List<Faseprojeto> findFaseprojetoEntities(int maxResults, int firstResult) {
        return findFaseprojetoEntities(false, maxResults, firstResult);
    }

    private List<Faseprojeto> findFaseprojetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Faseprojeto.class));
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

    public Faseprojeto findFaseprojeto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Faseprojeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getFaseprojetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Faseprojeto> rt = cq.from(Faseprojeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
