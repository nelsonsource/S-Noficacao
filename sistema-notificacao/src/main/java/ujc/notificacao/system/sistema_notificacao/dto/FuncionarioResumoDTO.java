package ujc.notificacao.system.sistema_notificacao.dto;

public class FuncionarioResumoDTO {
    private Long id;
    private String nomeCompleto;
    private String email;

    public FuncionarioResumoDTO(ujc.notificacao.system.sistema_notificacao.entity.Funcionario funcionario) {
        if (funcionario != null) {
            this.id = funcionario.getId();
            this.nomeCompleto = funcionario.getNome() + " " + funcionario.getApelido();
            this.email = funcionario.getEmail();
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public String getEmail() { return email; }
}