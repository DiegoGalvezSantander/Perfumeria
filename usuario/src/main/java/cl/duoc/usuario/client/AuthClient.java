package cl.duoc.usuario.client;

import cl.duoc.usuario.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                    .onStatus(status -> status.is4xxClientError(), clientResponse -> 
                        clientResponse.bodyToMono(ApiResponse.class)
                            .flatMap(errorBody -> Mono.error(new SecurityException(errorBody.getMessage())))
                    )
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<String>>() {})
                    .block();

            if (response == null || response.getCode() != 200) {
                throw new SecurityException("Acceso denegado: Token inválido o expirado");
            }
            
            return response.getData();

        } catch (SecurityException se) {
            throw se;
        } catch (Exception e) {
            throw new SecurityException("Error de red: El servicio de Autenticación no responde.");
        }
    }
}