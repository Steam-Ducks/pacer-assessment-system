package steamducks.SistemaRecap.models;

public class Pontuacao {
    private int idPontuacao;
    private int idEquipe;
    private double pontos;
    private int idSprint;


    // Construtor
    public Pontuacao(int id, int idEquipe, double pontos, int idSprint) {
        this.idPontuacao = id;
        this.idEquipe = idEquipe;
        this.pontos = pontos;
        this.idSprint = idSprint;
    }

    public Pontuacao(double pontos, int idSprint) {
        this.pontos = pontos;
        this.idSprint = idSprint;
    }

    public Pontuacao() {

    }

    // Getters e Setters
    public int getId() {
        return idPontuacao;
    }

    public void setId(int id) {
        this.idPontuacao = id;
    }

    public double getPontos() {
        return pontos;
    }

    public void setPontos(double pontos) {
        this.pontos = pontos;
    }

    public int getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(int idSprint) {
        this.idSprint = idSprint;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    @Override
    public String toString() {
        return "Pontuacao{" +
                "id=" + idPontuacao +
                ", pontos=" + pontos +
                ", idSprint=" + idSprint +
                '}';
    }
}
