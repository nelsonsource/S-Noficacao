package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.request.LoginRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.LoginResponseDTO;
import ujc.notificacao.system.sistema_notificacao.entity.Usuario;
import ujc.notificacao.system.sistema_notificacao.security.JwtUtil;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Usuario user = (Usuario) authentication.getPrincipal();
            String token = jwtUtil.generateToken(user);

            LoginResponseDTO response = new LoginResponseDTO(token, user.getEmail(), user.getPerfil().name());
            return ResponseHandler.ok(response, "Login realizado com sucesso");
        } catch (Exception e) {
            return ResponseHandler.unauthorized("Email ou senha inválidos");
        }
    }
}