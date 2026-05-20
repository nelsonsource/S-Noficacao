package ujc.notificacao.system.sistema_notificacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ujc.notificacao.system.sistema_notificacao.entity.Pedido;
import ujc.notificacao.system.sistema_notificacao.entity.PedidoEstado;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // 1. Buscar por atendimento
    List<Pedido> findByAtendimentoIdAtendimento(Long idAtendimento);

    // 2. Buscar por documento
    List<Pedido> findByDocumentoIdDocumento(Long idDocumento);

    // 3. Buscar por estado do pedido (ENUM)
    List<Pedido> findByEstadoPedido(PedidoEstado estadoPedido);

    // 4. Buscar por data do pedido
    List<Pedido> findByDataPedido(LocalDate dataPedido);

    // 5. Buscar por observação contendo texto
    List<Pedido> findByObservacaoContainingIgnoreCase(String observacao);

    // 6. Buscar por atendimento e documento
    List<Pedido> findByAtendimentoIdAtendimentoAndDocumentoIdDocumento(Long idAtendimento, Long idDocumento);

    // 7. Buscar por documento e estado
    List<Pedido> findByDocumentoIdDocumentoAndEstadoPedido(Long idDocumento, PedidoEstado estadoPedido);

    // 8. Buscar por atendimento e estado
    List<Pedido> findByAtendimentoIdAtendimentoAndEstadoPedido(Long idAtendimento, PedidoEstado estadoPedido);

    // 9. Buscar pedidos entre duas datas
    List<Pedido> findByDataPedidoBetween(LocalDate inicio, LocalDate fim);

    // 10. Buscar por estado entre datas
    List<Pedido> findByEstadoPedidoAndDataPedidoBetween(PedidoEstado estadoPedido, LocalDate inicio, LocalDate fim);

    // 11. Ordenar por data crescente
    List<Pedido> findAllByOrderByDataPedidoAsc();

    // 12. Ordenar por data decrescente
    List<Pedido> findAllByOrderByDataPedidoDesc();

    // 13. Buscar por documento OU atendimento
    List<Pedido> findByDocumentoIdDocumentoOrAtendimentoIdAtendimento(Long idDocumento, Long idAtendimento);
}