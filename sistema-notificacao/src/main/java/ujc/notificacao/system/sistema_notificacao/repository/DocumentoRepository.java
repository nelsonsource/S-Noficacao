package ujc.notificacao.system.sistema_notificacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ujc.notificacao.system.sistema_notificacao.entity.Documento;

import java.util.List;
import java.util.Optional;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    // 1. Buscar por nome exato
    Optional<Documento> findByNomeDocumento(String nomeDocumento);

    // 2. Verificar se existe pelo nome
    boolean existsByNomeDocumento(String nomeDocumento);

    // 3. Buscar por nome contendo texto
    List<Documento> findByNomeDocumentoContainingIgnoreCase(String nomeDocumento);

    // 4. Buscar por descrição contendo texto
    List<Documento> findByDescricaoContainingIgnoreCase(String descricao);

    // 5. Buscar por prazo exato
    List<Documento> findByPrazo(Integer prazo);

    // 6. Buscar documentos com prazo maior que
    List<Documento> findByPrazoGreaterThan(Integer prazo);

    // 7. Buscar documentos com prazo menor que
    List<Documento> findByPrazoLessThan(Integer prazo);

    // 8. Buscar documentos entre dois prazos
    List<Documento> findByPrazoBetween(Integer inicio, Integer fim);

    // 9. Buscar por nome OU descrição
    List<Documento> findByNomeDocumentoContainingIgnoreCaseOrDescricaoContainingIgnoreCase(String nome, String descricao);

    // 10. Buscar ordenado por nome crescente
    List<Documento> findAllByOrderByNomeDocumentoAsc();

    // 11. Buscar ordenado por prazo crescente
    List<Documento> findAllByOrderByPrazoAsc();

    // 12. Buscar ordenado por prazo decrescente
    List<Documento> findAllByOrderByPrazoDesc();
}