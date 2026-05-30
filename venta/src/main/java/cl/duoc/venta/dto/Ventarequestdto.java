package cl.duoc.venta.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class Ventarequestdto {

    @NotNull(message = "El ID de usuario no puede ser nulo")
    private Long idUsuario;

    private String estado;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    private double total;

    @NotEmpty(message = "La venta debe contener al menos un producto en los detalles")
    private List<Detalleventarequestdto> detalles;
    
}