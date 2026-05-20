package ujc.notificacao.system.sistema_notificacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ujc.notificacao.system.sistema_notificacao.dto.ApiResponse;
import ujc.notificacao.system.sistema_notificacao.entity.Documento;
import ujc.notificacao.system.sistema_notificacao.service.DocumentoService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping
    public ResponseEntity<ApiResponse<Documento>> salvar(@RequestBody Documento documento) {
        Documento novoDocumento = documentoService.salvar(documento);
        return ResponseHandler.sucesso("Documento cadastrado com sucesso", novoDocumento, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Documento>>> listarTodos() {
        return ResponseHandler.sucesso("Lista de documentos carregada com sucesso",
                documentoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Documento>> buscarPorId(@PathVariable Long id) {
        Optional<Documento> documento = documentoService.buscarPorId(id);

        if (documento.isPresent()) {
            return ResponseHandler.sucesso("Documento encontrado com sucesso", documento.get(), HttpStatus.OK);
        }

        return ResponseHandler.erro("Documento não encontrado", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Documento>> atualizar(@PathVariable Long id, @RequestBody Documento documento) {
        try {
            Documento atualizado = documentoService.atualizar(id, documento);
            return ResponseHandler.sucesso("Documento atualizado com sucesso", atualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseHandler.erro(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            documentoService.eliminar(id);
            return ResponseHandler.sucesso("Documento eliminado com sucesso", null, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseHandler.erro(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}