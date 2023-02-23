package com.nttd.resource;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.nttd.dto.PromotionDto;
import com.nttd.dto.ResponseDto;
import com.nttd.service.PromotionService;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/promotion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PromotionResource {

    @Inject
    PromotionService auditService;

    @POST
    @Operation(summary = "Registrar la promoción", description = "Registrar la promoción")
    public Uni<ResponseDto> add(PromotionDto promotionDto) {
        return auditService.add(promotionDto);
    }

    @GET
    @Operation(summary = "Obtener la lista de promociones", description = "Obtener la lista de promociones")
    public Uni<ResponseDto> listAll() {
        return auditService.listAll();
    }

}
