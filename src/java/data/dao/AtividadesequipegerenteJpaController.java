/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Atividade;
import data.crud.Atividadesequipegerente;
import data.crud.Equipe;
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
public class AtividadesequipegerenteJpaController implements Serializable {

    public AtividadesequipegerenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Atividadesequipegerente atividadesequipegerente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividade idatividade = atividadesequipegerente.getIdatividade();
            if (idatividade != null) {
                idatividade = em.getReference(idatividade.getClass(), idatividade.getId());
                atividadesequipegerente.setIdatividade(idatividade);
            }
            Equipe idequipe = atividadesequipegerente.getIdequipe();
            if (idequipe != null) {
                idequipe = em.getReference(idequipe.getClass(), idequipe.getId());
                atividadesequipegerente.setIdequipe(idequipe);
            }
            Usuario idusuariogerente = atividadesequipegerente.getIdusuariogerente();
            if (idusuariogerente != null) {
                idusuariogerente = em.getReference(idusuariogerente.getClass(), idusuariogerente.getId());
                atividadesequipegerente.setIdusuariogerente(idusuariogerente);
            }
            em.persist(atividadesequipegerente);
            if (idatividade != null) {
                idatividade.getAtividadesequipegerenteCollection().add(atividadesequipegerente);
                idatividade = em.merge(idatividade);
            }
            if (idequipe != null) {
                idequipe.getAtividadesequipegerenteCollection().add(atividadesequipegerente);
                idequipe = em.merge(idequipe);
            }
            if (idusuariogerente != null) {
                idusuariogerente.getAtividadesequipegerenteCollection().add(atividadesequipegerente);
                idusuariogerente = em.merge(idusuariogerente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Atividadesequipegerente atividadesequipegerente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividadesequipegerente persistentAtividadesequipegerente = em.find(Atividadesequipegerente.class, atividadesequipegerente.getId());
            Atividade idatividadeOld = persistentAtividadesequipegerente.getIdatividade();
            Atividade idatividadeNew = atividadesequipegerente.getIdatividade();
            Equipe idequipeOld = persistentAtividadesequipegerente.getIdequipe();
            Equipe idequipeNew = atividadesequipegerente.getIdequipe();
            Usuario idusuariogerenteOld = persistentAtividadesequipegerente.getIdusuariogerente();
            Usuario idusuariogerenteNew = atividadesequipegerente.getIdusuariogerente();
            if (idatividadeNew != null) {
                idatividadeNew = em.getReference(idatividadeNew.getClass(), idatividadeNew.getId());
                atividadesequipegerente.setIdatividade(idatividadeNew);
            }
            if (idequipeNew != null) {
                idequipeNew = em.getReference(idequipeNew.getClass(), idequipeNew.getId());
                atividadesequipegerente.setIdequipe(idequipeNew);
            }
            if (idusuariogerenteNew != null) {
                idusuariogerenteNew = em.getReference(idusuariogerenteNew.getClass(), idusuariogerenteNew.getId());
                atividadesequipegerente.setIdusuariogerente(idusuariogerenteNew);
            }
            atividadesequipegerente = em.merge(atividadesequipegerente);
            if (idatividadeOld != null && !idatividadeOld.equals(idatividadeNew)) {
                idatividadeOld.getAtividadesequipegerenteCollection().remove(atividadesequipegerente);
                idatividadeOld = em.merge(idatividadeOld);
            }
            if (idatividadeNew != null && !idatividadeNew.equals(idatividadeOld)) {
                idatividadeNew.getAtividadesequipegerenteCollection().add(atividadesequipegerente);
                idatividadeNew = em.merge(idatividadeNew);
            }
            if (idequipeOld != null && !idequipeOld.equals(idequipeNew)) {
                idequipeOld.getAtividadesequipegerenteCollection().remove(atividadesequipegerente);
                idequipeOld = em.merge(idequipeOld);
            }
            if (idequipeNew != null && !idequipeNew.equals(idequipeOld)) {
                idequipeNew.getAtividadesequipegerenteCollection().add(atividadesequipegerente);
                idequipeNew = em.merge(idequipeNew);
            }
            if (idusuariogerenteOld != null && !idusuariogerenteOld.equals(idusuariogerenteNew)) {
                idusuariogerenteOld.getAtividadesequipegerenteCollection().remove(atividadesequipegerente);
                idusuariogerenteOld = em.merge(idusuariogerenteOld);
            }
            if (idusuariogerenteNew != null && !idusuariogerenteNew.equals(idusuariogerenteOld)) {
                idusuariogerenteNew.getAtividadesequipegerenteCollection().add(atividadesequipegerente);
                idusuariogerenteNew = em.merge(idusuariogerenteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = atividadesequipegerente.getId();
                if (findAtividadesequipegerente(id) == null) {
                    throw new NonexistentEntityException("The atividadesequipegerente with id " + id + " no longer exists.");
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
            Atividadesequipegerente atividadesequipegerente;
            try {
                atividadesequipegerente = em.getReference(Atividadesequipegerente.class, id);
                atividadesequipegerente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atividadesequipegerente with id " + id + " no longer exists.", enfe);
            }
            Atividade idatividade = atividadesequipegerente.getIdatividade();
            if (idatividade != null) {
                idatividade.getAtividadesequipegerenteCollection().remove(atividadesequipegerente);
                idatividade = em.merge(idatividade);
            }
            Equipe idequipe = atividadesequipegerente.getIdequipe();
            if (idequipe != null) {
                idequipe.getAtividadesequipegerenteCollection().remove(atividadesequipegerente);
                idequipe = em.merge(idequipe);
            }
            Usuario idusuariogerente = atividadesequipegerente.getIdusuariogerente();
            if (idusuariogerente != null) {
                idusuariogerente.getAtividadesequipegerenteCollection().remove(atividadesequipegerente);
                idusuariogerente = em.merge(idusuariogerente);
            }
            em.remove(atividadesequipegerente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Atividadesequipegerente> findAtividadesequipegerenteEntities() {
        return findAtividadesequipegerenteEntities(true, -1, -1);
    }

    public List<Atividadesequipegerente> findAtividadesequipegerenteEntities(int maxResults, int firstResult) {
        return findAtividadesequipegerenteEntities(false, maxResults, firstResult);
    }

    private List<Atividadesequipegerente> findAtividadesequipegerenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Atividadesequipegerente.class));
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

    public Atividadesequipegerente findAtividadesequipegerente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Atividadesequipegerente.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtividadesequipegerenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Atividadesequipegerente> rt = cq.from(Atividadesequipegerente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Atividadesequipegerente> retornaAtvEqp(Equipe e){
        List<Atividadesequipegerente> lista = new ArrayList<Atividadesequipegerente>();
        List<Atividadesequipegerente> aux = findAtividadesequipegerenteEntities();
        for(Atividadesequipegerente atv : aux){
            int a = atv.getIdequipe().getId();
            int b = e.getId();
            
            if(a == b)
                lista.add(atv);
        }
        return lista;
    }
    
     public void salvarOuRemover(Equipe eqp, int[] atvSelGer){
        AtividadeJpaController daoAtv = new AtividadeJpaController(emf);
        List<Atividade> listaAtv = new ArrayList<Atividade>();
        
        List<Atividadesequipegerente> lista = retornaAtvEqp(eqp);
        List<Atividadesequipegerente> listaAux = new ArrayList<Atividadesequipegerente>();
        for(int i = 0; i < atvSelGer.length; i++){/*método responsável por transformar o array de int em obj*/
            Atividade atv = daoAtv.findAtividade(atvSelGer[i]);
            Atividadesequipegerente a = new Atividadesequipegerente();
            a.setIdatividade(atv);
            a.setIdequipe(eqp);
            
            listaAux.add(a);
        }
        
        for(Atividadesequipegerente a : lista){/*verifica e exclui todas as atividades desta equipe que não foram selecionadas*/
            if(!listaAux.contains(a)){
                try {
                    destroy(a.getId());
                } catch (Exception e) {
                    System.out.println("Erro, classe atividadesEquipeJpaController, método salvarRemover, " + e.toString());
                }
                
            }
        }
        
        for(Atividadesequipegerente a : listaAux){/*adiciona todas as atividades dessa equipe que foram selecionadas*/
            if(!lista.contains(a))
                create(a);
        }      
    }
    
}
