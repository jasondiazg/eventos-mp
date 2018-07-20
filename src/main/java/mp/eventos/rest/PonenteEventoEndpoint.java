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
import mp.eventos.dao.PonenteEventoDao;
import mp.eventos.model.PonenteEvento;

/**
 *
 * @author JasonDiazG
 */
@RequestScoped
@Path("/eventos/ponentes")
@Produces("application/json")
@Consumes("application/json")
public class PonenteEventoEndpoint {

    @Inject
    PonenteEventoDao service;

    @POST
    public Response create(PonenteEvento entity) {
        service.create(entity);
        return Response.created(UriBuilder.fromResource(PonenteEndpoint.class).path(String.valueOf(entity.getId())).build())
                .entity(entity)
                .build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final Long id) {
        PonenteEvento entity = service.findById(id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @GET
    @Path("/evento")
    public List<PonenteEvento> listAllByIdEvento(@QueryParam("idEvento") final Integer idEvento,
            @QueryParam("start") final Integer startPosition,
            @QueryParam("max") final Integer maxResult) {
        final List<PonenteEvento> entities = service.listAllByEvento(idEvento, startPosition, maxResult);
        return entities;
    }

    @GET
    @Path("/ponente")
    public List<PonenteEvento> listAllByIdPonente(@QueryParam("idPonente") final Integer idPonente,
            @QueryParam("start") final Integer startPosition,
            @QueryParam("max") final Integer maxResult) {
        final List<PonenteEvento> entities = service.listAllByPonente(idPonente, startPosition, maxResult);
        return entities;
    }

    @GET
    public List<PonenteEvento> listAll(@QueryParam("start") final Integer startPosition,
            @QueryParam("max") final Integer maxResult) {
        final List<PonenteEvento> entities = service.listAll(startPosition, maxResult);
        return entities;
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    public Response update(@PathParam("id") Long id, final PonenteEvento entity) {
        service.update(entity);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") final Long id) {
        service.deleteById(id);
        return Response.noContent().build();
    }

}
