package mp.eventos.rest;

import mp.eventos.dao.AsistenteDao;
import mp.eventos.model.Asistente;
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
public class AsistenteEndpointIT {

    private static Long entityId;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "eventos-mp-rest-test.war")
                .addClasses(AsistenteEndpoint.class, RestApplication.class,
                        AsistenteDao.class, Asistente.class)
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @InSequence(1)
    public void testAddEntity() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-rest-test/rest/asistentes");
        
        Asistente entity = new Asistente();
        entity.setNombre("Victor");
        entity.setApellido("Orozco");
        entity.setEmail("vorozco@mp.gob.gt");
        entity.setIdentificacion("4321-56789-0101");

        entity = target.request("application/json").post(Entity.json(entity), Asistente.class);
        entityId = entity.getId();

        entity = target.path("{id}").resolveTemplate("id", entityId)
                .request("application/json").get(Asistente.class);

        assertEquals("Victor Orozco (vorozco@mp.gob.gt, 4321-56789-0101)", (entity.getNombre() + " " + entity.getApellido() + " (" + entity.getEmail() + ", " + entity.getIdentificacion() + ")"));
    }

    @Test
    @InSequence(2)
    public void testUpdateEntity() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-rest-test/rest/asistentes/{id}")
                .resolveTemplate("id", entityId);

        Asistente entity = target.request("application/json").get(Asistente.class);
        entity.setNombre("Victor N.");

        target.request().put(Entity.json(entity));

        entity = target.request("application/json").get(Asistente.class);
        
        assertEquals("Victor N. Orozco (vorozco@mp.gob.gt, 4321-56789-0101)", (entity.getNombre() + " " + entity.getApellido() + " (" + entity.getEmail() + ", " + entity.getIdentificacion() + ")"));
    }

    @Test
    @InSequence(3)
    public void testDeleteEntity() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-rest-test/rest/asistentes/{id}")
                .resolveTemplate("id", entityId);
        target.request().delete();
        Asistente entity = null;
        try {
            entity = target.request("application/json").get(Asistente.class);
        } catch (NotFoundException e) {
        }
        assertNull(entity);
    }

}
