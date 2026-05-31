package ujc.notificacao.system.sistema_notificacao.repository;

import ujc.notificacao.system.sistema_notificacao.entity.PedidoResposta;
import ujc.notificacao.system.sistema_notificacao.entity.Pedido;
import ujc.notificacao.system.sistema_notificacao.entity.CampoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRespostaRepository extends JpaRepository<PedidoResposta, Long> {
    
    // Buscar respostas por pedido
    List<PedidoResposta> findByPedido(Pedido pedido);
    
    // Buscar resposta específica de um campo em um pedido
    Optional<PedidoResposta> findByPedidoAndCampoDocumento(Pedido pedido, CampoDocumento campoDocumento);
    
    // Deletar respostas de um pedido
    void deleteByPedido(Pedido pedido);
    
    // Buscar respostas por campo documento
    List<PedidoResposta> findByCampoDocumento(CampoDocumento campoDocumento);
    
    // Buscar respostas por valor
    List<PedidoResposta> findByValorRespostaContainingIgnoreCase(String valor);
}