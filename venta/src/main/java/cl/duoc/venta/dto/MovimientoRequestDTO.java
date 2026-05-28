package cl.duoc.venta.dto;

import lombok.Data;

@Data
public class MovimientoRequestDTO {
    private Long idProducto;
    private int cantidad;
}