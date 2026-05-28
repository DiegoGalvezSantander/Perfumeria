package cl.duoc.pago.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PAGOS")
@Data @NoArgsConstructor @AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @Column(name = "metodo_pago")
    private String metodoPago;

    @Column(name = "monto")
    private int monto;
}