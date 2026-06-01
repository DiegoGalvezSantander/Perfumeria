package cl.duoc.usuario.controller;

import cl.duoc.usuario.client.AuthClient;
import cl.duoc.usuario.dto.ApiResponse;
import cl.duoc.usuario.dto.UsuarioRequestDTO;
import cl.duoc.usuario.model.Usuario;
import cl.duoc.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthClient authClient;

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario en el sistema. Requiere token de autenticación válido.")
    public ResponseEntity<ApiResponse<Usuario>> crearUsuario(
            @RequestHeader("Authorization") String tokenHeader,
            @Valid @RequestBody UsuarioRequestDTO dto) { 

        String token = tokenHeader.replace("Bearer ", "");
        authClient.validarToken(token);

        Usuario nuevo = usuarioService.guardarUsuario(dto);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Usuario guardado con éxito", nuevo));
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Recupera una lista de todos los usuarios registrados en el sistema. Requiere token de autenticación válido.")
    public ResponseEntity<ApiResponse<List<Usuario>>> listarTodos(
            @RequestHeader("Authorization") String tokenHeader) {

        String token = tokenHeader.replace("Bearer ", "");
        authClient.validarToken(token);

        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuarios recuperados", usuarios));
    }
    
}