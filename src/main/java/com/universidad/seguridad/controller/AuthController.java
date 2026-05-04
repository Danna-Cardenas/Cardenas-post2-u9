package com.universidad.seguridad.controller;

import com.universidad.seguridad.dto.RegistroForm;
import com.universidad.seguridad.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registroForm", new RegistroForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute("registroForm") RegistroForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (usuarioService.existeEmail(form.getEmail())) {
            bindingResult.rejectValue("email", "email.exists", "El correo ya está registrado");
            return "register";
        }
        usuarioService.registrarUsuario(form);
        model.addAttribute("registroExitoso", true);
        return "login";
    }
}