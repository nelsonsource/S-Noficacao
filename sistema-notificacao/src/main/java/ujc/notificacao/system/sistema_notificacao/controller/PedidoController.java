package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.request.PedidoRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.PedidoResponseDTO;
import ujc.notificacao.system.sistema_notificacao.service.PedidoService;
import ujc.notificacao.system.sistema_notificacao.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Criar pedido
    @PostMapping
    public ResponseEntity<ApiResponse<PedidoResponseDTO>> criar(@Valid @RequestBody PedidoRequestDTO dto) {
        try {
            PedidoResponseDTO pedido = pedidoService.criarPedido(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.created(pedido, "Pedido criado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage(), 400));
        }
    }

    // Listar todos os pedidos
    @GetMapping
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> listarTodos() {
        List<PedidoResponseDTO> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success(pedidos, "Lista de pedidos obtida com sucesso"));
    }

    // Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponseDTO>> buscarPorId(@PathVariable Long id) {
        try {
            PedidoResponseDTO pedido = pedidoService.buscarPorId(id);
            return ResponseEntity.ok(ApiResponse.success(pedido, "Pedido encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Buscar por código
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ApiResponse<PedidoResponseDTO>> buscarPorCodigo(@PathVariable String codigo) {
        try {
            PedidoResponseDTO pedido = pedidoService.buscarPorCodigo(codigo);
            return ResponseEntity.ok(ApiResponse.success(pedido, "Pedido encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Buscar por estudante
    @GetMapping("/estudante/{estudanteId}")
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> buscarPorEstudante(@PathVariable Long estudanteId) {
        try {
            List<PedidoResponseDTO> pedidos = pedidoService.buscarPorEstudante(estudanteId);
            return ResponseEntity.ok(ApiResponse.success(pedidos, "Pedidos do estudante encontrados"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Buscar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> buscarPorEstado(@PathVariable String estado) {
        try {
            List<PedidoResponseDTO> pedidos = pedidoService.buscarPorEstado(estado);
            return ResponseEntity.ok(ApiResponse.success(pedidos, "Pedidos com estado " + estado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage(), 400));
        }
    }

    // Buscar por data
    @GetMapping("/data")
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> buscarPorData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPorData(data);
        return ResponseEntity.ok(ApiResponse.success(pedidos, "Pedidos da data " + data));
    }

    // Buscar por período
    @GetMapping("/periodo")
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPorPeriodo(inicio, fim);
        return ResponseEntity.ok(ApiResponse.success(pedidos, "Pedidos do período"));
    }

    // Atualizar estado do pedido
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<PedidoResponseDTO>> atualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            PedidoResponseDTO pedido = pedidoService.atualizarEstado(id, estado);
            return ResponseEntity.ok(ApiResponse.success(pedido, "Estado do pedido atualizado para " + estado));
        } catch (RuntimeException e) {
            HttpStatus status = e.getMessage().contains("não encontrado") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                    .body(ApiResponse.error(e.getMessage(), status.value()));
        }
    }

    // Cancelar pedido
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponse<Void>> cancelarPedido(@PathVariable Long id) {
        try {
            pedidoService.cancelarPedido(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Pedido cancelado com sucesso"));
        } catch (RuntimeException e) {
            HttpStatus status = e.getMessage().contains("não encontrado") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                    .body(ApiResponse.error(e.getMessage(), status.value()));
        }
    }

    // Contar por estado
    @GetMapping("/contar/estado")
    public ResponseEntity<ApiResponse<Long>> contarPorEstado(@RequestParam String estado) {
        Long quantidade = pedidoService.contarPorEstado(estado);
        return ResponseEntity.ok(ApiResponse.success(quantidade, "Total de pedidos com estado " + estado));
    }
}