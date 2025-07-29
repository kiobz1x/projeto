package exception.multa;

public class MultaJaPagaException   extends Exception {
	private static final long serialVersionUID = 1L;
    public MultaJaPagaException(String mensagem) {
        super(mensagem);
    }
}
