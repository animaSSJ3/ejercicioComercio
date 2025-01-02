package com.cchys.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cchys.entities.UsuariosEntity;
import com.cchys.entities.dto.login.LoginUserDTO;
import com.cchys.request.AuthRequest;
import com.cchys.response.ProductosEntity;
import com.cchys.services.UsuariosService;
import com.cchys.services.dto.RestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard-admin")
@Slf4j
public class HomeController {

	private final UsuariosService usuariosService;
	
	private final RestService restService;

	@Value("${base.url.api}")
	private String ruta_api;

	@GetMapping("/home")
	public String home() {

		return "home/admin/home";

	}
	
	
	@GetMapping("/productos")
	public String datosCita(Model model) {

		return "home/admin/panel/productos";

	}
	

	@ModelAttribute
	public void setGenericos(Principal principal, Model model) {

		if (principal != null) {

			LoginUserDTO datos = new LoginUserDTO();
			UsuariosEntity usuario = this.usuariosService.buscarPorCorreo(principal.getName(), 1);
			
			AuthRequest datosToken = new AuthRequest();

			datos.setNombre(usuario.getIdproveedor().getNombre());
			datos.setPrimerApellido(usuario.getIdproveedor().getPrimerApellido());
			datos.setSegundoApellido(usuario.getIdproveedor().getSegundoApellido());
			
			List<ProductosEntity> listaProductos = this.restService.listaProductos(usuario.getToken(), usuario.getIdproveedor());
			
			
			System.out.println(listaProductos);
			
			model.addAttribute("datos", datos);
			model.addAttribute("datosToken", datosToken);
			model.addAttribute("productos", listaProductos);
			
		}

	}

}
