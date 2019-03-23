/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import br.util.UtilDao;
import data.crud.Atividadesfaseprocesso;
import data.crud.Fasesprocesso;
import data.crud.Processo;
import data.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
public class FasesprocessoJpaController implements Serializable {

    public FasesprocessoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fasesprocesso fasesprocesso) {
        if (fasesprocesso.getAtividadesfaseprocessoCollection() == null) {
            fasesprocesso.setAtividadesfaseprocessoCollection(new ArrayList<Atividadesfaseprocesso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Processo idprocesso = fasesprocesso.getIdprocesso();
            if (idprocesso != null) {
                idprocesso = em.getReference(idprocesso.getClass(), idprocesso.getId());
                fasesprocesso.setIdprocesso(idprocesso);
            }
            Collection<Atividadesfaseprocesso> attachedAtividadesfaseprocessoCollection = new ArrayList<Atividadesfaseprocesso>();
            for (Atividadesfaseprocesso atividadesfaseprocessoCollectionAtividadesfaseprocessoToAttach : fasesprocesso.getAtividadesfaseprocessoCollection()) {
                atividadesfaseprocessoCollectionAtividadesfaseprocessoToAttach = em.getReference(atividadesfaseprocessoCollectionAtividadesfaseprocessoToAttach.getClass(), atividadesfaseprocessoCollectionAtividadesfaseprocessoToAttach.getId());
                attachedAtividadesfaseprocessoCollection.add(atividadesfaseprocessoCollectionAtividadesfaseprocessoToAttach);
            }
            fasesprocesso.setAtividadesfaseprocessoCollection(attachedAtividadesfaseprocessoCollection);
            em.persist(fasesprocesso);
            if (idprocesso != null) {
                idprocesso.getFasesprocessoCollection().add(fasesprocesso);
                idprocesso = em.merge(idprocesso);
            }
            for (Atividadesfaseprocesso atividadesfaseprocessoCollectionAtividadesfaseprocesso : fasesprocesso.getAtividadesfaseprocessoCollection()) {
                Fasesprocesso oldIdfaseOfAtividadesfaseprocessoCollectionAtividadesfaseprocesso = atividadesfaseprocessoCollectionAtividadesfaseprocesso.getIdfase();
                atividadesfaseprocessoCollectionAtividadesfaseprocesso.setIdfase(fasesprocesso);
                atividadesfaseprocessoCollectionAtividadesfaseprocesso = em.merge(atividadesfaseprocessoCollectionAtividadesfaseprocesso);
                if (oldIdfaseOfAtividadesfaseprocessoCollectionAtividadesfaseprocesso != null) {
                    oldIdfaseOfAtividadesfaseprocessoCollectionAtividadesfaseprocesso.getAtividadesfaseprocessoCollection().remove(atividadesfaseprocessoCollectionAtividadesfaseprocesso);
                    oldIdfaseOfAtividadesfaseprocessoCollectionAtividadesfaseprocesso = em.merge(oldIdfaseOfAtividadesfaseprocessoCollectionAtividadesfaseprocesso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fasesprocesso fasesprocesso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fasesprocesso persistentFasesprocesso = em.find(Fasesprocesso.class, fasesprocesso.getId());
            Processo idprocessoOld = persistentFasesprocesso.getIdprocesso();
            Processo idprocessoNew = fasesprocesso.getIdprocesso();
            Collection<Atividadesfaseprocesso> atividadesfaseprocessoCollectionOld = persistentFasesprocesso.getAtividadesfaseprocessoCollection();
            Collection<Atividadesfaseprocesso> atividadesfaseprocessoCollectionNew = fasesprocesso.getAtividadesfaseprocessoCollection();
            if (idprocessoNew != null) {
                idprocessoNew = em.getReference(idprocessoNew.getClass(), idprocessoNew.getId());
                fasesprocesso.setIdprocesso(idprocessoNew);
            }
            Collection<Atividadesfaseprocesso> attachedAtividadesfaseprocessoCollectionNew = new ArrayList<Atividadesfaseprocesso>();
            for (Atividadesfaseprocesso atividadesfaseprocessoCollectionNewAtividadesfaseprocessoToAttach : atividadesfaseprocessoCollectionNew) {
                atividadesfaseprocessoCollectionNewAtividadesfaseprocessoToAttach = em.getReference(atividadesfaseprocessoCollectionNewAtividadesfaseprocessoToAttach.getClass(), atividadesfaseprocessoCollectionNewAtividadesfaseprocessoToAttach.getId());
                attachedAtividadesfaseprocessoCollectionNew.add(atividadesfaseprocessoCollectionNewAtividadesfaseprocessoToAttach);
            }
            atividadesfaseprocessoCollectionNew = attachedAtividadesfaseprocessoCollectionNew;
            fasesprocesso.setAtividadesfaseprocessoCollection(atividadesfaseprocessoCollectionNew);
            fasesprocesso = em.merge(fasesprocesso);
            if (idprocessoOld != null && !idprocessoOld.equals(idprocessoNew)) {
                idprocessoOld.getFasesprocessoCollection().remove(fasesprocesso);
                idprocessoOld = em.merge(idprocessoOld);
            }
            if (idprocessoNew != null && !idprocessoNew.equals(idprocessoOld)) {
                idprocessoNew.getFasesprocessoCollection().add(fasesprocesso);
                idprocessoNew = em.merge(idprocessoNew);
            }
            for (Atividadesfaseprocesso atividadesfaseprocessoCollectionOldAtividadesfaseprocesso : atividadesfaseprocessoCollectionOld) {
                if (!atividadesfaseprocessoCollectionNew.contains(atividadesfaseprocessoCollectionOldAtividadesfaseprocesso)) {
                    atividadesfaseprocessoCollectionOldAtividadesfaseprocesso.setIdfase(null);
                    atividadesfaseprocessoCollectionOldAtividadesfaseprocesso = em.merge(atividadesfaseprocessoCollectionOldAtividadesfaseprocesso);
                }
            }
            for (Atividadesfaseprocesso atividadesfaseprocessoCollectionNewAtividadesfaseprocesso : atividadesfaseprocessoCollectionNew) {
                if (!atividadesfaseprocessoCollectionOld.contains(atividadesfaseprocessoCollectionNewAtividadesfaseprocesso)) {
                    Fasesprocesso oldIdfaseOfAtividadesfaseprocessoCollectionNewAtividadesfaseprocesso = atividadesfaseprocessoCollectionNewAtividadesfaseprocesso.getIdfase();
                    atividadesfaseprocessoCollectionNewAtividadesfaseprocesso.setIdfase(fasesprocesso);
                    atividadesfaseprocessoCollectionNewAtividadesfaseprocesso = em.merge(atividadesfaseprocessoCollectionNewAtividadesfaseprocesso);
                    if (oldIdfaseOfAtividadesfaseprocessoCollectionNewAtividadesfaseprocesso != null && !oldIdfaseOfAtividadesfaseprocessoCollectionNewAtividadesfaseprocesso.equals(fasesprocesso)) {
                        oldIdfaseOfAtividadesfaseprocessoCollectionNewAtividadesfaseprocesso.getAtividadesfaseprocessoCollection().remove(atividadesfaseprocessoCollectionNewAtividadesfaseprocesso);
                        oldIdfaseOfAtividadesfaseprocessoCollectionNewAtividadesfaseprocesso = em.merge(oldIdfaseOfAtividadesfaseprocessoCollectionNewAtividadesfaseprocesso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fasesprocesso.getId();
                if (findFasesprocesso(id) == null) {
                    throw new NonexistentEntityException("The fasesprocesso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void editar(Fasesprocesso fasesprocesso) throws NonexistentEntityException, Exception {
        EntityManagerFactory emf = UtilDao.retornaEntity();
        EntityManager em = emf.createEntityManager();
        em.merge(fasesprocesso);
        em.close();
        
    }
    
    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fasesprocesso fasesprocesso;
            try {
                fasesprocesso = em.getReference(Fasesprocesso.class, id);
                fasesprocesso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fasesprocesso with id " + id + " no longer exists.", enfe);
            }
            Processo idprocesso = fasesprocesso.getIdprocesso();
            if (idprocesso != null) {
                idprocesso.getFasesprocessoCollection().remove(fasesprocesso);
                idprocesso = em.merge(idprocesso);
            }
            Collection<Atividadesfaseprocesso> atividadesfaseprocessoCollection = fasesprocesso.getAtividadesfaseprocessoCollection();
            for (Atividadesfaseprocesso atividadesfaseprocessoCollectionAtividadesfaseprocesso : atividadesfaseprocessoCollection) {
                atividadesfaseprocessoCollectionAtividadesfaseprocesso.setIdfase(null);
                atividadesfaseprocessoCollectionAtividadesfaseprocesso = em.merge(atividadesfaseprocessoCollectionAtividadesfaseprocesso);
            }
            em.remove(fasesprocesso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fasesprocesso> findFasesprocessoEntities() {
        return findFasesprocessoEntities(true, -1, -1);
    }

    public List<Fasesprocesso> findFasesprocessoEntities(int maxResults, int firstResult) {
        return findFasesprocessoEntities(false, maxResults, firstResult);
    }

    private List<Fasesprocesso> findFasesprocessoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fasesprocesso.class));
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

    public Fasesprocesso findFasesprocesso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fasesprocesso.class, id);
        } finally {
            em.close();
        }
    }

    public int getFasesprocessoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fasesprocesso> rt = cq.from(Fasesprocesso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
