package cl.duoc.inventario.controller;

import cl.duoc.inventario.client.AuthClient;
import cl.duoc.inventario.dto.ApiResponse;
import cl.duoc.inventario.dto.MovimientoRequestDTO;
import cl.duoc.inventario.service.InventarioService;
import jakarta.validation.Valid; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;
    private final AuthClient authClient;

    @PostMapping("/movimiento")
    public ResponseEntity<ApiResponse<String>> crearMovimiento(
            @RequestHeader(value = "Authorization", required = false) String tokenHeader,
            @RequestHeader(value = "X-Internal-Secret", required = false) String internalSecret,
            @Valid @RequestBody MovimientoRequestDTO dto) { 

        // 1. Barrera VIP: Si trae la contraseña interna de microservicios, pasa sin revisar el rol
        if ("clave-secreta-palace-123".equals(internalSecret)) {
            // Viene de Ventas, confiamos en él
        } else {
            // 2. Si no es Ventas, es alguien de afuera. Exigimos Token y rol ADMIN.
            if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
                throw new SecurityException("Falta la cabecera de Authorization");
            }
            
            String token = tokenHeader.replace("Bearer ", "");
            String rolUsuario = authClient.validarToken(token);

            if (rolUsuario == null || !rolUsuario.equalsIgnoreCase("ADMIN")) {
                throw new SecurityException("Acceso denegado: Solo los administradores pueden alterar el stock manualmente");
            }
        }

        // 3. Continuar con la actualización
        String mensaje = inventarioService.actualizarStock(dto);
        return ResponseEntity.ok(new ApiResponse<>(200, "Operación exitosa", mensaje));
    }
}