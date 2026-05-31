package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.request.FuncionarioRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.FuncionarioResponseDTO;
import ujc.notificacao.system.sistema_notificacao.service.FuncionarioService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody FuncionarioRequestDTO dto) {
        try {
            FuncionarioResponseDTO funcionario = funcionarioService.criarFuncionario(dto);
            return ResponseHandler.created(funcionario, "Funcionário criado com sucesso");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("duplicado") || msg.contains("inválido") || msg.contains("obrigatório")) {
                return ResponseHandler.badRequest(msg);
            }
            return ResponseHandler.internalServerError("Erro interno ao criar funcionário");
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<FuncionarioResponseDTO> funcionarios = funcionarioService.listarTodos();
        return ResponseHandler.ok(funcionarios, "Lista de funcionários obtida com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            FuncionarioResponseDTO funcionario = funcionarioService.buscarPorId(id);
            return ResponseHandler.ok(funcionario, "Funcionário encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Funcionário", String.valueOf(id));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        try {
            FuncionarioResponseDTO funcionario = funcionarioService.buscarPorEmail(email);
            return ResponseHandler.ok(funcionario, "Funcionário encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Funcionário com email", email);
        }
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        try {
            List<FuncionarioResponseDTO> funcionarios = funcionarioService.buscarPorNome(nome);
            if (funcionarios.isEmpty()) {
                return ResponseHandler.ok(funcionarios, "Nenhum funcionário encontrado com o nome: " + nome);
            }
            return ResponseHandler.ok(funcionarios, "Busca realizada com sucesso");
        } catch (RuntimeException e) {
            return ResponseHandler.badRequest(e.getMessage());
        }
    }

    @GetMapping("/curso/{curso}")
    public ResponseEntity<?> buscarPorCurso(@PathVariable String curso) {
        List<FuncionarioResponseDTO> funcionarios = funcionarioService.buscarPorCurso(curso);
        if (funcionarios.isEmpty()) {
            return ResponseHandler.ok(funcionarios, "Nenhum funcionário encontrado no curso: " + curso);
        }
        return ResponseHandler.ok(funcionarios, "Funcionários do curso " + curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody FuncionarioRequestDTO dto) {
        try {
            FuncionarioResponseDTO funcionario = funcionarioService.atualizarFuncionario(id, dto);
            return ResponseHandler.ok(funcionario, "Funcionário atualizado com sucesso");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("não encontrado")) {
                return ResponseHandler.notFound("Funcionário", String.valueOf(id));
            }
            if (msg.contains("duplicado") || msg.contains("inválido")) {
                return ResponseHandler.badRequest(msg);
            }
            return ResponseHandler.internalServerError("Erro interno ao atualizar funcionário");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            funcionarioService.deletarFuncionario(id);
            return ResponseHandler.noContent("Funcionário deletado com sucesso");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseHandler.notFound("Funcionário", String.valueOf(id));
            }
            return ResponseHandler.internalServerError("Erro interno ao deletar funcionário");
        }
    }
}