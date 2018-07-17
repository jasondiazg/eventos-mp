/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.eventos.rest;

import mp.eventos.dao.EventoDao;
import mp.eventos.model.Evento;
import mp.eventos.util.TestConfig;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
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
    private static Long eventoId;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "eventos-mp-rest-test.war")
                .addClasses(EventoEndpointIT.class, RestApplication.class,
                        EventoDao.class, Evento.class)
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @InSequence(1)
    public void testAddBid() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-rest-test/rest/eventos");
        
        Evento evento = new Evento();

        evento.setNombre("Evento prueba");
        evento.setLugar("Lugar del evento");

        evento = target.request("application/json").post(Entity.json(evento), Evento.class);
        eventoId = evento.getId();

        evento = target.path("{id}").resolveTemplate("id", eventoId)
                .request("application/json").get(Evento.class);

        assertEquals("Evento prueba", evento.getNombre());
    }

    @Test
    @InSequence(2)
    public void testUpdateEvento() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-rest-test/rest/eventos/{id}")
                .resolveTemplate("id", eventoId);

        Evento evento = target.request("application/json").get(Evento.class);

        evento.setLugar("Lugar actualizado");

        target.request().put(Entity.json(evento));

        evento = target.request("application/json").get(Evento.class);

        assertEquals("Evento prueba", evento.getNombre());
        assertEquals("Lugar actualizado", evento.getLugar());
    }

    @Test
    @InSequence(3)
    public void testDeleteBid() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-rest-test/rest/eventos/{id}")
                .resolveTemplate("id", eventoId);
        System.out.println(eventoId);
        target.request().delete();
        Evento evento = null;
        try {
            evento = target.request("application/json").get(Evento.class);
        } catch (NotFoundException e) {
        }
        assertNull(evento);
    }
    
}
