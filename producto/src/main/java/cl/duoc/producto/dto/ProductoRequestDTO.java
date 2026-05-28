package cl.duoc.producto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nomProducto;

    private String descripcion;

    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private double precio;

    @NotBlank(message = "Debe indicar el nombre de la marca")
    private String nombreMarca;

    @NotBlank(message = "Debe indicar el nombre del tipo de fragancia")
    private String nombreFragancia;
}