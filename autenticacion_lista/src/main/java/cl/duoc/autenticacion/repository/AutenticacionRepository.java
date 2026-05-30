package cl.duoc.autenticacion.repository;

import cl.duoc.autenticacion.model.Autenticacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutenticacionRepository extends JpaRepository<Autenticacion, Long> {
    Optional<Autenticacion> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
}