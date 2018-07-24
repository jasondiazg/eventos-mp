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
import mp.eventos.dao.AsistenteEventoDao;
import mp.eventos.model.AsistenteEvento;

/**
 *
 * @author JasonDiazG
 */
@RequestScoped
@Path("/eventos/asistentes")
@Produces("application/json")
@Consumes("application/json")
public class AsistenteEventoEndpoint {

    @Inject
    AsistenteEventoDao service;

    @POST
    public Response create(AsistenteEvento entity) {
        service.create(entity);
        return Response.created(UriBuilder.fromResource(PonenteEndpoint.class).path(String.valueOf(entity.getId())).build())
                .entity(entity)
                .build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final Long id) {
        AsistenteEvento entity = service.findById(id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @GET
    @Path("/evento")
    public List<AsistenteEvento> listAllByIdEvento(@QueryParam("idEvento") final Integer idEvento) {
        final List<AsistenteEvento> entities = service.listAllByEvento(idEvento, null, null);
        return entities;
    }

    @GET
    @Path("/asistente")
    public List<AsistenteEvento> listAllByIdAsistente(@QueryParam("idAsistente") final Integer idAsistente) {
        final List<AsistenteEvento> entities = service.listAllByAsistente(idAsistente, null, null);
        return entities;
    }

    @GET
    @Path("/evento/{start:[0-9][0-9]*}/{max:[0-9][0-9]*}")
    public List<AsistenteEvento> listAllByIdEvento(@QueryParam("idEvento") final Integer idEvento,
            @PathParam("start") final Integer startPosition,
            @PathParam("max") final Integer maxResult) {
        final List<AsistenteEvento> entities = service.listAllByEvento(idEvento, startPosition, maxResult);
        return entities;
    }

    @GET
    @Path("/asistente/{start:[0-9][0-9]*}/{max:[0-9][0-9]*}")
    public List<AsistenteEvento> listAllByIdAsistente(@QueryParam("idAsistente") final Integer idAsistente,
            @PathParam("start") final Integer startPosition,
            @PathParam("max") final Integer maxResult) {
        final List<AsistenteEvento> entities = service.listAllByAsistente(idAsistente, startPosition, maxResult);
        return entities;
    }

    @GET
    public List<AsistenteEvento> listAll(@QueryParam("start") final Integer startPosition,
            @QueryParam("max") final Integer maxResult) {
        final List<AsistenteEvento> entities = service.listAll(startPosition, maxResult);
        return entities;
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    public Response update(@PathParam("id") Long id, final AsistenteEvento entity) {
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
