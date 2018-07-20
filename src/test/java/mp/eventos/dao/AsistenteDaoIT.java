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
import mp.eventos.model.Asistente;
import static org.junit.Assert.assertNull;

/**
 *
 * @author JasonDiazG
 */
@RunWith(Arquillian.class)
public class AsistenteDaoIT {

    private static Long entityId;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "evento-dao-test.war")
                .addClasses(AsistenteDao.class, Asistente.class)
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @EJB
    private AsistenteDao service;

    @Test
    @InSequence(1)
    public void testAddEntity() {
        
        Asistente entity = new Asistente();
        entity.setNombre("Victor");
        entity.setApellido("Orozco");
        entity.setEmail("vorozco@mp.gob.gt");
        entity.setIdentificacion("4321-56789-0101");
        
        service.create(entity);
        entityId = entity.getId();

        entity = service.findById(entityId);
        assertEquals("Victor Orozco (vorozco@mp.gob.gt, 4321-56789-0101)", (entity.getNombre() + " " + entity.getApellido() + " (" + entity.getEmail() + ", " + entity.getIdentificacion() + ")"));
    }

    @Test
    @InSequence(2)
    public void testModifyEntity() {
        Asistente entity = service.findById(entityId);
        entity.setNombre("Victor N.");
        service.update(entity);

        entityId = entity.getId();
        entity = service.findById(entityId);
        assertEquals("Victor N. Orozco (vorozco@mp.gob.gt, 4321-56789-0101)", (entity.getNombre() + " " + entity.getApellido() + " (" + entity.getEmail() + ", " + entity.getIdentificacion() + ")"));
    }

    @Test
    @InSequence(3)
    public void testDeleteEntity() {
        service.deleteById(entityId);

        Asistente entity = service.findById(entityId);
        assertNull(entity);
    }

}
