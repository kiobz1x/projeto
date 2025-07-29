package controller;

import dao.UsuarioDAO;
import exception.usuario.UsuarioJaExisteException;
import model.Usuario;

import java.util.List;

public class UsuarioController {
    private final UsuarioDAO dao;
    private List<Usuario> usuarios;

    public UsuarioController() {
        this.dao = new UsuarioDAO();
        this.usuarios = dao.carregar();
    }

    public void adicionarUsuario(Usuario novo) throws UsuarioJaExisteException {
        // Verifica se já existe matrícula
        for (Usuario u : usuarios) {
            if (u.getMatricula().equalsIgnoreCase(novo.getMatricula())) {
                throw new UsuarioJaExisteException();
            }
        }
        usuarios.add(novo);
        dao.salvar(usuarios);
        System.out.println("✅ Usuário adicionado com sucesso.");
    }

    public Usuario buscarPorMatricula(String matricula) {
        for (Usuario u : usuarios) {
            if (u.getMatricula().equalsIgnoreCase(matricula)) {
                return u;
            }
        }
        return null;
    }

    public boolean editarUsuario(String matricula, String novoTelefone, String novoEmail) {
        for (Usuario u : usuarios) {
            if (u.getMatricula().equalsIgnoreCase(matricula)) {
                u.setTelefone(novoTelefone);
                u.setEmail(novoEmail);
                dao.salvar(usuarios);
                System.out.println("✅ Usuário atualizado com sucesso.");
                return true;
            }
        }
        System.out.println("❌ Usuário não encontrado.");
        return false;
    }

    public boolean removerUsuario(String matricula) {
        for (Usuario u : usuarios) {
            if (u.getMatricula().equalsIgnoreCase(matricula)) {
                usuarios.remove(u);
                dao.salvar(usuarios);
                System.out.println("✅ Usuário removido com sucesso.");
                return true;
            }
        }
        System.out.println("❌ Usuário não encontrado.");
        return false;
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("📭 Nenhum usuário cadastrado.");
        } else {
            System.out.println("📚 Lista de usuários:");
            for (Usuario u : usuarios) {
                System.out.println(u);
            }
        }
    }
}
