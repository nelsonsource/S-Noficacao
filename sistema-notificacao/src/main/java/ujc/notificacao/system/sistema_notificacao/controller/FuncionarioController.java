package ujc.notificacao.system.sistema_notificacao.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import ujc.notificacao.system.sistema_notificacao.dto.request.FuncionarioRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.FuncionarioResponseDTO;
import ujc.notificacao.system.sistema_notificacao.service.FuncionarioService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/funcionario")
@CrossOrigin(origins = "*")
@Tag(name = "Funcionários", description = "API para gestão de funcionários da universidade (secretários, administrativos, etc.)")
@SecurityRequirement(name = "bearerAuth")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    @Operation(summary = "Criar novo funcionário",
            description = "Cadastra um novo funcionário no sistema com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos, duplicados ou obrigatórios não preenchidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> criar(
            @Parameter(description = "Dados do funcionário a ser criado", required = true)
            @Valid @RequestBody FuncionarioRequestDTO dto) {
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
    @Operation(summary = "Listar todos os funcionários",
            description = "Retorna uma lista com todos os funcionários cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de funcionários obtida com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listarTodos() {
        List<FuncionarioResponseDTO> funcionarios = funcionarioService.listarTodos();
        return ResponseHandler.ok(funcionarios, "Lista de funcionários obtida com sucesso");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar funcionário por ID",
            description = "Retorna um funcionário específico baseado no seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID do funcionário", example = "1", required = true)
            @PathVariable Long id) {
        try {
            FuncionarioResponseDTO funcionario = funcionarioService.buscarPorId(id);
            return ResponseHandler.ok(funcionario, "Funcionário encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Funcionário", String.valueOf(id));
        }
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar funcionário por email",
            description = "Retorna um funcionário específico baseado no seu endereço de email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> buscarPorEmail(
            @Parameter(description = "Email do funcionário", example = "carlos.macamo@ujc.ac.mz", required = true)
            @PathVariable String email) {
        try {
            FuncionarioResponseDTO funcionario = funcionarioService.buscarPorEmail(email);
            return ResponseHandler.ok(funcionario, "Funcionário encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Funcionário com email", email);
        }
    }

    @GetMapping("/buscar/nome")
    @Operation(summary = "Buscar funcionários por nome",
            description = "Retorna uma lista de funcionários cujo nome contenha o texto informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso (pode retornar lista vazia)"),
            @ApiResponse(responseCode = "400", description = "Parâmetro de busca inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> buscarPorNome(
            @Parameter(description = "Nome ou parte do nome do funcionário", example = "Carlos", required = true)
            @RequestParam String nome) {
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
    @Operation(summary = "Buscar funcionários por curso/sector",
            description = "Retorna uma lista de funcionários que atuam no curso ou sector informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários do curso encontrados (pode ser lista vazia)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> buscarPorCurso(
            @Parameter(description = "Nome do curso ou sector", example = "Secretaria Académica", required = true)
            @PathVariable String curso) {
        List<FuncionarioResponseDTO> funcionarios = funcionarioService.buscarPorCurso(curso);
        if (funcionarios.isEmpty()) {
            return ResponseHandler.ok(funcionarios, "Nenhum funcionário encontrado no curso: " + curso);
        }
        return ResponseHandler.ok(funcionarios, "Funcionários do curso " + curso);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar funcionário",
            description = "Atualiza os dados de um funcionário existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou duplicados"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN','SECRETARIA')")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do funcionário", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do funcionário", required = true)
            @Valid @RequestBody FuncionarioRequestDTO dto) {
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
    @Operation(summary = "Deletar funcionário",
            description = "Remove um funcionário do sistema pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Funcionário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do funcionário", example = "1", required = true)
            @PathVariable Long id) {
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