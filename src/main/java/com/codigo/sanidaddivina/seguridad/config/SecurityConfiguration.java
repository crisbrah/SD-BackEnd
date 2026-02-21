package com.codigo.sanidaddivina.seguridad.config;

import com.codigo.sanidaddivina.seguridad.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UsuarioService usuarioService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Públicos
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                // Admin: aprobación de miembros
                .requestMatchers("/api/v1/admin/**").hasAnyAuthority("ADMIN", "SUPER_ADMIN")

                // Sesiones de culto: ADMIN y SUPER_ADMIN pueden cerrar y eliminar
                .requestMatchers(HttpMethod.DELETE, "/api/v1/sesiones-culto/**").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/sesiones-culto/**").hasAnyAuthority("ENCARGADO", "ADMIN", "SUPER_ADMIN")

                // Solo SUPER_ADMIN puede eliminar (resto de endpoints)
                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasAuthority("SUPER_ADMIN")

                // Financiero: TESORERO, ADMIN, SUPER_ADMIN
                .requestMatchers("/api/v1/ingresos/**").hasAnyAuthority("TESORERO", "ADMIN", "SUPER_ADMIN")
                .requestMatchers("/api/v1/egresos/**").hasAnyAuthority("TESORERO", "ADMIN", "SUPER_ADMIN")
                .requestMatchers("/api/v1/reportes/financiero/**").hasAnyAuthority("TESORERO", "ADMIN", "SUPER_ADMIN")

                // Asistencias: registro solo ENCARGADO/ADMIN/SUPER_ADMIN; lectura para todos autenticados
                .requestMatchers(HttpMethod.POST, "/api/v1/asistencias/**")
                    .hasAnyAuthority("ENCARGADO", "ADMIN", "SUPER_ADMIN")

                // Notas de cursos: MAESTRO, ADMIN, SUPER_ADMIN
                .requestMatchers(HttpMethod.PUT, "/api/v1/cursos/*/inscripciones/*/nota")
                    .hasAnyAuthority("MAESTRO", "ADMIN", "SUPER_ADMIN")

                // Cursos: creación/modificación para MAESTRO y superiores
                .requestMatchers(HttpMethod.POST, "/api/v1/cursos/**")
                    .hasAnyAuthority("MAESTRO", "ADMIN", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/cursos/**")
                    .hasAnyAuthority("MAESTRO", "ADMIN", "SUPER_ADMIN")

                // Resto requiere autenticación
                .anyRequest().authenticated()
            )
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
            "http://localhost:3000",
            "http://localhost:5173",
            "http://127.0.0.1:3000",
            "http://127.0.0.1:5173"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService.userDetailService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
