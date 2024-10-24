package steamducks.pacerassessment.models;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private int idEquipe;
    private boolean isProfessor;

    public Usuario(String nome, String email, String senha, int idEquipe, boolean isProfessor) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idEquipe = idEquipe;
        this.isProfessor = isProfessor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public long getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public boolean isProfessor() {
        return isProfessor;
    }

    public void setProfessor(boolean professor) {
        isProfessor = professor;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", idEquipe=" + idEquipe +
                ", isProfessor=" + isProfessor +
                '}';
    }
}
