package cl.duoc.producto.repository;

import cl.duoc.producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Verifica si el producto ya existe, sin importar mayúsculas o minúsculas
    boolean existsByNomProductoIgnoreCase(String nomProducto);
}