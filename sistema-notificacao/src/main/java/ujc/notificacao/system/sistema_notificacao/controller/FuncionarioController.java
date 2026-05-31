package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.request.FuncionarioRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.FuncionarioResponseDTO;
import ujc.notificacao.system.sistema_notificacao.service.FuncionarioService;
import ujc.notificacao.system.sistema_notificacao.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    // Criar funcionário
    @PostMapping
    public ResponseEntity<ApiResponse<FuncionarioResponseDTO>> criar(@Valid @RequestBody FuncionarioRequestDTO dto) {
        try {
            FuncionarioResponseDTO funcionario = funcionarioService.criarFuncionario(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.created(funcionario, "Funcionário criado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage(), 400));
        }
    }

    // Listar todos os funcionários
    @GetMapping
    public ResponseEntity<ApiResponse<List<FuncionarioResponseDTO>>> listarTodos() {
        List<FuncionarioResponseDTO> funcionarios = funcionarioService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success(funcionarios, "Lista de funcionários obtida com sucesso"));
    }

    // Buscar funcionário por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FuncionarioResponseDTO>> buscarPorId(@PathVariable Long id) {
        try {
            FuncionarioResponseDTO funcionario = funcionarioService.buscarPorId(id);
            return ResponseEntity.ok(ApiResponse.success(funcionario, "Funcionário encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Buscar por email
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<FuncionarioResponseDTO>> buscarPorEmail(@PathVariable String email) {
        try {
            FuncionarioResponseDTO funcionario = funcionarioService.buscarPorEmail(email);
            return ResponseEntity.ok(ApiResponse.success(funcionario, "Funcionário encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Buscar por nome
    @GetMapping("/buscar/nome")
    public ResponseEntity<ApiResponse<List<FuncionarioResponseDTO>>> buscarPorNome(@RequestParam String nome) {
        try {
            List<FuncionarioResponseDTO> funcionarios = funcionarioService.buscarPorNome(nome);
            return ResponseEntity.ok(ApiResponse.success(funcionarios, "Busca realizada com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage(), 400));
        }
    }

    // Buscar por curso
    @GetMapping("/curso/{curso}")
    public ResponseEntity<ApiResponse<List<FuncionarioResponseDTO>>> buscarPorCurso(@PathVariable String curso) {
        List<FuncionarioResponseDTO> funcionarios = funcionarioService.buscarPorCurso(curso);
        return ResponseEntity.ok(ApiResponse.success(funcionarios, "Funcionários do curso " + curso));
    }

    // Atualizar funcionário
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FuncionarioResponseDTO>> atualizar(@PathVariable Long id, @Valid @RequestBody FuncionarioRequestDTO dto) {
        try {
            FuncionarioResponseDTO funcionario = funcionarioService.atualizarFuncionario(id, dto);
            return ResponseEntity.ok(ApiResponse.success(funcionario, "Funcionário atualizado com sucesso"));
        } catch (RuntimeException e) {
            HttpStatus status = e.getMessage().contains("não encontrado") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                    .body(ApiResponse.error(e.getMessage(), status.value()));
        }
    }

    // Deletar funcionário
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        try {
            funcionarioService.deletarFuncionario(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Funcionário deletado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }
}