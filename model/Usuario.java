package model;

public class Usuario {
    private static int contadorMatricula = 1;

    private String nome;
    private String matricula; // único
    private TipoUsuario tipo;
    private String telefone;
    private String email;

    // Construtor com matrícula automática
    public Usuario(String nome, TipoUsuario tipo, String telefone, String email) {
        this.nome = nome;
        this.tipo = tipo;
        this.telefone = telefone;
        this.email = email;
        this.matricula = gerarMatricula();
    }

    public Usuario(String nome, String matricula, TipoUsuario tipo, String telefone, String email) {
        this.nome = nome;
        this.matricula = matricula;
        this.tipo = tipo;
        this.telefone = telefone;
        this.email = email;
    }

    // formato da matricula padronizada USU-00001
    private String gerarMatricula() {
        String novaMatricula = String.format("USU-%05d", contadorMatricula);
        contadorMatricula++;
        return novaMatricula;
    }

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
    public void setMatricula(String novaMatricula) {
		this.matricula = novaMatricula;
		
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
