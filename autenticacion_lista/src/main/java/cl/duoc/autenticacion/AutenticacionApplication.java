package cl.duoc.autenticacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AutenticacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutenticacionApplication.class, args);
    }

}