package mp.eventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import mp.eventos.model.AsistenteEvento;

/**
 *
 * @author JasonDiazG
 */
@Stateless
public class AsistenteEventoDao {

    @PersistenceContext(unitName = "eventos-mp-persistence-unit")
    private EntityManager em;

    public void create(AsistenteEvento entity) {
        em.persist(entity);
    }

    public void deleteById(Long id) {
        AsistenteEvento entity = em.find(AsistenteEvento.class, id);
        if (entity != null) {
            em.remove(entity);
        }
    }

    public AsistenteEvento findById(Long id) {
        return em.find(AsistenteEvento.class, id);
    }

    public AsistenteEvento update(AsistenteEvento entity) {
        return em.merge(entity);
    }

    public List<AsistenteEvento> listAll(Integer startPosition, Integer maxResult) {
        TypedQuery<AsistenteEvento> findAllQuery = em.createQuery(
                "SELECT DISTINCT ae FROM PonenteEvento ae ORDER BY ae.id", AsistenteEvento.class);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }

    public List<AsistenteEvento> listAllByEvento(Integer idEvento, Integer startPosition, Integer maxResult) {
        TypedQuery<AsistenteEvento> findAllQuery = em.createQuery("SELECT DISTINCT ae FROM AsistenteEvento ae WHERE ae.idEvento.id = :idEvento ORDER BY ae.id", AsistenteEvento.class).setParameter("idEvento", idEvento);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }

    public List<AsistenteEvento> listAllByAsistente(Integer idAsistente, Integer startPosition, Integer maxResult) {
        TypedQuery<AsistenteEvento> findAllQuery = em.createQuery("SELECT DISTINCT ae FROM AsistenteEvento ae WHERE ae.idAsistente.id = :idAsistente ORDER BY ae.id", AsistenteEvento.class).setParameter("idAsistente", idAsistente);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }
}
