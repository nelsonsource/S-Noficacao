package ujc.notificacao.system.sistema_notificacao.dto.request;


import ujc.notificacao.system.sistema_notificacao.dto.CampoDocumentoDTO;
import java.util.List;
import jakarta.validation.constraints.*;


public class DocumentoRequestDTO {

    @NotBlank(message = "Código é obrigatório")
    private String codigo;

    @NotBlank(message = "Nome do documento é obrigatório")
    private String nomeDocumento;

    @NotNull(message = "Taxa é obrigatória")
    @Positive(message = "Taxa deve ser positiva")
    private Double taxa;

    @NotNull(message = "Prazo de emissão é obrigatório")
    @Positive(message = "Prazo deve ser positivo")
    private Integer prazoEmissao;

    private List<CampoDocumentoDTO> campos;

    // Getters e Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNomeDocumento() { return nomeDocumento; }
    public void setNomeDocumento(String nomeDocumento) { this.nomeDocumento = nomeDocumento; }
    public Double getTaxa() { return taxa; }
    public void setTaxa(Double taxa) { this.taxa = taxa; }
    public Integer getPrazoEmissao() { return prazoEmissao; }
    public void setPrazoEmissao(Integer prazoEmissao) { this.prazoEmissao = prazoEmissao; }
    public List<CampoDocumentoDTO> getCampos() { return campos; }
    public void setCampos(List<CampoDocumentoDTO> campos) { this.campos = campos; }
}