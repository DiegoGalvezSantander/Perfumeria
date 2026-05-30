package cl.duoc.inventario.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProductoClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public void validarProducto(Long idProducto) {
        webClientBuilder.build()
                .get()             
                .uri("http://PRODUCT-SERVICE/api/v1/productos/" + idProducto) 
                .retrieve()
                .bodyToMono(Object.class) 
                .block(); 
    }
    
}