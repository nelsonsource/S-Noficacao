package ujc.notificacao.system.sistema_notificacao.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ValidationUtils {
    
    // Validar email
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
    
    // Validar telefone (Moçambique: 8 ou 9 dígitos)
    public static boolean isValidTelefone(String telefone) {
        if (telefone == null) return false;
        String telefoneRegex = "^[89]\\d{8}$";
        return Pattern.compile(telefoneRegex).matcher(telefone).matches();
    }
    
    // Validar número de estudante
    public static boolean isValidNumeroEstudante(String numero) {
        if (numero == null) return false;
        return numero.matches("\\d{8,12}");
    }
    
    // Validar nome (não vazio, mínimo 3 caracteres)
    public static boolean isValidNome(String nome) {
        return nome != null && nome.trim().length() >= 3;
    }
    
    // Validar ano (entre 2000 e ano atual)
    public static boolean isValidAno(Integer ano) {
        if (ano == null) return false;
        int anoAtual = java.time.Year.now().getValue();
        return ano >= 2000 && ano <= anoAtual;
    }
    
    // Validar taxa (positiva)
    public static boolean isValidTaxa(Double taxa) {
        return taxa != null && taxa > 0;
    }
    
    // Validar prazo (positivo)
    public static boolean isValidPrazo(Integer prazo) {
        return prazo != null && prazo > 0;
    }
    
    // Validar campo obrigatório
    public static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}