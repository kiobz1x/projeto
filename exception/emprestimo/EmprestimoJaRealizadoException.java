package exception.emprestimo;

public class EmprestimoJaRealizadoException extends Exception{
	private static final long serialVersionUID = 1L;
	public EmprestimoJaRealizadoException(String mensagem) {
		super(mensagem);
	}
}
