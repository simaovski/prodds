/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import br.util.UtilDao;
import data.crud.Atividade;
import data.crud.Atividadesantecessoras;
import data.crud.Fasesprocesso;
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
public class AtividadeJpaController implements Serializable {

    public AtividadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Atividade atividade) {
        if (atividade.getAtividadesantecessorasCollection() == null) {
            atividade.setAtividadesantecessorasCollection(new ArrayList<Atividadesantecessoras>());
        }
        if (atividade.getAtividadesantecessorasCollection1() == null) {
            atividade.setAtividadesantecessorasCollection1(new ArrayList<Atividadesantecessoras>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fasesprocesso idfase = atividade.getIdfase();
            if (idfase != null) {
                idfase = em.getReference(idfase.getClass(), idfase.getId());
                atividade.setIdfase(idfase);
            }
            Usuario idgerente = atividade.getIdgerente();
            if (idgerente != null) {
                idgerente = em.getReference(idgerente.getClass(), idgerente.getId());
                atividade.setIdgerente(idgerente);
            }
            Collection<Atividadesantecessoras> attachedAtividadesantecessorasCollection = new ArrayList<Atividadesantecessoras>();
            for (Atividadesantecessoras atividadesantecessorasCollectionAtividadesantecessorasToAttach : atividade.getAtividadesantecessorasCollection()) {
                atividadesantecessorasCollectionAtividadesantecessorasToAttach = em.getReference(atividadesantecessorasCollectionAtividadesantecessorasToAttach.getClass(), atividadesantecessorasCollectionAtividadesantecessorasToAttach.getId());
                attachedAtividadesantecessorasCollection.add(atividadesantecessorasCollectionAtividadesantecessorasToAttach);
            }
            atividade.setAtividadesantecessorasCollection(attachedAtividadesantecessorasCollection);
            Collection<Atividadesantecessoras> attachedAtividadesantecessorasCollection1 = new ArrayList<Atividadesantecessoras>();
            for (Atividadesantecessoras atividadesantecessorasCollection1AtividadesantecessorasToAttach : atividade.getAtividadesantecessorasCollection1()) {
                atividadesantecessorasCollection1AtividadesantecessorasToAttach = em.getReference(atividadesantecessorasCollection1AtividadesantecessorasToAttach.getClass(), atividadesantecessorasCollection1AtividadesantecessorasToAttach.getId());
                attachedAtividadesantecessorasCollection1.add(atividadesantecessorasCollection1AtividadesantecessorasToAttach);
            }
            atividade.setAtividadesantecessorasCollection1(attachedAtividadesantecessorasCollection1);
            em.persist(atividade);
            if (idfase != null) {
                idfase.getAtividadeCollection().add(atividade);
                idfase = em.merge(idfase);
            }
            if (idgerente != null) {
                idgerente.getAtividadeCollection().add(atividade);
                idgerente = em.merge(idgerente);
            }
            for (Atividadesantecessoras atividadesantecessorasCollectionAtividadesantecessoras : atividade.getAtividadesantecessorasCollection()) {
                Atividade oldIdatividadeOfAtividadesantecessorasCollectionAtividadesantecessoras = atividadesantecessorasCollectionAtividadesantecessoras.getIdatividade();
                atividadesantecessorasCollectionAtividadesantecessoras.setIdatividade(atividade);
                atividadesantecessorasCollectionAtividadesantecessoras = em.merge(atividadesantecessorasCollectionAtividadesantecessoras);
                if (oldIdatividadeOfAtividadesantecessorasCollectionAtividadesantecessoras != null) {
                    oldIdatividadeOfAtividadesantecessorasCollectionAtividadesantecessoras.getAtividadesantecessorasCollection().remove(atividadesantecessorasCollectionAtividadesantecessoras);
                    oldIdatividadeOfAtividadesantecessorasCollectionAtividadesantecessoras = em.merge(oldIdatividadeOfAtividadesantecessorasCollectionAtividadesantecessoras);
                }
            }
            for (Atividadesantecessoras atividadesantecessorasCollection1Atividadesantecessoras : atividade.getAtividadesantecessorasCollection1()) {
                Atividade oldIdatividadeantecessoraOfAtividadesantecessorasCollection1Atividadesantecessoras = atividadesantecessorasCollection1Atividadesantecessoras.getIdatividadeantecessora();
                atividadesantecessorasCollection1Atividadesantecessoras.setIdatividadeantecessora(atividade);
                atividadesantecessorasCollection1Atividadesantecessoras = em.merge(atividadesantecessorasCollection1Atividadesantecessoras);
                if (oldIdatividadeantecessoraOfAtividadesantecessorasCollection1Atividadesantecessoras != null) {
                    oldIdatividadeantecessoraOfAtividadesantecessorasCollection1Atividadesantecessoras.getAtividadesantecessorasCollection1().remove(atividadesantecessorasCollection1Atividadesantecessoras);
                    oldIdatividadeantecessoraOfAtividadesantecessorasCollection1Atividadesantecessoras = em.merge(oldIdatividadeantecessoraOfAtividadesantecessorasCollection1Atividadesantecessoras);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Atividade atividade) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividade persistentAtividade = em.find(Atividade.class, atividade.getId());
            Fasesprocesso idfaseOld = persistentAtividade.getIdfase();
            Fasesprocesso idfaseNew = atividade.getIdfase();
            Usuario idgerenteOld = persistentAtividade.getIdgerente();
            Usuario idgerenteNew = atividade.getIdgerente();
//            Collection<Atividadesantecessoras> atividadesantecessorasCollectionOld = persistentAtividade.getAtividadesantecessorasCollection();
//            Collection<Atividadesantecessoras> atividadesantecessorasCollectionNew = atividade.getAtividadesantecessorasCollection();
//            Collection<Atividadesantecessoras> atividadesantecessorasCollection1Old = persistentAtividade.getAtividadesantecessorasCollection1();
//            Collection<Atividadesantecessoras> atividadesantecessorasCollection1New = atividade.getAtividadesantecessorasCollection1();
            if (idfaseNew != null) {
                idfaseNew = em.getReference(idfaseNew.getClass(), idfaseNew.getId());
                atividade.setIdfase(idfaseNew);
            }
            if (idgerenteNew != null) {
                idgerenteNew = em.getReference(idgerenteNew.getClass(), idgerenteNew.getId());
                atividade.setIdgerente(idgerenteNew);
            }
//            Collection<Atividadesantecessoras> attachedAtividadesantecessorasCollectionNew = new ArrayList<Atividadesantecessoras>();
//            for (Atividadesantecessoras atividadesantecessorasCollectionNewAtividadesantecessorasToAttach : atividadesantecessorasCollectionNew) {
//                atividadesantecessorasCollectionNewAtividadesantecessorasToAttach = em.getReference(atividadesantecessorasCollectionNewAtividadesantecessorasToAttach.getClass(), atividadesantecessorasCollectionNewAtividadesantecessorasToAttach.getId());
//                attachedAtividadesantecessorasCollectionNew.add(atividadesantecessorasCollectionNewAtividadesantecessorasToAttach);
//            }
//            atividadesantecessorasCollectionNew = attachedAtividadesantecessorasCollectionNew;
//            atividade.setAtividadesantecessorasCollection(atividadesantecessorasCollectionNew);
//            Collection<Atividadesantecessoras> attachedAtividadesantecessorasCollection1New = new ArrayList<Atividadesantecessoras>();
//            for (Atividadesantecessoras atividadesantecessorasCollection1NewAtividadesantecessorasToAttach : atividadesantecessorasCollection1New) {
//                atividadesantecessorasCollection1NewAtividadesantecessorasToAttach = em.getReference(atividadesantecessorasCollection1NewAtividadesantecessorasToAttach.getClass(), atividadesantecessorasCollection1NewAtividadesantecessorasToAttach.getId());
//                attachedAtividadesantecessorasCollection1New.add(atividadesantecessorasCollection1NewAtividadesantecessorasToAttach);
//            }
//            atividadesantecessorasCollection1New = attachedAtividadesantecessorasCollection1New;
//            atividade.setAtividadesantecessorasCollection1(atividadesantecessorasCollection1New);
            atividade = em.merge(atividade);
            if (idfaseOld != null && !idfaseOld.equals(idfaseNew)) {
                idfaseOld.getAtividadeCollection().remove(atividade);
                idfaseOld = em.merge(idfaseOld);
            }
            if (idfaseNew != null && !idfaseNew.equals(idfaseOld)) {
                idfaseNew.getAtividadeCollection().add(atividade);
                idfaseNew = em.merge(idfaseNew);
            }
            if (idgerenteOld != null && !idgerenteOld.equals(idgerenteNew)) {
                idgerenteOld.getAtividadeCollection().remove(atividade);
                idgerenteOld = em.merge(idgerenteOld);
            }
            if (idgerenteNew != null && !idgerenteNew.equals(idgerenteOld)) {
                idgerenteNew.getAtividadeCollection().add(atividade);
                idgerenteNew = em.merge(idgerenteNew);
            }
//            for (Atividadesantecessoras atividadesantecessorasCollectionOldAtividadesantecessoras : atividadesantecessorasCollectionOld) {
//                if (!atividadesantecessorasCollectionNew.contains(atividadesantecessorasCollectionOldAtividadesantecessoras)) {
//                    atividadesantecessorasCollectionOldAtividadesantecessoras.setIdatividade(null);
//                    atividadesantecessorasCollectionOldAtividadesantecessoras = em.merge(atividadesantecessorasCollectionOldAtividadesantecessoras);
//                }
//            }
//            for (Atividadesantecessoras atividadesantecessorasCollectionNewAtividadesantecessoras : atividadesantecessorasCollectionNew) {
//                if (!atividadesantecessorasCollectionOld.contains(atividadesantecessorasCollectionNewAtividadesantecessoras)) {
//                    Atividade oldIdatividadeOfAtividadesantecessorasCollectionNewAtividadesantecessoras = atividadesantecessorasCollectionNewAtividadesantecessoras.getIdatividade();
//                    atividadesantecessorasCollectionNewAtividadesantecessoras.setIdatividade(atividade);
//                    atividadesantecessorasCollectionNewAtividadesantecessoras = em.merge(atividadesantecessorasCollectionNewAtividadesantecessoras);
//                    if (oldIdatividadeOfAtividadesantecessorasCollectionNewAtividadesantecessoras != null && !oldIdatividadeOfAtividadesantecessorasCollectionNewAtividadesantecessoras.equals(atividade)) {
//                        oldIdatividadeOfAtividadesantecessorasCollectionNewAtividadesantecessoras.getAtividadesantecessorasCollection().remove(atividadesantecessorasCollectionNewAtividadesantecessoras);
//                        oldIdatividadeOfAtividadesantecessorasCollectionNewAtividadesantecessoras = em.merge(oldIdatividadeOfAtividadesantecessorasCollectionNewAtividadesantecessoras);
//                    }
//                }
//            }
//            for (Atividadesantecessoras atividadesantecessorasCollection1OldAtividadesantecessoras : atividadesantecessorasCollection1Old) {
//                if (!atividadesantecessorasCollection1New.contains(atividadesantecessorasCollection1OldAtividadesantecessoras)) {
//                    atividadesantecessorasCollection1OldAtividadesantecessoras.setIdatividadeantecessora(null);
//                    atividadesantecessorasCollection1OldAtividadesantecessoras = em.merge(atividadesantecessorasCollection1OldAtividadesantecessoras);
//                }
//            }
//            for (Atividadesantecessoras atividadesantecessorasCollection1NewAtividadesantecessoras : atividadesantecessorasCollection1New) {
//                if (!atividadesantecessorasCollection1Old.contains(atividadesantecessorasCollection1NewAtividadesantecessoras)) {
//                    Atividade oldIdatividadeantecessoraOfAtividadesantecessorasCollection1NewAtividadesantecessoras = atividadesantecessorasCollection1NewAtividadesantecessoras.getIdatividadeantecessora();
//                    atividadesantecessorasCollection1NewAtividadesantecessoras.setIdatividadeantecessora(atividade);
//                    atividadesantecessorasCollection1NewAtividadesantecessoras = em.merge(atividadesantecessorasCollection1NewAtividadesantecessoras);
//                    if (oldIdatividadeantecessoraOfAtividadesantecessorasCollection1NewAtividadesantecessoras != null && !oldIdatividadeantecessoraOfAtividadesantecessorasCollection1NewAtividadesantecessoras.equals(atividade)) {
//                        oldIdatividadeantecessoraOfAtividadesantecessorasCollection1NewAtividadesantecessoras.getAtividadesantecessorasCollection1().remove(atividadesantecessorasCollection1NewAtividadesantecessoras);
//                        oldIdatividadeantecessoraOfAtividadesantecessorasCollection1NewAtividadesantecessoras = em.merge(oldIdatividadeantecessoraOfAtividadesantecessorasCollection1NewAtividadesantecessoras);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = atividade.getId();
                if (findAtividade(id) == null) {
                    throw new NonexistentEntityException("The atividade with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void editar(Atividade atividade) throws NonexistentEntityException, Exception{
               EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            Atividade persistentAtividade = em.find(Atividade.class, atividade.getId());
            Fasesprocesso idfaseOld = persistentAtividade.getIdfase();
            Fasesprocesso idfaseNew = atividade.getIdfase();
            Usuario idgerenteOld = persistentAtividade.getIdgerente();
            Usuario idgerenteNew = atividade.getIdgerente();
            
            if (idfaseNew != null) {
                idfaseNew = em.getReference(idfaseNew.getClass(), idfaseNew.getId());
                atividade.setIdfase(idfaseNew);
            }
            if (idgerenteNew != null) {
                idgerenteNew = em.getReference(idgerenteNew.getClass(), idgerenteNew.getId());
                atividade.setIdgerente(idgerenteNew);
            }
            atividade = em.merge(atividade);
            if (idfaseOld != null && !idfaseOld.equals(idfaseNew)) {
                idfaseOld.getAtividadeCollection().remove(atividade);
                idfaseOld = em.merge(idfaseOld);
            }
            if (idfaseNew != null && !idfaseNew.equals(idfaseOld)) {
                idfaseNew.getAtividadeCollection().add(atividade);
                idfaseNew = em.merge(idfaseNew);
            }
            if (idgerenteOld != null && !idgerenteOld.equals(idgerenteNew)) {
                idgerenteOld.getAtividadeCollection().remove(atividade);
                idgerenteOld = em.merge(idgerenteOld);
            }
            if (idgerenteNew != null && !idgerenteNew.equals(idgerenteOld)) {
                idgerenteNew.getAtividadeCollection().add(atividade);
                idgerenteNew = em.merge(idgerenteNew);
            }
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = atividade.getId();
                if (findAtividade(id) == null) {
                    throw new NonexistentEntityException("The atividade with id " + id + " no longer exists.");
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
            Atividade atividade;
            try {
                atividade = em.getReference(Atividade.class, id);
                atividade.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atividade with id " + id + " no longer exists.", enfe);
            }
            Fasesprocesso idfase = atividade.getIdfase();
            if (idfase != null) {
                idfase.getAtividadeCollection().remove(atividade);
                idfase = em.merge(idfase);
            }
            Usuario idgerente = atividade.getIdgerente();
            if (idgerente != null) {
                idgerente.getAtividadeCollection().remove(atividade);
                idgerente = em.merge(idgerente);
            }
            Collection<Atividadesantecessoras> atividadesantecessorasCollection = atividade.getAtividadesantecessorasCollection();
            for (Atividadesantecessoras atividadesantecessorasCollectionAtividadesantecessoras : atividadesantecessorasCollection) {
                atividadesantecessorasCollectionAtividadesantecessoras.setIdatividade(null);
                atividadesantecessorasCollectionAtividadesantecessoras = em.merge(atividadesantecessorasCollectionAtividadesantecessoras);
            }
            Collection<Atividadesantecessoras> atividadesantecessorasCollection1 = atividade.getAtividadesantecessorasCollection1();
            for (Atividadesantecessoras atividadesantecessorasCollection1Atividadesantecessoras : atividadesantecessorasCollection1) {
                atividadesantecessorasCollection1Atividadesantecessoras.setIdatividadeantecessora(null);
                atividadesantecessorasCollection1Atividadesantecessoras = em.merge(atividadesantecessorasCollection1Atividadesantecessoras);
            }
            em.remove(atividade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Atividade> findAtividadeEntities() {
        return findAtividadeEntities(true, -1, -1);
    }

    public List<Atividade> findAtividadeEntities(int maxResults, int firstResult) {
        return findAtividadeEntities(false, maxResults, firstResult);
    }

    private List<Atividade> findAtividadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Atividade.class));
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

    public Atividade findAtividade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Atividade.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtividadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Atividade> rt = cq.from(Atividade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Atividade> retornaAtividadePorGerente(Usuario u, Projeto p){
        List<Atividade> lista = new ArrayList<Atividade>();
        List<Atividade> aux = findAtividadeEntities();
        for(Atividade atv : aux){
            int a = atv.getIdgerente().getId();
            int b = u.getId();
            
            int c = atv.getIdfase().getIdprocesso().getId();
            int d = p.getIdprocesso().getId();
            if(a == b && c == d)
                lista.add(atv);
        }
        return lista;
    }    
}
