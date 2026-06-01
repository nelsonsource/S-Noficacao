package ujc.notificacao.system.sistema_notificacao.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @ManyToOne
    @JoinColumn(name = "estudante_id")
    private Estudante estudante;

    @ManyToOne
    @JoinColumn(name = "documento_id")
    private Documento documento;

    private LocalDate dataPedido;

    @Enumerated(EnumType.STRING)
    private PedidoEstado estadoPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoResposta> respostas = new ArrayList<>();

    public Pedido() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Estudante getEstudante() { return estudante; }
    public void setEstudante(Estudante estudante) { this.estudante = estudante; }
    public Documento getDocumento() { return documento; }
    public void setDocumento(Documento documento) { this.documento = documento; }
    public LocalDate getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDate dataPedido) { this.dataPedido = dataPedido; }
    public PedidoEstado getEstadoPedido() { return estadoPedido; }
    public void setEstadoPedido(PedidoEstado estadoPedido) { this.estadoPedido = estadoPedido; }
    public List<PedidoResposta> getRespostas() { return respostas; }
    public void setRespostas(List<PedidoResposta> respostas) { this.respostas = respostas; }
}