package mp.eventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import mp.eventos.model.Ponente;

/**
 *
 * @author JasonDiazG
 */
@Stateless
public class PonenteDao {

    @PersistenceContext(unitName = "eventos-mp-persistence-unit")
    private EntityManager em;

    public void create(Ponente entity) {
        em.persist(entity);
    }

    public void deleteById(Long id) {
        Ponente entity = em.find(Ponente.class, id);
        if (entity != null) {
            em.remove(entity);
        }
    }

    public Ponente findById(Long id) {
        return em.find(Ponente.class, id);
    }

    public Ponente update(Ponente entity) {
        return em.merge(entity);
    }

    public List<Ponente> listAll(Integer startPosition, Integer maxResult) {
        TypedQuery<Ponente> findAllQuery = em.createQuery(
                "SELECT DISTINCT p FROM Ponente p ORDER BY p.id", Ponente.class);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }
}
