package ujc.notificacao.system.sistema_notificacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ujc.notificacao.system.sistema_notificacao.dto.ApiResponse;
import ujc.notificacao.system.sistema_notificacao.entity.Atendimento;
import ujc.notificacao.system.sistema_notificacao.service.AtendimentoService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @PostMapping
    public ResponseEntity<ApiResponse<Atendimento>> salvar(@RequestBody Atendimento atendimento) {
        Atendimento novoAtendimento = atendimentoService.salvar(atendimento);
        return ResponseHandler.sucesso("Atendimento cadastrado com sucesso", novoAtendimento, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Atendimento>>> listarTodos() {
        return ResponseHandler.sucesso("Lista de atendimentos carregada com sucesso",
                atendimentoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Atendimento>> buscarPorId(@PathVariable Long id) {
        Optional<Atendimento> atendimento = atendimentoService.buscarPorId(id);

        if (atendimento.isPresent()) {
            return ResponseHandler.sucesso("Atendimento encontrado com sucesso", atendimento.get(), HttpStatus.OK);
        }

        return ResponseHandler.erro("Atendimento não encontrado", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Atendimento>> atualizar(@PathVariable Long id, @RequestBody Atendimento atendimento) {
        try {
            Atendimento atualizado = atendimentoService.atualizar(id, atendimento);
            return ResponseHandler.sucesso("Atendimento atualizado com sucesso", atualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseHandler.erro(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            atendimentoService.eliminar(id);
            return ResponseHandler.sucesso("Atendimento eliminado com sucesso", null, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseHandler.erro(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}