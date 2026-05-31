package ujc.notificacao.system.sistema_notificacao.dto;

public class PedidoRespostaDTO {
    private Long campoDocumentoId;
    private String nomeCampo;
    private String valorResposta;

    public PedidoRespostaDTO() {}

    public PedidoRespostaDTO(ujc.notificacao.system.sistema_notificacao.entity.PedidoResposta resposta) {
        this.campoDocumentoId = resposta.getCampoDocumento().getId();
        this.nomeCampo = resposta.getCampoDocumento().getNomeCampo();
        this.valorResposta = resposta.getValorResposta();
    }

    // Getters e Setters
    public Long getCampoDocumentoId() { return campoDocumentoId; }
    public void setCampoDocumentoId(Long campoDocumentoId) { this.campoDocumentoId = campoDocumentoId; }
    public String getNomeCampo() { return nomeCampo; }
    public void setNomeCampo(String nomeCampo) { this.nomeCampo = nomeCampo; }
    public String getValorResposta() { return valorResposta; }
    public void setValorResposta(String valorResposta) { this.valorResposta = valorResposta; }
}