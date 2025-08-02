package exception.usuario;

public class UsuarioJaExisteException extends Exception {
    private static final long serialVersionUID = 1L;

    public UsuarioJaExisteException() {
        super("Usuário já está cadastrado no sistema.");
    }

    public UsuarioJaExisteException(String message) {
        super(message);
    }
}
