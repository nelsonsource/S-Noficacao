package ujc.notificacao.system.sistema_notificacao.dto.list;

public class EstudanteListDTO {
    private Long id;
    private String nomeCompleto;
    private String numeroEstudante;
    private String email;

    public EstudanteListDTO(ujc.notificacao.system.sistema_notificacao.entity.Estudante estudante) {
        this.id = estudante.getId();
        this.nomeCompleto = estudante.getNome() + " " + estudante.getApelido();
        this.numeroEstudante = estudante.getNumeroEstudante();
        this.email = estudante.getEmail();
    }

    // Getters
    public Long getId() { return id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public String getNumeroEstudante() { return numeroEstudante; }
    public String getEmail() { return email; }
}