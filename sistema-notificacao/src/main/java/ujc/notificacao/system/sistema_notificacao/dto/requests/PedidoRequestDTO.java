package ujc.notificacao.system.sistema_notificacao.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class PedidoRequestDTO {

    @NotBlank(message = "Código do pedido é obrigatório")
    private String codigo;

    @NotNull(message = "ID do estudante é obrigatório")
    private Long estudanteId;

    @NotNull(message = "ID do documento é obrigatório")
    private Long documentoId;

    private LocalDate dataPedido;

    private String estadoPedido;

    private List<PedidoRespostaDTO> respostas;

    // Getters e Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Long getEstudanteId() { return estudanteId; }
    public void setEstudanteId(Long estudanteId) { this.estudanteId = estudanteId; }
    public Long getDocumentoId() { return documentoId; }
    public void setDocumentoId(Long documentoId) { this.documentoId = documentoId; }
    public LocalDate getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDate dataPedido) { this.dataPedido = dataPedido; }
    public String getEstadoPedido() { return estadoPedido; }
    public void setEstadoPedido(String estadoPedido) { this.estadoPedido = estadoPedido; }
    public List<PedidoRespostaDTO> getRespostas() { return respostas; }
    public void setRespostas(List<PedidoRespostaDTO> respostas) { this.respostas = respostas; }
}