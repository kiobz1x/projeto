package exception.obras;

public class ObraNaoDisponivelException extends Exception{
	private static final long serialVersionUID = 1L;

	public ObraNaoDisponivelException(String message) {
		super(message);
	}
}
