package controller;

import dao.UsuarioDAO;
import model.Usuario;

import java.util.List;
import java.util.Scanner;

public class LoginController {
    private List<Usuario> usuarios;

    public LoginController() {
        UsuarioDAO dao = new UsuarioDAO();
        this.usuarios = dao.carregar();
    }

    public Usuario fazerLogin(Scanner scanner) {
        System.out.print("🔐 Digite a matrícula: ");
        String matricula = scanner.nextLine();

        for (Usuario u : usuarios) {
            if (u.getMatricula().equals(matricula)) {
                System.out.println("✅ Login bem-sucedido como " + u.getTipo());
                return u;
            }
        }

        System.out.println("❌ Usuário não encontrado.");
        return null;
    }
    public Usuario buscarPorMatricula(String matricula) {
        UsuarioController usuarioController = new UsuarioController();
        return usuarioController.buscarPorMatricula(matricula);
    }

}

}

