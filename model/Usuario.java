package model;

public class Usuario {
    private String nome;
    private String matricula; // deve ser única
    private TipoUsuario tipo;
    private String telefone;
    private String email;

    public Usuario(String nome, String matricula, TipoUsuario tipo, String telefone, String email) {
        this.nome = nome;
        this.matricula = matricula;
        this.tipo = tipo;
        this.telefone = telefone;
        this.email = email;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "[" + matricula + "] " + nome + " - " + tipo;
    }
}
