package cl.duoc.inventario.controller;

import cl.duoc.inventario.client.AuthClient;
import cl.duoc.inventario.service.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InventarioControllerTest {

    private MockMvc mockMvc;
    private InventarioService inventarioService;
    private AuthClient authClient;

    @BeforeEach
    void setup() {
        inventarioService = Mockito.mock(InventarioService.class);
        authClient = Mockito.mock(AuthClient.class);

        InventarioController controller = new InventarioController(inventarioService, authClient);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testConsultarStock_Exito() throws Exception {
        Mockito.when(inventarioService.consultarStock(1L)).thenReturn(100);

        mockMvc.perform(get("/api/v1/inventario/stock/1")
                .header("X-Internal-Secret", "clave-secreta-palace-123"))
                .andExpect(status().isOk());
    }
}