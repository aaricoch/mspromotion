package com.nttd.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.nttd.dto.PromotionDto;
import com.nttd.dto.ResponseDto;
import com.nttd.entity.PromotionEntity;
import com.nttd.repository.PromotionRepository;
import com.nttd.service.PromotionService;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PromotionServiceImpl implements PromotionService {

    @Inject
    PromotionRepository promotionRepository;

    @ConfigProperty(name = "mensaje.general")
    String messagegeneric;

    @ConfigProperty(name = "message.001")
    String message001;

    @ConfigProperty(name = "error.generic")
    String errorgeneric;

    public Uni<ResponseDto> add(PromotionDto promotionDto) {
        try {
            return promotionRepository.add(toAudit(promotionDto))
                    .map(audit -> new ResponseDto(201, message001, topromotionDto(audit)))
                    .onFailure().recoverWithItem(error -> new ResponseDto(400, errorgeneric, error.getMessage()));
        } catch (Exception ex) {
            return Uni.createFrom().item(new ResponseDto(400, errorgeneric, ex.getMessage()));
        }
    }

    public Uni<ResponseDto> listAll() {
        try {
            return promotionRepository.listAll()
                    .map(audits -> {
                        List<PromotionDto> lista = new ArrayList<>();

                        for (PromotionEntity audit : audits) {
                            PromotionDto promotionDto = new PromotionDto();

                            promotionDto.setIdAuditoria(audit.id.toString());
                            promotionDto.setAplicacion(audit.getApplication());
                            promotionDto.setUsuarioAplicacion(audit.getApplicationUser());
                            promotionDto.setUsuarioSesion(audit.getSessionUser());
                            promotionDto.setCodigoTransaccion(audit.getTransactionCode());
                            promotionDto.setFechaTransaccion(audit.getTransactionDate());
                            promotionDto.setMensaje(audit.getMessage());
                            promotionDto.setRequest(audit.getRequest());
                            promotionDto.setResponse(audit.getResponse());

                            lista.add(promotionDto);
                        }

                        return lista;
                    })
                    .map(promotionDto -> new ResponseDto(200, messagegeneric, promotionDto))
                    .onFailure().recoverWithItem(error -> new ResponseDto(400, errorgeneric,
                            error.getMessage()));
        } catch (Exception ex) {
            return Uni.createFrom().item(new ResponseDto(400, errorgeneric, ex.getMessage()));
        }
    }

    PromotionEntity toAudit(PromotionDto promotionDto) {
        PromotionEntity audit = new PromotionEntity();

        audit.setApplication(promotionDto.getAplicacion());
        audit.setApplicationUser(promotionDto.getUsuarioAplicacion());
        audit.setSessionUser(promotionDto.getUsuarioSesion());
        audit.setTransactionCode(String.valueOf(System.currentTimeMillis()));
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss z");
        audit.setTransactionDate(dateFormat.format(new Date()));
        audit.setMessage(promotionDto.getMensaje());
        audit.setRequest(promotionDto.getRequest());
        audit.setResponse(promotionDto.getResponse());

        return audit;
    }

    PromotionDto topromotionDto(PromotionEntity audit) {
        PromotionDto promotionDto = new PromotionDto();

        promotionDto.setIdAuditoria(audit.id.toString());
        promotionDto.setAplicacion(audit.getApplication());
        promotionDto.setUsuarioAplicacion(audit.getApplicationUser());
        promotionDto.setUsuarioSesion(audit.getSessionUser());
        promotionDto.setCodigoTransaccion(audit.getTransactionCode());
        promotionDto.setFechaTransaccion(audit.getTransactionDate());
        promotionDto.setMensaje(audit.getMessage());
        promotionDto.setRequest(audit.getRequest());
        promotionDto.setResponse(audit.getResponse());

        return promotionDto;
    }

}
