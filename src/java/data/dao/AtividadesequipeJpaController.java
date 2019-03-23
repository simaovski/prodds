/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Atividadesequipe;
import data.crud.Atividadesfaseprocesso;
import data.crud.Equipe;
import data.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.AbstractList;
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
public class AtividadesequipeJpaController implements Serializable {

    public AtividadesequipeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Atividadesequipe atividadesequipe) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividadesfaseprocesso idatividade = atividadesequipe.getIdatividade();
            if (idatividade != null) {
                idatividade = em.getReference(idatividade.getClass(), idatividade.getId());
                atividadesequipe.setIdatividade(idatividade);
            }
            Equipe idequipe = atividadesequipe.getIdequipe();
            if (idequipe != null) {
                idequipe = em.getReference(idequipe.getClass(), idequipe.getId());
                atividadesequipe.setIdequipe(idequipe);
            }
            em.persist(atividadesequipe);
            if (idatividade != null) {
                idatividade.getAtividadesequipeCollection().add(atividadesequipe);
                idatividade = em.merge(idatividade);
            }
            if (idequipe != null) {
                idequipe.getAtividadesequipeCollection().add(atividadesequipe);
                idequipe = em.merge(idequipe);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Atividadesequipe atividadesequipe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividadesequipe persistentAtividadesequipe = em.find(Atividadesequipe.class, atividadesequipe.getId());
            Atividadesfaseprocesso idatividadeOld = persistentAtividadesequipe.getIdatividade();
            Atividadesfaseprocesso idatividadeNew = atividadesequipe.getIdatividade();
            Equipe idequipeOld = persistentAtividadesequipe.getIdequipe();
            Equipe idequipeNew = atividadesequipe.getIdequipe();
            if (idatividadeNew != null) {
                idatividadeNew = em.getReference(idatividadeNew.getClass(), idatividadeNew.getId());
                atividadesequipe.setIdatividade(idatividadeNew);
            }
            if (idequipeNew != null) {
                idequipeNew = em.getReference(idequipeNew.getClass(), idequipeNew.getId());
                atividadesequipe.setIdequipe(idequipeNew);
            }
            atividadesequipe = em.merge(atividadesequipe);
            if (idatividadeOld != null && !idatividadeOld.equals(idatividadeNew)) {
                idatividadeOld.getAtividadesequipeCollection().remove(atividadesequipe);
                idatividadeOld = em.merge(idatividadeOld);
            }
            if (idatividadeNew != null && !idatividadeNew.equals(idatividadeOld)) {
                idatividadeNew.getAtividadesequipeCollection().add(atividadesequipe);
                idatividadeNew = em.merge(idatividadeNew);
            }
            if (idequipeOld != null && !idequipeOld.equals(idequipeNew)) {
                idequipeOld.getAtividadesequipeCollection().remove(atividadesequipe);
                idequipeOld = em.merge(idequipeOld);
            }
            if (idequipeNew != null && !idequipeNew.equals(idequipeOld)) {
                idequipeNew.getAtividadesequipeCollection().add(atividadesequipe);
                idequipeNew = em.merge(idequipeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = atividadesequipe.getId();
                if (findAtividadesequipe(id) == null) {
                    throw new NonexistentEntityException("The atividadesequipe with id " + id + " no longer exists.");
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
            Atividadesequipe atividadesequipe;
            try {
                atividadesequipe = em.getReference(Atividadesequipe.class, id);
                atividadesequipe.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atividadesequipe with id " + id + " no longer exists.", enfe);
            }
            Atividadesfaseprocesso idatividade = atividadesequipe.getIdatividade();
            if (idatividade != null) {
                idatividade.getAtividadesequipeCollection().remove(atividadesequipe);
                idatividade = em.merge(idatividade);
            }
            Equipe idequipe = atividadesequipe.getIdequipe();
            if (idequipe != null) {
                idequipe.getAtividadesequipeCollection().remove(atividadesequipe);
                idequipe = em.merge(idequipe);
            }
            em.remove(atividadesequipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Atividadesequipe> findAtividadesequipeEntities() {
        return findAtividadesequipeEntities(true, -1, -1);
    }

    public List<Atividadesequipe> findAtividadesequipeEntities(int maxResults, int firstResult) {
        return findAtividadesequipeEntities(false, maxResults, firstResult);
    }

    private List<Atividadesequipe> findAtividadesequipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Atividadesequipe.class));
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

    public Atividadesequipe findAtividadesequipe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Atividadesequipe.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtividadesequipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Atividadesequipe> rt = cq.from(Atividadesequipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Atividadesequipe> retornaAtvEqp(Equipe e){
        List<Atividadesequipe> lista = new ArrayList<Atividadesequipe>();
        List<Atividadesequipe> aux = findAtividadesequipeEntities();
        for(Atividadesequipe atv : aux){
            int a = atv.getIdequipe().getId();
            int b = e.getId();
            
            if(a == b)
                lista.add(atv);
                
        }
        return lista;
    }
    
    public void salvarOuRemover(Equipe eqp, int[] atvSel){
        AtividadesfaseprocessoJpaController daoAtv = new AtividadesfaseprocessoJpaController(emf);
        List<Atividadesfaseprocesso> listaAtv = new ArrayList<Atividadesfaseprocesso>();
        
        List<Atividadesequipe> lista = retornaAtvEqp(eqp);
        List<Atividadesequipe> listaAux = new ArrayList<Atividadesequipe>();
        for(int i = 0; i < atvSel.length; i++){/*método responsável por transformar o array de int em obj*/
            Atividadesfaseprocesso atv = daoAtv.findAtividadesfaseprocesso(atvSel[i]);
            Atividadesequipe a = new Atividadesequipe();
            a.setIdatividade(atv);
            a.setIdequipe(eqp);
            
            listaAux.add(a);
        }
        
        for(Atividadesequipe a : lista){/*verifica e exclui todas as atividades desta equipe que não foram selecionadas*/
            if(!listaAux.contains(a)){
                try {
                    destroy(a.getId());
                } catch (Exception e) {
                    System.out.println("Erro, classe atividadesEquipeJpaController, método salvarRemover, " + e.toString());
                }
                
            }
        }
        
        for(Atividadesequipe a : listaAux){/*adiciona todas as atividades dessa equipe que foram selecionadas*/
            if(!lista.contains(a))
                create(a);
        }      
    }
    
}
