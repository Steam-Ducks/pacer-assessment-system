package steamducks.pacerassessment;

public class Aluno {
    private String nome;
    private String email;
    private String senha;
    private Equipe equipe;

    public Aluno(String nome, String email, String senha, Equipe equipe) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.equipe = equipe;
    }

    // Getters e Setters
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

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", equipe='" + equipe.getNome() + '\'' +
                '}';
    }
}
