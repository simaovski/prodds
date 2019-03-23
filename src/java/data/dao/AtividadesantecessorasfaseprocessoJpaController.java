/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.dao;

import data.crud.Atividadesantecessorasfaseprocesso;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import data.crud.Atividadesfaseprocesso;
import data.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author padrao
 */
public class AtividadesantecessorasfaseprocessoJpaController implements Serializable {

    public AtividadesantecessorasfaseprocessoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocesso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividadesfaseprocesso idatividade = atividadesantecessorasfaseprocesso.getIdatividade();
            if (idatividade != null) {
                idatividade = em.getReference(idatividade.getClass(), idatividade.getId());
                atividadesantecessorasfaseprocesso.setIdatividade(idatividade);
            }
            Atividadesfaseprocesso idatividadesucessora = atividadesantecessorasfaseprocesso.getIdatividadesucessora();
            if (idatividadesucessora != null) {
                idatividadesucessora = em.getReference(idatividadesucessora.getClass(), idatividadesucessora.getId());
                atividadesantecessorasfaseprocesso.setIdatividadesucessora(idatividadesucessora);
            }
            em.persist(atividadesantecessorasfaseprocesso);
            if (idatividade != null) {
                idatividade.getAtividadesantecessorasfaseprocessoCollection().add(atividadesantecessorasfaseprocesso);
                idatividade = em.merge(idatividade);
            }
            if (idatividadesucessora != null) {
                idatividadesucessora.getAtividadesantecessorasfaseprocessoCollection().add(atividadesantecessorasfaseprocesso);
                idatividadesucessora = em.merge(idatividadesucessora);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocesso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividadesantecessorasfaseprocesso persistentAtividadesantecessorasfaseprocesso = em.find(Atividadesantecessorasfaseprocesso.class, atividadesantecessorasfaseprocesso.getId());
            Atividadesfaseprocesso idatividadeOld = persistentAtividadesantecessorasfaseprocesso.getIdatividade();
            Atividadesfaseprocesso idatividadeNew = atividadesantecessorasfaseprocesso.getIdatividade();
            Atividadesfaseprocesso idatividadesucessoraOld = persistentAtividadesantecessorasfaseprocesso.getIdatividadesucessora();
            Atividadesfaseprocesso idatividadesucessoraNew = atividadesantecessorasfaseprocesso.getIdatividadesucessora();
            if (idatividadeNew != null) {
                idatividadeNew = em.getReference(idatividadeNew.getClass(), idatividadeNew.getId());
                atividadesantecessorasfaseprocesso.setIdatividade(idatividadeNew);
            }
            if (idatividadesucessoraNew != null) {
                idatividadesucessoraNew = em.getReference(idatividadesucessoraNew.getClass(), idatividadesucessoraNew.getId());
                atividadesantecessorasfaseprocesso.setIdatividadesucessora(idatividadesucessoraNew);
            }
            atividadesantecessorasfaseprocesso = em.merge(atividadesantecessorasfaseprocesso);
            if (idatividadeOld != null && !idatividadeOld.equals(idatividadeNew)) {
                idatividadeOld.getAtividadesantecessorasfaseprocessoCollection().remove(atividadesantecessorasfaseprocesso);
                idatividadeOld = em.merge(idatividadeOld);
            }
            if (idatividadeNew != null && !idatividadeNew.equals(idatividadeOld)) {
                idatividadeNew.getAtividadesantecessorasfaseprocessoCollection().add(atividadesantecessorasfaseprocesso);
                idatividadeNew = em.merge(idatividadeNew);
            }
            if (idatividadesucessoraOld != null && !idatividadesucessoraOld.equals(idatividadesucessoraNew)) {
                idatividadesucessoraOld.getAtividadesantecessorasfaseprocessoCollection().remove(atividadesantecessorasfaseprocesso);
                idatividadesucessoraOld = em.merge(idatividadesucessoraOld);
            }
            if (idatividadesucessoraNew != null && !idatividadesucessoraNew.equals(idatividadesucessoraOld)) {
                idatividadesucessoraNew.getAtividadesantecessorasfaseprocessoCollection().add(atividadesantecessorasfaseprocesso);
                idatividadesucessoraNew = em.merge(idatividadesucessoraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = atividadesantecessorasfaseprocesso.getId();
                if (findAtividadesantecessorasfaseprocesso(id) == null) {
                    throw new NonexistentEntityException("The atividadesantecessorasfaseprocesso with id " + id + " no longer exists.");
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
            Atividadesantecessorasfaseprocesso atividadesantecessorasfaseprocesso;
            try {
                atividadesantecessorasfaseprocesso = em.getReference(Atividadesantecessorasfaseprocesso.class, id);
                atividadesantecessorasfaseprocesso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atividadesantecessorasfaseprocesso with id " + id + " no longer exists.", enfe);
            }
            Atividadesfaseprocesso idatividade = atividadesantecessorasfaseprocesso.getIdatividade();
            if (idatividade != null) {
                idatividade.getAtividadesantecessorasfaseprocessoCollection().remove(atividadesantecessorasfaseprocesso);
                idatividade = em.merge(idatividade);
            }
            Atividadesfaseprocesso idatividadesucessora = atividadesantecessorasfaseprocesso.getIdatividadesucessora();
            if (idatividadesucessora != null) {
                idatividadesucessora.getAtividadesantecessorasfaseprocessoCollection().remove(atividadesantecessorasfaseprocesso);
                idatividadesucessora = em.merge(idatividadesucessora);
            }
            em.remove(atividadesantecessorasfaseprocesso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Atividadesantecessorasfaseprocesso> findAtividadesantecessorasfaseprocessoEntities() {
        return findAtividadesantecessorasfaseprocessoEntities(true, -1, -1);
    }

    public List<Atividadesantecessorasfaseprocesso> findAtividadesantecessorasfaseprocessoEntities(int maxResults, int firstResult) {
        return findAtividadesantecessorasfaseprocessoEntities(false, maxResults, firstResult);
    }

    private List<Atividadesantecessorasfaseprocesso> findAtividadesantecessorasfaseprocessoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Atividadesantecessorasfaseprocesso.class));
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

    public Atividadesantecessorasfaseprocesso findAtividadesantecessorasfaseprocesso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Atividadesantecessorasfaseprocesso.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtividadesantecessorasfaseprocessoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Atividadesantecessorasfaseprocesso> rt = cq.from(Atividadesantecessorasfaseprocesso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
