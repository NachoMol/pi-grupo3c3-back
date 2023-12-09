package com.explorer.equipo3.security;

import com.explorer.equipo3.security.filters.JWTAuthenticationFilter;
import com.explorer.equipo3.security.filters.JWTValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests()
                .antMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/users/update/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/users/create").permitAll()
                .antMatchers(HttpMethod.GET,"/users/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/users/{email}/**").hasAnyRole("USER", "ADMIN")

                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                .antMatchers(HttpMethod.GET, "/products/**", "/categories/**", "/cities/**", "/countries/**", "/details/**", "/policies/**", "/images/**").permitAll()
                .antMatchers(HttpMethod.POST, "/products", "/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/products", "/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/products", "/categories").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/reservations/**").permitAll()
                //  .antMatchers(HttpMethod.POST, "/reservations/**", "/reservations/{id}/**", "/reservations/{id}/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/reservations/**", "/reservations/{id}/**", "/reservations/{id}/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/reservations/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/reservations/{id}").hasRole("ADMIN")

                .antMatchers("/roles/**").permitAll()
                .antMatchers("/favorites/**").permitAll()
                .antMatchers("/api/s3/**").permitAll()
                .antMatchers("https://bucket-explorer-images.s3.us-east-1.amazonaws.com/04.jpeg/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter((new JWTValidationFilter(authenticationConfiguration.getAuthenticationManager())))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(config -> config.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://app-explorer.s3-website-us-east-1.amazonaws.com"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}