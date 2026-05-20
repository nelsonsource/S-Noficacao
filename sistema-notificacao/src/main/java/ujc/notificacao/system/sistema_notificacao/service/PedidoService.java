package ujc.notificacao.system.sistema_notificacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujc.notificacao.system.sistema_notificacao.entity.Pedido;
import ujc.notificacao.system.sistema_notificacao.repository.PedidoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido atualizar(Long id, Pedido novoPedido) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setAtendimento(novoPedido.getAtendimento());
            pedido.setDocumento(novoPedido.getDocumento());
            pedido.setDataPedido(novoPedido.getDataPedido());
            pedido.setEstadoPedido(novoPedido.getEstadoPedido());
            pedido.setObservacao(novoPedido.getObservacao());
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }
}