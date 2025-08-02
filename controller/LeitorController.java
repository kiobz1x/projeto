package controller;

import dao.LeitorDAO;
import dao.UsuarioDAO;
import exception.leitor.LeitorJaExisteException;
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
        String novaMatricula = gerarMatriculaLeitor();
        leitor.setMatricula(novaMatricula);

        if (leitorDAO.buscarPorMatricula(novaMatricula) != null || usuarioDAO.buscarPorMatricula(novaMatricula) != null) {
            throw new LeitorJaExisteException();
        }

        leitor.setMatricula(novaMatricula);
        leitorDAO.adicionar(leitor);
        System.out.println("Leitor cadastrado com sucesso. Matrícula: " + novaMatricula);
    }

    public List<Leitor> listarLeitores() {
        return leitorDAO.listar();
    }

    public Leitor buscarLeitorPorMatricula(String matricula) {
        return leitorDAO.buscarPorMatricula(matricula);
    }
    
    public boolean atualizarLeitor(String matricula, String nome ,String telefone, String email){
    	Leitor leitorExiste = leitorDAO.buscarPorMatricula(matricula);
    	
    	if(leitorExiste != null) {
    		leitorExiste.setNome(nome);
    		leitorExiste.setTelefone(telefone);
    		leitorExiste.setEmail(email);
    		leitorDAO.atualizarLeitor(leitorExiste);
    		System.out.println("Leitor atualizado");
    		return true;
    	}else {
    		System.out.println("Leitor não encontrado");
    		return false;
    	}
    		
    }

    public boolean removerLeitor(String matricula) {
    	boolean remover = leitorDAO.remover(matricula);
    	if(remover) {
    		System.out.println("Leitor excluido");
    	}else {
    		System.out.println("Leitor não encontrado");
    	}
    	
    	return remover;
    }

    // Método que gera matrícula no padrão LEI-00001
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
