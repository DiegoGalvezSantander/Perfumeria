package cl.duoc.venta.controller;

import cl.duoc.venta.client.AuthClient;
import cl.duoc.venta.dto.ApiResponse;
import cl.duoc.venta.dto.Ventarequestdto;
import cl.duoc.venta.model.Venta;
import cl.duoc.venta.service.VentaService;
import jakarta.validation.Valid; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    private final AuthClient authClient;

    
    @PostMapping
    public ResponseEntity<ApiResponse<Venta>> crearVenta(
            @RequestHeader("Authorization") String tokenHeader,
            @Valid @RequestBody Ventarequestdto dto) { 

        String token = tokenHeader.replace("Bearer ", "");
        authClient.validarToken(token); 

        Venta nueva = ventaService.crearVenta(dto, token); 
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Venta Exitosa", nueva));
    }

    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<List<Venta>>> listarVentasPorUsuario(@PathVariable Long idUsuario) {
        List<Venta> historial = ventaService.obtenerVentasPorUsuario(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(200, "Historial de ventas recuperado", historial));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Venta>> obtenerVentaPorId(@PathVariable @NonNull Long id) {
        Venta venta = ventaService.obtenerVentaPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Venta encontrada exitosamente", venta));
    }
}