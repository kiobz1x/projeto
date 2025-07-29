package exception.obras;

public class ObraNaoEncontradaException extends Exception{
	private static final long serialVersionUID = 1L;
	public ObraNaoEncontradaException() {
        super("❌ Obra não encontrada no sistema.");
    }
}
