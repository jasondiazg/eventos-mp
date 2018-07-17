package mp.eventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import mp.eventos.model.Evento;

/**
 *
 * @author JasonDiazG
 */
@Stateless
public class EventoDao {

    @PersistenceContext(unitName = "eventos-mp-persistence-unit")
    private EntityManager em;

    public void create(Evento entity) {
        em.persist(entity);
    }

    public void deleteById(Long id) {
        Evento entity = em.find(Evento.class, id);
        if (entity != null) {
            em.remove(entity);
        }
    }

    public Evento findById(Long id) {
        return em.find(Evento.class, id);
    }

    public Evento update(Evento entity) {
        return em.merge(entity);
    }

    public List<Evento> listAll(Integer startPosition, Integer maxResult) {
        TypedQuery<Evento> findAllQuery = em.createQuery(
                "SELECT DISTINCT e FROM Evento e ORDER BY e.id", Evento.class);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }
}
