package model;

public class Leitor {
    private static int contadorMatricula = 1;

    private String nome;
    private String matricula; // único
    private TipoLeitor tipo;
    private String telefone;
    private String email;

    // matrícula automatica
    public Leitor(String nome, TipoLeitor tipo, String telefone, String email) {
        this.nome = nome;
        this.tipo = tipo;
        this.telefone = telefone;
        this.email = email;
        this.matricula = gerarMatricula();
    }

    public Leitor(String nome, String matricula, TipoLeitor tipo, String telefone, String email) {
        this.nome = nome;
        this.matricula = matricula;
        this.tipo = tipo;
        this.telefone = telefone;
        this.email = email;
    }

    private String gerarMatricula() {
        String novaMatricula = String.format("LEI-%05d", contadorMatricula);
        contadorMatricula++;
        return novaMatricula;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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
