package ujc.notificacao.system.sistema_notificacao.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacao")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacao")
    private Long idNotificacao;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_atendente", nullable = false)
    private Atendente atendente;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "data_notificacao", nullable = false)
    private LocalDateTime dataNotificacao;

    @Column(name = "estado_notificacao", length = 30)
    private String estadoNotificacao;

    public Notificacao() {
    }

    public Long getIdNotificacao() {
        return idNotificacao;
    }

    public void setIdNotificacao(Long idNotificacao) {
        this.idNotificacao = idNotificacao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataNotificacao() {
        return dataNotificacao;
    }

    public void setDataNotificacao(LocalDateTime dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }

    public String getEstadoNotificacao() {
        return estadoNotificacao;
    }

    public void setEstadoNotificacao(String estadoNotificacao) {
        this.estadoNotificacao = estadoNotificacao;
    }
}