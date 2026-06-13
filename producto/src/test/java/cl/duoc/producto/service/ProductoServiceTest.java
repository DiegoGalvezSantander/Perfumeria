package cl.duoc.producto.service;

import cl.duoc.producto.dto.ProductoRequestDTO;
import cl.duoc.producto.model.Marca;
import cl.duoc.producto.model.Producto;
import cl.duoc.producto.model.TipoFragancia;
import cl.duoc.producto.repository.MarcaRepository;
import cl.duoc.producto.repository.ProductoRepository;
import cl.duoc.producto.repository.TipoFraganciaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private MarcaRepository marcaRepository;
    @Mock
    private TipoFraganciaRepository fraganciaRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void testGuardarProducto_Exito() {
        
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNomProducto("Chanel N5");
        dto.setDescripcion("Fragancia clásica");
        dto.setPrecio(150000);
        dto.setNombreMarca("Chanel");
        dto.setNombreFragancia("Floral");

        
        Mockito.when(productoRepository.existsByNomProductoIgnoreCase("Chanel N5")).thenReturn(false);
        Mockito.when(marcaRepository.findByNombreMarca("Chanel")).thenReturn(Optional.empty());
        Mockito.when(fraganciaRepository.findByNombreFragancia("Floral")).thenReturn(Optional.empty());
        
        // Configuramos los métodos save para que devuelvan lo que reciben
        Mockito.when(marcaRepository.save(any(Marca.class))).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(fraganciaRepository.save(any(TipoFragancia.class))).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(productoRepository.save(any(Producto.class))).thenAnswer(i -> i.getArguments()[0]);

        
        Producto resultado = productoService.guardarProducto(dto);

        
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNomProducto()).isEqualTo("Chanel N5");
        assertThat(resultado.getMarca().getNombreMarca()).isEqualTo("Chanel");
        
        
        Mockito.verify(productoRepository).save(any(Producto.class));
    }
}