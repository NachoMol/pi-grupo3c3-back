package com.explorer.equipo3.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.explorer.equipo3.security.TokenJWTConfig.*;

public class JWTValidationFilter extends BasicAuthenticationFilter {
    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if(header == null || !header.startsWith(PREFIX_TOKEN)){
            chain.doFilter(request,response);
            return;
        }

        String token = header.replace(PREFIX_TOKEN, ""); //obtenemos la cadena exacta del token


        try{
            Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody(); //valida el token
            Object authoritiesClaims = claims.get("authorities");
            String username = claims.getSubject(); //obtiene el username
            Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper().readValue(authoritiesClaims.toString().getBytes(),SimpleGrantedAuthority.class));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request,response);
        }catch (JwtException e){
            Map<String,String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "JWT Token is not valid");
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(403);
            response.setContentType("application/json");
        }
    }
}
