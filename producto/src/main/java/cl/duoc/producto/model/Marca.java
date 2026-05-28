package cl.duoc.producto.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Marcas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marca")
    private Long idMarca;

    private String nombreMarca;
}
