package ujc.notificacao.system.sistema_notificacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos
                .requestMatchers("/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                
                // ============ ESTUDANTE - só pode ver seus próprios dados ============
                // Aluno ver seu perfil
                .requestMatchers(HttpMethod.GET, "/api/estudantes/perfil/**").hasRole("ALUNO")
                // Aluno criar pedido
                .requestMatchers(HttpMethod.POST, "/api/pedidos").hasAnyRole("ALUNO", "ADMIN", "SECRETARIA")
                // Aluno ver seus pedidos
                .requestMatchers(HttpMethod.GET, "/api/pedidos/estudante/**").hasAnyRole("ALUNO", "ADMIN", "SECRETARIA")
                // Aluno ver documentos disponíveis
                .requestMatchers(HttpMethod.GET, "/api/documentos").permitAll()
                
                // ============ SECRETARIA - pode ver estudantes e processar pedidos ============
                .requestMatchers(HttpMethod.GET, "/api/estudantes").hasAnyRole("SECRETARIA", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/estudantes/**").hasAnyRole("SECRETARIA", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/pedidos").hasAnyRole("SECRETARIA", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/pedidos/**/estado").hasAnyRole("SECRETARIA", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/levantamentos/**").hasAnyRole("SECRETARIA", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/levantamentos").hasAnyRole("SECRETARIA", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/documentos/**").hasAnyRole("SECRETARIA", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/documentos/**").hasAnyRole("SECRETARIA", "ADMIN")
                
                // ============ ADMIN - acesso total ============
                .requestMatchers(HttpMethod.POST, "/api/estudantes").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/estudantes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/estudantes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/funcionarios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/funcionarios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/funcionarios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/documentos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/documentos/**").hasRole("ADMIN")
                
                // Qualquer outra requisição nega
                .anyRequest().denyAll()
            )
            .httpBasic(httpBasic -> {});
        
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = passwordEncoder();
        
        // ADMIN - acesso total
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        // SECRETARIA - pode ver estudantes e processar pedidos
        UserDetails secretaria = User.builder()
                .username("secretaria")
                .password(encoder.encode("secretaria123"))
                .roles("SECRETARIA")
                .build();

        // ALUNO - só vê seus próprios dados
        UserDetails aluno = User.builder()
                .username("aluno")
                .password(encoder.encode("aluno123"))
                .roles("ALUNO")
                .build();

        return new InMemoryUserDetailsManager(admin, secretaria, aluno);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}