package model;

import java.time.LocalDate;

public abstract class Obra implements Emprestavel {
    protected String codigo;
    protected String titulo;
    protected String autor;
    protected int anoPublicacao;
    protected boolean emprestado;

    // Novos atributos para controle do empréstimo
    protected LocalDate dataEmprestimo;
    protected LocalDate dataPrevistaDevolucao;

    public Obra(String codigo, String titulo, String autor, int anoPublicacao) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.emprestado = false;
    }

    // Método abstrato já presente
    public abstract int getTempoEmprestimo();

    // Implementações de Emprestavel
    @Override
    public boolean emprestar(LocalDate dataEmprestimo) {
        if (emprestado) return false;
        this.emprestado = true;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataEmprestimo.plusDays(getTempoEmprestimo());
        return true;
    }

    @Override
    public boolean devolver(LocalDate dataDevolucao) {
        if (!emprestado) return false;
        this.emprestado = false;
        this.dataEmprestimo = null;
        this.dataPrevistaDevolucao = null;
        return true;
    }

    @Override
    public boolean isDisponivel() {
        return !emprestado;
    }

    @Override
    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    // Getters e Setters
    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public boolean isEmprestado() { return emprestado; }

    public void setEmprestado(boolean emprestado) { this.emprestado = emprestado; }

    @Override
    public String toString() {
        return "[" + codigo + "] " + titulo + " - " + autor + " (" + anoPublicacao + ")";
    }
}
