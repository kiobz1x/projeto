package model;

import java.time.LocalDate;

public class Multa {
    private String idEmprestimo;    // ID do empréstimo atrasado
    private String nomeLeitor;     // Nome do leitor para facilitar exibição
    private String tituloObra;      // Título da obra para exibição
    private LocalDate dataDevolucao;
    private int diasAtraso;
    private double valor;
    private boolean paga;

    public Multa(String idEmprestimo, String nomeLeitor, String tituloObra,
                 LocalDate dataDevolucao, int diasAtraso, double valor) {
        this.idEmprestimo = idEmprestimo;
        this.nomeLeitor = nomeLeitor;
        this.tituloObra = tituloObra;
        this.dataDevolucao = dataDevolucao;
        this.diasAtraso = diasAtraso;
        this.valor = valor;
        this.paga = false; // Multa criada como não paga
    }

    public String getIdEmprestimo() {
        return idEmprestimo;
    }

    public String getNomeLeitor() {
        return nomeLeitor;
    }

    public String getTituloObra() {
        return tituloObra;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public double getValor() {
        return valor;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    @Override
    public String toString() {
        return "Multa [Empréstimo ID: " + idEmprestimo + 
               ", Usuário: " + nomeLeitor + 
               ", Obra: " + tituloObra + 
               ", Devolução: " + dataDevolucao + 
               ", Dias atraso: " + diasAtraso + 
               ", Valor: R$" + valor + 
               ", Paga: " + (paga ? "Sim" : "Não") + "]";
    }
}
