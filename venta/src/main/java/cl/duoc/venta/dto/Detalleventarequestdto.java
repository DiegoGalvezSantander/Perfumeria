package cl.duoc.venta.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Detalleventarequestdto {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long idProducto;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;
    
}