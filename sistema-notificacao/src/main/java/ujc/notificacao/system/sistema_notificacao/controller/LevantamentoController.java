package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.request.LevantamentoRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.LevantamentoResponseDTO;
import ujc.notificacao.system.sistema_notificacao.service.LevantamentoService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/levantamento")
@CrossOrigin(origins = "*")
@Tag(name = "Levantamentos", description = "API para gestão de levantamentos de documentos (entrega ao estudante)")
@SecurityRequirement(name = "basicAuth")
public class LevantamentoController {

    @Autowired
    private LevantamentoService levantamentoService;

    @PostMapping
    @Operation(summary = "Registrar levantamento",
            description = "Registra a entrega/levantamento de um documento a um estudante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Levantamento registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido não está pronto para levantamento ou dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Pedido ou funcionário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Pedido já foi levantado anteriormente"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> registrar(
            @Parameter(description = "Dados do levantamento", required = true)
            @Valid @RequestBody LevantamentoRequestDTO dto) {
        try {
            LevantamentoResponseDTO levantamento = levantamentoService.registrarLevantamento(dto);
            return ResponseHandler.created(levantamento, "Levantamento registrado com sucesso");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("não encontrado")) {
                return ResponseHandler.notFound(msg);
            }
            if (msg.contains("já foi levantado")) {
                return ResponseHandler.conflict(msg);
            }
            if (msg.contains("não está pronto")) {
                return ResponseHandler.badRequest(msg);
            }
            return ResponseHandler.internalServerError("Erro interno ao registrar levantamento");
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos os levantamentos",
            description = "Retorna uma lista com todos os levantamentos registrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de levantamentos obtida com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> listarTodos() {
        List<LevantamentoResponseDTO> levantamentos = levantamentoService.listarTodos();
        return ResponseHandler.ok(levantamentos, "Lista de levantamentos obtida com sucesso");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar levantamento por ID",
            description = "Retorna um levantamento específico baseado no seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Levantamento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Levantamento não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID do levantamento", example = "1", required = true)
            @PathVariable Long id) {
        try {
            LevantamentoResponseDTO levantamento = levantamentoService.buscarPorId(id);
            return ResponseHandler.ok(levantamento, "Levantamento encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Levantamento", String.valueOf(id));
        }
    }

    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Buscar levantamento por pedido",
            description = "Retorna o levantamento associado a um pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Levantamento do pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Levantamento não encontrado para o pedido informado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorPedido(
            @Parameter(description = "ID do pedido", example = "1", required = true)
            @PathVariable Long pedidoId) {
        try {
            LevantamentoResponseDTO levantamento = levantamentoService.buscarPorPedido(pedidoId);
            return ResponseHandler.ok(levantamento, "Levantamento do pedido encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Levantamento para pedido", String.valueOf(pedidoId));
        }
    }

    @GetMapping("/data")
    @Operation(summary = "Buscar levantamentos por data",
            description = "Retorna todos os levantamentos realizados em uma data específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Levantamentos da data encontrados"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorData(
            @Parameter(description = "Data dos levantamentos (formato: yyyy-MM-dd)", example = "2024-01-15", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<LevantamentoResponseDTO> levantamentos = levantamentoService.buscarPorData(data);
        return ResponseHandler.ok(levantamentos, "Levantamentos da data " + data);
    }

    @GetMapping("/periodo")
    @Operation(summary = "Buscar levantamentos por período",
            description = "Retorna todos os levantamentos realizados entre duas datas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Levantamentos do período encontrados"),
            @ApiResponse(responseCode = "400", description = "Data inicial maior que data final"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorPeriodo(
            @Parameter(description = "Data inicial do período (formato: yyyy-MM-dd)", example = "2024-01-01", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @Parameter(description = "Data final do período (formato: yyyy-MM-dd)", example = "2024-12-31", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        if (inicio.isAfter(fim)) {
            return ResponseHandler.badRequest("Data inicial não pode ser maior que a data final");
        }
        List<LevantamentoResponseDTO> levantamentos = levantamentoService.buscarPorPeriodo(inicio, fim);
        return ResponseHandler.ok(levantamentos, "Levantamentos do período");
    }

    @GetMapping("/funcionario/{funcionarioId}")
    @Operation(summary = "Buscar levantamentos por funcionário",
            description = "Retorna todos os levantamentos realizados por um funcionário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Levantamentos realizados pelo funcionário encontrados"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorFuncionario(
            @Parameter(description = "ID do funcionário", example = "1", required = true)
            @PathVariable Long funcionarioId) {
        try {
            List<LevantamentoResponseDTO> levantamentos = levantamentoService.buscarPorFuncionario(funcionarioId);
            return ResponseHandler.ok(levantamentos, "Levantamentos realizados pelo funcionário");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Funcionário", String.valueOf(funcionarioId));
        }
    }

    @GetMapping("/contar/funcionario/{funcionarioId}")
    @Operation(summary = "Contar levantamentos por funcionário",
            description = "Retorna a quantidade total de levantamentos realizados por um funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total de levantamentos do funcionário"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> contarPorFuncionario(
            @Parameter(description = "ID do funcionário", example = "1", required = true)
            @PathVariable Long funcionarioId) {
        try {
            Long quantidade = levantamentoService.contarPorFuncionario(funcionarioId);
            return ResponseHandler.ok(quantidade, "Total de levantamentos realizados pelo funcionário");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Funcionário", String.valueOf(funcionarioId));
        }
    }

    @GetMapping("/ultimos")
    @Operation(summary = "Últimos levantamentos",
            description = "Retorna os últimos 10 levantamentos registrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Últimos 10 levantamentos obtidos com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> ultimosLevantamentos() {
        List<LevantamentoResponseDTO> levantamentos = levantamentoService.buscarUltimosLevantamentos();
        return ResponseHandler.ok(levantamentos, "Últimos 10 levantamentos");
    }
}