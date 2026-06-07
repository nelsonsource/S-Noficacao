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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/documento")
@CrossOrigin(origins = "*")
@Tag(name = "Documentos", description = "API para gestão de documentos académicos (declarações, certificados, etc.)")
@SecurityRequirement(name = "basicAuth")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping
    @Operation(summary = "Criar novo documento",
            description = "Cadastra um novo documento no sistema com base nos dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Documento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou obrigatórios não preenchidos"),
            @ApiResponse(responseCode = "409", description = "Conflito - código duplicado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> criar(
            @Parameter(description = "Dados do documento a ser criado", required = true)
            @Valid @RequestBody DocumentoRequestDTO dto) {
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
    @Operation(summary = "Listar todos os documentos",
            description = "Retorna uma lista com todos os documentos cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de documentos obtida com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> listarTodos() {
        List<DocumentoResponseDTO> documentos = documentoService.listarTodos();
        return ResponseHandler.ok(documentos, "Lista de documentos obtida com sucesso");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar documento por ID",
            description = "Retorna um documento específico baseado no seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID do documento", example = "1", required = true)
            @PathVariable Long id) {
        try {
            DocumentoResponseDTO documento = documentoService.buscarPorId(id);
            return ResponseHandler.ok(documento, "Documento encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Documento", String.valueOf(id));
        }
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar documento por código",
            description = "Retorna um documento específico baseado no seu código único (ex: DEC001, CERT002)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorCodigo(
            @Parameter(description = "Código único do documento", example = "DEC001", required = true)
            @PathVariable String codigo) {
        try {
            DocumentoResponseDTO documento = documentoService.buscarPorCodigo(codigo);
            return ResponseHandler.ok(documento, "Documento encontrado");
        } catch (RuntimeException e) {
            return ResponseHandler.notFound("Documento com código", codigo);
        }
    }

    @GetMapping("/buscar/nome")
    @Operation(summary = "Buscar documentos por nome",
            description = "Retorna uma lista de documentos cujo nome contenha o texto informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos encontrados (pode ser lista vazia)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorNome(
            @Parameter(description = "Nome ou parte do nome do documento", example = "Declaração", required = true)
            @RequestParam String nome) {
        List<DocumentoResponseDTO> documentos = documentoService.buscarPorNome(nome);
        if (documentos.isEmpty()) {
            return ResponseHandler.ok(documentos, "Nenhum documento encontrado com o nome: " + nome);
        }
        return ResponseHandler.ok(documentos, "Documentos encontrados");
    }

    @GetMapping("/buscar/taxa")
    @Operation(summary = "Buscar documentos por faixa de taxa",
            description = "Retorna documentos cujo valor da taxa esteja entre o mínimo e máximo informados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos encontrados na faixa"),
            @ApiResponse(responseCode = "400", description = "Valor mínimo maior que valor máximo"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorFaixaTaxa(
            @Parameter(description = "Valor mínimo da taxa", example = "100", required = true)
            @RequestParam Double min,
            @Parameter(description = "Valor máximo da taxa", example = "1000", required = true)
            @RequestParam Double max) {
        if (min > max) {
            return ResponseHandler.badRequest("O valor mínimo não pode ser maior que o máximo");
        }
        List<DocumentoResponseDTO> documentos = documentoService.buscarPorFaixaTaxa(min, max);
        return ResponseHandler.ok(documentos, "Documentos encontrados na faixa de taxa");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar documento",
            description = "Atualiza os dados de um documento existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito - código duplicado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do documento", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do documento", required = true)
            @Valid @RequestBody DocumentoRequestDTO dto) {
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
    @Operation(summary = "Adicionar campo ao documento",
            description = "Adiciona um novo campo personalizado a um documento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campo adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados do campo inválidos"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> adicionarCampo(
            @Parameter(description = "ID do documento", example = "1", required = true)
            @PathVariable Long documentoId,
            @Parameter(description = "Dados do campo a ser adicionado", required = true)
            @RequestBody CampoDocumentoDTO campoDTO) {
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
    @Operation(summary = "Remover campo do documento",
            description = "Remove um campo personalizado de um documento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campo removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "404", description = "Campo ou documento não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> removerCampo(
            @Parameter(description = "ID do documento", example = "1", required = true)
            @PathVariable Long documentoId,
            @Parameter(description = "ID do campo a ser removido", example = "1", required = true)
            @PathVariable Long campoId) {
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
    @Operation(summary = "Deletar documento",
            description = "Remove um documento do sistema pelo seu ID. Não permite deletar documentos com pedidos associados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Documento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito - documento possui pedidos associados"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do documento", example = "1", required = true)
            @PathVariable Long id) {
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