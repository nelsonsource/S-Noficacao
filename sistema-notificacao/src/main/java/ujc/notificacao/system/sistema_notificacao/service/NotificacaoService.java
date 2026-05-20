package ujc.notificacao.system.sistema_notificacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujc.notificacao.system.sistema_notificacao.entity.Notificacao;
import ujc.notificacao.system.sistema_notificacao.repository.NotificacaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public Notificacao salvar(Notificacao notificacao) {
        return notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> listarTodos() {
        return notificacaoRepository.findAll();
    }

    public Optional<Notificacao> buscarPorId(Long id) {
        return notificacaoRepository.findById(id);
    }

    public Notificacao atualizar(Long id, Notificacao novaNotificacao) {
        return notificacaoRepository.findById(id).map(notificacao -> {
            notificacao.setPedido(novaNotificacao.getPedido());
            notificacao.setAtendente(novaNotificacao.getAtendente());
            notificacao.setMensagem(novaNotificacao.getMensagem());
            notificacao.setDataNotificacao(novaNotificacao.getDataNotificacao());
            notificacao.setEstadoNotificacao(novaNotificacao.getEstadoNotificacao());
            return notificacaoRepository.save(notificacao);
        }).orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
    }

    public void eliminar(Long id) {
        notificacaoRepository.deleteById(id);
    }
}