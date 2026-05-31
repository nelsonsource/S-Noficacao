package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.request.DocumentoRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.DocumentoResponseDTO;
import ujc.notificacao.system.sistema_notificacao.dto.CampoDocumentoDTO;
import ujc.notificacao.system.sistema_notificacao.service.DocumentoService;
import ujc.notificacao.system.sistema_notificacao.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    // Criar documento
    @PostMapping
    public ResponseEntity<ApiResponse<DocumentoResponseDTO>> criar(@Valid @RequestBody DocumentoRequestDTO dto) {
        try {
            DocumentoResponseDTO documento = documentoService.criarDocumento(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.created(documento, "Documento criado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage(), 400));
        }
    }

    // Listar todos os documentos
    @GetMapping
    public ResponseEntity<ApiResponse<List<DocumentoResponseDTO>>> listarTodos() {
        List<DocumentoResponseDTO> documentos = documentoService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success(documentos, "Lista de documentos obtida com sucesso"));
    }

    // Buscar documento por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentoResponseDTO>> buscarPorId(@PathVariable Long id) {
        try {
            DocumentoResponseDTO documento = documentoService.buscarPorId(id);
            return ResponseEntity.ok(ApiResponse.success(documento, "Documento encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Buscar por código
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ApiResponse<DocumentoResponseDTO>> buscarPorCodigo(@PathVariable String codigo) {
        try {
            DocumentoResponseDTO documento = documentoService.buscarPorCodigo(codigo);
            return ResponseEntity.ok(ApiResponse.success(documento, "Documento encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }

    // Buscar por nome
    @GetMapping("/buscar/nome")
    public ResponseEntity<ApiResponse<List<DocumentoResponseDTO>>> buscarPorNome(@RequestParam String nome) {
        List<DocumentoResponseDTO> documentos = documentoService.buscarPorNome(nome);
        return ResponseEntity.ok(ApiResponse.success(documentos, "Documentos encontrados"));
    }

    // Buscar por faixa de taxa
    @GetMapping("/buscar/taxa")
    public ResponseEntity<ApiResponse<List<DocumentoResponseDTO>>> buscarPorFaixaTaxa(@RequestParam Double min, @RequestParam Double max) {
        List<DocumentoResponseDTO> documentos = documentoService.buscarPorFaixaTaxa(min, max);
        return ResponseEntity.ok(ApiResponse.success(documentos, "Documentos encontrados na faixa de taxa"));
    }

    // Atualizar documento
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentoResponseDTO>> atualizar(@PathVariable Long id, @Valid @RequestBody DocumentoRequestDTO dto) {
        try {
            DocumentoResponseDTO documento = documentoService.atualizarDocumento(id, dto);
            return ResponseEntity.ok(ApiResponse.success(documento, "Documento atualizado com sucesso"));
        } catch (RuntimeException e) {
            HttpStatus status = e.getMessage().contains("não encontrado") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                    .body(ApiResponse.error(e.getMessage(), status.value()));
        }
    }

    // Adicionar campo ao documento
    @PostMapping("/{documentoId}/campos")
    public ResponseEntity<ApiResponse<DocumentoResponseDTO>> adicionarCampo(@PathVariable Long documentoId, @RequestBody CampoDocumentoDTO campoDTO) {
        try {
            DocumentoResponseDTO documento = documentoService.adicionarCampoAoDocumento(documentoId, campoDTO);
            return ResponseEntity.ok(ApiResponse.success(documento, "Campo adicionado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage(), 400));
        }
    }

    // Remover campo do documento
    @DeleteMapping("/{documentoId}/campos/{campoId}")
    public ResponseEntity<ApiResponse<DocumentoResponseDTO>> removerCampo(@PathVariable Long documentoId, @PathVariable Long campoId) {
        try {
            DocumentoResponseDTO documento = documentoService.removerCampoDoDocumento(documentoId, campoId);
            return ResponseEntity.ok(ApiResponse.success(documento, "Campo removido com sucesso"));
        } catch (RuntimeException e) {
            HttpStatus status = e.getMessage().contains("não encontrado") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                    .body(ApiResponse.error(e.getMessage(), status.value()));
        }
    }

    // Deletar documento
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        try {
            documentoService.deletarDocumento(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Documento deletado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        }
    }
}