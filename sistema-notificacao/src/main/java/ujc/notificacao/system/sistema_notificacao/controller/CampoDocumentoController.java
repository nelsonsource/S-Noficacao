package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.CampoDocumentoDTO;
import ujc.notificacao.system.sistema_notificacao.service.CampoDocumentoService;
import ujc.notificacao.system.sistema_notificacao.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/campos-documento")
@CrossOrigin(origins = "*")
public class CampoDocumentoController {

    @Autowired
    private CampoDocumentoService campoDocumentoService;

    // Listar campos de um documento
    @GetMapping("/documento/{documentoId}")
    public ResponseEntity<ApiResponse<List<CampoDocumentoDTO>>> listarPorDocumento(@PathVariable Long documentoId) {
        List<CampoDocumentoDTO> campos = campoDocumentoService.listarCamposPorDocumento(documentoId);
        return ResponseEntity.ok(ApiResponse.success(campos, "Campos do documento encontrados"));
    }

    // Buscar campo por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CampoDocumentoDTO>> buscarPorId(@PathVariable Long id) {
        try {
            CampoDocumentoDTO campo = campoDocumentoService.buscarPorId(id);
            return ResponseEntity.ok(ApiResponse.success(campo, "Campo encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Atualizar campo
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CampoDocumentoDTO>> atualizar(@PathVariable Long id, @RequestBody CampoDocumentoDTO dto) {
        try {
            CampoDocumentoDTO campo = campoDocumentoService.atualizarCampo(id, dto);
            return ResponseEntity.ok(ApiResponse.success(campo, "Campo atualizado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Remover campo
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> remover(@PathVariable Long id) {
        try {
            campoDocumentoService.removerCampo(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Campo removido com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }
}