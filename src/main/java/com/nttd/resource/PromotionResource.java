package com.nttd.resource;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.nttd.dto.PromotionDto;
import com.nttd.dto.ResponseDto;
import com.nttd.service.PromotionService;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/promotion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PromotionResource {

    @Inject
    PromotionService promotionService;

    @POST
    @Operation(summary = "Registrar la promoción", description = "Registrar la promoción")
    public Uni<ResponseDto> add(PromotionDto promotionDto) {
        return promotionService.add(promotionDto);
    }

    @GET
    @Operation(summary = "Obtener la lista de promociones", description = "Obtener la lista de promociones")
    public Uni<ResponseDto> listAll() {
        return promotionService.listAll();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener promoción", description = "Obtener promoción")
    public Uni<ResponseDto> getById(@PathParam("id") String id) {
        return promotionService.getById(id);
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar promoción", description = "Modificar promoción")
    public Uni<ResponseDto> update(@PathParam("id") String id, PromotionDto promotionDto) {
        return promotionService.edit(id, promotionDto);
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar los usuarios no vigentes en multicanal", description = "Permite eliminar los usuarios no vigentes en multicanal")
    public Uni<ResponseDto> delete(@PathParam("id") String id) {
        return promotionService.remove(id);
    }

}
