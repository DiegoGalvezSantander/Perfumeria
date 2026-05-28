package cl.duoc.venta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Detalle_venta")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;


    @Column(name = "id_producto")
    private Long idProducto;


    @Column(name = "cantidad")
    private int cantidad;


    @Column(name = "precio_unitario")
    private double precioUnitario;


    @Column(name = "subtotal")
    private double subtotal;

    @Column(name = "estado_venta")
    private String estadoVenta;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    @JsonIgnore
    private Venta venta;
}