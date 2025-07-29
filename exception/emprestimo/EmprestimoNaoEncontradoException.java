package exception.emprestimo;

public class EmprestimoNaoEncontradoException  extends Exception {
	private static final long serialVersionUID = 1L;
    public EmprestimoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
