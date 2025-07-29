package exception.leitor;

public class LeitorJaExisteException extends Exception{
	private static final long serialVersionUID = 1L;
	public LeitorJaExisteException() {
        super("⚠️ Leitor já está cadastrado no sistema.");
	}
}
