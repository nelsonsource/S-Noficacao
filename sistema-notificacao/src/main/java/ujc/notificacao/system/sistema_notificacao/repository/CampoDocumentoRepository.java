package ujc.notificacao.system.sistema_notificacao.repository;

import ujc.notificacao.system.sistema_notificacao.entity.CampoDocumento;
import ujc.notificacao.system.sistema_notificacao.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CampoDocumentoRepository extends JpaRepository<CampoDocumento, Long> {
    
    // Buscar campos por documento
    List<CampoDocumento> findByDocumento(Documento documento);
    
    // Buscar campos por documento ordenado por ordem
    List<CampoDocumento> findByDocumentoOrderByOrdemAsc(Documento documento);
    
    // Buscar campos obrigatórios de um documento
    List<CampoDocumento> findByDocumentoAndObrigatorioTrue(Documento documento);
    
    // Buscar campos por tipo
    List<CampoDocumento> findByTipoCampo(String tipoCampo);
    
    // Deletar campos por documento
    void deleteByDocumento(Documento documento);
    
    // Contar campos de um documento
    long countByDocumento(Documento documento);
}