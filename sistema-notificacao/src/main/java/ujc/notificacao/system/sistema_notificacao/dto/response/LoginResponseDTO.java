// LoginResponseDTO.java
package ujc.notificacao.system.sistema_notificacao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String type = "Bearer";
    private String email;
    private String perfil;

    public LoginResponseDTO(String token, String email, String perfil) {
        this.token = token;
        this.email = email;
        this.perfil = perfil;
    }
}