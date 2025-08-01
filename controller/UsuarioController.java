package controller;

import dao.LeitorDAO;
import dao.UsuarioDAO;
import exception.usuario.UsuarioJaExisteException;
import model.Usuario;

import java.util.List;

public class UsuarioController {
    private final UsuarioDAO dao;
    private final LeitorDAO leitorDAO; // para evitar conflito com leitores
    private List<Usuario> usuarios;

    public UsuarioController() {
        this.dao = new UsuarioDAO();
        this.leitorDAO = new LeitorDAO();
        this.usuarios = dao.carregar();
    }

    public void adicionarUsuario(Usuario novo) throws UsuarioJaExisteException {
        // Gera matrícula automaticamente
        String novaMatricula = gerarMatriculaUsuario();
        novo.setMatricula(novaMatricula);

        // Verifica se matrícula já existe em usuários ou leitores
        boolean jaExisteComoUsuario = buscarPorMatricula(novaMatricula) != null;
        boolean jaExisteComoLeitor = leitorDAO.buscarPorMatricula(novaMatricula) != null;

        if (jaExisteComoUsuario || jaExisteComoLeitor) {
            throw new UsuarioJaExisteException("Usuário já existe com matrícula: " + novaMatricula);
        }

        usuarios.add(novo);
        dao.salvar(usuarios);
        System.out.println("✅ Usuário adicionado com sucesso. Matrícula: " + novaMatricula);
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

    // Geração automática da matrícula no formato USU-00001
    private String gerarMatriculaUsuario() {
        int maiorNumero = 0;

        // Verifica entre usuários
        for (Usuario u : usuarios) {
            String matricula = u.getMatricula();
            if (matricula != null && matricula.startsWith("USU-")) {
                try {
                    int num = Integer.parseInt(matricula.substring(4));
                    if (num > maiorNumero) {
                        maiorNumero = num;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        // Verifica entre leitores para evitar conflito (mesmo número sequencial)
        for (var l : leitorDAO.listar()) {
            String matricula = l.getMatricula();
            if (matricula != null && matricula.startsWith("LEI-")) {
                try {
                    int num = Integer.parseInt(matricula.substring(4));
                    if (num > maiorNumero) {
                        maiorNumero = num;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        return String.format("USU-%05d", maiorNumero + 1);
    }
}
