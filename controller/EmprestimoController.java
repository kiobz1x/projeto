package controller;

import dao.EmprestimoDAO;
import model.Emprestimo;
import model.Obra;
import model.Usuario;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoController {
    private final EmprestimoDAO dao;
    private final ObraController obraController;
    private final MultaController multaController;  // objt MultaController do tipo multacontroller
    private List<Emprestimo> emprestimos;

    public EmprestimoController() {
        this.dao = new EmprestimoDAO();
        this.emprestimos = dao.carregar();
        this.obraController = new ObraController();
        this.multaController = new MultaController();  // chama o multacontroller
    }

    public boolean realizarEmprestimo(Usuario usuario, Obra obra) {
        for (Emprestimo emp : emprestimos) {
            if (emp.getObra().getCodigo().equals(obra.getCodigo()) && emp.getDataDevolucao() == null) {
                System.out.println("❌ Esta obra já está emprestada.");
                return false;
            }
        }
        if (!obra.emprestar(LocalDate.now())) {
            System.out.println("❌ Obra já está emprestada.");
            return false;
        }

        Emprestimo novo = new Emprestimo(usuario, obra, LocalDate.now());
        emprestimos.add(novo);
        dao.salvar(emprestimos);

        obraController.salvarLista();

        System.out.println("✅ Empréstimo realizado com sucesso!");
        return true;
    }

    public boolean realizarDevolucao(Obra obraInformada) {
        for (Emprestimo emp : emprestimos) {
            if (emp.getObra().getCodigo().equals(obraInformada.getCodigo()) && emp.getDataDevolucao() == null) {
                Obra obraReal = emp.getObra();

                boolean noPrazo = obraReal.devolver(LocalDate.now());
                emp.devolver(LocalDate.now());

                dao.salvar(emprestimos);
                obraController.salvarLista();

                if (noPrazo) {
                    System.out.println("✅ Devolução no prazo.");
                } else {
                    System.out.println("⚠ A devolução ocorreu com atraso. Uma multa será gerada.");
                    multaController.gerarMultasAutomaticamente(List.of(emp));  // Gera multa para esse empréstimo atrasado
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
