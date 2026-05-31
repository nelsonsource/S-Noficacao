package ujc.notificacao.system.sistema_notificacao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pedido_resposta")
public class PedidoResposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "campo_documento_id", nullable = false)
    private CampoDocumento campoDocumento;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String valorResposta;  // Valor preenchido pelo aluno

    public PedidoResposta() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public CampoDocumento getCampoDocumento() { return campoDocumento; }
    public void setCampoDocumento(CampoDocumento campoDocumento) { this.campoDocumento = campoDocumento; }
    public String getValorResposta() { return valorResposta; }
    public void setValorResposta(String valorResposta) { this.valorResposta = valorResposta; }
}