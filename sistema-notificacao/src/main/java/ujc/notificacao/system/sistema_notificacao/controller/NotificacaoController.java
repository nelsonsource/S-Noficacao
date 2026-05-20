package ujc.notificacao.system.sistema_notificacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ujc.notificacao.system.sistema_notificacao.dto.ApiResponse;
import ujc.notificacao.system.sistema_notificacao.entity.Notificacao;
import ujc.notificacao.system.sistema_notificacao.service.NotificacaoService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @PostMapping
    public ResponseEntity<ApiResponse<Notificacao>> salvar(@RequestBody Notificacao notificacao) {
        Notificacao novaNotificacao = notificacaoService.salvar(notificacao);
        return ResponseHandler.sucesso("Notificação cadastrada com sucesso", novaNotificacao, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Notificacao>>> listarTodos() {
        return ResponseHandler.sucesso("Lista de notificações carregada com sucesso",
                notificacaoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Notificacao>> buscarPorId(@PathVariable Long id) {
        Optional<Notificacao> notificacao = notificacaoService.buscarPorId(id);

        if (notificacao.isPresent()) {
            return ResponseHandler.sucesso("Notificação encontrada com sucesso", notificacao.get(), HttpStatus.OK);
        }

        return ResponseHandler.erro("Notificação não encontrada", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Notificacao>> atualizar(@PathVariable Long id, @RequestBody Notificacao notificacao) {
        try {
            Notificacao atualizada = notificacaoService.atualizar(id, notificacao);
            return ResponseHandler.sucesso("Notificação atualizada com sucesso", atualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseHandler.erro(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            notificacaoService.eliminar(id);
            return ResponseHandler.sucesso("Notificação eliminada com sucesso", null, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseHandler.erro(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}