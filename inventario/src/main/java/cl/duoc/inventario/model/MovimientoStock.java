package cl.duoc.inventario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MOVIMIENTO_STOCK")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MovimientoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    @Column(name = "id_producto")
    private Long idProducto;

    private int cantidad;
    
}