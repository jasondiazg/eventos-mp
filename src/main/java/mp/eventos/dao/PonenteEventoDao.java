package mp.eventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import mp.eventos.model.PonenteEvento;

/**
 *
 * @author JasonDiazG
 */
@Stateless
public class PonenteEventoDao {

    @PersistenceContext(unitName = "eventos-mp-persistence-unit")
    private EntityManager em;

    public void create(PonenteEvento entity) {
        em.persist(entity);
    }

    public void deleteById(Long id) {
        PonenteEvento entity = em.find(PonenteEvento.class, id);
        if (entity != null) {
            em.remove(entity);
        }
    }

    public PonenteEvento findById(Long id) {
        return em.find(PonenteEvento.class, id);
    }

    public PonenteEvento update(PonenteEvento entity) {
        return em.merge(entity);
    }

    public List<PonenteEvento> listAll(Integer startPosition, Integer maxResult) {
        TypedQuery<PonenteEvento> findAllQuery = em.createQuery(
                "SELECT DISTINCT pe FROM PonenteEvento pe ORDER BY pe.id", PonenteEvento.class);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }

    public List<PonenteEvento> listAllByEvento(Integer idEvento, Integer startPosition, Integer maxResult) {
        TypedQuery<PonenteEvento> findAllQuery = em.createQuery("SELECT DISTINCT pe FROM PonenteEvento pe WHERE pe.idEvento.id = :idEvento ORDER BY pe.id", PonenteEvento.class).setParameter("idEvento", idEvento);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }

    public List<PonenteEvento> listAllByPonente(Integer idPonente, Integer startPosition, Integer maxResult) {
        TypedQuery<PonenteEvento> findAllQuery = em.createQuery("SELECT DISTINCT pe FROM PonenteEvento pe WHERE pe.idPonente = :idPonente ORDER BY pe.id", PonenteEvento.class).setParameter("idPonente", idPonente);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }
}
