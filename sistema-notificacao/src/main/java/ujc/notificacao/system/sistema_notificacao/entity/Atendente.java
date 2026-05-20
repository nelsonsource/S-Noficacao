package ujc.notificacao.system.sistema_notificacao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "atendente")
public class Atendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atendente")
    private Long idAtendente;

    @Column(name = "codigo_atendente", nullable = false, unique = true, length = 20)
    private String codigoAtendente;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 50)
    private String apelido;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;

    public Atendente() {
    }

    public Long getIdAtendente() {
        return idAtendente;
    }

    public void setIdAtendente(Long idAtendente) {
        this.idAtendente = idAtendente;
    }

    public String getCodigoAtendente() {
        return codigoAtendente;
    }

    public void setCodigoAtendente(String codigoAtendente) {
        this.codigoAtendente = codigoAtendente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}