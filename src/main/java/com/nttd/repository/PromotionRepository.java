package com.nttd.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.nttd.entity.PromotionEntity;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PromotionRepository {

    @ConfigProperty(name = "valor.flag.activo")
    String flag_activo;

    @ConfigProperty(name = "valor.flag.inactivo")
    String flag_inactivo;

    public Uni<PromotionEntity> add(PromotionEntity promotion) {
        promotion.setState(flag_activo);
        return promotion.persist();
    }

    public Uni<List<PromotionEntity>> listAll() {
        return PromotionEntity.listAll();
    }

    public Uni<PromotionEntity> getById(String id) {
        return PromotionEntity.findById(new ObjectId(id));
    }

    public Uni<PromotionEntity> edit(PromotionEntity promotion) {
        Uni<PromotionEntity> promo = PromotionEntity.findById(promotion.id);
        return promo.map(prom -> {
            prom.setAmount(promotion.getAmount());
            prom.setDescription(promotion.getDescription());
            prom.setDiscountRate(promotion.getDiscountRate());
            prom.setImageUrl(promotion.getImageUrl());
            prom.setOriginalAmount(promotion.getOriginalAmount());
            prom.setPeriod(promotion.getPeriod());
            prom.setStock(promotion.getStock());
            prom.setStoreName(promotion.getStoreName());
            prom.setTitle(promotion.getTitle());
            return prom;
        }).call(prom -> {
            return prom.persistOrUpdate();
        });
    }

    public Uni<PromotionEntity> remove(String id) {
        Uni<PromotionEntity> promotion = PromotionEntity.findById(new ObjectId(id));
        return promotion.map(promo -> {
            promo.setState(flag_inactivo);
            return promo;
        }).call(promo -> {
            return promo.persistOrUpdate();
        });
    }

}
