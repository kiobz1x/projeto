package exceptions.emprestimo;

public class DevolucaoInvalidaException  extends Exception {
    public DevolucaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
