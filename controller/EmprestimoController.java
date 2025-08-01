package controller;

import java.time.LocalDate;
import java.util.List;

import dao.EmprestimoDAO;
import dao.LeitorDAO;
import dao.ObraDAO;
import exception.emprestimo.DevolucaoInvalidaException;
import exception.emprestimo.EmprestimoJaRealizadoException;
import exception.emprestimo.EmprestimoNaoEncontradoException;
import model.Emprestimo;
import model.Leitor;
import model.Obra;

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

    public boolean realizarEmprestimo(String leitorId, String obraCodigo) throws EmprestimoJaRealizadoException {
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
            throw new EmprestimoJaRealizadoException("A obra com código " + obraCodigo + " já está emprestada.");
        }

        boolean sucessoEmprestimo = obra.emprestar(LocalDate.now());
        if (!sucessoEmprestimo) {
            System.out.println("❌ Erro ao emprestar a obra.");
            return false;
        }

        Emprestimo novoEmprestimo = new Emprestimo(leitorId, obra);
        emprestimos.add(novoEmprestimo);

        atualizarObraNoArquivo(obra);
        emprestimoDAO.salvar(emprestimos);

        System.out.println("✅ Empréstimo realizado com sucesso!");
        return true;
    }

    public boolean realizarDevolucao(String obraCodigo) throws DevolucaoInvalidaException {
        Obra obra = obraDAO.buscarPorCodigo(obraCodigo);
        if (obra == null) {
            throw new DevolucaoInvalidaException("Obra com código " + obraCodigo + " não encontrada.");
        }

        if (obra.isDisponivel()) {
            throw new DevolucaoInvalidaException("A obra com código " + obraCodigo + " não está emprestada.");
        }

        for (Emprestimo emp : emprestimos) {
            if (emp.getObra().getCodigo().equalsIgnoreCase(obraCodigo) && emp.getDataDevolucao() == null) {
                emp.setDataDevolucao(LocalDate.now());

                obra.devolver(LocalDate.now());
                atualizarObraNoArquivo(obra);
                emprestimoDAO.salvar(emprestimos);

                if (emp.getDataDevolucao().isAfter(emp.getDataPrevistaDevolucao())) {
                    System.out.println("⚠ Devolução com atraso! Multa deve ser gerada.");
                } else {
                    System.out.println("✅ Devolução no prazo.");
                }

                return true;
            }
        }

        throw new DevolucaoInvalidaException("Nenhum empréstimo ativo encontrado para a obra de código " + obraCodigo + ".");
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimos;
    }

    public Emprestimo encontrarEmprestimoPorId(String id) throws EmprestimoNaoEncontradoException {
        for (Emprestimo e : emprestimos) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        throw new EmprestimoNaoEncontradoException("Empréstimo com ID " + id + " não encontrado.");
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

    public boolean realizarEmprestimoComData(String leitorId, String obraCodigo, LocalDate dataEmprestimoForcada)
            throws EmprestimoJaRealizadoException {
        Leitor leitor = leitorDAO.buscarPorMatricula(leitorId);
        Obra obra = obraDAO.buscarPorCodigo(obraCodigo);

        if (leitor == null || obra == null || !obra.isDisponivel()) {
            System.out.println("❌ Erro: Leitor ou obra inválidos ou já emprestados.");
            return false;
        }

        boolean sucesso = obra.emprestar(dataEmprestimoForcada);
        if (!sucesso) {
            throw new EmprestimoJaRealizadoException("A obra com código " + obraCodigo + " já está emprestada.");
        }

        Emprestimo emp = new Emprestimo(leitorId, obra);
        emp.setDataEmprestimo(dataEmprestimoForcada);

        emprestimos.add(emp);
        atualizarObraNoArquivo(obra);
        emprestimoDAO.salvar(emprestimos);

        System.out.println("📘 Empréstimo simulado com data: " + dataEmprestimoForcada);
        return true;
    }

    public boolean realizarDevolucaoComData(String obraCodigo, LocalDate dataDevolucaoForcada) throws DevolucaoInvalidaException {
        Obra obra = obraDAO.buscarPorCodigo(obraCodigo);
        if (obra == null || obra.isDisponivel()) {
            throw new DevolucaoInvalidaException("Devolução inválida: obra não encontrada ou não está emprestada.");
        }

        for (Emprestimo emp : emprestimos) {
            if (emp.getObra().getCodigo().equalsIgnoreCase(obraCodigo) && emp.getDataDevolucao() == null) {
                emp.setDataDevolucao(dataDevolucaoForcada);

                obra.devolver(dataDevolucaoForcada);
                atualizarObraNoArquivo(obra);
                emprestimoDAO.salvar(emprestimos);

                System.out.println("📦 Devolução com data simulada: " + dataDevolucaoForcada);
                return true;
            }
        }

        throw new DevolucaoInvalidaException("Nenhum empréstimo ativo encontrado para essa obra.");
    }

    // 👉 Função auxiliar para atualizar uma obra específica no JSON
    private void atualizarObraNoArquivo(Obra obraAtualizada) {
        List<Obra> obras = obraDAO.carregar();
        for (int i = 0; i < obras.size(); i++) {
            if (obras.get(i).getCodigo().equalsIgnoreCase(obraAtualizada.getCodigo())) {
                obras.set(i, obraAtualizada);
                break;
            }
        }
        obraDAO.salvar(obras);
    }
}
