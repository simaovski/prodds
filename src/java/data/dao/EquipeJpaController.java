/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Equipe;
import data.crud.Projeto;
import data.crud.Usuario;
import data.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
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
public class EquipeJpaController implements Serializable {

    public EquipeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipe equipe) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idgerente = equipe.getIdgerente();
            if (idgerente != null) {
                idgerente = em.getReference(idgerente.getClass(), idgerente.getId());
                equipe.setIdgerente(idgerente);
            }
            em.persist(equipe);
            if (idgerente != null) {
                idgerente.getEquipeCollection().add(equipe);
                idgerente = em.merge(idgerente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipe equipe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe persistentEquipe = em.find(Equipe.class, equipe.getId());
            Usuario idgerenteOld = persistentEquipe.getIdgerente();
            Usuario idgerenteNew = equipe.getIdgerente();
            if (idgerenteNew != null) {
                idgerenteNew = em.getReference(idgerenteNew.getClass(), idgerenteNew.getId());
                equipe.setIdgerente(idgerenteNew);
            }
            equipe = em.merge(equipe);
            if (idgerenteOld != null && !idgerenteOld.equals(idgerenteNew)) {
                idgerenteOld.getEquipeCollection().remove(equipe);
                idgerenteOld = em.merge(idgerenteOld);
            }
            if (idgerenteNew != null && !idgerenteNew.equals(idgerenteOld)) {
                idgerenteNew.getEquipeCollection().add(equipe);
                idgerenteNew = em.merge(idgerenteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipe.getId();
                if (findEquipe(id) == null) {
                    throw new NonexistentEntityException("The equipe with id " + id + " no longer exists.");
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
            Equipe equipe;
            try {
                equipe = em.getReference(Equipe.class, id);
                equipe.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipe with id " + id + " no longer exists.", enfe);
            }
            Usuario idgerente = equipe.getIdgerente();
            if (idgerente != null) {
                idgerente.getEquipeCollection().remove(equipe);
                idgerente = em.merge(idgerente);
            }
            em.remove(equipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipe> findEquipeEntities() {
        return findEquipeEntities(true, -1, -1);
    }

    public List<Equipe> findEquipeEntities(int maxResults, int firstResult) {
        return findEquipeEntities(false, maxResults, firstResult);
    }

    private List<Equipe> findEquipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipe.class));
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

    public Equipe findEquipe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipe.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipe> rt = cq.from(Equipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public boolean verificaNomeEquipe(Usuario user, String nomeEquipe) {
        int idProjeto = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoGerente");
        List<Equipe> listaEqp = findEquipeEntities();
        
        for(Equipe e : listaEqp){
            String b = e.getNomeequipe();
            int c = e.getIdprojeto().getId();
            if(nomeEquipe.equals(b) == true && c == idProjeto){
                return false;
            }
        }
        return true;
    }
    
     public boolean verificaNomeEquipe(Equipe eqp, Projeto p) {
        List<Equipe> listaEqp = findEquipeEntities();
        
        for(Equipe e : listaEqp){
            String nome = e.getNomeequipe();
            String novoNome = eqp.getNomeequipe();
            int a = e.getId();
            int b = eqp.getId();
            
            int c = p.getId();
            int d = eqp.getIdprojeto().getId();
            if(nome.equals(novoNome)){
                System.out.println("equipe: " + eqp.getNomeequipe());
                if(c != d)
                    return true;
                else{
                    if(a == b)
                        return true;
                    else
                        return false;
                }
            }
        }
        return true;
    }
    
}
