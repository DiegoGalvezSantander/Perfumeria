package cl.duoc.venta.client;

import cl.duoc.venta.dto.ApiResponse;
import cl.duoc.venta.dto.ProductoDTO;
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

    public ProductoDTO obtenerProducto(Long idProducto, String token) {
        try {
            ApiResponse<ProductoDTO> response = webClient.get()
                    .uri(productoUrl + "/" + idProducto)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<ProductoDTO>>() {})
                    .block();

            if (response == null || response.getCode() != 200 || response.getData() == null) {
                throw new IllegalArgumentException("Producto con ID " + idProducto + " no encontrado.");
            }

            return response.getData();

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con el servicio de Productos: " + e.getMessage());
        }
    }
}