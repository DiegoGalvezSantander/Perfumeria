package cl.duoc.venta.client;

import cl.duoc.venta.dto.ApiResponse;
import cl.duoc.venta.dto.PagoRequestDTO;
import cl.duoc.venta.dto.PagoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PagoClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public PagoResponseDTO procesarPago(String metodo, int monto, String token) {
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setMetodoPago(metodo);
        dto.setMonto(monto);

        ApiResponse<PagoResponseDTO> response = webClientBuilder.build()
                .post()
                .uri("http://PAYMENT-SERVICE/api/v1/pagos/realizar")
                .header("Authorization", "Bearer " + token)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PagoResponseDTO>>() {})
                .block();

        if (response == null || response.getCode() != 200) {
            throw new RuntimeException("Error en microservicio de Pagos.");
        }
        return response.getData();
    }
}