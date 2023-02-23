package com.nttd.repository;

import java.util.List;

import com.nttd.entity.PromotionEntity;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PromotionRepository {

    public Uni<PromotionEntity> add(PromotionEntity promotion) {
        return promotion.persist();
    }

    public Uni<List<PromotionEntity>> listAll() {
        return PromotionEntity.listAll();
    }

}
