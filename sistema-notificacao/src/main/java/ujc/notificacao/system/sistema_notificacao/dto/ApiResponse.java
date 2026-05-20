package ujc.notificacao.system.sistema_notificacao.dto;

public class ApiResponse<T> {

    private boolean sucesso;
    private int codigo;
    private String mensagem;
    private T dados;

    public ApiResponse() {
    }

    public ApiResponse(boolean sucesso, int codigo, String mensagem, T dados) {
        this.sucesso = sucesso;
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.dados = dados;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public T getDados() {
        return dados;
    }

    public void setDados(T dados) {
        this.dados = dados;
    }
}