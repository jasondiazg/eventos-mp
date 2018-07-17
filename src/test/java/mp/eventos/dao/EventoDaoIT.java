package mp.eventos.dao;

import java.util.Date;
import static org.junit.Assert.assertEquals;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import mp.eventos.model.Evento;
import static org.junit.Assert.assertNull;

/**
 *
 * @author JasonDiazG
 */
@RunWith(Arquillian.class)
public class EventoDaoIT {

    private static Long eventoId;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "evento-dao-test.war")
                .addClasses(EventoDao.class, Evento.class)
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @EJB
    private EventoDao eventoService;

    @Test
    @InSequence(1)
    public void testAddEvento() {
        
        Evento evento = new Evento();
        evento.setNombre("Evento de prueba");
        evento.setFechaRegistrado(new Date());
        evento.setLugar("Lugar de prueba");

        eventoService.create(evento);
        eventoId = evento.getId();

        evento = eventoService.findById(eventoId);
        assertEquals("Evento de prueba", evento.getNombre());
    }

    @Test
    @InSequence(2)
    public void testModifyEvento() {
        Evento evento = eventoService.findById(eventoId);
        evento.setLugar("Nuevo lugar");
        eventoService.update(evento);

        eventoId = evento.getId();
        evento = eventoService.findById(eventoId);
        assertEquals("Evento de prueba", evento.getNombre());
        assertEquals("Nuevo lugar", evento.getLugar());
    }

    @Test
    @InSequence(3)
    public void testDeleteEvento() {
        eventoService.deleteById(eventoId);

        Evento evento = eventoService.findById(eventoId);
        assertNull(evento);
    }

}
