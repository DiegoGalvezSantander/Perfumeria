package cl.duoc.producto.controller;

import cl.duoc.producto.client.AuthClient;
import cl.duoc.producto.model.Producto;
import cl.duoc.producto.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductoControllerTest {

    private MockMvc mockMvc;
    private ProductoService productoService;
    private AuthClient authClient;

    @BeforeEach
    void setup() {
        productoService = Mockito.mock(ProductoService.class);
        authClient = Mockito.mock(AuthClient.class);

        ProductoController controller = new ProductoController(productoService, authClient);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListarProductos_Exito() throws Exception {
        Producto p1 = new Producto();
        p1.setNomProducto("Perfume Chanel N5");
        
        Mockito.when(productoService.obtenerTodos()).thenReturn(List.of(p1));

        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isOk());
    }
}