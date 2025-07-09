package model;

import java.time.LocalDate;

public class PagamentoMulta {
    private String id;
    private double valor;
    private LocalDate dataPagamento;
    private MetodoPagamento metodo;
    private Usuario usuario;

    public PagamentoMulta(String id, double valor, LocalDate dataPagamento, MetodoPagamento metodo, Usuario usuario) {
        this.id = id;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.metodo = metodo;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "Pagamento ID: " + id +
                ", Valor: R$" + valor +
                ", Data: " + dataPagamento +
                ", Método: " + metodo +
                ", Usuário: " + usuario.getNome();
    }
}
