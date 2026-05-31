package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.request.PedidoRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.PedidoResponseDTO;
import ujc.notificacao.system.sistema_notificacao.service.PedidoService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody PedidoRequestDTO dto) {
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
    public ResponseEntity<?> listarTodos() {
        List<PedidoResponseDTO> pedidos = pedidoService.listarTodos();
        return ResponseHandler.ok(pedidos, "Lista de pedidos obtida com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            PedidoResponseDTO pedido = pedidoService.buscarPorId(id);
            return ResponseHandler.ok(pedido, "Pedido encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Pedido", String.valueOf(id));
        }
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<?> buscarPorCodigo(@PathVariable String codigo) {
        try {
            PedidoResponseDTO pedido = pedidoService.buscarPorCodigo(codigo);
            return ResponseHandler.ok(pedido, "Pedido encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Pedido com código", codigo);
        }
    }

    @GetMapping("/estudante/{estudanteId}")
    public ResponseEntity<?> buscarPorEstudante(@PathVariable Long estudanteId) {
        try {
            List<PedidoResponseDTO> pedidos = pedidoService.buscarPorEstudante(estudanteId);
            return ResponseHandler.ok(pedidos, "Pedidos do estudante encontrados");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Estudante", String.valueOf(estudanteId));
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> buscarPorEstado(@PathVariable String estado) {
        try {
            List<PedidoResponseDTO> pedidos = pedidoService.buscarPorEstado(estado);
            return ResponseHandler.ok(pedidos, "Pedidos com estado " + estado);
        } catch (RuntimeException e) {
            return ResponseHandler.badRequest("Estado inválido: " + estado);
        }
    }

    @GetMapping("/data")
    public ResponseEntity<?> buscarPorData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPorData(data);
        return ResponseHandler.ok(pedidos, "Pedidos da data " + data);
    }

    @GetMapping("/periodo")
    public ResponseEntity<?> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        if (inicio.isAfter(fim)) {
            return ResponseHandler.badRequest("Data inicial não pode ser maior que a data final");
        }
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPorPeriodo(inicio, fim);
        return ResponseHandler.ok(pedidos, "Pedidos do período");
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> atualizarEstado(@PathVariable Long id, @RequestParam String estado) {
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
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
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
    public ResponseEntity<?> contarPorEstado(@RequestParam String estado) {
        try {
            Long quantidade = pedidoService.contarPorEstado(estado);
            return ResponseHandler.ok(quantidade, "Total de pedidos com estado " + estado);
        } catch (RuntimeException e) {
            return ResponseHandler.badRequest("Estado inválido: " + estado);
        }
    }
}