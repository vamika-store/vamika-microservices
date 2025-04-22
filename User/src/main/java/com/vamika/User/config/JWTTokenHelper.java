package com.vamika.User.config;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTTokenHelper {
	
	@Value("${jwt.auth.app}")
	private String appName;
	
	@Value("${jwt.auth.secret_key}")
	private String secretKey;
	
	@Value("${jwt.auth.expires_in}")
	private int expiresIn;
	
	public String generateToken(String username) {
		return Jwts.builder()
				.subject(username)
				.issuer(appName)
				.issuedAt(new Date())
				.expiration(getExpirationDate())
				.signWith(getSigningKey())
				.compact();
	}
	
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	private Date getExpirationDate() {
		return new Date(System.currentTimeMillis() + expiresIn * 1000L);
	}

	public static String getToken(HttpServletRequest request) {
		String authHeader = getAuthHeaderFromHeader(request);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return authHeader;
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUserNameFromToken(token);
		return (
				username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		Date expireDate = getExpirationDate(token);
		return expireDate.before(new Date());
	}
	
	private Date getExpirationDate(String token) {
		Date expireDate;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			expireDate = claims.getExpiration();
		} catch (Exception e) {
			expireDate = null;
		}
		return expireDate;
	}

	private static String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}
	public String getUserNameFromToken(String authToken) {
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(authToken);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}
	public Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.verifyWith((SecretKey) getSigningKey())
					.build()
					.parseSignedClaims(token)
					.getPayload();
		} catch (ExpiredJwtException e) {
		    System.err.println("Token has expired: " + e.getMessage());
		    claims = null;
		} catch (MalformedJwtException e) {
		    System.err.println("Invalid token format: " + e.getMessage());
		    claims = null;
		} catch (JwtException e) {
		    System.err.println("JWT error: " + e.getMessage());
		    claims = null;
		}
		return claims;
	}
}
