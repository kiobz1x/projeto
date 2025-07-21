package controller;

import dao.MultaDAO;
import model.Multa;
import model.Emprestimo;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MultaController {
    private final MultaDAO dao;
    private List<Multa> multas;

    public MultaController() {
        this.dao = new MultaDAO();
        this.multas = dao.carregar();
    }

    // Recebe todos os empréstimos para verificar quais estão atrasados e gerar multas
    public void gerarMultasAutomaticamente(List<Emprestimo> emprestimos) {
        for (Emprestimo e : emprestimos) {
            if (e.getDataDevolucao() == null) continue; // Não devolvido ainda, ignora

            if (e.isAtrasado()) {
                long diasAtraso = ChronoUnit.DAYS.between(e.getDataPrevistaDevolucao(), e.getDataDevolucao());

                // Verifica se já existe multa para esse empréstimo
                boolean multaExiste = multas.stream()
                    .anyMatch(m -> m.getIdEmprestimo().equals(e.getId()));

                if (!multaExiste) {
                    double valor = diasAtraso * 2.50; // R$2,50 por dia de atraso
                    Multa multa = new Multa(
                        e.getId(),
                        e.getUsuario().getNome(),
                        e.getObra().getTitulo(),
                        e.getDataDevolucao(),
                        (int) diasAtraso,
                        valor
                    );
                    multas.add(multa);
                    System.out.println(" Multa gerada para empréstimo ID " + e.getId() + ": R$" + valor);
                }
            }
        }
        dao.salvar(multas);
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

    public boolean pagarMulta(String idEmprestimo) {
        for (Multa m : multas) {
            if (m.getIdEmprestimo().equals(idEmprestimo) && !m.isPaga()) {
                m.setPaga(true);
                dao.salvar(multas);
                System.out.println("✅ Multa paga com sucesso.");
                return true;
            }
        }
        System.out.println("❌ Multa não encontrada ou já paga.");
        return false;
    }

    public void listarTodasMultas() {
        if (multas.isEmpty()) {
            System.out.println(" Nenhuma multa registrada.");
        } else {
            System.out.println(" Lista de todas as multas:");
            for (Multa m : multas) {
                System.out.println(m);
            }
        }
    }
}
