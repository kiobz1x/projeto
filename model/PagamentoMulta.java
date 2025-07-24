package model;

import java.time.LocalDate;

public class PagamentoMulta {
    private String id;
    private double valor;
    private LocalDate dataPagamento;
    private MetodoPagamento metodo;
    private Leitor leitor;

    public PagamentoMulta(String id, double valor, LocalDate dataPagamento, MetodoPagamento metodo, Leitor leitor) { //antes tava (String id, double valor, LocalDate dataPagamento, MetodoPagamento metodo, Usuario usuario)
        this.id = id;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.metodo = metodo;
        this.leitor = leitor;
    }

    public String getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public MetodoPagamento getMetodo() {
        return metodo;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    @Override
    public String toString() {
        return "Pagamento ID: " + id +
                ", Valor: R$" + valor +
                ", Data: " + dataPagamento +
                ", Método: " + metodo +
                ", Usuário: " + leitor.getNome();
    }
}
