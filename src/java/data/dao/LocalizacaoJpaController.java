/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Equipe;
import data.crud.Localizacao;
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
public class LocalizacaoJpaController implements Serializable {

    public LocalizacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Localizacao localizacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idgerente = localizacao.getIdgerente();
            if (idgerente != null) {
                idgerente = em.getReference(idgerente.getClass(), idgerente.getId());
                localizacao.setIdgerente(idgerente);
            }
            em.persist(localizacao);
            if (idgerente != null) {
                idgerente.getLocalizacaoCollection().add(localizacao);
                idgerente = em.merge(idgerente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Localizacao localizacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Localizacao persistentLocalizacao = em.find(Localizacao.class, localizacao.getId());
            Usuario idgerenteOld = persistentLocalizacao.getIdgerente();
            Usuario idgerenteNew = localizacao.getIdgerente();
            if (idgerenteNew != null) {
                idgerenteNew = em.getReference(idgerenteNew.getClass(), idgerenteNew.getId());
                localizacao.setIdgerente(idgerenteNew);
            }
            localizacao = em.merge(localizacao);
            if (idgerenteOld != null && !idgerenteOld.equals(idgerenteNew)) {
                idgerenteOld.getLocalizacaoCollection().remove(localizacao);
                idgerenteOld = em.merge(idgerenteOld);
            }
            if (idgerenteNew != null && !idgerenteNew.equals(idgerenteOld)) {
                idgerenteNew.getLocalizacaoCollection().add(localizacao);
                idgerenteNew = em.merge(idgerenteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = localizacao.getId();
                if (findLocalizacao(id) == null) {
                    throw new NonexistentEntityException("The localizacao with id " + id + " no longer exists.");
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
            Localizacao localizacao;
            try {
                localizacao = em.getReference(Localizacao.class, id);
                localizacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The localizacao with id " + id + " no longer exists.", enfe);
            }
            Usuario idgerente = localizacao.getIdgerente();
            if (idgerente != null) {
                idgerente.getLocalizacaoCollection().remove(localizacao);
                idgerente = em.merge(idgerente);
            }
            em.remove(localizacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Localizacao> findLocalizacaoEntities() {
        return findLocalizacaoEntities(true, -1, -1);
    }

    public List<Localizacao> findLocalizacaoEntities(int maxResults, int firstResult) {
        return findLocalizacaoEntities(false, maxResults, firstResult);
    }

    private List<Localizacao> findLocalizacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Localizacao.class));
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

    public Localizacao findLocalizacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Localizacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocalizacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Localizacao> rt = cq.from(Localizacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Localizacao retornaLocEqp(Equipe e){
       List<Localizacao> lista = findLocalizacaoEntities();
       for(Localizacao l : lista){
           int a = e.getId();
           int b = l.getIdequipe();
           if(a == b)
               return l;
       }
       return new Localizacao();
    }
    
}
