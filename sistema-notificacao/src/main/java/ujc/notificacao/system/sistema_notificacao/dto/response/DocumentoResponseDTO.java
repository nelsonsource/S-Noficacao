package ujc.notificacao.system.sistema_notificacao.dto.response;

import java.util.List;
import java.util.stream.Collectors;

public class DocumentoResponseDTO {
    private Long id;
    private String codigo;
    private String nomeDocumento;
    private Double taxa;
    private Integer prazoEmissao;
    private List<CampoDocumentoDTO> campos;

    public DocumentoResponseDTO(ujc.notificacao.system.sistema_notificacao.entity.Documento documento) {
        this.id = documento.getId();
        this.codigo = documento.getCodigo();
        this.nomeDocumento = documento.getNomeDocumento();
        this.taxa = documento.getTaxa();
        this.prazoEmissao = documento.getPrazoEmissao();
        if (documento.getCampos() != null) {
            this.campos = documento.getCampos().stream()
                .map(CampoDocumentoDTO::new)
                .collect(Collectors.toList());
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNomeDocumento() { return nomeDocumento; }
    public Double getTaxa() { return taxa; }
    public Integer getPrazoEmissao() { return prazoEmissao; }
    public List<CampoDocumentoDTO> getCampos() { return campos; }
}