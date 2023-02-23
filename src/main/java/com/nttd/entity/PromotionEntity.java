package com.nttd.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MongoEntity(collection = "promociones")
public class PromotionEntity extends ReactivePanacheMongoEntity {
    private String title;
    private String storeName;
    private Double amount;
    private String discountRate;
    private Double originalAmount;
    private String description;
    private String period;
    private long stock;
    private String imageUrl;
    private String state;
}
