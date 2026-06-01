package ujc.notificacao.system.sistema_notificacao.service;

import ujc.notificacao.system.sistema_notificacao.entity.Documento;
import ujc.notificacao.system.sistema_notificacao.entity.CampoDocumento;
import ujc.notificacao.system.sistema_notificacao.entity.Pedido;
import ujc.notificacao.system.sistema_notificacao.repository.DocumentoRepository;
import ujc.notificacao.system.sistema_notificacao.repository.CampoDocumentoRepository;
import ujc.notificacao.system.sistema_notificacao.repository.PedidoRepository;
import ujc.notificacao.system.sistema_notificacao.dto.request.DocumentoRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.DocumentoResponseDTO;
import ujc.notificacao.system.sistema_notificacao.dto.CampoDocumentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;
    
    @Autowired
    private CampoDocumentoRepository campoDocumentoRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public DocumentoResponseDTO criarDocumento(DocumentoRequestDTO dto) {
        if (dto.getCodigo() == null || dto.getCodigo().trim().isEmpty()) {
            throw new RuntimeException("Código do documento é obrigatório");
        }
        if (dto.getNomeDocumento() == null || dto.getNomeDocumento().trim().isEmpty()) {
            throw new RuntimeException("Nome do documento é obrigatório");
        }
        if (dto.getTaxa() == null || dto.getTaxa() <= 0) {
            throw new RuntimeException("Taxa deve ser positiva");
        }
        if (dto.getPrazoEmissao() == null || dto.getPrazoEmissao() <= 0) {
            throw new RuntimeException("Prazo de emissão deve ser positivo");
        }
        
        if (documentoRepository.existsByCodigo(dto.getCodigo())) {
            throw new RuntimeException("Código de documento já existe: " + dto.getCodigo());
        }
        
        Documento documento = new Documento();
        documento.setCodigo(dto.getCodigo());
        documento.setNomeDocumento(dto.getNomeDocumento());
        documento.setTaxa(dto.getTaxa());
        documento.setPrazoEmissao(dto.getPrazoEmissao());
        
        Documento saved = documentoRepository.save(documento);
        
        if (dto.getCampos() != null && !dto.getCampos().isEmpty()) {
            for (CampoDocumentoDTO campoDTO : dto.getCampos()) {
                CampoDocumento campo = new CampoDocumento();
                campo.setDocumento(saved);
                campo.setNomeCampo(campoDTO.getNomeCampo());
                campo.setTipoCampo(campoDTO.getTipoCampo());
                campo.setObrigatorio(campoDTO.getObrigatorio() != null ? campoDTO.getObrigatorio() : false);
                campo.setOrdem(campoDTO.getOrdem() != null ? campoDTO.getOrdem() : 0);
                campoDocumentoRepository.save(campo);
            }
        }
        
        Documento completo = documentoRepository.findById(saved.getId()).orElse(saved);
        return new DocumentoResponseDTO(completo);
    }

    public List<DocumentoResponseDTO> listarTodos() {
        return documentoRepository.findAll()
                .stream()
                .map(DocumentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public DocumentoResponseDTO buscarPorId(Long id) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com ID: " + id));
        return new DocumentoResponseDTO(documento);
    }

    public DocumentoResponseDTO buscarPorCodigo(String codigo) {
        Documento documento = documentoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com código: " + codigo));
        return new DocumentoResponseDTO(documento);
    }

    public List<DocumentoResponseDTO> buscarPorNome(String nome) {
        return documentoRepository.findByNomeDocumentoContainingIgnoreCase(nome)
                .stream()
                .map(DocumentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<DocumentoResponseDTO> buscarPorFaixaTaxa(Double min, Double max) {
        return documentoRepository.findByTaxaBetween(min, max)
                .stream()
                .map(DocumentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public DocumentoResponseDTO atualizarDocumento(Long id, DocumentoRequestDTO dto) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com ID: " + id));
        
        if (!documento.getCodigo().equals(dto.getCodigo()) && 
            documentoRepository.existsByCodigo(dto.getCodigo())) {
            throw new RuntimeException("Código de documento já existe: " + dto.getCodigo());
        }
        
        documento.setCodigo(dto.getCodigo());
        documento.setNomeDocumento(dto.getNomeDocumento());
        documento.setTaxa(dto.getTaxa());
        documento.setPrazoEmissao(dto.getPrazoEmissao());
        
        Documento updated = documentoRepository.save(documento);
        return new DocumentoResponseDTO(updated);
    }

    @Transactional
    public DocumentoResponseDTO adicionarCampoAoDocumento(Long documentoId, CampoDocumentoDTO campoDTO) {
        Documento documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com ID: " + documentoId));
        
        CampoDocumento campo = new CampoDocumento();
        campo.setDocumento(documento);
        campo.setNomeCampo(campoDTO.getNomeCampo());
        campo.setTipoCampo(campoDTO.getTipoCampo());
        campo.setObrigatorio(campoDTO.getObrigatorio() != null ? campoDTO.getObrigatorio() : false);
        campo.setOrdem(campoDTO.getOrdem() != null ? campoDTO.getOrdem() : 0);
        campoDocumentoRepository.save(campo);
        
        return new DocumentoResponseDTO(documento);
    }

    @Transactional
    public DocumentoResponseDTO removerCampoDoDocumento(Long documentoId, Long campoId) {
        Documento documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado"));
        
        CampoDocumento campo = campoDocumentoRepository.findById(campoId)
                .orElseThrow(() -> new RuntimeException("Campo não encontrado"));
        
        if (!campo.getDocumento().getId().equals(documentoId)) {
            throw new RuntimeException("Campo não pertence a este documento");
        }
        
        campoDocumentoRepository.delete(campo);
        return new DocumentoResponseDTO(documento);
    }

    @Transactional
    public void deletarDocumento(Long id) {
        if (!documentoRepository.existsById(id)) {
            throw new RuntimeException("Documento não encontrado com ID: " + id);
        }
        
        List<Pedido> pedidos = pedidoRepository.findByDocumentoId(id);
        if (!pedidos.isEmpty()) {
            throw new RuntimeException("Não é possível deletar documento pois existem pedidos associados a ele");
        }
        
        documentoRepository.deleteById(id);
    }
}