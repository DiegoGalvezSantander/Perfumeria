package cl.duoc.usuario.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String nombreUsuario;

    @NotBlank(message = "El correo del usuario es obligatorio")
    @Email(message = "Debe ingresar un formato de correo electrónico válido")
    private String emailUsuario;

    private String fonoUsuario;

    @NotBlank(message = "El RUN del usuario es obligatorio")
    private String runUsuario;
    
}