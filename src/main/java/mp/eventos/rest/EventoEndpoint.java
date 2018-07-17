package mp.eventos.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import mp.eventos.dao.EventoDao;
import mp.eventos.model.Evento;

/**
 *
 * @author JasonDiazG
 */

@RequestScoped
@Path("/eventos")
@Produces("application/json")
@Consumes("application/json")
public class EventoEndpoint {

    @Inject
    EventoDao eventoService;

    @POST
    public Response create(Evento evento) {
        eventoService.create(evento);
        return Response.created(UriBuilder.fromResource(EventoEndpoint.class).path(String.valueOf(evento.getId())).build())
                .entity(evento)
                .build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final Long id) {
        Evento evento = eventoService.findById(id);
        if (evento == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(evento).build();
    }

    @GET
    public List<Evento> listAll(@QueryParam("start") final Integer startPosition,
            @QueryParam("max") final Integer maxResult) {
        final List<Evento> eventos = eventoService.listAll(startPosition, maxResult);
        return eventos;
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    public Response update(@PathParam("id") Long id, final Evento evento) {
        eventoService.update(evento);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") final Long id) {
        eventoService.deleteById(id);
        return Response.noContent().build();
    }

}
