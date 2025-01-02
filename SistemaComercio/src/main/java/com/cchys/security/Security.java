package com.cchys.security;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

//SE USA PARA INDICAR INFORMACION AL NUCLEO DEL FRAMEWORK
@Configuration
//HABILITAMOS TODO EL PAQUETE DE BATERIA DE SPRING SECURITY
@EnableWebSecurity
//CON ESTO HACEMOS LAS CONFIGURACIONES PRINCIPALES
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@AllArgsConstructor
public class Security {
	
	
	@Bean
	AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		ProviderManager providerManager = new ProviderManager(authenticationProvider);
		providerManager.setEraseCredentialsAfterAuthentication(false);

		return providerManager;
	}

	
	// SIRVE PARA HACER LA IMPLEMENTACION DE LAS CONTRASEÑAS DE LOS USUARIOS
	// ADMINISTRA EL HASH DE CONTRASEÑAS
	@Bean
	BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
	
	
	// CONFIGURA TODOS LOS FILTROS DE NUESTRA APLICACION
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(requests -> requests
				// SE CONFIGURAN LAS VISTAS QUE NO NECESITAN SER AUTENTICADAS
				.requestMatchers("/acceso/registro")
				// INDICA QUE PERMITA ESTA CONFIGURACION APLICADA
				.permitAll()
				.requestMatchers("/dashboard-admin/**").hasRole("ADMIN")
				// SE HACEN LAS CONFIGURACIONES GENERALES
				.anyRequest().authenticated()

		);
		// SE INDICA EL FORMATO DE LOGIN
		http.formLogin(formLogin -> formLogin
				.successHandler(myAuthenticationSuccessHandler())
				.loginPage("/acceso/login")
				.failureUrl("/aceeso/login?error=true")
				.permitAll())
		// INDICA LA RUTA DE LOGOUT
		.logout(logout -> logout
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/acceso/login?logout")
				.permitAll()
				
			);

		http.cors(cors -> corsConfigurationSource());
		// Form login handles the redirect to the login page from the
		// authorization server filter chain
		http.csrf(csrf -> csrf.csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
				// INDICAMOS QUE RUTAS IGNORARAN EL TOKEN
				.ignoringRequestMatchers("/acceso/login")
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
		
		return http.build();

	}
	
	@Bean
	RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
	}
	
    @Bean
    AuthenticationProvider authenticationProvider(PasswordEncoder encoder, UsuarioLogin userDetails) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(encoder);
        authProvider.setUserDetailsService(userDetails);
        return authProvider;
    }
    
    @Bean
    AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new LoginPersonalizado();
    }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		// CREAMOS EL OBJETO CORS CONFIG
		var config = new CorsConfiguration();

		// LE INDICAMOS LA LISTA DE DOMINIOS PERMITIDOS
		// config.setAllowedOrigins(List.of("dominios permitidos"));

		// CON ESTO PODEMOS PERMITIR CUALQUIER PAGINA
		config.setAllowedOrigins(List.of("*"));

		// INDICAMOS LOS TIPOS DE METODOS REQUEST HTTP PERMITIDOS
		// config.setAllowedMethods(List.of("GET","POST","PUT"));

		// CON ESTO PERMITIMOS TODOS LOS METODDOS
		config.setAllowedMethods(List.of("*"));

		// CON ESTO SE PERMITEN LOS HEADERS
		config.setAllowedHeaders(List.of("*"));

		// SE CREA EL OBJETO CORS CONFIG SOURCE
		var source = new UrlBasedCorsConfigurationSource();

		// SE INDICA QUE SE QUIERE PROTEGER
		source.registerCorsConfiguration("/**", config);

		return source;

	}



	final class SpaCsrfTokenRequestHandler extends CsrfTokenRequestAttributeHandler {
		private final CsrfTokenRequestHandler delegate = new XorCsrfTokenRequestAttributeHandler();

		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken) {
			/*
			 * Always use XorCsrfTokenRequestAttributeHandler to provide BREACH protection
			 * of the CsrfToken when it is rendered in the response body.
			 */
			this.delegate.handle(request, response, csrfToken);
		}

		@Override
		public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
			/*
			 * If the request contains a request header, use
			 * CsrfTokenRequestAttributeHandler to resolve the CsrfToken. This applies when
			 * a single-page application includes the header value automatically, which was
			 * obtained via a cookie containing the raw CsrfToken.
			 */
			if (StringUtils.hasText(request.getHeader(csrfToken.getHeaderName()))) {
				return super.resolveCsrfTokenValue(request, csrfToken);
			}
			/*
			 * In all other cases (e.g. if the request contains a request parameter), use
			 * XorCsrfTokenRequestAttributeHandler to resolve the CsrfToken. This applies
			 * when a server-side rendered form includes the _csrf request parameter as a
			 * hidden input.
			 */
			return this.delegate.resolveCsrfTokenValue(request, csrfToken);
		}
	}

	final class CsrfCookieFilter extends OncePerRequestFilter {

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
				FilterChain filterChain) throws ServletException, IOException {
			CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");

			if (Objects.nonNull(csrfToken.getHeaderName())) {

				// SE SETEA EL HEADER Y EL TOKEN
				response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
			}

			// Render the token value to a cookie by causing the deferred token to be loaded
			csrfToken.getToken();

			filterChain.doFilter(request, response);
		}
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/imagenes/**", "/herramientas/**");
	}

}
