package ujc.notificacao.system.sistema_notificacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ujc.notificacao.system.sistema_notificacao.dto.ApiResponse;
import ujc.notificacao.system.sistema_notificacao.entity.Estudante;
import ujc.notificacao.system.sistema_notificacao.service.EstudanteService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estudantes")
public class EstudanteController {

    @Autowired
    private EstudanteService estudanteService;

    @PostMapping
    public ResponseEntity<ApiResponse<Estudante>> salvar(@RequestBody Estudante estudante) {
        Estudante novoEstudante = estudanteService.salvar(estudante);
        return ResponseHandler.sucesso("Estudante cadastrado com sucesso", novoEstudante, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Estudante>>> listarTodos() {
        return ResponseHandler.sucesso("Lista de estudantes carregada com sucesso",
                estudanteService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Estudante>> buscarPorId(@PathVariable Long id) {
        Optional<Estudante> estudante = estudanteService.buscarPorId(id);

        if (estudante.isPresent()) {
            return ResponseHandler.sucesso("Estudante encontrado com sucesso", estudante.get(), HttpStatus.OK);
        }

        return ResponseHandler.erro("Estudante não encontrado", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Estudante>> atualizar(@PathVariable Long id, @RequestBody Estudante estudante) {
        try {
            Estudante atualizado = estudanteService.atualizar(id, estudante);
            return ResponseHandler.sucesso("Estudante atualizado com sucesso", atualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseHandler.erro(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            estudanteService.eliminar(id);
            return ResponseHandler.sucesso("Estudante eliminado com sucesso", null, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseHandler.erro(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}