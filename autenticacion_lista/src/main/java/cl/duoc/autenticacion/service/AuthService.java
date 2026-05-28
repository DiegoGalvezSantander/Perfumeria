package cl.duoc.autenticacion.service;

import cl.duoc.autenticacion.model.Autenticacion;
import cl.duoc.autenticacion.model.Rol;
import cl.duoc.autenticacion.repository.AutenticacionRepository;
import cl.duoc.autenticacion.repository.RolRepository;
import cl.duoc.autenticacion.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AutenticacionRepository authRepository;
    private final RolRepository rolRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Autenticacion registrar(String username, String email, String rawPassword, String nombreRol) {
        if (authRepository.existsByUsername(username) || authRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El usuario o email ya existen en el sistema");
        }
        
        String encoded = passwordEncoder.encode(rawPassword);
        String rolFinal = (nombreRol != null && !nombreRol.isEmpty()) ? nombreRol.toUpperCase() : "CLIENTE";
        
        Rol rol = rolRepository.findByNombreRol(rolFinal)
                .orElseGet(() -> rolRepository.save(new Rol(null, rolFinal)));
        
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);

        Autenticacion auth = new Autenticacion();
        auth.setUsername(username);
        auth.setEmail(email);
        auth.setPassword(encoded);
        auth.setRoles(roles);
        
        return authRepository.save(auth);
    }

    public String login(String username, String rawPassword) {
        Optional<Autenticacion> userOpt = authRepository.findByUsername(username);
        
        if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
            String rol = userOpt.get().getRoles().iterator().next().getNombreRol();
            return jwtUtil.generateToken(username, rol);
        }
        return null; 
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}