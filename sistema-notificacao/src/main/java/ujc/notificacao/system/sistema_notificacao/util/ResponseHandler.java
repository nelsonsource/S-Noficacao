package ujc.notificacao.system.sistema_notificacao.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    // 200 OK
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        return ResponseEntity.ok(ApiResponse.ok(data, message));
    }

    // 201 CREATED
    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(data, message));
    }

    // 202 ACCEPTED
    public static <T> ResponseEntity<ApiResponse<T>> accepted(String message) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ApiResponse.accepted(message));
    }

    // 204 NO CONTENT
    public static <T> ResponseEntity<ApiResponse<T>> noContent(String message) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.noContent(message));
    }

    // 400 BAD REQUEST
    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.badRequest(message));
    }

    // 401 UNAUTHORIZED
    public static <T> ResponseEntity<ApiResponse<T>> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.unauthorized(message));
    }

    // 403 FORBIDDEN
    public static <T> ResponseEntity<ApiResponse<T>> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.forbidden(message));
    }

    // 404 NOT FOUND (com 1 parâmetro)
    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.notFound(message));
    }

    // 404 NOT FOUND (com 2 parâmetros - entity e id)
    public static <T> ResponseEntity<ApiResponse<T>> notFound(String entity, String id) {
        String message = entity + " com ID " + id + " não encontrado(a)";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.notFound(message));
    }

    // 409 CONFLICT
    public static <T> ResponseEntity<ApiResponse<T>> conflict(String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.conflict(message));
    }

    // 415 UNSUPPORTED MEDIA TYPE
    public static <T> ResponseEntity<ApiResponse<T>> unsupportedMediaType(String message) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(ApiResponse.unsupportedMediaType(message));
    }

    // 422 UNPROCESSABLE ENTITY
    public static <T> ResponseEntity<ApiResponse<T>> unprocessableEntity(String message) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponse.unprocessableEntity(message));
    }

    // 429 TOO MANY REQUESTS
    public static <T> ResponseEntity<ApiResponse<T>> tooManyRequests(String message) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ApiResponse.tooManyRequests(message));
    }

    // 500 INTERNAL SERVER ERROR
    public static <T> ResponseEntity<ApiResponse<T>> internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.internalServerError(message));
    }

    // 503 SERVICE UNAVAILABLE
    public static <T> ResponseEntity<ApiResponse<T>> serviceUnavailable(String message) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiResponse.serviceUnavailable(message));
    }
}