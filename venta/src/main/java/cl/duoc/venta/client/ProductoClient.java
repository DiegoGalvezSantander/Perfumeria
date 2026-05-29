package cl.duoc.venta.client;

import cl.duoc.venta.dto.ApiResponse;
import cl.duoc.venta.dto.ProductoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProductoClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ProductoDTO obtenerProducto(Long idProducto, String token) {
        try {
            ApiResponse<ProductoDTO> response = webClientBuilder.build()
                    .get()
                    .uri("http://PRODUCT-SERVICE/api/v1/productos/" + idProducto)
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