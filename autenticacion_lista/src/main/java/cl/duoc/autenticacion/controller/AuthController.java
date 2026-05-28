package cl.duoc.autenticacion.controller;

import cl.duoc.autenticacion.dto.ApiResponse;
import cl.duoc.autenticacion.dto.AuthCredentialsDTO;
import cl.duoc.autenticacion.dto.AuthLoginDTO; // Importación del nuevo DTO para el Login
import cl.duoc.autenticacion.security.JwtUtil;
import cl.duoc.autenticacion.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    // Endpoint para registrar usuarios (Sigue usando AuthCredentialsDTO porque requiere el email)
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody AuthCredentialsDTO dto) {
        authService.registrar(dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getRol());
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario registrado correctamente", "OK"));
    }

    // Endpoint para iniciar sesión (Actualizado para usar AuthLoginDTO)
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody AuthLoginDTO dto) {
        String token = authService.login(dto.getUsername(), dto.getPassword());
        
        if (token != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Login exitoso", token));
        }
        return ResponseEntity.status(401).body(new ApiResponse<>(401, "Credenciales inválidas", null));
    }

    // Endpoint para validar el token desde otros microservicios
    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<String>> validateToken(@RequestParam String token) {
        if (authService.validateToken(token)) {
            String rol = jwtUtil.extractRol(token);
            return ResponseEntity.ok(new ApiResponse<>(200, "Token válido", rol));
        }
        return ResponseEntity.status(401).body(new ApiResponse<>(401, "Token inválido", null));
    }
}