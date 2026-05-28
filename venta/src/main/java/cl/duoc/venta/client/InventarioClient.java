package cl.duoc.venta.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class InventarioClient {

    private final WebClient webClient;

    // Asegúrate de que esta variable coincida con la que tienes en tu application.properties
    @Value("${app.inventario.url:http://localhost:8084/api/v1/inventario}")
    private String inventarioUrl;

    public InventarioClient(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public void descontarStock(Long idProducto, int cantidad, String token) {
        
        // 1. Preparamos los datos a enviar a Inventario. 
        // (Nota: Si ya tenías un DTO creado para esto, puedes seguir usándolo en lugar de este Map)
        Map<String, Object> request = new HashMap<>();
        request.put("idProducto", idProducto);
        request.put("cantidad", cantidad);
        
        // 2. Realizamos la llamada HTTP agregando ambas cabeceras
        webClient.post()
                .uri(inventarioUrl + "/movimiento")
                
                // Cabecera estándar con el token del cliente que está comprando
                .header("Authorization", "Bearer " + token)
                
                // LA CABECERA MÁGICA: Nuestro "pase VIP" para que Inventario nos deje pasar
                .header("X-Internal-Secret", "clave-secreta-palace-123") 
                
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}