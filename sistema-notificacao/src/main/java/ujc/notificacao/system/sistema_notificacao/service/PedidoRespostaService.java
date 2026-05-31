package ujc.notificacao.system.sistema_notificacao.service;

import ujc.notificacao.system.sistema_notificacao.entity.Pedido;
import ujc.notificacao.system.sistema_notificacao.entity.CampoDocumento;
import ujc.notificacao.system.sistema_notificacao.entity.PedidoResposta;
import ujc.notificacao.system.sistema_notificacao.repository.PedidoRespostaRepository;
import ujc.notificacao.system.sistema_notificacao.repository.PedidoRepository;
import ujc.notificacao.system.sistema_notificacao.repository.CampoDocumentoRepository;
import ujc.notificacao.system.sistema_notificacao.dto.PedidoRespostaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoRespostaService {

    @Autowired
    private PedidoRespostaRepository pedidoRespostaRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private CampoDocumentoRepository campoDocumentoRepository;

    // Adicionar resposta a um pedido
    @Transactional
    public PedidoRespostaDTO adicionarResposta(Long pedidoId, PedidoRespostaDTO dto) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + pedidoId));
        
        CampoDocumento campo = campoDocumentoRepository.findById(dto.getCampoDocumentoId())
                .orElseThrow(() -> new RuntimeException("Campo não encontrado com ID: " + dto.getCampoDocumentoId()));
        
        // Verificar se o campo pertence ao documento do pedido
        if (!campo.getDocumento().getId().equals(pedido.getDocumento().getId())) {
            throw new RuntimeException("Campo " + campo.getNomeCampo() + " não pertence ao documento deste pedido");
        }
        
        // Verificar se já existe resposta para este campo
        if (pedidoRespostaRepository.findByPedidoAndCampoDocumento(pedido, campo).isPresent()) {
            throw new RuntimeException("Já existe resposta para o campo: " + campo.getNomeCampo());
        }
        
        PedidoResposta resposta = new PedidoResposta();
        resposta.setPedido(pedido);
        resposta.setCampoDocumento(campo);
        resposta.setValorResposta(dto.getValorResposta());
        
        PedidoResposta saved = pedidoRespostaRepository.save(resposta);
        return new PedidoRespostaDTO(saved);
    }

    // Listar respostas de um pedido
    public List<PedidoRespostaDTO> listarRespostasPorPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        return pedidoRespostaRepository.findByPedido(pedido)
                .stream()
                .map(PedidoRespostaDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar resposta específica
    public PedidoRespostaDTO buscarResposta(Long pedidoId, Long campoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        CampoDocumento campo = campoDocumentoRepository.findById(campoId)
                .orElseThrow(() -> new RuntimeException("Campo não encontrado"));
        
        PedidoResposta resposta = pedidoRespostaRepository.findByPedidoAndCampoDocumento(pedido, campo)
                .orElseThrow(() -> new RuntimeException("Resposta não encontrada"));
        
        return new PedidoRespostaDTO(resposta);
    }

    // Atualizar resposta
    @Transactional
    public PedidoRespostaDTO atualizarResposta(Long id, String novoValor) {
        PedidoResposta resposta = pedidoRespostaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resposta não encontrada com ID: " + id));
        
        resposta.setValorResposta(novoValor);
        PedidoResposta updated = pedidoRespostaRepository.save(resposta);
        return new PedidoRespostaDTO(updated);
    }

    // Remover resposta
    @Transactional
    public void removerResposta(Long id) {
        if (!pedidoRespostaRepository.existsById(id)) {
            throw new RuntimeException("Resposta não encontrada com ID: " + id);
        }
        pedidoRespostaRepository.deleteById(id);
    }

    // Remover todas as respostas de um pedido
    @Transactional
    public void removerRespostasPorPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedidoRespostaRepository.deleteByPedido(pedido);
    }
}