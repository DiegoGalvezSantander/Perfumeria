package cl.duoc.inventario.client;

import cl.duoc.inventario.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthClient {


    @Autowired
    private WebClient.Builder webClientBuilder;

    public String validarToken(String token) {
        try {
            
            ApiResponse<String> response = webClientBuilder.build()
                .get()
                .uri("http://AUTH-SERVICE/api/v1/auth/validate?token=" + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<String>>() {})
                .block();
                
            return (response != null && response.getCode() == 200) ? response.getData() : null;
        } catch (Exception e) {
            // Retorna null si el microservicio de autenticación está apagado o falla la red
            return null;
        }
    }
    
}