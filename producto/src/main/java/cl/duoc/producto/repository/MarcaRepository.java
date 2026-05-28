package cl.duoc.producto.repository;

import cl.duoc.producto.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    // Método mágico para buscar por nombre
    Optional<Marca> findByNombreMarca(String nombreMarca);
}