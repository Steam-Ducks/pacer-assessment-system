package steamducks.pacerassessment.models;

public class Pontuacao {
    private int id;
    private double pontos;
    private int idSprint;

    // Construtor
    public Pontuacao(int id, double pontos, int idSprint) {
        this.id = id;
        this.pontos = pontos;
        this.idSprint = idSprint;
    }

    public Pontuacao(double pontos, int idSprint) {
        this.pontos = pontos;
        this.idSprint = idSprint;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Pontuacao{" +
                "id=" + id +
                ", pontos=" + pontos +
                ", idSprint=" + idSprint +
                '}';
    }
}
