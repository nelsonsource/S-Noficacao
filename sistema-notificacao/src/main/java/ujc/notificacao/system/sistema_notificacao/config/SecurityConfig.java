package ujc.notificacao.system.sistema_notificacao.config;

import ujc.notificacao.system.sistema_notificacao.security.JwtAuthenticationFilter;
import ujc.notificacao.system.sistema_notificacao.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // Permite usar @PreAuthorize nos Controllers
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    // Endpoints públicos
    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/**",           // Login e registro
            "/swagger-ui/**",         // Swagger UI
            "/swagger-ui.html",       // Swagger UI
            "/v3/api-docs/**",        // OpenAPI docs
            "/api-docs/**",           // OpenAPI docs
            "/h2-console/**"          // H2 console (se estiver usando)
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos - qualquer um pode acessar
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()

                        // Endpoints específicos por perfil
                        .requestMatchers("/api/documento/**").hasAnyRole("ADMIN", "SECRETARIA")
                        .requestMatchers("/api/pedido/**").hasAnyRole("ADMIN", "SECRETARIA", "ALUNO")
                        .requestMatchers("/api/pedido/criar").hasRole("ALUNO")
                        .requestMatchers("/api/levantamento/**").hasAnyRole("ADMIN", "SECRETARIA")
                        .requestMatchers("/api/funcionario/**").hasAnyRole("ADMIN", "SECRETARIA")
                        .requestMatchers("/api/estudante/**").permitAll()//hasAnyRole("ADMIN", "SECRETARIA", "ALUNO")

                        // Qualquer outra requisição precisa autenticação
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
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