package cl.duoc.inventario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "INVENTARIO")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "stock_producto")
    private int stockProducto;
    
}