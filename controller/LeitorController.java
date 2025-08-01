package controller;

import dao.LeitorDAO;
import dao.UsuarioDAO;
import exception.leitor.LeitorJaExisteException;
import exception.leitor.LeitorNaoExisteException;
import model.Leitor;

import java.util.List;

public class LeitorController {

    private LeitorDAO leitorDAO;
    private UsuarioDAO usuarioDAO;

    public LeitorController() {
        this.leitorDAO = new LeitorDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public void cadastrarLeitor(Leitor leitor) throws LeitorJaExisteException {
        // Gera matrícula automaticamente
        String novaMatricula = gerarMatriculaLeitor();
        leitor.setMatricula(novaMatricula);

        // Verifica se já existe no LeitorDAO ou no UsuarioDAO
        boolean jaExisteComoLeitor = leitorDAO.buscarPorMatricula(novaMatricula) != null;
        boolean jaExisteComoUsuario = usuarioDAO.buscarPorMatricula(novaMatricula) != null;

        if (jaExisteComoLeitor || jaExisteComoUsuario) {
            throw new LeitorJaExisteException();
        }

        leitorDAO.adicionar(leitor);
        System.out.println("✅ Leitor cadastrado com sucesso. Matrícula: " + novaMatricula);
    }

    public List<Leitor> listarLeitores() {
        return leitorDAO.listar();
    }

    public Leitor buscarLeitorPorMatricula(String matricula) {
        return leitorDAO.buscarPorMatricula(matricula);
    }

    public boolean removerLeitor(String matricula) {
        return leitorDAO.remover(matricula);
    }

    // 🔧 Método que gera matrícula no padrão LEI-00001
    private String gerarMatriculaLeitor() {
        int maiorNumero = 0;
        for (Leitor l : leitorDAO.listar()) {
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
        return String.format("LEI-%05d", maiorNumero + 1);
    }
}
