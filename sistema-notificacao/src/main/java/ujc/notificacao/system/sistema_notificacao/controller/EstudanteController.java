package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.request.EstudanteRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.EstudanteResponseDTO;
import ujc.notificacao.system.sistema_notificacao.dto.list.EstudanteListDTO;
import ujc.notificacao.system.sistema_notificacao.service.EstudanteService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estudantes")
@CrossOrigin(origins = "*")
public class EstudanteController {

    @Autowired
    private EstudanteService estudanteService;

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody EstudanteRequestDTO dto) {
        try {
            EstudanteResponseDTO estudante = estudanteService.criarEstudante(dto);
            return ResponseHandler.created(estudante, "Estudante criado com sucesso");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("duplicado") || msg.contains("inválido") || msg.contains("obrigatório")) {
                return ResponseHandler.badRequest(msg);
            }
            return ResponseHandler.internalServerError("Erro interno ao criar estudante");
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<EstudanteListDTO> estudantes = estudanteService.listarTodos();
        return ResponseHandler.ok(estudantes, "Lista de estudantes obtida com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            EstudanteResponseDTO estudante = estudanteService.buscarPorId(id);
            return ResponseHandler.ok(estudante, "Estudante encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Estudante", String.valueOf(id));
        }
    }

    @GetMapping("/numero/{numeroEstudante}")
    public ResponseEntity<?> buscarPorNumero(@PathVariable String numeroEstudante) {
        try {
            EstudanteResponseDTO estudante = estudanteService.buscarPorNumeroEstudante(numeroEstudante);
            return ResponseHandler.ok(estudante, "Estudante encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Estudante com número", numeroEstudante);
        }
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        try {
            List<EstudanteListDTO> estudantes = estudanteService.buscarPorNome(nome);
            if (estudantes.isEmpty()) {
                return ResponseHandler.ok(estudantes, "Nenhum estudante encontrado com o nome: " + nome);
            }
            return ResponseHandler.ok(estudantes, "Busca realizada com sucesso");
        } catch (RuntimeException e) {
            return ResponseHandler.badRequest(e.getMessage());
        }
    }

    @GetMapping("/curso/{curso}")
    public ResponseEntity<?> buscarPorCurso(@PathVariable String curso) {
        List<EstudanteListDTO> estudantes = estudanteService.buscarPorCurso(curso);
        if (estudantes.isEmpty()) {
            return ResponseHandler.ok(estudantes, "Nenhum estudante encontrado no curso: " + curso);
        }
        return ResponseHandler.ok(estudantes, "Estudantes do curso " + curso);
    }

    @GetMapping("/buscar/termo")
    public ResponseEntity<?> buscarPorTermo(@RequestParam String termo) {
        try {
            List<EstudanteListDTO> estudantes = estudanteService.buscarPorTermo(termo);
            return ResponseHandler.ok(estudantes, "Busca realizada com sucesso");
        } catch (RuntimeException e) {
            return ResponseHandler.badRequest(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody EstudanteRequestDTO dto) {
        try {
            EstudanteResponseDTO estudante = estudanteService.atualizarEstudante(id, dto);
            return ResponseHandler.ok(estudante, "Estudante atualizado com sucesso");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("não encontrado")) {
                return ResponseHandler.notFound("Estudante", String.valueOf(id));
            }
            if (msg.contains("duplicado") || msg.contains("inválido")) {
                return ResponseHandler.badRequest(msg);
            }
            return ResponseHandler.internalServerError("Erro interno ao atualizar estudante");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            estudanteService.deletarEstudante(id);
            return ResponseHandler.noContent("Estudante deletado com sucesso");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseHandler.notFound("Estudante", String.valueOf(id));
            }
            return ResponseHandler.internalServerError("Erro interno ao deletar estudante");
        }
    }

    @GetMapping("/contar/curso")
    public ResponseEntity<?> contarPorCurso(@RequestParam String curso) {
        Long quantidade = estudanteService.contarPorCurso(curso);
        return ResponseHandler.ok(quantidade, "Total de estudantes no curso " + curso);
    }
}