package ujc.notificacao.system.sistema_notificacao.repository;

import ujc.notificacao.system.sistema_notificacao.entity.Levantamento;
import ujc.notificacao.system.sistema_notificacao.entity.Pedido;
import ujc.notificacao.system.sistema_notificacao.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LevantamentoRepository extends JpaRepository<Levantamento, Long> {
    
    // Buscar levantamento por pedido
    Optional<Levantamento> findByPedido(Pedido pedido);
    
    // Buscar levantamentos por data
    List<Levantamento> findByDataLevantamento(LocalDate dataLevantamento);
    
    // Buscar levantamentos entre datas
    List<Levantamento> findByDataLevantamentoBetween(LocalDate inicio, LocalDate fim);
    
    // Buscar levantamentos por funcionário
    List<Levantamento> findByFuncionario(Funcionario funcionario);
    
    // Buscar levantamentos por período e funcionário
    List<Levantamento> findByFuncionarioAndDataLevantamentoBetween(Funcionario funcionario, LocalDate inicio, LocalDate fim);
    
    // Buscar levantamentos por pessoa que recebeu
    List<Levantamento> findByPessoaQueRecebeuContainingIgnoreCase(String nome);
    
    // Verificar se pedido já foi levantado
    boolean existsByPedido(Pedido pedido);
    
    // Contar levantamentos por funcionário
    Long countByFuncionario(Funcionario funcionario);
    
    // Contar levantamentos por período
    Long countByDataLevantamentoBetween(LocalDate inicio, LocalDate fim);
    
    // Buscar últimos levantamentos
    List<Levantamento> findTop10ByOrderByDataLevantamentoDesc();
    
    // Buscar levantamentos por mês e ano
    @Query("SELECT l FROM Levantamento l WHERE YEAR(l.dataLevantamento) = :ano AND MONTH(l.dataLevantamento) = :mes")
    List<Levantamento> findLevantamentosPorMesAno(@Param("ano") int ano, @Param("mes") int mes);
}