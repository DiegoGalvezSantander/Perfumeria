package cl.duoc.producto.repository;

import cl.duoc.producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    boolean existsByNomProductoIgnoreCase(String nomProducto);
}