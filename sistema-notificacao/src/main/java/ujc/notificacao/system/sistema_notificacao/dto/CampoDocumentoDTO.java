package ujc.notificacao.system.sistema_notificacao.dto;

public class CampoDocumentoDTO {
    private Long id;
    private String nomeCampo;
    private String tipoCampo;
    private Boolean obrigatorio;
    private Integer ordem;

    public CampoDocumentoDTO() {}

    public CampoDocumentoDTO(ujc.notificacao.system.sistema_notificacao.entity.CampoDocumento campo) {
        this.id = campo.getId();
        this.nomeCampo = campo.getNomeCampo();
        this.tipoCampo = campo.getTipoCampo();
        this.obrigatorio = campo.getObrigatorio();
        this.ordem = campo.getOrdem();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeCampo() { return nomeCampo; }
    public void setNomeCampo(String nomeCampo) { this.nomeCampo = nomeCampo; }
    public String getTipoCampo() { return tipoCampo; }
    public void setTipoCampo(String tipoCampo) { this.tipoCampo = tipoCampo; }
    public Boolean getObrigatorio() { return obrigatorio; }
    public void setObrigatorio(Boolean obrigatorio) { this.obrigatorio = obrigatorio; }
    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }
}