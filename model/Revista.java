package model;

public class Revista extends Obra {
    public Revista(String codigo, String titulo, String autor, int anoPublicacao) {
        super(codigo, titulo, autor, anoPublicacao);
    }

    @Override
    public int getTempoEmprestimo() {
        return 3;
    }
}
