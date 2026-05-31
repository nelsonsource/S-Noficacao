package ujc.notificacao.system.sistema_notificacao.dto.response;

import ujc.notificacao.system.sistema_notificacao.dto.PedidoResumoDTO;
import ujc.notificacao.system.sistema_notificacao.dto.FuncionarioResumoDTO;
import java.time.LocalDate;

public class LevantamentoResponseDTO {
    private Long id;
    private PedidoResumoDTO pedido;
    private LocalDate dataLevantamento;
    private String pessoaQueRecebeu;
    private String pessoaDocumentoIdentificacao;
    private FuncionarioResumoDTO funcionario;

    public LevantamentoResponseDTO(ujc.notificacao.system.sistema_notificacao.entity.Levantamento levantamento) {
        this.id = levantamento.getId();
        this.pedido = new PedidoResumoDTO(levantamento.getPedido());
        this.dataLevantamento = levantamento.getDataLevantamento();
        this.pessoaQueRecebeu = levantamento.getPessoaQueRecebeu();
        this.pessoaDocumentoIdentificacao = levantamento.getPessoaDocumentoIdentificacao();
        this.funcionario = new FuncionarioResumoDTO(levantamento.getFuncionario());
    }

    // Getters
    public Long getId() { return id; }
    public PedidoResumoDTO getPedido() { return pedido; }
    public LocalDate getDataLevantamento() { return dataLevantamento; }
    public String getPessoaQueRecebeu() { return pessoaQueRecebeu; }
    public String getPessoaDocumentoIdentificacao() { return pessoaDocumentoIdentificacao; }
    public FuncionarioResumoDTO getFuncionario() { return funcionario; }
}