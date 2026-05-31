package ujc.notificacao.system.sistema_notificacao.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "levantamento")
public class Levantamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private Pedido pedido;

    @Column(name = "data_levantamento", nullable = false)
    private LocalDate dataLevantamento;

    @Column(name = "pessoa_que_recebeu", nullable = false, length = 200)
    private String pessoaQueRecebeu;

    @Column(name = "pessoa_documento_identificacao", nullable = false, length = 50)
    private String pessoaDocumentoIdentificacao;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    // Construtor padrão
    public Levantamento() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public LocalDate getDataLevantamento() {
        return dataLevantamento;
    }

    public void setDataLevantamento(LocalDate dataLevantamento) {
        this.dataLevantamento = dataLevantamento;
    }

    public String getPessoaQueRecebeu() {
        return pessoaQueRecebeu;
    }

    public void setPessoaQueRecebeu(String pessoaQueRecebeu) {
        this.pessoaQueRecebeu = pessoaQueRecebeu;
    }

    public String getPessoaDocumentoIdentificacao() {
        return pessoaDocumentoIdentificacao;
    }

    public void setPessoaDocumentoIdentificacao(String pessoaDocumentoIdentificacao) {
        this.pessoaDocumentoIdentificacao = pessoaDocumentoIdentificacao;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}