package mp.eventos.rest;

import mp.eventos.dao.PonenteDao;
import mp.eventos.model.Ponente;
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
public class PonenteEndpointIT {

    private static Long entityId;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "eventos-mp-rest-test.war")
                .addClasses(PonenteEndpoint.class, RestApplication.class,
                        PonenteDao.class, Ponente.class)
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @InSequence(1)
    public void testAddEvento() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-rest-test/rest/eventos");

        Ponente entity = new Ponente();
        
        entity.setNombre("Jason");
        entity.setApellido("Diaz");
        entity.setEmail("jasondiazg@mp.gob.gt");
        entity.setIdentificacion("1234-56789-0101");

        entity = target.request("application/json").post(Entity.json(entity), Ponente.class);
        entityId = entity.getId();

        entity = target.path("{id}").resolveTemplate("id", entityId)
                .request("application/json").get(Ponente.class);

        assertEquals("Jason Diaz (jasondiaz@mp.gob.gt, 1234-56789-0101)", (entity.getNombre() + " " + entity.getApellido() + " (" + entity.getEmail() + ", " + entity.getIdentificacion() + ")"));
    }

    @Test
    @InSequence(2)
    public void testUpdateEvento() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-rest-test/rest/eventos/{id}")
                .resolveTemplate("id", entityId);

        Ponente entity = target.request("application/json").get(Ponente.class);
        
        entity.setNombre("Jason Rene");

        target.request().put(Entity.json(entity));

        entity = target.request("application/json").get(Ponente.class);
        
        assertEquals("Jason Rene Diaz (jasondiaz@mp.gob.gt, 1234-56789-0101)", (entity.getNombre() + " " + entity.getApellido() + " (" + entity.getEmail() + ", " + entity.getIdentificacion() + ")"));
    }

    @Test
    @InSequence(3)
    public void testDeleteEvento() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-rest-test/rest/eventos/{id}")
                .resolveTemplate("id", entityId);
        target.request().delete();
        Ponente entity = null;
        try {
            entity = target.request("application/json").get(Ponente.class);
        } catch (NotFoundException e) {
        }
        assertNull(entity);
    }

}
