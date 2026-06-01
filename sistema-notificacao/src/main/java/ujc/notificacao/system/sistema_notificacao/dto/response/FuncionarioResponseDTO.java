package ujc.notificacao.system.sistema_notificacao.dto.response;

public class FuncionarioResponseDTO {
    private Long id;
    private String nome;
    private String apelido;
    private String genero;
    private String curso;
    private String telefone;
    private String email;

    public FuncionarioResponseDTO(ujc.notificacao.system.sistema_notificacao.entity.Funcionario funcionario) {
        this.id = funcionario.getId();
        this.nome = funcionario.getNome();
        this.apelido = funcionario.getApelido();
        this.genero = funcionario.getGenero();
        this.curso = funcionario.getCurso();
        this.telefone = funcionario.getTelefone();
        this.email = funcionario.getEmail();
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getApelido() { return apelido; }
    public String getGenero() { return genero; }
    public String getCurso() { return curso; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
}