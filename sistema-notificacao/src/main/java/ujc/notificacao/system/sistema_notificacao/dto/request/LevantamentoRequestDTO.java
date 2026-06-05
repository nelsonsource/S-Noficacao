package ujc.notificacao.system.sistema_notificacao.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class LevantamentoRequestDTO {

    @NotNull(message = "ID do pedido é obrigatório")
    private Long pedidoId;

    @NotNull(message = "Data de levantamento é obrigatória")
    private LocalDate dataLevantamento;

    @NotBlank(message = "Pessoa que recebeu é obrigatória")
    private String pessoaQueRecebeu;

    @NotBlank(message = "Documento de identificação é obrigatório")
    private String pessoaDocumentoIdentificacao;

    @NotNull(message = "ID do funcionário é obrigatório")
    private Long funcionarioId;

    // Getters e Setters
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public LocalDate getDataLevantamento() { return dataLevantamento; }
    public void setDataLevantamento(LocalDate dataLevantamento) { this.dataLevantamento = dataLevantamento; }
    public String getPessoaQueRecebeu() { return pessoaQueRecebeu; }
    public void setPessoaQueRecebeu(String pessoaQueRecebeu) { this.pessoaQueRecebeu = pessoaQueRecebeu; }
    public String getPessoaDocumentoIdentificacao() { return pessoaDocumentoIdentificacao; }
    public void setPessoaDocumentoIdentificacao(String pessoaDocumentoIdentificacao) { this.pessoaDocumentoIdentificacao = pessoaDocumentoIdentificacao; }
    public Long getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }
}