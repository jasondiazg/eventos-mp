package mp.eventos.dao;

import java.util.Date;
import static org.junit.Assert.assertEquals;
import javax.ejb.EJB;
import mp.eventos.model.Asistente;
import mp.eventos.model.AsistenteEvento;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import mp.eventos.model.Evento;
import mp.eventos.model.Ponente;
import mp.eventos.model.PonenteEvento;
import static org.junit.Assert.assertNull;

/**
 *
 * @author JasonDiazG
 */
@RunWith(Arquillian.class)
public class EventoDaoIT {

    private static Long entityId;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "evento-dao-test.war")
                .addClasses(EventoDao.class, Evento.class, PonenteEvento.class, AsistenteEvento.class, Asistente.class, Ponente.class)
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @EJB
    private EventoDao service;

    @Test
    @InSequence(1)
    public void testAddEvento() {
        
        Evento entity = new Evento();
        entity.setNombre("Evento de prueba");
        entity.setFechaEvento(new Date());
        entity.setLugar("Lugar de prueba");

        service.create(entity);
        entityId = entity.getId();

        entity = service.findById(entityId);
        assertEquals("Evento de prueba", entity.getNombre());
    }

    @Test
    @InSequence(2)
    public void testModifyEvento() {
        Evento entity = service.findById(entityId);
        entity.setLugar("Nuevo lugar");
        service.update(entity);

        entityId = entity.getId();
        entity = service.findById(entityId);
        assertEquals("Evento de prueba", entity.getNombre());
        assertEquals("Nuevo lugar", entity.getLugar());
    }

    @Test
    @InSequence(3)
    public void testDeleteEvento() {
        service.deleteById(entityId);

        Evento entity = service.findById(entityId);
        assertNull(entity);
    }

}
