package ujc.notificacao.system.sistema_notificacao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estudante")
public class Estudante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudante")
    private Long idEstudante;

    @Column(name = "codigo_estudante", nullable = false, unique = true, length = 20)
    private String codigoEstudante;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 50)
    private String apelido;

    @Column(length = 100)
    private String curso;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_estudante", nullable = false)
    private EstadoEstudante estadoEstudante;

    public Estudante() {
    }

    public Long getIdEstudante() {
        return idEstudante;
    }

    public void setIdEstudante(Long idEstudante) {
        this.idEstudante = idEstudante;
    }

    public String getCodigoEstudante() {
        return codigoEstudante;
    }

    public void setCodigoEstudante(String codigoEstudante) {
        this.codigoEstudante = codigoEstudante;
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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
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

    public EstadoEstudante getEstadoEstudante() {
        return estadoEstudante;
    }

    public void setEstadoEstudante(EstadoEstudante estadoEstudante) {
        this.estadoEstudante = estadoEstudante;
    }
}