package cl.duoc.producto.service;

import cl.duoc.producto.dto.ProductoRequestDTO;
import cl.duoc.producto.model.Marca;
import cl.duoc.producto.model.Producto;
import cl.duoc.producto.model.TipoFragancia;
import cl.duoc.producto.repository.MarcaRepository;
import cl.duoc.producto.repository.ProductoRepository;
import cl.duoc.producto.repository.TipoFraganciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;
    private final TipoFraganciaRepository fraganciaRepository;

    public Producto obtenerPorId(@NonNull Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto ID " + id + " no encontrado."));
    }

    public Producto guardarProducto(ProductoRequestDTO dto) {
        
        
        if (productoRepository.existsByNomProductoIgnoreCase(dto.getNomProducto())) {
            throw new IllegalArgumentException("El producto '" + dto.getNomProducto() + "' ya se encuentra registrado en el catálogo.");
        }
        
        Marca marca = marcaRepository.findByNombreMarca(dto.getNombreMarca())
                .orElseGet(() -> marcaRepository.save(new Marca(null, dto.getNombreMarca())));

        TipoFragancia fragancia = fraganciaRepository.findByNombreFragancia(dto.getNombreFragancia())
                .orElseGet(() -> fraganciaRepository.save(new TipoFragancia(null, dto.getNombreFragancia())));

        Producto producto = new Producto();
        producto.setNomProducto(dto.getNomProducto());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setMarca(marca);
        producto.setTipoFragancia(fragancia);

        return productoRepository.save(producto);
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }
}