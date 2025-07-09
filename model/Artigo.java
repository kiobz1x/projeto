package model;

public class Artigo extends Obra {
    public Artigo(String codigo, String titulo, String autor, int anoPublicacao) {
        super(codigo, titulo, autor, anoPublicacao);
    }

    @Override
    public int getTempoEmprestimo() {
        return 2;
    }
}
