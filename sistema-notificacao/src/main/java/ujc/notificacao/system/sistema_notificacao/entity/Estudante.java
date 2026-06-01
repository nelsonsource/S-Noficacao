package ujc.notificacao.system.sistema_notificacao.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "estudante")
public class Estudante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String apelido;

    @Column(name = "numero_estudante", unique = true, nullable = false, length = 20)
    private String numeroEstudante;

    @Column(nullable = false, length = 20)
    private String genero;

    @Column(nullable = false, length = 100)
    private String curso;

    @Column(name = "ano_ingresso", nullable = false)
    private Integer anoIngresso;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // Construtor padrão
    public Estudante() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNumeroEstudante() {
        return numeroEstudante;
    }

    public void setNumeroEstudante(String numeroEstudante) {
        this.numeroEstudante = numeroEstudante;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Integer getAnoIngresso() {
        return anoIngresso;
    }

    public void setAnoIngresso(Integer anoIngresso) {
        this.anoIngresso = anoIngresso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}