package com.nttd.service;


import com.nttd.dto.PromotionDto;
import com.nttd.dto.ResponseDto;

import io.smallrye.mutiny.Uni;

public interface PromotionService {

    Uni<ResponseDto> add(PromotionDto promotionDto);

    Uni<ResponseDto> listAll();

}
