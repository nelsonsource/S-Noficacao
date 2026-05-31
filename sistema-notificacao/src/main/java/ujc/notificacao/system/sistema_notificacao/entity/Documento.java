package ujc.notificacao.system.sistema_notificacao.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documento")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String nomeDocumento;
    private Double taxa;
    private Integer prazoEmissao;

    @OneToMany(mappedBy = "documento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampoDocumento> campos = new ArrayList<>();

    public Documento() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNomeDocumento() { return nomeDocumento; }
    public void setNomeDocumento(String nomeDocumento) { this.nomeDocumento = nomeDocumento; }
    public Double getTaxa() { return taxa; }
    public void setTaxa(Double taxa) { this.taxa = taxa; }
    public Integer getPrazoEmissao() { return prazoEmissao; }
    public void setPrazoEmissao(Integer prazoEmissao) { this.prazoEmissao = prazoEmissao; }
    public List<CampoDocumento> getCampos() { return campos; }
    public void setCampos(List<CampoDocumento> campos) { this.campos = campos; }
}