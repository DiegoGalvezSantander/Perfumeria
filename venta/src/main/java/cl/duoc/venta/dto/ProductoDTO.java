package cl.duoc.venta.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long idProducto;
    private String nomProducto;
    private double precio;
    private int stock;
}