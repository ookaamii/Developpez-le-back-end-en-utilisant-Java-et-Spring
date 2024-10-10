package com.api.location.filter;

import com.api.location.service.CustomUserDetailsService;
import com.api.location.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final CustomUserDetailsService customUserDetailsService;
  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

    if(request.getServletPath().contains("/auth/register") || request.getServletPath().contains("/auth/login")) {
      filterChain.doFilter(request, response);
      return;
    }

    // Récupération de l'en-tête Authorization
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String email = null;

    // Vérifie si le header commence par "Bearer "
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(7); // Extrait le token
      email = jwtService.extractEmail(token); // Extrait l'email du token (modifier dans JwtService si nécessaire)
    }

    // Si l'email est valide et l'utilisateur n'est pas déjà authentifié
    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

      // Valide le token et établit l'authentification
      if (jwtService.validateToken(token, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    // Continue la chaîne de filtres
    filterChain.doFilter(request, response);
  }
}
