package ujc.notificacao.system.sistema_notificacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujc.notificacao.system.sistema_notificacao.entity.Documento;
import ujc.notificacao.system.sistema_notificacao.repository.DocumentoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    public Documento salvar(Documento documento) {
        return documentoRepository.save(documento);
    }

    public List<Documento> listarTodos() {
        return documentoRepository.findAll();
    }

    public Optional<Documento> buscarPorId(Long id) {
        return documentoRepository.findById(id);
    }

    public Optional<Documento> buscarPorNome(String nomeDocumento) {
        return documentoRepository.findByNomeDocumento(nomeDocumento);
    }

    public Documento atualizar(Long id, Documento novoDocumento) {
        return documentoRepository.findById(id).map(documento -> {
            documento.setNomeDocumento(novoDocumento.getNomeDocumento());
            documento.setDescricao(novoDocumento.getDescricao());
            documento.setPrazo(novoDocumento.getPrazo());
            return documentoRepository.save(documento);
        }).orElseThrow(() -> new RuntimeException("Documento não encontrado"));
    }

    public void eliminar(Long id) {
        documentoRepository.deleteById(id);
    }
}