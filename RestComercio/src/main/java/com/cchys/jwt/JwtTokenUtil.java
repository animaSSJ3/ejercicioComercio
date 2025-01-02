package com.cchys.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.cchys.entities.UsuariosEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;// 24 HORAS
	//SE USA CUANDO ESTE CONSTRUIDO EL JWT(JSON WEB TOKEN) Y SE PUEDAN FIRMAR CON ESTA CONSTANTE
	private static final String FIRMA="cHJ1ZWJhX0BhbHRlcm5hdGl2YTEyMzQ1Njc4OUVzdGEzc3VuNEMwbnRyNHMzbmE=";

	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
			return true;
		} catch (ExpiredJwtException ex) {
			System.out.println("JWT expirado" + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			System.out.println("Token es null, está vacío o contiene espacios " + ex.getMessage());
		} catch (MalformedJwtException ex) {
			System.out.println("JWT es inválido" + ex);
		} catch (UnsupportedJwtException ex) {
			System.out.println("JWT no soportado" + ex);
		} catch (Exception ex) {
			System.out.println("Validación de firma errónea");
		}

		return false;
	}

	public String generarToken(UsuariosEntity usuarioModel) {
		return Jwts.builder().subject(String.format("%s,%s", usuarioModel.getId(), usuarioModel.getCorreo()))
				.issuer("Cesar").issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(getSigningKey()).compact();
		

	}

	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}

	private Claims parseClaims(String token) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}
	
	private SecretKey getSigningKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(FIRMA);
	    return Keys.hmacShaKeyFor(keyBytes);
	}

}
