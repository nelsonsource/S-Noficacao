package ujc.notificacao.system.sistema_notificacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujc.notificacao.system.sistema_notificacao.entity.Atendimento;
import ujc.notificacao.system.sistema_notificacao.repository.AtendimentoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    public Atendimento salvar(Atendimento atendimento) {
        return atendimentoRepository.save(atendimento);
    }

    public List<Atendimento> listarTodos() {
        return atendimentoRepository.findAll();
    }

    public Optional<Atendimento> buscarPorId(Long id) {
        return atendimentoRepository.findById(id);
    }

    public Atendimento atualizar(Long id, Atendimento novoAtendimento) {
        return atendimentoRepository.findById(id).map(atendimento -> {
            atendimento.setAtendente(novoAtendimento.getAtendente());
            atendimento.setEstudante(novoAtendimento.getEstudante());
            atendimento.setDataAtendimento(novoAtendimento.getDataAtendimento());
            atendimento.setDescricao(novoAtendimento.getDescricao());
            return atendimentoRepository.save(atendimento);
        }).orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));
    }

    public void eliminar(Long id) {
        atendimentoRepository.deleteById(id);
    }
}