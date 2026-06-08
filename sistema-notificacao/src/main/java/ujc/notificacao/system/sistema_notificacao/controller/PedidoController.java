package ujc.notificacao.system.sistema_notificacao.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import ujc.notificacao.system.sistema_notificacao.dto.request.PedidoRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.PedidoResponseDTO;
import ujc.notificacao.system.sistema_notificacao.service.PedidoService;
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
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "*")
@Tag(name = "Pedidos", description = "API para gestão de pedidos de documentos académicos")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Criar novo pedido",
            description = "Cria um novo pedido de documento académico para um estudante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados obrigatórios não preenchidos"),
            @ApiResponse(responseCode = "404", description = "Documento ou estudante não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito - pedido duplicado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARIA','ALUNO')")
    public ResponseEntity<?> criar(
            @Parameter(description = "Dados do pedido", required = true)
            @Valid @RequestBody PedidoRequestDTO dto) {
        try {
            PedidoResponseDTO pedido = pedidoService.criarPedido(dto);
            return ResponseHandler.created(pedido, "Pedido criado com sucesso");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("não encontrado")) {
                return ResponseHandler.notFound(msg);
            }
            if (msg.contains("duplicado")) {
                return ResponseHandler.conflict(msg);
            }
            if (msg.contains("obrigatório")) {
                return ResponseHandler.badRequest(msg);
            }
            return ResponseHandler.internalServerError("Erro interno ao criar pedido");
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos os pedidos",
            description = "Retorna uma lista com todos os pedidos cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos obtida com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARIA','ALUNO')")
    public ResponseEntity<?> listarTodos() {
        List<PedidoResponseDTO> pedidos = pedidoService.listarTodos();
        return ResponseHandler.ok(pedidos, "Lista de pedidos obtida com sucesso");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID",
            description = "Retorna um pedido específico baseado no seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARIA','ALUNO')")
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID do pedido", example = "1", required = true)
            @PathVariable Long id) {
        try {
            PedidoResponseDTO pedido = pedidoService.buscarPorId(id);
            return ResponseHandler.ok(pedido, "Pedido encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Pedido", String.valueOf(id));
        }
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar pedido por código",
            description = "Retorna um pedido específico baseado no seu código único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARIA','ALUNO')")
    public ResponseEntity<?> buscarPorCodigo(
            @Parameter(description = "Código do pedido", example = "PED2024001", required = true)
            @PathVariable String codigo) {
        try {
            PedidoResponseDTO pedido = pedidoService.buscarPorCodigo(codigo);
            return ResponseHandler.ok(pedido, "Pedido encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Pedido com código", codigo);
        }
    }

    @GetMapping("/estudante/{estudanteId}")
    @Operation(summary = "Buscar pedidos por estudante",
            description = "Retorna todos os pedidos de um estudante específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos do estudante encontrados"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARIA')")
    public ResponseEntity<?> buscarPorEstudante(
            @Parameter(description = "ID do estudante", example = "1", required = true)
            @PathVariable Long estudanteId) {
        try {
            List<PedidoResponseDTO> pedidos = pedidoService.buscarPorEstudante(estudanteId);
            return ResponseHandler.ok(pedidos, "Pedidos do estudante encontrados");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Estudante", String.valueOf(estudanteId));
        }
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar pedidos por estado",
            description = "Retorna todos os pedidos com o estado informado (PENDENTE, PRONTO, ENTREGUE, RECUSADO, CANCELADO)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados com o estado informado"),
            @ApiResponse(responseCode = "400", description = "Estado inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARIA','ALUNO')")
    public ResponseEntity<?> buscarPorEstado(
            @Parameter(description = "Estado do pedido", example = "PENDENTE", required = true)
            @PathVariable String estado) {
        try {
            List<PedidoResponseDTO> pedidos = pedidoService.buscarPorEstado(estado);
            return ResponseHandler.ok(pedidos, "Pedidos com estado " + estado);
        } catch (RuntimeException e) {
            return ResponseHandler.badRequest("Estado inválido: " + estado);
        }
    }

    @GetMapping("/data")
    @Operation(summary = "Buscar pedidos por data",
            description = "Retorna todos os pedidos realizados em uma data específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos da data encontrados"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARIA','ALUNO')")
    public ResponseEntity<?> buscarPorData(
            @Parameter(description = "Data dos pedidos (formato: yyyy-MM-dd)", example = "2024-01-15", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPorData(data);
        return ResponseHandler.ok(pedidos, "Pedidos da data " + data);
    }

    @GetMapping("/periodo")
    @Operation(summary = "Buscar pedidos por período",
            description = "Retorna todos os pedidos realizados entre duas datas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos do período encontrados"),
            @ApiResponse(responseCode = "400", description = "Data inicial maior que data final"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARIA','ALUNO')")
    public ResponseEntity<?> buscarPorPeriodo(
            @Parameter(description = "Data inicial (formato: yyyy-MM-dd)", example = "2024-01-01", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @Parameter(description = "Data final (formato: yyyy-MM-dd)", example = "2024-12-31", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        if (inicio.isAfter(fim)) {
            return ResponseHandler.badRequest("Data inicial não pode ser maior que a data final");
        }
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPorPeriodo(inicio, fim);
        return ResponseHandler.ok(pedidos, "Pedidos do período");
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Atualizar estado do pedido",
            description = "Atualiza o estado de um pedido (PENDENTE, PRONTO, ENTREGUE, RECUSADO, CANCELADO)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado do pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Estado inválido"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARIA')")
    public ResponseEntity<?> atualizarEstado(
            @Parameter(description = "ID do pedido", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novo estado do pedido", example = "PRONTO", required = true)
            @RequestParam String estado) {
        try {
            PedidoResponseDTO pedido = pedidoService.atualizarEstado(id, estado);
            return ResponseHandler.ok(pedido, "Estado do pedido atualizado para " + estado);
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("não encontrado")) {
                return ResponseHandler.notFound("Pedido", String.valueOf(id));
            }
            if (msg.contains("inválido")) {
                return ResponseHandler.badRequest(msg);
            }
            return ResponseHandler.internalServerError("Erro interno ao atualizar estado");
        }
    }

    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pedido",
            description = "Cancela um pedido (altera estado para CANCELADO)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido não pode ser cancelado (já está em estado terminal)"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARIA','ALUNO')")
    public ResponseEntity<?> cancelarPedido(
            @Parameter(description = "ID do pedido", example = "1", required = true)
            @PathVariable Long id) {
        try {
            pedidoService.cancelarPedido(id);
            return ResponseHandler.ok(null, "Pedido cancelado com sucesso");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("não encontrado")) {
                return ResponseHandler.notFound("Pedido", String.valueOf(id));
            }
            return ResponseHandler.badRequest(msg);
        }
    }

    @GetMapping("/contar/estado")
    @Operation(summary = "Contar pedidos por estado",
            description = "Retorna a quantidade total de pedidos com um determinado estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total de pedidos encontrado"),
            @ApiResponse(responseCode = "400", description = "Estado inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARIA','ALUNO')")
    public ResponseEntity<?> contarPorEstado(
            @Parameter(description = "Estado do pedido", example = "PENDENTE", required = true)
            @RequestParam String estado) {
        try {
            Long quantidade = pedidoService.contarPorEstado(estado);
            return ResponseHandler.ok(quantidade, "Total de pedidos com estado " + estado);
        } catch (RuntimeException e) {
            return ResponseHandler.badRequest("Estado inválido: " + estado);
        }
    }
}