package ujc.notificacao.system.sistema_notificacao.repository;

import ujc.notificacao.system.sistema_notificacao.entity.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {
    
    // Buscar por número de estudante (único)
    Optional<Estudante> findByNumeroEstudante(String numeroEstudante);
    
    // Buscar por email
    Optional<Estudante> findByEmail(String email);
    
    // Buscar por nome (ignorando maiúsculas/minúsculas)
    List<Estudante> findByNomeContainingIgnoreCase(String nome);
    
    // Buscar por apelido
    List<Estudante> findByApelidoContainingIgnoreCase(String apelido);
    
    // Buscar por curso
    List<Estudante> findByCurso(String curso);
    
    // Buscar por ano de ingresso
    List<Estudante> findByAnoIngresso(Integer anoIngresso);
    
    // Buscar por gênero
    List<Estudante> findByGenero(String genero);
    
    // Query personalizada para buscar por nome ou número
    @Query("SELECT e FROM Estudante e WHERE e.nome LIKE %:termo% OR e.apelido LIKE %:termo% OR e.numeroEstudante LIKE %:termo%")
    List<Estudante> buscarPorTermo(@Param("termo") String termo);
    
    // Contar estudantes por curso
    Long countByCurso(String curso);
    
    // Verificar se existe estudante por email
    boolean existsByEmail(String email);
    
    // Verificar se existe por número de estudante
    boolean existsByNumeroEstudante(String numeroEstudante);
    
    // Buscar estudantes ordenados por nome
    List<Estudante> findAllByOrderByNomeAsc();
}