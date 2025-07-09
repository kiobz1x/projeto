package model;

public class Livro extends Obra {
    public Livro(String codigo, String titulo, String autor, int anoPublicacao) {
        super(codigo, titulo, autor, anoPublicacao);
    }

    @Override
    public int getTempoEmprestimo() {
        return 7;
    }
}
