/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Projeto;
import data.crud.Statusprojeto;
import data.crud.Usuario;
import data.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class StatusprojetoJpaController implements Serializable {

    public StatusprojetoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Statusprojeto statusprojeto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idadm = statusprojeto.getIdadm();
            if (idadm != null) {
                idadm = em.getReference(idadm.getClass(), idadm.getId());
                statusprojeto.setIdadm(idadm);
            }
            em.persist(statusprojeto);
            if (idadm != null) {
                idadm.getStatusprojetoCollection().add(statusprojeto);
                idadm = em.merge(idadm);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Statusprojeto statusprojeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Statusprojeto persistentStatusprojeto = em.find(Statusprojeto.class, statusprojeto.getId());
            Usuario idadmOld = persistentStatusprojeto.getIdadm();
            Usuario idadmNew = statusprojeto.getIdadm();
            if (idadmNew != null) {
                idadmNew = em.getReference(idadmNew.getClass(), idadmNew.getId());
                statusprojeto.setIdadm(idadmNew);
            }
            statusprojeto = em.merge(statusprojeto);
            if (idadmOld != null && !idadmOld.equals(idadmNew)) {
                idadmOld.getStatusprojetoCollection().remove(statusprojeto);
                idadmOld = em.merge(idadmOld);
            }
            if (idadmNew != null && !idadmNew.equals(idadmOld)) {
                idadmNew.getStatusprojetoCollection().add(statusprojeto);
                idadmNew = em.merge(idadmNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = statusprojeto.getId();
                if (findStatusprojeto(id) == null) {
                    throw new NonexistentEntityException("The statusprojeto with id " + id + " no longer exists.");
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
            Statusprojeto statusprojeto;
            try {
                statusprojeto = em.getReference(Statusprojeto.class, id);
                statusprojeto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The statusprojeto with id " + id + " no longer exists.", enfe);
            }
            Usuario idadm = statusprojeto.getIdadm();
            if (idadm != null) {
                idadm.getStatusprojetoCollection().remove(statusprojeto);
                idadm = em.merge(idadm);
            }
            em.remove(statusprojeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Statusprojeto> findStatusprojetoEntities() {
        return findStatusprojetoEntities(true, -1, -1);
    }

    public List<Statusprojeto> findStatusprojetoEntities(int maxResults, int firstResult) {
        return findStatusprojetoEntities(false, maxResults, firstResult);
    }

    private List<Statusprojeto> findStatusprojetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Statusprojeto.class));
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

    public Statusprojeto findStatusprojeto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Statusprojeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getStatusprojetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Statusprojeto> rt = cq.from(Statusprojeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public int retornaStatus(int p){
        List<Statusprojeto> lista = findStatusprojetoEntities();
        for(Statusprojeto l : lista){
            int a = l.getIdprojeto();
            if(a == p)
                return l.getEstado();
        }
        return 0;
    }
    
    public Statusprojeto retornaStatusprojeto(Projeto p){
        List<Statusprojeto> lista = findStatusprojetoEntities();
        for(Statusprojeto l : lista){
            int a = l.getIdprojeto();
            int b = p.getId();
            if(a == b)
                return l;
        }
        return null;
    }
    
}
