package com.universidad.seguridad.config;

import com.universidad.seguridad.model.Usuario;
import com.universidad.seguridad.repository.UsuarioRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                List<Usuario> usuarios = List.of(
                    crearUsuario("Administrador", "admin@demo.com", "admin123", "ROLE_ADMIN", passwordEncoder),
                    crearUsuario("Usuario Demo", "user@demo.com", "user123", "ROLE_USER", passwordEncoder)
                );
                usuarioRepository.saveAll(usuarios);
            }
        };
    }

    private Usuario crearUsuario(String nombre, String email, String password, String rol, PasswordEncoder passwordEncoder) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContrasenia(passwordEncoder.encode(password));
        usuario.setRol(rol);
        usuario.setActivo(true);
        return usuario;
    }
}