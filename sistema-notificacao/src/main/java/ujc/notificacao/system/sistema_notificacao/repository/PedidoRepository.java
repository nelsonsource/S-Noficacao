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
    
    // Buscar por código (único)
    Optional<Pedido> findByCodigo(String codigo);
    
    // Buscar pedidos por estudante
    List<Pedido> findByEstudante(Estudante estudante);
    
    // Buscar pedidos por estado
    List<Pedido> findByEstadoPedido(PedidoEstado estado);
    
    // Buscar pedidos por data
    List<Pedido> findByDataPedido(LocalDate dataPedido);
    
    // Buscar pedidos entre datas
    List<Pedido> findByDataPedidoBetween(LocalDate inicio, LocalDate fim);
    
    // Buscar pedidos por estudante e estado
    List<Pedido> findByEstudanteAndEstadoPedido(Estudante estudante, PedidoEstado estado);
    
    // Buscar pedidos por documento
    List<Pedido> findByDocumentoId(Long documentoId);
    
    // Buscar pedidos pendentes há mais de X dias
    @Query("SELECT p FROM Pedido p WHERE p.estadoPedido = 'PENDENTE' AND p.dataPedido <= :data")
    List<Pedido> findPedidosPendentesAte(@Param("data") LocalDate data);
    
    // Contar pedidos por estado
    Long countByEstadoPedido(PedidoEstado estado);
    
    // Contar pedidos por estudante
    Long countByEstudante(Estudante estudante);
    
    // Verificar se existe código
    boolean existsByCodigo(String codigo);
    
    // Buscar pedidos com levantamento pendente (prontos mas não retirados)
    @Query("SELECT p FROM Pedido p WHERE p.estadoPedido = 'PRONTO' AND NOT EXISTS (SELECT l FROM Levantamento l WHERE l.pedido = p)")
    List<Pedido> findPedidosProntosNaoRetirados();
    
    // Buscar pedidos por período e estado
    List<Pedido> findByDataPedidoBetweenAndEstadoPedido(LocalDate inicio, LocalDate fim, PedidoEstado estado);
    
    // Buscar últimos pedidos de um estudante
    List<Pedido> findTop5ByEstudanteOrderByDataPedidoDesc(Estudante estudante);
}