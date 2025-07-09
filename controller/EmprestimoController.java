package controller;

import dao.EmprestimoDAO;
import model.Emprestimo;
import model.Obra;
import model.Usuario;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoController {
    private final EmprestimoDAO dao;
    private List<Emprestimo> emprestimos;

    public EmprestimoController() {
        this.dao = new EmprestimoDAO();
        this.emprestimos = dao.carregar();
    }

    public boolean realizarEmprestimo(Usuario usuario, Obra obra) {
        if (!obra.emprestar(LocalDate.now())) {
            System.out.println("❌ Obra já está emprestada.");
            return false;
        }

        Emprestimo novo = new Emprestimo(usuario, obra, LocalDate.now());
        emprestimos.add(novo);
        dao.salvar(emprestimos);

        System.out.println("✅ Empréstimo realizado com sucesso!");
        return true;
    }

    public boolean realizarDevolucao(Obra obra) {
        for (Emprestimo emp : emprestimos) {
            if (emp.getObra().getCodigo().equals(obra.getCodigo()) && emp.getDataDevolucao() == null) {
                boolean noPrazo = obra.devolver(LocalDate.now());
                emp.devolver(LocalDate.now()); // registra a data na classe Emprestimo
                dao.salvar(emprestimos);

                if (noPrazo) {
                    System.out.println("✅ Devolução no prazo.");
                } else {
                    System.out.println("⚠ A devolução ocorreu com atraso. Uma multa deve ser cobrada.");
                }

                return true;
            }
        }

        System.out.println("❌ Empréstimo não encontrado.");
        return false;
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimos;
    }

    public Emprestimo encontrarEmprestimoPorCodigoObra(String codigo) {
        for (Emprestimo emp : emprestimos) {
            if (emp.getObra().getCodigo().equals(codigo) && emp.getDataDevolucao() == null) {
                return emp;
            }
        }
        return null;
    }
}
