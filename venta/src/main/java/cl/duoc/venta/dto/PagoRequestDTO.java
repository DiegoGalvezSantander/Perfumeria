package cl.duoc.venta.dto;

import lombok.Data;

@Data
public class PagoRequestDTO {
    private String metodoPago;
    private int monto;
}