package controller;

import dao.EmprestimoDAO;
import dao.LeitorDAO;
import dao.ObraDAO;
import model.Emprestimo;
import model.Leitor;
import model.Obra;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoController {
    private final EmprestimoDAO emprestimoDAO;
    private final ObraDAO obraDAO;
    private final LeitorDAO leitorDAO;
    private List<Emprestimo> emprestimos;

    public EmprestimoController() {
        this.emprestimoDAO = new EmprestimoDAO();
        this.obraDAO = new ObraDAO();
        this.leitorDAO = new LeitorDAO();
        this.emprestimos = emprestimoDAO.carregar();
    }

    public boolean realizarEmprestimo(String leitorId, String obraCodigo) {
        Leitor leitor = leitorDAO.buscarPorMatricula(leitorId);
        Obra obra = obraDAO.buscarPorCodigo(obraCodigo);

        if (leitor == null) {
            System.out.println("❌ Leitor não encontrado.");
            return false;
        }
        if (obra == null) {
            System.out.println("❌ Obra não encontrada.");
            return false;
        }
        if (!obra.isDisponivel()) {
            System.out.println("❌ Obra já está emprestada.");
            return false;
        }

        boolean sucessoEmprestimo = obra.emprestar(LocalDate.now());
        if (!sucessoEmprestimo) {
            System.out.println("❌ Erro ao emprestar a obra.");
            return false;
        }

        Emprestimo novoEmprestimo = new Emprestimo(leitorId, obra);
        emprestimos.add(novoEmprestimo);

        obraDAO.salvar(obraDAO.carregar());
        emprestimoDAO.salvar(emprestimos);

        System.out.println("✅ Empréstimo realizado com sucesso!");
        return true;
    }

    public boolean realizarDevolucao(String obraCodigo) {
        for (Emprestimo emp : emprestimos) {
            if (emp.getObra().getCodigo().equalsIgnoreCase(obraCodigo) && emp.getDataDevolucao() == null) {
                emp.setDataDevolucao(LocalDate.now());

                Obra obra = obraDAO.buscarPorCodigo(obraCodigo);
                if (obra != null) {
                    obra.devolver(LocalDate.now());
                    obraDAO.salvar(obraDAO.carregar());
                }

                emprestimoDAO.salvar(emprestimos);

                if (emp.getDataDevolucao().isAfter(emp.getDataPrevistaDevolucao())) {
                    System.out.println("⚠ Devolução com atraso! Multa deve ser gerada.");
                    // multaController.gerarMulta(emp);
                } else {
                    System.out.println("✅ Devolução no prazo.");
                }

                return true;
            }
        }
        System.out.println("❌ Empréstimo não encontrado para essa obra.");
        return false;
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimos;
    }

    public Emprestimo encontrarEmprestimoPorId(String id) {
        for (Emprestimo e : emprestimos) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }
    public Emprestimo encontrarEmprestimoAtivoPorObra(String codigoObra) {
        for (Emprestimo emp : emprestimos) {
            if (emp.getObra().getCodigo().equalsIgnoreCase(codigoObra)
                    && emp.getDataDevolucao() == null) {
                return emp;
            }
        }
        return null;
    }

    public Leitor getLeitorDoEmprestimo(Emprestimo e) {
        return leitorDAO.buscarPorMatricula(e.getLeitorId());
    }

    public Obra getObraDoEmprestimo(Emprestimo e) {
        return e.getObra();
    }
    public boolean realizarEmprestimoComData(String leitorId, String obraCodigo, LocalDate dataEmprestimoForcada) {
        Leitor leitor = leitorDAO.buscarPorMatricula(leitorId);
        Obra obra = obraDAO.buscarPorCodigo(obraCodigo);

        if (leitor == null || obra == null || !obra.isDisponivel()) {
            System.out.println("❌ Erro: Leitor ou obra inválidos ou já emprestados.");
            return false;
        }

        boolean sucesso = obra.emprestar(dataEmprestimoForcada);
        if (!sucesso) {
            System.out.println("❌ Não foi possível emprestar.");
            return false;
        }

        Emprestimo emp = new Emprestimo(leitorId, obra);
        emp.setDataEmprestimo(dataEmprestimoForcada); // ⚠️ Força a data

        emprestimos.add(emp);
        obraDAO.salvar(obraDAO.carregar());
        emprestimoDAO.salvar(emprestimos);

        System.out.println("📘 Empréstimo simulado com data: " + dataEmprestimoForcada);
        return true;
    }

    public boolean realizarDevolucaoComData(String obraCodigo, LocalDate dataDevolucaoForcada) {
        for (Emprestimo emp : emprestimos) {
            if (emp.getObra().getCodigo().equalsIgnoreCase(obraCodigo) && emp.getDataDevolucao() == null) {
                emp.setDataDevolucao(dataDevolucaoForcada);

                Obra obra = obraDAO.buscarPorCodigo(obraCodigo);
                if (obra != null) {
                    obra.devolver(dataDevolucaoForcada);
                    obraDAO.salvar(obraDAO.carregar());
                }

                emprestimoDAO.salvar(emprestimos);

                System.out.println("📦 Devolução com data simulada: " + dataDevolucaoForcada);
                return true;
            }
        }
        System.out.println("❌ Empréstimo não encontrado para essa obra.");
        return false;
    }

}
