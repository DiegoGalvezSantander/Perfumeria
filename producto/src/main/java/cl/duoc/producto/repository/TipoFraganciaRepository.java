package cl.duoc.producto.repository;

import cl.duoc.producto.model.TipoFragancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoFraganciaRepository extends JpaRepository<TipoFragancia, Long> {
    
    Optional<TipoFragancia> findByNombreFragancia(String nombreFragancia);
}