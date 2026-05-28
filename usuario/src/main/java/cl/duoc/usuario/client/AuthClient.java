package cl.duoc.usuario.client;

import cl.duoc.usuario.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthClient {

    private final WebClient webClient;

    @Value("${app.auth.url}")
    private String authUrl;

    public AuthClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public void validarToken(String token) {
        try {
            ApiResponse<?> response = webClient.get()
                    .uri(authUrl + "?token=" + token)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {})
                    .block();

            if (response == null || response.getCode() != 200) {
                throw new SecurityException("Acceso denegado: Token inválido o expirado");
            }
        } catch (SecurityException se) {
            throw se;
        } catch (Exception e) {
            throw new SecurityException("Error de red: El servicio de Autenticación (8081) no responde.");
        }
    }
}