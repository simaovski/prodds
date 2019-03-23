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
import data.crud.Atividade;
import data.crud.Atividadesantecessoras;
import data.crud.Atividadesfaseprocesso;
import data.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author padrao
 */
public class AtividadesantecessorasJpaController implements Serializable {

    public AtividadesantecessorasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Atividadesantecessoras atividadesantecessoras) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividade idatividade = atividadesantecessoras.getIdatividade();
            if (idatividade != null) {
                idatividade = em.getReference(idatividade.getClass(), idatividade.getId());
                atividadesantecessoras.setIdatividade(idatividade);
            }
            Atividade idatividadeantecessora = atividadesantecessoras.getIdatividadeantecessora();
            if (idatividadeantecessora != null) {
                idatividadeantecessora = em.getReference(idatividadeantecessora.getClass(), idatividadeantecessora.getId());
                atividadesantecessoras.setIdatividadeantecessora(idatividadeantecessora);
            }
            Atividadesfaseprocesso idatividadeprocesso = atividadesantecessoras.getIdatividadeprocesso();
            if (idatividadeprocesso != null) {
                idatividadeprocesso = em.getReference(idatividadeprocesso.getClass(), idatividadeprocesso.getId());
                atividadesantecessoras.setIdatividadeprocesso(idatividadeprocesso);
            }
            Atividadesfaseprocesso idatividadeantecessoraprocesso = atividadesantecessoras.getIdatividadeantecessoraprocesso();
            if (idatividadeantecessoraprocesso != null) {
                idatividadeantecessoraprocesso = em.getReference(idatividadeantecessoraprocesso.getClass(), idatividadeantecessoraprocesso.getId());
                atividadesantecessoras.setIdatividadeantecessoraprocesso(idatividadeantecessoraprocesso);
            }
            em.persist(atividadesantecessoras);
            if (idatividade != null) {
                idatividade.getAtividadesantecessorasCollection().add(atividadesantecessoras);
                idatividade = em.merge(idatividade);
            }
            if (idatividadeantecessora != null) {
                idatividadeantecessora.getAtividadesantecessorasCollection().add(atividadesantecessoras);
                idatividadeantecessora = em.merge(idatividadeantecessora);
            }
            if (idatividadeprocesso != null) {
                idatividadeprocesso.getAtividadesantecessorasCollection().add(atividadesantecessoras);
                idatividadeprocesso = em.merge(idatividadeprocesso);
            }
            if (idatividadeantecessoraprocesso != null) {
                idatividadeantecessoraprocesso.getAtividadesantecessorasCollection().add(atividadesantecessoras);
                idatividadeantecessoraprocesso = em.merge(idatividadeantecessoraprocesso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Atividadesantecessoras atividadesantecessoras) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividadesantecessoras persistentAtividadesantecessoras = em.find(Atividadesantecessoras.class, atividadesantecessoras.getId());
            Atividade idatividadeOld = persistentAtividadesantecessoras.getIdatividade();
            Atividade idatividadeNew = atividadesantecessoras.getIdatividade();
            Atividade idatividadeantecessoraOld = persistentAtividadesantecessoras.getIdatividadeantecessora();
            Atividade idatividadeantecessoraNew = atividadesantecessoras.getIdatividadeantecessora();
            Atividadesfaseprocesso idatividadeprocessoOld = persistentAtividadesantecessoras.getIdatividadeprocesso();
            Atividadesfaseprocesso idatividadeprocessoNew = atividadesantecessoras.getIdatividadeprocesso();
            Atividadesfaseprocesso idatividadeantecessoraprocessoOld = persistentAtividadesantecessoras.getIdatividadeantecessoraprocesso();
            Atividadesfaseprocesso idatividadeantecessoraprocessoNew = atividadesantecessoras.getIdatividadeantecessoraprocesso();
            if (idatividadeNew != null) {
                idatividadeNew = em.getReference(idatividadeNew.getClass(), idatividadeNew.getId());
                atividadesantecessoras.setIdatividade(idatividadeNew);
            }
            if (idatividadeantecessoraNew != null) {
                idatividadeantecessoraNew = em.getReference(idatividadeantecessoraNew.getClass(), idatividadeantecessoraNew.getId());
                atividadesantecessoras.setIdatividadeantecessora(idatividadeantecessoraNew);
            }
            if (idatividadeprocessoNew != null) {
                idatividadeprocessoNew = em.getReference(idatividadeprocessoNew.getClass(), idatividadeprocessoNew.getId());
                atividadesantecessoras.setIdatividadeprocesso(idatividadeprocessoNew);
            }
            if (idatividadeantecessoraprocessoNew != null) {
                idatividadeantecessoraprocessoNew = em.getReference(idatividadeantecessoraprocessoNew.getClass(), idatividadeantecessoraprocessoNew.getId());
                atividadesantecessoras.setIdatividadeantecessoraprocesso(idatividadeantecessoraprocessoNew);
            }
            atividadesantecessoras = em.merge(atividadesantecessoras);
            if (idatividadeOld != null && !idatividadeOld.equals(idatividadeNew)) {
                idatividadeOld.getAtividadesantecessorasCollection().remove(atividadesantecessoras);
                idatividadeOld = em.merge(idatividadeOld);
            }
            if (idatividadeNew != null && !idatividadeNew.equals(idatividadeOld)) {
                idatividadeNew.getAtividadesantecessorasCollection().add(atividadesantecessoras);
                idatividadeNew = em.merge(idatividadeNew);
            }
            if (idatividadeantecessoraOld != null && !idatividadeantecessoraOld.equals(idatividadeantecessoraNew)) {
                idatividadeantecessoraOld.getAtividadesantecessorasCollection().remove(atividadesantecessoras);
                idatividadeantecessoraOld = em.merge(idatividadeantecessoraOld);
            }
            if (idatividadeantecessoraNew != null && !idatividadeantecessoraNew.equals(idatividadeantecessoraOld)) {
                idatividadeantecessoraNew.getAtividadesantecessorasCollection().add(atividadesantecessoras);
                idatividadeantecessoraNew = em.merge(idatividadeantecessoraNew);
            }
            if (idatividadeprocessoOld != null && !idatividadeprocessoOld.equals(idatividadeprocessoNew)) {
                idatividadeprocessoOld.getAtividadesantecessorasCollection().remove(atividadesantecessoras);
                idatividadeprocessoOld = em.merge(idatividadeprocessoOld);
            }
            if (idatividadeprocessoNew != null && !idatividadeprocessoNew.equals(idatividadeprocessoOld)) {
                idatividadeprocessoNew.getAtividadesantecessorasCollection().add(atividadesantecessoras);
                idatividadeprocessoNew = em.merge(idatividadeprocessoNew);
            }
            if (idatividadeantecessoraprocessoOld != null && !idatividadeantecessoraprocessoOld.equals(idatividadeantecessoraprocessoNew)) {
                idatividadeantecessoraprocessoOld.getAtividadesantecessorasCollection().remove(atividadesantecessoras);
                idatividadeantecessoraprocessoOld = em.merge(idatividadeantecessoraprocessoOld);
            }
            if (idatividadeantecessoraprocessoNew != null && !idatividadeantecessoraprocessoNew.equals(idatividadeantecessoraprocessoOld)) {
                idatividadeantecessoraprocessoNew.getAtividadesantecessorasCollection().add(atividadesantecessoras);
                idatividadeantecessoraprocessoNew = em.merge(idatividadeantecessoraprocessoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = atividadesantecessoras.getId();
                if (findAtividadesantecessoras(id) == null) {
                    throw new NonexistentEntityException("The atividadesantecessoras with id " + id + " no longer exists.");
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
            Atividadesantecessoras atividadesantecessoras;
            try {
                atividadesantecessoras = em.getReference(Atividadesantecessoras.class, id);
                atividadesantecessoras.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atividadesantecessoras with id " + id + " no longer exists.", enfe);
            }
            Atividade idatividade = atividadesantecessoras.getIdatividade();
            if (idatividade != null) {
                idatividade.getAtividadesantecessorasCollection().remove(atividadesantecessoras);
                idatividade = em.merge(idatividade);
            }
            Atividade idatividadeantecessora = atividadesantecessoras.getIdatividadeantecessora();
            if (idatividadeantecessora != null) {
                idatividadeantecessora.getAtividadesantecessorasCollection().remove(atividadesantecessoras);
                idatividadeantecessora = em.merge(idatividadeantecessora);
            }
            Atividadesfaseprocesso idatividadeprocesso = atividadesantecessoras.getIdatividadeprocesso();
            if (idatividadeprocesso != null) {
                idatividadeprocesso.getAtividadesantecessorasCollection().remove(atividadesantecessoras);
                idatividadeprocesso = em.merge(idatividadeprocesso);
            }
            Atividadesfaseprocesso idatividadeantecessoraprocesso = atividadesantecessoras.getIdatividadeantecessoraprocesso();
            if (idatividadeantecessoraprocesso != null) {
                idatividadeantecessoraprocesso.getAtividadesantecessorasCollection().remove(atividadesantecessoras);
                idatividadeantecessoraprocesso = em.merge(idatividadeantecessoraprocesso);
            }
            em.remove(atividadesantecessoras);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Atividadesantecessoras> findAtividadesantecessorasEntities() {
        return findAtividadesantecessorasEntities(true, -1, -1);
    }

    public List<Atividadesantecessoras> findAtividadesantecessorasEntities(int maxResults, int firstResult) {
        return findAtividadesantecessorasEntities(false, maxResults, firstResult);
    }

    private List<Atividadesantecessoras> findAtividadesantecessorasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Atividadesantecessoras.class));
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

    public Atividadesantecessoras findAtividadesantecessoras(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Atividadesantecessoras.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtividadesantecessorasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Atividadesantecessoras> rt = cq.from(Atividadesantecessoras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
