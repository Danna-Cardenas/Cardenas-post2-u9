package com.universidad.seguridad.controller;

import com.universidad.seguridad.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final UsuarioService usuarioService;

    public AdminController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/admin/users")
    public String users(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "admin/users";
    }

    @PostMapping("/admin/users/role")
    public String changeRole(@RequestParam Long id, @RequestParam String nuevoRol) {
        usuarioService.cambiarRol(id, nuevoRol);
        return "redirect:/admin/users";
    }
}