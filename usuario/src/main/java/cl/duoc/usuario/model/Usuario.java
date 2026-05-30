package cl.duoc.usuario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USUARIO")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "email_usuario")
    private String emailUsuario;

    @Column(name = "fono_usuario")
    private String fonoUsuario;

    @Column(name = "run_usuario")
    private String runUsuario;
}
