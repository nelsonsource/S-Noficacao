package ujc.notificacao.system.sistema_notificacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ujc.notificacao.system.sistema_notificacao.dto.ApiResponse;
import ujc.notificacao.system.sistema_notificacao.entity.Atendente;
import ujc.notificacao.system.sistema_notificacao.service.AtendenteService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atendentes")
public class AtendenteController {

    @Autowired
    private AtendenteService atendenteService;

    @PostMapping("/atendentes/salvar")
    public ResponseEntity<ApiResponse<Atendente>> salvar(@RequestBody Atendente atendente) {
        Atendente novoAtendente = atendenteService.salvar(atendente);
        return ResponseHandler.sucesso("Atendente cadastrado com sucesso", novoAtendente, HttpStatus.CREATED);
    }

    @GetMapping("/atendentes/listarTodos")
    public ResponseEntity<ApiResponse<List<Atendente>>> listarTodos() {
        return ResponseHandler.sucesso("Lista de atendentes carregada com sucesso", atendenteService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Atendente>> buscarPorId(@PathVariable Long id) {
        Optional<Atendente> atendente = atendenteService.buscarPorId(id);

        if (atendente.isPresent()) {
            return ResponseHandler.sucesso("Atendente encontrado com sucesso", atendente.get(), HttpStatus.OK);
        }

        return ResponseHandler.erro("Atendente não encontrado", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Atendente>> atualizar(@PathVariable Long id, @RequestBody Atendente atendente) {
        try {
            Atendente atualizado = atendenteService.atualizar(id, atendente);
            return ResponseHandler.sucesso("Atendente atualizado com sucesso", atualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseHandler.erro(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            atendenteService.eliminar(id);
            return ResponseHandler.sucesso("Atendente eliminado com sucesso", null, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseHandler.erro(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}