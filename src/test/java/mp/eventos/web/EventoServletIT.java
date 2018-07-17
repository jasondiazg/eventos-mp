package mp.eventos.web;

import mp.eventos.util.TestConfig;
import static org.junit.Assert.*;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author JasonDiazG
 */

@RunWith(Arquillian.class)
public class EventoServletIT {

	@Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "eventos-mp-servlet-test.war")
                .addClass(EventoServlet.class);
    }
	@Before
	public void setUp() throws Exception {
	}

	@Test
    public void testGetAlerts() {
        Client client = ClientBuilder.newClient();

        // Get account balance
        JsonObject response = client
                .target(TestConfig.TEST_BASE_URL + "/eventos-mp-servlet-test/eventos")
                .queryParam("user_id", "99").request("application/json")
                .get(JsonObject.class);
        // TODO Assert more of the content.
        assertEquals(3, response.getJsonArray("eventos").size());
    }

}
