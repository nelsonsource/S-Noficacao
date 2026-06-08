package ujc.notificacao.system.sistema_notificacao.config;

import ujc.notificacao.system.sistema_notificacao.security.JwtAuthenticationFilter;
import ujc.notificacao.system.sistema_notificacao.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
@EnableMethodSecurity(prePostEnabled = true) 
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    // Endpoints públicos
    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/api-docs/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()

//
//                                // ===== ESTUDANTE =====
                        .requestMatchers(HttpMethod.POST, "/api/estudante/**").hasAnyAuthority("ADMIN","SECRETARIA")
                        .requestMatchers(HttpMethod.DELETE, "/api/estudante/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/estudante/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/estudante/**").hasAnyAuthority("ADMIN","SECRETARIA")

                        // Documento
                        .requestMatchers(HttpMethod.POST, "/api/documento/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/documento/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/documento/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/documento/**").hasAnyAuthority("ALUNO","ADMIN", "SECRETARIA")

                        // Levantamento
                        .requestMatchers(HttpMethod.POST, "/api/levantamento/**").hasAnyAuthority("ADMIN", "SECRETARIA")
                        .requestMatchers(HttpMethod.PUT, "/api/levantamento/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/levantamento/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/levantamento/**").hasAnyAuthority("ALUNO","ADMIN", "SECRETARIA")

                        // pedidos
                        .requestMatchers(HttpMethod.POST, "/api/pedido/**").hasAnyAuthority("ALUNO","ADMIN", "SECRETARIA")
                        .requestMatchers(HttpMethod.PUT, "/api/pedido/**").hasAnyAuthority("ADMIN", "SECRETARIA")
                        .requestMatchers(HttpMethod.DELETE, "/api/pedido/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/pedido/**").hasAnyAuthority("ALUNO","ADMIN", "SECRETARIA")

                        // Funcionario
                        .requestMatchers(HttpMethod.PUT,"/api/funcionario/**").hasAnyAuthority("ADMIN", "SECRETARIA")
                        .requestMatchers(HttpMethod.GET,"/api/funcionario/**").hasAnyAuthority("ADMIN", "SECRETARIA")
                        .requestMatchers(HttpMethod.POST, "/api/funcionario/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/funcionario/**").hasAuthority("ADMIN")
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
