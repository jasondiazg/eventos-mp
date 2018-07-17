package mp.eventos.web;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

/**
 *
 * @author JasonDiazG
 */

public class ArquillianTest {

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "payara-arquillian.war");
	}

	@Test
	public void test01() {
		System.out.println("Hola mundo");
	}

}
