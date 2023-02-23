package com.nttd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
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
            return promotionRepository.add(toPromotion(promotionDto))
                    .map(promotion -> new ResponseDto(201, message001, topromotionDto(promotion)))
                    .onFailure().recoverWithItem(error -> new ResponseDto(400, errorgeneric, error.getMessage()));
        } catch (Exception ex) {
            return Uni.createFrom().item(new ResponseDto(400, errorgeneric, ex.getMessage()));
        }
    }

    public Uni<ResponseDto> listAll() {
        try {
            return promotionRepository.listAll()
                    .map(promotions -> {
                        List<PromotionDto> lista = new ArrayList<>();

                        for (PromotionEntity promotion : promotions) {
                            PromotionDto promotionDto = new PromotionDto();

                            promotionDto.setIdPromocion(promotion.id.toString());
                            promotionDto.setNombreTienda(promotion.getStoreName());
                            promotionDto.setMonto(promotion.getAmount());
                            promotionDto.setPorcentajeDescuento(promotion.getDiscountRate());
                            promotionDto.setMontoOriginal(promotion.getOriginalAmount());
                            promotionDto.setDescripcion(promotion.getDescription());
                            promotionDto.setPeriodo(promotion.getPeriod());
                            promotionDto.setCantidad(promotion.getStock());
                            promotionDto.setImagenUrl(promotion.getImageUrl());
                            promotionDto.setEstado(promotion.getState());

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

    @Override
    public Uni<ResponseDto> getById(String id) {
        try {
            return promotionRepository.getById(id)
                    .map(promotion -> new ResponseDto(200, messagegeneric, topromotionDto(promotion)))
                    .onFailure().recoverWithItem(error -> new ResponseDto(400, errorgeneric, error.getMessage()));
        } catch (Exception ex) {
            return Uni.createFrom().item(new ResponseDto(400, errorgeneric, ex.getMessage()));
        }
    }

    @Override
    public Uni<ResponseDto> edit(String id, PromotionDto promotionDto) {
        promotionDto.setIdPromocion(id);
        return promotionRepository.edit(toPromotion(promotionDto))
                .map(promotion -> new ResponseDto(200, messagegeneric, topromotionDto(promotion)))
                .onFailure().recoverWithItem(error -> new ResponseDto(400, errorgeneric, error.getMessage()));
    }

    @Override
    public Uni<ResponseDto> remove(String id) {
        return promotionRepository.remove(id)
                .map(promotion -> new ResponseDto(200, messagegeneric, null))
                .onFailure().recoverWithItem(error -> new ResponseDto(400, errorgeneric, error.getMessage()));
    }

    PromotionEntity toPromotion(PromotionDto promotionDto) {
        PromotionEntity promotion = new PromotionEntity();
        promotion.id = new ObjectId(promotionDto.getIdPromocion());
        promotion.setStoreName(promotionDto.getNombreTienda());
        promotion.setAmount(promotionDto.getMonto());
        promotion.setDiscountRate(promotionDto.getPorcentajeDescuento());
        promotion.setOriginalAmount(promotionDto.getMontoOriginal());
        promotion.setDescription(promotionDto.getDescripcion());
        promotion.setPeriod(promotionDto.getPeriodo());
        promotion.setStock(promotionDto.getCantidad());
        promotion.setImageUrl(promotionDto.getImagenUrl());
        promotion.setState(promotionDto.getEstado());

        return promotion;
    }

    PromotionDto topromotionDto(PromotionEntity promotion) {
        PromotionDto promotionDto = new PromotionDto();

        promotionDto.setIdPromocion(promotion.id.toString());
        promotionDto.setNombreTienda(promotion.getStoreName());
        promotionDto.setMonto(promotion.getAmount());
        promotionDto.setPorcentajeDescuento(promotion.getDiscountRate());
        promotionDto.setMontoOriginal(promotion.getOriginalAmount());
        promotionDto.setDescripcion(promotion.getDescription());
        promotionDto.setPeriodo(promotion.getPeriod());
        promotionDto.setCantidad(promotion.getStock());
        promotionDto.setImagenUrl(promotion.getImageUrl());
        promotionDto.setEstado(promotion.getState());

        return promotionDto;
    }

}
