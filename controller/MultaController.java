package controller;

import dao.MultaDAO;
import dao.LeitorDAO;
import dao.ObraDAO;
import dao.PagamentoMultaDAO;
import exception.MultaJaPagaException;
import exception.MultaNaoEncontradaException;
import model.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MultaController {
    private final MultaDAO dao;
    private final PagamentoMultaDAO pagamentoDAO;
    private final LeitorDAO leitorDAO;
    private final ObraDAO obraDAO;

    private List<Multa> multas;
    private List<PagamentoMulta> pagamentos;

    public MultaController() {
        this.dao = new MultaDAO();
        this.pagamentoDAO = new PagamentoMultaDAO();
        this.leitorDAO = new LeitorDAO();
        this.obraDAO = new ObraDAO();
        this.multas = dao.carregar();
        this.pagamentos = pagamentoDAO.carregar();
    }

    public void gerarMultasAutomaticamente(List<Emprestimo> emprestimos) {
        for (Emprestimo e : emprestimos) {
            if (e.getDataDevolucao() == null) continue;

            if (e.isAtrasado()) {
                long diasAtraso = ChronoUnit.DAYS.between(
                        e.getDataPrevistaDevolucao(), e.getDataDevolucao()
                );

                boolean multaExiste = multas.stream()
                        .anyMatch(m -> m.getIdEmprestimo().equals(e.getId()));

                if (!multaExiste) {
                    double valor = diasAtraso * 2.50;

                    String leitorNome = "Desconhecido";
                    String tituloObra = "Desconhecido";

                    Leitor leitor = leitorDAO.buscarPorMatricula(e.getLeitorId());
                    if (leitor != null) leitorNome = leitor.getNome();

                    Obra obra = obraDAO.buscarPorCodigo(e.getObra().getCodigo());
                    if (obra != null) tituloObra = obra.getTitulo();

                    Multa multa = new Multa(
                            e.getId(),
                            leitorNome,
                            tituloObra,
                            e.getDataDevolucao(),
                            (int) diasAtraso,
                            valor
                    );

                    multas.add(multa);
                    System.out.println("📌 Multa gerada para empréstimo ID " + e.getId() + ": R$" + valor);
                }
            }
        }
        dao.salvar(multas);
    }

    public void gerarMultasAutomaticamente() {
        EmprestimoController emprestimoController = new EmprestimoController();
        List<Emprestimo> emprestimos = emprestimoController.listarEmprestimos();
        gerarMultasAutomaticamente(emprestimos);
    }

    public List<Multa> listarMultasPendentes() {
        List<Multa> pendentes = new ArrayList<>();
        for (Multa m : multas) {
            if (!m.isPaga()) {
                pendentes.add(m);
            }
        }
        return pendentes;
    }

    public boolean pagarMulta(String idEmprestimo) throws MultaNaoEncontradaException, MultaJaPagaException {
        for (Multa m : multas) {
            if (m.getIdEmprestimo().equals(idEmprestimo)) {
                if (m.isPaga()) {
                    throw new MultaJaPagaException("A multa do empréstimo " + idEmprestimo + " já foi paga.");
                }

                m.setPaga(true);
                dao.salvar(multas);
                System.out.println("✅ Multa paga com sucesso.");
                return true;
            }
        }

        throw new MultaNaoEncontradaException("Nenhuma multa encontrada para o empréstimo " + idEmprestimo + ".");
    }

    // ✅ Novo método com registro completo
    public boolean pagarMultaComRegistro(String idEmprestimo, MetodoPagamento metodo, Leitor leitor) { 
        for (Multa m : multas) {
            if (m.getIdEmprestimo().equals(idEmprestimo) && !m.isPaga()) {
                m.setPaga(true);
                dao.salvar(multas);

                PagamentoMulta pagamento = new PagamentoMulta(
                        UUID.randomUUID().toString(),
                        m.getValor(),
                        LocalDate.now(),
                        metodo,
                        leitor
                );

                pagamentos.add(pagamento);
                pagamentoDAO.salvar(pagamentos);

                System.out.println("✅ Multa paga e registrada com sucesso.");
                return true;
            }
        }
        System.out.println("❌ Multa não encontrada ou já paga.");
        return false;
    }

    public void listarTodasMultas() {
        if (multas.isEmpty()) {
            System.out.println("📭 Nenhuma multa registrada.");
        } else {
            System.out.println("📋 Lista de todas as multas:");
            for (Multa m : multas) {
                System.out.println(m);
            }
        }
    }

    public void listarPagamentos() {
        if (pagamentos.isEmpty()) {
            System.out.println("📭 Nenhum pagamento registrado.");
        } else {
            System.out.println("📋 Lista de pagamentos:");
            for (PagamentoMulta p : pagamentos) {
                System.out.println(p);
            }
        }
    }
}
