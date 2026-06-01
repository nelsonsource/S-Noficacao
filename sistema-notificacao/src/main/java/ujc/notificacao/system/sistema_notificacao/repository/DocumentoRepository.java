package ujc.notificacao.system.sistema_notificacao.repository;

import ujc.notificacao.system.sistema_notificacao.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    
    // Buscar por código (único)
    Optional<Documento> findByCodigo(String codigo);
    
    // Buscar por nome do documento
    List<Documento> findByNomeDocumentoContainingIgnoreCase(String nomeDocumento);
    
    // Buscar documentos com taxa menor que
    List<Documento> findByTaxaLessThan(Double taxa);
    
    // Buscar documentos com taxa maior que
    List<Documento> findByTaxaGreaterThan(Double taxa);
    
    // Buscar documentos com taxa entre
    List<Documento> findByTaxaBetween(Double min, Double max);
    
    // Buscar documentos por prazo de emissão
    List<Documento> findByPrazoEmissao(Integer prazoEmissao);
    
    // Buscar documentos com prazo menor que
    List<Documento> findByPrazoEmissaoLessThan(Integer prazo);
    
    // Verificar se existe código
    boolean existsByCodigo(String codigo);
    
    // Listar ordenado por nome
    List<Documento> findAllByOrderByNomeDocumentoAsc();
    
    // Listar ordenado por taxa
    List<Documento> findAllByOrderByTaxaAsc();
}