package model;

public class Leitor {
    private String nome;
    private String matricula; // também único
    private TipoLeitor tipo;
    private String telefone;
    private String email;

    public Leitor(String nome, String matricula, TipoLeitor tipo, String telefone, String email) {
        this.nome = nome;
        this.matricula = matricula;
        this.tipo = tipo;
        this.telefone = telefone;
        this.email = email;
    }

    // Getters & Setters
    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public TipoLeitor getTipo() {
        return tipo;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipo(TipoLeitor tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "[" + matricula + "] " + nome + " - " + tipo;
    }
}

