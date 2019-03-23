/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import br.bean.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import data.crud.Processo;
import data.crud.Processo;
import data.crud.Projeto;
import data.crud.Projeto;
import data.crud.Usuario;
import data.crud.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author padrao
 */
public class ProjetoJpaController implements Serializable {

    public ProjetoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Projeto projeto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Processo idprocesso = projeto.getIdprocesso();
            if (idprocesso != null) {
                idprocesso = em.getReference(idprocesso.getClass(), idprocesso.getId());
                projeto.setIdprocesso(idprocesso);
            }
            Usuario idadm = projeto.getIdadm();
            if (idadm != null) {
                idadm = em.getReference(idadm.getClass(), idadm.getId());
                projeto.setIdadm(idadm);
            }
            em.persist(projeto);
            if (idprocesso != null) {
                idprocesso.getProjetoCollection().add(projeto);
                idprocesso = em.merge(idprocesso);
            }
            if (idadm != null) {
                idadm.getProjetoCollection().add(projeto);
                idadm = em.merge(idadm);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Projeto projeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Projeto persistentProjeto = em.find(Projeto.class, projeto.getId());
            Processo idprocessoOld = persistentProjeto.getIdprocesso();
            Processo idprocessoNew = projeto.getIdprocesso();
            Usuario idadmOld = persistentProjeto.getIdadm();
            Usuario idadmNew = projeto.getIdadm();
            if (idprocessoNew != null) {
                idprocessoNew = em.getReference(idprocessoNew.getClass(), idprocessoNew.getId());
                projeto.setIdprocesso(idprocessoNew);
            }
            if (idadmNew != null) {
                idadmNew = em.getReference(idadmNew.getClass(), idadmNew.getId());
                projeto.setIdadm(idadmNew);
            }
            projeto = em.merge(projeto);
            if (idprocessoOld != null && !idprocessoOld.equals(idprocessoNew)) {
                idprocessoOld.getProjetoCollection().remove(projeto);
                idprocessoOld = em.merge(idprocessoOld);
            }
            if (idprocessoNew != null && !idprocessoNew.equals(idprocessoOld)) {
                idprocessoNew.getProjetoCollection().add(projeto);
                idprocessoNew = em.merge(idprocessoNew);
            }
            if (idadmOld != null && !idadmOld.equals(idadmNew)) {
                idadmOld.getProjetoCollection().remove(projeto);
                idadmOld = em.merge(idadmOld);
            }
            if (idadmNew != null && !idadmNew.equals(idadmOld)) {
                idadmNew.getProjetoCollection().add(projeto);
                idadmNew = em.merge(idadmNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = projeto.getId();
                if (findProjeto(id) == null) {
                    throw new NonexistentEntityException("The projeto with id " + id + " no longer exists.");
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
            Projeto projeto;
            try {
                projeto = em.getReference(Projeto.class, id);
                projeto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The projeto with id " + id + " no longer exists.", enfe);
            }
            Processo idprocesso = projeto.getIdprocesso();
            if (idprocesso != null) {
                idprocesso.getProjetoCollection().remove(projeto);
                idprocesso = em.merge(idprocesso);
            }
            Usuario idadm = projeto.getIdadm();
            if (idadm != null) {
                idadm.getProjetoCollection().remove(projeto);
                idadm = em.merge(idadm);
            }
            em.remove(projeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Projeto> findProjetoEntities() {
        return findProjetoEntities(true, -1, -1);
    }

    public List<Projeto> findProjetoEntities(int maxResults, int firstResult) {
        return findProjetoEntities(false, maxResults, firstResult);
    }

    private List<Projeto> findProjetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projeto.class));
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

    public Projeto findProjeto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Projeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Projeto> rt = cq.from(Projeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public boolean verificarNomeProjeto(String nomeProjeto, Usuario u) {
        List<Projeto> listaProj = findProjetoEntities();
        for(int i = 0; i < listaProj.size(); i++){
            String a = listaProj.get(i).getNomeprojeto();
            int b = listaProj.get(i).getIdadm().getId();
            
            if(a.equals(nomeProjeto) && u.getId() == b){
                return false;
            }
        }
        return true;
    }
    
    public boolean verificaGerente(Integer id) {
        EntityManager em = getEntityManager();
        Query q = em.createQuery("SELECT g FROM Gerente g WHERE g.idusuariogerente.id='"+id+"'");
        
        try {
            if(q.getResultList().size() > 0){
                return true;
            }
        } catch (Exception e) {
            System.out.println("Erro <EquipaJpaControler funcao verificaGerente>: " + e.toString());
        }
        return false;
    }
}

