package com.universidad.seguridad.service;

import com.universidad.seguridad.dto.RegistroForm;
import com.universidad.seguridad.model.Usuario;
import com.universidad.seguridad.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public void registrarUsuario(RegistroForm form) {
        Usuario usuario = new Usuario();
        usuario.setNombre(form.getNombre());
        usuario.setEmail(form.getEmail());
        usuario.setContrasenia(passwordEncoder.encode(form.getContrasenia()));
        usuario.setRol("ROLE_USER");
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or #email == authentication.name")
    public Optional<Usuario> buscarPorEmailProtegido(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void cambiarRol(Long id, String nuevoRol) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuario.setRol(nuevoRol);
    }

    @PreAuthorize("#usuario.email == authentication.name or hasRole('ADMIN')")
    @Transactional
    public void actualizarNombre(Usuario usuario) {
        Usuario existente = usuarioRepository.findById(usuario.getId())
            .orElseThrow(() -> new IllegalArgumentException("No encontrado"));
        existente.setNombre(usuario.getNombre());
    }
}