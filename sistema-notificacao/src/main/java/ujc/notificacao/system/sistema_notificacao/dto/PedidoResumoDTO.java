package ujc.notificacao.system.sistema_notificacao.dto;

public class PedidoResumoDTO {
    private Long id;
    private String codigo;
    private String estado;

    public PedidoResumoDTO(ujc.notificacao.system.sistema_notificacao.entity.Pedido pedido) {
        if (pedido != null) {
            this.id = pedido.getId();
            this.codigo = pedido.getCodigo();
            this.estado = pedido.getEstadoPedido().toString();
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getEstado() { return estado; }
}