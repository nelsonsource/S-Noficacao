package ujc.notificacao.system.sistema_notificacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ujc.notificacao.system.sistema_notificacao.entity.Notificacao;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    // 1. Buscar por pedido
    List<Notificacao> findByPedidoIdPedido(Long idPedido);

    // 2. Buscar por atendente
    List<Notificacao> findByAtendenteIdAtendente(Long idAtendente);

    // 3. Buscar por estado da notificação
    List<Notificacao> findByEstadoNotificacao(String estadoNotificacao);

    // 4. Buscar por data da notificação
    List<Notificacao> findByDataNotificacao(LocalDateTime dataNotificacao);

    // 5. Buscar por mensagem contendo texto
    List<Notificacao> findByMensagemContainingIgnoreCase(String mensagem);

    // 6. Buscar por pedido e atendente
    List<Notificacao> findByPedidoIdPedidoAndAtendenteIdAtendente(Long idPedido, Long idAtendente);

    // 7. Buscar por pedido e estado
    List<Notificacao> findByPedidoIdPedidoAndEstadoNotificacao(Long idPedido, String estadoNotificacao);

    // 8. Buscar por atendente e estado
    List<Notificacao> findByAtendenteIdAtendenteAndEstadoNotificacao(Long idAtendente, String estadoNotificacao);

    // 9. Buscar entre duas datas
    List<Notificacao> findByDataNotificacaoBetween(LocalDateTime inicio, LocalDateTime fim);

    // 10. Buscar por atendente entre duas datas
    List<Notificacao> findByAtendenteIdAtendenteAndDataNotificacaoBetween(Long idAtendente, LocalDateTime inicio, LocalDateTime fim);

    // 11. Buscar por pedido entre duas datas
    List<Notificacao> findByPedidoIdPedidoAndDataNotificacaoBetween(Long idPedido, LocalDateTime inicio, LocalDateTime fim);

    // 12. Listar todas ordenadas por data crescente
    List<Notificacao> findAllByOrderByDataNotificacaoAsc();

    // 13. Listar todas ordenadas por data decrescente
    List<Notificacao> findAllByOrderByDataNotificacaoDesc();
}