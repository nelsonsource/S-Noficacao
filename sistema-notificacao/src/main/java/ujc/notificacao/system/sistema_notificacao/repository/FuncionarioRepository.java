package ujc.notificacao.system.sistema_notificacao.repository;

import ujc.notificacao.system.sistema_notificacao.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    
    // Buscar por email
    Optional<Funcionario> findByEmail(String email);
    
    // Buscar por nome
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
    
    // Buscar por apelido
    List<Funcionario> findByApelidoContainingIgnoreCase(String apelido);
    
    // Buscar por curso (departamento)
    List<Funcionario> findByCurso(String curso);
    
    // Buscar por telefone
    Optional<Funcionario> findByTelefone(String telefone);
    
    // Buscar por gênero
    List<Funcionario> findByGenero(String genero);
    
    // Query personalizada para busca geral
    @Query("SELECT f FROM Funcionario f WHERE f.nome LIKE %:termo% OR f.apelido LIKE %:termo% OR f.email LIKE %:termo%")
    List<Funcionario> buscarPorTermo(@Param("termo") String termo);
    
    // Verificar se existe email
    boolean existsByEmail(String email);
    
    // Listar ordenado por nome
    List<Funcionario> findAllByOrderByNomeAsc();
}