package mp.eventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import mp.eventos.model.Asistente;

/**
 *
 * @author JasonDiazG
 */
@Stateless
public class AsistenteDao {

    @PersistenceContext(unitName = "eventos-mp-persistence-unit")
    private EntityManager em;

    public void create(Asistente entity) {
        em.persist(entity);
    }

    public void deleteById(Long id) {
        Asistente entity = em.find(Asistente.class, id);
        if (entity != null) {
            em.remove(entity);
        }
    }

    public Asistente findById(Long id) {
        return em.find(Asistente.class, id);
    }

    public Asistente update(Asistente entity) {
        return em.merge(entity);
    }

    public List<Asistente> listAll(Integer startPosition, Integer maxResult) {
        TypedQuery<Asistente> findAllQuery = em.createQuery(
                "SELECT DISTINCT a FROM Asistente a ORDER BY a.id", Asistente.class);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }
}
