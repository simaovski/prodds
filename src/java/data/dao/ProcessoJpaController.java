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
import data.crud.Usuario;
import data.crud.Fasesprocesso;
import data.crud.Processo;
import data.dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author padrao
 */
public class ProcessoJpaController implements Serializable {

    public ProcessoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Processo processo) {
        if (processo.getFasesprocessoCollection() == null) {
            processo.setFasesprocessoCollection(new ArrayList<Fasesprocesso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idusuario = processo.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getId());
                processo.setIdusuario(idusuario);
            }
            Collection<Fasesprocesso> attachedFasesprocessoCollection = new ArrayList<Fasesprocesso>();
            for (Fasesprocesso fasesprocessoCollectionFasesprocessoToAttach : processo.getFasesprocessoCollection()) {
                fasesprocessoCollectionFasesprocessoToAttach = em.getReference(fasesprocessoCollectionFasesprocessoToAttach.getClass(), fasesprocessoCollectionFasesprocessoToAttach.getId());
                attachedFasesprocessoCollection.add(fasesprocessoCollectionFasesprocessoToAttach);
            }
            processo.setFasesprocessoCollection(attachedFasesprocessoCollection);
            em.persist(processo);
            if (idusuario != null) {
                idusuario.getProcessoCollection().add(processo);
                idusuario = em.merge(idusuario);
            }
            for (Fasesprocesso fasesprocessoCollectionFasesprocesso : processo.getFasesprocessoCollection()) {
                Processo oldIdprocessoOfFasesprocessoCollectionFasesprocesso = fasesprocessoCollectionFasesprocesso.getIdprocesso();
                fasesprocessoCollectionFasesprocesso.setIdprocesso(processo);
                fasesprocessoCollectionFasesprocesso = em.merge(fasesprocessoCollectionFasesprocesso);
                if (oldIdprocessoOfFasesprocessoCollectionFasesprocesso != null) {
                    oldIdprocessoOfFasesprocessoCollectionFasesprocesso.getFasesprocessoCollection().remove(fasesprocessoCollectionFasesprocesso);
                    oldIdprocessoOfFasesprocessoCollectionFasesprocesso = em.merge(oldIdprocessoOfFasesprocessoCollectionFasesprocesso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Processo processo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Processo persistentProcesso = em.find(Processo.class, processo.getId());
            Usuario idusuarioOld = persistentProcesso.getIdusuario();
            Usuario idusuarioNew = processo.getIdusuario();
            Collection<Fasesprocesso> fasesprocessoCollectionOld = persistentProcesso.getFasesprocessoCollection();
            Collection<Fasesprocesso> fasesprocessoCollectionNew = processo.getFasesprocessoCollection();
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getId());
                processo.setIdusuario(idusuarioNew);
            }
            Collection<Fasesprocesso> attachedFasesprocessoCollectionNew = new ArrayList<Fasesprocesso>();
            for (Fasesprocesso fasesprocessoCollectionNewFasesprocessoToAttach : fasesprocessoCollectionNew) {
                fasesprocessoCollectionNewFasesprocessoToAttach = em.getReference(fasesprocessoCollectionNewFasesprocessoToAttach.getClass(), fasesprocessoCollectionNewFasesprocessoToAttach.getId());
                attachedFasesprocessoCollectionNew.add(fasesprocessoCollectionNewFasesprocessoToAttach);
            }
            fasesprocessoCollectionNew = attachedFasesprocessoCollectionNew;
            processo.setFasesprocessoCollection(fasesprocessoCollectionNew);
            processo = em.merge(processo);
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getProcessoCollection().remove(processo);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getProcessoCollection().add(processo);
                idusuarioNew = em.merge(idusuarioNew);
            }
            for (Fasesprocesso fasesprocessoCollectionOldFasesprocesso : fasesprocessoCollectionOld) {
                if (!fasesprocessoCollectionNew.contains(fasesprocessoCollectionOldFasesprocesso)) {
                    fasesprocessoCollectionOldFasesprocesso.setIdprocesso(null);
                    fasesprocessoCollectionOldFasesprocesso = em.merge(fasesprocessoCollectionOldFasesprocesso);
                }
            }
            for (Fasesprocesso fasesprocessoCollectionNewFasesprocesso : fasesprocessoCollectionNew) {
                if (!fasesprocessoCollectionOld.contains(fasesprocessoCollectionNewFasesprocesso)) {
                    Processo oldIdprocessoOfFasesprocessoCollectionNewFasesprocesso = fasesprocessoCollectionNewFasesprocesso.getIdprocesso();
                    fasesprocessoCollectionNewFasesprocesso.setIdprocesso(processo);
                    fasesprocessoCollectionNewFasesprocesso = em.merge(fasesprocessoCollectionNewFasesprocesso);
                    if (oldIdprocessoOfFasesprocessoCollectionNewFasesprocesso != null && !oldIdprocessoOfFasesprocessoCollectionNewFasesprocesso.equals(processo)) {
                        oldIdprocessoOfFasesprocessoCollectionNewFasesprocesso.getFasesprocessoCollection().remove(fasesprocessoCollectionNewFasesprocesso);
                        oldIdprocessoOfFasesprocessoCollectionNewFasesprocesso = em.merge(oldIdprocessoOfFasesprocessoCollectionNewFasesprocesso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = processo.getId();
                if (findProcesso(id) == null) {
                    throw new NonexistentEntityException("The processo with id " + id + " no longer exists.");
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
            Processo processo;
            try {
                processo = em.getReference(Processo.class, id);
                processo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The processo with id " + id + " no longer exists.", enfe);
            }
            Usuario idusuario = processo.getIdusuario();
            if (idusuario != null) {
                idusuario.getProcessoCollection().remove(processo);
                idusuario = em.merge(idusuario);
            }
            Collection<Fasesprocesso> fasesprocessoCollection = processo.getFasesprocessoCollection();
            for (Fasesprocesso fasesprocessoCollectionFasesprocesso : fasesprocessoCollection) {
                fasesprocessoCollectionFasesprocesso.setIdprocesso(null);
                fasesprocessoCollectionFasesprocesso = em.merge(fasesprocessoCollectionFasesprocesso);
            }
            em.remove(processo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Processo> findProcessoEntities() {
        return findProcessoEntities(true, -1, -1);
    }

    public List<Processo> findProcessoEntities(int maxResults, int firstResult) {
        return findProcessoEntities(false, maxResults, firstResult);
    }

    private List<Processo> findProcessoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Processo.class));
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

    public Processo findProcesso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Processo.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcessoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Processo> rt = cq.from(Processo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public boolean verificaNomeProcesso(String nomeProcesso, Usuario user) {
        Collection<Processo> lista = user.getProcessoCollection();
        for(Processo p : lista){
            System.out.println("Entrei" + p.getNomeprocesso().toUpperCase().equals(nomeProcesso.toUpperCase()));
            if(p.getNomeprocesso().toUpperCase().equals(nomeProcesso.toUpperCase()))
                return true;
        }
        return false;
    }
    
}
