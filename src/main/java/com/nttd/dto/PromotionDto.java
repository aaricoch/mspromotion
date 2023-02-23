package com.nttd.dto;

import lombok.Data;

@Data
public class PromotionDto {
    private String idPromocion;
    private String titulo;
    private String nombreTienda;
    private Double monto;
    private String porcentajeDescuento;
    private Double montoOriginal;
    private String descripcion;
    private String periodo;
    private long cantidad;
    private String imagenUrl;
    private String estado;
}
