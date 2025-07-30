package exception;

public class UsuarioJaExisteException extends Exception {
    private static final long serialVersionUID = 1L;

    public UsuarioJaExisteException() {
        super("⚠️ Usuário já está cadastrado no sistema.");
    }

    // Construtor que recebe mensagem personalizada
    public UsuarioJaExisteException(String message) {
        super(message);
    }
}
