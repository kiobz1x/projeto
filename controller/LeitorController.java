package controller;

import dao.LeitorDAO;
import model.Leitor;

import java.util.List;

public class LeitorController {

    private LeitorDAO leitorDAO;

    public LeitorController() {
        this.leitorDAO = new LeitorDAO();
    }

    public boolean cadastrarLeitor(Leitor leitor) {
        if (leitorDAO.buscarPorMatricula(leitor.getMatricula()) != null) {
            System.out.println("Já existe um leitor com esta matrícula.");
            return false;
        }
        return leitorDAO.adicionar(leitor);
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
