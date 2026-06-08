package ujc.notificacao.system.sistema_notificacao.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String email = null;
        String jwt = null;

        // LOG 1 - Verificar se o header está chegando
        System.out.println("========== JWT FILTER ==========");
        System.out.println("URL: " + request.getRequestURL());
        System.out.println("Auth Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            System.out.println("Token recebido: " + jwt.substring(0, Math.min(jwt.length(), 50)) + "...");

            try {
                email = jwtUtil.extractEmail(jwt);
                System.out.println("Email extraído: " + email);
            } catch (Exception e) {
                System.out.println("Erro ao extrair email: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Header inválido ou ausente");
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                System.out.println("UserDetails carregado: " + userDetails.getUsername());
                System.out.println("Autoridades: " + userDetails.getAuthorities());

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Token válido! Autenticado com sucesso!");
                } else {
                    System.out.println("Token inválido ou expirado!");
                }
            } catch (Exception e) {
                System.out.println("Erro ao carregar usuário: " + e.getMessage());
            }
        }

        System.out.println("=================================");
        chain.doFilter(request, response);
    }
}
