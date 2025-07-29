package exception.multa;

public class MultaNaoEncontradaException extends Exception {
	private static final long serialVersionUID = 1L;
    public MultaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
