package ujc.notificacao.system.sistema_notificacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ujc.notificacao.system.sistema_notificacao.dto.ApiResponse;
import ujc.notificacao.system.sistema_notificacao.entity.Pedido;
import ujc.notificacao.system.sistema_notificacao.service.PedidoService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<ApiResponse<Pedido>> salvar(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.salvar(pedido);
        return ResponseHandler.sucesso("Pedido cadastrado com sucesso", novoPedido, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pedido>>> listarTodos() {
        return ResponseHandler.sucesso("Lista de pedidos carregada com sucesso",
                pedidoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Pedido>> buscarPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(id);

        if (pedido.isPresent()) {
            return ResponseHandler.sucesso("Pedido encontrado com sucesso", pedido.get(), HttpStatus.OK);
        }

        return ResponseHandler.erro("Pedido não encontrado", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Pedido>> atualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
        try {
            Pedido atualizado = pedidoService.atualizar(id, pedido);
            return ResponseHandler.sucesso("Pedido atualizado com sucesso", atualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseHandler.erro(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            pedidoService.eliminar(id);
            return ResponseHandler.sucesso("Pedido eliminado com sucesso", null, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseHandler.erro(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}