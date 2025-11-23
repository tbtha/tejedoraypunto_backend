package com.tbtha.tejedoraypunto.webconfig;

import com.tbtha.tejedoraypunto.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration  
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    // Rutas públicas
                    .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    // Registro de usuario - público (DEBE IR ANTES de la regla general de usuarios)
                    .requestMatchers("POST", "/api/usuarios").permitAll()
                    // Productos - GET público, modificar solo ADMIN
                    .requestMatchers("GET", "/api/productos/**").permitAll()
                    .requestMatchers("POST", "/api/productos/**").hasRole("ADMIN")
                    .requestMatchers("PUT", "/api/productos/**").hasRole("ADMIN")
                    .requestMatchers("DELETE", "/api/productos/**").hasRole("ADMIN")
                    .requestMatchers("PATCH", "/api/productos/**").hasRole("ADMIN")
                    // Categorías - GET público, modificar solo ADMIN
                    .requestMatchers("GET", "/api/categorias/**").permitAll()
                    .requestMatchers("POST", "/api/categorias/**").hasRole("ADMIN")
                    .requestMatchers("PUT", "/api/categorias/**").hasRole("ADMIN")
                    .requestMatchers("DELETE", "/api/categorias/**").hasRole("ADMIN")
                    // Boletas - crear requiere autenticación, ver solo propias o ADMIN ve todas
                    .requestMatchers("POST", "/api/boletas").authenticated()
                    .requestMatchers("GET", "/api/boletas/usuario/**").authenticated()
                    .requestMatchers("GET", "/api/boletas/**").hasRole("ADMIN")
                    .requestMatchers("PUT", "/api/boletas/**").hasRole("ADMIN")
                    .requestMatchers("PATCH", "/api/boletas/**").hasRole("ADMIN")
                    .requestMatchers("DELETE", "/api/boletas/**").hasRole("ADMIN")
                    // Detalles de Boleta - requiere autenticación
                    .requestMatchers("POST", "/api/detalles-boleta").authenticated()
                    .requestMatchers("GET", "/api/detalles-boleta/boleta/**").authenticated()
                    .requestMatchers("GET", "/api/detalles-boleta/**").hasRole("ADMIN")
                    .requestMatchers("PUT", "/api/detalles-boleta/**").hasRole("ADMIN")
                    .requestMatchers("DELETE", "/api/detalles-boleta/**").hasRole("ADMIN")
                    // Imágenes - públicas para GET, ADMIN para modificar
                    .requestMatchers("GET", "/api/imagenes/**").permitAll()
                    .requestMatchers("/api/imagenes/**").hasRole("ADMIN")
                    // Usuarios - gestión completa solo ADMIN (GET, PUT, DELETE, PATCH)
                    .requestMatchers("GET", "/api/usuarios/**").hasRole("ADMIN")
                    .requestMatchers("PUT", "/api/usuarios/**").hasRole("ADMIN")
                    .requestMatchers("DELETE", "/api/usuarios/**").hasRole("ADMIN")
                    .requestMatchers("PATCH", "/api/usuarios/**").hasRole("ADMIN")
                    // Cualquier otra ruta requiere autenticación
                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
