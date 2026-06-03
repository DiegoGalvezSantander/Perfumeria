package cl.duoc.inventario.controller;

import cl.duoc.inventario.client.AuthClient;
import cl.duoc.inventario.dto.ApiResponse;
import cl.duoc.inventario.dto.MovimientoRequestDTO;
import cl.duoc.inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid; 
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j 
@RestController
@RequestMapping("/api/v1/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;
    private final AuthClient authClient;

    @PostMapping("/movimiento")
    @Operation(summary = "Crear un movimiento de inventario", description = "Permite crear un movimiento de inventario para actualizar el stock de un producto. Solo los usuarios con rol ADMIN pueden realizar esta operación, a menos que se proporcione una clave secreta interna válida.")
    public ResponseEntity<ApiResponse<String>> crearMovimiento(
            @RequestHeader(value = "Authorization", required = false) String tokenHeader,
            @RequestHeader(value = "X-Internal-Secret", required = false) String internalSecret,
            @Valid @RequestBody MovimientoRequestDTO dto) { 

        log.info("Verificando y procesando movimiento de stock en bodega...");

        if ("clave-secreta-palace-123".equals(internalSecret)) {
        
        } else {           
            if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
                throw new SecurityException("Falta la cabecera de Authorization");
            }
            
            String token = tokenHeader.replace("Bearer ", "");
            String rolUsuario = authClient.validarToken(token);

            if (rolUsuario == null || !rolUsuario.equalsIgnoreCase("ADMIN")) {
                throw new SecurityException("Acceso denegado: Solo los administradores pueden alterar el stock manualmente");
            }
        }

        String mensaje = inventarioService.actualizarStock(dto);
        return ResponseEntity.ok(new ApiResponse<>(200, "Operación exitosa", mensaje));
    }


    @GetMapping("/stock/{idProducto}")
    @Operation(summary = "Consultar stock de un producto", description = "Permite verificar cuántas unidades quedan de un producto en bodega.")
    public ResponseEntity<ApiResponse<Integer>> consultarStock(
            @RequestHeader(value = "Authorization", required = false) String tokenHeader,
            @RequestHeader(value = "X-Internal-Secret", required = false) String internalSecret,
            @PathVariable Long idProducto) {

        log.info("Consultando stock actual para el producto ID: {}", idProducto);

        if (!"clave-secreta-palace-123".equals(internalSecret)) {
            if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
                throw new SecurityException("Falta la cabecera de Authorization");
            }
            String token = tokenHeader.replace("Bearer ", "");
            authClient.validarToken(token); 
        }

        Integer stockActual = inventarioService.consultarStock(idProducto);
        return ResponseEntity.ok(new ApiResponse<>(200, "Stock consultado exitosamente", stockActual));
    }
}