package steamducks.pacerassessment.models;

public class Usuario {
    private String email; // Email como chave primária
    private String nome;
    private String senha;
    private int idEquipe;
    private boolean isProfessor;

    // Construtor
    public Usuario(String nome, String email, String senha, int idEquipe, boolean isProfessor) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idEquipe = idEquipe;
        this.isProfessor = isProfessor;
    }
    public Usuario() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public boolean isProfessor() {
        return isProfessor;
    }

    public void setIsProfessor(boolean isProfessor) {
        this.isProfessor = isProfessor;
    }

    @Override
    public String toString() {
        return nome;
    }
}
