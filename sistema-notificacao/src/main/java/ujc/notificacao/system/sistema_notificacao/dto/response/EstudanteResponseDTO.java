package ujc.notificacao.system.sistema_notificacao.dto.response;

public class EstudanteResponseDTO {
    private Long id;
    private String nome;
    private String apelido;
    private String numeroEstudante;
    private String genero;
    private String curso;
    private Integer anoIngresso;
    private String email;

    // Construtor a partir da entidade
    public EstudanteResponseDTO(ujc.notificacao.system.sistema_notificacao.entity.Estudante estudante) {
        this.id = estudante.getId();
        this.nome = estudante.getNome();
        this.apelido = estudante.getApelido();
        this.numeroEstudante = estudante.getNumeroEstudante();
        this.genero = estudante.getGenero();
        this.curso = estudante.getCurso();
        this.anoIngresso = estudante.getAnoIngresso();
        this.email = estudante.getEmail();
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getApelido() { return apelido; }
    public String getNumeroEstudante() { return numeroEstudante; }
    public String getGenero() { return genero; }
    public String getCurso() { return curso; }
    public Integer getAnoIngresso() { return anoIngresso; }
    public String getEmail() { return email; }
}