package model;

import java.time.LocalDate;

public interface Emprestavel {
    boolean emprestar(LocalDate dataEmprestimo);
    boolean devolver(LocalDate dataDevolucao);
    boolean isDisponivel();
    LocalDate getDataPrevistaDevolucao();
}
