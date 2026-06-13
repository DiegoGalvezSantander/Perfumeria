package cl.duoc.inventario.service;

import cl.duoc.inventario.client.ProductoClient;
import cl.duoc.inventario.model.Inventario;
import cl.duoc.inventario.repository.InventarioRepository;
import cl.duoc.inventario.repository.MovimientoStockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepo;
    @Mock
    private MovimientoStockRepository movimientoRepo;
    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void testConsultarStock_Exito() {
        Inventario inv = new Inventario();
        inv.setIdProducto(1L);
        inv.setStockProducto(50);

        Mockito.when(inventarioRepo.findByIdProducto(1L)).thenReturn(Optional.of(inv));

        Integer resultado = inventarioService.consultarStock(1L);

        assertThat(resultado).isEqualTo(50);
    }
}