package cl.duoc.inventario.client;

import cl.duoc.inventario.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProductoClient {

    private final WebClient webClient;

    @Value("${app.producto.url}")
    private String productoUrl;

    public ProductoClient(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public void validarProducto(Long idProducto) {
        try {
            ApiResponse<?> response = webClient.get()
                    .uri(productoUrl + "/" + idProducto)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {})
                    .block();

            if (response == null || response.getCode() != 200) {
                throw new IllegalArgumentException("El producto ID " + idProducto + " no existe en el catálogo.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: El producto ID " + idProducto + " no existe o el servicio de Productos está apagado.");
        }
    }
}