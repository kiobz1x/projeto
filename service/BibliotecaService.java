package service;

import dao.UsuarioDAO;
import dao.ObraDAO;
import dao.EmprestimoDAO;
import model.Usuario;
import model.Obra;
import model.Emprestimo;

import java.util.List;

public class BibliotecaService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ObraDAO obraDAO = new ObraDAO();
    private EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

    private List<Usuario> usuarios;
    private List<Obra> obras;
    private List<Emprestimo> emprestimos;

    public BibliotecaService() {
        usuarios = usuarioDAO.carregar();
        obras = obraDAO.carregar();
        emprestimos = emprestimoDAO.carregar();
    }

    public void salvarTudo() {
        usuarioDAO.salvar(usuarios);
        obraDAO.salvar(obras);
        emprestimoDAO.salvar(emprestimos);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.carregar();
    }

    public List<Obra> listarObras() {
        return obraDAO.carregar();
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimoDAO.carregar();
    }


    // Aqui você pode criar outros métodos para devolver obra, buscar usuário, etc.
}
