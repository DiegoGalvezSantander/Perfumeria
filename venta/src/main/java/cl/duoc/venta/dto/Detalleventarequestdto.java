package cl.duoc.venta.dto;

import lombok.Data;

@Data
public class Detalleventarequestdto {

    private Long idProducto;
    private int cantidad;
    private String estadoVenta;
    
}