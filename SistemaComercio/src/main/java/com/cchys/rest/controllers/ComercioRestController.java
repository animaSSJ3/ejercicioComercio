package com.cchys.rest.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cchys.entities.UsuariosEntity;
import com.cchys.request.AuthRequest;
import com.cchys.response.AuthResponse;
import com.cchys.services.UsuariosService;
import com.cchys.services.dto.RestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comercio-rest")
@Slf4j
public class ComercioRestController {
	
	
	private final RestService tokenService;
	private final UsuariosService usuariosService;

	
	@PostMapping("/obtieneToken")
	public String obtieneToken(@Validated AuthRequest datosToken, BindingResult result,
			RedirectAttributes flash, Principal principal, Model model) {
		
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("datosToken", datosToken);
			return "home/admin/home";

		}
		
		
		if (principal != null) {
			
			UsuariosEntity usuario = this.usuariosService.buscarPorCorreo(principal.getName(), 1);

			if (usuario.getCorreo().equals(datosToken.getCorreo()) &&
				usuario.getCorreo().equals(principal.getName())) {
				
				AuthResponse tokenRecuperado = this.tokenService.obtieneToken(datosToken);
				
				if (tokenRecuperado != null) {
					
					usuario.setToken(tokenRecuperado.getAccessToken());
					this.usuariosService.guardar(usuario);
					
					flash.addFlashAttribute("clase", "success");
					flash.addFlashAttribute("mensaje", "Token generado, puede usar nuestros servicios");
					return "redirect:/dashboard-admin/home";
					
			
				}else {
					
					flash.addFlashAttribute("clase", "danger");
					flash.addFlashAttribute("mensaje", "Token no disponible");
					return "redirect:/dashboard-admin/home";
					
				}
				
			}else {
				
				flash.addFlashAttribute("clase", "danger");
				flash.addFlashAttribute("mensaje", "Credenciales erroneas");
				return "redirect:/dashboard-admin/home";
				
			}
			
			
		}else {
			
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "Debes acceder con tu usuario");
			return "redirect:/acceso/login";
			
		}
		
	}
	

}
