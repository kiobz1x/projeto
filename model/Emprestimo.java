package model;

import java.time.LocalDate;

public class Emprestimo {
    private Usuario usuario;
    private Obra obra;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao; // null se ainda não devolvido

    public Emprestimo(Usuario usuario, Obra obra, LocalDate dataEmprestimo) {
        this.usuario = usuario;
        this.obra = obra;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataEmprestimo.plusDays(obra.getTempoEmprestimo());
        this.dataDevolucao = null;
        obra.setEmprestado(true); // marca a obra como emprestada
    }

    public boolean devolver(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
        obra.setEmprestado(false);
        return !isAtrasado();
    }

    public boolean isAtrasado() {
        return dataDevolucao != null && dataDevolucao.isAfter(dataPrevistaDevolucao);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Obra getObra() {
        return obra;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    @Override
    public String toString() {
        return "Empréstimo de: " + obra.getTitulo() +
                " para " + usuario.getNome() +
                " em " + dataEmprestimo +
                " (Devolução prevista: " + dataPrevistaDevolucao + ")";
    }
}
