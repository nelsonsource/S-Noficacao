package ujc.notificacao.system.sistema_notificacao.service;

import ujc.notificacao.system.sistema_notificacao.entity.Documento;
import ujc.notificacao.system.sistema_notificacao.entity.CampoDocumento;
import ujc.notificacao.system.sistema_notificacao.repository.DocumentoRepository;
import ujc.notificacao.system.sistema_notificacao.repository.CampoDocumentoRepository;
import ujc.notificacao.system.sistema_notificacao.dto.request.DocumentoRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.DocumentoResponseDTO;
import ujc.notificacao.system.sistema_notificacao.dto.CampoDocumentoDTO;
import ujc.notificacao.system.sistema_notificacao.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;
    
    @Autowired
    private CampoDocumentoRepository campoDocumentoRepository;

    // Criar novo documento sem campos
    @Transactional
    public DocumentoResponseDTO criarDocumento(DocumentoRequestDTO dto) {
        // Validações
        if (!ValidationUtils.isNotBlank(dto.getCodigo())) {
            throw new RuntimeException("Código do documento é obrigatório");
        }
        if (!ValidationUtils.isValidTaxa(dto.getTaxa())) {
            throw new RuntimeException("Taxa deve ser positiva");
        }
        if (!ValidationUtils.isValidPrazo(dto.getPrazoEmissao())) {
            throw new RuntimeException("Prazo de emissão deve ser positivo");
        }
        
        // Verificar código duplicado
        if (documentoRepository.existsByCodigo(dto.getCodigo())) {
            throw new RuntimeException("Código de documento já existe: " + dto.getCodigo());
        }
        
        // Criar documento
        Documento documento = new Documento();
        documento.setCodigo(dto.getCodigo());
        documento.setNomeDocumento(dto.getNomeDocumento());
        documento.setTaxa(dto.getTaxa());
        documento.setPrazoEmissao(dto.getPrazoEmissao());
        
        Documento saved = documentoRepository.save(documento);
        
        // Adicionar campos se houver
        if (dto.getCampos() != null && !dto.getCampos().isEmpty()) {
            for (CampoDocumentoDTO campoDTO : dto.getCampos()) {
                CampoDocumento campo = new CampoDocumento();
                campo.setDocumento(saved);
                campo.setNomeCampo(campoDTO.getNomeCampo());
                campo.setTipoCampo(campoDTO.getTipoCampo());
                campo.setObrigatorio(campoDTO.getObrigatorio() != null ? campoDTO.getObrigatorio() : false);
                campo.setOrdem(campoDTO.getOrdem());
                campoDocumentoRepository.save(campo);
            }
        }
        
        Documento completo = documentoRepository.findById(saved.getId()).get();
        return new DocumentoResponseDTO(completo);
    }

    // Listar todos os documentos
    public List<DocumentoResponseDTO> listarTodos() {
        return documentoRepository.findAll()
                .stream()
                .map(DocumentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar documento por ID
    public DocumentoResponseDTO buscarPorId(Long id) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com ID: " + id));
        return new DocumentoResponseDTO(documento);
    }

    // Buscar por código
    public DocumentoResponseDTO buscarPorCodigo(String codigo) {
        Documento documento = documentoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com código: " + codigo));
        return new DocumentoResponseDTO(documento);
    }

    // Buscar por nome
    public List<DocumentoResponseDTO> buscarPorNome(String nome) {
        return documentoRepository.findByNomeDocumentoContainingIgnoreCase(nome)
                .stream()
                .map(DocumentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar por faixa de taxa
    public List<DocumentoResponseDTO> buscarPorFaixaTaxa(Double min, Double max) {
        return documentoRepository.findByTaxaBetween(min, max)
                .stream()
                .map(DocumentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar documentos com campos
    public List<DocumentoResponseDTO> listarDocumentosComCampos() {
        return documentoRepository.findAll()
                .stream()
                .map(DocumentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Atualizar documento
    @Transactional
    public DocumentoResponseDTO atualizarDocumento(Long id, DocumentoRequestDTO dto) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com ID: " + id));
        
        // Verificar código duplicado (se mudou)
        if (!documento.getCodigo().equals(dto.getCodigo()) && 
            documentoRepository.existsByCodigo(dto.getCodigo())) {
            throw new RuntimeException("Código de documento já existe: " + dto.getCodigo());
        }
        
        documento.setCodigo(dto.getCodigo());
        documento.setNomeDocumento(dto.getNomeDocumento());
        documento.setTaxa(dto.getTaxa());
        documento.setPrazoEmissao(dto.getPrazoEmissao());
        
        // Atualizar campos se fornecidos
        if (dto.getCampos() != null) {
            // Remover campos antigos
            campoDocumentoRepository.deleteByDocumento(documento);
            
            // Adicionar novos campos
            for (CampoDocumentoDTO campoDTO : dto.getCampos()) {
                CampoDocumento campo = new CampoDocumento();
                campo.setDocumento(documento);
                campo.setNomeCampo(campoDTO.getNomeCampo());
                campo.setTipoCampo(campoDTO.getTipoCampo());
                campo.setObrigatorio(campoDTO.getObrigatorio() != null ? campoDTO.getObrigatorio() : false);
                campo.setOrdem(campoDTO.getOrdem());
                campoDocumentoRepository.save(campo);
            }
        }
        
        Documento updated = documentoRepository.save(documento);
        return new DocumentoResponseDTO(updated);
    }

    // Adicionar campo a documento existente
    @Transactional
    public DocumentoResponseDTO adicionarCampoAoDocumento(Long documentoId, CampoDocumentoDTO campoDTO) {
        Documento documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com ID: " + documentoId));
        
        CampoDocumento campo = new CampoDocumento();
        campo.setDocumento(documento);
        campo.setNomeCampo(campoDTO.getNomeCampo());
        campo.setTipoCampo(campoDTO.getTipoCampo());
        campo.setObrigatorio(campoDTO.getObrigatorio() != null ? campoDTO.getObrigatorio() : false);
        campo.setOrdem(campoDTO.getOrdem());
        campoDocumentoRepository.save(campo);
        
        return new DocumentoResponseDTO(documento);
    }

    // Remover campo do documento
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

    // Deletar documento
    @Transactional
    public void deletarDocumento(Long id) {
        if (!documentoRepository.existsById(id)) {
            throw new RuntimeException("Documento não encontrado com ID: " + id);
        }
        documentoRepository.deleteById(id);
    }
}