package ujc.notificacao.system.sistema_notificacao.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campo_documento")
public class CampoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "documento_id", nullable = false)
    private Documento documento;

    @Column(nullable = false, length = 100)
    private String nomeCampo;  // Ex: "nome_pai", "data_nascimento"

    @Column(nullable = false, length = 50)
    private String tipoCampo;  // Ex: "TEXTO", "DATA", "NUMERO"

    private Boolean obrigatorio = false;

    private Integer ordem;

    @OneToMany(mappedBy = "campoDocumento", cascade = CascadeType.ALL)
    private List<PedidoResposta> respostas = new ArrayList<>();

    public CampoDocumento() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Documento getDocumento() { return documento; }
    public void setDocumento(Documento documento) { this.documento = documento; }
    public String getNomeCampo() { return nomeCampo; }
    public void setNomeCampo(String nomeCampo) { this.nomeCampo = nomeCampo; }
    public String getTipoCampo() { return tipoCampo; }
    public void setTipoCampo(String tipoCampo) { this.tipoCampo = tipoCampo; }
    public Boolean getObrigatorio() { return obrigatorio; }
    public void setObrigatorio(Boolean obrigatorio) { this.obrigatorio = obrigatorio; }
    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }
    public List<PedidoResposta> getRespostas() { return respostas; }
    public void setRespostas(List<PedidoResposta> respostas) { this.respostas = respostas; }
}