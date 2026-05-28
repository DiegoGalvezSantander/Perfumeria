package cl.duoc.producto.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Tipo_fragancias")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TipoFragancia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fragancia")
    private Long idFragancia;

    private String nombreFragancia;
}
