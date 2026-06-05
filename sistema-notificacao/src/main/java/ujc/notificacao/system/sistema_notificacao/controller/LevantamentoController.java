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
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/levantamento")
@CrossOrigin(origins = "*")
public class LevantamentoController {

    @Autowired
    private LevantamentoService levantamentoService;

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody LevantamentoRequestDTO dto) {
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
    public ResponseEntity<?> listarTodos() {
        List<LevantamentoResponseDTO> levantamentos = levantamentoService.listarTodos();
        return ResponseHandler.ok(levantamentos, "Lista de levantamentos obtida com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            LevantamentoResponseDTO levantamento = levantamentoService.buscarPorId(id);
            return ResponseHandler.ok(levantamento, "Levantamento encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Levantamento", String.valueOf(id));
        }
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<?> buscarPorPedido(@PathVariable Long pedidoId) {
        try {
            LevantamentoResponseDTO levantamento = levantamentoService.buscarPorPedido(pedidoId);
            return ResponseHandler.ok(levantamento, "Levantamento do pedido encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Levantamento para pedido", String.valueOf(pedidoId));
        }
    }

    @GetMapping("/data")
    public ResponseEntity<?> buscarPorData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<LevantamentoResponseDTO> levantamentos = levantamentoService.buscarPorData(data);
        return ResponseHandler.ok(levantamentos, "Levantamentos da data " + data);
    }

    @GetMapping("/periodo")
    public ResponseEntity<?> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        if (inicio.isAfter(fim)) {
            return ResponseHandler.badRequest("Data inicial não pode ser maior que a data final");
        }
        List<LevantamentoResponseDTO> levantamentos = levantamentoService.buscarPorPeriodo(inicio, fim);
        return ResponseHandler.ok(levantamentos, "Levantamentos do período");
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<?> buscarPorFuncionario(@PathVariable Long funcionarioId) {
        try {
            List<LevantamentoResponseDTO> levantamentos = levantamentoService.buscarPorFuncionario(funcionarioId);
            return ResponseHandler.ok(levantamentos, "Levantamentos realizados pelo funcionário");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Funcionário", String.valueOf(funcionarioId));
        }
    }

    @GetMapping("/contar/funcionario/{funcionarioId}")
    public ResponseEntity<?> contarPorFuncionario(@PathVariable Long funcionarioId) {
        try {
            Long quantidade = levantamentoService.contarPorFuncionario(funcionarioId);
            return ResponseHandler.ok(quantidade, "Total de levantamentos realizados pelo funcionário");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Funcionário", String.valueOf(funcionarioId));
        }
    }

    @GetMapping("/ultimos")
    public ResponseEntity<?> ultimosLevantamentos() {
        List<LevantamentoResponseDTO> levantamentos = levantamentoService.buscarUltimosLevantamentos();
        return ResponseHandler.ok(levantamentos, "Últimos 10 levantamentos");
    }
}