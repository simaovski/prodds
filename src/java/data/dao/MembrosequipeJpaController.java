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
import data.crud.Membrosequipe;
import data.crud.Usuario;
import data.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author padrao
 */
public class MembrosequipeJpaController implements Serializable {

    public MembrosequipeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Membrosequipe membrosequipe) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe idequipe = membrosequipe.getIdequipe();
            if (idequipe != null) {
                idequipe = em.getReference(idequipe.getClass(), idequipe.getId());
                membrosequipe.setIdequipe(idequipe);
            }
            Usuario idgerente = membrosequipe.getIdgerente();
            if (idgerente != null) {
                idgerente = em.getReference(idgerente.getClass(), idgerente.getId());
                membrosequipe.setIdgerente(idgerente);
            }
            Usuario idmembro = membrosequipe.getIdmembro();
            if (idmembro != null) {
                idmembro = em.getReference(idmembro.getClass(), idmembro.getId());
                membrosequipe.setIdmembro(idmembro);
            }
            em.persist(membrosequipe);
            if (idequipe != null) {
                idequipe.getMembrosequipeCollection().add(membrosequipe);
                idequipe = em.merge(idequipe);
            }
            if (idgerente != null) {
                idgerente.getMembrosequipeCollection().add(membrosequipe);
                idgerente = em.merge(idgerente);
            }
            if (idmembro != null) {
                idmembro.getMembrosequipeCollection().add(membrosequipe);
                idmembro = em.merge(idmembro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Membrosequipe membrosequipe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Membrosequipe persistentMembrosequipe = em.find(Membrosequipe.class, membrosequipe.getId());
            Equipe idequipeOld = persistentMembrosequipe.getIdequipe();
            Equipe idequipeNew = membrosequipe.getIdequipe();
            Usuario idgerenteOld = persistentMembrosequipe.getIdgerente();
            Usuario idgerenteNew = membrosequipe.getIdgerente();
            Usuario idmembroOld = persistentMembrosequipe.getIdmembro();
            Usuario idmembroNew = membrosequipe.getIdmembro();
            if (idequipeNew != null) {
                idequipeNew = em.getReference(idequipeNew.getClass(), idequipeNew.getId());
                membrosequipe.setIdequipe(idequipeNew);
            }
            if (idgerenteNew != null) {
                idgerenteNew = em.getReference(idgerenteNew.getClass(), idgerenteNew.getId());
                membrosequipe.setIdgerente(idgerenteNew);
            }
            if (idmembroNew != null) {
                idmembroNew = em.getReference(idmembroNew.getClass(), idmembroNew.getId());
                membrosequipe.setIdmembro(idmembroNew);
            }
            membrosequipe = em.merge(membrosequipe);
            if (idequipeOld != null && !idequipeOld.equals(idequipeNew)) {
                idequipeOld.getMembrosequipeCollection().remove(membrosequipe);
                idequipeOld = em.merge(idequipeOld);
            }
            if (idequipeNew != null && !idequipeNew.equals(idequipeOld)) {
                idequipeNew.getMembrosequipeCollection().add(membrosequipe);
                idequipeNew = em.merge(idequipeNew);
            }
            if (idgerenteOld != null && !idgerenteOld.equals(idgerenteNew)) {
                idgerenteOld.getMembrosequipeCollection().remove(membrosequipe);
                idgerenteOld = em.merge(idgerenteOld);
            }
            if (idgerenteNew != null && !idgerenteNew.equals(idgerenteOld)) {
                idgerenteNew.getMembrosequipeCollection().add(membrosequipe);
                idgerenteNew = em.merge(idgerenteNew);
            }
            if (idmembroOld != null && !idmembroOld.equals(idmembroNew)) {
                idmembroOld.getMembrosequipeCollection().remove(membrosequipe);
                idmembroOld = em.merge(idmembroOld);
            }
            if (idmembroNew != null && !idmembroNew.equals(idmembroOld)) {
                idmembroNew.getMembrosequipeCollection().add(membrosequipe);
                idmembroNew = em.merge(idmembroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = membrosequipe.getId();
                if (findMembrosequipe(id) == null) {
                    throw new NonexistentEntityException("The membrosequipe with id " + id + " no longer exists.");
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
            Membrosequipe membrosequipe;
            try {
                membrosequipe = em.getReference(Membrosequipe.class, id);
                membrosequipe.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The membrosequipe with id " + id + " no longer exists.", enfe);
            }
            Equipe idequipe = membrosequipe.getIdequipe();
            if (idequipe != null) {
                idequipe.getMembrosequipeCollection().remove(membrosequipe);
                idequipe = em.merge(idequipe);
            }
            Usuario idgerente = membrosequipe.getIdgerente();
            if (idgerente != null) {
                idgerente.getMembrosequipeCollection().remove(membrosequipe);
                idgerente = em.merge(idgerente);
            }
            Usuario idmembro = membrosequipe.getIdmembro();
            if (idmembro != null) {
                idmembro.getMembrosequipeCollection().remove(membrosequipe);
                idmembro = em.merge(idmembro);
            }
            em.remove(membrosequipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Membrosequipe> findMembrosequipeEntities() {
        return findMembrosequipeEntities(true, -1, -1);
    }

    public List<Membrosequipe> findMembrosequipeEntities(int maxResults, int firstResult) {
        return findMembrosequipeEntities(false, maxResults, firstResult);
    }

    private List<Membrosequipe> findMembrosequipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Membrosequipe.class));
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

    public Membrosequipe findMembrosequipe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Membrosequipe.class, id);
        } finally {
            em.close();
        }
    }

    public int getMembrosequipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Membrosequipe> rt = cq.from(Membrosequipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public boolean retornaMembro(int idMem, int idEquipe){
        List<Membrosequipe> lista = findMembrosequipeEntities();
        
        for(Membrosequipe mem : lista){
            int a = mem.getIdmembro().getId();
            int b = mem.getIdequipe().getId();
            if(a == idMem && b == idEquipe)
                return true;
        }
        return false;
    }
    
}
