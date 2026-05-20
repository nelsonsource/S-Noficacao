package ujc.notificacao.system.sistema_notificacao.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ujc.notificacao.system.sistema_notificacao.dto.ApiResponse;

public class ResponseHandler {

    public static <T> ResponseEntity<ApiResponse<T>> sucesso(String mensagem, T dados, HttpStatus status) {
        ApiResponse<T> resposta = new ApiResponse<>(
                true,
                status.value(),
                mensagem,
                dados
        );
        return new ResponseEntity<>(resposta, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> erro(String mensagem, HttpStatus status) {
        ApiResponse<T> resposta = new ApiResponse<>(
                false,
                status.value(),
                mensagem,
                null
        );
        return new ResponseEntity<>(resposta, status);
    }
}