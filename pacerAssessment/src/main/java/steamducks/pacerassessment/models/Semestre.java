package steamducks.pacerassessment.models;

public class Semestre {

    private int idSemestre;
    private String nome;

    public Semestre(int idSemestre, String nome) {
        this.idSemestre = idSemestre;
        this.nome = nome;
    }

    public Semestre(String nome) {
        this.nome = nome;
    }

    public int getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(int idSemestre) {
        this.idSemestre = idSemestre;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Semestre{" +
                "idSemestre=" + idSemestre +
                ", nome='" + nome + '\'' +
                '}';
    }
}
