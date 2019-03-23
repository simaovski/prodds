/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import br.util.UtilDao;
import data.crud.Atividade;
import data.crud.Atividademembroequipe;
import data.crud.Equipe;
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
public class AtividademembroequipeJpaController implements Serializable {

    public AtividademembroequipeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Atividademembroequipe atividademembroequipe) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe idequipe = atividademembroequipe.getIdequipe();
            if (idequipe != null) {
                idequipe = em.getReference(idequipe.getClass(), idequipe.getId());
                atividademembroequipe.setIdequipe(idequipe);
            }
            em.persist(atividademembroequipe);
            if (idequipe != null) {
                idequipe.getAtividademembroequipeCollection().add(atividademembroequipe);
                idequipe = em.merge(idequipe);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Atividademembroequipe atividademembroequipe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividademembroequipe persistentAtividademembroequipe = em.find(Atividademembroequipe.class, atividademembroequipe.getId());
            Equipe idequipeOld = persistentAtividademembroequipe.getIdequipe();
            Equipe idequipeNew = atividademembroequipe.getIdequipe();
            if (idequipeNew != null) {
                idequipeNew = em.getReference(idequipeNew.getClass(), idequipeNew.getId());
                atividademembroequipe.setIdequipe(idequipeNew);
            }
            atividademembroequipe = em.merge(atividademembroequipe);
            if (idequipeOld != null && !idequipeOld.equals(idequipeNew)) {
                idequipeOld.getAtividademembroequipeCollection().remove(atividademembroequipe);
                idequipeOld = em.merge(idequipeOld);
            }
            if (idequipeNew != null && !idequipeNew.equals(idequipeOld)) {
                idequipeNew.getAtividademembroequipeCollection().add(atividademembroequipe);
                idequipeNew = em.merge(idequipeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = atividademembroequipe.getId();
                if (findAtividademembroequipe(id) == null) {
                    throw new NonexistentEntityException("The atividademembroequipe with id " + id + " no longer exists.");
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
            Atividademembroequipe atividademembroequipe;
            try {
                atividademembroequipe = em.getReference(Atividademembroequipe.class, id);
                atividademembroequipe.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atividademembroequipe with id " + id + " no longer exists.", enfe);
            }
            Equipe idequipe = atividademembroequipe.getIdequipe();
            if (idequipe != null) {
                idequipe.getAtividademembroequipeCollection().remove(atividademembroequipe);
                idequipe = em.merge(idequipe);
            }
            em.remove(atividademembroequipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Atividademembroequipe> findAtividademembroequipeEntities() {
        return findAtividademembroequipeEntities(true, -1, -1);
    }

    public List<Atividademembroequipe> findAtividademembroequipeEntities(int maxResults, int firstResult) {
        return findAtividademembroequipeEntities(false, maxResults, firstResult);
    }

    private List<Atividademembroequipe> findAtividademembroequipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Atividademembroequipe.class));
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

    public Atividademembroequipe findAtividademembroequipe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Atividademembroequipe.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtividademembroequipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Atividademembroequipe> rt = cq.from(Atividademembroequipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public boolean retornaMembro(int id){
        List<Atividademembroequipe> lista = findAtividademembroequipeEntities();
        
        for(Atividademembroequipe atv : lista){
            int a = atv.getIdmembro().getId();
            System.out.println("id: " + atv.getIdatividade());
            if(a == id)
                return true;
        }
        return false;

    }
    
    public boolean verificaRemocao(Atividade atv, Usuario user, Projeto proj){
        List<Atividademembroequipe> lista = findAtividademembroequipeEntities();
        for(Atividademembroequipe at : lista){
            int a = at.getIdatividade();
            int b = atv.getId();
            
            int c = at.getIdequipe().getIdgerente().getId();
            int d = user.getId();
            
            int e = at.getIdequipe().getIdprojeto().getId();
            int f = proj.getId();
            
            if(a == b && c == d && e == f)
                return false;
        }
        return true;
    }
    
}
