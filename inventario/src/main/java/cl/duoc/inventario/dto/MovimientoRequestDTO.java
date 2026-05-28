package cl.duoc.inventario.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovimientoRequestDTO {

    @NotNull(message = "Debe especificar el ID del producto")
    private Long idProducto;
    
    private int cantidad;
}