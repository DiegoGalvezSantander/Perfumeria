package cl.duoc.pago.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Pagorequestdto {
    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    @Min(value = 1, message = "El monto a pagar debe ser mayor a 0")
    private int monto;
    
}