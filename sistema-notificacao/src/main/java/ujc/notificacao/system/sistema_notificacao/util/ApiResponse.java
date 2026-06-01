package ujc.notificacao.system.sistema_notificacao.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
    private boolean success;
    private String message;
    private T data;
    private List<String> errors;
    private LocalDateTime timestamp;
    private int statusCode;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(boolean success, String message, T data, int statusCode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(boolean success, String message, List<String> errors, int statusCode) {
        this.success = success;
        this.message = message;
        this.errors = errors;
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now();
    }

    // ==================== SUCESSO (2xx) ====================

    // 200 OK
    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(true, message, data, 200);
    }

    // 201 CREATED
    public static <T> ApiResponse<T> created(T data, String message) {
        return new ApiResponse<>(true, message, data, 201);
    }

    // 202 ACCEPTED
    public static <T> ApiResponse<T> accepted(String message) {
        return new ApiResponse<>(true, message, null, 202);
    }

    // 203 NON-AUTHORITATIVE INFORMATION
    public static <T> ApiResponse<T> nonAuthoritative(T data, String message) {
        return new ApiResponse<>(true, message, data, 203);
    }

    // 204 NO CONTENT
    public static <T> ApiResponse<T> noContent(String message) {
        return new ApiResponse<>(true, message, null, 204);
    }

    // 205 RESET CONTENT
    public static <T> ApiResponse<T> resetContent(String message) {
        return new ApiResponse<>(true, message, null, 205);
    }

    // 206 PARTIAL CONTENT
    public static <T> ApiResponse<T> partialContent(T data, String message) {
        return new ApiResponse<>(true, message, data, 206);
    }

    // ==================== ERRO CLIENTE (4xx) ====================

    // 400 BAD REQUEST
    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(false, message, null, 400);
    }

    public static <T> ApiResponse<T> badRequest(List<String> errors, String message) {
        return new ApiResponse<>(false, message, errors, 400);
    }

    // 401 UNAUTHORIZED
    public static <T> ApiResponse<T> unauthorized(String message) {
        return new ApiResponse<>(false, message, null, 401);
    }

    // 402 PAYMENT REQUIRED
    public static <T> ApiResponse<T> paymentRequired(String message) {
        return new ApiResponse<>(false, message, null, 402);
    }

    // 403 FORBIDDEN
    public static <T> ApiResponse<T> forbidden(String message) {
        return new ApiResponse<>(false, message, null, 403);
    }

    // 404 NOT FOUND
    public static <T> ApiResponse<T> notFound(String entityName) {
        return new ApiResponse<>(false, entityName + " não encontrado(a)", null, 404);
    }

    public static <T> ApiResponse<T> notFound(String entityName, String id) {
        return new ApiResponse<>(false, entityName + " com ID " + id + " não encontrado", null, 404);
    }

    // 405 METHOD NOT ALLOWED
    public static <T> ApiResponse<T> methodNotAllowed(String message) {
        return new ApiResponse<>(false, message, null, 405);
    }

    // 406 NOT ACCEPTABLE
    public static <T> ApiResponse<T> notAcceptable(String message) {
        return new ApiResponse<>(false, message, null, 406);
    }

    // 407 PROXY AUTHENTICATION REQUIRED
    public static <T> ApiResponse<T> proxyAuthenticationRequired(String message) {
        return new ApiResponse<>(false, message, null, 407);
    }

    // 408 REQUEST TIMEOUT
    public static <T> ApiResponse<T> requestTimeout(String message) {
        return new ApiResponse<>(false, message, null, 408);
    }

    // 409 CONFLICT
    public static <T> ApiResponse<T> conflict(String message) {
        return new ApiResponse<>(false, message, null, 409);
    }

    // 410 GONE
    public static <T> ApiResponse<T> gone(String message) {
        return new ApiResponse<>(false, message, null, 410);
    }

    // 411 LENGTH REQUIRED
    public static <T> ApiResponse<T> lengthRequired(String message) {
        return new ApiResponse<>(false, message, null, 411);
    }

    // 412 PRECONDITION FAILED
    public static <T> ApiResponse<T> preconditionFailed(String message) {
        return new ApiResponse<>(false, message, null, 412);
    }

    // 413 PAYLOAD TOO LARGE
    public static <T> ApiResponse<T> payloadTooLarge(String message) {
        return new ApiResponse<>(false, message, null, 413);
    }

    // 414 URI TOO LONG
    public static <T> ApiResponse<T> uriTooLong(String message) {
        return new ApiResponse<>(false, message, null, 414);
    }

    // 415 UNSUPPORTED MEDIA TYPE
    public static <T> ApiResponse<T> unsupportedMediaType(String message) {
        return new ApiResponse<>(false, message, null, 415);
    }

    // 416 RANGE NOT SATISFIABLE
    public static <T> ApiResponse<T> rangeNotSatisfiable(String message) {
        return new ApiResponse<>(false, message, null, 416);
    }

    // 417 EXPECTATION FAILED
    public static <T> ApiResponse<T> expectationFailed(String message) {
        return new ApiResponse<>(false, message, null, 417);
    }

    // 418 I'M A TEAPOT (April Fools)
    public static <T> ApiResponse<T> imATeapot(String message) {
        return new ApiResponse<>(false, message, null, 418);
    }

    // 422 UNPROCESSABLE ENTITY
    public static <T> ApiResponse<T> unprocessableEntity(String message) {
        return new ApiResponse<>(false, message, null, 422);
    }

    public static <T> ApiResponse<T> unprocessableEntity(List<String> errors, String message) {
        return new ApiResponse<>(false, message, errors, 422);
    }

    // 423 LOCKED
    public static <T> ApiResponse<T> locked(String message) {
        return new ApiResponse<>(false, message, null, 423);
    }

    // 429 TOO MANY REQUESTS
    public static <T> ApiResponse<T> tooManyRequests(String message) {
        return new ApiResponse<>(false, message, null, 429);
    }

    // ==================== ERRO SERVIDOR (5xx) ====================

    // 500 INTERNAL SERVER ERROR
    public static <T> ApiResponse<T> internalServerError(String message) {
        return new ApiResponse<>(false, message, null, 500);
    }

    public static <T> ApiResponse<T> internalServerError(String message, String details) {
        return new ApiResponse<>(false, message + ": " + details, null, 500);
    }

    // 501 NOT IMPLEMENTED
    public static <T> ApiResponse<T> notImplemented(String message) {
        return new ApiResponse<>(false, message, null, 501);
    }

    // 502 BAD GATEWAY
    public static <T> ApiResponse<T> badGateway(String message) {
        return new ApiResponse<>(false, message, null, 502);
    }

    // 503 SERVICE UNAVAILABLE
    public static <T> ApiResponse<T> serviceUnavailable(String message) {
        return new ApiResponse<>(false, message, null, 503);
    }

    // 504 GATEWAY TIMEOUT
    public static <T> ApiResponse<T> gatewayTimeout(String message) {
        return new ApiResponse<>(false, message, null, 504);
    }

    // 505 HTTP VERSION NOT SUPPORTED
    public static <T> ApiResponse<T> httpVersionNotSupported(String message) {
        return new ApiResponse<>(false, message, null, 505);
    }

    // ==================== MÉTODOS GENÉRICOS ====================

    public static <T> ApiResponse<T> error(String message, int statusCode) {
        return new ApiResponse<>(false, message, null, statusCode);
    }

    public static <T> ApiResponse<T> error(List<String> errors, String message, int statusCode) {
        return new ApiResponse<>(false, message, errors, statusCode);
    }

    public static <T> ApiResponse<T> custom(boolean success, String message, T data, int statusCode) {
        return new ApiResponse<>(success, message, data, statusCode);
    }

    // ==================== GETTERS E SETTERS ====================

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
}