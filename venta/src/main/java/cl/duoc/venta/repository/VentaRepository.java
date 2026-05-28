package cl.duoc.venta.repository;

import cl.duoc.venta.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByIdUsuario(Long idUsuario);
}