package controller;

import dao.UsuarioDAO;
import model.Usuario;

import java.util.List;

public class UsuarioController {
    private final UsuarioDAO dao;
    private List<Usuario> usuarios;

    public UsuarioController() {
        this.dao = new UsuarioDAO();
        this.usuarios = dao.carregar();
    }

    public boolean adicionarUsuario(Usuario novo) {
        // Verifica se já existe matrícula
        for (Usuario u : usuarios) {
            if (u.getMatricula().equals(novo.getMatricula())) {
                System.out.println("❌ Já existe um usuário com essa matrícula.");
                return false;
            }
        }
        usuarios.add(novo);
        dao.salvar(usuarios);
        System.out.println("✅ Usuário adicionado com sucesso.");
        return true;
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
            if (u.getMatricula().equals(matricula)) {
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
            if (u.getMatricula().equals(matricula)) {
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
