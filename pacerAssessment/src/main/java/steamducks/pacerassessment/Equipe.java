package steamducks.pacerassessment;

public class Equipe {

    private String nome;
    private String gitHub;

    public Equipe(String nome, String gitHub) {
        this.nome = nome;
        this.gitHub = gitHub;
    }

    public String getNome() {
        return nome;
    }

    public String getGitHub() {
        return gitHub;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }
}
