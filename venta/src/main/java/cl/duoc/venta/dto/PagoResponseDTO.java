package cl.duoc.venta.dto;

import lombok.Data;

@Data
public class PagoResponseDTO {
    private Long idPago;
    private String metodoPago;
    private int monto;
}