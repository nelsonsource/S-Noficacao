package ujc.notificacao.system.sistema_notificacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ujc.notificacao.system.sistema_notificacao.entity.Atendente;

import java.util.List;
import java.util.Optional;

public interface AtendenteRepository extends JpaRepository<Atendente, Long> {

    // 1. Buscar por código
    Optional<Atendente> findByCodigoAtendente(String codigoAtendente);

    // 2. Verificar se existe pelo código
    boolean existsByCodigoAtendente(String codigoAtendente);

    // 3. Buscar por nome (contém)
    List<Atendente> findByNomeContainingIgnoreCase(String nome);

    // 4. Buscar por apelido (contém)
    List<Atendente> findByApelidoContainingIgnoreCase(String apelido);

    // 5. Buscar por email exato
    Optional<Atendente> findByEmail(String email);

    // 6. Buscar por telefone
    List<Atendente> findByTelefone(String telefone);

    // 7. Buscar por nome e apelido
    List<Atendente> findByNomeAndApelido(String nome, String apelido);

    // 8. Buscar por nome OU apelido
    List<Atendente> findByNomeOrApelido(String nome, String apelido);

    // 9. Buscar ordenado por nome
    List<Atendente> findAllByOrderByNomeAsc();

    // 10. Buscar por código começando com algo
    List<Atendente> findByCodigoAtendenteStartingWith(String prefixo);

    // 11. Buscar por email contendo texto
    List<Atendente> findByEmailContainingIgnoreCase(String email);

    // 12. Buscar por nome diferente
    List<Atendente> findByNomeNot(String nome);
}