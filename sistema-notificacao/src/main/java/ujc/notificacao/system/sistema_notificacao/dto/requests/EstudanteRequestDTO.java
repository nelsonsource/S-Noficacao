package ujc.notificacao.system.sistema_notificacao.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class EstudanteRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Apelido é obrigatório")
    @Size(min = 3, max = 100, message = "Apelido deve ter entre 3 e 100 caracteres")
    private String apelido;

    @NotBlank(message = "Número de estudante é obrigatório")
    @Pattern(regexp = "\\d{8,12}", message = "Número de estudante deve ter entre 8 e 12 dígitos")
    private String numeroEstudante;

    @NotBlank(message = "Gênero é obrigatório")
    @Pattern(regexp = "^(MASCULINO|FEMININO|OUTRO)$", message = "Gênero inválido")
    private String genero;

    @NotBlank(message = "Curso é obrigatório")
    private String curso;

    @NotNull(message = "Ano de ingresso é obrigatório")
    @Min(value = 2000, message = "Ano de ingresso deve ser maior que 2000")
    @Max(value = 2026, message = "Ano de ingresso não pode ser futuro")
    private Integer anoIngresso;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }
    public String getNumeroEstudante() { return numeroEstudante; }
    public void setNumeroEstudante(String numeroEstudante) { this.numeroEstudante = numeroEstudante; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public Integer getAnoIngresso() { return anoIngresso; }
    public void setAnoIngresso(Integer anoIngresso) { this.anoIngresso = anoIngresso; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}