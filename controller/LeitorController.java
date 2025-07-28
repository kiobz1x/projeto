package controller;

import dao.LeitorDAO;
import exception.LeitorJaExisteException;
import model.Leitor;

import java.util.List;

public class LeitorController {

    private LeitorDAO leitorDAO;

    public LeitorController() {
        this.leitorDAO = new LeitorDAO();
    }

    public void cadastrarLeitor(Leitor leitor) throws LeitorJaExisteException {
        if (leitorDAO.buscarPorMatricula(leitor.getMatricula()) != null) {
            throw new LeitorJaExisteException();
        }
        leitorDAO.adicionar(leitor);
        System.out.println("✅ Leitor cadastrado com sucesso.");
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
}
