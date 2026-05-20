package ujc.notificacao.system.sistema_notificacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujc.notificacao.system.sistema_notificacao.entity.Estudante;
import ujc.notificacao.system.sistema_notificacao.repository.EstudanteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EstudanteService {

    @Autowired
    private EstudanteRepository estudanteRepository;

    public Estudante salvar(Estudante estudante) {
        return estudanteRepository.save(estudante);
    }

    public List<Estudante> listarTodos() {
        return estudanteRepository.findAll();
    }

    public Optional<Estudante> buscarPorId(Long id) {
        return estudanteRepository.findById(id);
    }

    public Optional<Estudante> buscarPorCodigo(String codigoEstudante) {
        return estudanteRepository.findByCodigoEstudante(codigoEstudante);
    }

    public Estudante atualizar(Long id, Estudante novoEstudante) {
        return estudanteRepository.findById(id).map(estudante -> {
            estudante.setCodigoEstudante(novoEstudante.getCodigoEstudante());
            estudante.setNome(novoEstudante.getNome());
            estudante.setApelido(novoEstudante.getApelido());
            estudante.setCurso(novoEstudante.getCurso());
            estudante.setEmail(novoEstudante.getEmail());
            estudante.setTelefone(novoEstudante.getTelefone());
            estudante.setEstadoEstudante(novoEstudante.getEstadoEstudante());
            return estudanteRepository.save(estudante);
        }).orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
    }

    public void eliminar(Long id) {
        estudanteRepository.deleteById(id);
    }
}