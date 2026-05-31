package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.request.LevantamentoRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.LevantamentoResponseDTO;
import ujc.notificacao.system.sistema_notificacao.service.LevantamentoService;
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
@RequestMapping("/api/levantamentos")
@CrossOrigin(origins = "*")
public class LevantamentoController {

    @Autowired
    private LevantamentoService levantamentoService;

    // Registrar levantamento
    @PostMapping
    public ResponseEntity<ApiResponse<LevantamentoResponseDTO>> registrar(@Valid @RequestBody LevantamentoRequestDTO dto) {
        try {
            LevantamentoResponseDTO levantamento = levantamentoService.registrarLevantamento(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.created(levantamento, "Levantamento registrado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage(), 400));
        }
    }

    // Listar todos os levantamentos
    @GetMapping
    public ResponseEntity<ApiResponse<List<LevantamentoResponseDTO>>> listarTodos() {
        List<LevantamentoResponseDTO> levantamentos = levantamentoService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success(levantamentos, "Lista de levantamentos obtida com sucesso"));
    }

    // Buscar levantamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LevantamentoResponseDTO>> buscarPorId(@PathVariable Long id) {
        try {
            LevantamentoResponseDTO levantamento = levantamentoService.buscarPorId(id);
            return ResponseEntity.ok(ApiResponse.success(levantamento, "Levantamento encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Buscar por pedido
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<ApiResponse<LevantamentoResponseDTO>> buscarPorPedido(@PathVariable Long pedidoId) {
        try {
            LevantamentoResponseDTO levantamento = levantamentoService.buscarPorPedido(pedidoId);
            return ResponseEntity.ok(ApiResponse.success(levantamento, "Levantamento do pedido encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Buscar por data
    @GetMapping("/data")
    public ResponseEntity<ApiResponse<List<LevantamentoResponseDTO>>> buscarPorData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<LevantamentoResponseDTO> levantamentos = levantamentoService.buscarPorData(data);
        return ResponseEntity.ok(ApiResponse.success(levantamentos, "Levantamentos da data " + data));
    }

    // Buscar por período
    @GetMapping("/periodo")
    public ResponseEntity<ApiResponse<List<LevantamentoResponseDTO>>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        List<LevantamentoResponseDTO> levantamentos = levantamentoService.buscarPorPeriodo(inicio, fim);
        return ResponseEntity.ok(ApiResponse.success(levantamentos, "Levantamentos do período"));
    }

    // Buscar por funcionário
    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<ApiResponse<List<LevantamentoResponseDTO>>> buscarPorFuncionario(@PathVariable Long funcionarioId) {
        try {
            List<LevantamentoResponseDTO> levantamentos = levantamentoService.buscarPorFuncionario(funcionarioId);
            return ResponseEntity.ok(ApiResponse.success(levantamentos, "Levantamentos realizados pelo funcionário"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Contar por funcionário
    @GetMapping("/contar/funcionario/{funcionarioId}")
    public ResponseEntity<ApiResponse<Long>> contarPorFuncionario(@PathVariable Long funcionarioId) {
        try {
            Long quantidade = levantamentoService.contarPorFuncionario(funcionarioId);
            return ResponseEntity.ok(ApiResponse.success(quantidade, "Total de levantamentos realizados pelo funcionário"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Últimos levantamentos
    @GetMapping("/ultimos")
    public ResponseEntity<ApiResponse<List<LevantamentoResponseDTO>>> ultimosLevantamentos() {
        List<LevantamentoResponseDTO> levantamentos = levantamentoService.buscarUltimosLevantamentos();
        return ResponseEntity.ok(ApiResponse.success(levantamentos, "Últimos 10 levantamentos"));
    }
}