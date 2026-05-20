package ujc.notificacao.system.sistema_notificacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujc.notificacao.system.sistema_notificacao.entity.Atendente;
import ujc.notificacao.system.sistema_notificacao.repository.AtendenteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AtendenteService {

    @Autowired
    private AtendenteRepository atendenteRepository;

    // salvar
    public Atendente salvar(Atendente atendente) {
        return atendenteRepository.save(atendente);
    }

    // listar todos
    public List<Atendente> listarTodos() {
        return atendenteRepository.findAll();
    }

    // buscar por id
    public Optional<Atendente> buscarPorId(Long id) {
        return atendenteRepository.findById(id);
    }

    // buscar por código
    public Optional<Atendente> buscarPorCodigo(String codigoAtendente) {
        return atendenteRepository.findByCodigoAtendente(codigoAtendente);
    }

    // atualizar
    public Atendente atualizar(Long id, Atendente novoAtendente) {
        return atendenteRepository.findById(id).map(atendente -> {
            atendente.setCodigoAtendente(novoAtendente.getCodigoAtendente());
            atendente.setNome(novoAtendente.getNome());
            atendente.setApelido(novoAtendente.getApelido());
            atendente.setEmail(novoAtendente.getEmail());
            atendente.setTelefone(novoAtendente.getTelefone());
            return atendenteRepository.save(atendente);
        }).orElseThrow(() -> new RuntimeException("Atendente não encontrado"));
    }

    // eliminar
    public void eliminar(Long id) {
        atendenteRepository.deleteById(id);
    }
}