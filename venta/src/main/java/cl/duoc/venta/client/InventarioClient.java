package cl.duoc.venta.client;


import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class InventarioClient {

    private final WebClient webClient;
    private String inventarioUrl = "http://INVENTARIO-SERVICE/api/v1/inventario";


    public InventarioClient(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public void descontarStock(Long idProducto, int cantidad, String token) {
        
        Map<String, Object> request = new HashMap<>();
        request.put("idProducto", idProducto);
        request.put("cantidad", -cantidad); 
        
        webClient.post()
                .uri(inventarioUrl + "/movimiento")
                .header("Authorization", "Bearer " + token)
                .header("X-Internal-Secret", "clave-secreta-palace-123") 
                .bodyValue(request)
                .retrieve()
                
                .onStatus(HttpStatusCode::is4xxClientError, response -> 
                    Mono.error(new IllegalArgumentException("No queda stock suficiente para realizar la venta del producto ID: " + idProducto))
                )
                .bodyToMono(Void.class)
                .block();
    }
    
}