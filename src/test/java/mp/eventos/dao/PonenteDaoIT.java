package mp.eventos.dao;

import static org.junit.Assert.assertEquals;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import mp.eventos.model.Ponente;
import static org.junit.Assert.assertNull;

/**
 *
 * @author JasonDiazG
 */
@RunWith(Arquillian.class)
public class PonenteDaoIT {

    private static Long entityId;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "evento-dao-test.war")
                .addClasses(PonenteDao.class, Ponente.class)
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @EJB
    private PonenteDao service;

    @Test
    @InSequence(1)
    public void testAddEntity() {
        
        Ponente entity = new Ponente();
        entity.setNombre("Jason");
        entity.setApellido("Diaz");
        entity.setEmail("jasondiazg@mp.gob.gt");
        entity.setIdentificacion("1234-56789-0101");
        
        service.create(entity);
        entityId = entity.getId();

        entity = service.findById(entityId);
        assertEquals("Jason Diaz (jasondiazg@mp.gob.gt, 1234-56789-0101)", (entity.getNombre() + " " + entity.getApellido() + " (" + entity.getEmail() + ", " + entity.getIdentificacion() + ")"));
    }

    @Test
    @InSequence(2)
    public void testModifyEntity() {
        Ponente entity = service.findById(entityId);
        entity.setNombre("Jason Rene");
        
        service.update(entity);
        entityId = entity.getId();
        entity = service.findById(entityId);
        assertEquals("Jason Rene Diaz (jasondiazg@mp.gob.gt, 1234-56789-0101)", (entity.getNombre() + " " + entity.getApellido() + " (" + entity.getEmail() + ", " + entity.getIdentificacion() + ")"));
    }

    @Test
    @InSequence(3)
    public void testDeleteEntity() {
        service.deleteById(entityId);

        Ponente entity = service.findById(entityId);
        assertNull(entity);
    }

}
