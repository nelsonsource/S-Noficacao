package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.request.EstudanteRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.EstudanteResponseDTO;
import ujc.notificacao.system.sistema_notificacao.dto.list.EstudanteListDTO;
import ujc.notificacao.system.sistema_notificacao.service.EstudanteService;
import ujc.notificacao.system.sistema_notificacao.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estudantes")
@CrossOrigin(origins = "*")
public class EstudanteController {

    @Autowired
    private EstudanteService estudanteService;

    // Criar estudante
    @PostMapping
    public ResponseEntity<ApiResponse<EstudanteResponseDTO>> criar(@Valid @RequestBody EstudanteRequestDTO dto) {
        try {
            EstudanteResponseDTO estudante = estudanteService.criarEstudante(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.created(estudante, "Estudante criado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage(), 400));
        }
    }

    // Listar todos os estudantes
    @GetMapping
    public ResponseEntity<ApiResponse<List<EstudanteListDTO>>> listarTodos() {
        List<EstudanteListDTO> estudantes = estudanteService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success(estudantes, "Lista de estudantes obtida com sucesso"));
    }

    // Buscar estudante por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudanteResponseDTO>> buscarPorId(@PathVariable Long id) {
        try {
            EstudanteResponseDTO estudante = estudanteService.buscarPorId(id);
            return ResponseEntity.ok(ApiResponse.success(estudante, "Estudante encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Buscar por número de estudante
    @GetMapping("/numero/{numeroEstudante}")
    public ResponseEntity<ApiResponse<EstudanteResponseDTO>> buscarPorNumero(@PathVariable String numeroEstudante) {
        try {
            EstudanteResponseDTO estudante = estudanteService.buscarPorNumeroEstudante(numeroEstudante);
            return ResponseEntity.ok(ApiResponse.success(estudante, "Estudante encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Buscar por nome
    @GetMapping("/buscar/nome")
    public ResponseEntity<ApiResponse<List<EstudanteListDTO>>> buscarPorNome(@RequestParam String nome) {
        try {
            List<EstudanteListDTO> estudantes = estudanteService.buscarPorNome(nome);
            return ResponseEntity.ok(ApiResponse.success(estudantes, "Busca realizada com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage(), 400));
        }
    }

    // Buscar por curso
    @GetMapping("/curso/{curso}")
    public ResponseEntity<ApiResponse<List<EstudanteListDTO>>> buscarPorCurso(@PathVariable String curso) {
        List<EstudanteListDTO> estudantes = estudanteService.buscarPorCurso(curso);
        return ResponseEntity.ok(ApiResponse.success(estudantes, "Estudantes do curso " + curso));
    }

    // Buscar por termo geral
    @GetMapping("/buscar/termo")
    public ResponseEntity<ApiResponse<List<EstudanteListDTO>>> buscarPorTermo(@RequestParam String termo) {
        try {
            List<EstudanteListDTO> estudantes = estudanteService.buscarPorTermo(termo);
            return ResponseEntity.ok(ApiResponse.success(estudantes, "Busca realizada com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage(), 400));
        }
    }

    // Atualizar estudante
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudanteResponseDTO>> atualizar(@PathVariable Long id, @Valid @RequestBody EstudanteRequestDTO dto) {
        try {
            EstudanteResponseDTO estudante = estudanteService.atualizarEstudante(id, dto);
            return ResponseEntity.ok(ApiResponse.success(estudante, "Estudante atualizado com sucesso"));
        } catch (RuntimeException e) {
            HttpStatus status = e.getMessage().contains("não encontrado") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                    .body(ApiResponse.error(e.getMessage(), status.value()));
        }
    }

    // Deletar estudante
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        try {
            estudanteService.deletarEstudante(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Estudante deletado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Contar por curso
    @GetMapping("/contar/curso")
    public ResponseEntity<ApiResponse<Long>> contarPorCurso(@RequestParam String curso) {
        Long quantidade = estudanteService.contarPorCurso(curso);
        return ResponseEntity.ok(ApiResponse.success(quantidade, "Total de estudantes no curso " + curso));
    }
}