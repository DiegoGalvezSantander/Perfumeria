package cl.duoc.venta.client;

import cl.duoc.venta.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public String validarToken(String token) {
        ApiResponse<String> response = webClientBuilder.build()
                .get()
                .uri("http://AUTH-SERVICE/api/v1/auth/validate?token=" + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<String>>() {})
                .block();

        if (response == null || response.getCode() != 200) {
            throw new RuntimeException("Acceso denegado: Token inválido");
        }

        return response.getData();
    }
    
}