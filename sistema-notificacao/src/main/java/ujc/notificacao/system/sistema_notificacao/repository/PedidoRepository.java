package ujc.notificacao.system.sistema_notificacao.repository;

import ujc.notificacao.system.sistema_notificacao.entity.Pedido;
import ujc.notificacao.system.sistema_notificacao.entity.PedidoEstado;
import ujc.notificacao.system.sistema_notificacao.entity.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    Optional<Pedido> findByCodigo(String codigo);
    
    List<Pedido> findByEstudante(Estudante estudante);
    
    List<Pedido> findByEstadoPedido(PedidoEstado estado);
    
    List<Pedido> findByDataPedido(LocalDate dataPedido);
    
    List<Pedido> findByDataPedidoBetween(LocalDate inicio, LocalDate fim);
    
    List<Pedido> findByEstudanteAndEstadoPedido(Estudante estudante, PedidoEstado estado);
    
    // Corrigido: método para buscar por documentoId
    List<Pedido> findByDocumentoId(Long documentoId);
    
    @Query("SELECT p FROM Pedido p WHERE p.estadoPedido = 'PENDENTE' AND p.dataPedido <= :data")
    List<Pedido> findPedidosPendentesAte(@Param("data") LocalDate data);
    
    Long countByEstadoPedido(PedidoEstado estado);
    
    Long countByEstudante(Estudante estudante);
    
    boolean existsByCodigo(String codigo);
    
    @Query("SELECT p FROM Pedido p WHERE p.estadoPedido = 'PRONTO' AND NOT EXISTS (SELECT l FROM Levantamento l WHERE l.pedido = p)")
    List<Pedido> findPedidosProntosNaoRetirados();
    
    List<Pedido> findByDataPedidoBetweenAndEstadoPedido(LocalDate inicio, LocalDate fim, PedidoEstado estado);
    
    List<Pedido> findTop5ByEstudanteOrderByDataPedidoDesc(Estudante estudante);
}