package ujc.notificacao.system.sistema_notificacao.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "atendimento")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atendimento")
    private Long idAtendimento;

    @ManyToOne
    @JoinColumn(name = "id_atendente", nullable = false)
    private Atendente atendente;

    @ManyToOne
    @JoinColumn(name = "id_estudante", nullable = false)
    private Estudante estudante;

    @Column(name = "data_atendimento", nullable = false)
    private LocalDate dataAtendimento;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    public Atendimento() {
    }

    public Long getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(Long idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public LocalDate getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDate dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}