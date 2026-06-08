package ujc.notificacao.system.sistema_notificacao.controller;

import ujc.notificacao.system.sistema_notificacao.dto.CampoDocumentoDTO;
import ujc.notificacao.system.sistema_notificacao.service.CampoDocumentoService;
import ujc.notificacao.system.sistema_notificacao.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/campos-documento")
@CrossOrigin(origins = "*")
@Tag(name = "Campos de Documento", description = "API para gestão dos campos associados aos documentos acadêmicos")
@SecurityRequirement(name = "bearerAuth")
public class CampoDocumentoController {

    @Autowired
    private CampoDocumentoService campoDocumentoService;

    @GetMapping("/documento/{documentoId}")
    @Operation(summary = "Listar campos por documento",
            description = "Retorna todos os campos associados a um documento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campos do documento encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> listarPorDocumento(
            @Parameter(description = "ID do documento", example = "1", required = true)
            @PathVariable Long documentoId) {
        try {
            List<CampoDocumentoDTO> campos = campoDocumentoService.listarCamposPorDocumento(documentoId);
            return ResponseHandler.ok(campos, "Campos do documento encontrados");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Documento", String.valueOf(documentoId));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar campo por ID",
            description = "Retorna um campo específico baseado no seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campo encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Campo não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID do campo", example = "1", required = true)
            @PathVariable Long id) {
        try {
            CampoDocumentoDTO campo = campoDocumentoService.buscarPorId(id);
            return ResponseHandler.ok(campo, "Campo encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Campo", String.valueOf(id));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar campo",
            description = "Atualiza os dados de um campo existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados incorretos"),
            @ApiResponse(responseCode = "404", description = "Campo não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do campo", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do campo", required = true)
            @RequestBody CampoDocumentoDTO dto) {
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
    @Operation(summary = "Remover campo",
            description = "Remove um campo do sistema pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Campo removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Campo não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> remover(
            @Parameter(description = "ID do campo", example = "1", required = true)
            @PathVariable Long id) {
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