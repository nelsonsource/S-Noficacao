package ujc.notificacao.system.sistema_notificacao.service;

import ujc.notificacao.system.sistema_notificacao.entity.CampoDocumento;
import ujc.notificacao.system.sistema_notificacao.entity.Documento;
import ujc.notificacao.system.sistema_notificacao.repository.CampoDocumentoRepository;
import ujc.notificacao.system.sistema_notificacao.repository.DocumentoRepository;
import ujc.notificacao.system.sistema_notificacao.dto.CampoDocumentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampoDocumentoService {

    @Autowired
    private CampoDocumentoRepository campoDocumentoRepository;
    
    @Autowired
    private DocumentoRepository documentoRepository;

    // Adicionar campo a um documento
    @Transactional
    public CampoDocumentoDTO adicionarCampo(Long documentoId, CampoDocumentoDTO dto) {
        Documento documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com ID: " + documentoId));
        
        CampoDocumento campo = new CampoDocumento();
        campo.setDocumento(documento);
        campo.setNomeCampo(dto.getNomeCampo());
        campo.setTipoCampo(dto.getTipoCampo());
        campo.setObrigatorio(dto.getObrigatorio() != null ? dto.getObrigatorio() : false);
        campo.setOrdem(dto.getOrdem());
        
        CampoDocumento saved = campoDocumentoRepository.save(campo);
        return new CampoDocumentoDTO(saved);
    }

    // Listar campos de um documento
    public List<CampoDocumentoDTO> listarCamposPorDocumento(Long documentoId) {
        Documento documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado"));
        
        return campoDocumentoRepository.findByDocumentoOrderByOrdemAsc(documento)
                .stream()
                .map(CampoDocumentoDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar campo por ID
    public CampoDocumentoDTO buscarPorId(Long id) {
        CampoDocumento campo = campoDocumentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campo não encontrado com ID: " + id));
        return new CampoDocumentoDTO(campo);
    }

    // Atualizar campo
    @Transactional
    public CampoDocumentoDTO atualizarCampo(Long id, CampoDocumentoDTO dto) {
        CampoDocumento campo = campoDocumentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campo não encontrado com ID: " + id));
        
        campo.setNomeCampo(dto.getNomeCampo());
        campo.setTipoCampo(dto.getTipoCampo());
        campo.setObrigatorio(dto.getObrigatorio());
        campo.setOrdem(dto.getOrdem());
        
        CampoDocumento updated = campoDocumentoRepository.save(campo);
        return new CampoDocumentoDTO(updated);
    }

    // Remover campo
    @Transactional
    public void removerCampo(Long id) {
        if (!campoDocumentoRepository.existsById(id)) {
            throw new RuntimeException("Campo não encontrado com ID: " + id);
        }
        campoDocumentoRepository.deleteById(id);
    }

    // Remover todos os campos de um documento
    @Transactional
    public void removerCamposPorDocumento(Long documentoId) {
        Documento documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado"));
        campoDocumentoRepository.deleteByDocumento(documento);
    }
}