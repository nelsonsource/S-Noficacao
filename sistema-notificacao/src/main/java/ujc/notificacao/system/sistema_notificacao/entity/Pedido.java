package ujc.notificacao.system.sistema_notificacao.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @ManyToOne
    @JoinColumn(name = "id_atendimento", nullable = false)
    private Atendimento atendimento;

    @ManyToOne
    @JoinColumn(name = "id_documento", nullable = false)
    private Documento documento;

    @Column(name = "data_pedido", nullable = false)
    private LocalDate dataPedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pedido", nullable = false)
    private PedidoEstado estadoPedido;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    public Pedido() {
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public PedidoEstado getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(PedidoEstado pedidoEstado) {
        this.estadoPedido = estadoPedido;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}