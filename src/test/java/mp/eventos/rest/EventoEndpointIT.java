package mp.eventos.rest;

import mp.eventos.dao.EventoDao;
import mp.eventos.model.Evento;
import mp.eventos.util.TestConfig;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import mp.eventos.model.Asistente;
import mp.eventos.model.AsistenteEvento;
import mp.eventos.model.Ponente;
import mp.eventos.model.PonenteEvento;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author JasonDiazG
 */

@RunWith(Arquillian.class)
public class EventoEndpointIT {

    private static Long entityId;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "eventos-mp-rest-test.war")
                .addClasses(EventoEndpoint.class, RestApplication.class,
                        EventoDao.class, Evento.class, Ponente.class, Asistente.class,
                        PonenteEvento.class, AsistenteEvento.class)
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @InSequence(1)
    public void testAddEvento() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-rest-test/rest/eventos");

        Evento entity = new Evento();

        entity.setNombre("Evento prueba");
        entity.setLugar("Lugar del evento");

        entity = target.request("application/json").post(Entity.json(entity), Evento.class);
        entityId = entity.getId();

        entity = target.path("{id}").resolveTemplate("id", entityId)
                .request("application/json").get(Evento.class);

        assertEquals("Evento prueba", entity.getNombre());
    }

    @Test
    @InSequence(2)
    public void testUpdateEvento() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-rest-test/rest/eventos/{id}")
                .resolveTemplate("id", entityId);

        Evento entity = target.request("application/json").get(Evento.class);

        entity.setLugar("Lugar actualizado");

        target.request().put(Entity.json(entity));

        entity = target.request("application/json").get(Evento.class);

        assertEquals("Evento prueba", entity.getNombre());
        assertEquals("Lugar actualizado", entity.getLugar());
    }

    @Test
    @InSequence(3)
    public void testDeleteEvento() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-rest-test/rest/eventos/{id}")
                .resolveTemplate("id", entityId);
        target.request().delete();
        Evento entity = null;
        try {
            entity = target.request("application/json").get(Evento.class);
        } catch (NotFoundException e) {
        }
        assertNull(entity);
    }

}
