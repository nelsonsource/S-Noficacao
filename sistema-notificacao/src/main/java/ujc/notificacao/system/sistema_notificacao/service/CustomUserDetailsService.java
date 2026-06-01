package ujc.notificacao.system.sistema_notificacao.service;

import ujc.notificacao.system.sistema_notificacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // Tenta buscar por email primeiro (ADMIN e SECRETARIA)
        // Depois tenta buscar por código (ESTUDANTE)
        return usuarioRepository.findByEmail(login)
                .or(() -> usuarioRepository.findByCodigo(login))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + login));
    }
}