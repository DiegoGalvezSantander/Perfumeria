package cl.duoc.venta.controller;

import cl.duoc.venta.client.AuthClient;
import cl.duoc.venta.dto.ApiResponse;
import cl.duoc.venta.dto.Ventarequestdto;
import cl.duoc.venta.model.Venta;
import cl.duoc.venta.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid; 
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; 
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j 
@RestController
@RequestMapping("/api/v1/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    private final AuthClient authClient;

    
    @PostMapping
    @Operation(summary = "Crear una nueva venta", description = "Permite crear una nueva venta con los detalles proporcionados en el cuerpo de la solicitud. Requiere un token de autenticación válido en el encabezado.")  
    public ResponseEntity<ApiResponse<Venta>> crearVenta(
            @RequestHeader("Authorization") String tokenHeader,
            @Valid @RequestBody Ventarequestdto dto) { 

        log.info("Iniciando orquestación: Creando nueva venta...");

        String token = tokenHeader.replace("Bearer ", "");
        authClient.validarToken(token); 

        Venta nueva = ventaService.crearVenta(dto, token); 
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Venta Exitosa", nueva));
    }

    
    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Listar ventas por usuario", description = "Recupera el historial de ventas asociadas a un usuario específico identificado por su ID. Exclusivo para rol ADMIN.")    
    public ResponseEntity<ApiResponse<List<Venta>>> listarVentasPorUsuario(
            @RequestHeader("Authorization") String tokenHeader,
            @PathVariable Long idUsuario) {
            
        String token = tokenHeader.replace("Bearer ", "");
        String rolUsuario = authClient.validarToken(token);
        
        if (rolUsuario == null || !rolUsuario.equals("ADMIN")) {
             throw new SecurityException("No tienes permisos para ver el historial de este usuario");
        }

        List<Venta> historial = ventaService.obtenerVentasPorUsuario(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(200, "Historial de ventas recuperado", historial));
    }

    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener venta por ID", description = "Recupera los detalles de una venta específica utilizando su ID único. Exclusivo para rol ADMIN.") 
    public ResponseEntity<ApiResponse<Venta>> obtenerVentaPorId(
            @RequestHeader("Authorization") String tokenHeader,
            @PathVariable @NonNull Long id) {
        
        String token = tokenHeader.replace("Bearer ", "");
        String rolUsuario = authClient.validarToken(token);
        
        if (rolUsuario == null || !rolUsuario.equals("ADMIN")) {
             throw new SecurityException("No tienes permisos para ver esta venta");
        }
        
        Venta venta = ventaService.obtenerVentaPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Venta encontrada exitosamente", venta));
    }
}