package ujc.notificacao.system.sistema_notificacao.service;

import ujc.notificacao.system.sistema_notificacao.entity.*;
import ujc.notificacao.system.sistema_notificacao.repository.*;
import ujc.notificacao.system.sistema_notificacao.dto.request.LevantamentoRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.LevantamentoResponseDTO;
import ujc.notificacao.system.sistema_notificacao.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevantamentoService {

    @Autowired
    private LevantamentoRepository levantamentoRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Registrar levantamento
    @Transactional
    public LevantamentoResponseDTO registrarLevantamento(LevantamentoRequestDTO dto) {
        // Validações
        if (!ValidationUtils.isNotBlank(dto.getPessoaQueRecebeu())) {
            throw new RuntimeException("Nome da pessoa que recebeu é obrigatório");
        }
        if (!ValidationUtils.isNotBlank(dto.getPessoaDocumentoIdentificacao())) {
            throw new RuntimeException("Documento de identificação é obrigatório");
        }
        
        // Buscar pedido
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + dto.getPedidoId()));
        
        // Verificar se pedido já foi levantado
        if (levantamentoRepository.existsByPedido(pedido)) {
            throw new RuntimeException("Este pedido já foi levantado");
        }
        
        // Verificar se pedido está pronto
        if (pedido.getEstadoPedido() != PedidoEstado.PRONTO) {
            throw new RuntimeException("Pedido não está pronto para levantamento. Status atual: " + pedido.getEstadoPedido());
        }
        
        // Buscar funcionário
        Funcionario funcionario = funcionarioRepository.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + dto.getFuncionarioId()));
        
        // Criar levantamento
        Levantamento levantamento = new Levantamento();
        levantamento.setPedido(pedido);
        levantamento.setDataLevantamento(dto.getDataLevantamento() != null ? dto.getDataLevantamento() : LocalDate.now());
        levantamento.setPessoaQueRecebeu(dto.getPessoaQueRecebeu());
        levantamento.setPessoaDocumentoIdentificacao(dto.getPessoaDocumentoIdentificacao());
        levantamento.setFuncionario(funcionario);
        
        Levantamento saved = levantamentoRepository.save(levantamento);
        return new LevantamentoResponseDTO(saved);
    }

    // Listar todos os levantamentos
    public List<LevantamentoResponseDTO> listarTodos() {
        return levantamentoRepository.findAll()
                .stream()
                .map(LevantamentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar levantamento por ID
    public LevantamentoResponseDTO buscarPorId(Long id) {
        Levantamento levantamento = levantamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Levantamento não encontrado com ID: " + id));
        return new LevantamentoResponseDTO(levantamento);
    }

    // Buscar por pedido
    public LevantamentoResponseDTO buscarPorPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        Levantamento levantamento = levantamentoRepository.findByPedido(pedido)
                .orElseThrow(() -> new RuntimeException("Levantamento não encontrado para o pedido: " + pedidoId));
        return new LevantamentoResponseDTO(levantamento);
    }

    // Buscar por data
    public List<LevantamentoResponseDTO> buscarPorData(LocalDate data) {
        return levantamentoRepository.findByDataLevantamento(data)
                .stream()
                .map(LevantamentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar por período
    public List<LevantamentoResponseDTO> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return levantamentoRepository.findByDataLevantamentoBetween(inicio, fim)
                .stream()
                .map(LevantamentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar por funcionário
    public List<LevantamentoResponseDTO> buscarPorFuncionario(Long funcionarioId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        return levantamentoRepository.findByFuncionario(funcionario)
                .stream()
                .map(LevantamentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Contar levantamentos por funcionário
    public Long contarPorFuncionario(Long funcionarioId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        return levantamentoRepository.countByFuncionario(funcionario);
    }

    // Buscar últimos levantamentos
    public List<LevantamentoResponseDTO> buscarUltimosLevantamentos() {
        return levantamentoRepository.findTop10ByOrderByDataLevantamentoDesc()
                .stream()
                .map(LevantamentoResponseDTO::new)
                .collect(Collectors.toList());
    }
}