/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.eventos.web;

import mp.eventos.dao.EventoDao;
import mp.eventos.model.Evento;
import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author JasonDiazG
 */
@RunWith(Arquillian.class)
public class EventoWebIT {
    
    @ArquillianResource
    URL contextPath;

    @Drone
    WebDriver browser;

    private static final String WEBAPP_SRC = "src/main/webapp";
    /**
     * Creates a testing WAR of using ShrinkWrap
     *
     * @return WebArchive to be tested
     */
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "eventos-mp.war")
                // add classes
                .addClasses(EventoDao.class, Evento.class, EventoManagedBean.class)
                // add configuration
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-faces-config.xml", "faces-config.xml")
                // add pages
                .addAsWebInfResource("test-web.xml", "web.xml")
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                    .importDirectory(WEBAPP_SRC).as(GenericArchive.class),
                    "/", Filters.include(".*\\.xhtml$"))
                ;
    }


    @Test
    public void testAddBid() {
        browser.navigate().to(contextPath);
        System.out.println(browser.getPageSource());

        browser.findElement(By.id("eventoForm:nombreField")).sendKeys("Nombre evento");
        browser.findElement(By.id("eventoForm:lugarField")).sendKeys("Algun lugar");

        browser.findElement(By.id("eventoForm:addEventButton")).click();

        assertEquals("Nombre evento", browser.findElement(By.id("nombreField")).getText());
        assertEquals("Algun lugar", browser.findElement(By.id("lugarField")).getText());
    }
}
