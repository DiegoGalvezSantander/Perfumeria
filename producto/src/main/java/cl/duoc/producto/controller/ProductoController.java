package cl.duoc.producto.controller;

import cl.duoc.producto.client.AuthClient;
import cl.duoc.producto.dto.ApiResponse;
import cl.duoc.producto.dto.ProductoRequestDTO;
import cl.duoc.producto.model.Producto;
import cl.duoc.producto.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    
    // CAMBIO 1: Inyectar el cliente de autenticación
    private final AuthClient authClient;

    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> crear(
            // CAMBIO 2: Exigir el header de Authorization
            @RequestHeader("Authorization") String tokenHeader, 
            @Valid @RequestBody ProductoRequestDTO dto) {


        String token = tokenHeader.replace("Bearer ", "");
        
    
        String rolUsuario = authClient.validarToken(token);
        
    
        if (rolUsuario == null || !rolUsuario.equals("ADMIN")) {
             throw new SecurityException("No tienes permisos para crear productos");
         }

        Producto nuevo = productoService.guardarProducto(dto);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Producto creado exitosamente", nuevo));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Catálogo recuperado", productoService.obtenerTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> obtenerPorId(@PathVariable @NonNull Long id) {
        Producto p = productoService.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Producto encontrado", p));
    }
}