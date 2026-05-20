package ujc.notificacao.system.sistema_notificacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ujc.notificacao.system.sistema_notificacao.entity.Atendimento;

import java.time.LocalDate;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    // 1. Buscar por atendente
    List<Atendimento> findByAtendenteIdAtendente(Long idAtendente);

    // 2. Buscar por estudante
    List<Atendimento> findByEstudanteIdEstudante(Long idEstudante);

    // 3. Buscar por data de atendimento
    List<Atendimento> findByDataAtendimento(LocalDate dataAtendimento);

    // 4. Buscar por descrição contendo texto
    List<Atendimento> findByDescricaoContainingIgnoreCase(String descricao);

    // 5. Buscar por atendente e estudante
    List<Atendimento> findByAtendenteIdAtendenteAndEstudanteIdEstudante(Long idAtendente, Long idEstudante);

    // 6. Buscar por atendente e data
    List<Atendimento> findByAtendenteIdAtendenteAndDataAtendimento(Long idAtendente, LocalDate dataAtendimento);

    // 7. Buscar por estudante e data
    List<Atendimento> findByEstudanteIdEstudanteAndDataAtendimento(Long idEstudante, LocalDate dataAtendimento);

    // 8. Buscar atendimentos entre duas datas
    List<Atendimento> findByDataAtendimentoBetween(LocalDate dataInicio, LocalDate dataFim);

    // 9. Buscar atendimentos por atendente entre duas datas
    List<Atendimento> findByAtendenteIdAtendenteAndDataAtendimentoBetween(Long idAtendente, LocalDate dataInicio, LocalDate dataFim);

    // 10. Buscar atendimentos por estudante entre duas datas
    List<Atendimento> findByEstudanteIdEstudanteAndDataAtendimentoBetween(Long idEstudante, LocalDate dataInicio, LocalDate dataFim);

    // 11. Buscar todos ordenados por data crescente
    List<Atendimento> findAllByOrderByDataAtendimentoAsc();

    // 12. Buscar todos ordenados por data decrescente
    List<Atendimento> findAllByOrderByDataAtendimentoDesc();
}