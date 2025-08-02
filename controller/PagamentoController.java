package controller;

import dao.PagamentoDAO;
import model.Leitor;
import model.MetodoPagamento;
import model.PagamentoMulta;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PagamentoController {
    private final PagamentoDAO dao = new PagamentoDAO();
    private List<PagamentoMulta> pagamentos;

    public PagamentoController() {
        this.pagamentos = dao.carregar();
    }

    public void registrarPagamento(Leitor leitor, double valor, MetodoPagamento metodo) { //antes tava (Usuario usuario, double valor, MetodoPagamento metodo)
        String id = UUID.randomUUID().toString();
        PagamentoMulta pagamento = new PagamentoMulta(id, valor, LocalDate.now(), metodo, leitor);
        pagamentos.add(pagamento);
        dao.salvar(pagamentos);

        System.out.println("Pagamento registrado com sucesso:");
        System.out.println(pagamento);
    }

    public List<PagamentoMulta> getTodosPagamentos() {
        return pagamentos;
    }
}
