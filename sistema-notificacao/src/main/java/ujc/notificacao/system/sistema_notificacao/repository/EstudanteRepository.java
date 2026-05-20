package ujc.notificacao.system.sistema_notificacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ujc.notificacao.system.sistema_notificacao.entity.Estudante;
import ujc.notificacao.system.sistema_notificacao.entity.EstadoEstudante;

import java.util.List;
import java.util.Optional;

public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    // 1. Buscar por código
    Optional<Estudante> findByCodigoEstudante(String codigoEstudante);

    // 2. Verificar se existe pelo código
    boolean existsByCodigoEstudante(String codigoEstudante);

    // 3. Buscar por nome (LIKE)
    List<Estudante> findByNomeContainingIgnoreCase(String nome);

    // 4. Buscar por apelido (LIKE)
    List<Estudante> findByApelidoContainingIgnoreCase(String apelido);

    // 5. Buscar por email
    Optional<Estudante> findByEmail(String email);

    // 6. Buscar por curso
    List<Estudante> findByCursoContainingIgnoreCase(String curso);

    // 7. Buscar por telefone
    List<Estudante> findByTelefone(String telefone);

    // 8. Buscar por estado (ENUM)
    List<Estudante> findByEstadoEstudante(EstadoEstudante estadoEstudante);

    // 9. Buscar por nome e apelido
    List<Estudante> findByNomeAndApelido(String nome, String apelido);

    // 10. Buscar por nome OU apelido
    List<Estudante> findByNomeOrApelido(String nome, String apelido);

    // 11. Ordenar por nome crescente
    List<Estudante> findAllByOrderByNomeAsc();

    // 12. Ordenar por apelido decrescente
    List<Estudante> findAllByOrderByApelidoDesc();
}