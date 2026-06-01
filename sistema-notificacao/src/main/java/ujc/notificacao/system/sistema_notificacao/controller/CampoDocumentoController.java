package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.CampoDocumentoDTO;
import ujc.notificacao.system.sistema_notificacao.service.CampoDocumentoService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/campos-documento")
@CrossOrigin(origins = "*")
public class CampoDocumentoController {

    @Autowired
    private CampoDocumentoService campoDocumentoService;

    @GetMapping("/documento/{documentoId}")
    public ResponseEntity<?> listarPorDocumento(@PathVariable Long documentoId) {
        try {
            List<CampoDocumentoDTO> campos = campoDocumentoService.listarCamposPorDocumento(documentoId);
            return ResponseHandler.ok(campos, "Campos do documento encontrados");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Documento", String.valueOf(documentoId));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            CampoDocumentoDTO campo = campoDocumentoService.buscarPorId(id);
            return ResponseHandler.ok(campo, "Campo encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Campo", String.valueOf(id));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody CampoDocumentoDTO dto) {
        try {
            CampoDocumentoDTO campo = campoDocumentoService.atualizarCampo(id, dto);
            return ResponseHandler.ok(campo, "Campo atualizado com sucesso");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseHandler.notFound("Campo", String.valueOf(id));
            }
            return ResponseHandler.badRequest(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            campoDocumentoService.removerCampo(id);
            return ResponseHandler.noContent("Campo removido com sucesso");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseHandler.notFound("Campo", String.valueOf(id));
            }
            return ResponseHandler.internalServerError("Erro interno ao remover campo");
        }
    }
}