package ujc.notificacao.system.sistema_notificacao.dto;

public class EstudanteResumoDTO {
    private Long id;
    private String nomeCompleto;
    private String numeroEstudante;

    public EstudanteResumoDTO(ujc.notificacao.system.sistema_notificacao.entity.Estudante estudante) {
        if (estudante != null) {
            this.id = estudante.getId();
            this.nomeCompleto = estudante.getNome() + " " + estudante.getApelido();
            this.numeroEstudante = estudante.getNumeroEstudante();
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public String getNumeroEstudante() { return numeroEstudante; }
}