package cl.duoc.producto.controller;

import cl.duoc.producto.client.AuthClient;
import cl.duoc.producto.dto.ApiResponse;
import cl.duoc.producto.dto.ProductoRequestDTO;
import cl.duoc.producto.model.Producto;
import cl.duoc.producto.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
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
    
    
    private final AuthClient authClient;

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Permite a los administradores crear un nuevo producto en el catálogo. Requiere un token de autenticación válido con rol ADMIN.")
    public ResponseEntity<ApiResponse<Producto>> crear(
            
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
    @Operation(summary = "Listar productos", description = "Recupera una lista de todos los productos disponibles en el catálogo. No requiere autenticación.")
    public ResponseEntity<ApiResponse<List<Producto>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Catálogo recuperado", productoService.obtenerTodos()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Recupera un producto específico por su ID. No requiere autenticación.")
    public ResponseEntity<ApiResponse<Producto>> obtenerPorId(@PathVariable @NonNull Long id) {
        Producto p = productoService.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Producto encontrado", p));
    }
}