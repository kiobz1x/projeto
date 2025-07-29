package exception.emprestimo;

public class DevolucaoInvalidaException  extends Exception {
	private static final long serialVersionUID = 1L;
    public DevolucaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
