package cl.duoc.producto.controller;

import cl.duoc.producto.client.AuthClient;
import cl.duoc.producto.dto.ApiResponse;
import cl.duoc.producto.dto.ProductoRequestDTO;
import cl.duoc.producto.model.Producto;
import cl.duoc.producto.service.ProductoService;
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
        
        log.info("Iniciando creación de un nuevo producto en el catálogo...");

        String token = tokenHeader.replace("Bearer ", "");
        String rolUsuario = authClient.validarToken(token);
        
        if (rolUsuario == null || !rolUsuario.equals("ADMIN")) {
             throw new SecurityException("No tienes permisos para crear productos");
        }

        Producto nuevo = productoService.guardarProducto(dto);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Producto creado exitosamente", nuevo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Permite a los administradores modificar los datos de un producto existente. Requiere token con rol ADMIN.")
    public ResponseEntity<ApiResponse<Producto>> actualizar(
            @PathVariable @NonNull Long id,
            @RequestHeader("Authorization") String tokenHeader,
            @Valid @RequestBody ProductoRequestDTO dto) {

        log.info("Iniciando actualización del producto con ID: {}", id);

        String token = tokenHeader.replace("Bearer ", "");
        String rolUsuario = authClient.validarToken(token);

        if (rolUsuario == null || !rolUsuario.equals("ADMIN")) {
            throw new SecurityException("No tienes permisos para actualizar productos");
        }

        Producto actualizado = productoService.actualizarProducto(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(200, "Producto actualizado exitosamente", actualizado));
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


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del catálogo. Requiere rol ADMIN.")
    public ResponseEntity<ApiResponse<String>> eliminar(
            @PathVariable @NonNull Long id,
            @RequestHeader("Authorization") String tokenHeader) {
            
        log.info("Iniciando eliminación del producto con ID: {}", id);
            
        String token = tokenHeader.replace("Bearer ", "");
        String rolUsuario = authClient.validarToken(token);
        
        if (rolUsuario == null || !rolUsuario.equals("ADMIN")) {
            throw new SecurityException("No tienes permisos para eliminar productos");
        }
        
        productoService.eliminarProducto(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Producto eliminado correctamente", "OK"));
    }
}