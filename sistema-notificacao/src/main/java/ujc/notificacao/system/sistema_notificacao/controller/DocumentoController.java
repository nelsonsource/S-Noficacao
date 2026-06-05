package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.request.DocumentoRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.DocumentoResponseDTO;
import ujc.notificacao.system.sistema_notificacao.dto.CampoDocumentoDTO;
import ujc.notificacao.system.sistema_notificacao.service.DocumentoService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documento")
@CrossOrigin(origins = "*")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody DocumentoRequestDTO dto) {
        try {
            DocumentoResponseDTO documento = documentoService.criarDocumento(dto);
            return ResponseHandler.created(documento, "Documento criado com sucesso");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("duplicado")) {
                return ResponseHandler.conflict(msg);
            }
            if (msg.contains("inválido") || msg.contains("obrigatório")) {
                return ResponseHandler.badRequest(msg);
            }
            return ResponseHandler.internalServerError("Erro interno ao criar documento");
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<DocumentoResponseDTO> documentos = documentoService.listarTodos();
        return ResponseHandler.ok(documentos, "Lista de documentos obtida com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            DocumentoResponseDTO documento = documentoService.buscarPorId(id);
            return ResponseHandler.ok(documento, "Documento encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Documento", String.valueOf(id));
        }
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<?> buscarPorCodigo(@PathVariable String codigo) {
        try {
            DocumentoResponseDTO documento = documentoService.buscarPorCodigo(codigo);
            return ResponseHandler.ok(documento, "Documento encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Documento com código", codigo);
        }
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        List<DocumentoResponseDTO> documentos = documentoService.buscarPorNome(nome);
        if (documentos.isEmpty()) {
            return ResponseHandler.ok(documentos, "Nenhum documento encontrado com o nome: " + nome);
        }
        return ResponseHandler.ok(documentos, "Documentos encontrados");
    }

    @GetMapping("/buscar/taxa")
    public ResponseEntity<?> buscarPorFaixaTaxa(@RequestParam Double min, @RequestParam Double max) {
        if (min > max) {
            return ResponseHandler.badRequest("O valor mínimo não pode ser maior que o máximo");
        }
        List<DocumentoResponseDTO> documentos = documentoService.buscarPorFaixaTaxa(min, max);
        return ResponseHandler.ok(documentos, "Documentos encontrados na faixa de taxa");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody DocumentoRequestDTO dto) {
        try {
            DocumentoResponseDTO documento = documentoService.atualizarDocumento(id, dto);
            return ResponseHandler.ok(documento, "Documento atualizado com sucesso");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("não encontrado")) {
                return ResponseHandler.notFound("Documento", String.valueOf(id));
            }
            if (msg.contains("duplicado")) {
                return ResponseHandler.conflict(msg);
            }
            if (msg.contains("inválido")) {
                return ResponseHandler.badRequest(msg);
            }
            return ResponseHandler.internalServerError("Erro interno ao atualizar documento");
        }
    }

    @PostMapping("/{documentoId}/campos")
    public ResponseEntity<?> adicionarCampo(@PathVariable Long documentoId, @RequestBody CampoDocumentoDTO campoDTO) {
        try {
            DocumentoResponseDTO documento = documentoService.adicionarCampoAoDocumento(documentoId, campoDTO);
            return ResponseHandler.ok(documento, "Campo adicionado com sucesso");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseHandler.notFound("Documento", String.valueOf(documentoId));
            }
            return ResponseHandler.badRequest(e.getMessage());
        }
    }

    @DeleteMapping("/{documentoId}/campos/{campoId}")
    public ResponseEntity<?> removerCampo(@PathVariable Long documentoId, @PathVariable Long campoId) {
        try {
            DocumentoResponseDTO documento = documentoService.removerCampoDoDocumento(documentoId, campoId);
            return ResponseHandler.ok(documento, "Campo removido com sucesso");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("não encontrado")) {
                return ResponseHandler.notFound("Campo", String.valueOf(campoId));
            }
            return ResponseHandler.badRequest(msg);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            documentoService.deletarDocumento(id);
            return ResponseHandler.noContent("Documento deletado com sucesso");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("não encontrado")) {
                return ResponseHandler.notFound("Documento", String.valueOf(id));
            }
            if (msg.contains("pedidos associados") || msg.contains("foreign key")) {
                return ResponseHandler.conflict("Não é possível deletar documento com pedidos associados");
            }
            return ResponseHandler.internalServerError("Erro interno ao deletar documento");
        }
    }
}