package ujc.notificacao.system.sistema_notificacao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/api/estudante")
@CrossOrigin(origins = "*")
@Tag(name = "Estudantes", description = "API para gestão de estudantes académicos")
@SecurityRequirement(name = "basicAuth")
public class EstudanteController {

    @Autowired
    private EstudanteService estudanteService;

    @PostMapping
    @Operation(summary = "Criar novo estudante", description = "Cadastra um novo estudante no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos, duplicados ou obrigatórios não preenchidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> criar(
            @Parameter(description = "Dados do estudante a ser criado", required = true)
            @Valid @RequestBody EstudanteRequestDTO dto) {
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
    @Operation(summary = "Listar todos os estudantes",
            description = "Retorna uma lista com todos os estudantes cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estudantes obtida com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> listarTodos() {
        List<EstudanteListDTO> estudantes = estudanteService.listarTodos();
        return ResponseHandler.ok(estudantes, "Lista de estudantes obtida com sucesso");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar estudante por ID",
            description = "Retorna um estudante específico baseado no seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudante encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID do estudante", example = "1", required = true)
            @PathVariable Long id) {
        try {
            EstudanteResponseDTO estudante = estudanteService.buscarPorId(id);
            return ResponseHandler.ok(estudante, "Estudante encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Estudante", String.valueOf(id));
        }
    }

    @GetMapping("/busca/{numeroEstudante}")
    @Operation(summary = "Buscar estudante por número de estudante",
            description = "Retorna um estudante específico baseado no seu número de matrícula/estudante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudante encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorNumero(
            @Parameter(description = "Número de estudante (matrícula)", example = "20240001", required = true)
            @PathVariable String numeroEstudante) {
        try {
            EstudanteResponseDTO estudante = estudanteService.buscarPorNumeroEstudante(numeroEstudante);
            return ResponseHandler.ok(estudante, "Estudante encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Estudante com número", numeroEstudante);
        }
    }

    @GetMapping("/nome")
    @Operation(summary = "Buscar estudantes por nome",
            description = "Retorna uma lista de estudantes cujo nome contenha o texto informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso (pode retornar lista vazia)"),
            @ApiResponse(responseCode = "400", description = "Parâmetro de busca inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorNome(
            @Parameter(description = "Nome ou parte do nome do estudante", example = "Fernando", required = true)
            @RequestParam String nome) {
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
    @Operation(summary = "Buscar estudantes por curso",
            description = "Retorna uma lista de estudantes matriculados no curso informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudantes do curso encontrados (pode ser lista vazia)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorCurso(
            @Parameter(description = "Nome do curso", example = "Engenharia em Tecnologias e Sistemas de Informação", required = true)
            @PathVariable String curso) {
        List<EstudanteListDTO> estudantes = estudanteService.buscarPorCurso(curso);
        if (estudantes.isEmpty()) {
            return ResponseHandler.ok(estudantes, "Nenhum estudante encontrado no curso: " + curso);
        }
        return ResponseHandler.ok(estudantes, "Estudantes do curso " + curso);
    }

    @GetMapping("/termo")
    @Operation(summary = "Busca geral por termo",
            description = "Pesquisa estudantes por um termo geral (nome, email, curso, número de estudante)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetro de busca inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorTermo(
            @Parameter(description = "Termo de busca geral", example = "Macamo", required = true)
            @RequestParam String termo) {
        try {
            List<EstudanteListDTO> estudantes = estudanteService.buscarPorTermo(termo);
            return ResponseHandler.ok(estudantes, "Busca realizada com sucesso");
        } catch (RuntimeException e) {
            return ResponseHandler.badRequest(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estudante",
            description = "Atualiza os dados de um estudante existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudante atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou duplicados"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do estudante", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do estudante", required = true)
            @Valid @RequestBody EstudanteRequestDTO dto) {
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
    @Operation(summary = "Deletar estudante",
            description = "Remove um estudante do sistema pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudante deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do estudante", example = "1", required = true)
            @PathVariable Long id) {
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

//    @GetMapping("/curso")
//    public ResponseEntity<?> contarPorCurso(@RequestParam String curso) {
//        Long quantidade = estudanteService.contarPorCurso(curso);
//        return ResponseHandler.ok(quantidade, "Total de estudantes no curso " + curso);
//    }
}