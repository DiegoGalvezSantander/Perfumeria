package cl.duoc.producto.client;

import cl.duoc.producto.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthClient {

    private final WebClient webClient;

    @Value("${app.auth.url}")
    private String authUrl;

    public AuthClient(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public String validarToken(String token) {
        ApiResponse<String> response = webClient.get()
            .uri(authUrl + "?token=" + token)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<String>>() {})
            .block();
        return (response != null && response.getCode() == 200) ? response.getData() : null;
    }
}
