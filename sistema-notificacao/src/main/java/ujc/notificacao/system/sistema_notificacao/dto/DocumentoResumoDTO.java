package ujc.notificacao.system.sistema_notificacao.dto;

public class DocumentoResumoDTO {
    private Long id;
    private String codigo;
    private String nomeDocumento;
    private Double taxa;

    public DocumentoResumoDTO(ujc.notificacao.system.sistema_notificacao.entity.Documento documento) {
        if (documento != null) {
            this.id = documento.getId();
            this.codigo = documento.getCodigo();
            this.nomeDocumento = documento.getNomeDocumento();
            this.taxa = documento.getTaxa();
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNomeDocumento() { return nomeDocumento; }
    public Double getTaxa() { return taxa; }
}