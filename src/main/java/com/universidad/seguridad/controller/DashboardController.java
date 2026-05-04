package com.universidad.seguridad.controller;

import com.universidad.seguridad.model.Usuario;
import com.universidad.seguridad.service.UsuarioService;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final UsuarioService usuarioService;

    public DashboardController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Principal principal, Model model) {
        Usuario usuario = usuarioService.buscarPorEmailProtegido(principal.getName())
            .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado"));
        model.addAttribute("usuario", usuario);
        return "dashboard";
    }
}