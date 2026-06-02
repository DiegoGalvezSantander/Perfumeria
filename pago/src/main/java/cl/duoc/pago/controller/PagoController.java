package cl.duoc.pago.controller;

import cl.duoc.pago.client.AuthClient;
import cl.duoc.pago.dto.ApiResponse;
import cl.duoc.pago.dto.Pagorequestdto;
import cl.duoc.pago.model.Pago;
import cl.duoc.pago.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid; 
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j 
@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService service;
    private final AuthClient authClient; 

    @PostMapping("/realizar")
    @Operation(summary = "Realizar un pago", description = "Permite a los usuarios realizar un pago proporcionando los detalles necesarios.")
    public ResponseEntity<ApiResponse<Pago>> pagar(
            @RequestHeader("Authorization") String tokenHeader, 
            @Valid @RequestBody Pagorequestdto dto) { 

        
        log.info("Procesando transacción de pago...");

        String token = tokenHeader.replace("Bearer ", "");
        authClient.validarToken(token);

        
        Pago resultado = service.procesarPago(dto);
        return ResponseEntity.ok(new ApiResponse<>(200, "Pago exitoso", resultado));
    }
    
}