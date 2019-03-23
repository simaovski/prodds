/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Atividadesantecessorasfaseprocesso;
import data.crud.Atividadesequipe;
import data.crud.Atividadesfaseprocesso;
import data.crud.Fasesprocesso;
import data.crud.Processo;
import data.crud.Projeto;
import data.crud.Usuario;
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
public class AtividadesfaseprocessoJpaController implements Serializable {

    public AtividadesfaseprocessoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Atividadesfaseprocesso atividadesfaseprocesso) {
        if (atividadesfaseprocesso.getAtividadesantecessorasfaseprocessoCollection() == null) {
            atividadesfaseprocesso.setAtividadesantecessorasfaseprocessoCollection(new ArrayList<Atividadesantecessorasfaseprocesso>());
        }
        if (atividadesfaseprocesso.getAtividadesantecessorasfaseprocessoCollection1() == null) {
            atividadesfaseprocesso.setAtividadesantecessorasfaseprocessoCollection1(new ArrayList<Atividadesantecessorasfaseprocesso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fasesprocesso idfase = atividadesfaseprocesso.getIdfase();
            if (idfase != null) {
                idfase = em.getReference(idfase.getClass(), idfase.getId());
                atividadesfaseprocesso.setIdfase(idfase);
            }
            Collection<Atividadesantecessorasfaseprocesso> attachedAtividadesantecessorasfaseprocessoCollection = new ArrayList<Atividadesantecessorasfaseprocesso>();
            for (Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocessoToAttach : atividadesfaseprocesso.getAtividadesantecessorasfaseprocessoCollection()) {
                atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocessoToAttach = em.getReference(atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocessoToAttach.getClass(), atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocessoToAttach.getId());
                attachedAtividadesantecessorasfaseprocessoCollection.add(atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocessoToAttach);
            }
            atividadesfaseprocesso.setAtividadesantecessorasfaseprocessoCollection(attachedAtividadesantecessorasfaseprocessoCollection);
            Collection<Atividadesantecessorasfaseprocesso> attachedAtividadesantecessorasfaseprocessoCollection1 = new ArrayList<Atividadesantecessorasfaseprocesso>();
            for (Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocessoCollection1AtividadesantecessorasfaseprocessoToAttach : atividadesfaseprocesso.getAtividadesantecessorasfaseprocessoCollection1()) {
                atividadesantecessorasfaseprocessoCollection1AtividadesantecessorasfaseprocessoToAttach = em.getReference(atividadesantecessorasfaseprocessoCollection1AtividadesantecessorasfaseprocessoToAttach.getClass(), atividadesantecessorasfaseprocessoCollection1AtividadesantecessorasfaseprocessoToAttach.getId());
                attachedAtividadesantecessorasfaseprocessoCollection1.add(atividadesantecessorasfaseprocessoCollection1AtividadesantecessorasfaseprocessoToAttach);
            }
            atividadesfaseprocesso.setAtividadesantecessorasfaseprocessoCollection1(attachedAtividadesantecessorasfaseprocessoCollection1);
            em.persist(atividadesfaseprocesso);
            if (idfase != null) {
                idfase.getAtividadesfaseprocessoCollection().add(atividadesfaseprocesso);
                idfase = em.merge(idfase);
            }
            for (Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso : atividadesfaseprocesso.getAtividadesantecessorasfaseprocessoCollection()) {
                Atividadesfaseprocesso oldIdatividadeOfAtividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso = atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso.getIdatividade();
                atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso.setIdatividade(atividadesfaseprocesso);
                atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso = em.merge(atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso);
                if (oldIdatividadeOfAtividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso != null) {
                    oldIdatividadeOfAtividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso.getAtividadesantecessorasfaseprocessoCollection().remove(atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso);
                    oldIdatividadeOfAtividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso = em.merge(oldIdatividadeOfAtividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso);
                }
            }
            for (Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso : atividadesfaseprocesso.getAtividadesantecessorasfaseprocessoCollection1()) {
                Atividadesfaseprocesso oldIdatividadesucessoraOfAtividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso = atividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso.getIdatividadesucessora();
                atividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso.setIdatividadesucessora(atividadesfaseprocesso);
                atividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso = em.merge(atividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso);
                if (oldIdatividadesucessoraOfAtividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso != null) {
                    oldIdatividadesucessoraOfAtividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso.getAtividadesantecessorasfaseprocessoCollection1().remove(atividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso);
                    oldIdatividadesucessoraOfAtividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso = em.merge(oldIdatividadesucessoraOfAtividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Atividadesfaseprocesso atividadesfaseprocesso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividadesfaseprocesso persistentAtividadesfaseprocesso = em.find(Atividadesfaseprocesso.class, atividadesfaseprocesso.getId());
            Fasesprocesso idfaseOld = persistentAtividadesfaseprocesso.getIdfase();
            Fasesprocesso idfaseNew = atividadesfaseprocesso.getIdfase();
            Collection<Atividadesantecessorasfaseprocesso> atividadesantecessorasfaseprocessoCollectionOld = persistentAtividadesfaseprocesso.getAtividadesantecessorasfaseprocessoCollection();
            Collection<Atividadesantecessorasfaseprocesso> atividadesantecessorasfaseprocessoCollectionNew = atividadesfaseprocesso.getAtividadesantecessorasfaseprocessoCollection();
            Collection<Atividadesantecessorasfaseprocesso> atividadesantecessorasfaseprocessoCollection1Old = persistentAtividadesfaseprocesso.getAtividadesantecessorasfaseprocessoCollection1();
            Collection<Atividadesantecessorasfaseprocesso> atividadesantecessorasfaseprocessoCollection1New = atividadesfaseprocesso.getAtividadesantecessorasfaseprocessoCollection1();
            if (idfaseNew != null) {
                idfaseNew = em.getReference(idfaseNew.getClass(), idfaseNew.getId());
                atividadesfaseprocesso.setIdfase(idfaseNew);
            }
            Collection<Atividadesantecessorasfaseprocesso> attachedAtividadesantecessorasfaseprocessoCollectionNew = new ArrayList<Atividadesantecessorasfaseprocesso>();
            for (Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocessoToAttach : atividadesantecessorasfaseprocessoCollectionNew) {
                atividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocessoToAttach = em.getReference(atividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocessoToAttach.getClass(), atividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocessoToAttach.getId());
                attachedAtividadesantecessorasfaseprocessoCollectionNew.add(atividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocessoToAttach);
            }
            atividadesantecessorasfaseprocessoCollectionNew = attachedAtividadesantecessorasfaseprocessoCollectionNew;
            atividadesfaseprocesso.setAtividadesantecessorasfaseprocessoCollection(atividadesantecessorasfaseprocessoCollectionNew);
            Collection<Atividadesantecessorasfaseprocesso> attachedAtividadesantecessorasfaseprocessoCollection1New = new ArrayList<Atividadesantecessorasfaseprocesso>();
            for (Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocessoToAttach : atividadesantecessorasfaseprocessoCollection1New) {
                atividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocessoToAttach = em.getReference(atividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocessoToAttach.getClass(), atividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocessoToAttach.getId());
                attachedAtividadesantecessorasfaseprocessoCollection1New.add(atividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocessoToAttach);
            }
            atividadesantecessorasfaseprocessoCollection1New = attachedAtividadesantecessorasfaseprocessoCollection1New;
            atividadesfaseprocesso.setAtividadesantecessorasfaseprocessoCollection1(atividadesantecessorasfaseprocessoCollection1New);
            atividadesfaseprocesso = em.merge(atividadesfaseprocesso);
            if (idfaseOld != null && !idfaseOld.equals(idfaseNew)) {
                idfaseOld.getAtividadesfaseprocessoCollection().remove(atividadesfaseprocesso);
                idfaseOld = em.merge(idfaseOld);
            }
            if (idfaseNew != null && !idfaseNew.equals(idfaseOld)) {
                idfaseNew.getAtividadesfaseprocessoCollection().add(atividadesfaseprocesso);
                idfaseNew = em.merge(idfaseNew);
            }
            for (Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocessoCollectionOldAtividadesantecessorasfaseprocesso : atividadesantecessorasfaseprocessoCollectionOld) {
                if (!atividadesantecessorasfaseprocessoCollectionNew.contains(atividadesantecessorasfaseprocessoCollectionOldAtividadesantecessorasfaseprocesso)) {
                    atividadesantecessorasfaseprocessoCollectionOldAtividadesantecessorasfaseprocesso.setIdatividade(null);
                    atividadesantecessorasfaseprocessoCollectionOldAtividadesantecessorasfaseprocesso = em.merge(atividadesantecessorasfaseprocessoCollectionOldAtividadesantecessorasfaseprocesso);
                }
            }
            for (Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso : atividadesantecessorasfaseprocessoCollectionNew) {
                if (!atividadesantecessorasfaseprocessoCollectionOld.contains(atividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso)) {
                    Atividadesfaseprocesso oldIdatividadeOfAtividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso = atividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso.getIdatividade();
                    atividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso.setIdatividade(atividadesfaseprocesso);
                    atividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso = em.merge(atividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso);
                    if (oldIdatividadeOfAtividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso != null && !oldIdatividadeOfAtividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso.equals(atividadesfaseprocesso)) {
                        oldIdatividadeOfAtividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso.getAtividadesantecessorasfaseprocessoCollection().remove(atividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso);
                        oldIdatividadeOfAtividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso = em.merge(oldIdatividadeOfAtividadesantecessorasfaseprocessoCollectionNewAtividadesantecessorasfaseprocesso);
                    }
                }
            }
            for (Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocessoCollection1OldAtividadesantecessorasfaseprocesso : atividadesantecessorasfaseprocessoCollection1Old) {
                if (!atividadesantecessorasfaseprocessoCollection1New.contains(atividadesantecessorasfaseprocessoCollection1OldAtividadesantecessorasfaseprocesso)) {
                    atividadesantecessorasfaseprocessoCollection1OldAtividadesantecessorasfaseprocesso.setIdatividadesucessora(null);
                    atividadesantecessorasfaseprocessoCollection1OldAtividadesantecessorasfaseprocesso = em.merge(atividadesantecessorasfaseprocessoCollection1OldAtividadesantecessorasfaseprocesso);
                }
            }
            for (Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso : atividadesantecessorasfaseprocessoCollection1New) {
                if (!atividadesantecessorasfaseprocessoCollection1Old.contains(atividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso)) {
                    Atividadesfaseprocesso oldIdatividadesucessoraOfAtividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso = atividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso.getIdatividadesucessora();
                    atividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso.setIdatividadesucessora(atividadesfaseprocesso);
                    atividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso = em.merge(atividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso);
                    if (oldIdatividadesucessoraOfAtividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso != null && !oldIdatividadesucessoraOfAtividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso.equals(atividadesfaseprocesso)) {
                        oldIdatividadesucessoraOfAtividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso.getAtividadesantecessorasfaseprocessoCollection1().remove(atividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso);
                        oldIdatividadesucessoraOfAtividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso = em.merge(oldIdatividadesucessoraOfAtividadesantecessorasfaseprocessoCollection1NewAtividadesantecessorasfaseprocesso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = atividadesfaseprocesso.getId();
                if (findAtividadesfaseprocesso(id) == null) {
                    throw new NonexistentEntityException("The atividadesfaseprocesso with id " + id + " no longer exists.");
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
            Atividadesfaseprocesso atividadesfaseprocesso;
            try {
                atividadesfaseprocesso = em.getReference(Atividadesfaseprocesso.class, id);
                atividadesfaseprocesso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atividadesfaseprocesso with id " + id + " no longer exists.", enfe);
            }
            Fasesprocesso idfase = atividadesfaseprocesso.getIdfase();
            if (idfase != null) {
                idfase.getAtividadesfaseprocessoCollection().remove(atividadesfaseprocesso);
                idfase = em.merge(idfase);
            }
            Collection<Atividadesantecessorasfaseprocesso> atividadesantecessorasfaseprocessoCollection = atividadesfaseprocesso.getAtividadesantecessorasfaseprocessoCollection();
            for (Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso : atividadesantecessorasfaseprocessoCollection) {
                atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso.setIdatividade(null);
                atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso = em.merge(atividadesantecessorasfaseprocessoCollectionAtividadesantecessorasfaseprocesso);
            }
            Collection<Atividadesantecessorasfaseprocesso> atividadesantecessorasfaseprocessoCollection1 = atividadesfaseprocesso.getAtividadesantecessorasfaseprocessoCollection1();
            for (Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso : atividadesantecessorasfaseprocessoCollection1) {
                atividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso.setIdatividadesucessora(null);
                atividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso = em.merge(atividadesantecessorasfaseprocessoCollection1Atividadesantecessorasfaseprocesso);
            }
            em.remove(atividadesfaseprocesso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Atividadesfaseprocesso> findAtividadesfaseprocessoEntities() {
        return findAtividadesfaseprocessoEntities(true, -1, -1);
    }

    public List<Atividadesfaseprocesso> findAtividadesfaseprocessoEntities(int maxResults, int firstResult) {
        return findAtividadesfaseprocessoEntities(false, maxResults, firstResult);
    }

    private List<Atividadesfaseprocesso> findAtividadesfaseprocessoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Atividadesfaseprocesso.class));
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

    public Atividadesfaseprocesso findAtividadesfaseprocesso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Atividadesfaseprocesso.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtividadesfaseprocessoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Atividadesfaseprocesso> rt = cq.from(Atividadesfaseprocesso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
   public List<Atividadesfaseprocesso> retornaAtividadePorAdm(Usuario u, Processo p){
        List<Atividadesfaseprocesso> lista = new ArrayList<Atividadesfaseprocesso>();
        List<Atividadesfaseprocesso> aux = findAtividadesfaseprocessoEntities();
        for(Atividadesfaseprocesso atv : aux){
            int a = atv.getIdfase().getIdprocesso().getIdusuario().getId();
            int b = u.getId();
            
            int c = atv.getIdfase().getIdprocesso().getId();
            int d = p.getId();
            if(a == b && c == d)
                lista.add(atv);
        }
        return lista;
    }
}
