package com.cchys.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cchys.entities.UsuariosEntity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtUtil;
	
	//GENERAS UN FILTRO PARA LA AUTENTICACION DEL USUARIO RECIBIENDO EL TOCKEN
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (!hasAuthorizationBearer(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = getAccessToken(request);

		if (!jwtUtil.validateAccessToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		setAuthenticationContext(token, request);
		filterChain.doFilter(request, response);
	}
	
	
	// Bearer sdfsDFSDFDSFSDF.fsdfdsfdsfsdf.sdfsdfds
	private boolean hasAuthorizationBearer(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			return false;
		}

		return true;
	}
	
	//VERIFICA EL ACCESO AL TOKEN
	private String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.split(" ")[1].trim();
		return token;
	}
	
	//TOMA EL TOKEN PARA LA AUTENTICACION
	private void setAuthenticationContext(String token, HttpServletRequest request) {
		UsuariosEntity userDetails = getUserDetails(token);

		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(userDetails, null,
				null);

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	//HACE EL LOGIN CON EL TOCKEN ENVIADO
	//CONVIERTE EL USER MODEL A UN USER DETAIL
	private UsuariosEntity getUserDetails(String token) {
		UsuariosEntity userDetails = new UsuariosEntity();
		String[] jwtSubject = jwtUtil.getSubject(token).split(",");

		userDetails.setId(Long.parseLong(jwtSubject[0]));
		userDetails.setCorreo(jwtSubject[1]);

		return userDetails;
	}

}
