package ujc.notificacao.system.sistema_notificacao.service;

import ujc.notificacao.system.sistema_notificacao.entity.*;
import ujc.notificacao.system.sistema_notificacao.repository.*;
import ujc.notificacao.system.sistema_notificacao.dto.request.PedidoRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.PedidoResponseDTO;
import ujc.notificacao.system.sistema_notificacao.dto.PedidoRespostaDTO;
import ujc.notificacao.system.sistema_notificacao.util.ValidationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private EstudanteRepository estudanteRepository;
    
    @Autowired
    private DocumentoRepository documentoRepository;
    
    @Autowired
    private CampoDocumentoRepository campoDocumentoRepository;
    
    @Autowired
    private PedidoRespostaRepository pedidoRespostaRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    // Criar novo pedido
    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {
        // Validações
        if (!ValidationUtils.isNotBlank(dto.getCodigo())) {
            throw new RuntimeException("Código do pedido é obrigatório");
        }
        
        // Verificar código duplicado
        if (pedidoRepository.existsByCodigo(dto.getCodigo())) {
            throw new RuntimeException("Código de pedido já existe: " + dto.getCodigo());
        }
        
        // Buscar estudante
        Estudante estudante = estudanteRepository.findById(dto.getEstudanteId())
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado com ID: " + dto.getEstudanteId()));
        
        // Buscar documento
        Documento documento = documentoRepository.findById(dto.getDocumentoId())
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com ID: " + dto.getDocumentoId()));
        
        // Criar pedido
        Pedido pedido = new Pedido();
        pedido.setCodigo(dto.getCodigo());
        pedido.setEstudante(estudante);
        pedido.setDocumento(documento);
        pedido.setDataPedido(dto.getDataPedido() != null ? dto.getDataPedido() : LocalDate.now());
        
        // Definir estado inicial
        if (dto.getEstadoPedido() != null) {
            pedido.setEstadoPedido(PedidoEstado.valueOf(dto.getEstadoPedido()));
        } else {
            pedido.setEstadoPedido(PedidoEstado.PENDENTE);
        }
        
        Pedido saved = pedidoRepository.save(pedido);
        
        // Salvar respostas do formulário
        if (dto.getRespostas() != null && !dto.getRespostas().isEmpty()) {
            for (PedidoRespostaDTO respostaDTO : dto.getRespostas()) {
                CampoDocumento campo = campoDocumentoRepository.findById(respostaDTO.getCampoDocumentoId())
                        .orElseThrow(() -> new RuntimeException("Campo não encontrado com ID: " + respostaDTO.getCampoDocumentoId()));
                
                // Verificar se campo pertence ao documento
                if (!campo.getDocumento().getId().equals(documento.getId())) {
                    throw new RuntimeException("Campo " + campo.getNomeCampo() + " não pertence ao documento " + documento.getNomeDocumento());
                }
                
                // Verificar campo obrigatório
                if (campo.getObrigatorio() && !ValidationUtils.isNotBlank(respostaDTO.getValorResposta())) {
                    throw new RuntimeException("Campo " + campo.getNomeCampo() + " é obrigatório");
                }
                
                PedidoResposta resposta = new PedidoResposta();
                resposta.setPedido(saved);
                resposta.setCampoDocumento(campo);
                resposta.setValorResposta(respostaDTO.getValorResposta());
                pedidoRespostaRepository.save(resposta);
            }
        }
        
        return new PedidoResponseDTO(saved);
    }

    // Listar todos os pedidos
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar pedido por ID
    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        return new PedidoResponseDTO(pedido);
    }

    // Buscar por código
    public PedidoResponseDTO buscarPorCodigo(String codigo) {
        Pedido pedido = pedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com código: " + codigo));
        return new PedidoResponseDTO(pedido);
    }

    // Buscar por estudante
    public List<PedidoResponseDTO> buscarPorEstudante(Long estudanteId) {
        Estudante estudante = estudanteRepository.findById(estudanteId)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
        return pedidoRepository.findByEstudante(estudante)
                .stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar por estado
    public List<PedidoResponseDTO> buscarPorEstado(String estado) {
        PedidoEstado pedidoEstado = PedidoEstado.valueOf(estado);
        return pedidoRepository.findByEstadoPedido(pedidoEstado)
                .stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar por data
    public List<PedidoResponseDTO> buscarPorData(LocalDate data) {
        return pedidoRepository.findByDataPedido(data)
                .stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar por período
    public List<PedidoResponseDTO> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return pedidoRepository.findByDataPedidoBetween(inicio, fim)
                .stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Atualizar estado do pedido
    @Transactional
    public PedidoResponseDTO atualizarEstado(Long id, String novoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        
        PedidoEstado estado = PedidoEstado.valueOf(novoEstado);
        pedido.setEstadoPedido(estado);
        
        Pedido updated = pedidoRepository.save(pedido);
        return new PedidoResponseDTO(updated);
    }

    // Cancelar pedido
    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        
        if (pedido.getEstadoPedido() == PedidoEstado.PRONTO) {
            throw new RuntimeException("Não é possível cancelar um pedido que já está pronto");
        }
        
        pedido.setEstadoPedido(PedidoEstado.RECUSADO);
        pedidoRepository.save(pedido);
    }

    // Contar por estado
    public Long contarPorEstado(String estado) {
        return pedidoRepository.countByEstadoPedido(PedidoEstado.valueOf(estado));
    }
}