package cl.duoc.usuario.service;

import cl.duoc.usuario.dto.UsuarioRequestDTO;
import cl.duoc.usuario.model.Usuario;
import cl.duoc.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario guardarUsuario(UsuarioRequestDTO dto) {
        Usuario user = new Usuario();
        user.setNombreUsuario(dto.getNombreUsuario());
        user.setEmailUsuario(dto.getEmailUsuario());
        user.setFonoUsuario(dto.getFonoUsuario());
        user.setRunUsuario(dto.getRunUsuario());
        return usuarioRepository.save(user);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
        
    }
}