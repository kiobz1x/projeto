package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Emprestimo {
    private String id;
    private String leitorId; // <<< Corrigido aqui
    private Obra obra;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;

    public Emprestimo(String leitorId, Obra obra) {
        this.id = gerarId();
        this.leitorId = leitorId;
        this.obra = obra;
        this.dataEmprestimo = LocalDate.now();
        this.dataPrevistaDevolucao = dataEmprestimo.plusDays(obra.getTempoEmprestimo());
    }

    private String gerarId() {
        int numero = (int)(Math.random() * 900 + 100);
        return "E" + numero;
    }

    // Getters
    public String getId() { return id; }
    public String getLeitorId() { return leitorId; } // <<< Corrigido aqui
    public Obra getObra() { return obra; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataPrevistaDevolucao() { return dataPrevistaDevolucao; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }

    // Setters
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    // Outros métodos
    public boolean isAtrasado() {
        return dataDevolucao != null && dataDevolucao.isAfter(dataPrevistaDevolucao);
    }

    public String getDataEmprestimoFormatada() {
        return dataEmprestimo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getDataPrevistaDevolucaoFormatada() {
        return dataPrevistaDevolucao.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getDataDevolucaoFormatada() {
        return (dataDevolucao == null) ? "-" : dataDevolucao.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Obra: %s | Leitor: %s | Empréstimo: %s | Previsto: %s | Devolvido: %s",
                id, obra.getCodigo(), leitorId, getDataEmprestimoFormatada(), getDataPrevistaDevolucaoFormatada(), getDataDevolucaoFormatada());
    }
    public void setDataEmprestimo(LocalDate data) {
        this.dataEmprestimo = data;
        this.dataPrevistaDevolucao = data.plusDays(obra.getTempoEmprestimo());
    }


}
