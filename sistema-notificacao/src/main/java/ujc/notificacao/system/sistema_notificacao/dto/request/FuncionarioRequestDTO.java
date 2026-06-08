package ujc.notificacao.system.sistema_notificacao.dto.request;

import jakarta.validation.constraints.*;

public class FuncionarioRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Apelido é obrigatório")
    private String apelido;

    @NotBlank(message = "Gênero é obrigatório")
    private String genero;

    private String curso;

    @Pattern(regexp = "^\\d{9,12}$", message = "Telefone deve ter 9 a 12 dígitos")
    private String telefone;

    @NotBlank(message = "Codigo do funcionario")
    private String codigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}