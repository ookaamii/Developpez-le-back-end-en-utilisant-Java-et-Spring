package com.api.location.service;

import com.api.location.configuration.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${JWT_KEY}")
  private String secretKey;

  @Value("${security.jwt.expiration-time}")
  private long jwtExpiration;

  // Génère un token avec l'email
  public String generateToken(String email) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, email);
  }

  // Crée un JWT token avec des claims spécifiques et un sujet (email)
  private String createToken(Map<String, Object> claims, String email) {
    return Jwts.builder()
      .setClaims(claims)
      .setSubject(email)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // Token valide 30min
      .signWith(getSignKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  // Récupère la signature de la clé JWT token
  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  // Extrait l'email du token
  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // Extrait la date d'expiration du token
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // Extraait un claim du token
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // Extrait tous les claims du token
  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(getSignKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  // Vérifie si le token a expiré
  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // Valide le token par rapport à l'user details et son expiration
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String email = extractEmail(token);
    return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  // Vérifie l'authentification de l'utilisateur
  public void checkAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new UnauthorizedException("Utilisateur non authentifié");
    }
  }
}
