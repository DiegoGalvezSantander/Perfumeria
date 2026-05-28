package cl.duoc.autenticacion.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Roles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "nombre_rol", nullable = false, unique = true)
    private String nombreRol;
}