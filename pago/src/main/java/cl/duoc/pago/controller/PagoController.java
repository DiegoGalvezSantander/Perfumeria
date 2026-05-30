package cl.duoc.pago.controller;

import cl.duoc.pago.client.AuthClient;
import cl.duoc.pago.dto.ApiResponse;
import cl.duoc.pago.dto.Pagorequestdto;
import cl.duoc.pago.model.Pago;
import cl.duoc.pago.service.PagoService;
import jakarta.validation.Valid; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService service;
    private final AuthClient authClient; 

    @PostMapping("/realizar")
    public ResponseEntity<ApiResponse<Pago>> pagar(
            @RequestHeader("Authorization") String tokenHeader, 
            @Valid @RequestBody Pagorequestdto dto) { 

        
        String token = tokenHeader.replace("Bearer ", "");
        authClient.validarToken(token);

        
        Pago resultado = service.procesarPago(dto);
        return ResponseEntity.ok(new ApiResponse<>(200, "Pago exitoso", resultado));
    }
    
}