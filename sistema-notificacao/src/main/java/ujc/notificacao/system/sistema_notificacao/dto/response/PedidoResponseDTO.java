package ujc.notificacao.system.sistema_notificacao.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoResponseDTO {
    private Long id;
    private String codigo;
    private EstudanteResumoDTO estudante;
    private DocumentoResumoDTO documento;
    private LocalDate dataPedido;
    private String estadoPedido;
    private List<PedidoRespostaDTO> respostas;

    public PedidoResponseDTO(ujc.notificacao.system.sistema_notificacao.entity.Pedido pedido) {
        this.id = pedido.getId();
        this.codigo = pedido.getCodigo();
        this.estudante = new EstudanteResumoDTO(pedido.getEstudante());
        this.documento = new DocumentoResumoDTO(pedido.getDocumento());
        this.dataPedido = pedido.getDataPedido();
        this.estadoPedido = pedido.getEstadoPedido().toString();
        if (pedido.getRespostas() != null) {
            this.respostas = pedido.getRespostas().stream()
                .map(PedidoRespostaDTO::new)
                .collect(Collectors.toList());
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public EstudanteResumoDTO getEstudante() { return estudante; }
    public DocumentoResumoDTO getDocumento() { return documento; }
    public LocalDate getDataPedido() { return dataPedido; }
    public String getEstadoPedido() { return estadoPedido; }
    public List<PedidoRespostaDTO> getRespostas() { return respostas; }
}