package cl.duoc.autenticacion.controller;

import cl.duoc.autenticacion.dto.ApiResponse;
import cl.duoc.autenticacion.dto.AuthCredentialsDTO;
import cl.duoc.autenticacion.dto.AuthLoginDTO; 
import cl.duoc.autenticacion.security.JwtUtil;
import cl.duoc.autenticacion.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
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

 
    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo usuario en su auntenticación", description = "Permite registrar un nuevo usuario con su nombre de usuario, correo electrónico, contraseña y rol.")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody AuthCredentialsDTO dto) {
        authService.registrar(dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getRol());
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario registrado correctamente", "OK"));
    }

    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión en su autenticación", description = "Permite a un usuario iniciar sesión proporcionando su nombre de usuario y contraseña. Devuelve un token JWT si las credenciales son válidas.")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody AuthLoginDTO dto) {
        String token = authService.login(dto.getUsername(), dto.getPassword());
        
        if (token != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Login exitoso", token));
        }
        return ResponseEntity.status(401).body(new ApiResponse<>(401, "Credenciales inválidas", null));
    }

    
    @GetMapping("/validate")
    @Operation(summary = "Validar token de autenticación", description = "Permite validar un token JWT proporcionado. Devuelve el rol del usuario si el token es válido.")
    public ResponseEntity<ApiResponse<String>> validateToken(@RequestParam String token) {
        if (authService.validateToken(token)) {
            String rol = jwtUtil.extractRol(token);
            return ResponseEntity.ok(new ApiResponse<>(200, "Token válido", rol));
        }
        return ResponseEntity.status(401).body(new ApiResponse<>(401, "Token inválido", null));
    }
}